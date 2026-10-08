package com.ChairShop.model.dto.Shopping_cart;

import com.ChairShop.model.dto.Cart_item.CartItemDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartDTO implements Serializable {
    private Integer id;
    private List<CartItemDTO> items;
}
