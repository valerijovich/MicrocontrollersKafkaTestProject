package com.valerijovich.sender.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Slf4j
public class MessageServiceImpl implements MessageService {

    private final KafkaTemplate<Long, Integer> kafkaMessageTemplate;

    @Autowired
    public MessageServiceImpl(KafkaTemplate<Long, Integer> kafkaMessageTemplate) {
        this.kafkaMessageTemplate = kafkaMessageTemplate;
    }

    // Метод produce каждые 5 секунд отправляет сообщение в топик кафки под именем server.message
    @Scheduled(initialDelay = 5000, fixedDelay = 5000)
    @Override
    public void produce() {
        Integer message = createRandomNumber();
        log.info("Sender sent random number to kafka: {}", message);
        kafkaMessageTemplate.send("server.message", message);
    }

    // Метод createRandomNumber генерирует случайное число от 1 до 10 включительно
    private Integer createRandomNumber() {
        return new Random().nextInt(10) + 1;
    }
}
