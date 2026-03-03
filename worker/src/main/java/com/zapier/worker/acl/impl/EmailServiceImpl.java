package com.zapier.worker.acl.impl;

import com.zapier.worker.acl.EmailServiceACL;
import com.zapier.worker.domain.action.ActionResult;
import com.zapier.worker.domain.action.SendEmailAction;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component("EmailServiceImpl")
@Slf4j
public class EmailServiceImpl implements EmailServiceACL {

    @Value("${smtp.endpoint}")
    private String smtpEndpoint;

    @Value("${smtp.username}")
    private String smtpUsername;

    @Value("${smtp.password}")
    private String smtpPassword;

    @Value("${smtp.port:587}")
    private int smtpPort;

    @Value("${smtp.from:kaushiktop205.kd@gmail.com}")
    private String fromEmail;

    private JavaMailSender mailSender;

    @PostConstruct
    public void init(){
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(smtpEndpoint);
        sender.setUsername(smtpUsername);
        sender.setPassword(smtpPassword);
        sender.setPort(smtpPort);

        Properties props = sender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.debug", "false");

        this.mailSender = sender;
    }
    @Override
    public ActionResult sendEmail(SendEmailAction action) {
         try{
             log.info("Sending email via SMTP to {}", action.getRecipient());
             SimpleMailMessage message = new SimpleMailMessage();
             message.setFrom(fromEmail);
             message.setTo(action.getRecipient());
             message.setSubject(action.getSubject());
             message.setText(action.getBody());

             mailSender.send(message);

             log.info("Email sent successfully via SMTP to {}", action.getRecipient());

             return ActionResult.success("Email Sent Successfully","email_" + System.currentTimeMillis());
         }catch (Exception e){
             log.error("SMTP mail error", e);
             return ActionResult.failure("Failed to send email" + e.getMessage(), e);
         }
    }
}
