package com.openclassrooms.Project6Test.Controllers;

import com.openclassrooms.Project6Test.Models.User;
import com.openclassrooms.Project6Test.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/")
public class HomeController {

    @Autowired
    UserService userService;

    @ModelAttribute
    private User setupForm() {
        return new User(); }

    @RequestMapping("/")
    public ModelAndView home() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        return modelAndView;
    }

    @GetMapping("/register")
    public ModelAndView register() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView registerUser(@ModelAttribute("user")User user) {

        userService.createUserByRole(user.getEmail(), user.getPassword(), "Regular");

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/login");

        return new ModelAndView(redirectView);
    }
}
