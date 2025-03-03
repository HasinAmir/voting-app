package com.example.votingapp.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class OtpService {
    private final JavaMailSender mailSender;
    private final Map<String, String> otpStorage = new HashMap<>();
    private final Random random = new Random();

    public OtpService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public String generateOtp(String email) {
        String otp = String.format("%06d", random.nextInt(999999)); // Generate 6-digit OTP
        otpStorage.put(email, otp);
        sendOtpEmail(email, otp);
        return otp;
    }

    public boolean validateOtp(String email, String userOtp) {
        return otpStorage.containsKey(email) && otpStorage.get(email).equals(userOtp);
    }

    private void sendOtpEmail(String email, String otp) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false);

            helper.setTo(email);
            helper.setSubject("Your OTP for Voting");
            helper.setText("Your One-Time Password (OTP) is: " + otp + ". It is valid for 5 minutes.", false);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
