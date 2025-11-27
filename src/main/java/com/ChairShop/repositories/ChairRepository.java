package com.ChairShop.repositories;


import com.ChairShop.model.enteties.Chair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ChairRepository extends JpaRepository<Chair, Integer>, JpaSpecificationExecutor<Chair> {


    boolean  existsByName(String name);


    Optional<Chair> findByIdAndDeletedFalse(Integer id);

}
