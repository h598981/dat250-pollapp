package com.example.pollapp.domain;

import jakarta.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(
    name = "vote_options",
    uniqueConstraints = @UniqueConstraint(columnNames = {"poll_id", "presentation_order"})
)
public class VoteOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "poll_id", nullable = false)
    private Poll poll;

    @Column(nullable = false)
    private String caption;

    @Column(name = "presentation_order", nullable = false)
    private int presentationOrder;

    @OneToMany(mappedBy = "votesOn", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Vote> votes = new LinkedHashSet<>();

    protected VoteOption() {} // JPA constructor

    public VoteOption(Poll poll, String caption, int presentationOrder) {
        this.poll = poll;
        this.caption = caption;
        this.presentationOrder = presentationOrder;
    }

    // Getters
    public Long getId() { return id; }
    public Poll getPoll() { return poll; }
    public String getCaption() { return caption; }
    public int getPresentationOrder() { return presentationOrder; }
    public Set<Vote> getVotes() { return votes; }

    // Setters
    public void setCaption(String caption) { this.caption = caption; }
}
