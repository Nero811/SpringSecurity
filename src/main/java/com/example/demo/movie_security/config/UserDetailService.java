package com.example.demo.movie_security.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.demo.movie_security.entity.MemberEntity;
import com.example.demo.movie_security.repository.MemberRepository;

@Component
public class UserDetailService implements UserDetailsService{

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberEntity member = memberRepository.findByEmail(username);

        if (member == null) {
            throw new UsernameNotFoundException("Member not found for: " + username);
        } 

        List<GrantedAuthority> authorities = new ArrayList<>();

        String name = member.getName();
        String password = member.getPassword();

        return new User(name, password, authorities);
    }
}
