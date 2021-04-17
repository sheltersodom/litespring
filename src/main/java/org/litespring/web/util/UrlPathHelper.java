package org.litespring.web.util;

import org.litespring.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @autor sheltersodom
 * @create 2021-03-30-20:52
 */
public class UrlPathHelper {
    /**
     * 获取项目名
     * @param request
     * @return
     */
    public String getContextPath(HttpServletRequest request) {
         String contextPath = request.getContextPath();

        if ("/".equals(contextPath)) {
            // Invalid case, but happens for includes on Jetty: silently adapt it.
            contextPath = "";
        }
        return contextPath;
    }

    /**
     * 获取项目名+servlet
     * @param request
     * @return
     */
    public String getRequestUri(HttpServletRequest request) {
        return request.getRequestURI();
    }

    /**
     * 寻找路径
     * @param request
     * @return
     */
    public String findPathWithinApplication(HttpServletRequest request) {
        String contextPath = getContextPath(request);
        String requestUri = getRequestUri(request);
        String path = getRemainingPath(requestUri, contextPath, true);
        if (path != null) {
            // Normal case: URI contains context path.
            return (StringUtils.hasText(path) ? path : "/");
        }
        else {
            return requestUri;
        }
    }
    private String getRemainingPath(String requestUri, String mapping, boolean ignoreCase) {
        int index1 = 0;
        int index2 = 0;
        for ( ; (index1 < requestUri.length()) && (index2 < mapping.length()); index1++, index2++) {
            char c1 = requestUri.charAt(index1);
            char c2 = mapping.charAt(index2);
            if (c1 == ';') {
                index1 = requestUri.indexOf('/', index1);
                if (index1 == -1) {
                    return null;
                }
                c1 = requestUri.charAt(index1);
            }
            if (c1 == c2) {
                continue;
            }
            if (ignoreCase && (Character.toLowerCase(c1) == Character.toLowerCase(c2))) {
                continue;
            }
            return null;
        }
        if (index2 != mapping.length()) {
            return null;
        }
        if (index1 == requestUri.length()) {
            return "";
        }
        else if (requestUri.charAt(index1) == ';') {
            index1 = requestUri.indexOf('/', index1);
        }
        return (index1 != -1) ? requestUri.substring(index1) : "";
    }




}
