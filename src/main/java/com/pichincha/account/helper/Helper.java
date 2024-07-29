package com.pichincha.account.helper;



import com.pichincha.account.service.dto.response.BaseResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Helper {

  public static <T>ResponseEntity<BaseResponseDto<T>> buildResponseDto(T data,
      HttpStatus httpStatus) {
    return new ResponseEntity<>(BaseResponseDto.<T>builder()
        .code(httpStatus.value())
        .status(httpStatus.getReasonPhrase())
        .data(data)
        .build(), httpStatus);
  }
}