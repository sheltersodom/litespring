package org.litespring.web.servlet;

import org.litespring.ui.ModelMap;

import java.util.Map;

/**
 * @autor sheltersodom
 * @create 2021-04-01-20:11
 */
public class ModelAndView {
    private Object view;
    private ModelMap modelMap;

    public ModelAndView() {
    }

    public ModelAndView(String viewName) {
        this.view = viewName;
    }

    public ModelAndView(View view) {
        this.view = view;
    }

    public ModelAndView(String viewName, Map<String, ?> model) {
        this.view = viewName;
        if (model != null) {
            getModelMap().addAllAttributes(model);
        }
    }

    public ModelAndView(View view, Map<String, ?> model) {
        this.view = view;
        if (model != null) {
            getModelMap().addAllAttributes(model);
        }
    }

    public ModelMap getModelMap() {
        return modelMap;
    }

    public void setView(Object view) {
        this.view = view;
    }

    public void setModelMap(ModelMap modelMap) {
        this.modelMap = modelMap;
    }

    public Object getView() {
        return view;
    }

    public String getViewName() {
        return (this.view instanceof String ? (String) this.view : null);
    }


    public ModelAndView addObject(String attributeName, Object attributeValue) {
        getModelMap().addAttribute(attributeName, attributeValue);
        return this;
    }
    public ModelAndView addAllObjects(Map<String, ?> modelMap) {
        getModelMap().addAllAttributes(modelMap);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ModelAndView: ");
        if (isReference()) {
            sb.append("reference to view with name '").append(this.view).append("'");
        } else {
            sb.append("materialized View is [").append(this.view).append(']');
        }
        sb.append("; model is ").append(this.modelMap);
        return sb.toString();
    }

    private boolean isReference() {
        return (this.view instanceof String);
    }
}
