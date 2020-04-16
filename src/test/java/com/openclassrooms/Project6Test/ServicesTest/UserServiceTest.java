package com.openclassrooms.Project6Test.ServicesTest;

import com.openclassrooms.Project6Test.Models.*;
import com.openclassrooms.Project6Test.Repositories.*;
import com.openclassrooms.Project6Test.Services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

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

    @InjectMocks
    private UserService userService;

    @Test
    public void createUserByRole_createRegularUserWhoIsInexistent_userCreated() {

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

        //Act
        userService.createUserByRole(userEmail, password, regularString);

        //Assert
        verify(userRepository, times(1)).save(any(User.class));
        verify(connectionRepository, times(1)).save(any(Connection.class));
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    public void createUserByRole_createRegularUserWhoExists_userNotCreated() {

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
        User user = new User();
        user.setEmail(userEmail);

        when(userRepository.findUserByEmail(userEmail)).thenReturn(user);

        //Act
        userService.createUserByRole(userEmail, password, regularString);

        //Assert
        verify(userRepository, times(0)).save(any(User.class));
        verify(connectionRepository, times(0)).save(any(Connection.class));
        verify(accountRepository, times(0)).save(any(Account.class));
    }

    @Test
    public void createUserByRole_createCompanyUserWhoIsInexistent_userCreated() {

        //Arrange
        String userEmail = "abc@abc.com";
        String password = "123456";
        String companyString = "Company";
        String activeString = "Active";

        Role role = new Role();
        role.setRole(companyString);
        ConnectionType connectionType = new ConnectionType();
        connectionType.setConnectionType(companyString);
        AccountType accountType = new AccountType();
        accountType.setAccountType(companyString);
        AccountStatus accountStatus = new AccountStatus();
        accountStatus.setAccountStatus(activeString);

        when(userRepository.findUserByEmail(userEmail)).thenReturn(null);

        when(roleRepository.findRoleByRole(companyString)).thenReturn(role);

        when(connectionTypeRepository.findConnectionTypeByConnectionType(companyString)).thenReturn(connectionType);

        when(accountTypeRepository.findAccountTypeByAccountType(companyString)).thenReturn(accountType);

        when(accountStatusRepository.findAccountStatusByAccountStatus(activeString)).thenReturn(accountStatus);

        //Act
        userService.createUserByRole(userEmail, password, companyString);

        //Assert
        verify(userRepository, times(1)).save(any(User.class));
        verify(connectionRepository, times(1)).save(any(Connection.class));
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    public void createUserByRole_createCompanyUserWhoExists_userNotCreated() {

        //Arrange
        String userEmail = "abc@abc.com";
        String password = "123456";
        String companyString = "Company";
        String activeString = "Active";

        Role role = new Role();
        role.setRole(companyString);
        ConnectionType connectionType = new ConnectionType();
        connectionType.setConnectionType(companyString);
        AccountType accountType = new AccountType();
        accountType.setAccountType(companyString);
        AccountStatus accountStatus = new AccountStatus();
        accountStatus.setAccountStatus(activeString);
        User user = new User();
        user.setEmail(userEmail);

        when(userRepository.findUserByEmail(userEmail)).thenReturn(user);

        //Act
        userService.createUserByRole(userEmail, password, companyString);

        //Assert
        verify(userRepository, times(0)).save(any(User.class));
        verify(connectionRepository, times(0)).save(any(Connection.class));
        verify(accountRepository, times(0)).save(any(Account.class));
    }

    @Test
    public void createUserByRole_createAdminUserWhoIsInexistent_userCreated() {

        //Arrange
        String userEmail = "abc@abc.com";
        String password = "123456";
        String adminString = "Company";
        String activeString = "Active";

        Role role = new Role();
        role.setRole(adminString);

        when(userRepository.findUserByEmail(userEmail)).thenReturn(null);

        when(roleRepository.findRoleByRole(adminString)).thenReturn(role);

        //Act
        userService.createUserByRole(userEmail, password, adminString);

        //Assert
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void createUserByRole_createAdminUserWhoExists_userNotCreated() {

        //Arrange
        String userEmail = "abc@abc.com";
        String password = "123456";
        String adminString = "Company";
        String activeString = "Active";

        User user = new User();
        user.setEmail(userEmail);
        Role role = new Role();
        role.setRole(adminString);

        when(userRepository.findUserByEmail(userEmail)).thenReturn(user);

        //Act
        userService.createUserByRole(userEmail, password, adminString);

        //Assert
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    public void createUserByRole_roleNotValid_userNotCreated() {

        //Arrange
        String userEmail = "abc@abc.com";
        String password = "123456";
        String hackerString = "Hacker";
        String activeString = "Active";

        Role role = new Role();
        role.setRole(hackerString);
        ConnectionType connectionType = new ConnectionType();
        connectionType.setConnectionType(hackerString);
        AccountType accountType = new AccountType();
        accountType.setAccountType(hackerString);
        AccountStatus accountStatus = new AccountStatus();
        accountStatus.setAccountStatus(activeString);

        when(userRepository.findUserByEmail(userEmail)).thenReturn(null);

        //Act
        userService.createUserByRole(userEmail, password, hackerString);

        //Assert
        verify(userRepository, times(0)).save(any(User.class));
        verify(connectionRepository, times(0)).save(any(Connection.class));
        verify(accountRepository, times(0)).save(any(Account.class));
    }

    @Test
    public void createUserByRole_passwordEmpty_userNotCreated() {

        //Arrange
        String userEmail = "abc@abc.com";
        String password = "";
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

        //Act
        userService.createUserByRole(userEmail, password, regularString);

        //Assert
        verify(userRepository, times(0)).save(any(User.class));
        verify(connectionRepository, times(0)).save(any(Connection.class));
        verify(accountRepository, times(0)).save(any(Account.class));
    }

    @Test
    public void getUserByEmail_userExists_userReturned() {

        //Arrange
        String userEmail = "abc@abc.com";

        User user = new User();
        user.setEmail(userEmail);

        when(userRepository.findUserByEmail(userEmail)).thenReturn(user);

        //Act
        User newUser = userService.getUserByEmail(userEmail);

        //Assert
        assertNotNull(newUser);
    }

    @Test
    public void getUserByEmail_userInexistent_nullUserReturned() {

        //Arrange
        String userEmail = "abc@abc.com";

        when(userRepository.findUserByEmail(userEmail)).thenReturn(null);

        //Act
        User newUser = userService.getUserByEmail(userEmail);

        //Assert
        assertNull(newUser);
    }

    @Test
    public void getUserFromAuthentication_userLoggedIn_userReturned() {

        //Arrange
        String userEmail = "abc@abc.com";
        User user = new User();
        user.setEmail(userEmail);

        Authentication authentication = mock(Authentication.class);

        when(authentication.getName()).thenReturn(userEmail);

        when(userRepository.findUserByEmail(userEmail)).thenReturn(user);

        //Act
        User newUser = userService.getUserFromAuthentication(authentication);

        //Assert
        assertNotNull(newUser);
    }

    @Test
    public void getUserFromAuthentication_userNotLoggedIn_nullUseReturned() {

        //Arrange
        User user = null;

        Authentication authentication = mock(Authentication.class);

        when(authentication.getName()).thenReturn(null);

        //Act
        User newUser = userService.getUserFromAuthentication(authentication);

        //Assert
        assertNull(newUser);
    }

    @Test
    public void userAccountExistenceValidatorByEmail_userExists_trueReturned() {

        //Arrange
        String userEmail = "abc@abc.com";
        User user = new User();
        user.setEmail(userEmail);

        when(userRepository.findUserByEmail(userEmail)).thenReturn(user);

        //Act
        boolean value = userService.userAccountExistenceValidatorByEmail(userEmail);

        //Assert
        assertTrue(value);
    }

    @Test
    public void userAccountExistenceValidatorByEmail_userInexistent_falseReturned() {

        //Arrange
        String userEmail = "abc@abc.com";

        when(userRepository.findUserByEmail(userEmail)).thenReturn(null);

        //Act
        boolean value = userService.userAccountExistenceValidatorByEmail(userEmail);

        //Assert
        assertFalse(value);
    }

    @Test
    public void userAccountExistenceValidatorByEmail_emptyStringInput_falseReturned() {

        //Arrange
        String emptyString = "";

        //Act
        boolean value = userService.userAccountExistenceValidatorByEmail(emptyString);

        //Assert
        assertFalse(value);
    }

    @Test
    public void userRoleValidator_regularRoleInput_trueReturned() {

        //Arrange
        String roleString = "Regular";

        //Act
        boolean value = userService.userRoleValidator(roleString);

        //Assert
        assertTrue(value);
    }

    @Test
    public void userRoleValidator_companyRoleInput_trueReturned() {

        //Arrange
        String roleString = "Company";

        //Act
        boolean value = userService.userRoleValidator(roleString);

        //Assert
        assertTrue(value);
    }

    @Test
    public void userRoleValidator_AdminRoleInput_trueReturned() {

        //Arrange
        String roleString = "Admin";

        //Act
        boolean value = userService.userRoleValidator(roleString);

        //Assert
        assertTrue(value);
    }

    @Test
    public void userRoleValidator_hackerRoleInput_falseReturned() {

        //Arrange
        String roleString = "Hacker";

        //Act
        boolean value = userService.userRoleValidator(roleString);

        //Assert
        assertFalse(value);
    }

    @Test
    public void entityTypesExistenceChecker_entitiesInexistent_entitiesCreated() {

        //Arrange
        List<Role> roleList = new ArrayList<>();
        List<AccountType> accountTypeList = new ArrayList<>();
        List<AccountStatus> accountStatusList = new ArrayList<>();
        List<ConnectionType> connectionTypeList = new ArrayList<>();
        List<TransactionType> transactionTypeList = new ArrayList<>();

        when(roleRepository.findAll()).thenReturn(roleList);
        when(accountTypeRepository.findAll()).thenReturn(accountTypeList);
        when(accountStatusRepository.findAll()).thenReturn(accountStatusList);
        when(connectionTypeRepository.findAll()).thenReturn(connectionTypeList);
        when(transactionTypeRepository.findAll()).thenReturn(transactionTypeList);

        //Act
        userService.entityTypesExistenceChecker();

        //Assert
        verify(roleRepository, times(1)).saveAll(any());
        verify(accountTypeRepository, times(1)).saveAll(any());
        verify(accountStatusRepository, times(1)).saveAll(any());
        verify(connectionTypeRepository, times(1)).saveAll(any());
        verify(transactionTypeRepository, times(1)).saveAll(any());
    }

    @Test
    public void entityTypesExistenceChecker_entitiesExist_entitiesCreated() {

        //Arrange
        List<Role> roleList = Arrays.asList(new Role("Company"), new Role("Admin"), new Role("Regular"));
        List<AccountType> accountTypeList = Arrays.asList(new AccountType("Regular"), new AccountType("Company"));
        List<AccountStatus> accountStatusList = Arrays.asList(new AccountStatus("Active"), new AccountStatus("Inactive"),
                    new AccountStatus("NotYetActivated"), new AccountStatus("Deactivated"));
        List<ConnectionType> connectionTypeList = Arrays.asList(new ConnectionType("Regular"), new ConnectionType("Company"));
        List<TransactionType> transactionTypeList = Arrays.asList(new TransactionType("Regular"), new TransactionType("TopUp"),
                                new TransactionType("Withdrawal"));

        when(roleRepository.findAll()).thenReturn(roleList);
        when(accountTypeRepository.findAll()).thenReturn(accountTypeList);
        when(accountStatusRepository.findAll()).thenReturn(accountStatusList);
        when(connectionTypeRepository.findAll()).thenReturn(connectionTypeList);
        when(transactionTypeRepository.findAll()).thenReturn(transactionTypeList);

        //Act
        userService.entityTypesExistenceChecker();

        //Assert
        verify(roleRepository, times(0)).saveAll(any());
        verify(accountTypeRepository, times(0)).saveAll(any());
        verify(accountStatusRepository, times(0)).saveAll(any());
        verify(connectionTypeRepository, times(0)).saveAll(any());
        verify(transactionTypeRepository, times(0)).saveAll(any());
    }
}
