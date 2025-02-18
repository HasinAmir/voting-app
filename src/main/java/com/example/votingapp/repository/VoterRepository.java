package com.example.votingapp.repository;

import com.example.votingapp.Entity.Voter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoterRepository extends JpaRepository<Voter, String> {
    boolean existsByNidNumber(String nidNumber);
}