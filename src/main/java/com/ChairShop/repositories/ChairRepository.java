package com.ChairShop.repositories;


import com.ChairShop.model.enteties.Chair;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChairRepository extends JpaRepository<Chair, Integer> {


    boolean  existsByName(String name);


    Optional<Chair> findByIdAndDeletedFalse(Integer id);
}
