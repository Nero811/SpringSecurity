package com.example.demo.movie_security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.movie_security.entity.MemberHasRoleEntity;

@Repository
public interface MemberHasRoleRepositroy extends JpaRepository<MemberHasRoleEntity, Integer> {

}
