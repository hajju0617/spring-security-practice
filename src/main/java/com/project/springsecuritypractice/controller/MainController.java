package com.project.springsecuritypractice.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

@Controller
public class MainController {
    @GetMapping("/")
    public String mainPage(Model model) {
        String id = SecurityContextHolder.getContext().getAuthentication().getName();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
//        GrantedAuthority auth = iter.next();
//        String role = auth.getAuthority();

            //권한 2개 이상 처리하기.
        List<String> roles = new ArrayList<>();

        for (GrantedAuthority authority : authorities) {
            String role = authority.getAuthority();
            roles.add(role);
        }


        model.addAttribute("id", id);
        model.addAttribute("role", roles);
        return "main";
    }
}
