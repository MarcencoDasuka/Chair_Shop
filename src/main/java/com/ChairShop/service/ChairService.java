package com.ChairShop.service;


import com.ChairShop.model.dto.chair.ChairDTO;
import com.ChairShop.model.request.chair.NewChairRequest;
import com.ChairShop.model.request.chair.UpdateChairRequest;
import com.ChairShop.model.response.IamResponse;
import jakarta.validation.constraints.NotNull;

import java.lang.annotation.IncompleteAnnotationException;

public interface ChairService {
    IamResponse<ChairDTO> getById (@NotNull Integer chairId);

    IamResponse<ChairDTO> createChair (@NotNull NewChairRequest newChairRequest);

    IamResponse<ChairDTO> updateChair (@NotNull Integer id, @NotNull UpdateChairRequest updateChairRequest);

    void softDeleteChair (@NotNull Integer id);
}
