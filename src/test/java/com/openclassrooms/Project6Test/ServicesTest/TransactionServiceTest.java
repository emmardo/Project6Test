package com.openclassrooms.Project6Test.ServicesTest;

import com.openclassrooms.Project6Test.Models.*;
import com.openclassrooms.Project6Test.Repositories.*;
import com.openclassrooms.Project6Test.Services.TransactionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionTypeRepository transactionTypeRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private ConnectionRepository connectionRepository;

    @Mock
    private IbanRepository ibanRepository;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    public void createTransactionByTransactionType_createTopUpTransaction_transactionCreated() {

        //Arrange
        String sendersEmail = "abc@abc.com";
        User sendersUser = new User();
        sendersUser.setEmail(sendersEmail);
        Account sendersAccount = new Account();
        sendersAccount.setUser(sendersUser);
        float sendersCurrentBalance = 100f;
        sendersAccount.setCurrentBalance(sendersCurrentBalance);
        String sendersAccountStatusString = "Active";
        AccountStatus sendersAccountStatus = new AccountStatus();
        sendersAccountStatus.setAccountStatus(sendersAccountStatusString);
        sendersAccount.setAccountStatus(sendersAccountStatus);

        String transactionTypeString = "TopUp";

        float transactionMoneyAmount = 50f;
        String origin = "Bank Account";
        String description = "TopUp";


        when(accountRepository.findAccountByUserEmail(anyString())).thenReturn(sendersAccount);

        when(accountRepository.save(any(Account.class))).thenReturn(new Account());

        when(transactionRepository.save(any(Transaction.class))).thenReturn(new Transaction());

        //Act
        transactionService.createTransactionByTransactionType(transactionTypeString, sendersEmail, transactionMoneyAmount, origin, description);

        //Assert
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    public void createTransactionByTransactionType_createWithdrawalTransactionIbanInexistent_transactionAndIbanCreated() {

        //Arrange
        String sendersEmail = "abc@abc.com";
        User sendersUser = new User();
        sendersUser.setEmail(sendersEmail);
        Account sendersAccount = new Account();
        sendersAccount.setUser(sendersUser);
        float sendersCurrentBalance = 100f;
        sendersAccount.setCurrentBalance(sendersCurrentBalance);
        String sendersAccountStatusString = "Active";
        AccountStatus sendersAccountStatus = new AccountStatus();
        sendersAccountStatus.setAccountStatus(sendersAccountStatusString);
        sendersAccount.setAccountStatus(sendersAccountStatus);

        String ibanString1 = "GB123456";
        String ibanString2 = "AR123456";

        Iban iban1 = new Iban(sendersAccount, ibanString1);
        Iban iban2 = new Iban(sendersAccount, ibanString2);

        List<Iban> ibanList = new ArrayList<>();
        ibanList.add(iban1);
        ibanList.add(iban2);

        String transactionTypeString = "Withdrawal";

        float transactionMoneyAmount = 50f;
        String ibanString = "FR123456";
        String description = "Withdrawal";

        when(ibanRepository.findByAccount_UserEmail(anyString())).thenReturn(ibanList);

        when(ibanRepository.save(any(Iban.class))).thenReturn(new Iban());

        when(accountRepository.findAccountByUserEmail(anyString())).thenReturn(sendersAccount);

        when(accountRepository.save(any(Account.class))).thenReturn(new Account());

        when(transactionRepository.save(any(Transaction.class))).thenReturn(new Transaction());

        //Act
        transactionService.createTransactionByTransactionType(transactionTypeString, sendersEmail, transactionMoneyAmount,
                                                                ibanString, description);

        //Assert
        verify(transactionRepository, times(1)).save(any(Transaction.class));
        verify(ibanRepository, times(1)).save(any(Iban.class));
    }

    @Test
    public void createTransactionByTransactionType_createWithdrawalTransactionIbanExists_transactionCreated() {

        //Arrange
        String sendersEmail = "abc@abc.com";
        User sendersUser = new User();
        sendersUser.setEmail(sendersEmail);
        Account sendersAccount = new Account();
        sendersAccount.setUser(sendersUser);
        float sendersCurrentBalance = 100f;
        sendersAccount.setCurrentBalance(sendersCurrentBalance);
        String sendersAccountStatusString = "Active";
        AccountStatus sendersAccountStatus = new AccountStatus();
        sendersAccountStatus.setAccountStatus(sendersAccountStatusString);
        sendersAccount.setAccountStatus(sendersAccountStatus);

        String ibanString1 = "GB123456";
        String ibanString2 = "AR123456";

        Iban iban1 = new Iban(sendersAccount, ibanString1);
        Iban iban2 = new Iban(sendersAccount, ibanString2);

        List<Iban> ibanList = new ArrayList<>();
        ibanList.add(iban1);
        ibanList.add(iban2);

        String transactionTypeString = "Withdrawal";

        float transactionMoneyAmount = 50f;
        String description = "Withdrawal";

        when(ibanRepository.findByAccount_UserEmail(anyString())).thenReturn(ibanList);

        when(accountRepository.findAccountByUserEmail(anyString())).thenReturn(sendersAccount);

        when(accountRepository.save(any(Account.class))).thenReturn(new Account());

        when(transactionRepository.save(any(Transaction.class))).thenReturn(new Transaction());

        //Act
        transactionService.createTransactionByTransactionType(transactionTypeString, sendersEmail, transactionMoneyAmount,
                ibanString1, description);

        //Assert
        verify(transactionRepository, times(1)).save(any(Transaction.class));
        verify(ibanRepository, times(0)).save(any(Iban.class));
    }

    @Test
    public void createTransactionByTransactionType_createWithdrawalTransactionInsufficientFunds_transactionNotCreated() {

        //Arrange
        String sendersEmail = "abc@abc.com";
        User sendersUser = new User();
        sendersUser.setEmail(sendersEmail);
        Account sendersAccount = new Account();
        sendersAccount.setUser(sendersUser);
        float sendersCurrentBalance = 100f;
        sendersAccount.setCurrentBalance(sendersCurrentBalance);
        String sendersAccountStatusString = "Active";
        AccountStatus sendersAccountStatus = new AccountStatus();
        sendersAccountStatus.setAccountStatus(sendersAccountStatusString);
        sendersAccount.setAccountStatus(sendersAccountStatus);

        String ibanString1 = "GB123456";
        String ibanString2 = "AR123456";

        Iban iban1 = new Iban(sendersAccount, ibanString1);
        Iban iban2 = new Iban(sendersAccount, ibanString2);

        List<Iban> ibanList = new ArrayList<>();
        ibanList.add(iban1);
        ibanList.add(iban2);

        String transactionTypeString = "Withdrawal";

        float transactionMoneyAmount = 200f;
        String ibanString = "FR123456";
        String description = "Withdrawal";

        when(accountRepository.findAccountByUserEmail(anyString())).thenReturn(sendersAccount);

        //Act
        transactionService.createTransactionByTransactionType(transactionTypeString, sendersEmail, transactionMoneyAmount,
                ibanString, description);

        //Assert
        verify(transactionRepository, times(0)).save(any(Transaction.class));
    }

    @Test
    public void createTransactionByTransactionType_createRegularTransactionEnoughFunds_transactionCreated() {

        //Arrange
        String sendersEmail = "abc@abc.com";
        User sendersUser = new User();
        sendersUser.setEmail(sendersEmail);
        Account sendersAccount = new Account();
        sendersAccount.setUser(sendersUser);
        float sendersCurrentBalance = 100f;
        sendersAccount.setCurrentBalance(sendersCurrentBalance);
        String sendersAccountStatusString = "Active";
        AccountStatus sendersAccountStatus = new AccountStatus();
        sendersAccountStatus.setAccountStatus(sendersAccountStatusString);
        sendersAccount.setAccountStatus(sendersAccountStatus);

        String receiversEmail = "abc@abc.com";
        User receiversUser = new User();
        receiversUser.setEmail(receiversEmail);
        Account receiversAccount = new Account();
        receiversAccount.setUser(sendersUser);
        float receiversCurrentBalance = 100f;
        receiversAccount.setCurrentBalance(receiversCurrentBalance);
        String receiversAccountStatusString = "Active";
        AccountStatus receiversAccountStatus = new AccountStatus();
        receiversAccountStatus.setAccountStatus(receiversAccountStatusString);
        receiversAccount.setAccountStatus(receiversAccountStatus);
        Connection receiversConnection = new Connection();

        String companysEmail = "000@000.com";
        Account companysAccount = new Account();
        float companysCurrentBalance = 0f;
        companysAccount.setCurrentBalance(companysCurrentBalance);

        String transactionTypeString = "Regular";

        float transactionMoneyAmount = 50f;
        String description = "Shoes";

        when(accountRepository.findAccountByUserEmail(sendersEmail)).thenReturn(sendersAccount);
        when(accountRepository.findAccountByUserEmail(receiversEmail)).thenReturn(receiversAccount);
        when(accountRepository.findAccountByUserEmail(companysEmail)).thenReturn(companysAccount);

        when(connectionRepository.findConnectionByUserEmail(receiversEmail)).thenReturn(receiversConnection);

        when(accountRepository.save(any(Account.class))).thenReturn(new Account());

        when(transactionRepository.save(any(Transaction.class))).thenReturn(new Transaction());

        //Act
        transactionService.createTransactionByTransactionType(transactionTypeString, sendersEmail, transactionMoneyAmount,
                receiversEmail, description);

        //Assert
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    public void createTransactionByTransactionType_createRegularTransactionInsufficientFunds_transactionNotCreated() {

        //Arrange
        String sendersEmail = "abc@abc.com";
        User sendersUser = new User();
        sendersUser.setEmail(sendersEmail);
        Account sendersAccount = new Account();
        sendersAccount.setUser(sendersUser);
        float sendersCurrentBalance = 100f;
        sendersAccount.setCurrentBalance(sendersCurrentBalance);
        String sendersAccountStatusString = "Active";
        AccountStatus sendersAccountStatus = new AccountStatus();
        sendersAccountStatus.setAccountStatus(sendersAccountStatusString);
        sendersAccount.setAccountStatus(sendersAccountStatus);

        String receiversEmail = "abc@abc.com";
        User receiversUser = new User();
        receiversUser.setEmail(receiversEmail);
        Account receiversAccount = new Account();
        receiversAccount.setUser(sendersUser);
        float receiversCurrentBalance = 100f;
        receiversAccount.setCurrentBalance(receiversCurrentBalance);
        String receiversAccountStatusString = "Active";
        AccountStatus receiversAccountStatus = new AccountStatus();
        receiversAccountStatus.setAccountStatus(receiversAccountStatusString);
        receiversAccount.setAccountStatus(receiversAccountStatus);
        Connection receiversConnection = new Connection();

        String companysEmail = "000@000.com";
        Account companysAccount = new Account();
        float companysCurrentBalance = 0f;
        companysAccount.setCurrentBalance(companysCurrentBalance);

        String transactionTypeString = "Regular";

        float transactionMoneyAmount = 200f;
        String description = "Shoes";

        when(accountRepository.findAccountByUserEmail(sendersEmail)).thenReturn(sendersAccount);
        when(accountRepository.findAccountByUserEmail(receiversEmail)).thenReturn(receiversAccount);

        //Act
        transactionService.createTransactionByTransactionType(transactionTypeString, sendersEmail, transactionMoneyAmount,
                receiversEmail, description);

        //Assert
        verify(transactionRepository, times(0)).save(any(Transaction.class));
    }

    @Test
    public void getAUsersTransactionsByEmail_transactionsExist_transactionsReturned() {

        //Arrange
        String userEmail = "abc@abc.com";

        Transaction transaction1 = new Transaction();
        Transaction transaction2 = new Transaction();

        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(transaction1);
        transactionList.add(transaction2);

        when(transactionRepository.findTransactionsByAccountUserEmail(userEmail)).thenReturn(transactionList);
        when(transactionRepository.findTransactionsByConnectionUserEmail(userEmail)).thenReturn(transactionList);

        //Act
        List<Transaction> list = transactionService.getAUsersTransactionsByEmail(userEmail);

        //Assert
        assertTrue(!list.isEmpty());
    }

    @Test
    public void getAUsersTransactionsByEmail_transactionsInexistent_emptyTransactionListReturned() {

        //Arrange
        String userEmail = "abc@abc.com";

        List<Transaction> transactionList = new ArrayList<>();

        when(transactionRepository.findTransactionsByAccountUserEmail(userEmail)).thenReturn(transactionList);
        when(transactionRepository.findTransactionsByConnectionUserEmail(userEmail)).thenReturn(transactionList);

        //Act
        List<Transaction> list = transactionService.getAUsersTransactionsByEmail(userEmail);

        //Assert
        assertTrue(list.isEmpty());
    }

    @Test
    public void transactionConditionsValidator_topUpTransaction_transactionConditionsValid() {

        //Arrange
        String sendersEmail = "abc@abc.com";
        User sendersUser = new User();
        sendersUser.setEmail(sendersEmail);
        Account sendersAccount = new Account();
        sendersAccount.setUser(sendersUser);
        float sendersAccountCurrentBalance = 100f;
        sendersAccount.setCurrentBalance(sendersAccountCurrentBalance);
        String activeAccountStatusString = "Active";
        AccountStatus sendersAccountStatus = new AccountStatus();
        sendersAccountStatus.setAccountStatus(activeAccountStatusString);
        sendersAccount.setAccountStatus(sendersAccountStatus);

        String transactionType = "TopUp";
        float transactionMoneyAmount = 100f;
        String origin = "Credit Card";

        when(accountRepository.findAccountByUserEmail(sendersEmail)).thenReturn(sendersAccount);

        //Act
        boolean result = transactionService.transactionConditionsValidator(transactionType, sendersEmail
                                                                , transactionMoneyAmount, origin);

        //Assert
        assertTrue(result);
    }

    @Test
    public void transactionConditionsValidator_withdrawalTransactionSufficientFunds_transactionConditionsValid() {

        //Arrange
        String sendersEmail = "abc@abc.com";
        User sendersUser = new User();
        sendersUser.setEmail(sendersEmail);
        Account sendersAccount = new Account();
        sendersAccount.setUser(sendersUser);
        float sendersAccountCurrentBalance = 100f;
        sendersAccount.setCurrentBalance(sendersAccountCurrentBalance);
        String activeAccountStatusString = "Active";
        AccountStatus sendersAccountStatus = new AccountStatus();
        sendersAccountStatus.setAccountStatus(activeAccountStatusString);
        sendersAccount.setAccountStatus(sendersAccountStatus);

        String transactionType = "Withdrawal";
        float transactionMoneyAmount = 100f;
        String ibanString = "GB123456";

        when(accountRepository.findAccountByUserEmail(sendersEmail)).thenReturn(sendersAccount);

        //Act
        boolean result = transactionService.transactionConditionsValidator(transactionType, sendersEmail
                                                                , transactionMoneyAmount, ibanString);

        //Assert
        assertTrue(result);
    }

    @Test
    public void transactionConditionsValidator_withdrawalTransactionInsufficientFunds_transactionConditionsNotValid() {

        //Arrange
        String sendersEmail = "abc@abc.com";
        User sendersUser = new User();
        sendersUser.setEmail(sendersEmail);
        Account sendersAccount = new Account();
        sendersAccount.setUser(sendersUser);
        float sendersAccountCurrentBalance = 100f;
        sendersAccount.setCurrentBalance(sendersAccountCurrentBalance);
        String activeAccountStatusString = "Active";
        AccountStatus sendersAccountStatus = new AccountStatus();
        sendersAccountStatus.setAccountStatus(activeAccountStatusString);
        sendersAccount.setAccountStatus(sendersAccountStatus);

        String transactionType = "Withdrawal";
        float transactionMoneyAmount = 200f;
        String ibanString = "GB123456";

        when(accountRepository.findAccountByUserEmail(sendersEmail)).thenReturn(sendersAccount);

        //Act
        boolean result = transactionService.transactionConditionsValidator(transactionType, sendersEmail
                , transactionMoneyAmount, ibanString);

        //Assert
        assertFalse(result);
    }

    @Test
    public void transactionConditionsValidator_regularTransactionSufficientFunds_transactionConditionsValid() {

        //Arrange
        String sendersEmail = "abc@abc.com";
        User sendersUser = new User();
        sendersUser.setEmail(sendersEmail);
        Account sendersAccount = new Account();
        sendersAccount.setUser(sendersUser);
        float sendersAccountCurrentBalance = 100f;
        sendersAccount.setCurrentBalance(sendersAccountCurrentBalance);
        String activeAccountStatusString = "Active";
        AccountStatus sendersAccountStatus = new AccountStatus();
        sendersAccountStatus.setAccountStatus(activeAccountStatusString);
        sendersAccount.setAccountStatus(sendersAccountStatus);

        String receiversEmail = "def@def.com";
        User receiversUser = new User();
        receiversUser.setEmail(receiversEmail);
        Account receiversAccount = new Account();
        receiversAccount.setUser(receiversUser);
        AccountStatus receiversAccountStatus = new AccountStatus();
        receiversAccountStatus.setAccountStatus(activeAccountStatusString);
        receiversAccount.setAccountStatus(receiversAccountStatus);

        String transactionType = "Regular";
        float transactionMoneyAmount = 95f;

        when(accountRepository.findAccountByUserEmail(sendersEmail)).thenReturn(sendersAccount);
        when(accountRepository.findAccountByUserEmail(receiversEmail)).thenReturn(receiversAccount);

        //Act
        boolean result = transactionService.transactionConditionsValidator(transactionType, sendersEmail
                                                                , transactionMoneyAmount, receiversEmail);

        //Assert
        assertTrue(result);
    }

    @Test
    public void transactionConditionsValidator_regularTransactionInsufficientFunds_transactionConditionsNotValid() {

        //Arrange
        String sendersEmail = "abc@abc.com";
        User sendersUser = new User();
        sendersUser.setEmail(sendersEmail);
        Account sendersAccount = new Account();
        sendersAccount.setUser(sendersUser);
        float sendersAccountCurrentBalance = 100f;
        sendersAccount.setCurrentBalance(sendersAccountCurrentBalance);
        String activeAccountStatusString = "Active";
        AccountStatus sendersAccountStatus = new AccountStatus();
        sendersAccountStatus.setAccountStatus(activeAccountStatusString);
        sendersAccount.setAccountStatus(sendersAccountStatus);

        String receiversEmail = "def@def.com";
        User receiversUser = new User();
        receiversUser.setEmail(receiversEmail);
        Account receiversAccount = new Account();
        receiversAccount.setUser(receiversUser);
        AccountStatus receiversAccountStatus = new AccountStatus();
        receiversAccountStatus.setAccountStatus(activeAccountStatusString);
        receiversAccount.setAccountStatus(receiversAccountStatus);

        String transactionType = "Regular";
        float transactionMoneyAmount = 100f;

        when(accountRepository.findAccountByUserEmail(sendersEmail)).thenReturn(sendersAccount);
        when(accountRepository.findAccountByUserEmail(receiversEmail)).thenReturn(receiversAccount);

        //Act
        boolean result = transactionService.transactionConditionsValidator(transactionType, sendersEmail
                , transactionMoneyAmount, receiversEmail);

        //Assert
        assertFalse(result);
    }

    @Test
    public void transactionConditionsValidator_transactionTypeNotValid_transactionConditionsNotValid() {

        //Arrange
        String sendersEmail = "abc@abc.com";
        User sendersUser = new User();
        sendersUser.setEmail(sendersEmail);
        Account sendersAccount = new Account();
        sendersAccount.setUser(sendersUser);
        float sendersAccountCurrentBalance = 100f;
        sendersAccount.setCurrentBalance(sendersAccountCurrentBalance);
        String activeAccountStatusString = "Active";
        AccountStatus sendersAccountStatus = new AccountStatus();
        sendersAccountStatus.setAccountStatus(activeAccountStatusString);
        sendersAccount.setAccountStatus(sendersAccountStatus);

        String receiversEmail = "def@def.com";
        User receiversUser = new User();
        receiversUser.setEmail(receiversEmail);
        Account receiversAccount = new Account();
        receiversAccount.setUser(receiversUser);
        AccountStatus receiversAccountStatus = new AccountStatus();
        receiversAccountStatus.setAccountStatus(activeAccountStatusString);
        receiversAccount.setAccountStatus(receiversAccountStatus);

        String transactionType = "Theft";
        float transactionMoneyAmount = 95f;

        //Act
        boolean result = transactionService.transactionConditionsValidator(transactionType, sendersEmail
                , transactionMoneyAmount, receiversEmail);

        //Assert
        assertFalse(result);
    }

    @Test
    public void transactionConditionsValidator_sendersAccountInactive_transactionConditionsNotValid() {

        //Arrange
        String sendersEmail = "abc@abc.com";
        User sendersUser = new User();
        sendersUser.setEmail(sendersEmail);
        Account sendersAccount = new Account();
        sendersAccount.setUser(sendersUser);
        float sendersAccountCurrentBalance = 100f;
        sendersAccount.setCurrentBalance(sendersAccountCurrentBalance);
        String activeAccountStatusString = "Inactive";
        AccountStatus sendersAccountStatus = new AccountStatus();
        sendersAccountStatus.setAccountStatus(activeAccountStatusString);
        sendersAccount.setAccountStatus(sendersAccountStatus);

        String receiversEmail = "def@def.com";
        User receiversUser = new User();
        receiversUser.setEmail(receiversEmail);
        Account receiversAccount = new Account();
        receiversAccount.setUser(receiversUser);
        AccountStatus receiversAccountStatus = new AccountStatus();
        receiversAccountStatus.setAccountStatus(activeAccountStatusString);
        receiversAccount.setAccountStatus(receiversAccountStatus);

        String transactionType = "Regular";
        float transactionMoneyAmount = 95f;

        when(accountRepository.findAccountByUserEmail(sendersEmail)).thenReturn(sendersAccount);

        //Act
        boolean result = transactionService.transactionConditionsValidator(transactionType, sendersEmail
                , transactionMoneyAmount, receiversEmail);

        //Assert
        assertFalse(result);
    }

    @Test
    public void transactionTypeValidator_regularTransaction_transactionTypeValid() {

        //Arrange
        String transactionType = "Regular";

        //Act
        boolean result = transactionService.transactionTypeValidator(transactionType);

        //Assert
        assertTrue(result);
    }

    @Test
    public void transactionTypeValidator_topUpTransaction_transactionTypeValid() {

        //Arrange
        String transactionType = "TopUp";

        //Act
        boolean result = transactionService.transactionTypeValidator(transactionType);

        //Assert
        assertTrue(result);
    }

    @Test
    public void transactionTypeValidator_withdrawalTransaction_transactionTypeValid() {

        //Arrange
        String transactionType = "Withdrawal";

        //Act
        boolean result = transactionService.transactionTypeValidator(transactionType);

        //Assert
        assertTrue(result);
    }

    @Test
    public void transactionTypeValidator_allTransaction_transactionTypeValid() {

        //Arrange
        String transactionType = "All";

        //Act
        boolean result = transactionService.transactionTypeValidator(transactionType);

        //Assert
        assertTrue(result);
    }

    @Test
    public void transactionTypeValidator_receiverTransaction_transactionTypeValid() {

        //Arrange
        String transactionType = "Receiver";

        //Act
        boolean result = transactionService.transactionTypeValidator(transactionType);

        //Assert
        assertTrue(result);
    }

    @Test
    public void transactionTypeValidator_theftTransaction_transactionTypeNotValid() {

        //Arrange
        String transactionType = "Theft";

        //Act
        boolean result = transactionService.transactionTypeValidator(transactionType);

        //Assert
        assertFalse(result);
    }

    @Test
    public void activeAccountValidator_activeAccount_trueReturned() {

        //Arrange
        String userEmail = "abc@abc.com";
        User user = new User();
        user.setEmail(userEmail);
        Account usersAccount = new Account();
        usersAccount.setUser(user);
        String activeAccountStatusString = "Active";
        AccountStatus usersAccountStatus = new AccountStatus();
        usersAccountStatus.setAccountStatus(activeAccountStatusString);
        usersAccount.setAccountStatus(usersAccountStatus);

        when(accountRepository.findAccountByUserEmail(userEmail)).thenReturn(usersAccount);

        //Act
        boolean result = transactionService.activeAccountValidator(userEmail);

        //Assert
        assertTrue(result);
    }

    @Test
    public void activeAccountValidator_inactiveAccount_falseReturned() {

        //Arrange
        String userEmail = "abc@abc.com";
        User user = new User();
        user.setEmail(userEmail);
        Account usersAccount = new Account();
        usersAccount.setUser(user);
        String activeAccountStatusString = "Inactive";
        AccountStatus usersAccountStatus = new AccountStatus();
        usersAccountStatus.setAccountStatus(activeAccountStatusString);
        usersAccount.setAccountStatus(usersAccountStatus);

        when(accountRepository.findAccountByUserEmail(userEmail)).thenReturn(usersAccount);

        //Act
        boolean result = transactionService.activeAccountValidator(userEmail);

        //Assert
        assertFalse(result);
    }
}
