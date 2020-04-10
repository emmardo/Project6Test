package com.openclassrooms.Project6Test.ModelsTest;

import com.openclassrooms.Project6Test.Models.Account;
import com.openclassrooms.Project6Test.Models.AccountStatus;
import com.openclassrooms.Project6Test.Models.AccountType;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AccountTypeTest {

    private int id = 1;

    private String accountType = "";

    private List<Account> accounts = new ArrayList<>();

    private Account account = new Account();

    @Test
    public void parameterizedConstructor() {

        //Act
        AccountType newAccountType = new AccountType(accountType);

        //Assert
        assertEquals(accountType, newAccountType.getAccountType());
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
    public void setAccountType() {

        //Arrange
        AccountType newAccountType = new AccountType();

        //Act
        newAccountType.setAccountType(accountType);

        //Assert
        assertEquals(accountType, newAccountType.getAccountType());
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
