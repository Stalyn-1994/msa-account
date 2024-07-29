package com.pichincha.account.service.mapper;


import com.pichincha.account.domain.AccountEntity;
import com.pichincha.account.domain.MovementsEntity;
import com.pichincha.account.service.dto.request.AccountRequestDto;
import com.pichincha.account.service.dto.request.AccountRequestEditDto;
import com.pichincha.account.service.dto.request.AccountUpdateRequestDto;
import com.pichincha.account.service.dto.response.AccountResponseDto;
import com.pichincha.account.service.dto.response.CustomerResponseLegacyDto;
import com.pichincha.account.service.dto.response.MovementResponseDto;
import java.util.Random;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;


@Mapper(componentModel = "spring")
public abstract class AccountServiceMapper {

  @Mapping(target = "accountNumber", expression = "java(generateAccountNumber())")
  @Mapping(target = "customer", source = "customerResponseLegacyDto.name")
  @Mapping(target = "customerId", source = "customerResponseLegacyDto.clientId")
  public abstract AccountEntity toAccountEntity(AccountRequestDto accountRequestDto,
      CustomerResponseLegacyDto customerResponseLegacyDto);

  @Mapping(target = "accountNumber", source = "accountRequestDto.accountNumber")
  @Mapping(target = "customer", source = "customerResponseLegacyDto.name")
  @Mapping(target = "customerId", source = "customerResponseLegacyDto.clientId")
  @Mapping(target = "id", source = "id")
  public abstract AccountEntity toAccountEntityUpdated(AccountUpdateRequestDto accountRequestDto,
      CustomerResponseLegacyDto customerResponseLegacyDto, Long id);

  public abstract AccountResponseDto toAccountResponseDto(AccountEntity accountEntity);

  @Mapping(target = "accountNumber", source = "movementsEntity.accountNumber.accountNumber")
  @Mapping(target = "customer", source = "movementsEntity.accountNumber.customer")
  @Mapping(target = "initialBalance", source = "movementsEntity.accountNumber.initialBalance")
  @Mapping(target = "status", source = "movementsEntity.accountNumber.status")
  @Mapping(target = "type", source = "movementsEntity.accountNumber.type")
  public abstract MovementResponseDto toAccountResponseDto(MovementsEntity movementsEntity);

  @Mapping(target = "id", ignore = true)
  public abstract MovementsEntity toMovementsEntity(MovementsEntity movementsEntity);

  public String generateAccountNumber() {
    Random random = new Random();
    return String.valueOf(random.nextLong() & Long.MAX_VALUE);
  }

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "accountNumber", ignore = true)
  @Mapping(target = "type", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, source = "accountRequestDto.type")
  @Mapping(target = "initialBalance", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, source = "accountRequestDto.initialBalance")
  @Mapping(target = "status", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, source = "accountRequestDto.status")
  @Mapping(target = "customerId", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, source = "customerResponseLegacyDto.clientId")
  @Mapping(target = "customer", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, source = "customerResponseLegacyDto.name")
  public abstract AccountEntity editAccountFromAccountRequestDto(@MappingTarget AccountEntity customer, AccountRequestEditDto accountRequestDto,CustomerResponseLegacyDto customerResponseLegacyDto);
}

