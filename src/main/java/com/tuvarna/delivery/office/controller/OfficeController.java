package com.tuvarna.delivery.office.controller;

import com.tuvarna.delivery.office.payload.request.CourierRequestDTO;
import com.tuvarna.delivery.office.service.OfficeService;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/offices")
public class OfficeController {
    private final OfficeService officeService;


    @PostMapping("{officeId}/couriers")
    public ResponseEntity<CourierRequestDTO> addCourierToOffice(@Schema(example = "1") @PathVariable Long officeId,
                                                                @RequestBody @Valid CourierRequestDTO requestDTO) {
        return new ResponseEntity<>(officeService.enlistCourierToOffice(officeId, requestDTO), HttpStatus.CREATED);
    }
}
