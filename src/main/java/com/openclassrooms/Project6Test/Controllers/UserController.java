package com.openclassrooms.Project6Test.Controllers;

import com.openclassrooms.Project6Test.Models.*;
import com.openclassrooms.Project6Test.Repositories.TransactionRepository;
import com.openclassrooms.Project6Test.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {

    private UserService userService;
    private ConnectionListElementService connectionListElementService;
    private TransactionService transactionService;
    private IbanService ibanService;
    private TransactionRepository transactionRepository;

    @Autowired
    public UserController(UserService userService, ConnectionListElementService connectionListElementService,
                          TransactionService transactionService, IbanService ibanService,
                          TransactionRepository transactionRepository) {

        this.userService = userService;
        this.connectionListElementService = connectionListElementService;
        this.transactionService = transactionService;
        this.ibanService = ibanService;
        this.transactionRepository = transactionRepository;
    }

    @GetMapping("/user/transfer")
    public ModelAndView transferGet() {

        ModelAndView modelAndView = new ModelAndView();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Object principal = authentication.getPrincipal();

        if(!principal.equals("anonymousUser")) {

            List<TransactionDTO> dtos = new ArrayList<>();

            User user = userService.getUserFromAuthentication(authentication);

            String userEmail = user.getEmail();

            List<ConnectionListElement> connectionListElementList = connectionListElementService
                    .getConnectionListElementsByUserEmail(userEmail);

            List<Transaction> transactions = transactionService.getAUsersTransactionsByEmail(userEmail);

            if(user.getRole().getRole().equals("Company")
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
                        modelAndView.addObject("connectionListElementList", connectionListElementList);
                    }
                }

            } else if(!transactions.isEmpty() && !user.getRole().getRole().equals("Company")) {

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
            modelAndView.addObject("connectionListElementList", connectionListElementList);

        }else{

            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("/login");

            modelAndView.setView(redirectView);
        }

        return modelAndView;
    }

    @PostMapping("/user/transfer")
    public ModelAndView transferPost(@RequestParam("email") String email, @RequestParam("amount") String amount,
                                     @RequestParam("description") String description) {

        ModelAndView modelAndView = new ModelAndView();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Object principal = authentication.getPrincipal();

        if(!principal.equals("anonymousUser")) {

            transactionService.createTransactionByTransactionType(
                    "Regular", userService.getUserFromAuthentication(authentication).getEmail(),
                    Float.parseFloat(amount), email, description);

            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("/user/profile");

            modelAndView.setView(redirectView);
        }else{

            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("/login");

            modelAndView.setView(redirectView);
        }
        return modelAndView;
    }

    @GetMapping("/user/profile")
    public ModelAndView profile() {

        ModelAndView modelAndView = new ModelAndView();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Object principal = authentication.getPrincipal();

        if(!principal.equals("anonymousUser")) {

            String email = userService.getUserFromAuthentication(authentication).getEmail();

            List<Iban> ibans = ibanService.getAllIbansByEmail(email);

            List<String> connectionsEmails = connectionListElementService.getAUsersConnectionsEmailsByUserEmail(email);

            modelAndView.setViewName("profile");
            modelAndView.addObject("user", userService.getUserByEmail(email));
            modelAndView.addObject("ibans", ibans);
            modelAndView.addObject("connectionsEmails", connectionsEmails);

        }else{

            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("/login");

            modelAndView.setView(redirectView);
        }
        return modelAndView;
    }

    @GetMapping("/user/addConnection")
    public ModelAndView addConnectionGet(@ModelAttribute("user") User user) {

        ModelAndView modelAndView = new ModelAndView();

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!principal.equals("anonymousUser")) {

            modelAndView.setViewName("addConnection");
            modelAndView.addObject("user", user);

        }else{

            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("/login");

            modelAndView.setView(redirectView);
        }
        return modelAndView;
    }

    @PostMapping("/user/addConnection")
    public ModelAndView addConnectionPost(@ModelAttribute("user") User user) {

        ModelAndView modelAndView = new ModelAndView();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Object principal = authentication.getPrincipal();

        if(!principal.equals("anonymousUser")) {

            connectionListElementService.createConnectionListElement(
                    userService.getUserFromAuthentication(authentication).getEmail(),
                    user.getEmail());

            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("/user/profile");

            modelAndView.setView(redirectView);
        }else{

            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("/login");

            modelAndView.setView(redirectView);
        }
        return modelAndView;
    }

    @GetMapping("/user/addMoney")
    public ModelAndView addMoneyGet(@ModelAttribute("transaction") Transaction transaction) {

        ModelAndView modelAndView = new ModelAndView();

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!principal.equals("anonymousUser")) {

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
    public ModelAndView addMoneyPost(@ModelAttribute("transaction")Transaction transaction) {

        ModelAndView modelAndView = new ModelAndView();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Object principal = authentication.getPrincipal();

        if(!principal.equals("anonymousUser")) {

            User user = userService.getUserFromAuthentication(authentication);

            transactionService.createTransactionByTransactionType(
                    "TopUp", user.getEmail(), transaction.getMoneyAmount(), transaction.getOrigin(),
                    "Top Up");

            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("/user/profile");

            modelAndView.setView(redirectView);

        }else{

            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("/login");

            modelAndView.setView(redirectView);
        }
        return modelAndView;
    }

    @GetMapping("/user/addIban")
    public ModelAndView addIbanGet() {

        ModelAndView modelAndView = new ModelAndView();

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!principal.equals("anonymousUser")) {

            modelAndView.setViewName("addIban");

        }else{

            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("/login");

            modelAndView.setView(redirectView);
        }
        return modelAndView;
    }

    @PostMapping("/user/addIban")
    public ModelAndView addIbanPost(@RequestParam("ibanString") String ibanString) {

        ModelAndView modelAndView = new ModelAndView();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Object principal = authentication.getPrincipal();

        if(!principal.equals("anonymousUser")) {

            ibanService.createIban(userService.getUserFromAuthentication(authentication).getEmail(), ibanString);

            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("/user/addIban");

            modelAndView.setView(redirectView);

        }else{

            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("/login");

            modelAndView.setView(redirectView);
        }
        return modelAndView;
    }

    @GetMapping("/user/withdrawal")
    public ModelAndView withdrawalGet() {

        ModelAndView modelAndView = new ModelAndView();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Object principal = authentication.getPrincipal();

        if(!principal.equals("anonymousUser")) {

            modelAndView.setViewName("withdrawal");
            modelAndView.addObject("ibans",
                    userService.getUserFromAuthentication(authentication).getAccount().getIbans());
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
                                       @ModelAttribute("account") String iban) {

        ModelAndView modelAndView = new ModelAndView();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Object principal = authentication.getPrincipal();

        if(!principal.equals("anonymousUser")) {

            transactionService.createTransactionByTransactionType(
                    "Withdrawal", userService.getUserFromAuthentication(authentication).getEmail(),
                    Float.parseFloat(moneyAmount), iban, "Withdrawal");

            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("/user/profile");

            modelAndView.setView(redirectView);

        }else{

            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("/login");

            modelAndView.setView(redirectView);
        }
        return modelAndView;
    }

    @GetMapping("/user/contact")
    public ModelAndView contact() {

        ModelAndView modelAndView = new ModelAndView();

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!principal.equals("anonymousUser")) {

            modelAndView.setViewName("contact");

        }else{

            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("/login");

            modelAndView.setView(redirectView);
        }
        return modelAndView;
    }
}
