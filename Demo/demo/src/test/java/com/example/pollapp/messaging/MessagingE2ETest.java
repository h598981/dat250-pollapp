package com.example.pollapp.messaging;

import com.rabbitmq.client.DeliverCallback;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MessagingE2ETest {

  @Test
  void pollExchange_create_subscribe_publish_receive() throws Exception {
    var mq = RabbitMQService.get(); // singleton

    long pollId = 42L;
    String ex = "poll." + pollId;

    mq.ensurePollExchange(ex);

    CountDownLatch latch = new CountDownLatch(1);
    DeliverCallback cb = (tag, delivery) -> {
      String json = new String(delivery.getBody(), StandardCharsets.UTF_8);
      System.out.println("[test] received: " + json);
      latch.countDown();
    };
    mq.subscribeFanout(ex, cb);

    // simulate a vote event
    String voteJson = "{\"type\":\"vote.cast\",\"pollId\":42,\"optionId\":7,\"user\":\"alice\"}";
    mq.publish(ex, voteJson);

    boolean received = latch.await(2, TimeUnit.SECONDS);
    assertTrue(received, "should receive vote event from RabbitMQ");
  }
}
