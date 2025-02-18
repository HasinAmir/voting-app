package com.example.votingapp;
import com.example.votingapp.repository.VoterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import com.example.votingapp.Entity.Voter;
import org.springframework.web.servlet.ModelAndView;

import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
public class VotingAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(VotingAppApplication.class, args);
    }
}

@Controller
@RequestMapping("/")
class VotingController {

    private final VoterRepository voterRepository;
    private final AtomicInteger partyOneVotes = new AtomicInteger(0);
    private final AtomicInteger partyTwoVotes = new AtomicInteger(0);

    public VotingController(VoterRepository voterRepository) {
        this.voterRepository = voterRepository;
    }

    @GetMapping
    public String showInfoPage() {
        return "info";
    }

    @PostMapping("/register")
    public String registerVoter(@RequestParam("fullname") String fullname,
                                @RequestParam("nidNumber") String nidNumber,
                                @RequestParam("email") String email,
                                Model model) {
        Voter voter = new Voter();
        voter.setNidNumber(nidNumber);
        voter.setFullName(fullname);
        voter.setEmail(email);
        voterRepository.save(voter);

        model.addAttribute("fullname", fullname);
        model.addAttribute("nidNumber", nidNumber);
        model.addAttribute("email", email);
        return "voting"; // Redirect to the voting page
    }

    @PostMapping("/vote")
    public String vote(@RequestParam("nidNumber") String nidNumber, @RequestParam("party") String party) {
        Optional<Voter> voterOpt = voterRepository.findById(nidNumber);

        if (voterOpt.isEmpty()) {
            return "Error: Voter not found!";
        }

        Voter voter = voterOpt.get();
        if (voter.getEmail().equals("VOTED")) {
            return "Error: You have already voted!";
        }

        if ("party1".equalsIgnoreCase(party)) {
            partyOneVotes.incrementAndGet();
        } else if ("party2".equalsIgnoreCase(party)) {
            partyTwoVotes.incrementAndGet();

        }
        return "Voted successfully";
    }

    @GetMapping("/admin")
    public String showAdminPage(Model model) {
        model.addAttribute("partyOneVotes", partyOneVotes.get());
        model.addAttribute("partyTwoVotes", partyTwoVotes.get());
        return "admin";
    }
}
@RestController
@RequestMapping("/check-nid")
class VoterController {

    @Autowired
    private VoterRepository voterRepository;

    @PostMapping
    public Map<String, Boolean> checkNid(@RequestBody Map<String, String> request) {
        String nidNumber = request.get("nidNumber");
        boolean exists = voterRepository.existsByNidNumber(nidNumber);

        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return response;
    }
}
