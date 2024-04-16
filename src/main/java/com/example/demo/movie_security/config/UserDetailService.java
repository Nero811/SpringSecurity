package com.example.demo.movie_security.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.demo.movie_security.entity.MemberEntity;
import com.example.demo.movie_security.pojo.MemberPojo;
import com.example.demo.movie_security.repository.MemberEntityManager;
import com.example.demo.movie_security.repository.MemberRepository;

@Component
public class UserDetailService implements UserDetailsService{

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberEntityManager em;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberEntity member = memberRepository.findByEmail(username);

        if (member == null) {
            throw new UsernameNotFoundException("Member not found for: " + username);
        } 

        List<MemberPojo> memberPojos = em.findByMemberId(member.getMemberId());
        List<GrantedAuthority> authorities = convertToAuthorities(memberPojos);

        String name = member.getName();
        String password = member.getPassword();

        return new User(name, password, authorities);
    }

    public List<GrantedAuthority> convertToAuthorities(List<MemberPojo> memberPojos) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        memberPojos.forEach(member -> {authorities.add(new SimpleGrantedAuthority(member.getRoleName()));});
        return authorities;
    }
}
