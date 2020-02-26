package com.openclassrooms.Project6Test.Repositories;

import com.openclassrooms.Project6Test.Models.UserModificationRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserModificationRegisterRepository extends JpaRepository<UserModificationRegister, Integer> {

    /*UserModificationRegister findUserModificationRegisterById(int userModificationRegisterId);

    List<UserModificationRegister> findUserModificationRegisterByUserEmail(String email);*/
}
