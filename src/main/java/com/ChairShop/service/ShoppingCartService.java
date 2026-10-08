package com.ChairShop.service;

import com.ChairShop.model.dto.Shopping_cart.ShoppingCartDTO;
import com.ChairShop.model.request.shopping_cart.AddToShoppingCart;
import com.ChairShop.model.response.IamResponse;
import jakarta.validation.constraints.NotNull;

public interface ShoppingCartService {

    IamResponse<ShoppingCartDTO> addToCart(@NotNull AddToShoppingCart request);
    IamResponse<ShoppingCartDTO> getCartByUserId(@NotNull Integer userId);
    void clearCartByUserId(@NotNull Integer userId);

}
