package org.valerijovich.receiver.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.valerijovich.receiver.dto.MessageDto;
import org.valerijovich.receiver.entity.UserEntity;

@Service
@Slf4j
public class MessageServiceImpl implements MessageService {

    private final KafkaTemplate<Object, UserEntity> kafkaMessageTemplate;
    private final UserService userService;
    private final DBInitializer dbInitializer;

    @Autowired
    public MessageServiceImpl(KafkaTemplate<Object, UserEntity> kafkaMessageTemplate,
                              UserService userService, DBInitializer dbInitializer) {
        this.kafkaMessageTemplate = kafkaMessageTemplate;
        this.userService = userService;
        this.dbInitializer = dbInitializer;
    }

    @Override
    @KafkaListener(topics = {"server.message"}, containerFactory = "singleFactory")
    public void consume(MessageDto dto) {
        log.info("Consume method received object from kafka: {}", dto);
        process(dto);
    }

    @Override
    public void process(MessageDto dto) {
        log.info("Process method found user in table with id = {}", dto.getMessage());
        produce(userService.findById((long) dto.getMessage()));
    }

    @Override
    public void produce(UserEntity userEntity) {
        log.info("Produce method sent this user as object to kafka: {}", userEntity);
        kafkaMessageTemplate.send("server.user", userEntity);
    }
}
