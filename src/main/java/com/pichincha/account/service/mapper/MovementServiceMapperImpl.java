package com.pichincha.account.service.mapper;


import static com.pichincha.account.util.Constants.INSUFFICIENT_BALANCE;

import com.pichincha.account.domain.AccountEntity;
import com.pichincha.account.domain.MovementsEntity;
import com.pichincha.account.domain.exception.GenericException;
import com.pichincha.account.helper.factory.AccountInterface;
import com.pichincha.account.helper.factory.AccountMovementsEnum;
import com.pichincha.account.repository.MovementRepository;
import com.pichincha.account.service.dto.request.MovementRequestDto;
import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MovementServiceMapperImpl implements MovementServiceMapper {

  @Resource
  final Map<AccountMovementsEnum, AccountInterface> loadServices;
  final MovementRepository movementRepository;

  @Override
  public MovementsEntity buildMoveEntity(Double movementRequestDto, AccountEntity account) {
    double balanceTotal = calculateBalanceTotal(account);
    if (balanceTotal < 0 || (balanceTotal + movementRequestDto) < 0) {
      throw new GenericException(HttpStatus.FORBIDDEN, INSUFFICIENT_BALANCE);
    }
    return MovementsEntity.builder()
        .date(new Date(System.currentTimeMillis()))
        .amount(movementRequestDto)
        .balance(balanceTotal + movementRequestDto)
        .accountNumber(account)
        .type(buildTypeMovement(movementRequestDto)).build();

  }

  private String buildTypeMovement(double amount) {
    StringBuilder typeMovement = new StringBuilder();
    if (amount > 0) {
      typeMovement.append("Deposit of ");
    } else {
      typeMovement.append("Withdrawal of ");
    }
    typeMovement.append(amount);
    return typeMovement.toString();
  }

  public double calculateBalanceTotal(AccountEntity account) {
    if (!Objects.isNull(account.getMovements())&&
        !account.getMovements().isEmpty()) {
      return  loadServices.get(AccountMovementsEnum.WITH_MOVEMENTS)
          .calculateInitialBalance(account);
    }
    return loadServices.get(AccountMovementsEnum.WITHOUT_MOVEMENTS)
        .calculateInitialBalance(account);
  }

  @Override
  public List<MovementsEntity> recalculateBalance(List<MovementsEntity> movementsEntitiesOld,Double initBalance){
    movementsEntitiesOld.get(0).setBalance(initBalance);
    movementsEntitiesOld.get(0).setAmount(initBalance);
    movementsEntitiesOld.get(0).setType(buildTypeMovement(movementsEntitiesOld.get(0).getAmount()));
    for (int i = 1; i < movementsEntitiesOld.size(); i++) {
      movementsEntitiesOld.get(i).setType(buildTypeMovement(movementsEntitiesOld.get(i).getAmount()));
      movementsEntitiesOld.get(i).setBalance(movementsEntitiesOld.get(i-1).getBalance()+movementsEntitiesOld.get(i).getAmount());
    }
    return movementsEntitiesOld;
  }

  public List<MovementsEntity> getMovementsUpdated(String accountNumber,Double initBalance){
    List<MovementsEntity> movementsEntities=movementRepository.findMovementsEntitiesByAccountNumber_AccountNumberOrderByIdAsc(accountNumber);
    return recalculateBalance(movementsEntities,initBalance);
  }

public List<MovementsEntity> getMovementsUpdates(AccountEntity accountEntity,MovementsEntity movements,
    MovementRequestDto movementRequestDto) {
  MovementsEntity movementPrevious = new MovementsEntity();
  List<MovementsEntity> movementsEntitiesList = new ArrayList<>();
  for (MovementsEntity movement : accountEntity.getMovements()) {
    if (movement.getId().equals(movements.getId())) {
      movement.setType(buildTypeMovement(movementRequestDto.getAmount()));
      movement.setBalance(movementPrevious.getBalance() + movementRequestDto.getAmount());
      movement.setAmount(movementRequestDto.getAmount());
    }
    if (movement.getId()>movements.getId()){
      movement.setBalance(movementPrevious.getBalance() + movement.getAmount());
    }
    movementsEntitiesList.add(movement);
    movementPrevious = movement;
  }
  return movementsEntitiesList;

}
}
