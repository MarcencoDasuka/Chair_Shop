package com.ChairShop.service;


import com.ChairShop.model.dto.chair.ChairDTO;
import com.ChairShop.model.dto.chair.ChairSearchDTO;
import com.ChairShop.model.request.chair.ChairSearchRequest;
import com.ChairShop.model.request.chair.NewChairRequest;
import com.ChairShop.model.request.chair.UpdateChairRequest;
import com.ChairShop.model.response.IamResponse;
import com.ChairShop.model.response.PaginationResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;

import java.lang.annotation.IncompleteAnnotationException;

public interface ChairService {
    IamResponse<ChairDTO> getById (@NotNull Integer chairId);

    IamResponse<ChairDTO> createChair (@NotNull NewChairRequest newChairRequest, String userName);

    IamResponse<ChairDTO> updateChair (@NotNull Integer id, @NotNull UpdateChairRequest updateChairRequest);

    void softDeleteChair (@NotNull Integer id);

    IamResponse<PaginationResponse<ChairSearchDTO>> findAllChairs(Pageable pageable);

    IamResponse<PaginationResponse<ChairSearchDTO>> searchChair (@NotNull ChairSearchRequest chairSearchRequest, Pageable pageable);

}
