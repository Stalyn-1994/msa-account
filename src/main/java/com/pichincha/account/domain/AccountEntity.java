package com.pichincha.account.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "account", schema = "account")
public class AccountEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;
  @Column(name = "account_number", nullable = false, length = 255, unique = true)
  String accountNumber;
  @Column(name = "type", nullable = false, length = 255)
  String type;
  @Column(name = "initial_balance")
  Double initialBalance;
  @Column(name = "status", nullable = false)
  Boolean status = false;
  @Column(name = "customer", nullable = false)
  String customer ;
  @Column(name = "customer_id", nullable = false)
  String customerId ;
  @OneToMany(mappedBy = "accountNumber",cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE},fetch = FetchType.LAZY)
  List<MovementsEntity> movements;
}