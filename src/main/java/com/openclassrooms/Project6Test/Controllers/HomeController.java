package com.openclassrooms.Project6Test.Controllers;

import com.openclassrooms.Project6Test.Models.User;
import com.openclassrooms.Project6Test.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HomeController {

    @Autowired
    UserService userService;

    @ModelAttribute
    private User setupForm() {
        return new User(); }

    @GetMapping("/")
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
    public String registerUser(@ModelAttribute User user) {

        userService.createUserByRole(user.getEmail(), user.getPassword(), "Regular");
        return "redirect:/login";
    }
}
