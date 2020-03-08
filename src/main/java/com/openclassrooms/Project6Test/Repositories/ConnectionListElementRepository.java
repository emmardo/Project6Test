package com.openclassrooms.Project6Test.Repositories;

import com.openclassrooms.Project6Test.Models.ConnectionListElement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConnectionListElementRepository extends JpaRepository<ConnectionListElement, Integer> {

    List<ConnectionListElement> findConnectionListElementsByAccountUserEmail(String userEmail);

    ConnectionListElement findConnectionListElementByConnectionUserEmail(String connectionEmail);
}
