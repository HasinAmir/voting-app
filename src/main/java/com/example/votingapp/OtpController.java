package com.example.votingapp.controller;

import com.example.votingapp.service.OtpService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/otp")
public class OtpController {
    private final OtpService otpService;

    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }

    @PostMapping("/send")
    public String sendOtp(@RequestParam String email, Model model) {
        otpService.generateOtp(email);
        model.addAttribute("email", email);
        return "otp-verification"; // Redirect to OTP verification page
    }

    @PostMapping("/verify")
    public String verifyOtp(@RequestParam String email, @RequestParam String otp, Model model) {
        if (otpService.validateOtp(email, otp)) {
            return "redirect:/register-success"; // OTP Verified, continue registration
        } else {
            model.addAttribute("error", "Invalid OTP. Please try again.");
            return "otp-verification"; // Stay on OTP page if failed
        }
    }
}
