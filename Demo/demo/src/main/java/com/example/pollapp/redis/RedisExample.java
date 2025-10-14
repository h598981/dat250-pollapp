package com.example.pollapp.redis;

import redis.clients.jedis.UnifiedJedis;

public class RedisExample {
    public static void main(String[] args) {
        // Connect to your local Redis instance
        try (UnifiedJedis jedis = new UnifiedJedis("redis://localhost:6379")) {
            System.out.println("✅ Connected to Redis!");

            // Test the connection
            String pong = jedis.ping();
            System.out.println("PING -> " + pong);

            // Simple key-value test
            jedis.set("user", "alice");
            System.out.println("user = " + jedis.get("user"));

            // Set an expiration
            jedis.expire("user", 5);
            System.out.println("TTL (user) = " + jedis.ttl("user"));
        }
    }
}
