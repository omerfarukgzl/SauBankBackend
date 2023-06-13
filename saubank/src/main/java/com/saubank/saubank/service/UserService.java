package com.saubank.saubank.service;

import com.saubank.saubank.model.User;
import com.saubank.saubank.respository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(String id){
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı."));
    }

    public Boolean updateUser(User user){
        userRepository.save(user);
        return true;
    }
}
