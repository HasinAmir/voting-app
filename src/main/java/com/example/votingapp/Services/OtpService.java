package com.example.votingapp.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.*;

@Service
public class OtpService {
    @Autowired
    private JavaMailSender mailSender;

    private final ConcurrentHashMap<String, String> otpStorage = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public String generateAndSendOtp(String email) {
        String otp = OtpUtil.generateOtp();
        otpStorage.put(email, otp);

        // OTP expires in 5 minutes
        scheduler.schedule(() -> otpStorage.remove(email), 5, TimeUnit.MINUTES);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP is: " + otp + "\n\nValid for 5 minutes.");
        mailSender.send(message);

        return "OTP sent successfully to " + email;
    }

    public boolean verifyOtp(String email, String enteredOtp) {
        return otpStorage.containsKey(email) && otpStorage.get(email).equals(enteredOtp);
    }
}

class OtpUtil {
    public static String generateOtp() {
        return String.valueOf(new Random().nextInt(900000) + 100000); // Generates 6-digit OTP
    }
}