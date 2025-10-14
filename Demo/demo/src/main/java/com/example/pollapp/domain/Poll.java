package com.example.pollapp.domain;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "polls")
public class Poll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id", nullable = false)
    private User createdBy;

    @Column(nullable = false)
    private String question;

    private boolean isPublic;
    private boolean limitOnePerUser;
    private Instant publishedAt;
    private Instant deadlineAt;

    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("presentationOrder ASC")
    private List<VoteOption> options = new ArrayList<>();

    protected Poll() {} // JPA constructor

    public Poll(String question, User createdBy) {
        this.question = question;
        this.createdBy = createdBy;
    }

    /**
     * Adds a new option to this Poll and returns it.
     * presentationOrder = current size (0-based).
     */
    public VoteOption addVoteOption(String caption) {
        int order = options.size();
        VoteOption option = new VoteOption(this, caption, order);
        options.add(option);
        return option;
    }

    // Getters
    public Long getId() { return id; }
    public User getCreatedBy() { return createdBy; }
    public String getQuestion() { return question; }
    public List<VoteOption> getOptions() { return options; }
    public boolean isPublic() { return isPublic; }
    public boolean isLimitOnePerUser() { return limitOnePerUser; }
    public Instant getPublishedAt() { return publishedAt; }
    public Instant getDeadlineAt() { return deadlineAt; }

    // Setters
    public void setPublic(boolean isPublic) { this.isPublic = isPublic; }
    public void setLimitOnePerUser(boolean limitOnePerUser) { this.limitOnePerUser = limitOnePerUser; }
    public void setPublishedAt(Instant publishedAt) { this.publishedAt = publishedAt; }
    public void setDeadlineAt(Instant deadlineAt) { this.deadlineAt = deadlineAt; }
}
