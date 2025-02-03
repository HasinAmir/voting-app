package com.example.votingapp;
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
class VotingController {

    private final AtomicInteger partyOneVotes = new AtomicInteger(0);
    private final AtomicInteger partyTwoVotes = new AtomicInteger(0);

    @GetMapping
    public String showInfoPage() {
        return "info";
    }
    //I added a register end point
    @PostMapping("/register")
    public String registerVoter(@RequestParam String fullname,
                                @RequestParam String votingnumber,
                                @RequestParam String email,
                                Model model) {
        // Store user details in the model to pass to the voting page
        model.addAttribute("fullname", fullname);
        model.addAttribute("votingnumber", votingnumber);
        model.addAttribute("email", email);

        return "voting";  // Redirects to voting.html
    }

    @PostMapping("/vote")
    @ResponseBody
    public String vote(@RequestParam String party) {
        if ("party1".equals(party)) {
            partyOneVotes.incrementAndGet();
        } else if ("party2".equals(party)) {
            partyTwoVotes.incrementAndGet();
        }
        System.out.println("Vote stats:");
        System.out.println("Party 1: " + partyOneVotes.get());
        System.out.println("Party 2: " + partyTwoVotes.get());
        return "Vote registered!";
    }

    @GetMapping("/admin")
    public String showAdminPage(Model model) {
        model.addAttribute("partyOneVotes", partyOneVotes.get());
        model.addAttribute("partyTwoVotes", partyTwoVotes.get());
        return "admin";
    }
}

