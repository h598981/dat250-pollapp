package com.example.pollapp.domain;

import java.time.Instant;

public class Vote {
    private Long id;
    private Long pollId;
    private Long optionId;
    private Long userId;     // null for anonymous votes
    private Instant timestamp;

    public Vote() {}

    public Vote(Long id, Long pollId, Long optionId, Long userId, Instant timestamp) {
        this.id = id;
        this.pollId = pollId;
        this.optionId = optionId;
        this.userId = userId;
        this.timestamp = timestamp;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPollId() { return pollId; }
    public void setPollId(Long pollId) { this.pollId = pollId; }

    public Long getOptionId() { return optionId; }
    public void setOptionId(Long optionId) { this.optionId = optionId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
