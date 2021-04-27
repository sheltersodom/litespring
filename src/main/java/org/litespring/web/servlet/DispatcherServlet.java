package org.litespring.web.servlet;

import org.litespring.beans.BeansException;
import org.litespring.core.io.FileSystemResource;
import org.litespring.ui.ModelMap;
import org.litespring.utils.ClassUtils;
import org.litespring.utils.StringUtils;
import org.litespring.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @autor sheltersodom
 * @create 2021-03-30-11:47
 */
public class DispatcherServlet extends FrameworkServlet {
    public static final String HANDLER_MAPPING_BEAN_NAME = "handlerMapping";
    public static final String HANDLER_ADAPTER_BEAN_NAME = "handlerAdapter";
    public static final String VIEW_RESOLVER_BEAN_NAME = "viewResolver";
    private static final Properties defaultStrategies;
    //TODO:处理文件位置的历史遗留问题
    private static final String DEFAULT_STRATEGIES_PATH = DispatcherServlet.class.getResource("").getPath() + "/DispatcherServlet.properties";

    static {
        try {
            FileSystemResource resource = new FileSystemResource(DEFAULT_STRATEGIES_PATH);
            Properties props = new Properties();
            props.load(resource.getInputStream());
            defaultStrategies = props;
        } catch (IOException e) {
            throw new IllegalStateException("Could not load 'DispatcherServlet.properties': " + e.getMessage());
        }
    }

    private List<HandlerMapping> handlerMappings;
    private List<HandlerAdapter> handlerAdapters;
    private List<ViewResolver> viewResolvers;


    protected void initStrategies(WebApplicationContext context) {
        initHandlerMappings(context);
        initHandlerAdapters(context);
        initViewResolvers(context);
    }

    private <T> List<T> initListComponent(WebApplicationContext context, Class<T> clazz) {
        List<Object> handlerList = context.getBeansByType(clazz);
        List<T> convertedhandlerMappings = ClassUtils.convertList(handlerList, clazz);
        if (convertedhandlerMappings.isEmpty()) {
            return getDefaultStrategies(context, clazz);
        } else {
            return convertedhandlerMappings;
        }
    }

    private void initViewResolvers(WebApplicationContext context) {
        List<ViewResolver> viewResolvers = initListComponent(context, ViewResolver.class);
        this.viewResolvers = viewResolvers;
    }

    private void initHandlerAdapters(WebApplicationContext context) {
        List<HandlerAdapter> handlerAdapters = initListComponent(context, HandlerAdapter.class);
        this.handlerAdapters = handlerAdapters;

    }

    private void initHandlerMappings(WebApplicationContext context) {
        List<HandlerMapping> handlerMappings = initListComponent(context, HandlerMapping.class);
        this.handlerMappings = handlerMappings;
    }

    private <T> List<T> getDefaultStrategies(WebApplicationContext context, Class<T> strategyInterface) {
        String key = strategyInterface.getName();
        String value = defaultStrategies.getProperty(key);
        if (value != null) {
            String[] classNames = StringUtils.commaDelimitedListToStringArray(value);
            List<T> strategies = new ArrayList<>(classNames.length);
            for (String className : classNames) {
                try {
                    Class<?> clazz = this.getClass().getClassLoader().loadClass(className);
                    Object strategy = context.createBean(clazz);
                    strategies.add((T) strategy);
                } catch (ClassNotFoundException e) {
                    throw new BeansException(
                            "Could not find DispatcherServlet's default strategy class [" + className +
                                    "] for interface [" + key + "]", e);
                }
            }
            return strategies;
        }
        return new LinkedList<T>();
    }

    @Override
    protected void onRefresh(WebApplicationContext context) {
        initStrategies(context);
    }

    @Override
    protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
        doDispatch(request, response);
    }

    private void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HandlerExecutionChain mappedHandler = null;
        ModelAndView mv = null;
        try {
            Exception dispatchException = null;
            try {
                //1.获取方法句柄


                mappedHandler = getHandler(request);
                if (mappedHandler == null) {
                    return;
                }
                //2.获取适配器
                HandlerAdapter ha = getHandlerAdapter(mappedHandler.getHandler());

                //3.执行拦截器前置方法
                if (!mappedHandler.applyPreHandle(request, response)) {
                    return;
                }

                //4.执行适配方法
                mv = ha.handle(request, response, mappedHandler.getHandler());

                //5.执行拦截器后置方法
                mappedHandler.applyPostHandle(request, response, mv);
            } catch (Exception e) {
                dispatchException = e;
            }

            //6.视图渲染
            processDispatchResult(request, response, mappedHandler, mv, dispatchException);
        } catch (Exception ex) {
            triggerAfterCompletion(request, response, mappedHandler, ex);
        }

    }

    private void triggerAfterCompletion(HttpServletRequest request,
                                        HttpServletResponse response,
                                        HandlerExecutionChain mappedHandler,
                                        Exception ex) throws Exception {
        if (mappedHandler != null) {
            mappedHandler.triggerAfterCompletion(request, response, ex);
        }
        throw ex;
    }

    private void processDispatchResult(HttpServletRequest request,
                                       HttpServletResponse response,
                                       HandlerExecutionChain mappedHandler,
                                       ModelAndView mv,
                                       Exception ex) throws Exception {

        //1.通过视图解析器获取视图对象
        View view = resolveViewName(mv.getViewName(), mv.getModelMap(), request);
        if (view == null) {
            throw new IllegalStateException(
                    "Could not resolve view with name '" + mv.getViewName() + "' in servlet with name '" +
                            getServletName() + "'");
        }
        view.render(mv.getModelMap(), request, response);
    }

    private View resolveViewName(String viewName, ModelMap modelMap, HttpServletRequest request) {
        for (ViewResolver viewResolver : viewResolvers) {
            View view = viewResolver.resolveViewName(viewName);
            if (view != null) {
                return view;
            }
        }
        return null;
    }

    private HandlerAdapter getHandlerAdapter(Object handler) {
        for (HandlerAdapter handlerAdapter : handlerAdapters) {
            if (handlerAdapter.supports(handler)) {
                return handlerAdapter;
            }
        }
        throw new IllegalStateException("No adapter for handler [" + handler +
                "]: The DispatcherServlet configuration needs to include a HandlerAdapter that supports this handler");
    }

    private HandlerExecutionChain getHandler(HttpServletRequest request) {
        for (HandlerMapping handlerMapping : handlerMappings) {
            HandlerExecutionChain handler = handlerMapping.getHandler(request);
            if (handler != null) {
                return handler;
            }
        }
        return null;
    }
}
