package org.litespring.web.servlet.view;

import org.litespring.web.servlet.View;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @autor sheltersodom
 * @create 2021-04-02-20:50
 */
public class InternalResourceView implements View {
    private String url = "";

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public InternalResourceView(String url) {
        this.url = url;
    }

    public InternalResourceView() {

    }

    @Override
    public void render(Map<String, ?> model,
                       HttpServletRequest request,
                       HttpServletResponse response) throws Exception {
        //1.暴露隐含模型中的属性给request中
        exposeModelAsRequestAttributes(model, request);

        //2.准备转发地址
        String forwardUrl = getUrl();

        //3.准备转发工具
        RequestDispatcher rd = getRequestDispatcher(request, forwardUrl);

        //4.实施转发
        rd.forward(request, response);
    }

    private RequestDispatcher getRequestDispatcher(HttpServletRequest request, String forwardUrl) {
        return request.getRequestDispatcher(forwardUrl);
    }

    private void exposeModelAsRequestAttributes(Map<String, ?> model, HttpServletRequest request) {
        if (model == null) return;
        for (Map.Entry<String, ?> entry : model.entrySet()) {
            String modelName = entry.getKey();
            Object modelValue = entry.getValue();
            if (modelValue != null) {
                request.setAttribute(modelName, modelValue);
            } else {
                request.removeAttribute(modelName);
            }
        }
    }
}
