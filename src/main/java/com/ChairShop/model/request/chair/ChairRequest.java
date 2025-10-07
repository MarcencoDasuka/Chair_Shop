package com.ChairShop.model.request.chair;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChairRequest implements Serializable {
    
    private String name;

    private String description;

    private String category;

    private Float price;

    private Integer stock;

    private String material;

    private String imageUrl;



}


