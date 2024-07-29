package com.pichincha.account.service;


import com.pichincha.account.service.dto.request.AccountRequestDto;
import com.pichincha.account.service.dto.request.AccountUpdateRequestDto;
import com.pichincha.account.service.dto.response.AccountResponseDto;
import com.pichincha.account.service.dto.response.BaseResponseDto;
import java.util.Map;
import org.springframework.http.ResponseEntity;

public interface AccountService {


  ResponseEntity<BaseResponseDto<AccountResponseDto>> save(AccountRequestDto accountResponseDto);


  ResponseEntity<BaseResponseDto<AccountResponseDto>> update(
      AccountUpdateRequestDto accountResponseDto);


  ResponseEntity<BaseResponseDto<AccountResponseDto>> edit(Map<String, Object> customerDto, String identification);

  ResponseEntity<BaseResponseDto<AccountResponseDto>> delete(String identification);

}
