package com.pichincha.account.domain.enums;

import java.util.Arrays;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TypeMovementEnum {

  WITHDRAWAL("withdrawal"), DEPOSIT("deposit");

  String name;

  public static Optional<TypeMovementEnum> findByCode(String name) {
    return Arrays.stream(TypeMovementEnum.values()).filter(genderEnum -> genderEnum.name().equals(name))
        .findFirst();
  }

}