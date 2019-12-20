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

@Controller
public class AuthenticationController {

    @Autowired
    TeacherServiceDao teacherServiceDao;

    @GetMapping(value = "/error", produces = "text/html")
    public ModelAndView errorHtml() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/exams");
        return modelAndView;
    }

    @GetMapping("/")
    public ModelAndView root() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/exams");
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
        Teacher teacher = new Teacher();
        modelAndView.addObject("teacher", teacher);
        modelAndView.setViewName("authentication/register"); // resources/templates/authentication/register.html
        return modelAndView;
    }

    @GetMapping(value = "/logged/exams")
    public ModelAndView adminHome() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/logged/exams");
        return modelAndView;
    }

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










