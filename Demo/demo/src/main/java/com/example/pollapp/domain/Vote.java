package com.example.pollapp.domain;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "votes")
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "voter_id", nullable = false)
    private User voter;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id", nullable = false)
    private VoteOption votesOn;

    @Column(nullable = false)
    private Instant timestamp = Instant.now();

    protected Vote() {}

    public Vote(User voter, VoteOption option) {
        this.voter = voter;
        this.votesOn = option;
        this.timestamp = Instant.now();
    }

    // Getters
    public Long getId() { return id; }
    public User getVoter() { return voter; }
    public VoteOption getVotesOn() { return votesOn; }
    public Instant getTimestamp() { return timestamp; }
}
