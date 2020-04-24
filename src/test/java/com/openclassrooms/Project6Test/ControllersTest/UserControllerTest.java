package com.openclassrooms.Project6Test.ControllersTest;

import com.openclassrooms.Project6Test.Models.*;
import com.openclassrooms.Project6Test.Repositories.*;
import com.openclassrooms.Project6Test.Services.ConnectionListElementService;
import com.openclassrooms.Project6Test.Services.IbanService;
import com.openclassrooms.Project6Test.Services.TransactionService;
import com.openclassrooms.Project6Test.Services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = SpringSecurityWebAuxTestConfig.class
)
@AutoConfigureMockMvc
public class UserControllerTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private ConnectionRepository connectionRepository;

    @MockBean
    private ConnectionListElementRepository connectionListElementRepository;

    @MockBean
    private TransactionRepository transactionRepository;

    @MockBean
    private IbanRepository ibanRepository;


    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @WithUserDetails("regular@paymybuddy.com")
    public void transferGet_regularUserLoggedIn_requestSuccessful() throws Exception {
        String regularUserEmail = "regular@paymybuddy.com";

        float balance = 100f;

        Account regularUserAccount = new Account();
        regularUserAccount.setAccountStatus(new AccountStatus("Active"));
        regularUserAccount.setCurrentBalance(balance);

        Role regularRole = new Role();
        regularRole.setRole("Regular");

        User regularUser = new User(regularUserEmail, "password", new Role("Regular"));
        regularUser.setActive(true);
        regularUser.setRole(regularRole);

        String connectionUserEmail = "abc@abc.com";

        Account connectionUserAccount = new Account();
        connectionUserAccount.setAccountStatus(new AccountStatus("Active"));
        connectionUserAccount.setCurrentBalance(balance);

        User connectionUser = new User(connectionUserEmail, "password", new Role("Regular"));
        connectionUser.setActive(true);
        connectionUser.setRole(regularRole);

        Connection connection = new Connection();
        connection.setUser(connectionUser);

        String companysEmail = "000@000.com";

        Account companyAccount = new Account();
        companyAccount.setCurrentBalance(balance);

        List<Transaction> transactionList = new ArrayList<>();

        Transaction transaction = new Transaction();
        transaction.setMoneyAmount(100f);
        transaction.setOrigin("Bank Account");

        Mockito.when(transactionRepository.findTransactionsByAccountUserEmail(regularUserEmail))
                .thenReturn(transactionList);

        Mockito.when(transactionRepository.findTransactionsByConnectionUserEmail(regularUserEmail))
                .thenReturn(transactionList);


        Mockito.when(userRepository.findUserByEmail(regularUserEmail)).thenReturn(regularUser);

        Mockito.when(accountRepository.findAccountByUserEmail(regularUserEmail)).thenReturn(regularUserAccount);

        Mockito.when(accountRepository.findAccountByUserEmail(connectionUserEmail)).thenReturn(connectionUserAccount);

        Mockito.when(userRepository.findUserByEmail(connectionUserEmail)).thenReturn(connectionUser);

        Mockito.when(accountRepository.findAccountByUserEmail(companysEmail)).thenReturn(companyAccount);

        Mockito.when(connectionRepository.findConnectionByUserEmail(anyString())).thenReturn(connection);

        Mockito.when(ibanRepository.save(any(Iban.class))).thenReturn(new Iban());

        Mockito.when(transactionRepository.save(any(Transaction.class))).thenReturn(new Transaction());

        Mockito.when(accountRepository.save(any(Account.class))).thenReturn(new Account());

        mockMvc.perform(MockMvcRequestBuilders.get("/user/transfer"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithUserDetails("admin@paymybuddy.com")
    public void transferGet_adminUserLoggedIn_requestSuccessful() throws Exception {
        String adminUserEmail = "admin@paymybuddy.com";

        float balance = 100f;

        Account adminUserAccount = new Account();
        adminUserAccount.setAccountStatus(new AccountStatus("Active"));
        adminUserAccount.setCurrentBalance(balance);

        Role adminRole = new Role();
        adminRole.setRole("Admin");

        User adminUser = new User(adminUserEmail, "password", new Role("Admin"));
        adminUser.setActive(true);
        adminUser.setRole(adminRole);

        String connectionUserEmail = "abc@abc.com";

        Account connectionUserAccount = new Account();
        connectionUserAccount.setAccountStatus(new AccountStatus("Active"));
        connectionUserAccount.setCurrentBalance(balance);

        User connectionUser = new User(connectionUserEmail, "password", new Role("Admin"));
        connectionUser.setActive(true);
        connectionUser.setRole(adminRole);

        Connection connection = new Connection();
        connection.setUser(connectionUser);

        String companysEmail = "000@000.com";

        Account companyAccount = new Account();
        companyAccount.setCurrentBalance(balance);

        List<Transaction> transactionList = new ArrayList<>();

        Transaction transaction = new Transaction();
        transaction.setMoneyAmount(100f);
        transaction.setOrigin("Bank Account");

        Mockito.when(transactionRepository.findTransactionsByAccountUserEmail(adminUserEmail))
                .thenReturn(transactionList);

        Mockito.when(transactionRepository.findTransactionsByConnectionUserEmail(adminUserEmail))
                .thenReturn(transactionList);


        Mockito.when(userRepository.findUserByEmail(adminUserEmail)).thenReturn(adminUser);

        Mockito.when(accountRepository.findAccountByUserEmail(adminUserEmail)).thenReturn(adminUserAccount);

        Mockito.when(accountRepository.findAccountByUserEmail(connectionUserEmail)).thenReturn(connectionUserAccount);

        Mockito.when(userRepository.findUserByEmail(connectionUserEmail)).thenReturn(connectionUser);

        Mockito.when(accountRepository.findAccountByUserEmail(companysEmail)).thenReturn(companyAccount);

        Mockito.when(connectionRepository.findConnectionByUserEmail(anyString())).thenReturn(connection);

        Mockito.when(ibanRepository.save(any(Iban.class))).thenReturn(new Iban());

        Mockito.when(transactionRepository.save(any(Transaction.class))).thenReturn(new Transaction());

        Mockito.when(accountRepository.save(any(Account.class))).thenReturn(new Account());

        mockMvc.perform(MockMvcRequestBuilders.get("/user/transfer"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithUserDetails("company@paymybuddy.com")
    public void transferGet_companyUserLoggedIn_requestSuccessful() throws Exception {
        String companyUserEmail = "company@paymybuddy.com";

        float balance = 100f;

        Account companyUserAccount = new Account();
        companyUserAccount.setAccountStatus(new AccountStatus("Active"));
        companyUserAccount.setCurrentBalance(balance);

        Role companyRole = new Role();
        companyRole.setRole("Company");

        User companyUser = new User(companyUserEmail, "password", new Role("Company"));
        companyUser.setActive(true);
        companyUser.setRole(companyRole);

        String connectionUserEmail = "abc@abc.com";

        Account connectionUserAccount = new Account();
        connectionUserAccount.setAccountStatus(new AccountStatus("Active"));
        connectionUserAccount.setCurrentBalance(balance);

        User connectionUser = new User(connectionUserEmail, "password", new Role("Company"));
        connectionUser.setActive(true);
        connectionUser.setRole(companyRole);

        Connection connection = new Connection();
        connection.setUser(connectionUser);

        String companysEmail = "000@000.com";

        Account companyAccount = new Account();
        companyAccount.setCurrentBalance(balance);

        List<Transaction> transactionList = new ArrayList<>();

        Transaction transaction = new Transaction();
        transaction.setMoneyAmount(100f);
        transaction.setOrigin("Bank Account");

        Mockito.when(transactionRepository.findTransactionsByAccountUserEmail(companyUserEmail))
                .thenReturn(transactionList);

        Mockito.when(transactionRepository.findTransactionsByConnectionUserEmail(companyUserEmail))
                .thenReturn(transactionList);


        Mockito.when(userRepository.findUserByEmail(companyUserEmail)).thenReturn(companyUser);

        Mockito.when(accountRepository.findAccountByUserEmail(companyUserEmail)).thenReturn(companyUserAccount);

        Mockito.when(accountRepository.findAccountByUserEmail(connectionUserEmail)).thenReturn(connectionUserAccount);

        Mockito.when(userRepository.findUserByEmail(connectionUserEmail)).thenReturn(connectionUser);

        Mockito.when(accountRepository.findAccountByUserEmail(companysEmail)).thenReturn(companyAccount);

        Mockito.when(connectionRepository.findConnectionByUserEmail(anyString())).thenReturn(connection);

        Mockito.when(ibanRepository.save(any(Iban.class))).thenReturn(new Iban());

        Mockito.when(transactionRepository.save(any(Transaction.class))).thenReturn(new Transaction());

        Mockito.when(accountRepository.save(any(Account.class))).thenReturn(new Account());

        mockMvc.perform(MockMvcRequestBuilders.get("/user/transfer"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void transferGet_anonymousUserAttemptsRequest_requestUnsuccessful() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/user/transfer"))
                .andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/login"));
    }

    @Test
    @WithUserDetails("regular@paymybuddy.com")
    public void transferPost_regularUserLoggedIn_requestSuccessful() throws Exception {
        String regularUserEmail = "regular@paymybuddy.com";

        float balance = 100f;

        Account regularUserAccount = new Account();
        regularUserAccount.setAccountStatus(new AccountStatus("Active"));
        regularUserAccount.setCurrentBalance(balance);

        Role regularRole = new Role();
        regularRole.setRole("Regular");

        User regularUser = new User(regularUserEmail, "password", new Role("Regular"));
        regularUser.setActive(true);
        regularUser.setRole(regularRole);

        String connectionUserEmail = "abc@abc.com";

        Account connectionUserAccount = new Account();
        connectionUserAccount.setAccountStatus(new AccountStatus("Active"));
        connectionUserAccount.setCurrentBalance(balance);

        User connectionUser = new User(connectionUserEmail, "password", new Role("Regular"));
        connectionUser.setActive(true);
        connectionUser.setRole(regularRole);

        Connection connection = new Connection();
        connection.setUser(connectionUser);

        String companysEmail = "000@000.com";

        Account companyAccount = new Account();
        companyAccount.setCurrentBalance(balance);


        Mockito.when(userRepository.findUserByEmail(regularUserEmail)).thenReturn(regularUser);

        Mockito.when(accountRepository.findAccountByUserEmail(regularUserEmail)).thenReturn(regularUserAccount);

        Mockito.when(accountRepository.findAccountByUserEmail(connectionUserEmail)).thenReturn(connectionUserAccount);

        Mockito.when(userRepository.findUserByEmail(connectionUserEmail)).thenReturn(connectionUser);

        Mockito.when(accountRepository.findAccountByUserEmail(companysEmail)).thenReturn(companyAccount);

        Mockito.when(connectionRepository.findConnectionByUserEmail(anyString())).thenReturn(connection);

        Mockito.when(ibanRepository.save(any(Iban.class))).thenReturn(new Iban());

        Mockito.when(transactionRepository.save(any(Transaction.class))).thenReturn(new Transaction());

        Mockito.when(accountRepository.save(any(Account.class))).thenReturn(new Account());

        mockMvc.perform(MockMvcRequestBuilders.post("/user/transfer").queryParam("amount", "50")
                .queryParam("email", connectionUserEmail)
                .queryParam("description", "shoes"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/profile"));
    }

    @Test
    @WithUserDetails("admin@paymybuddy.com")
    public void transferPost_adminUserLoggedIn_requestSuccessful() throws Exception {
        String adminUserEmail = "admin@paymybuddy.com";

        float balance = 100f;

        Account adminUserAccount = new Account();
        adminUserAccount.setAccountStatus(new AccountStatus("Active"));
        adminUserAccount.setCurrentBalance(balance);

        Role adminRole = new Role();
        adminRole.setRole("Admin");

        User adminUser = new User(adminUserEmail, "password", new Role("Admin"));
        adminUser.setActive(true);
        adminUser.setRole(adminRole);

        String connectionUserEmail = "abc@abc.com";

        Account connectionUserAccount = new Account();
        connectionUserAccount.setAccountStatus(new AccountStatus("Active"));
        connectionUserAccount.setCurrentBalance(balance);

        User connectionUser = new User(connectionUserEmail, "password", new Role("Admin"));
        connectionUser.setActive(true);
        connectionUser.setRole(adminRole);

        Connection connection = new Connection();
        connection.setUser(connectionUser);

        String companysEmail = "000@000.com";

        Account companyAccount = new Account();
        companyAccount.setCurrentBalance(balance);


        Mockito.when(userRepository.findUserByEmail(adminUserEmail)).thenReturn(adminUser);

        Mockito.when(accountRepository.findAccountByUserEmail(adminUserEmail)).thenReturn(adminUserAccount);

        Mockito.when(accountRepository.findAccountByUserEmail(connectionUserEmail)).thenReturn(connectionUserAccount);

        Mockito.when(userRepository.findUserByEmail(connectionUserEmail)).thenReturn(connectionUser);

        Mockito.when(accountRepository.findAccountByUserEmail(companysEmail)).thenReturn(companyAccount);

        Mockito.when(connectionRepository.findConnectionByUserEmail(anyString())).thenReturn(connection);

        Mockito.when(ibanRepository.save(any(Iban.class))).thenReturn(new Iban());

        Mockito.when(transactionRepository.save(any(Transaction.class))).thenReturn(new Transaction());

        Mockito.when(accountRepository.save(any(Account.class))).thenReturn(new Account());

        mockMvc.perform(MockMvcRequestBuilders.post("/user/transfer").queryParam("amount", "50")
                .queryParam("email", connectionUserEmail)
                .queryParam("description", "shoes"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/profile"));
    }

    @Test
    public void transferPost_anonymousUserAttemptsRequest_requestUnsuccessful() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/user/transfer").queryParam("amount", "50")
                .queryParam("email", "abc@abc.com")
                .queryParam("description", "shoes"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    @WithUserDetails("regular@paymybuddy.com")
    public void profile_regularUserLoggedIn_requestSuccessful() throws Exception {
        String regularUserEmail = "regular@paymybuddy.com";

        Role regularRole = new Role();
        regularRole.setRole("Regular");

        float balance = 100f;

        Account regularUserAccount = new Account();
        regularUserAccount.setCurrentBalance(balance);

        User regularUser = new User(regularUserEmail, "password", new Role("Regular"));
        regularUser.setActive(true);
        regularUser.setRole(regularRole);
        regularUser.setAccount(regularUserAccount);

        List<Iban> ibanList = new ArrayList<>();

        List<ConnectionListElement> connectionListElementList = new ArrayList<>();

        Mockito.when(userRepository.findUserByEmail(regularUserEmail)).thenReturn(regularUser);

        Mockito.when(ibanRepository.findByAccount_UserEmail(regularUserEmail)).thenReturn(ibanList);

        Mockito.when(connectionListElementRepository.findConnectionListElementsByAccountUserEmail(regularUserEmail))
                .thenReturn(connectionListElementList);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/profile")).andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithUserDetails("admin@paymybuddy.com")
    public void profile_adminUserLoggedIn_requestSuccessful() throws Exception {
        String adminUserEmail = "admin@paymybuddy.com";

        Role adminRole = new Role();
        adminRole.setRole("Admin");

        float balance = 100f;

        Account adminUserAccount = new Account();
        adminUserAccount.setCurrentBalance(balance);

        User adminUser = new User(adminUserEmail, "password", new Role("Admin"));
        adminUser.setActive(true);
        adminUser.setRole(adminRole);
        adminUser.setAccount(adminUserAccount);

        List<Iban> ibanList = new ArrayList<>();

        List<ConnectionListElement> connectionListElementList = new ArrayList<>();

        Mockito.when(userRepository.findUserByEmail(adminUserEmail)).thenReturn(adminUser);

        Mockito.when(ibanRepository.findByAccount_UserEmail(adminUserEmail)).thenReturn(ibanList);

        Mockito.when(connectionListElementRepository.findConnectionListElementsByAccountUserEmail(adminUserEmail))
                .thenReturn(connectionListElementList);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/profile")).andExpect(status().is2xxSuccessful());
    }

    @Test
    public void profile_anonymousUserAttemptsRequest_requestUnsuccessful() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/user/profile")).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    @WithUserDetails("regular@paymybuddy.com")
    public void addConnectionGet_regularUserLoggedIn_requestSuccessful() throws Exception {
        String userEmail = "abc@abc.com";

        User user = new User(userEmail, "password", new Role("Regular"));

        mockMvc.perform(MockMvcRequestBuilders.get("/user/addConnection")
                .requestAttr("user", user)).andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithUserDetails("admin@paymybuddy.com")
    public void addConnectionGet_adminUserLoggedIn_requestSuccessful() throws Exception {
        String userEmail = "abc@abc.com";

        User user = new User(userEmail, "password", new Role("Regular"));

        mockMvc.perform(MockMvcRequestBuilders.get("/user/addConnection")
                .requestAttr("user", user)).andExpect(status().is2xxSuccessful());
    }

    @Test
    public void addConnectionGet_anonymousUserAttemptsRequest_requestUnsuccessful() throws Exception {
        String userEmail = "abc@abc.com";

        User user = new User(userEmail, "password", new Role("Regular"));

        mockMvc.perform(MockMvcRequestBuilders.get("/user/addConnection")
                .requestAttr("user", user)).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    @WithUserDetails("regular@paymybuddy.com")
    public void addConnectionPost_regularUserLoggedIn_requestSuccessful() throws Exception {
        String regularUserEmail = "regular@paymybuddy.com";

        Account regularUserAccount = new Account();
        regularUserAccount.setAccountStatus(new AccountStatus("Active"));

        Role regularRole = new Role();
        regularRole.setRole("Regular");

        User regularUser = new User(regularUserEmail, "password", new Role("Regular"));
        regularUser.setActive(true);
        regularUser.setRole(regularRole);

        String connectionEmail = "abc@abc.com";

        User connectionUser = new User(connectionEmail, "password", new Role("Regular"));

        Connection connection = new Connection();

        List<ConnectionListElement> connectionListElementList = new ArrayList<>();

        Mockito.when(connectionListElementRepository.findConnectionListElementsByAccountUserEmail(regularUserEmail))
                .thenReturn(connectionListElementList);

        Mockito.when(accountRepository.findAccountByUserEmail(regularUserEmail)).thenReturn(regularUserAccount);

        Mockito.when(connectionRepository.findConnectionByUserEmail(connectionEmail)).thenReturn(connection);

        Mockito.when(userRepository.findUserByEmail(regularUserEmail)).thenReturn(regularUser);

        Mockito.when(connectionListElementRepository.save(any(ConnectionListElement.class)))
                .thenReturn(new ConnectionListElement());

        mockMvc.perform(MockMvcRequestBuilders.post("/user/addConnection")
                .requestAttr("user", connectionUser)).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/profile"));
    }

    @Test
    @WithUserDetails("admin@paymybuddy.com")
    public void addConnectionPost_adminUserLoggedIn_requestSuccessful() throws Exception {
        String adminUserEmail = "admin@paymybuddy.com";

        Account adminUserAccount = new Account();
        adminUserAccount.setAccountStatus(new AccountStatus("Active"));

        Role adminRole = new Role();
        adminRole.setRole("Regular");

        User adminUser = new User(adminUserEmail, "password", new Role("Admin"));
        adminUser.setActive(true);
        adminUser.setRole(adminRole);

        String connectionEmail = "abc@abc.com";

        User connectionUser = new User(connectionEmail, "password", new Role("Admin"));

        Connection connection = new Connection();

        List<ConnectionListElement> connectionListElementList = new ArrayList<>();

        Mockito.when(connectionListElementRepository.findConnectionListElementsByAccountUserEmail(adminUserEmail))
                .thenReturn(connectionListElementList);

        Mockito.when(accountRepository.findAccountByUserEmail(adminUserEmail)).thenReturn(adminUserAccount);

        Mockito.when(connectionRepository.findConnectionByUserEmail(connectionEmail)).thenReturn(connection);

        Mockito.when(userRepository.findUserByEmail(adminUserEmail)).thenReturn(adminUser);

        Mockito.when(connectionListElementRepository.save(any(ConnectionListElement.class)))
                .thenReturn(new ConnectionListElement());

        mockMvc.perform(MockMvcRequestBuilders.post("/user/addConnection")
                .requestAttr("user", connectionUser)).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/profile"));
    }

    @Test
    public void addConnectionPost_anonymousUserAttemptsRequest_requestUnsuccessful() throws Exception {

        String connectionEmail = "abc@abc.com";

        User connectionUser = new User(connectionEmail, "password", new Role("Admin"));

        mockMvc.perform(MockMvcRequestBuilders.post("/user/addConnection")
                .requestAttr("user", connectionUser)).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    @WithUserDetails("regular@paymybuddy.com")
    public void addMoneyGet_regularUserLoggedIn_requestSuccessful() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setMoneyAmount(100f);
        transaction.setOrigin("Bank Account");

        mockMvc.perform(MockMvcRequestBuilders.get("/user/addMoney")
                .requestAttr("transaction", transaction)).andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithUserDetails("admin@paymybuddy.com")
    public void addMoneyGet_adminUserLoggedIn_requestSuccessful() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setMoneyAmount(100f);
        transaction.setOrigin("Bank Account");

        mockMvc.perform(MockMvcRequestBuilders.get("/user/addMoney")
                .requestAttr("transaction", transaction)).andExpect(status().is2xxSuccessful());
    }

    @Test
    public void addMoneyGet_anonymousUserAttemptsRequest_requestUnsuccessful() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setMoneyAmount(100f);
        transaction.setOrigin("Bank Account");

        mockMvc.perform(MockMvcRequestBuilders.get("/user/addMoney")
                .requestAttr("transaction", transaction)).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    @WithUserDetails("regular@paymybuddy.com")
    public void addMoneyPost_regularUserLoggedIn_requestSuccessful() throws Exception {
        String regularUserEmail = "regular@paymybuddy.com";

        Account regularUserAccount = new Account();
        regularUserAccount.setAccountStatus(new AccountStatus("Active"));

        Role regularRole = new Role();
        regularRole.setRole("Regular");

        User regularUser = new User(regularUserEmail, "password", new Role("Regular"));
        regularUser.setActive(true);
        regularUser.setRole(regularRole);

        List<Transaction> transactionList = new ArrayList<>();

        Transaction transaction = new Transaction();
        transaction.setMoneyAmount(100f);
        transaction.setOrigin("Bank Account");

        Mockito.when(transactionRepository.findTransactionsByAccountUserEmail(regularUserEmail))
                .thenReturn(transactionList);

        Mockito.when(transactionRepository.findTransactionsByConnectionUserEmail(regularUserEmail))
                .thenReturn(transactionList);

        Mockito.when(transactionRepository.findAll()).thenReturn(transactionList);

        Mockito.when(userRepository.findUserByEmail(regularUserEmail)).thenReturn(regularUser);

        Mockito.when(accountRepository.findAccountByUserEmail(regularUserEmail)).thenReturn(regularUserAccount);

        Mockito.when(ibanRepository.save(any(Iban.class))).thenReturn(new Iban());

        Mockito.when(transactionRepository.save(any(Transaction.class))).thenReturn(new Transaction());

        Mockito.when(accountRepository.save(any(Account.class))).thenReturn(new Account());

        mockMvc.perform(MockMvcRequestBuilders.post("/user/addMoney")
                .requestAttr("transaction", transaction)).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/profile"));
    }

    @Test
    @WithUserDetails("admin@paymybuddy.com")
    public void addMoneyPost_adminUserLoggedIn_requestSuccessful() throws Exception {
        String adminUserEmail = "admin@paymybuddy.com";

        Account adminUserAccount = new Account();
        adminUserAccount.setAccountStatus(new AccountStatus("Active"));

        Role adminRole = new Role();
        adminRole.setRole("Admin");

        User adminUser = new User(adminUserEmail, "password", new Role("Admin"));
        adminUser.setActive(true);
        adminUser.setRole(adminRole);

        List<Transaction> transactionList = new ArrayList<>();

        Transaction transaction = new Transaction();
        transaction.setMoneyAmount(100f);
        transaction.setOrigin("Bank Account");

        Mockito.when(transactionRepository.findTransactionsByAccountUserEmail(adminUserEmail))
                .thenReturn(transactionList);

        Mockito.when(transactionRepository.findTransactionsByConnectionUserEmail(adminUserEmail))
                .thenReturn(transactionList);

        Mockito.when(transactionRepository.findAll()).thenReturn(transactionList);

        Mockito.when(userRepository.findUserByEmail(adminUserEmail)).thenReturn(adminUser);

        Mockito.when(accountRepository.findAccountByUserEmail(adminUserEmail)).thenReturn(adminUserAccount);

        Mockito.when(ibanRepository.save(any(Iban.class))).thenReturn(new Iban());

        Mockito.when(transactionRepository.save(any(Transaction.class))).thenReturn(new Transaction());

        Mockito.when(accountRepository.save(any(Account.class))).thenReturn(new Account());

        mockMvc.perform(MockMvcRequestBuilders.post("/user/addMoney")
                .requestAttr("transaction", transaction)).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/profile"));
    }

    @Test
    public void addMoneyPost_anonymousUserAttemptsRequest_requestUnsuccessful() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setMoneyAmount(100f);
        transaction.setOrigin("Bank Account");

        mockMvc.perform(MockMvcRequestBuilders.post("/user/addMoney")
                .requestAttr("transaction", transaction)).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    @WithUserDetails("regular@paymybuddy.com")
    public void addIbanGet_regularUserLoggedIn_requestSuccessful() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/user/addIban")).andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithUserDetails("admin@paymybuddy.com")
    public void addIbanGet_adminUserLoggedIn_requestSuccessful() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/user/addIban")).andExpect(status().is2xxSuccessful());
    }

    @Test
    public void addIbanGet_anonymousUserAttemptsRequest_requestUnsuccessful() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/user/addIban")).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    @WithUserDetails("regular@paymybuddy.com")
    public void addIbanPost_regularUserLoggedIn_requestSuccessful() throws Exception {
        String regularUserEmail = "regular@paymybuddy.com";

        List<Iban> ibanList = new ArrayList<>();

        Account regularUserAccount = new Account();
        regularUserAccount.setAccountStatus(new AccountStatus("Active"));
        regularUserAccount.setIbans(ibanList);

        Role regularRole = new Role();
        regularRole.setRole("Regular");

        User regularUser = new User(regularUserEmail, "password", new Role("Regular"));
        regularUser.setActive(true);
        regularUser.setRole(regularRole);
        regularUser.setAccount(regularUserAccount);

        Mockito.when(userRepository.findUserByEmail(regularUserEmail)).thenReturn(regularUser);

        Mockito.when(ibanRepository.findByAccount_UserEmail(regularUserEmail)).thenReturn(ibanList);

        Mockito.when(accountRepository.findAccountByUserEmail(regularUserEmail)).thenReturn(regularUserAccount);

        Mockito.when(ibanRepository.save(any(Iban.class))).thenReturn(new Iban());

        mockMvc.perform(MockMvcRequestBuilders.post("/user/addIban")
                .queryParam("ibanString", "GB123456")).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/addIban"));
    }

    @Test
    @WithUserDetails("admin@paymybuddy.com")
    public void addIbanPost_adminUserLoggedIn_requestSuccessful() throws Exception {
        String adminUserEmail = "admin@paymybuddy.com";

        List<Iban> ibanList = new ArrayList<>();

        Account adminUserAccount = new Account();
        adminUserAccount.setAccountStatus(new AccountStatus("Active"));
        adminUserAccount.setIbans(ibanList);

        Role adminRole = new Role();
        adminRole.setRole("Admin");

        User adminUser = new User(adminUserEmail, "password", new Role("Admin"));
        adminUser.setActive(true);
        adminUser.setRole(adminRole);
        adminUser.setAccount(adminUserAccount);

        Mockito.when(userRepository.findUserByEmail(adminUserEmail)).thenReturn(adminUser);

        Mockito.when(ibanRepository.findByAccount_UserEmail(adminUserEmail)).thenReturn(ibanList);

        Mockito.when(accountRepository.findAccountByUserEmail(adminUserEmail)).thenReturn(adminUserAccount);

        Mockito.when(ibanRepository.save(any(Iban.class))).thenReturn(new Iban());

        mockMvc.perform(MockMvcRequestBuilders.post("/user/addIban")
                .queryParam("ibanString", "GB123456")).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/addIban"));
    }

    @Test
    public void addIbanPost_anonymousUserAttemptsRequest_requestUnsuccessful() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/user/addIban")
                .queryParam("ibanString", "GB123456")).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    @WithUserDetails("regular@paymybuddy.com")
    public void withdrawalGet_regularUserLoggedIn_requestSuccessful() throws Exception {
        String regularUserEmail = "regular@paymybuddy.com";

        List<Iban> ibanList = new ArrayList<>();

        Account regularUserAccount = new Account();
        regularUserAccount.setAccountStatus(new AccountStatus("Active"));
        regularUserAccount.setIbans(ibanList);

        Role regularRole = new Role();
        regularRole.setRole("Regular");

        User regularUser = new User(regularUserEmail, "password", new Role("Regular"));
        regularUser.setActive(true);
        regularUser.setRole(regularRole);
        regularUser.setAccount(regularUserAccount);

        Mockito.when(userRepository.findUserByEmail(regularUserEmail)).thenReturn(regularUser);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/withdrawal")).andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithUserDetails("admin@paymybuddy.com")
    public void withdrawalGet_adminUserLoggedIn_requestSuccessful() throws Exception {
        String adminUserEmail = "admin@paymybuddy.com";

        List<Iban> ibanList = new ArrayList<>();

        Account adminUserAccount = new Account();
        adminUserAccount.setAccountStatus(new AccountStatus("Active"));
        adminUserAccount.setIbans(ibanList);

        Role adminRole = new Role();
        adminRole.setRole("Admin");

        User adminUser = new User(adminUserEmail, "password", new Role("Admin"));
        adminUser.setActive(true);
        adminUser.setRole(adminRole);
        adminUser.setAccount(adminUserAccount);

        Mockito.when(userRepository.findUserByEmail(adminUserEmail)).thenReturn(adminUser);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/withdrawal")).andExpect(status().is2xxSuccessful());
    }

    @Test
    public void withdrawalGet_anonymousUserAttemptsRequest_requestAttemptUnsuccessful() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/user/withdrawal")).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    @WithUserDetails("regular@paymybuddy.com")
    public void withdrawalPost_regularUserAttemptsWithdrawal_withdrawalAttemptGoesThrough() throws Exception {
        String regularUserEmail = "regular@paymybuddy.com";

        Account regularUserAccount = new Account();
        regularUserAccount.setAccountStatus(new AccountStatus("Active"));

        Role regularRole = new Role();
        regularRole.setRole("Regular");

        User regularUser = new User(regularUserEmail, "password", new Role("Regular"));
        regularUser.setActive(true);
        regularUser.setRole(regularRole);

        List<Transaction> transactionList = new ArrayList<>();

        Mockito.when(transactionRepository.findTransactionsByAccountUserEmail(regularUserEmail))
                .thenReturn(transactionList);

        Mockito.when(transactionRepository.findTransactionsByConnectionUserEmail(regularUserEmail))
                .thenReturn(transactionList);

        Mockito.when(transactionRepository.findAll()).thenReturn(transactionList);

        Mockito.when(userRepository.findUserByEmail(regularUserEmail)).thenReturn(regularUser);

        Mockito.when(accountRepository.findAccountByUserEmail(regularUserEmail)).thenReturn(regularUserAccount);

        Mockito.when(ibanRepository.save(any(Iban.class))).thenReturn(new Iban());

        Mockito.when(transactionRepository.save(any(Transaction.class))).thenReturn(new Transaction());

        Mockito.when(accountRepository.save(any(Account.class))).thenReturn(new Account());

        mockMvc.perform(MockMvcRequestBuilders.post("/user/withdrawal").queryParam("moneyAmount", "0")
                .queryParam("account", "GB123456")).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/profile?moneyAmount=0&account=GB123456"));
    }

    @Test
    @WithUserDetails("admin@paymybuddy.com")
    public void withdrawalPost_adminUserAttemptsWithdrawal_withdrawalAttemptGoesThrough() throws Exception {
        String adminUserEmail = "admin@paymybuddy.com";

        Account adminUserAccount = new Account();
        adminUserAccount.setAccountStatus(new AccountStatus("Active"));

        Role adminRole = new Role();
        adminRole.setRole("Admin");

        User adminUser = new User(adminUserEmail, "password", new Role("Admin"));
        adminUser.setActive(true);
        adminUser.setRole(adminRole);

        List<Transaction> transactionList = new ArrayList<>();

        Mockito.when(transactionRepository.findTransactionsByAccountUserEmail(adminUserEmail))
                .thenReturn(transactionList);

        Mockito.when(transactionRepository.findTransactionsByConnectionUserEmail(adminUserEmail))
                .thenReturn(transactionList);

        Mockito.when(transactionRepository.findAll()).thenReturn(transactionList);

        Mockito.when(userRepository.findUserByEmail(adminUserEmail)).thenReturn(adminUser);

        Mockito.when(accountRepository.findAccountByUserEmail(adminUserEmail)).thenReturn(adminUserAccount);

        Mockito.when(ibanRepository.save(any(Iban.class))).thenReturn(new Iban());

        Mockito.when(transactionRepository.save(any(Transaction.class))).thenReturn(new Transaction());

        Mockito.when(accountRepository.save(any(Account.class))).thenReturn(new Account());

        mockMvc.perform(MockMvcRequestBuilders.post("/user/withdrawal").queryParam("moneyAmount", "0")
                .queryParam("account", "GB123456")).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/profile?moneyAmount=0&account=GB123456"));
    }

    @Test
    public void withdrawalPost_anonymousUserAttemptsWithdrawal_withdrawalAttemptUnsuccessful() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/user/withdrawal").queryParam("moneyAmount", "0")
                .queryParam("account", "GB123456")).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?moneyAmount=0&account=GB123456"));
    }

    @Test
    @WithUserDetails("regular@paymybuddy.com")
    public void contact_regularUserLoggedIn_requestSuccessful() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/user/contact"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithUserDetails("admin@paymybuddy.com")
    public void contact_adminUserLoggedIn_requestSuccessful() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/user/contact"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void contact_anonymousUserAccessAttempt_requestSuccessful() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/user/contact"))
                .andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/login"));
    }
}

