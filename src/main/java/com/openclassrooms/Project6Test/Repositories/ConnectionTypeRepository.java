package com.openclassrooms.Project6Test.Repositories;

import com.openclassrooms.Project6Test.Models.ConnectionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectionTypeRepository extends JpaRepository<ConnectionType, Integer> {

    /*ConnectionType findConnectionTypeById(int id);

    ConnectionType findConnectionTypeByConnectionType(String connectiontype);*/
}
