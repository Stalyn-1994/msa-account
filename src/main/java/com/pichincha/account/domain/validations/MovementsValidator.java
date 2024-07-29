package com.pichincha.account.domain.validations;


import com.pichincha.account.domain.enums.TypeMovementEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MovementsValidator implements ConstraintValidator<MovementsValidation, TypeMovementEnum> {


  @Override
  public void initialize(MovementsValidation constraintAnnotation) {
    ConstraintValidator.super.initialize(constraintAnnotation);
  }

  @Override
  public boolean isValid(TypeMovementEnum value, ConstraintValidatorContext context) {
    if (value == null) {
      return false;
    }
    return TypeMovementEnum.findByCode(value.name()).isPresent();
  }
}
