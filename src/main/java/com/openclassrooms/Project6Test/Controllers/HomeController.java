package com.openclassrooms.Project6Test.Controllers;

import com.openclassrooms.Project6Test.Models.*;
import com.openclassrooms.Project6Test.Repositories.TransactionRepository;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
@Transactional
public class HomeController {

    private UserService userService;
    private ConnectionListElementService connectionListElementService;
    private MyUserDetailsService myUserDetailsService;
    private TransactionService transactionService;
    private IbanService ibanService;
    private TransactionRepository transactionRepository;

    @Autowired
    public HomeController(UserService userService, ConnectionListElementService connectionListElementService,
                          MyUserDetailsService myUserDetailsService, TransactionService transactionService,
                          IbanService ibanService, TransactionRepository transactionRepository) {

        this.userService = userService;
        this.connectionListElementService = connectionListElementService;
        this.myUserDetailsService = myUserDetailsService;
        this.transactionService = transactionService;
        this.ibanService = ibanService;
        this.transactionRepository = transactionRepository;
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

    @GetMapping("/user/profile")
    public ModelAndView profile(Authentication authentication) {

        ModelAndView modelAndView = new ModelAndView();

        if(authentication != null){

            List<Iban> ibans = ibanService.getAllIbansByEmail(getUserFromAuthentication(authentication).getEmail());

            List<String> connectionsEmails = connectionListElementService.getAUsersConnectionsEmailsByUserEmail(
                    getUserFromAuthentication(authentication).getEmail());

            modelAndView.setViewName("profile");
            modelAndView.addObject("user", getUserFromAuthentication(authentication));
            modelAndView.addObject("ibans", ibans);
            modelAndView.addObject("connectionsEmails", connectionsEmails);

        }else{

            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("/login");

            modelAndView.setView(redirectView);
        }


        return modelAndView;
    }

    @GetMapping("/user/transfer")
    public ModelAndView transferGet(@ModelAttribute("user")User user, Authentication authentication) {

        ModelAndView modelAndView = new ModelAndView();

        if(authentication != null) {

            List<TransactionDTO> dtos = new ArrayList<>();

            List<Transaction> transactions = transactionService.getAUsersTransactionsByEmail(
                    getUserFromAuthentication(authentication).getEmail());

            if(getUserFromAuthentication(authentication).getRole().getRole().equals("Company")
                    && !transactionRepository.findAll().stream()
                    .filter(t -> t.getTransactionType().getTransactionType()
                            .equals("Regular")).collect(Collectors.toList()).isEmpty()) {

                List<Transaction> regularTransactions = transactionRepository.findAll().stream()
                                                        .filter(t -> t.getTransactionType().getTransactionType()
                                                                .equals("Regular")).collect(Collectors.toList());

                for(Transaction transaction : regularTransactions) {

                    if(transaction.getTransactionType().getTransactionType().equals("Regular")) {

                        TransactionDTO dto = new TransactionDTO();

                        dto.setDescription(transaction.getMadeAt().toString());

                        dto.setAmount(transaction.getMoneyAmount());

                        dto.setConnectionEmail(transaction.getAccount().getUser().getEmail());

                        dtos.add(dto);

                        modelAndView.setViewName("transfer");
                        modelAndView.addObject("transactions", dtos);
                        modelAndView.addObject("request", new TransactionDTO());
                        modelAndView.addObject("user", getUserFromAuthentication(authentication));
                    }
                }

            } else if(!transactions.isEmpty() &&
                    !getUserFromAuthentication(authentication).getRole().getRole().equals("Company")) {

                for(Transaction transaction : transactions) {

                    TransactionDTO dto = new TransactionDTO();

                    dto.setAmount(transaction.getMoneyAmount());

                    if(transaction.getTransactionType().getTransactionType().equals("TopUp")) {

                        dto.setDescription(transaction.getDescription() + " - " + transaction.getOrigin());
                    }

                    if(transaction.getTransactionType().getTransactionType().equals("Withdrawal")) {

                        dto.setDescription(transaction.getDescription() + " - " + transaction.getIban().getIbanString());
                    }

                    if(transaction.getTransactionType().getTransactionType().equals("Regular")) {

                        dto.setDescription(transaction.getDescription());
                    }

                    if(transaction.getConnection() == null) {

                        dto.setConnectionEmail("");

                    }else{

                        dto.setConnectionEmail(transaction.getConnection().getUser().getEmail());
                    }

                    dtos.add(dto);
                }
            }

            if(dtos.isEmpty()){

                dtos.add(new TransactionDTO("No Transactions Yet", "No Transactions Yet", 0.0f));
            }

            modelAndView.setViewName("transfer");
            modelAndView.addObject("transactions", dtos);
            modelAndView.addObject("request", new TransactionDTO());
            modelAndView.addObject("user", getUserFromAuthentication(authentication));

        }else{

            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("/login");

            modelAndView.setView(redirectView);
        }

        return modelAndView;
    }

    @PostMapping("/user/transfer")
    public ModelAndView transferPost(@ModelAttribute("request")TransactionDTO request, Authentication authentication) {

        transactionService.createTransactionByTransactionType(
                "Regular", getUserFromAuthentication(authentication).getEmail(),
                request.getAmount(), request.getConnectionEmail(), request.getDescription());

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/user/profile");

        return new ModelAndView(redirectView);
    }

    @GetMapping("/user/addConnection")
    public ModelAndView addConnectionGet(@ModelAttribute("connectionListElement") ConnectionListElement connectionListElement,
                                         Authentication authentication) {

        ModelAndView modelAndView = new ModelAndView();

        if(authentication != null) {

            modelAndView.setViewName("addConnection");
            modelAndView.addObject("connectionListElement", connectionListElement);

        }else{

            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("/login");

            modelAndView.setView(redirectView);
        }

        return modelAndView;
    }

    @PostMapping("/user/addConnection")
    public ModelAndView addConnection(@ModelAttribute("connectionListElement") ConnectionListElement connectionListElement,
                                      Authentication authentication) {

        connectionListElementService.createConnectionListElement(getUserFromAuthentication(authentication).getEmail(),
                connectionListElement.getConnection().getUser().getEmail());

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/user/profile");

        return new ModelAndView(redirectView);
    }

    @GetMapping("/user/addMoney")
    public ModelAndView addMoneyGet(@ModelAttribute("transaction")Transaction transaction, Authentication authentication) {

        ModelAndView modelAndView = new ModelAndView();

        if(authentication != null) {

            modelAndView.setViewName("addMoney");
            modelAndView.addObject("transaction", transaction);

        }else{

            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("/login");

            modelAndView.setView(redirectView);
        }

        return modelAndView;
    }

    @PostMapping("/user/addMoney")
    public ModelAndView addMoneyPost(@ModelAttribute("transaction")Transaction transaction,
                                 Authentication authentication) {

        User user = getUserFromAuthentication(authentication);

        transactionService.createTransactionByTransactionType(
                "TopUp", user.getEmail(), transaction.getMoneyAmount(), transaction.getOrigin(),
                "Top Up");

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/user/profile");

        return new ModelAndView(redirectView);
    }

    @GetMapping("/user/addIban")
    public ModelAndView addIbanGet(Authentication authentication) {

        ModelAndView modelAndView = new ModelAndView();

        if(authentication != null) {

            modelAndView.setViewName("addIban");

        }else{

            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("/login");

            modelAndView.setView(redirectView);
        }

        return modelAndView;
    }

    @PostMapping("/user/addIban")
    public ModelAndView addIbanPost(@RequestParam String ibanString, Authentication authentication) {

        ibanService.createIban(getUserFromAuthentication(authentication).getEmail(), ibanString);

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/user/addIban");

        return new ModelAndView(redirectView);
    }

    @GetMapping("/user/withdrawal")
    public ModelAndView withdrawalGet(Authentication authentication) {

        ModelAndView modelAndView = new ModelAndView();

        if(authentication != null) {

            modelAndView.setViewName("withdrawal");
            modelAndView.addObject("ibans", getUserFromAuthentication(authentication).getAccount().getIbans());
            modelAndView.addObject("request", new WithdrawalDTO());

        }else{

            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("/login");

            modelAndView.setView(redirectView);
        }

        return modelAndView;
    }

    @PostMapping("/user/withdrawal")
    public ModelAndView withdrawalPost(@ModelAttribute("moneyAmount") String moneyAmount,
                                       @ModelAttribute("account") String iban,
                                       Authentication authentication) {

        transactionService.createTransactionByTransactionType(
                "Withdrawal", getUserFromAuthentication(authentication).getEmail(),
                Float.parseFloat(moneyAmount), iban, "Withdrawal");

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/user/profile");

        return new ModelAndView(redirectView);
    }

    @GetMapping("/user/contact")
    public ModelAndView contact(Authentication authentication) {

        ModelAndView modelAndView = new ModelAndView();

        if(authentication != null) {

            modelAndView.setViewName("contact");

        }else{

            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("/login");

            modelAndView.setView(redirectView);
        }

        return modelAndView;
    }
}
