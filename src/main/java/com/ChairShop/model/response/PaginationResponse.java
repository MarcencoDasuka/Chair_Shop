package com.ChairShop.model.response;


import com.ChairShop.model.dto.chair.ChairSearchDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationResponse<T> implements Serializable {

    public List<T> content;
    private Pagination pagination;

    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Pagination implements Serializable{
        private long total;
        private int limit;
        private int page;
        private int pages;
    }
}
