package com.pichincha.account.helper;


import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class ApiUtilsHelper {

  private final RestTemplate restTemplate;

  public <T> T consumeApi(String url, Object body, HttpMethod method, HttpHeaders headers) {
    ResponseEntity<T> response = restTemplate.exchange(url, method, new HttpEntity<>(body, headers),
        ParameterizedTypeReference.forType(String.class));
    return response.getBody();
  }

}
