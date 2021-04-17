package org.litespring.web.servlet.view;

import org.litespring.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @autor sheltersodom
 * @create 2021-04-02-20:49
 */
public class RedirectView implements View {
    private String url = "";

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public RedirectView(String redirectUrl) {
        this.url = redirectUrl;
    }

    @Override
    public void render(Map<String, ?> model,
                       HttpServletRequest request,
                       HttpServletResponse response) throws Exception {

        String targetUrl = getUrl();
        String encodedURL = response.encodeRedirectURL(targetUrl);
        response.sendRedirect(encodedURL);
    }
}
