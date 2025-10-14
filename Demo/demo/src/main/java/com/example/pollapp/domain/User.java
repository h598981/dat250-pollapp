package com.example.pollapp.domain;

import jakarta.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Poll> created = new LinkedHashSet<>();

    @OneToMany(mappedBy = "voter", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Vote> votes = new LinkedHashSet<>();

    protected User() {} // required by JPA

    /**
     * Creates a new User object with given username and email.
     * The id of a new user object gets determined by the database.
     */
    public User(String username, String email) {
        this.username = username;
        this.email = email;
        this.created = new LinkedHashSet<>();
        this.votes = new LinkedHashSet<>();
    }

    /**
     * Creates a new Poll object for this user with the given poll question.
     */
    public Poll createPoll(String question) {
        Poll p = new Poll(question, this);
        this.created.add(p);
        return p;
    }

    /**
     * Creates a new Vote for a given VoteOption in a Poll and returns the Vote.
     */
    public Vote voteFor(VoteOption option) {
        if (option == null) throw new IllegalArgumentException("Vote option cannot be null");
        Vote v = new Vote(this, option);
        this.votes.add(v);
        option.getVotes().add(v);
        return v;
    }

    // Getters
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public Set<Poll> getCreated() { return created; }
    public Set<Vote> getVotes() { return votes; }

    // Setters
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
}
