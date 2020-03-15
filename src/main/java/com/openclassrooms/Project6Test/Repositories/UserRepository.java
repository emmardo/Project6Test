package com.openclassrooms.Project6Test.Repositories;

import com.openclassrooms.Project6Test.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findUserByEmail (String userEmail);
}
