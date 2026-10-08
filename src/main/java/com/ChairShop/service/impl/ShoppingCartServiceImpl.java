package com.ChairShop.service.impl;

import com.ChairShop.mapper.Impl.ShoppingCartMapperImpl;
import com.ChairShop.mapper.ShoppingCartMapper;
import com.ChairShop.model.constants.ApiErrorMessage;
import com.ChairShop.model.dto.Shopping_cart.ShoppingCartDTO;
import com.ChairShop.model.enteties.Chair;
import com.ChairShop.model.enteties.CartItem;
import com.ChairShop.model.enteties.ShoppingCart;
import com.ChairShop.model.enteties.User;
import com.ChairShop.model.exception.NotFoundException;
import com.ChairShop.model.request.shopping_cart.AddToShoppingCart;
import com.ChairShop.model.response.IamResponse;
import com.ChairShop.repositories.ChairRepository;
import com.ChairShop.repositories.CartItemRepository;
import com.ChairShop.repositories.ShoppingCartRepository;
import com.ChairShop.repositories.UserRepository;
import com.ChairShop.service.ShoppingCartService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final ChairRepository chairRepository;
    private final CartItemRepository cartItemRepository;
    private final ShoppingCartMapper shoppingCartMapper;

    @Override
    public IamResponse<ShoppingCartDTO> addToCart(@NotNull AddToShoppingCart request) {
        // - проверка существует ли пользователь
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.USER_WITH_ID_NOT_FOUND.getMessage(request.getUserId())));

        // - проверка существует ли велосипед
        Chair bicycle = chairRepository.findByIdAndDeletedFalse(request.getChairId())
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.CHAIR_WITH_ID_NOT_FOUND.getMessage(request.getChairId())));

        // - проверка есть ли у пользователя корзина
        ShoppingCart cart = user.getShoppingCart();
        if(cart == null) {
            throw new NotFoundException(ApiErrorMessage.CART_WITH_USER_ID_NOT_FOUND.getMessage(user.getId()));
//            cart = new ShoppingCart(); // - если нету, то создаем и присваиваем пользователю
//            cart.setUser(user); // - привязываем текущего пользователя к корзине
//            user.setShoppingCart(cart);  // - привязываем созданную корзину к текущему пользователю
//            shoppingCartRepository.save(cart);// - сохраняем ее в базу данных
//            userRepository.save(user); // - сохраняем измененного пользователя в базу данных
        }

        // - проверяем есть ли уже этот велик в корзине
        CartItem existingItem = cart.getItems().stream() // - получаем список великов
                .filter(item -> item.getChair().getId().equals(request.getChairId())) // - сравниваем все айдишники великов с карзины с тем который ищем
                .findFirst() // - первое совпадение будет присвоено к existingItem
                .orElse(null); // - если не найдется то existingItem = null (в корзине нету этого велика)

        // - если велик все таки был в корзине
        if(existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + request.getQuantity()); // - увеличиваем количество на сколько указали в параметре
            cartItemRepository.save(existingItem); // - сохраняем изменения в базу данных
        } else { // - если этого велика в корзине не было
            CartItem newItem = new CartItem(); // - создаем новый заказ для корзины и присваиваем значения
            newItem.setCart(cart);
            newItem.setQuantity(request.getQuantity());
            newItem.setChair(bicycle);

            cart.getItems().add(newItem); // - добавляем новый заказ в список корзины

            cartItemRepository.save(newItem);
        }

        ShoppingCartDTO response = shoppingCartMapper.toShoppingCartDTO(cart);
        return IamResponse.createSuccessful(response);
    }



    @Override
    public IamResponse<ShoppingCartDTO> getCartByUserId(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.USER_WITH_ID_NOT_FOUND.getMessage(userId)));


        ShoppingCart cart = user.getShoppingCart();
        // - проверка не нужна - но это для уверенности
        if(cart == null) {
            throw new NotFoundException(ApiErrorMessage.CART_WITH_USER_ID_NOT_FOUND.getMessage(userId));
            // - проверка не нужна так как пользователь сразу создается с корзиной
//            cart = new ShoppingCart();
//            cart.setUser(user);
//            user.setShoppingCart(cart);
//            shoppingCartRepository.save(cart);
//            userRepository.save(user);
        }

        ShoppingCartDTO response = shoppingCartMapper.toShoppingCartDTO(cart);

        return IamResponse.createSuccessful(response);
    }

    @Override
    public void clearCartByUserId(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.USER_WITH_ID_NOT_FOUND.getMessage(userId)));

        ShoppingCart cart = user.getShoppingCart();
        if(cart == null) throw new NotFoundException(ApiErrorMessage.CART_WITH_USER_ID_NOT_FOUND.getMessage(userId));

        cart.getItems().clear();
        shoppingCartRepository.save(cart);
    }
}
