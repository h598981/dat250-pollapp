package com.example.pollapp.domain;

public class VoteOption {
    private Long id;
    private Long pollId;
    private String text;
    private int presentationOrder;

    public VoteOption() {}
    public VoteOption(Long id, Long pollId, String text, int presentationOrder) {
        this.id = id; this.pollId = pollId; this.text = text; this.presentationOrder = presentationOrder;
    }

    // getters/setters...



    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPollId() { return pollId; }
    public void setPollId(Long pollId) { this.pollId = pollId; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
}

