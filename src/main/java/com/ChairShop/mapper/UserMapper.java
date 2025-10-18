package com.ChairShop.mapper;

import com.ChairShop.model.dto.Cart_item.CartItemDTO;
import com.ChairShop.model.dto.Shopping_cart.ShoppingCartDTO;
import com.ChairShop.model.dto.User.FullUserDTO;
import com.ChairShop.model.dto.User.UserDTO;
import com.ChairShop.model.enteties.ShoppingCart;
import com.ChairShop.model.enteties.User;
import com.ChairShop.model.enums.RegistrationStatus;
import com.ChairShop.model.request.user.NewUserRequest;
import org.hibernate.type.descriptor.DateTimeUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.Objects;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        imports = {RegistrationStatus.class, Object.class}
)
public interface UserMapper {

    @Mapping(source = "last_login", target = "lastLogin")
    @Mapping(target = "shoppingCart", expression = "java(mapShoppingCart(user.getShoppingCart()))")
    FullUserDTO toFullDTO(User user);

    // --- метод для маппинга корзины ---
    default ShoppingCartDTO mapShoppingCart(ShoppingCart cart) {
        if (cart == null) return null;

        ShoppingCartDTO cartDTO = new ShoppingCartDTO();
        cartDTO.setId(cart.getId());

        cartDTO.setItems(
                cart.getItems().stream().map(item -> {
                    CartItemDTO itemDTO = new CartItemDTO();
                    itemDTO.setId(item.getId());
                    itemDTO.setName(item.getChair().getName());
                    itemDTO.setBrand(item.getChair().getBrand());
                    itemDTO.setType(item.getChair().getType());
                    itemDTO.setPrice(item.getChair().getPrice().floatValue());
                    itemDTO.setDescription(item.getChair().getDescription());
                    itemDTO.setImageUrl(item.getChair().getImageUrl());
                    itemDTO.setQuantity(item.getQuantity());
                    return itemDTO;
                }).toList()
        );

        return cartDTO;
    }



    @Mapping(source = "last_login", target = "lastLogin")
    UserDTO toDTO(User user);



    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "registrationStatus", expression = "java(RegistrationStatus.ACTIVE)")
    User createUser(NewUserRequest newUserRequest);


}
