package com.example.pollapp;

import com.example.pollapp.messaging.RabbitMQService;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class BootSubscriber implements CommandLineRunner {
  @Override public void run(String... args) throws Exception {
    long pollId = 1L;
    String ex = "poll." + pollId;
    var mq = RabbitMQService.get();
    mq.ensurePollExchange(ex);
    DeliverCallback cb = (tag, d) ->
        System.out.println("[app] " + ex + " -> " + new String(d.getBody(), StandardCharsets.UTF_8));
    mq.subscribeFanout(ex, cb);
    System.out.println("[app] subscribed to " + ex);
  }
}
