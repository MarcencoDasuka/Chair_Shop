package com.ChairShop.model.dto.Cart_item;

import com.ChairShop.model.enteties.Chair;
import com.ChairShop.model.enteties.ShoppingCart;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO implements Serializable {
    private Integer id;
    private String name;
    private String brand;
    private String type;
    private Float price;
    private String description;
    private String imageUrl;
    private Integer quantity;
}
