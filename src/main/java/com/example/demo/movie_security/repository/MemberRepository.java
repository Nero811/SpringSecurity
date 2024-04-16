package com.example.demo.movie_security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.movie_security.entity.MemberEntity;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Integer> {

    MemberEntity findByEmail(String username);
    // MemberEntity findByMemberId(Integer memberId);
}
