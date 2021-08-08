package org.valerijovich.receiver.service;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.valerijovich.receiver.entity.UserEntity;

import javax.annotation.PostConstruct;

@Slf4j
@Service
public class DBInitializer{
    private final Logger logger = LoggerFactory.getLogger(DBInitializer.class);

    UserService userService;

    @Autowired
    public DBInitializer(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    void init(){
        logger.info("Starting Database initialization...");

        for (int i = 1; i <= 10; i++) {
            UserEntity user = new UserEntity();
            user.setId((long) i);
            user.setEmail("johndoe" + i + "@gmail.com");
            user.setName("John Doe " + i);
            userService.createUser(user);
        }

        logger.info("Database initialization completed!");
    }
}
