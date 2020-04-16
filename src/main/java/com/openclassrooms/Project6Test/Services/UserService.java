package com.openclassrooms.Project6Test.Services;

import com.openclassrooms.Project6Test.Models.*;
import com.openclassrooms.Project6Test.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Arrays;

@Service
public class UserService {

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private AccountRepository accountRepository;

    private AccountTypeRepository accountTypeRepository;

    private AccountStatusRepository accountStatusRepository;

    private ConnectionRepository connectionRepository;

    private ConnectionTypeRepository connectionTypeRepository;

    private TransactionTypeRepository transactionTypeRepository;


    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository,
                       AccountRepository accountRepository, AccountTypeRepository accountTypeRepository,
                       AccountStatusRepository accountStatusRepository, ConnectionRepository connectionRepository,
                       ConnectionTypeRepository connectionTypeRepository,
                       TransactionTypeRepository transactionTypeRepository) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.accountRepository = accountRepository;
        this.accountTypeRepository = accountTypeRepository;
        this.accountStatusRepository = accountStatusRepository;
        this.connectionRepository = connectionRepository;
        this.connectionTypeRepository = connectionTypeRepository;
        this.transactionTypeRepository = transactionTypeRepository;
    }


    public void createUserByRole(String email, String password, String userRole) {

        if(!userAccountExistenceValidatorByEmail(email) && userRoleValidator(userRole)
            && (password != null && !password.equals(""))) {

            entityTypesExistenceChecker();

            User user = new User(email, password, roleRepository.findRoleByRole(userRole));

            userRepository.save(user);

            if(userRole != "Admin") {

                //Create Connection
                Connection connection = new Connection(
                                                connectionTypeRepository.findConnectionTypeByConnectionType(userRole),
                                                user);

                //Create Account and Assign it a Balance of 0£
                Account account = new Account(user, accountTypeRepository.findAccountTypeByAccountType(userRole),
                                        accountStatusRepository.findAccountStatusByAccountStatus("Active"), connection,
                                        0);

                connectionRepository.save(connection);

                accountRepository.save(account);
            }

        }
    }

    public User getUserByEmail(String userEmail) {

        User newUser = null;

        if(userAccountExistenceValidatorByEmail(userEmail)) {

            newUser = userRepository.findUserByEmail(userEmail);
        }

        return newUser;
    }

    public User getUserFromAuthentication(Authentication authentication) {

        User user = null;

        if(authentication.getName() != null) {

            String userEmail = authentication.getName();

            user =  getUserByEmail(userEmail);
        }
        return user;
    }


    public boolean userAccountExistenceValidatorByEmail(String email) {

        boolean value = false;

        if(email != null || !email.equals("")) {

            if(userRepository.findUserByEmail(email) != null) {

                value = true;
            }
        }

        return value;
    }


    public boolean userRoleValidator(String role) {

        boolean value = false;

        if(role != null || !role.equals("")) {

            if(Arrays.asList("Company", "Admin", "Regular").contains(role)) {

                value = true;
            }
        }

        return value;
    }

    public void entityTypesExistenceChecker() {

        if(roleRepository.findAll().isEmpty()){
            roleRepository.saveAll(Arrays.asList(new Role("Company"), new Role("Admin"), new Role("Regular")));
        }

        if(accountTypeRepository.findAll().isEmpty()){

            accountTypeRepository.saveAll(Arrays.asList(new AccountType("Regular"), new AccountType("Company")));
        }

        if(accountStatusRepository.findAll().isEmpty()){

            accountStatusRepository.saveAll(Arrays.asList(new AccountStatus("Active"), new AccountStatus("Inactive"),
                    new AccountStatus("NotYetActivated"), new AccountStatus("Deactivated")));
        }

        if(connectionTypeRepository.findAll().isEmpty()){

            connectionTypeRepository.saveAll(Arrays.asList(new ConnectionType("Regular"),
                    new ConnectionType("Company")));
        }

        if(transactionTypeRepository.findAll().isEmpty()){

            transactionTypeRepository.saveAll(Arrays.asList(new TransactionType("Regular"), new TransactionType("TopUp"),
                    new TransactionType("Withdrawal")));
        }
    }
}
