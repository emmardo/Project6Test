package com.openclassrooms.Project6Test.Services;

import com.openclassrooms.Project6Test.Models.*;
import com.openclassrooms.Project6Test.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserModificationTypeRepository userModificationTypeRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountTypeRepository accountTypeRepository;

    @Autowired
    private AccountStatusRepository accountStatusRepository;

    @Autowired
    private ConnectionRepository connectionRepository;

    @Autowired
    private ConnectionTypeRepository connectionTypeRepository;

    @Autowired
    private TransactionTypeRepository transactionTypeRepository;

    @Autowired
    private UserModificationRegisterRepository userModificationRegisterRepository;


    public UserService(UserRepository userRepository, AccountRepository accountRepository,
                       ConnectionRepository connectionRepository,
                       UserModificationRegisterRepository userModificationRegisterRepository) {

        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.connectionRepository = connectionRepository;
        this.userModificationRegisterRepository = userModificationRegisterRepository;
    }


    public void createUserByRole(String email, String password, String userRole) {

        if(userAccountInexistenceValidatorByEmail(email) && userRoleValidator(userRole)
            && (password != null || !password.isEmpty())) {

            entityTypesCreator();

            //Create Role
            Role role = new Role(userRole);

            //Create User and Assign Email, Password and Role to it
            User user;
            user = new User(email, password, role);

            //Create Date and Assign it to the Created User
            Date date = new Date();
            user.setCreatedAt(date);

            userRepository.save(user);

            if(userRole != "Admin") {

                //Create AccountType
                String accountTypeString = userRole;
                AccountType accountType = new AccountType(accountTypeString);

                //Create AccountStatus
                String accountStatusString = "Active";
                AccountStatus accountStatus = new AccountStatus(accountStatusString);

                //Create ConnectionType
                String connectionTypeString = userRole;
                ConnectionType connectionType = new ConnectionType(connectionTypeString);

                //Create Connection
                Connection connection = new Connection(connectionType, user);

                //Create Account and Assign it a Balance of 0£
                Account account = new Account(user, accountType, accountStatus, connection, 0);

                connectionRepository.save(connection);

                accountRepository.save(account);
            }
        }
    }

    public User getUserByEmail(String userEmail) {

        User newUser = null;

        if(!userAccountInexistenceValidatorByEmail(userEmail)) {

            newUser = userRepository.findByEmail(userEmail);
        }

        return newUser;
    }

    /*public List<User> getAllUsersByRole(String role) {

        return userRepository.findAll().stream().filter(u -> u.getRole().getRole().equals(role))
                .collect(Collectors.toList());
    }*/

    public void updateUsersEmailAddress(String currentEmailAddress, String password, String newEmailAddress) {

        if(!userAccountInexistenceValidatorByEmail(currentEmailAddress)
                && passwordValidator(currentEmailAddress, password)) {

            User user = getUserByEmail(currentEmailAddress);
            user.setEmail(newEmailAddress);

            Date date = new Date();
            user.setUpdatedAt(date);

            userRepository.save(user);

            UserModificationType userModificationType = new UserModificationType("Email");

            UserModificationRegister userModificationRegister = new UserModificationRegister
                                                                    (user, userModificationType, date);
            userModificationRegister.setPreviousDetails(currentEmailAddress);
            userModificationRegister.setNewDetails(newEmailAddress);

            userModificationRegisterRepository.save(userModificationRegister);
        }
    }

    public void updateUsersPassword(String emailAddress, String currentPassword, String newPassword) {

        if(!userAccountInexistenceValidatorByEmail(emailAddress) && passwordValidator(emailAddress, currentPassword)) {

            User user = getUserByEmail(emailAddress);
            user.setPassword(newPassword);

            Date date = new Date();
            user.setUpdatedAt(date);

            userRepository.save(user);

            UserModificationType userModificationType = new UserModificationType("Password");

            UserModificationRegister userModificationRegister = new UserModificationRegister
                                                                    (user, userModificationType, date);

            userModificationRegisterRepository.save(userModificationRegister);
        }
    }


    public void deleteUserByEmail(String userEmail, String password) {

        if(!userAccountInexistenceValidatorByEmail(userEmail) && passwordValidator(userEmail, password)) {

            accountRepository.delete(accountRepository.findAccountByUserEmail(userEmail));

            connectionRepository.delete(connectionRepository.findConnectionByUserEmail(userEmail));

            userRepository.delete(userRepository.findByEmail(userEmail));
        }
    }


    public boolean userAccountInexistenceValidatorByEmail(String email) {

        boolean value = true;

        if(email != null || !email.isEmpty()) {

            if(!userRepository.findByEmail(email).getEmail().isEmpty()
                || userRepository.findByEmail(email).getEmail() != null) {

                value = false;
            }
        }

        return value;
    }


    public boolean userRoleValidator(String role) {

        boolean value = false;

        if(role != null || !role.isEmpty()) {

            if(Arrays.asList("Company", "Admin", "Regular").contains(role)) {

                value = true;
            }
        }

        return value;
    }

    public boolean passwordValidator(String email, String password) {

        boolean value = false;

        if((email != null || !email.isEmpty())
            && (password != null || !password.isEmpty())
            && !userAccountInexistenceValidatorByEmail(email)) {

            if(userRepository.findByEmail(email).getPassword().equals(password)) {

                value = true;
            }
        }

        return value;
    }

    public void entityTypesCreator() {

        roleRepository.deleteAll();
        userModificationTypeRepository.deleteAll();
        accountTypeRepository.deleteAll();
        accountStatusRepository.deleteAll();
        connectionTypeRepository.deleteAll();
        transactionTypeRepository.deleteAll();

        roleRepository.saveAll(Arrays.asList(new Role("Company"), new Role("Admin"), new Role("Regular")));
        userModificationTypeRepository.saveAll(
                Arrays.asList(new UserModificationType("Email"), new UserModificationType("Password")));
        accountTypeRepository.saveAll(Arrays.asList(new AccountType("Regular"), new AccountType("Company")));
        accountStatusRepository.saveAll(Arrays.asList(new AccountStatus("Active"), new AccountStatus("Inactive"),
                new AccountStatus("NotYetActivated"), new AccountStatus("Deactivated")));
        connectionTypeRepository.saveAll(Arrays.asList(new ConnectionType("Regular"), new ConnectionType("Company")));
        transactionTypeRepository.saveAll(Arrays.asList(new TransactionType("Regular"), new TransactionType("TopUp"),
                new TransactionType("Withdrawal")));
    }
}
