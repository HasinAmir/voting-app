package com.example.votingapp;
import com.example.votingapp.model.Voter;
import com.example.votingapp.repository.VoterRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
public class VotingAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(VotingAppApplication.class, args);
    }
}

@Controller
@RequestMapping("/")
public class VotingController {

    private final AtomicInteger partyOneVotes = new AtomicInteger(0);
    private final AtomicInteger partyTwoVotes = new AtomicInteger(0);
    private final VoterRepository voterRepository;

    public VotingController(VoterRepository voterRepository) {
        this.voterRepository = voterRepository;
    }

    @GetMapping
    public String showInfoPage() {
        return "info";
    }

    @PostMapping("/register")
    public String registerVoter(@RequestParam String fullname,
                                @RequestParam String votingnumber,
                                @RequestParam String email,
                                Model model) {
        if (!voterRepository.existsByVotingNumber(votingnumber)) {
            Voter voter = new Voter(votingnumber, fullname, email);
            voterRepository.save(voter);
        }

        model.addAttribute("fullname", fullname);
        model.addAttribute("votingnumber", votingnumber);
        model.addAttribute("email", email);
        return "voting";
    }

    @PostMapping("/vote")
    @ResponseBody
    public String vote(@RequestParam String party, @RequestParam String votingnumber) {
        Voter voter = voterRepository.findById(votingnumber).orElse(null);
        if (voter == null) {
            return "Error: Voter not found!";
        }
        if (voter.isHasVoted()) {
            return "Error: You have already voted!";
        }

        if ("party1".equals(party)) {
            partyOneVotes.incrementAndGet();
        } else if ("party2".equals(party)) {
            partyTwoVotes.incrementAndGet();
        }

        voter.setHasVoted(true);
        voterRepository.save(voter);

        return "Vote registered!";
    }

    @GetMapping("/admin")
    public String showAdminPage(Model model) {
        model.addAttribute("partyOneVotes", partyOneVotes.get());
        model.addAttribute("partyTwoVotes", partyTwoVotes.get());
        return "admin";
    }
}

