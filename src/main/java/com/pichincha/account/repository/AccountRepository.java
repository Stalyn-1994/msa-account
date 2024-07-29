package com.pichincha.account.repository;

import com.pichincha.account.domain.AccountEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<AccountEntity, Long> {

  Optional<AccountEntity> findAccountEntitiesByAccountNumber(String id);

  Optional<List<AccountEntity>> findAccountEntitiesByCustomerId(String id);
}
