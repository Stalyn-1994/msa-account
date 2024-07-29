package com.pichincha.account.service.dto.request;


import com.pichincha.account.domain.enums.AccountEnum;
import com.pichincha.account.domain.validations.AccountValidation;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountRequestDto {

  @AccountValidation
  AccountEnum type;
  @NotBlank(message = "Customer id is required")
  @Size(min = 3, max = 100, message = "Customer id must be between 8 and 20 characters")
  String customerId;
  @Builder.Default
  Boolean status = false;
  @NotNull(message = "Initial balance is required")
  @Min(value = 0, message = "Initial balance must be at least 0")
  double initialBalance = 0.0;
}
