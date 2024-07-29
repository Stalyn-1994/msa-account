package com.pichincha.account.service.mapper;

import com.pichincha.account.domain.AccountEntity;
import com.pichincha.account.domain.MovementsEntity;
import com.pichincha.account.service.dto.request.MovementRequestDto;
import java.util.List;

public interface MovementServiceMapper {

  MovementsEntity buildMoveEntity(Double movementRequestDto, AccountEntity account);

  double calculateBalanceTotal(AccountEntity account);

  List<MovementsEntity> recalculateBalance(List<MovementsEntity> movementsEntities,Double initBalance);

  List<MovementsEntity> getMovementsUpdated(String accountNumber,Double initBalance);

  List<MovementsEntity> getMovementsUpdates(AccountEntity accountEntity,MovementsEntity movements,
      MovementRequestDto movementRequestDto);
}
