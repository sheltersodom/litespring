package org.litespring.web.servlet;

import org.litespring.context.ApplicationContext;
import org.litespring.web.context.ContextLoader;
import org.litespring.web.context.WebApplicationContext;
import org.litespring.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @autor sheltersodom
 * @create 2021-04-02-21:35
 */
public abstract class FrameworkServlet extends HttpServlet {

    public static final String DEFAULT_CONFIG_LOCATION = "/WEB-INF/applicationContext.xml";
    private static String configLocation = "";


    public static String getConfigLocation() {
        return configLocation;
    }

    public static void setConfigLocation(String configLocation) {
        FrameworkServlet.configLocation = configLocation;
    }

    @Override
    public void init() throws ServletException {
        WebApplicationContext rootContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        if (rootContext == null) {
            ContextLoader loader = new ContextLoader();
            loader.initWebApplicationContext(getServletContext());
        }
        onRefresh(rootContext);
    }

    protected abstract void onRefresh(WebApplicationContext context);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            doService(request, response);
        } catch (ServletException ex) {
            throw ex;
        } catch (IOException ex) {
            throw ex;
        } catch (Throwable ex) {
            throw new IllegalStateException("Request processing failed", ex);
        }
    }

    protected abstract void doService(HttpServletRequest request, HttpServletResponse response) throws Exception;


}
