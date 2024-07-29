package com.pichincha.account.controller;

import com.pichincha.account.service.MovementService;
import com.pichincha.account.service.dto.request.MovementRequestDto;
import com.pichincha.account.service.dto.response.AccountResponseDto;
import com.pichincha.account.service.dto.response.BaseResponseDto;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/movements")
public class MovementController {

  final MovementService movementService;

  @PostMapping("")
  public ResponseEntity<BaseResponseDto<Long>> saveAccount(
      @RequestBody @Valid MovementRequestDto movementRequestDto) {
    return movementService.save(movementRequestDto);
  }

  @PutMapping("/{id}")
  public ResponseEntity<BaseResponseDto<AccountResponseDto>> updateAccount(
      @RequestBody @Valid MovementRequestDto movementRequestDto,@PathVariable Long id) {
    return movementService.update(movementRequestDto,id);
  }

  @PatchMapping("/{identification}")
  public ResponseEntity<BaseResponseDto<AccountResponseDto>> editAccount(
      @RequestBody Map<String, Object> MovementRequestDto, @PathVariable Long identification) {
    return movementService.edit(MovementRequestDto, identification);
  }

  @DeleteMapping("/{identification}")
  public ResponseEntity<BaseResponseDto<AccountResponseDto>> deleteAccount(
      @PathVariable Long identification) {
    return movementService.delete(identification);
  }
}