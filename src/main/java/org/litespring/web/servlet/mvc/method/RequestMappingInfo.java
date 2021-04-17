package org.litespring.web.servlet.mvc.method;

import org.litespring.utils.StringUtils;
import org.litespring.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * requestmapping的映射,这里本来应该有对六个属性的过滤,但现在我们只实现一个属性,因此这里不对六个属性都进行描述
 *
 * @autor sheltersodom
 * @create 2021-03-30-20:30
 */
public class RequestMappingInfo {
    private final Set<String> patterns;

    private UrlPathHelper pathHelper;

    public Set<String> getPatterns() {
        return prependLeadingSlash(patterns);
    }

    public RequestMappingInfo(String... patterns) {

        this.patterns = Collections.unmodifiableSet(prependLeadingSlash(asList(patterns)));
        pathHelper=new UrlPathHelper();
    }

    private List<String> asList(String[] patterns) {
        return patterns.length == 0 ? Collections.EMPTY_LIST : Arrays.asList(patterns);
    }

    private Set<String> prependLeadingSlash(Collection<String> patterns) {
        Set<String> result = new LinkedHashSet<>();
        for (String pattern : patterns) {
            //细节处理,如果没有/,需要帮忙加上/,默认是全部映射
            if (StringUtils.hasLength(pattern) && !pattern.startsWith("/")) {
                pattern = "/" + pattern;
            }
            result.add(pattern);
        }
        return result;
    }
    public String getMatchingCondition(HttpServletRequest request) {
        String findPath = this.pathHelper.findPathWithinApplication(request);
        if(patterns.contains(findPath)){
            return findPath;
        }
        return null;
    }


}
