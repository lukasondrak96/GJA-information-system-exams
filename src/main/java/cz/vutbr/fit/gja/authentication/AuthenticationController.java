package cz.vutbr.fit.gja.authentication;

import javax.validation.Valid;

import cz.vutbr.fit.gja.services.UserServiceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import cz.vutbr.fit.gja.models.User;

@Controller
public class AuthenticationController {

    @Autowired
    UserServiceDao userServiceDao;

    @GetMapping("/")
    public ModelAndView root() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/exams"); // resources/templates/authentication/login.html
        return modelAndView;
    }

    @GetMapping(value = { "/login" })
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("authentication/login"); // resources/templates/authentication/login.html
        return modelAndView;
    }

    @GetMapping(value = "/register")
    public ModelAndView register() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("authentication/register"); // resources/templates/authentication/register.html
        return modelAndView;
    }

    @GetMapping(value = "/home")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/exams");
        return modelAndView;
    }

    @GetMapping(value = "/admin")
    public ModelAndView adminHome() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/logged/exams");
        return modelAndView;
    }

    @PostMapping(value="/register")
    public ModelAndView registerUser(@Valid User user, BindingResult bindingResult, ModelMap modelMap) {
        ModelAndView modelAndView = new ModelAndView();
        // Check for the validations
        if(bindingResult.hasErrors()) {
            modelAndView.addObject("successMessage", "Prosím opravte chyby ve formuláři!");
            modelMap.addAttribute("bindingResult", bindingResult);
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("authentication/register"); // resources/templates/authentication/register.html
            modelAndView.addObject(user);
        }
        else if(userServiceDao.isUserAlreadyRegistered(user)){
            modelAndView.addObject("successMessage", "Uživatel s touto emailovou adresou již existuje.");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("authentication/register"); // resources/templates/authentication/register.html
            modelAndView.addObject(user);
        }
        else if(userServiceDao.isPasswordSame(user)){
            modelAndView.addObject("successMessage", "Hesla se neshodují!");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("authentication/register"); // resources/templates/authentication/register.html
            modelAndView.addObject(user);
        }
        // we will save the user if, no binding errors
        else {
            userServiceDao.saveUser(user);
            modelAndView.addObject("successMessage", "Uživatel byl úspěšně registrován.");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("authentication/register"); // resources/templates/authentication/register.html
        }

        return modelAndView;
    }
}










