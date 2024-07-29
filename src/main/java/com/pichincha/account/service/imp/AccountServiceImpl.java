package com.pichincha.account.service.imp;


import static com.pichincha.account.helper.Helper.buildResponseDto;
import static com.pichincha.account.util.Constants.ACCOUNT_WITH_MOVEMENTS;
import static com.pichincha.account.util.Constants.NOT_FOUND;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pichincha.account.domain.AccountEntity;
import com.pichincha.account.domain.MovementsEntity;
import com.pichincha.account.domain.exception.GenericException;
import com.pichincha.account.repository.AccountRepository;
import com.pichincha.account.repository.CustomerRepository;
import com.pichincha.account.service.AccountService;
import com.pichincha.account.service.MovementService;
import com.pichincha.account.service.dto.request.AccountRequestDto;
import com.pichincha.account.service.dto.request.AccountRequestEditDto;
import com.pichincha.account.service.dto.request.AccountUpdateRequestDto;
import com.pichincha.account.service.dto.response.AccountResponseDto;
import com.pichincha.account.service.dto.response.BaseResponseDto;
import com.pichincha.account.service.dto.response.CustomerResponseLegacyDto;
import com.pichincha.account.service.mapper.AccountServiceMapper;
import com.pichincha.account.service.mapper.MovementServiceMapper;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;


@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

  final MovementServiceMapper movementServiceMapper;
  final AccountServiceMapper accountServiceMapper;
  final CustomerRepository customerRepository;
  final AccountRepository accountRepository;
  final MovementService movementService;
  final ObjectMapper objectMapper;

  @Override
  public ResponseEntity<BaseResponseDto<AccountResponseDto>> save(AccountRequestDto accountRequestDto) {
    try {
      CustomerResponseLegacyDto customerResponseLegacyDto = customerRepository
          .getCustomerResponseLegacyDtoBaseResponseDto(accountRequestDto.getCustomerId()).getData();
      AccountEntity account=accountServiceMapper.toAccountEntity(accountRequestDto, customerResponseLegacyDto);
      account.setMovements(List.of(movementServiceMapper.buildMoveEntity(accountRequestDto.getInitialBalance(), account)));
      String accountNumber = accountRepository.save(account).getAccountNumber();
      return buildResponseDto(AccountResponseDto.builder()
          .accountNumber(accountNumber)
          .build(), HttpStatus.CREATED);
    }catch (HttpClientErrorException httpClientErrorException){
      log.error(httpClientErrorException.getMessage());
      throw new GenericException((HttpStatus) httpClientErrorException.getStatusCode(), httpClientErrorException.getMessage());
    }
  }

  @Override
  public ResponseEntity<BaseResponseDto<AccountResponseDto>>update(
      AccountUpdateRequestDto accountRequestDto) {
    AccountEntity accountEntity = accountRepository
        .findAccountEntitiesByAccountNumber(accountRequestDto.getAccountNumber())
        .orElseThrow(() -> new GenericException(HttpStatus.NOT_FOUND, NOT_FOUND));
    CustomerResponseLegacyDto customerResponseLegacyDto = customerRepository
        .getCustomerResponseLegacyDtoBaseResponseDto(accountRequestDto.getCustomerId()).getData();
    AccountEntity account=accountServiceMapper.toAccountEntityUpdated(accountRequestDto, customerResponseLegacyDto, accountEntity.getId());
    List<MovementsEntity>movementsEntities=movementServiceMapper.getMovementsUpdated(account.getAccountNumber(),accountRequestDto.getInitialBalance());
    account.setMovements(movementsEntities);
    String accountNumber = accountRepository.save(account).getAccountNumber();
    return buildResponseDto(AccountResponseDto.builder()
        .accountNumber(String.valueOf(accountNumber))
        .build(), HttpStatus.OK);
  }

  @Override
  public ResponseEntity<BaseResponseDto<AccountResponseDto>> edit(Map<String, Object> accountFields,
      String accountNumber) {
    AccountEntity accountEntity = accountRepository.findAccountEntitiesByAccountNumber(
        accountNumber).orElseThrow(() -> new GenericException(HttpStatus.NOT_FOUND, NOT_FOUND));
    try{
    AccountRequestEditDto accountRequestDto=objectMapper.convertValue(accountFields, AccountRequestEditDto.class);
    CustomerResponseLegacyDto customerResponseLegacyDto=new CustomerResponseLegacyDto();
    if (!Objects.isNull(accountRequestDto.getCustomerId())) {
      customerResponseLegacyDto= customerRepository.getCustomerResponseLegacyDtoBaseResponseDto(accountRequestDto.getCustomerId()).getData();
    }
    AccountEntity account=accountServiceMapper.editAccountFromAccountRequestDto(accountEntity,accountRequestDto,customerResponseLegacyDto);
    if (!Objects.isNull(accountRequestDto.getInitialBalance())){
      List<MovementsEntity>movementsEntities=movementServiceMapper.getMovementsUpdated(account.getAccountNumber(),accountRequestDto.getInitialBalance());
      account.setMovements(movementsEntities);
    }
    accountRepository.save(account);
    return buildResponseDto(AccountResponseDto.builder()
        .accountNumber(accountNumber)
        .build(), HttpStatus.OK);
    }catch (Exception exception){
      log.error(exception.getMessage());
      throw new GenericException(HttpStatus.BAD_REQUEST,exception.getMessage());
    }
  }

  @Override
  public ResponseEntity<BaseResponseDto<AccountResponseDto>> delete(String accountNumber) {
    AccountEntity accountEntity = accountRepository.findAccountEntitiesByAccountNumber(
        accountNumber).orElseThrow(() -> new GenericException(HttpStatus.NOT_FOUND, NOT_FOUND));
    if (accountEntity.getMovements().size()<=1) {
      accountRepository.delete(accountEntity);
      return ResponseEntity.noContent().build();
    }
    accountEntity.setStatus(Boolean.FALSE);
    accountRepository.save(accountEntity);
    throw new GenericException(HttpStatus.BAD_REQUEST, ACCOUNT_WITH_MOVEMENTS);
  }
}
