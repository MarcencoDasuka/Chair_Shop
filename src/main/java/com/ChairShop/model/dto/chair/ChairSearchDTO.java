package com.ChairShop.model.dto.chair;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ChairSearchDTO implements Serializable {

    public Integer id;

    public String name;

    public String description;

    public String category;

    public Float price;

    public Integer stock;

    public String material;

    public String imageUrl;

    public LocalDateTime createdAt;

    private boolean deleted;
}
