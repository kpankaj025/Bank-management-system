package com.bank.config;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import com.bank.dto.EmailDto;
import com.bank.service.EmailService;

@Configuration
public class ProjectConfig {

    private final EmailService emailService;

    public ProjectConfig(EmailService emailService) {
        this.emailService = emailService;
    }

    private static final Logger logger = LoggerFactory.getLogger(ProjectConfig.class);

    @Bean
    public Consumer<Message<EmailDto>> emailConsumer() {

        return message -> {
            EmailDto payload = message.getPayload();
            logger.info("📧 Received Email Message: {}", payload.getBody());
            System.out.println(payload.getSubject());
            System.out.println(payload.getTo());
            emailService.sendEmail(payload.getTo(), payload.getSubject(), payload.getBody());
            System.out.println("Email sent successfully!");
        };
    }

    @Bean
    public Consumer<Message<EmailDto>> dailyEmail() {

        return message -> {
            EmailDto payload = message.getPayload();
            logger.info("📧 Received Daily Email Message: {}", payload.getBody());
            System.out.println(payload.getSubject());
            System.out.println(payload.getTo());
            emailService.sendEmail(payload.getTo(), payload.getSubject(), payload.getBody());
            System.out.println("Daily Email sent successfully!");
        };
    }
}
