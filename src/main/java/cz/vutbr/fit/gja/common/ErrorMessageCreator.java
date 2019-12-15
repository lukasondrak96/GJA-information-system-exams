package cz.vutbr.fit.gja.common;

import org.springframework.web.servlet.ModelAndView;

public class ErrorMessageCreator {
    public static ModelAndView errorPageWithMessage(ModelAndView modelAndView, String errorMessage) {
        modelAndView.addObject("message", errorMessage);
        modelAndView.setViewName("pages/error");
        return modelAndView;
    }

    public static ModelAndView errorPageWithMessageLogged(ModelAndView modelAndView, String errorMessage) {
        modelAndView.addObject("message", errorMessage);
        modelAndView.setViewName("pages/logged/error");
        return modelAndView;
    }
}
