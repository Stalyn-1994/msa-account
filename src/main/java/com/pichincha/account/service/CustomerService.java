package com.pichincha.account.service;

import com.pichincha.account.service.dto.response.BaseResponseDto;
import com.pichincha.account.service.dto.response.CustomerResponseLegacyDto;

public interface CustomerService {

  BaseResponseDto<CustomerResponseLegacyDto> getCustomerResponseLegacyDtoBaseResponseDto(String customerId);

}
