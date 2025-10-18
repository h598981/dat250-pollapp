package com.example.pollapp.messaging;

import com.rabbitmq.client.*;
import java.io.Closeable;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class RabbitMQService implements Closeable {
  private static RabbitMQService INSTANCE;

  public static synchronized RabbitMQService get() {
    if (INSTANCE == null) {
      try { INSTANCE = new RabbitMQService(); }
      catch (Exception e) { throw new RuntimeException("RabbitMQ init failed", e); }
    }
    return INSTANCE;
  }

  private final Connection connection;
  private final Channel channel;

  private RabbitMQService() throws IOException, TimeoutException {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");             // default Docker port mapping
    connection = factory.newConnection();
    channel = connection.createChannel();
  }

  /** Create durable fanout exchange per poll. */
  public void ensurePollExchange(String exchange) throws IOException {
    channel.exchangeDeclare(exchange, BuiltinExchangeType.FANOUT, true);
  }

  public void publish(String exchange, String json) throws IOException {
    channel.basicPublish(exchange, "", null, json.getBytes(StandardCharsets.UTF_8));
  }

  /** Auto-queue + bind + consume. */
  public void subscribeFanout(String exchange, DeliverCallback cb) throws IOException {
    String queue = channel.queueDeclare().getQueue(); // non-durable, auto-delete, exclusive
    channel.queueBind(queue, exchange, "");
    channel.basicConsume(queue, true, cb, tag -> {});
  }

  @Override public void close() throws IOException {
    try { channel.close(); } catch (Exception ignored) {}
    try { connection.close(); } catch (Exception ignored) {}
  }
}
