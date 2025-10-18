package com.example.pollapp.messaging;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RabbitMQTest {
  @Test
  void serviceConstructsSingleton() {
    RabbitMQService s = RabbitMQService.get();
    assertNotNull(s);
  }
}
