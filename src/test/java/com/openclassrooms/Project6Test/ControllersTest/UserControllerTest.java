package com.openclassrooms.Project6Test.ControllersTest;

import com.openclassrooms.Project6Test.Models.*;
import com.openclassrooms.Project6Test.Repositories.*;
import com.openclassrooms.Project6Test.Services.TransactionService;
import com.openclassrooms.Project6Test.Services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = SpringSecurityWebAuxTestConfig.class
)
@AutoConfigureMockMvc
public class UserControllerTest {

    /*@Autowired
    private WebApplicationContext webApplicationContext;*/

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private AccountTypeRepository accountTypeRepository;

    @MockBean
    private AccountStatusRepository accountStatusRepository;

    @MockBean
    private ConnectionRepository connectionRepository;

    @Mock
    private ConnectionTypeRepository connectionTypeRepository;

    @MockBean
    private TransactionRepository transactionRepository;

    @MockBean
    private TransactionTypeRepository transactionTypeRepository;

    @MockBean
    private IbanRepository ibanRepository;

    @InjectMocks
    private UserService userService = new UserService(userRepository, roleRepository, accountRepository, accountTypeRepository,
                                            accountStatusRepository, connectionRepository, connectionTypeRepository, transactionTypeRepository);

    @InjectMocks
    private TransactionService transactionService = new TransactionService(transactionRepository, transactionTypeRepository,
                                                            accountRepository, connectionRepository, ibanRepository);

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @WithUserDetails("admin@paymybuddy.com")
    public void givenManagerUser_whenGetFooSalute_thenOk() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/contact")
                .accept(MediaType.ALL))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("regular@paymybuddy.com")
    public void withdrawalPost_regularUserAttemptsWithdrawal_withdrawalAttemptGoesThrough() throws Exception
    {
        String regularUserEmail = "regular@paymybuddy.com";

        Account account = new Account();
        account.setAccountStatus(new AccountStatus("Active"));

        Role regularRole = new Role();
        regularRole.setRole("Regular");

        User regularUser = new User(regularUserEmail, "password", new Role("Regular"));
        regularUser.setActive(true);
        regularUser.setRole(regularRole);

        List<Transaction> transactionList = new ArrayList<>();

        Authentication authentication = mock(Authentication.class);

        /*when(accountRepository.findAccountByUserEmail(anyString())).thenReturn(account);

        when(userService.getUserFromAuthentication(any(Authentication.class)).getEmail()).thenReturn("regular@paymybuddy.com");

        when(userService.getUserFromAuthentication(any(Authentication.class))).thenReturn(regular);*/

        Mockito.when(transactionRepository.findTransactionsByAccountUserEmail(regularUserEmail))
                .thenReturn(transactionList);

        Mockito.when(transactionRepository.findTransactionsByConnectionUserEmail(regularUserEmail))
                .thenReturn(transactionList);

        Mockito.when(transactionRepository.findAll()).thenReturn(transactionList);

        Mockito.when(userRepository.findUserByEmail(regularUserEmail)).thenReturn(regularUser);

        Mockito.when(accountRepository.findAccountByUserEmail(regularUserEmail)).thenReturn(account);

        Mockito.when(ibanRepository.save(any(Iban.class))).thenReturn(new Iban());

        Mockito.when(transactionRepository.save(any(Transaction.class))).thenReturn(new Transaction());

        /*Mockito.when(authentication.getName()).thenReturn(regularUserEmail);

        Mockito.when(userService.getUserByEmail(anyString())).thenReturn(regularUser);*/

        mockMvc.perform(MockMvcRequestBuilders.post("/user/withdrawal").queryParam("moneyAmount", "0")
                .queryParam("account", "GB123456")).andExpect(status().isOk());



        when(userService.getUserFromAuthentication(any()).getEmail()).thenReturn(regularUserEmail);
    }

    @Test
    @WithUserDetails("admin@paymybuddy.com")
    public void contact_adminUserLoggedIn_getRequestSuccessful() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/user/contact"))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("regular@paymybuddy.com")
    public void contact_regularUserLoggedIn_getRequestSuccessful() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/user/contact"))
                .andExpect(status().isOk());
    }

    @Test
    public void contact_anonymousUserAccessAttempt_getRequestSuccessful() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/user/contact"))
                .andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/login"));
    }

    /*@Test
    public void profileGetRequestSuccessful() throws Exception {

        String email = "abc@abc.com";
        String password = "abc";
        String role = "Regular";

        EmbeddedLdapProperties.Credential credential = new EmbeddedLdapProperties.Credential();
        credential.setUsername(email);
        credential.setPassword(password);

        Principal principal = new Principal() {
            @Override
            public String getName() {
                return email;
            }
        };

        UserDetails userDetails = new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return Arrays.asList(new SimpleGrantedAuthority(role));
            }

            @Override
            public String getPassword() {
                return password;
            }

            @Override
            public String getUsername() {
                return email;
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };

        Authentication authenticationMock = new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return userDetails.getAuthorities();
            }

            @Override
            public Object getCredentials() {
                return credential;
            }

            @Override
            public Object getDetails() {
                return userDetails;
            }

            @Override
            public Object getPrincipal() {
                return principal;
            }

            @Override
            public boolean isAuthenticated() {
                return true;
            }

            @Override
            public void setAuthenticated(boolean b) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return userDetails.getUsername();
            }
        };

        SecurityContext securityContext = new SecurityContext() {
            @Override
            public Authentication getAuthentication() {
                return authenticationMock;
            }

            @Override
            public void setAuthentication(Authentication authentication) {

            }
        };

        when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(authenticationMock);

        mockMvc.perform(get("/user/profile")).andExpect(status().is2xxSuccessful());
    }*/
}

/*@SpringBootTest(
        classes = SpringSecurityWebAuxTestConfig.class
)
@RunWith(SpringRunner.class)
public class UserControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountTypeRepository accountTypeRepository;

    @Mock
    private AccountStatusRepository accountStatusRepository;

    @Mock
    private ConnectionRepository connectionRepository;

    @Mock
    private ConnectionTypeRepository connectionTypeRepository;

    @Mock
    private TransactionTypeRepository transactionTypeRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private IbanRepository ibanRepository;

    @Mock
    private ConnectionListElementRepository connectionListElementRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @InjectMocks
    private UserService userServiceMock;

    @InjectMocks
    private ConnectionListElementService connectionListElementServiceMock;

    @InjectMocks
    private TransactionService transactionServiceMock;

    @InjectMocks
    private IbanService ibanServiceMock;

    @Before
    public void MockMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


}*/

