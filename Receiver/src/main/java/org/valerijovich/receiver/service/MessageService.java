package org.valerijovich.receiver.service;

import org.valerijovich.receiver.entity.UserEntity;

public interface MessageService {

    void consume(Integer message);

    void process(Integer message);

    void produce(UserEntity userEntity);
}