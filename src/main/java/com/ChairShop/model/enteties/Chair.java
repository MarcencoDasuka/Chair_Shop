package com.ChairShop.model.enteties;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.decimal.DecimalMinValidatorForFloat;


import java.text.DecimalFormat;
import java.time.LocalDateTime;

@Entity
@Table (name = "chairs")
@Getter
@Setter
public class Chair {

    public static final String ID_FIELD ="id";
    public static final String NAME_FIELD ="name";
    public static final String PRICE_FIELD ="price";
    public static final String DELETED_FIELD ="deleted";

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    public Integer id;

    @Column (nullable = false, unique = true)
    public String name;

    @Column (nullable = false)
    public String description;

    @Column (nullable = false)
    public String category;

    @Column(nullable = false)
    public String brand;

    @Column(nullable = false)
    public String type;

    @Column (nullable = false)
    public Float price;

    @Column (nullable = false)
    public Integer stock;

    @Column (nullable = false)
    public String material;

    @Column (name = "image_url", nullable = false)
    public String imageUrl;

    @Column (name = "created_at", nullable = false, updatable = false)
    public LocalDateTime createdAt = LocalDateTime.now();

    @Column (name = "updated_at", nullable = false)
    public LocalDateTime updatedAt = LocalDateTime.now();

    @Column(nullable = false)
    public boolean deleted = false;

}
