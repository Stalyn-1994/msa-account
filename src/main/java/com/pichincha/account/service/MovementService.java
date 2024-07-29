package com.pichincha.account.service;

import com.pichincha.account.service.dto.request.MovementRequestDto;
import com.pichincha.account.service.dto.response.AccountResponseDto;
import com.pichincha.account.service.dto.response.BaseResponseDto;
import com.pichincha.account.service.dto.response.MovementResponseDto;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;

public interface MovementService {


  ResponseEntity<BaseResponseDto<Long>> save(MovementRequestDto movementRequestDto);

  ResponseEntity<BaseResponseDto<List<MovementResponseDto>>> getReportByDate(LocalDate initDate, LocalDate endDate, String customer);

  ResponseEntity<BaseResponseDto<AccountResponseDto>> update(MovementRequestDto movementRequestDto,Long id);

  ResponseEntity<BaseResponseDto<AccountResponseDto>> edit(Map<String, Object> customerDto, Long identification);

  ResponseEntity<BaseResponseDto<AccountResponseDto>> delete(Long identification);

}
