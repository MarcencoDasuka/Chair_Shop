package com.ChairShop.service;


import com.ChairShop.model.dto.chair.ChairDTO;
import com.ChairShop.model.response.IamResponse;
import jakarta.validation.constraints.NotNull;

public interface ChairService {
    IamResponse<ChairDTO> getById(@NotNull Integer chairId);


}
