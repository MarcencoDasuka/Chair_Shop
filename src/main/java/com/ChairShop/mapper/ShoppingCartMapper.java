package com.ChairShop.mapper;

import com.ChairShop.model.dto.Shopping_cart.ShoppingCartDTO;
import com.ChairShop.model.enteties.ShoppingCart;

public interface ShoppingCartMapper {

    public ShoppingCartDTO toShoppingCartDTO(ShoppingCart shoppingCart);
}
