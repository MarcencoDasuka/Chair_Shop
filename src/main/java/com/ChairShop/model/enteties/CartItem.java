package com.ChairShop.model.enteties;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "cart_items",
        uniqueConstraints = @UniqueConstraint(columnNames = {"cart_id", "chair_id"}))
@Getter
@Setter
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_id", nullable = false)
    private ShoppingCart cart;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "chair_id", nullable = false)
    private Chair chair;

    @Column(nullable = false)
    private Integer quantity = 1;
}

