package cz.vutbr.fit.gja.common;

import org.springframework.web.servlet.ModelAndView;

/**
 * This class is used to set attributes of ModelAndView object
 */
public class ModelAndViewSetter {
    /**
     * Prepares ModelAndView object of error page for not logged user
     * @param modelAndView ModelAndView object which attributes will be set
     * @param errorMessage Error message, which will be added to given ModelAndView object
     * @return ModelAndView object with set attributes
     */
    public static ModelAndView errorPageWithMessage(ModelAndView modelAndView, String errorMessage) {
        modelAndView.addObject("message", errorMessage);
        modelAndView.setViewName("pages/error");
        return modelAndView;
    }

    /**
     * Prepares ModelAndView object of error page for not logged user
     * @param modelAndView ModelAndView object which attributes will be set
     * @param errorMessage Error message, which will be added to given ModelAndView object
     * @return ModelAndView object with set attributes
     */
    public static ModelAndView errorPageWithMessageLogged(ModelAndView modelAndView, String errorMessage) {
        modelAndView.addObject("message", errorMessage);
        modelAndView.setViewName("pages/logged/error");
        return modelAndView;
    }
}
