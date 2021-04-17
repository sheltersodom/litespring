package org.litespring.web.servlet.view;

import org.litespring.web.servlet.View;
import org.litespring.web.servlet.ViewResolver;

/**
 * @autor sheltersodom
 * @create 2021-04-02-20:35
 */
public class InternalResourceViewResolver implements ViewResolver {
    /**
     * Prefix for special view names that specify a redirect URL (usually
     * to a controller after a form has been submitted and processed).
     * Such view names will not be resolved in the configured default
     * way but rather be treated as special shortcut.
     */
    public static final String REDIRECT_URL_PREFIX = "redirect:";

    /**
     * Prefix for special view names that specify a forward URL (usually
     * to a controller after a form has been submitted and processed).
     * Such view names will not be resolved in the configured default
     * way but rather be treated as special shortcut.
     */
    public static final String FORWARD_URL_PREFIX = "forward:";
    private String prefix = "";

    private String suffix = "";


    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    @Override
    public View resolveViewName(String viewName) {
        // Check for special "redirect:" prefix.
        if (viewName.startsWith(REDIRECT_URL_PREFIX)) {
            String redirectUrl = viewName.substring(REDIRECT_URL_PREFIX.length());
            RedirectView view = new RedirectView(redirectUrl);
            return view;
        }
        // Check for special "forward:" prefix.
        if (viewName.startsWith(FORWARD_URL_PREFIX)) {
            String forwardUrl = viewName.substring(FORWARD_URL_PREFIX.length());
            return new InternalResourceView(forwardUrl);
        }
        return buildView(viewName);
    }

    private View buildView(String viewName) {
        InternalResourceView view = new InternalResourceView();
        view.setUrl(getPrefix() + viewName + getSuffix());
        return view;
    }
}
