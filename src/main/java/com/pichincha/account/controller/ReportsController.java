package com.pichincha.account.controller;

import com.pichincha.account.service.MovementService;
import com.pichincha.account.service.dto.response.BaseResponseDto;
import com.pichincha.account.service.dto.response.MovementResponseDto;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/reports")
public class ReportsController {

  final MovementService movementService;

  @GetMapping("")
  public ResponseEntity<BaseResponseDto<List<MovementResponseDto>>> getReportMovements(
      @RequestParam(name = "dateInit") LocalDate dateInit
      , @RequestParam(name = "dateEnd") LocalDate dateEnd
      , @RequestParam(name = "customerId") String customerId) {
    return movementService.getReportByDate(dateInit, dateEnd, customerId);
  }
}