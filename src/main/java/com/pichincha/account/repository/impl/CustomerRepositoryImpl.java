package com.pichincha.account.repository.impl;


import static com.pichincha.account.util.Constants.CUSTOMER_NOT_ACTIVE;
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
import org.springframework.core.env.Environment;
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
  final Environment environment;

  @Override
  public BaseResponseDto<CustomerResponseLegacyDto> getCustomerResponseLegacyDtoBaseResponseDto(
      String customerId) {
    try {
      String responseCustomer = apiUtilsService.consumeApi(environment.getRequiredProperty("account.services.url",String.class) + customerId, null, HttpMethod.GET, new HttpHeaders());
      return objectMapper.readValue(responseCustomer, new TypeReference<>() {});
    } catch (JsonProcessingException exception) {
      log.error(exception.getMessage());
      throw new InternalErrorException(exception.getMessage());
    }
  }
}
