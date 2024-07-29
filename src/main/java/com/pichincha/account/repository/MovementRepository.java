package com.pichincha.account.repository;


import com.pichincha.account.domain.MovementsEntity;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MovementRepository extends CrudRepository<MovementsEntity, Long> {
  @Query("SELECT a FROM MovementsEntity a WHERE a.accountNumber.customerId =:customerId AND a.date BETWEEN :startDate AND :endDate")
  List<MovementsEntity> findMovementsEntitiesByAccountNumber_CustomerAndDateBetween(@Param("customerId") String customerId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

  List<MovementsEntity> findMovementsEntitiesByAccountNumber_AccountNumberOrderByIdAsc(String accountNumber);

}
