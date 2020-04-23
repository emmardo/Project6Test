package com.openclassrooms.Project6Test.ControllersTest;

import com.openclassrooms.Project6Test.Models.Role;
import com.openclassrooms.Project6Test.Models.User;
import com.openclassrooms.Project6Test.Repositories.*;
import com.openclassrooms.Project6Test.Services.ConnectionListElementService;
import com.openclassrooms.Project6Test.Services.IbanService;
import com.openclassrooms.Project6Test.Services.TransactionService;
import com.openclassrooms.Project6Test.Services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import java.lang.annotation.*;
import java.security.Principal;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.matchers.JUnitMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserControllerTest {

    @Target({ElementType.METHOD, ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    @Documented
    @WithSecurityContext(
            factory = WithMockUserSecurityContextFactory.class
    )
    public @interface WithMockUser {
        String value() default "user";

        String username() default "";

        String[] roles() default {"USER"};

        String password() default "password";
    }

    /*@Mock
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

        ServletContext a = webApplicationContext.getServletContext();
        ServletContext a = new ServletContext() {
        }
    }

    @Test
    public void contactGetRequestSuccessful() throws Exception {

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

        mockMvc.perform(get("/user/contact")).andExpect(status().is2xxSuccessful());
    }

    @Test
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
    }

    @Test
    public void contactGetRequestUnsuccessful() throws Exception {

        mockMvc.perform(get("/user/contact"))
                .andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/login"));
    }*/
}
