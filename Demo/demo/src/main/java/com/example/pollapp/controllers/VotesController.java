package com.example.pollapp.controllers;

import com.example.pollapp.domain.Vote;
import com.example.pollapp.dto.CreateVoteDto;
import com.example.pollapp.service.PollManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/polls/{pollId}/votes")
public class VotesController {
    private final PollManager manager;
    public VotesController(PollManager manager){ this.manager = manager; }

    @PostMapping
    public ResponseEntity<?> vote(@PathVariable Long pollId,
                                  @RequestBody CreateVoteDto dto,
                                  @RequestHeader(value = "X-User-Id", required = false) Long userIdHeader){
        try{
            Long effectiveUserId = (dto.userId != null) ? dto.userId : userIdHeader; 
            Vote v = manager.vote(pollId, effectiveUserId, dto.optionId);
            return ResponseEntity.created(URI.create("/api/polls/"+pollId+"/votes/"+v.getId())).body(v);
        } catch (IllegalStateException | NoSuchElementException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Vote>> list(@PathVariable Long pollId){
        try{
            return ResponseEntity.ok(manager.listVotesByPoll(pollId));
        } catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
    }
}
