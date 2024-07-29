package com.pichincha.account.service.imp;

import static com.pichincha.account.util.Constants.CUSTOMER_NOT_ACTIVE;
import static com.pichincha.account.util.Constants.NOT_FOUND;

import com.pichincha.account.domain.exception.GenericException;
import com.pichincha.account.repository.CustomerRepository;
import com.pichincha.account.service.CustomerService;
import com.pichincha.account.service.dto.response.BaseResponseDto;
import com.pichincha.account.service.dto.response.CustomerResponseLegacyDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

  final CustomerRepository customerRepository;

  @Override
  public BaseResponseDto<CustomerResponseLegacyDto> getCustomerResponseLegacyDtoBaseResponseDto(
      String customerId) {
    BaseResponseDto<CustomerResponseLegacyDto> baseResponseDto = customerRepository.getCustomerResponseLegacyDtoBaseResponseDto(
        customerId);
    if (baseResponseDto.getStatus().equals(HttpStatus.OK.getReasonPhrase())) {
      if (baseResponseDto.getData().getState()) {
        return baseResponseDto;
      }
      throw new GenericException(HttpStatus.OK, CUSTOMER_NOT_ACTIVE);
    }
    throw new GenericException(HttpStatus.NOT_FOUND, NOT_FOUND);
  }
}
