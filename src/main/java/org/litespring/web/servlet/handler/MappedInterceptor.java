package org.litespring.web.servlet.handler;

import org.litespring.web.servlet.HandlerInterceptor;

import java.util.List;

/**
 * @autor sheltersodom
 * @create 2021-04-01-19:04
 */
public class MappedInterceptor {
    private final List<String> includePatterns;
    private final List<String> excludePatterns;
    private final HandlerInterceptor interceptor;

    public MappedInterceptor(List<String> includePatterns, List<String> excludePatterns, HandlerInterceptor interceptor) {
        this.includePatterns = includePatterns;
        this.excludePatterns = excludePatterns;
        this.interceptor = interceptor;
    }

    public MappedInterceptor(List<String> includePatterns, HandlerInterceptor interceptor) {
        this(includePatterns, null, interceptor);
    }

    public List<String> getPathPatterns() {
        return includePatterns;
    }

    public HandlerInterceptor getInterceptor() {
        return interceptor;
    }

    public boolean matches(String lookupPath) {
        if (this.excludePatterns != null) {
            for (String excludePattern : excludePatterns) {
                if (lookupPath.equals(excludePattern)) {
                    return false;
                }
            }
        }
        if (this.includePatterns == null) {
            return true;
        } else {
            for (String includePattern : includePatterns) {
                if (lookupPath.equals(includePattern)) {
                    return true;
                }
            }
        }
        return false;
    }
}
