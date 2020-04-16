package com.openclassrooms.Project6Test.ModelsTest;

import com.openclassrooms.Project6Test.Models.WithdrawalDTO;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WithdrawalDTOTest {

    private float amount = 1f;

    private String account = "";

    @Test
    public void parameterizedConstructor() {

        //Act
        WithdrawalDTO withdrawalDTO = new WithdrawalDTO(amount, account);

        //Assert
        assertEquals(account, withdrawalDTO.getAccount());
    }

    @Test
    public void setAccount() {

        //Arrange
        WithdrawalDTO withdrawalDTO = new WithdrawalDTO();

        //Act
        withdrawalDTO.setAccount(account);

        //Assert
        assertEquals(account, withdrawalDTO.getAccount());
    }
}
