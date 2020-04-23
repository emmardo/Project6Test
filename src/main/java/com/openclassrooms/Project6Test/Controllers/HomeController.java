package com.openclassrooms.Project6Test.Controllers;

import com.openclassrooms.Project6Test.Models.*;
import com.openclassrooms.Project6Test.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@Transactional
public class HomeController {

    private UserService userService;

    @Autowired
    public HomeController(UserService userService) {

        this.userService = userService;
    }

    @RequestMapping("/")
    public ModelAndView home() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        return modelAndView;
    }

    @GetMapping("/register")
    public ModelAndView register(@ModelAttribute("user")User user) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(user);
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView registerUser(@Valid @ModelAttribute("user")User user, BindingResult result) {

        ModelAndView modelAndView = new ModelAndView();
        RedirectView redirectView = new RedirectView();

        if(user == null || user.getEmail().equals("") || user.getEmail().length() < 7 || user.getEmail().length() > 50
                || user.getPassword().equals("") || user.getPassword().length() < 2 ) {

            result.reject("error.login");
        }

        if (!result.hasErrors()) {

            userService.createUserByRole(user.getEmail(),
                    new BCryptPasswordEncoder().encode(user.getPassword()), "Regular");

            redirectView.setUrl("/login");
            modelAndView.setView(redirectView);

        } else {

            redirectView.setUrl("/register");
            modelAndView.setView(redirectView);
            modelAndView.addObject("errorMessage", "Email or Password Invalid");
            modelAndView.addObject("bindingResult", result);
        }

        return modelAndView;
    }

    @GetMapping("/login")
    public ModelAndView login() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }
}
