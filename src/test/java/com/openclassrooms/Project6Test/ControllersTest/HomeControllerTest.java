package com.openclassrooms.Project6Test.ControllersTest;

import com.openclassrooms.Project6Test.Models.*;
import com.openclassrooms.Project6Test.Repositories.*;
import com.openclassrooms.Project6Test.Services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
public class HomeControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

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
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserService userServiceMock;

    @Before
    public void MockMvc() {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void home() throws Exception {

        mockMvc.perform(get("/")).andExpect(status().isOk());
    }

    @Test
    public void registerGetRequest() throws Exception {

        mockMvc.perform(get("/register")).andExpect(status().isOk());
    }

    /*@WithMockUser(value = "abc@abc.com", password = "123456")*/
    @Test
    public void registerPostRequest() throws Exception {

        //Arrange
        String userEmail = "abc@abc.com";
        String password = "123456";
        String regularString = "Regular";
        String activeString = "Active";

        Role role = new Role();
        role.setRole(regularString);
        ConnectionType connectionType = new ConnectionType();
        connectionType.setConnectionType(regularString);
        AccountType accountType = new AccountType();
        accountType.setAccountType(regularString);
        AccountStatus accountStatus = new AccountStatus();
        accountStatus.setAccountStatus(activeString);

        when(userRepository.findUserByEmail(userEmail)).thenReturn(null);

        when(roleRepository.findRoleByRole(regularString)).thenReturn(role);

        when(connectionTypeRepository.findConnectionTypeByConnectionType(regularString)).thenReturn(connectionType);

        when(accountTypeRepository.findAccountTypeByAccountType(regularString)).thenReturn(accountType);

        when(accountStatusRepository.findAccountStatusByAccountStatus(activeString)).thenReturn(accountStatus);

        mockMvc.perform(formLogin("/register").user(userEmail).password(password))
                .andExpect(status().isOk()).andExpect(redirectedUrl("/login"));

    }
}
