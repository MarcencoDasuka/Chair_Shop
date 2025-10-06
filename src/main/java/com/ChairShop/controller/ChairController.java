package com.ChairShop.controller;


import com.ChairShop.mapper.ChairMapper;
import com.ChairShop.model.constants.ApiErrorMessage;
import com.ChairShop.model.constants.ApiLogMessage;
import com.ChairShop.model.dto.chair.ChairDTO;
import com.ChairShop.model.enteties.Chair;
import com.ChairShop.model.response.IamResponse;
import com.ChairShop.repositories.ChairRepository;
import com.ChairShop.service.ChairService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/chair")
public class ChairController {


    private final ChairService chairService;

    @GetMapping("/{id}")
    public ResponseEntity<IamResponse<ChairDTO>> getChairById(@PathVariable Integer id){
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiLogMessage.CHAIR_INFO_BY_ID);

        IamResponse<ChairDTO> response = chairService.getById(id);
        return ResponseEntity.ok(response);
    }

}
