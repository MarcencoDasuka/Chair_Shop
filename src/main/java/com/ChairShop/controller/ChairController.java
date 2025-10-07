package com.ChairShop.controller;


import com.ChairShop.model.constants.ApiLogMessage;
import com.ChairShop.model.dto.chair.ChairDTO;
import com.ChairShop.model.request.chair.NewChairRequest;
import com.ChairShop.model.request.chair.UpdateChairRequest;
import com.ChairShop.model.response.IamResponse;
import com.ChairShop.service.ChairService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.sql.model.jdbc.UpsertOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/chair")
public class ChairController {


    private final ChairService chairService;

    @GetMapping("/{id}")
    public ResponseEntity<IamResponse<ChairDTO>> getChairById(
            @PathVariable Integer id){
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiLogMessage.CHAIR_INFO_BY_ID);
        IamResponse<ChairDTO> response = chairService.getById(id);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<IamResponse<ChairDTO>> createChair(
            @RequestBody @Valid NewChairRequest newChairRequest) {
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiLogMessage.CHAIR_INFO_BY_ID);
        IamResponse<ChairDTO> response = chairService.createChair(newChairRequest);

        return ResponseEntity.ok(response);
    }


    @PutMapping ("/{id}")
    public ResponseEntity<IamResponse<ChairDTO>> updateById(
            @PathVariable Integer id, @RequestBody @Valid UpdateChairRequest request){
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiLogMessage.CHAIR_INFO_BY_ID);
        IamResponse<ChairDTO> response = chairService.updateChair(id,request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeletedById(@PathVariable(name = "id") Integer chairId){
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiLogMessage.CHAIR_INFO_BY_ID);

        chairService.softDeleteChair(chairId);
        return ResponseEntity.ok().build();
    }

}
