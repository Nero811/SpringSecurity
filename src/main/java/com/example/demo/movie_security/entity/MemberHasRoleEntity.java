package com.example.demo.movie_security.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "member_has_role")
public class MemberHasRoleEntity {

    @Id
    @Column(name = "member_has_role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer memberHasRoleId;

    @Column(name = "member_id")
    private Integer memberId;

    @Column(name = "role_id")
    private Integer roleId;
}
