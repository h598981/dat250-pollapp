package com.example.pollapp.redis;

import redis.clients.jedis.Jedis;

public class RedisExample {
  public static void main(String[] args) {
    // Only run this demo when explicitly turned on
    if (!"true".equalsIgnoreCase(System.getenv("ENABLE_REDIS_DEMO"))) {
      System.out.println("Redis demo disabled. Set ENABLE_REDIS_DEMO=true to run it.");
      return;
    }

    String host = System.getenv().getOrDefault("REDIS_HOST", "localhost");
    int port = Integer.parseInt(System.getenv().getOrDefault("REDIS_PORT", "6379"));

    System.out.println("Connecting to Redis at " + host + ":" + port + " ...");
    try (Jedis jedis = new Jedis(host, port)) {
      System.out.println("✅ Redis PING => " + jedis.ping());
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
  }
}
