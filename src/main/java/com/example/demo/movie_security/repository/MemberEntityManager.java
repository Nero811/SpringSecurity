package com.example.demo.movie_security.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.movie_security.pojo.MemberPojo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Repository
public class MemberEntityManager {

    @Autowired
    private EntityManager em;

    private static final String query = """
            SELECT new com.example.demo.movie_security.pojo.MemberPojo(m.memberId ,mhr.roleId ,r.roleName) from MemberEntity m inner join MemberHasRoleEntity mhr on m.memberId = mhr.memberId
            left join RoleEntity r on mhr.roleId = r.roleId
            where m.memberId = :memberId
                """;
    
    public List<MemberPojo> findByMemberId(Integer memberId) {

        List<MemberPojo> memberPojos = null;
        try {
            TypedQuery<MemberPojo> typedQuery = em.createQuery(query, MemberPojo.class);
            typedQuery.setParameter("memberId", memberId);
            memberPojos = typedQuery.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return memberPojos;
    }

}
