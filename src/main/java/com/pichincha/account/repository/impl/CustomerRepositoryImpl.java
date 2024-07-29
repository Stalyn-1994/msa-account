package com.pichincha.account.repository.impl;


import static com.pichincha.account.util.Constants.NOT_FOUND;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pichincha.account.domain.exception.GenericException;
import com.pichincha.account.domain.exception.InternalErrorException;
import com.pichincha.account.helper.ApiUtilsHelper;
import com.pichincha.account.repository.CustomerRepository;
import com.pichincha.account.service.dto.response.BaseResponseDto;
import com.pichincha.account.service.dto.response.CustomerResponseLegacyDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;


@Log4j2
@Repository
@RequiredArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepository {

  final ApiUtilsHelper apiUtilsService;
  final ObjectMapper objectMapper;

  @Override
  public BaseResponseDto<CustomerResponseLegacyDto> getCustomerResponseLegacyDtoBaseResponseDto(
      String customerId) {
    try {
      String responseCustomer = apiUtilsService.consumeApi("http://localhost:8080/api/customer/" + customerId, null, HttpMethod.GET, new HttpHeaders());
      BaseResponseDto<CustomerResponseLegacyDto> customerResponseLegacyDto = objectMapper.readValue(
          responseCustomer, new TypeReference<>() {
          });
      if (!StringUtils.equals(customerResponseLegacyDto.getStatus(), "OK")) {
        throw new GenericException(HttpStatus.NOT_FOUND, NOT_FOUND);
      }
      return customerResponseLegacyDto;
    } catch (JsonProcessingException exception) {
      log.error(exception.getMessage());
      throw new InternalErrorException(exception.getMessage());
    }
  }
}
