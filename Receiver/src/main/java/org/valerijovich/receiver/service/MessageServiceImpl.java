package org.valerijovich.receiver.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.valerijovich.receiver.entity.UserEntity;

import java.util.Optional;

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

    // Метод consume получает из топика server.message кафки сообщение с числовым значением
    // и передаёт его в метод process
    @Override
    @KafkaListener(topics = {"server.message"}, containerFactory = "singleFactory")
    public void consume(Integer message) {
        log.info("Consume method received number from kafka: {}", message);
        process(message);
    }

    // Метод process по полученному числу находит в БД юзера с таким айдишником
    // и передаёт этого юзера в метод produce
    @Override
    public void process(Integer message) {
        log.info("Process method found user in the table with id = {}", message);
        Optional<UserEntity> userEntityOptional = userService.findById(message);
        userEntityOptional.ifPresent(this::produce);
    }

    // Метод produce отправляет полученного юзера в кафку в топик server.user
    @Override
    public void produce(UserEntity userEntity) {
        log.info("Produce method sent this user as object to kafka: {}", userEntity);
        kafkaMessageTemplate.send("server.user", userEntity);
    }
}
