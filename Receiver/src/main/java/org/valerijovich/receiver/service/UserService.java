package org.valerijovich.receiver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.valerijovich.receiver.entity.UserEntity;
import org.valerijovich.receiver.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void createUser(UserEntity userEntity) {
        userRepository.save(userEntity);
    }

    public UserEntity findById(Long userId){
        return userRepository.findById(userId).orElse(null);
    }
}
