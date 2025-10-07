package com.ChairShop.service.impl;

import com.ChairShop.mapper.ChairMapper;
import com.ChairShop.model.constants.ApiErrorMessage;
import com.ChairShop.model.dto.chair.ChairDTO;
import com.ChairShop.model.enteties.Chair;
import com.ChairShop.model.exception.NotFoundException;
import com.ChairShop.model.request.chair.ChairRequest;
import com.ChairShop.model.response.IamResponse;
import com.ChairShop.repositories.ChairRepository;
import com.ChairShop.service.ChairService;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChairServiceImpl implements ChairService{

    private final ChairRepository chairRepository;
    private final ChairMapper chairMapper;

    @Override
    public IamResponse<ChairDTO> getById(@NotNull Integer chairId) {
        Chair chair = chairRepository.findById(chairId)
                .orElseThrow(()->new NotFoundException(ApiErrorMessage.CHAIR_WITH_ID_NOT_FOUND.getMessage(chairId)));


//        ChairDTO chairDTO = ChairDTO.builder()
//                .id(chair.getId())
//                .price(chair.getPrice())
//                .stock(chair.getStock())
//                .name(chair.getName())
//                .description(chair.getDescription())
//                .category(chair.getCategory())
//                .material(chair.getMaterial())
//                .createdAt(chair.getCreatedAt())
//                .imageUrl(chair.getImageUrl())
//                .updatedAt(chair.getUpdatedAt())
//                .build();

        return IamResponse.createSuccessful(chairMapper.toChairDTO(chair));
    }

    @Override
    public IamResponse<ChairDTO> createChair(@NotNull ChairRequest chairRequest) {
        Chair chair = chairMapper.createChair(chairRequest);
        Chair saveChair = chairRepository.save(chair);
        ChairDTO chairDTO = chairMapper.toChairDTO(chair);

        return IamResponse.createSuccessful(chairDTO);
    }
}
