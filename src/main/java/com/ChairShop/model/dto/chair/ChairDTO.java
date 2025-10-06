package com.ChairShop.model.dto.chair;


import com.ChairShop.service.ChairService;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;



@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChairDTO implements Serializable {


    public Integer id;

    public String name;

    public String description;

    public String category;

    public Float price;

    public Integer stock;

    public String material;

    public String imageUrl;

    public LocalDateTime createdAt = LocalDateTime.now();

    public LocalDateTime updatedAt = LocalDateTime.now();
}
