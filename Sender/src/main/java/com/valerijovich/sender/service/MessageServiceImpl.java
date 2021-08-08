package com.valerijovich.sender.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.valerijovich.sender.dto.MessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Slf4j
public class MessageServiceImpl implements MessageService {

    private final KafkaTemplate<Long, MessageDto> kafkaMessageTemplate;

    public MessageServiceImpl(KafkaTemplate<Long, MessageDto> kafkaMessageTemplate) {
        this.kafkaMessageTemplate = kafkaMessageTemplate;
    }

    @Scheduled(initialDelay = 5000, fixedDelay = 5000)
    @Override
    public void produce() {
        MessageDto dto = createDto();
        log.info("Sender sent object to kafka: {}", dto);
        kafkaMessageTemplate.send("server.message", dto);
    }

    private MessageDto createDto() {
        return new MessageDto(new Random().nextInt(10) + 1);
    }
}
