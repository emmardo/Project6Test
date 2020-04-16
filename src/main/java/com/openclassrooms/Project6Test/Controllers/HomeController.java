package com.openclassrooms.Project6Test.Controllers;

import com.openclassrooms.Project6Test.Models.*;
import com.openclassrooms.Project6Test.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.transaction.Transactional;

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
    public ModelAndView registerUser(@ModelAttribute("user")User user) {

        userService.createUserByRole(user.getEmail(),
                new BCryptPasswordEncoder().encode(user.getPassword()), "Regular");

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/login");

        return new ModelAndView(redirectView);
    }

    @GetMapping("/login")
    public ModelAndView login() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }
}
