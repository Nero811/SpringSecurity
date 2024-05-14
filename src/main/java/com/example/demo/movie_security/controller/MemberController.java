package com.example.demo.movie_security.controller;

import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.movie_security.entity.MemberEntity;
import com.example.demo.movie_security.entity.MemberHasRoleEntity;
import com.example.demo.movie_security.entity.RoleEntity;
import com.example.demo.movie_security.repository.MemberHasRoleRepositroy;
import com.example.demo.movie_security.repository.MemberRepository;
import com.example.demo.movie_security.repository.RoleRepository;



@RestController
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberHasRoleRepositroy memberHasRoleRepositroy;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    private static final String ROLE_NOMAL_MEMBER = "ROLE_NORMAL_MEMBER";

    private Logger log = LoggerFactory.getLogger(MemberController.class);

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody MemberEntity memberEntity) {

        if (checkRegistry(memberEntity)) {
            return ResponseEntity.ok().body("用戶已註冊過");
        }
        
        String password = memberEntity.getPassword();
        memberEntity.setPassword(encoder.encode(password));
        memberRepository.save(memberEntity);

        MemberEntity member = memberRepository.findByEmail(memberEntity.getEmail());
        RoleEntity role = roleRepository.findByRoleName(ROLE_NOMAL_MEMBER);
        MemberHasRoleEntity memberHasRoleEntity = new MemberHasRoleEntity();
        try {
            memberHasRoleEntity.setMemberId(member.getMemberId());
            memberHasRoleEntity.setRoleId(role.getRoleId());
            memberHasRoleRepositroy.save(memberHasRoleEntity);   
        } catch (NullPointerException e) {
            log.error(e.getMessage());
        }

        return ResponseEntity.ok().body("註冊成功");
    }

    @PostMapping("/userLogin")
    public ResponseEntity<String> userLogin(Authentication authentication) {

        String username = authentication.getName();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        return ResponseEntity.ok().body("登入成功！帳號 " + username + " 的權限為: " + authorities);
    }

    private boolean checkRegistry(MemberEntity memberEntity){
        MemberEntity result = memberRepository.findByEmail(memberEntity.getEmail());
        if (result != null) {
            return true;
        }
        return false;
    }
}
