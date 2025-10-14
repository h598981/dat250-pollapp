package com.example.pollapp.redis;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Demo flow:
 *  1) Try cache → miss → "DB aggregate" → put to cache
 *  2) Try cache → hit
 *  3) Simulate a new vote → either increment cache or invalidate
 */
public class RedisCacheDemo {
    public static void main(String[] args) {
        String pollId = "03ebcb7b"; // example

        try (PollCache cache = new PollCache("redis://localhost:6379")) {

            // 1) READ counts (cache-first)
            Map<String, Integer> counts;
            if (cache.isCached(pollId)) {
                System.out.println("Cache HIT");
                counts = cache.getVotes(pollId);
            } else {
                System.out.println("Cache MISS → aggregating from DB...");
                // Simulated DB aggregation result (replace with real JPA query results)
                counts = new LinkedHashMap<>();
                counts.put("Yes, yammy!", 269);
                counts.put("Mamma mia, nooooo!", 268);
                counts.put("I do not really care ...", 42);

                // Put into cache with a TTL (e.g., 5 minutes)
                cache.putVotes(pollId, counts, 300);
            }

            System.out.println("Counts now: " + counts);

            // 2) READ again (should be a hit now)
            if (cache.isCached(pollId)) {
                System.out.println("Cache HIT (second read)");
                System.out.println("Counts from cache: " + cache.getVotes(pollId));
            }

            // 3) Simulate a new vote on "Yes, yammy!"
            String votedOption = "Yes, yammy!";
            System.out.println("\nNew vote for: " + votedOption);

            // Option A (fast path): update cached count atomically
            long newCount = cache.increment(pollId, votedOption);
            System.out.println("Updated cached count for '" + votedOption + "': " + newCount);

            // Option B (alternative): invalidate and let next read re-aggregate from DB
            // cache.invalidate(pollId);

            System.out.println("Counts after vote (cache): " + cache.getVotes(pollId));
        }
    }
}
