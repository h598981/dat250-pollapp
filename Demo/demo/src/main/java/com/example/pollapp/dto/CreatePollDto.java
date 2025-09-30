package com.example.pollapp.dto;

import java.time.Instant;
import java.util.List;

public class CreatePollDto {
    public Long ownerUserId;        // creator (must be an existing user)
    public String question;
    public boolean isPublic;        // true = public, false = private
    public boolean limitOnePerUser; // only applies if isPublic == false

    public Instant publishedAt;     // ISO-8601, e.g. "2025-09-26T10:00:00Z"
    public Instant deadlineAt;

    public List<String> options;    // option captions
    public List<Long> invitedUserIds; // optional; for private polls
}
