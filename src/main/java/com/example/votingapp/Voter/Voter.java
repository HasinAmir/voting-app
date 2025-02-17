package com.example.votingapp;

import javax.persistence.*;

@Entity
public class Voter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @Column(unique = true)
    private String votingNumber;

    private String email;
    private boolean hasVoted = false;

    // Getters and Setters
}