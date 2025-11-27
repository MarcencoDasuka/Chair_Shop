package com.ChairShop.mapper.Impl;

import com.ChairShop.mapper.ShoppingCartMapper;
import com.ChairShop.model.dto.Cart_item.CartItemDTO;
import com.ChairShop.model.dto.Shopping_cart.ShoppingCartDTO;
import com.ChairShop.model.enteties.ShoppingCart;
import org.springframework.stereotype.Component;

@Component
public class ShoppingCartMapperImpl implements ShoppingCartMapper {

    public ShoppingCartDTO toShoppingCartDTO(ShoppingCart shoppingCart) {
        ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO();
        shoppingCartDTO.setId(shoppingCart.getId());
        shoppingCartDTO.setItems(shoppingCart.getItems().stream().map(item ->{
            CartItemDTO cartItemDTO = new CartItemDTO();
            cartItemDTO.setId(item.getId());
            cartItemDTO.setQuantity(item.getQuantity());
            cartItemDTO.setName(item.getChair().getName());
            cartItemDTO.setPrice(item.getChair().getPrice());
            cartItemDTO.setDescription(item.getChair().getDescription());
            cartItemDTO.setImageUrl(item.getChair().getImageUrl());
            return cartItemDTO;
        }).toList());
        return shoppingCartDTO;
    }

}
