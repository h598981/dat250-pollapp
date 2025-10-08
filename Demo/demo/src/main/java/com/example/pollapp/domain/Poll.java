package com.example.pollapp.domain;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Poll {
    private Long id;
    private Long ownerUserId;
    private String question;

    //  visibility + constraints
    private boolean isPublic;             // true = public, false = private
    private boolean limitOnePerUser;      // only relevant for private polls

    // voting time window
    private Instant publishedAt;          // voting valid from (inclusive)
    private Instant deadlineAt;           // voting valid until (inclusive)

    // Associations by ID to avoid JSON cycles
    private List<Long> optionIds = new ArrayList<>();
    private List<Long> voteIds   = new ArrayList<>();

    //  invited users for private polls
    private Set<Long> invitedUserIds = new HashSet<>();

    public Poll() {}

    public Poll(Long id, Long ownerUserId, String question, boolean isPublic, boolean limitOnePerUser,
                Instant publishedAt, Instant deadlineAt) {
        this.id = id;
        this.ownerUserId = ownerUserId;
        this.question = question;
        this.isPublic = isPublic;
        this.limitOnePerUser = limitOnePerUser;
        this.publishedAt = publishedAt;
        this.deadlineAt = deadlineAt;
    }

    // getters/setters...

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getOwnerUserId() { return ownerUserId; }
    public void setOwnerUserId(Long ownerUserId) { this.ownerUserId = ownerUserId; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public boolean isPublic() { return isPublic; }
    public void setPublic(boolean aPublic) { isPublic = aPublic; }

    public boolean isLimitOnePerUser() { return limitOnePerUser; }
    public void setLimitOnePerUser(boolean limitOnePerUser) { this.limitOnePerUser = limitOnePerUser; }

    public Instant getPublishedAt() { return publishedAt; }
    public void setPublishedAt(Instant publishedAt) { this.publishedAt = publishedAt; }

    public Instant getDeadlineAt() { return deadlineAt; }
    public void setDeadlineAt(Instant deadlineAt) { this.deadlineAt = deadlineAt; }

    public List<Long> getOptionIds() { return optionIds; }
    public List<Long> getVoteIds() { return voteIds; }

    public Set<Long> getInvitedUserIds() { return invitedUserIds; }
}
