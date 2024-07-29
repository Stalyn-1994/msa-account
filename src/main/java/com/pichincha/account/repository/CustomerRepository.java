package com.pichincha.account.repository;


import com.pichincha.account.service.dto.response.BaseResponseDto;
import com.pichincha.account.service.dto.response.CustomerResponseLegacyDto;

public interface CustomerRepository {

  BaseResponseDto<CustomerResponseLegacyDto> getCustomerResponseLegacyDtoBaseResponseDto(
      String customerId);

}
