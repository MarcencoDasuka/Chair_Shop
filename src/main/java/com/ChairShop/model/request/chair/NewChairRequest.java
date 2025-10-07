package com.ChairShop.model.request.chair;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewChairRequest implements Serializable {

    @NotBlank(message = "name Can not be empty")
    private String name;
    @NotBlank(message = "description Can not be empty")
    private String description;
    @NotBlank(message = "category Can not be empty")
    private String category;
    @NotNull(message = "price Can not be empty")
    private Float price;
    @NotNull(message = "stock Can not be empty")
    private Integer stock;
    @NotBlank(message = "material Can not be empty")
    private String material;
    @NotBlank(message = "imageUrl Can not be empty")
    private String imageUrl;



}


