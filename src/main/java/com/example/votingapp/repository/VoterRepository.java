// src/main/java/com/example/votingapp/repository/VoterRepository.java
package com.example.votingapp.repository;

import com.example.votingapp.model.Voter;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

interface repository extends JpaRepository<Voter, Long> {
    Optional<Voter> findByVotingNumber(String votingNumber);
    Optional<Voter> findByEmail(String email);
    long countByVotedFor(String votedFor);
}