package com.openclassrooms.Project6Test.Repositories;

import com.openclassrooms.Project6Test.Models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findTransactionsByConnectionUserEmail(String userEmail);
}
