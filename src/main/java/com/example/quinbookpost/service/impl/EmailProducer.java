package com.example.quinbookpost.service.impl;
import com.example.quinbookpost.dto.EmailDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmailProducer {
    private static final String TOPIC = "email-topic";


    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendEmail(EmailDetails emailDetails) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String email = objectMapper.writeValueAsString(emailDetails);
            kafkaTemplate.send(TOPIC, email);
            System.out.println("Email Producer");
        } catch (JsonProcessingException e) {
            // Handle the exception or log the error
            e.printStackTrace();
        }
    }
}