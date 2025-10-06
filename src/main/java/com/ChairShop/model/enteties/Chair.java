package com.ChairShop.model.enteties;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;



import java.text.DecimalFormat;
import java.time.LocalDateTime;

@Entity
@Table (name = "chairs")
@Getter
@Setter
public class Chair {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    public Integer id;

    @Column (nullable = false)
    public String name;

    @Column (nullable = false)
    public String description;

    @Column (nullable = false)
    public String category;

    @Column (nullable = false)
    public DecimalFormat price;

    @Column (nullable = false)
    public Integer stock;

    @Column (nullable = false)
    public String material;

    @Column (name = "image_URL", nullable = false)
    public String imageUrl;

    @Column (name = "created_at", nullable = false)
    public LocalDateTime createdAt = LocalDateTime.now();

    @Column (name = "updated_at", nullable = false)
    public LocalDateTime updatedAt = LocalDateTime.now();

}
