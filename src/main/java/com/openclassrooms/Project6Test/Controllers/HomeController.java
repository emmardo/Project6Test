package com.openclassrooms.Project6Test.Controllers;

import com.openclassrooms.Project6Test.Models.ConnectionListElement;
import com.openclassrooms.Project6Test.Models.Iban;
import com.openclassrooms.Project6Test.Models.Transaction;
import com.openclassrooms.Project6Test.Models.User;
import com.openclassrooms.Project6Test.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
@Transactional
public class HomeController {

    private UserService userService;
    private ConnectionService connectionService;
    private ConnectionListElementService connectionListElementService;
    private MyUserDetailsService myUserDetailsService;
    private TransactionService transactionService;
    private IbanService ibanService;

    @Autowired
    public HomeController(UserService userService, ConnectionService connectionService,
                          ConnectionListElementService connectionListElementService,
                          MyUserDetailsService myUserDetailsService, TransactionService transactionService,
                          IbanService ibanService) {

        this.userService = userService;
        this.connectionService = connectionService;
        this.connectionListElementService = connectionListElementService;
        this.myUserDetailsService = myUserDetailsService;
        this.transactionService = transactionService;
        this.ibanService = ibanService;
    }

    @ModelAttribute
    private User setupForm() {
        return new User();
    }

    public User getUserFromAuthentication(Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return userService.getUserByEmail(userDetails.getUsername());
    }

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

    @GetMapping("/profile")
    public ModelAndView profile(Authentication authentication) {

        List<Iban> ibans = ibanService.getAllIbansByEmail(getUserFromAuthentication(authentication).getEmail());

        List<String> connectionsEmails = connectionListElementService.getAUsersConnectionsEmailsByUserEmail(
                                            getUserFromAuthentication(authentication).getEmail());

        ModelAndView modelAndView = new ModelAndView("/profile");
        modelAndView.addObject("user", getUserFromAuthentication(authentication));
        modelAndView.addObject("ibans", ibans);
        modelAndView.addObject("connectionsEmails", connectionsEmails);
        return modelAndView;
    }

    @GetMapping("/transfer")
    public ModelAndView transfer(/*@ModelAttribute("user")User user, */Authentication authentication) {

        ModelAndView modelAndView = new ModelAndView("/transfer");
        modelAndView.addObject("user", getUserFromAuthentication(authentication));
        return modelAndView;
    }

    @GetMapping("/addConnection")
    public ModelAndView addConnectionGet(@ModelAttribute("connectionListElement") ConnectionListElement connectionListElement) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("addConnection");
        modelAndView.addObject("connectionListElement", connectionListElement);
        return modelAndView;
    }

    @PostMapping("/addConnection")
    public ModelAndView addConnection(@ModelAttribute("connectionListElement") ConnectionListElement connectionListElement,
                                      Authentication authentication) {

        connectionListElementService.createConnectionListElement(getUserFromAuthentication(authentication).getEmail(),
                connectionListElement.getConnection().getUser().getEmail());

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/profile");

        return new ModelAndView(redirectView);
    }

    @GetMapping("/addMoney")
    public ModelAndView addMoneyGet(@ModelAttribute("transaction")Transaction transaction) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("addMoney");
        modelAndView.addObject("transaction", transaction);
        return modelAndView;
    }

    @PostMapping("/addMoney")
    public ModelAndView addMoneyPost(@ModelAttribute("transaction")Transaction transaction,
                                 Authentication authentication) {

        User user = getUserFromAuthentication(authentication);

        transactionService.createTransactionByTransactionType(
                "TopUp", user.getEmail(), transaction.getMoneyAmount(), transaction.getOrigin());

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/profile");

        return new ModelAndView(redirectView);
    }

    @GetMapping("/addIban")
    public ModelAndView addIbanGet() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("addIban");
        return modelAndView;
    }

    @PostMapping("/addIban")
    public ModelAndView addIbanPost(@RequestParam String ibanString, Authentication authentication) {

        ibanService.createIban(getUserFromAuthentication(authentication).getEmail(), ibanString);

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/addIban");

        return new ModelAndView(redirectView);
    }

    @GetMapping("/withdrawal")
    public ModelAndView withdrawalGet(Authentication authentication) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("withdrawal");
        modelAndView.addObject("ibans", getUserFromAuthentication(authentication).getAccount().getIbans());
        modelAndView.addObject("iban", new Iban());
        return modelAndView;
    }

    @PostMapping("/withdrawal")
    public ModelAndView withdrawalPost(@ModelAttribute("moneyAmount")float moneyAmount,
                                       @ModelAttribute("ibanString") Iban iban,
                                     Authentication authentication) {

        transactionService.createTransactionByTransactionType(
                "Withdrawal", getUserFromAuthentication(authentication).getEmail(), moneyAmount,
                iban.getIbanString());

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/profile");

        return new ModelAndView(redirectView);
    }

    @GetMapping("/contact")
    public ModelAndView contact() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("contact");
        return modelAndView;
    }
}
