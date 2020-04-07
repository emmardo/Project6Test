package com.openclassrooms.Project6Test.Repositories;

import com.openclassrooms.Project6Test.Models.Iban;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IbanRepository extends JpaRepository<Iban, Integer> {

    List<Iban> findByAccount_UserEmail(String email);
}
