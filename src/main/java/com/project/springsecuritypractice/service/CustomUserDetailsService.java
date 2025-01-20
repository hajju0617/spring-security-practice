package com.project.springsecuritypractice.service;

import com.project.springsecuritypractice.dto.CustomUserDetails;
import com.project.springsecuritypractice.entity.UserEntity;
import com.project.springsecuritypractice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username);
        System.out.println("로그인 요청 들어옴. loadUserByUsername 메서드 호출.");
        if (userEntity != null) {
            return new CustomUserDetails(userEntity);
        }
        return null;
    }
}
