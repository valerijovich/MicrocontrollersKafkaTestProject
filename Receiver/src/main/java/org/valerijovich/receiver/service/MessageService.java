package org.valerijovich.receiver.service;

import org.valerijovich.receiver.dto.MessageDto;
import org.valerijovich.receiver.entity.UserEntity;

public interface MessageService {

    void consume(MessageDto dto);

    void produce(UserEntity userEntity);

    void process(MessageDto dto);
}