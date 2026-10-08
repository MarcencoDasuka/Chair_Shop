package com.ChairShop.model.request.chair;


import com.ChairShop.model.enums.ChairSortField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.io.Serializable;

@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class ChairSearchRequest implements Serializable {
    private String name;
    private Integer price;

    private Boolean deleted;
    private String keyword;
    private ChairSortField chairSortField;

}
