package com.openclassrooms.Project6Test.Services;

import com.openclassrooms.Project6Test.Models.*;
import com.openclassrooms.Project6Test.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;

@Service
public class UserService {

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private UserModificationTypeRepository userModificationTypeRepository;

    private AccountRepository accountRepository;

    private AccountTypeRepository accountTypeRepository;

    private AccountStatusRepository accountStatusRepository;

    private ConnectionRepository connectionRepository;

    private ConnectionTypeRepository connectionTypeRepository;

    private TransactionTypeRepository transactionTypeRepository;

    private UserModificationRegisterRepository userModificationRegisterRepository;


    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository,
                       UserModificationTypeRepository userModificationTypeRepository,
                       AccountRepository accountRepository, AccountTypeRepository accountTypeRepository,
                       AccountStatusRepository accountStatusRepository, ConnectionRepository connectionRepository,
                       ConnectionTypeRepository connectionTypeRepository,
                       TransactionTypeRepository transactionTypeRepository,
                       UserModificationRegisterRepository userModificationRegisterRepository) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userModificationTypeRepository = userModificationTypeRepository;
        this.accountRepository = accountRepository;
        this.accountTypeRepository = accountTypeRepository;
        this.accountStatusRepository = accountStatusRepository;
        this.connectionRepository = connectionRepository;
        this.connectionTypeRepository = connectionTypeRepository;
        this.transactionTypeRepository = transactionTypeRepository;
        this.userModificationRegisterRepository = userModificationRegisterRepository;
    }


    public void createUserByRole(String email, String password, String userRole) {

        if(!userAccountExistenceValidatorByEmail(email) && userRoleValidator(userRole)
            && (password != null || !password.isEmpty())) {

            entityTypesExistenceChecker();

            /*//Create Role
            Role role = new Role(roleRepository.findRoleByRole(userRole).getRole());*/

            //Create User and Assign Email, Password and Role to it
            User user;
            user = new User(email, password, roleRepository.findRoleByRole(userRole));

            //Create Date and Assign it to the Created User
            Date date = new Date();
            user.setCreatedAt(date);

            userRepository.save(user);

            if(userRole != "Admin") {

                /*//Create AccountType
                String accountTypeString = userRole;
                AccountType accountType = new AccountType(accountTypeString);

                //Create AccountStatus
                String accountStatusString = "Active";
                AccountStatus accountStatus = new AccountStatus(accountStatusString);

                //Create ConnectionType
                String connectionTypeString = userRole;
                ConnectionType connectionType = new ConnectionType(connectionTypeString);*/

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

    /*public List<User> getAllUsersByRole(String role) {

        return userRepository.findAll().stream().filter(u -> u.getRole().getRole().equals(role))
                .collect(Collectors.toList());
    }*/

    public void updateUsersEmailAddress(String currentEmailAddress, String password, String newEmailAddress) {

        if(userAccountExistenceValidatorByEmail(currentEmailAddress)
                && passwordValidator(currentEmailAddress, password)) {

            User user = getUserByEmail(currentEmailAddress);
            user.setEmail(newEmailAddress);

            Date date = new Date();
            user.setUpdatedAt(date);

            userRepository.save(user);

            UserModificationType userModificationType = userModificationTypeRepository
                                                        .findUserModificationTypeByUserModificationType("Email");

            UserModificationRegister userModificationRegister = new UserModificationRegister
                                                                    (user, userModificationType, date);
            userModificationRegister.setPreviousDetails(currentEmailAddress);
            userModificationRegister.setNewDetails(newEmailAddress);

            userModificationRegisterRepository.save(userModificationRegister);
        }
    }

    public void updateUsersPassword(String emailAddress, String currentPassword, String newPassword) {

        if(userAccountExistenceValidatorByEmail(emailAddress) && passwordValidator(emailAddress, currentPassword)) {

            User user = getUserByEmail(emailAddress);
            user.setPassword(newPassword);

            Date date = new Date();
            user.setUpdatedAt(date);

            userRepository.save(user);

            UserModificationType userModificationType = userModificationTypeRepository
                                                        .findUserModificationTypeByUserModificationType("Password");

            UserModificationRegister userModificationRegister = new UserModificationRegister
                                                                    (user, userModificationType, date);

            userModificationRegisterRepository.save(userModificationRegister);
        }
    }


    public void deleteUserByEmail(String userEmail, String password) {

        if(userAccountExistenceValidatorByEmail(userEmail) && passwordValidator(userEmail, password)) {

            accountRepository.delete(accountRepository.findAccountByUserEmail(userEmail));

            connectionRepository.delete(connectionRepository.findConnectionByUserEmail(userEmail));

            userRepository.delete(userRepository.findUserByEmail(userEmail));
        }
    }


    public boolean userAccountExistenceValidatorByEmail(String email) {

        boolean value = false;

        if(email != null || !email.isEmpty()) {

            if(userRepository.findUserByEmail(email)!= null) {

                value = true;
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
            && userAccountExistenceValidatorByEmail(email)) {

            if(userRepository.findUserByEmail(email).getPassword().equals(password)) {

                value = true;
            }
        }

        return value;
    }

    public void entityTypesExistenceChecker() {

        if(roleRepository.findAll().isEmpty()){

            roleRepository.saveAll(Arrays.asList(new Role("Company"), new Role("Admin"), new Role("Regular")));
        }

        if(userModificationTypeRepository.findAll().isEmpty()){

            userModificationTypeRepository.saveAll(
                    Arrays.asList(new UserModificationType("Email"), new UserModificationType("Password")));
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
