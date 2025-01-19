package com.project.springsecuritypractice.controller;

import com.project.springsecuritypractice.dto.JoinDTo;
import com.project.springsecuritypractice.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class JoinController {
    private final JoinService joinService;

    @GetMapping("/join")
    public String joinPage() {
        return "join";
    }

    @PostMapping("/joinProc")
    public String joinProcess(JoinDTo joinDTo) {
        System.out.println("joinDTo.getUsername() = " + joinDTo.getUsername());
        System.out.println("joinDTo.getPassword() = " + joinDTo.getPassword());

        joinService.JoinProcess(joinDTo);

        return "redirect:/login";
    }
}
