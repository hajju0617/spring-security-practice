package com.project.springsecuritypractice.service;

import com.project.springsecuritypractice.dto.JoinDTo;
import com.project.springsecuritypractice.entity.UserEntity;
import com.project.springsecuritypractice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public void JoinProcess(JoinDTo joinDTo) {
        if (userRepository.existsByUsername(joinDTo.getUsername())) {
            return;
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(joinDTo.getUsername());
        userEntity.setPassword(bCryptPasswordEncoder.encode(joinDTo.getPassword()));
        userEntity.setRole("ROLE_USER");
        userRepository.save(userEntity);
    }
}
