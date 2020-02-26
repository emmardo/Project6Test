package com.openclassrooms.Project6Test.ModelsTest;

import com.openclassrooms.Project6Test.Models.Account;
import com.openclassrooms.Project6Test.Models.AccountStatus;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AccountStatusTest {

    private int id = 1;

    private String accountStatus = "";

    private List<Account> accounts = new ArrayList<>();

    private Account account = new Account();

    @Test
    public void parameterizedConstructor() {

        //Act
        AccountStatus newAccountStatus = new AccountStatus(accountStatus);

        //Assert
        assertEquals(accountStatus, newAccountStatus.getAccountStatus());
    }

    @Test
    public void setId() {

        //Arrange
        AccountStatus newAccount = new AccountStatus();

        //Act
        newAccount.setId(id);

        //Assert
        assertEquals(id, newAccount.getId());
    }

    @Test
    public void setAccountStatus() {

        //Arrange
        AccountStatus newAccount = new AccountStatus();

        //Act
        newAccount.setAccountStatus(accountStatus);

        //Assert
        assertEquals(accountStatus, newAccount.getAccountStatus());
    }

    @Test
    public void setAccounts() {

        //Arrange
        AccountStatus newAccount = new AccountStatus();

        accounts.add(account);

        //Act
        newAccount.setAccounts(accounts);

        //Assert
        assertEquals(accounts, newAccount.getAccounts());
    }
}
