package com.ningyuan.base.exception;

import com.ningyuan.core.Conf;
import org.springframework.web.servlet.ModelAndView;

public class ViewException extends Exception {

    private String viewName;

    private String errorMessage;

    private ModelAndView modelAndView;

    public ViewException(String viewName, String errorMessage) {
        this.viewName = viewName;
        modelAndView = new ModelAndView(Conf.get(viewName));
        this.errorMessage = errorMessage;
    }

    public ViewException(ModelAndView modelAndView, String errorMessage) {
        this.modelAndView = modelAndView;
        this.errorMessage = errorMessage;
    }

    public ViewException(String errorMessage) {
        this.modelAndView = new ModelAndView("error/index");
        this.modelAndView.addObject("errorMessage", errorMessage);
        this.errorMessage = errorMessage;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ModelAndView getModelAndView() {
        return modelAndView;
    }

    public void setModelAndView(ModelAndView modelAndView) {
        this.modelAndView = modelAndView;
    }
}
