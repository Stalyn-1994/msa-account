package com.pichincha.account.service.imp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pichincha.account.domain.exception.GenericException;
import com.pichincha.account.repository.AccountRepository;
import com.pichincha.account.service.AccountService;
import com.pichincha.account.service.dto.request.CustomerRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaReceiverImpl {

  final ObjectMapper objectMapper;
  final AccountService accountService;
  final AccountRepository accountRepository;

  @KafkaListener(topics = "example", groupId = "my-group", autoStartup = "${kafka.listener.enabled}")
  public void listen(String message) {
    try {
      CustomerRequestDto customerRequestDto = objectMapper.readValue(message, CustomerRequestDto.class);
      accountRepository.findAccountEntitiesByCustomerId(customerRequestDto.getIdentification())
          .ifPresent(account -> {
            account.forEach(accountEntity -> accountEntity.setStatus(false));
            accountRepository.saveAll(account);
      });
    } catch (Exception exception) {
      throw new GenericException(HttpStatus.INTERNAL_SERVER_ERROR, "Error in decode request");
    }
  }
}