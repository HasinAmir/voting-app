package com.example.votingapp;

import javax.persistence.*;

@Entity
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String party;

    @OneToOne
    @JoinColumn(name = "voter_id")
    private Voter voter;

    // Getters and Setters
}