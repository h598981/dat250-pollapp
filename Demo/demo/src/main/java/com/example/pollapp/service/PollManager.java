/**package com.example.pollapp.service;

import com.example.pollapp.domain.Poll;
import com.example.pollapp.domain.Vote;
import com.example.pollapp.domain.VoteOption;
import com.example.pollapp.domain.User;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class PollManager {
    private final AtomicLong userSeq = new AtomicLong(1);
    private final AtomicLong pollSeq = new AtomicLong(1);
    private final AtomicLong optionSeq = new AtomicLong(1);
    private final AtomicLong voteSeq = new AtomicLong(1);

    private final Map<Long, User> users = new ConcurrentHashMap<>();
    private final Map<Long, Poll> polls = new ConcurrentHashMap<>();
    private final Map<Long, VoteOption> options = new ConcurrentHashMap<>();
    private final Map<Long, Vote> votes = new ConcurrentHashMap<>();

    // --- Users (you already have this pattern) ---
    public User createUser(String username, String email) {
        Long id = userSeq.getAndIncrement();
        User u = new User(id, username, email);
        users.put(id, u);
        return u;
    }
    public Optional<User> getUser(Long id){ return Optional.ofNullable(users.get(id)); }
    public Collection<User> listUsers(){ return users.values(); }
    public boolean deleteUser(Long id){ return users.remove(id) != null; }

    // --- Poll helpers you need for voting ---
    public Optional<Poll> getPoll(Long id){ return Optional.ofNullable(polls.get(id)); }

    // If you don’t have createPoll yet, you can stub it out or skip for now.

    /** EXACT signature used by VotesController line ~25 
    public Vote vote(Long pollId, Long userIdOrNull, Long optionId) {
        Poll p = polls.get(pollId);
        if (p == null) throw new NoSuchElementException("Poll not found");

        VoteOption opt = options.get(optionId);
        if (opt == null || !Objects.equals(opt.getPollId(), pollId))
            throw new NoSuchElementException("Option not in this poll");

        Long id = voteSeq.getAndIncrement();
        Vote v = new Vote(id, pollId, optionId, userIdOrNull, Instant.now());
        votes.put(id, v);
        p.getVoteIds().add(id); // ensure Poll has getVoteIds()
        return v;
    }

    /** EXACT signature used by VotesController line ~35 
    public List<Vote> listVotesByPoll(Long pollId) {
        Poll p = polls.get(pollId);
        if (p == null) throw new NoSuchElementException("Poll not found");
        List<Vote> list = new ArrayList<>();
        for (Long vid : p.getVoteIds()) {
            Vote v = votes.get(vid);
            if (v != null) list.add(v);
        }
        return list;
    }

    

    public com.example.pollapp.domain.Poll createPoll(
        Long ownerUserId,
        String question,
        boolean isPublic,
        boolean limitOnePerUser,
        java.time.Instant publishedAt,
        java.time.Instant deadlineAt,
        java.util.List<String> optionTexts,
        java.util.List<Long> invitedUserIds
) {
    // 1) basic checks
    if (ownerUserId == null) throw new IllegalArgumentException("ownerUserId is required");
    if (!getUser(ownerUserId).isPresent()) throw new java.util.NoSuchElementException("Owner not found");
    if (question == null || question.isBlank()) throw new IllegalArgumentException("question is required");
    if (publishedAt == null || deadlineAt == null || !publishedAt.isBefore(deadlineAt)) {
        throw new IllegalArgumentException("Invalid time window (publishedAt < deadlineAt required)");
    }

    // 2) create poll
    Long pollId = pollSeq.getAndIncrement();
    com.example.pollapp.domain.Poll p = new com.example.pollapp.domain.Poll(
            pollId, ownerUserId, question, limitOnePerUser, limitOnePerUser, deadlineAt, deadlineAt
    );
    // set extra fields on the Poll (assuming you added these getters/setters)
    p.setPublic(isPublic);
    p.setLimitOnePerUser(limitOnePerUser);
    p.setPublishedAt(publishedAt);
    p.setDeadlineAt(deadlineAt);

    // 3) persist poll
    polls.put(pollId, p);

    // 4) create options
    if (optionTexts != null) {
        int order = 1;
        for (String txt : optionTexts) {
            Long optId = optionSeq.getAndIncrement();
            com.example.pollapp.domain.VoteOption vo =
                    new com.example.pollapp.domain.VoteOption(optId, pollId, txt, order++);
            options.put(optId, vo);
            p.getOptionIds().add(optId);
        }
    }

    // 5) invited users for private polls
    if (!isPublic && invitedUserIds != null) {
        for (Long uid : invitedUserIds) {
            if (getUser(uid).isPresent()) {
                p.getInvitedUserIds().add(uid);
            }
        }
    }

    return p;
}


    public Collection<Poll> listPolls() {
        // TODO: Implement logic to return all polls
        // For now, return an empty list or fetch from your data source
        return java.util.Collections.emptyList();
    }

    public List<VoteOption> listOptionsByPoll(Long pollId) {
    Poll p = polls.get(pollId);
    if (p == null) throw new NoSuchElementException("Poll not found");
    List<VoteOption> out = new ArrayList<>();
    for (Long oid : p.getOptionIds()) {
        VoteOption vo = options.get(oid);
        if (vo != null) out.add(vo);
    }
    return out;
}

}
*/