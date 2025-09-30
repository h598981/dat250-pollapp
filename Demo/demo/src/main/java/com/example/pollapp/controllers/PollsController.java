package com.example.pollapp.controllers;

import com.example.pollapp.domain.Poll;
import com.example.pollapp.domain.VoteOption; // <-- add this
import com.example.pollapp.dto.CreatePollDto;
import com.example.pollapp.service.PollManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;
import java.util.List; // <-- add this
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/polls")
public class PollsController {

    private final PollManager manager;
    public PollsController(PollManager manager) { this.manager = manager; }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreatePollDto dto) {
        try {
            if (dto == null || dto.ownerUserId == null || dto.question == null || dto.question.isBlank()) {
                return ResponseEntity.badRequest().body("ownerUserId + question required");
            }
            Poll p = manager.createPoll(
                    dto.ownerUserId,
                    dto.question.trim(),
                    dto.isPublic,
                    dto.limitOnePerUser,
                    dto.publishedAt,
                    dto.deadlineAt,
                    dto.options,
                    dto.invitedUserIds
            );
            return ResponseEntity.created(URI.create("/api/polls/" + p.getId())).body(p);
        } catch (IllegalArgumentException | NoSuchElementException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public Collection<Poll> list() { return manager.listPolls(); }

    @GetMapping("/{id}")
    public ResponseEntity<Poll> get(@PathVariable Long id) {
        return manager.getPoll(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // NEW: list options for a poll
    @GetMapping("/{id}/options")
    public ResponseEntity<List<VoteOption>> options(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(manager.listOptionsByPoll(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
