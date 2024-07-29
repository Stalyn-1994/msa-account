package com.pichincha.account.service.imp;


import static com.pichincha.account.helper.Helper.buildResponseDto;
import static com.pichincha.account.util.Constants.NOT_FOUND;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pichincha.account.domain.AccountEntity;
import com.pichincha.account.domain.MovementsEntity;
import com.pichincha.account.domain.exception.GenericException;
import com.pichincha.account.repository.AccountRepository;
import com.pichincha.account.repository.CustomerRepository;
import com.pichincha.account.repository.MovementRepository;
import com.pichincha.account.service.MovementService;
import com.pichincha.account.service.dto.request.MovementRequestDto;
import com.pichincha.account.service.dto.response.AccountResponseDto;
import com.pichincha.account.service.dto.response.BaseResponseDto;
import com.pichincha.account.service.dto.response.MovementResponseDto;
import com.pichincha.account.service.mapper.AccountServiceMapper;
import com.pichincha.account.service.mapper.MovementServiceMapper;
import jakarta.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class MovementServiceImpl implements MovementService {

  final MovementServiceMapper movementServiceMapper;
  final AccountServiceMapper accountServiceMapper;
  final CustomerRepository customerRepository;
  final MovementRepository movementRepository;
  final AccountRepository accountRepository;
  final ObjectMapper objectMapper;

  @Override
  public ResponseEntity<BaseResponseDto<Long>> save(MovementRequestDto movementRequestDto) {
    AccountEntity account = accountRepository.findAccountEntitiesByAccountNumber(movementRequestDto.getAccountNumber())
        .orElseThrow(() -> new GenericException(HttpStatus.NOT_FOUND, NOT_FOUND));
    MovementsEntity movementsEntity = movementServiceMapper.buildMoveEntity(movementRequestDto.getAmount(), account);
    Long id = movementRepository.save(movementsEntity).getId();
    return buildResponseDto(id, HttpStatus.CREATED);
  }

  @Override
  @Transactional
  public ResponseEntity<BaseResponseDto<AccountResponseDto>> update(MovementRequestDto movementRequestDto, Long id) {
    MovementsEntity movementsEntity = movementRepository.findById(id).orElseThrow(() -> new GenericException(HttpStatus.NOT_FOUND, NOT_FOUND));
    AccountEntity accountEntity=accountRepository.findAccountEntitiesByAccountNumber(movementRequestDto.getAccountNumber()).orElseThrow(() -> new GenericException(HttpStatus.NOT_FOUND, NOT_FOUND));
    List<MovementsEntity> movementsEntityList=movementServiceMapper.getMovementsUpdates(accountEntity,movementsEntity,movementRequestDto);
    movementRepository.saveAll(movementsEntityList);
    return buildResponseDto(AccountResponseDto.builder()
        .accountNumber(String.valueOf(id))
        .build(), HttpStatus.OK);
  }

  @Override
  public ResponseEntity<BaseResponseDto<List<MovementResponseDto>>> getReportByDate(LocalDate initDate, LocalDate endDate,
      String customer) {
    List<MovementResponseDto> movementsResponseDto = movementRepository
        .findMovementsEntitiesByAccountNumber_CustomerAndDateBetween(customer,Timestamp.valueOf(initDate.atStartOfDay()), Timestamp.valueOf(endDate.atStartOfDay()))
        .stream().map(accountServiceMapper::toAccountResponseDto).toList();
    return buildResponseDto(movementsResponseDto, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<BaseResponseDto<AccountResponseDto>> edit(Map<String, Object> movementsFields,
      Long idMovement) {
    MovementsEntity movementsEntity = movementRepository
        .findById(idMovement)
        .orElseThrow(() -> new GenericException(HttpStatus.NOT_FOUND, NOT_FOUND));
    AccountEntity accountEntityOld=movementsEntity.getAccountNumber();
    try {
      MovementRequestDto movementRequestDto = objectMapper.convertValue(movementsFields, MovementRequestDto.class);
      List<MovementsEntity> movementsEntities = new ArrayList<>();
      List<MovementsEntity> movementsOld = new ArrayList<>();
      if (!Objects.isNull(movementRequestDto.getAccountNumber())) {
        AccountEntity accountEntity= accountRepository.findAccountEntitiesByAccountNumber(movementRequestDto.getAccountNumber()).orElseThrow(() -> new GenericException(HttpStatus.NOT_FOUND, NOT_FOUND));
        movementsEntity.setAccountNumber(accountEntity);
        movementsEntities=movementServiceMapper.getMovementsUpdates(accountEntity,movementsEntity,movementRequestDto);
       movementsOld=movementServiceMapper.getMovementsUpdated(accountEntityOld.getAccountNumber(),accountEntityOld.getInitialBalance());
      }
    if (!Objects.isNull(movementRequestDto.getAmount())){
        movementsEntities=movementServiceMapper.getMovementsUpdates(movementsEntity.getAccountNumber(),movementsEntity,movementRequestDto);
      }
    movementRepository.saveAll(movementsOld);
      movementRepository.saveAll(movementsEntities);
      return buildResponseDto(AccountResponseDto.builder()
          .accountNumber(String.valueOf(idMovement))
          .build(), HttpStatus.OK);
    }catch(Exception exception){
      log.error(exception.getMessage());
      throw new GenericException(HttpStatus.BAD_REQUEST,exception.getMessage());
    }
  }

  @Override
  public ResponseEntity<BaseResponseDto<AccountResponseDto>> delete(Long idMovement) {
    MovementsEntity movementsEntity = movementRepository
        .findById(idMovement)
        .orElseThrow(() -> new GenericException(HttpStatus.NOT_FOUND, NOT_FOUND));
    movementRepository.delete(movementsEntity);
    return buildResponseDto(
        AccountResponseDto.builder().accountNumber(String.valueOf(idMovement)).build(),
        HttpStatus.NO_CONTENT);
  }
}