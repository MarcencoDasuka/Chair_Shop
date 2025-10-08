package com.ChairShop.controller;


import com.ChairShop.model.constants.ApiLogMessage;
import com.ChairShop.model.dto.chair.ChairDTO;
import com.ChairShop.model.dto.chair.ChairSearchDTO;
import com.ChairShop.model.request.chair.ChairSearchRequest;
import com.ChairShop.model.request.chair.NewChairRequest;
import com.ChairShop.model.request.chair.UpdateChairRequest;
import com.ChairShop.model.response.IamResponse;
import com.ChairShop.model.response.PaginationResponse;
import com.ChairShop.service.ChairService;
import com.ChairShop.utils.ApiUtils;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.sql.model.jdbc.UpsertOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        IamResponse<ChairDTO> response = chairService.getById(id);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<IamResponse<ChairDTO>> createChair(
            @RequestBody @Valid NewChairRequest newChairRequest) {
    log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        IamResponse<ChairDTO> response = chairService.createChair(newChairRequest);

        return ResponseEntity.ok(response);
    }


    @PutMapping ("/{id}")
    public ResponseEntity<IamResponse<ChairDTO>> updateById(
            @PathVariable Integer id, @RequestBody @Valid UpdateChairRequest request){
    log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        IamResponse<ChairDTO> response = chairService.updateChair(id,request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeletedById(@PathVariable(name = "id") Integer chairId){
    log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());


        chairService.softDeleteChair(chairId);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/all")
    public ResponseEntity<IamResponse<PaginationResponse<ChairSearchDTO>>> getAllChairs(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "limit", defaultValue = "3") int limit
    ){
    log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());
        Pageable pageable = PageRequest.of(page, limit);
        IamResponse<PaginationResponse<ChairSearchDTO>> response = chairService.findAllChairs(pageable);
        return ResponseEntity.ok(response);
    }

    @PostMapping ("/search")
    public ResponseEntity<IamResponse<PaginationResponse<ChairSearchDTO>>> searchChair(
            @RequestBody @Valid ChairSearchRequest request,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "limit", defaultValue = "3") int limit
            ){
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());
        Pageable pageable = PageRequest.of(page, limit);
        IamResponse<PaginationResponse<ChairSearchDTO>> response = chairService.searchChair(request, pageable);
        return ResponseEntity.ok(response);
    }

}
