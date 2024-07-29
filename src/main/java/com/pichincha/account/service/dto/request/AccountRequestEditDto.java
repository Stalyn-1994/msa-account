package com.pichincha.account.service.dto.request;


import com.pichincha.account.domain.enums.AccountEnum;
import com.pichincha.account.domain.validations.AccountValidation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountRequestEditDto {

  @AccountValidation
  AccountEnum type;
  String customerId;
  Boolean status;
  Double initialBalance;
}
