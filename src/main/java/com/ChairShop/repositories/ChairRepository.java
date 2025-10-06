package com.ChairShop.repositories;


import com.ChairShop.model.enteties.Chair;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChairRepository extends JpaRepository<Chair, Integer> {

}
