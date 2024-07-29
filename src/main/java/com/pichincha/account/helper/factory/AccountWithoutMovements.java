package com.pichincha.account.helper.factory;


import com.pichincha.account.domain.AccountEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountWithoutMovements implements AccountInterface {

  @Override
  public Double calculateInitialBalance(AccountEntity account) {
    return 0.0;
  }
}