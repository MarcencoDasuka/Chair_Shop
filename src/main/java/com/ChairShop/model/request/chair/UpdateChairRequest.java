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
public class UpdateChairRequest implements Serializable {

    @NotBlank(message = "name Can not be empty")
    private String name;

    @NotBlank(message = "description Can not be empty")
    private String description;

    @NotNull(message = "price Can not be empty")
    private Float price;

    @NotNull(message = "stock Can not be empty")
    private Integer stock;

    @NotBlank(message = "imageUrl Can not be empty")
    private String imageUrl;

}


