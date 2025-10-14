package com.example.pollapp.redis;

import redis.clients.jedis.UnifiedJedis;

import java.util.HashMap;
import java.util.Map;

/**
 * Redis-backed cache for "votes per option" for a poll.
 * Key shape: poll:<pollId>:votes  (HASH)
 *   field = option caption (or option ID if you prefer)
 *   value = count (stringified integer)
 */
public class PollCache implements AutoCloseable {
    private static final String KEY_PREFIX = "poll:";
    private static final String KEY_SUFFIX = ":votes";

    private final UnifiedJedis jedis;

    /** Connects to local Redis on default port 6379. */
    public PollCache() {
        this("redis://localhost:6379");
    }

    public PollCache(String redisUrl) {
        this.jedis = new UnifiedJedis(redisUrl);
    }

    /** Build the Redis key for a given poll. */
    private String key(String pollId) {
        return KEY_PREFIX + pollId + KEY_SUFFIX;
    }

    /** Does the cache entry exist? */
    public boolean isCached(String pollId) {
        return jedis.exists(key(pollId));
    }

    /**
     * Get votes for a poll as Map<optionCaption, Integer>.
     * Returns empty map if not cached.
     */
    public Map<String, Integer> getVotes(String pollId) {
        Map<String, String> raw = jedis.hgetAll(key(pollId));
        Map<String, Integer> result = new HashMap<>();
        for (Map.Entry<String, String> e : raw.entrySet()) {
            try {
                result.put(e.getKey(), Integer.parseInt(e.getValue()));
            } catch (NumberFormatException ex) {
                // if a field is malformed, treat as 0
                result.put(e.getKey(), 0);
            }
        }
        return result;
    }

    /**
     * Put votes map into cache with TTL (seconds).
     * Use this after aggregating from the DB on a cache miss.
     */
    public void putVotes(String pollId, Map<String, Integer> optionToCount, int ttlSeconds) {
        if (optionToCount == null || optionToCount.isEmpty()) return;
        String k = key(pollId);

        // Convert values to strings for HSET
        Map<String, String> payload = new HashMap<>();
        optionToCount.forEach((opt, cnt) -> payload.put(opt, Integer.toString(cnt)));

        jedis.hset(k, payload);
        if (ttlSeconds > 0) {
            jedis.expire(k, ttlSeconds);
        }
    }

    /**
     * Increment a single option's count in the cache (atomic).
     * Returns the new count after increment.
     */
    public long increment(String pollId, String optionCaption) {
        return jedis.hincrBy(key(pollId), optionCaption, 1);
    }

    /** Remove the poll from cache (force next request to re-fetch from DB). */
    public void invalidate(String pollId) {
        jedis.del(key(pollId));
    }

    @Override
    public void close() {
        jedis.close();
    }
}
