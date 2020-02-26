package com.openclassrooms.Project6Test.Repositories;

import com.openclassrooms.Project6Test.Models.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountTypeRepository extends JpaRepository<AccountType, Integer> {

    /*AccountType findAccountTypeById(int id);

    AccountType findAccountTypeByAccountType(String accountType);*/
}
