package com.pichincha.account.controller;


import com.pichincha.account.service.AccountService;
import com.pichincha.account.service.dto.request.AccountRequestDto;
import com.pichincha.account.service.dto.request.AccountUpdateRequestDto;
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
@RequestMapping("api/account")
public class AccountController {

  final AccountService accountService;

  @PostMapping("")
  public ResponseEntity<BaseResponseDto<AccountResponseDto>> saveAccount(
      @RequestBody @Valid AccountRequestDto accountRequestDto) {
    return accountService.save(accountRequestDto);
  }

  @PutMapping("")
  public ResponseEntity<BaseResponseDto<AccountResponseDto>> updateAccount(
      @RequestBody @Valid AccountUpdateRequestDto accountRequestDto) {
    return accountService.update(accountRequestDto);
  }

  @PatchMapping("/{accountNumber}")
  public ResponseEntity<BaseResponseDto<AccountResponseDto>> editAccount(
      @RequestBody Map<String, Object> accountRequestDto, @PathVariable String accountNumber) {
    return accountService.edit(accountRequestDto, accountNumber);
  }


  @DeleteMapping("/{identification}")
  public ResponseEntity<BaseResponseDto<AccountResponseDto>> deleteAccount(
      @PathVariable String identification) {
    return accountService.delete(identification);
  }
}