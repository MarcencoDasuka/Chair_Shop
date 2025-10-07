package com.ChairShop.service.impl;

import com.ChairShop.mapper.ChairMapper;
import com.ChairShop.model.constants.ApiErrorMessage;
import com.ChairShop.model.dto.chair.ChairDTO;
import com.ChairShop.model.enteties.Chair;
import com.ChairShop.model.exception.DataExistException;
import com.ChairShop.model.exception.NotFoundException;
import com.ChairShop.model.request.chair.NewChairRequest;
import com.ChairShop.model.request.chair.UpdateChairRequest;
import com.ChairShop.model.response.IamResponse;
import com.ChairShop.repositories.ChairRepository;
import com.ChairShop.service.ChairService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.lang.annotation.IncompleteAnnotationException;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class ChairServiceImpl implements ChairService{

    private final ChairRepository chairRepository;
    private final ChairMapper chairMapper;
    private final CharacterEncodingFilter characterEncodingFilter;

    @Override
    public IamResponse<ChairDTO> getById(@NotNull Integer chairId) {
        Chair chair = chairRepository.findByIdAndDeletedFalse(chairId)
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
    public IamResponse<ChairDTO> createChair(@NotNull NewChairRequest newChairRequest) {
        if (chairRepository.existsByName(newChairRequest.getName())){
            throw new DataExistException(ApiErrorMessage.CHAIR_WITH_NAME_ALREADY_EXISTS.getMessage(newChairRequest.getName()));
        }

        Chair chair = chairMapper.createChair(newChairRequest);
        Chair saveChair = chairRepository.save(chair);
        ChairDTO chairDTO = chairMapper.toChairDTO(chair);

        return IamResponse.createSuccessful(chairDTO);
    }

    @Override
    public IamResponse<ChairDTO> updateChair(@NotNull Integer id, @NotNull UpdateChairRequest request) {
        Chair chair = chairRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(()->new NotFoundException(ApiErrorMessage.CHAIR_WITH_ID_NOT_FOUND.getMessage(id)));
        chairMapper.updateChair(chair,request);
        chair.setUpdatedAt(LocalDateTime.now());
        chair = chairRepository.save(chair);

        ChairDTO chairDTO = chairMapper.toChairDTO(chair);
        return IamResponse.createSuccessful(chairDTO);
    }

    @Override
    public void softDeleteChair(Integer id) {
        Chair chair = chairRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(()->new NotFoundException(ApiErrorMessage.CHAIR_WITH_ID_NOT_FOUND.getMessage(id)));
        chair.setDeleted(true);
        chairRepository.save(chair);
    }


}
