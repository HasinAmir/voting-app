package com.example.votingapp;

import com.example.votingapp.Services.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/otp")
public class OtpController {
    @Autowired
    private OtpService otpService;

    @PostMapping("/generate")
    public String generateOtp(@RequestParam String email) {
        return otpService.generateAndSendOtp(email);
    }

    @PostMapping("/verify")
    public String verifyOtp(@RequestParam String email, @RequestParam String otp) {
        return otpService.verifyOtp(email, otp) ? "OTP Verified!" : "Invalid or Expired OTP!";
    }
}
