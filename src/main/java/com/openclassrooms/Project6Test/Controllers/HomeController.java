package com.openclassrooms.Project6Test.Controllers;

import com.openclassrooms.Project6Test.Models.ConnectionListElement;
import com.openclassrooms.Project6Test.Models.User;
import com.openclassrooms.Project6Test.Services.ConnectionListElementService;
import com.openclassrooms.Project6Test.Services.ConnectionService;
import com.openclassrooms.Project6Test.Services.MyUserDetailsService;
import com.openclassrooms.Project6Test.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/")
public class HomeController {

    private UserService userService;
    private ConnectionService connectionService;
    private ConnectionListElementService connectionListElementService;
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public HomeController(UserService userService, ConnectionService connectionService,
                          ConnectionListElementService connectionListElementService,
                          MyUserDetailsService myUserDetailsService) {

        this.userService = userService;
        this.connectionService = connectionService;
        this.connectionListElementService = connectionListElementService;
        this.myUserDetailsService = myUserDetailsService;
    }

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

        userService.createUserByRole(user.getEmail(), passwordEncoder.encode(user.getPassword()), "Regular");

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

    @PostMapping("/loginPost")
    public ModelAndView loginUser(@ModelAttribute("user")User user) {

        CharSequence pass = user.getPassword();

        UserDetails userDetails = myUserDetailsService.loadUserByUsername(user.getEmail());

        if (passwordEncoder.encode(pass).equals(userDetails.getPassword())) {

            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("/profile");

            return new ModelAndView(redirectView);

        } else {

            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("/login");

            return new ModelAndView(redirectView);
        }
    }

    @GetMapping("/profile")
    public ModelAndView profile() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("profile");
        return modelAndView;
    }

    @PostMapping("/addConnection")
    public ModelAndView addConnection(@ModelAttribute("ConnectionEmail")String connectionEmail) {

        if(connectionService.getConnectionByEmail(connectionEmail) != null) {

            /*connectionListElementService.createConnectionListElement();*/
        };

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/addConnection");

        return new ModelAndView(redirectView);
    }
}
