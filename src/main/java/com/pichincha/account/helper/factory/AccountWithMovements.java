package com.pichincha.account.helper.factory;


import com.pichincha.account.domain.AccountEntity;
import com.pichincha.account.domain.MovementsEntity;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountWithMovements implements AccountInterface {

  @Override
  public Double calculateInitialBalance(AccountEntity account) {
    Optional<MovementsEntity> movementsEntity = account.getMovements().stream()
        .min((o1, o2) -> o2.getDate().compareTo(o1.getDate()));
    return movementsEntity.get().getBalance();
  }
}
