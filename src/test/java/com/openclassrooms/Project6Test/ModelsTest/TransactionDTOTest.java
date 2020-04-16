package com.openclassrooms.Project6Test.ModelsTest;

import com.openclassrooms.Project6Test.Models.TransactionDTO;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TransactionDTOTest {

    private String connectionEmail = "";

    private String description = "";

    private float amount = 1f;

    @Test
    public void parameterizedConstructor() {

        //Act
        TransactionDTO transactionDTO = new TransactionDTO(connectionEmail, description, amount);

        //Assert
        assertEquals(connectionEmail, transactionDTO.getConnectionEmail());
        assertEquals(description, transactionDTO.getDescription());
    }

    @Test
    public void setConnectionEmail() {

        //Arrange
        TransactionDTO transactionDTO = new TransactionDTO();

        //Act
        transactionDTO.setConnectionEmail(connectionEmail);

        //Assert
        assertEquals(connectionEmail, transactionDTO.getConnectionEmail());
    }

    @Test
    public void setDescription() {

        //Arrange
        TransactionDTO transactionDTO = new TransactionDTO();

        //Act
        transactionDTO.setDescription(description);

        //Assert
        assertEquals(description, transactionDTO.getDescription());
    }
}
