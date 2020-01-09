package cz.vutbr.fit.gja.authentication;

import cz.vutbr.fit.gja.models.teacher.Teacher;
import cz.vutbr.fit.gja.models.teacher.TeacherServiceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;

/**
 * Class for authentication and register
 */
@Controller
public class AuthenticationController {

    @Autowired
    TeacherServiceDao teacherServiceDao;

//    @GetMapping("/")
//    public ModelAndView root() {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("pages/logged/exams");
//        return modelAndView;
//    }

    /**
     * Prepares ModelAndView object of login page for nor logged user
     * @return ModelAndView object
     */
    @GetMapping(value = { "/login" })
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("authentication/login"); // resources/templates/authentication/login.html
        return modelAndView;
    }

    /**
     * Prepares ModelAndView object of register page nor not Logged user
     * @return ModelAndView object
     */
    @GetMapping(value = "/register")
    public ModelAndView register() {
        ModelAndView modelAndView = new ModelAndView();
        Teacher teacher = new Teacher();
        modelAndView.addObject("teacher", teacher);
        modelAndView.setViewName("authentication/register"); // resources/templates/authentication/register.html
        return modelAndView;
    }

    /**
     * Prepares ModelAndView object of register page after send form
     * @param teacher user features
     * @param bindingResult - success or error information
     * @param modelMap - model for adding a message to a page
     * @return
     */
    @PostMapping(value="/register")
    public ModelAndView registerUser(@Valid Teacher teacher, BindingResult bindingResult, ModelMap modelMap) {
        ModelAndView modelAndView = new ModelAndView();
        // Check for the validations
        if(bindingResult.hasErrors()) {
            modelAndView.addObject("Message", "Prosím opravte chyby ve formuláři!");
            modelMap.addAttribute("bindingResult", bindingResult);
            modelAndView.addObject("teacher", new Teacher());
            modelAndView.setViewName("authentication/register"); // resources/templates/authentication/register.html
            modelAndView.addObject(teacher);
        }
        else if(teacherServiceDao.isTeacherAlreadySaved(teacher)){
            modelAndView.addObject("Message", "Uživatel s touto emailovou adresou již existuje.");
            modelAndView.addObject("teacher", new Teacher());
            modelAndView.setViewName("authentication/register"); // resources/templates/authentication/register.html
            modelAndView.addObject(teacher);
        }
        else if(teacherServiceDao.isPasswordSame(teacher)){
            modelAndView.addObject("Message", "Hesla se neshodují!");
            modelAndView.addObject("teacher", new Teacher());
            modelAndView.setViewName("authentication/register"); // resources/templates/authentication/register.html
            modelAndView.addObject(teacher);
        }
        // we will save the teacher if, no binding errors
        else {
            teacherServiceDao.saveTeacher(teacher);
            modelAndView.addObject("Message", "Uživatel byl úspěšně registrován.");
            modelAndView.addObject("teacher", new Teacher());
            modelAndView.setViewName("authentication/register"); // resources/templates/authentication/register.html
        }

        return modelAndView;
    }
}










