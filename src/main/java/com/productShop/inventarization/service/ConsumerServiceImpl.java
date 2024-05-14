package com.productShop.inventarization.service;

import com.productShop.inventarization.model.Consumer;
import com.productShop.inventarization.model.dto.AddConsumerDto;
import com.productShop.inventarization.model.dto.GetConsumerDto;
import com.productShop.inventarization.repos.ConsumerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConsumerServiceImpl implements ConsumerService{

    private final ConsumerRepository consumerRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void addConsumer(AddConsumerDto consumerDto) {
        Optional<Consumer> consumer = consumerRepository.findByEmail(consumerDto.getEmail());
        if (consumer.isPresent()) {
            throw new RuntimeException("Such email already exists");
        }

        consumerRepository.save(Consumer.builder()
                        .username(consumerDto.getUsername())
                        .password(passwordEncoder.encode(consumerDto.getPassword()))
                        .email(consumerDto.getEmail())
                        .key(buildConsumerUniqueKey(consumerDto.getUsername(), consumerDto.getEmail()))
                .build());
    }

    @Override
    public String getConsumerKey(String email) {
        Consumer consumer = consumerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("No such email"));

        return consumer.getKey();
    }

    @Override
    public String getConsumer(GetConsumerDto consumerDto) {
        Consumer consumer = consumerRepository.findByEmail(consumerDto.getEmail())
                .orElseThrow(() -> new RuntimeException("No such email"));

        return consumer.getKey();
    }

    @Override
    public Consumer getConsumerByEmail(String email) {
        return consumerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("No such email"));
    }

    @Override
    public Consumer getConsumerByKey(String key) {
        return consumerRepository.findByKey(key)
                .orElseThrow(() -> new RuntimeException("No such email"));
    }

    private String buildConsumerUniqueKey(String username, String email) {
        String combinedString = username + email + UUID.randomUUID();
        return Base64.getEncoder().encodeToString(combinedString.getBytes());
    }
}
