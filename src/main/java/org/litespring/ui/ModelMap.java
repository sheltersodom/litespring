package org.litespring.ui;

import org.litespring.utils.Assert;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @autor sheltersodom
 * @create 2021-04-01-21:31
 */
public class ModelMap extends LinkedHashMap<String,Object> {
    public ModelMap() {
    }
    public ModelMap addAttribute(String attributeName,Object attributeValue){
        Assert.notNull(attributeName, "Model attribute name must not be null");
        put(attributeName, attributeValue);
        return this;
    }

    public ModelMap addAllAttributes(Map<String, ?> attributes) {
        if (attributes != null) {
            putAll(attributes);
        }
        return this;
    }

    public boolean containsAttribute(String attributeName) {
        return containsKey(attributeName);
    }

}
