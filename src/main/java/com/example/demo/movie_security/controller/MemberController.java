package com.example.demo.movie_security.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.movie_security.entity.MemberEntity;
import com.example.demo.movie_security.repository.MemberRepository;


@RestController
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody MemberEntity memberEntity) {

        String password = memberEntity.getPassword();
        memberEntity.setPassword(encoder.encode(password));
        memberRepository.save(memberEntity);

        return ResponseEntity.ok().body("註冊成功");
    }

    @PostMapping("/userLogin")
    public ResponseEntity<String> userLogin(Authentication authentication) {

        String username = authentication.getName();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        return ResponseEntity.ok().body("登入成功！帳號 " + username + " 的權限為: " + authorities);
    }
}
