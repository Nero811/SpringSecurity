package com.example.demo.movie_security.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.movie_security.dto.SubscribeRequest;
import com.example.demo.movie_security.entity.MemberEntity;
import com.example.demo.movie_security.entity.MemberHasRoleEntity;
import com.example.demo.movie_security.entity.RoleEntity;
import com.example.demo.movie_security.repository.MemberHasRoleRepositroy;
import com.example.demo.movie_security.repository.MemberRepository;
import com.example.demo.movie_security.repository.RoleRepository;

@RestController
public class SubscribeController {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MemberHasRoleRepositroy memberHasRoleRepositroy;

    private static final String ROLE = "ROLE_VIP_MEMBER";

    private Logger log = LoggerFactory.getLogger(SubscribeController.class);

    @PostMapping("/subscribe")
    public ResponseEntity<String> subscribe(@RequestBody SubscribeRequest request) {
        MemberEntity memberEntity = null;
        try {
            List<MemberEntity> members = createMemberEntitys(request.getEmail());
            RoleEntity role = roleRepository.findByRoleName(ROLE);
            if (members.size() != 0) {
                memberEntity = members.get(0);
                if (!checkSubscribe(memberEntity, role)) {
                    MemberHasRoleEntity memberHasRoleEntity = createMemberHasRoleEntity(memberEntity, role);
                    memberHasRoleRepositroy.save(memberHasRoleEntity);
                } else {
                    return ResponseEntity.ok("用戶已訂閱，無須再次訂閱 !!");
                }
            }
        } catch (NullPointerException | IllegalArgumentException e) {
            log.error(e.getMessage());
        }
        return ResponseEntity.ok("訂閱成功，請刪除Cookie再重新登入 !!");
    }

    @PostMapping("/unSubscribe")
    public ResponseEntity<String> unSubscribe(@RequestBody SubscribeRequest request) {
        MemberEntity memberEntity = null;
        MemberHasRoleEntity memberHasRoleEntity = null;

        try {
            List<MemberEntity> members = createMemberEntitys(request.getEmail());
            RoleEntity role = roleRepository.findByRoleName(ROLE);

            if (members.size() > 0) {
                memberEntity = members.get(0);
                memberHasRoleEntity = createMemberHasRoleEntity(memberEntity, role);
            }

            List<MemberHasRoleEntity> memberHasRoleEntities = memberHasRoleRepositroy.findAll(Example.of(memberHasRoleEntity));

            if (memberHasRoleEntities.size() > 0) {
                memberHasRoleRepositroy.delete(memberHasRoleEntities.get(0));
            } else {
                return ResponseEntity.ok("用戶沒有訂閱，無須取消訂閱");
            }
        } catch (NullPointerException | IllegalArgumentException e) {
            log.error(e.getMessage());
        }
        return ResponseEntity.ok("取消訂閱成功!! 請刪除Cookie重新登入");
    }

    private List<MemberEntity> createMemberEntitys(String email) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setEmail(email);
        return memberRepository.findAll(Example.of(memberEntity));
    }

    private MemberHasRoleEntity createMemberHasRoleEntity(MemberEntity memberEntity, RoleEntity roleEntity) {
        return MemberHasRoleEntity.builder()
                .memberId(memberEntity.getMemberId())
                .roleId(roleEntity.getRoleId())
                .build();
    }

    private boolean checkSubscribe(MemberEntity memberEntity, RoleEntity role) {
        MemberHasRoleEntity memberHasRoleEntity = new MemberHasRoleEntity();
        memberHasRoleEntity.setMemberId(memberEntity.getMemberId());
        memberHasRoleEntity.setRoleId(role.getRoleId());
        List<MemberHasRoleEntity> result = memberHasRoleRepositroy.findAll(Example.of(memberHasRoleEntity));
        if (result.size() > 0) {
            return true;
        }
        return false;
    }
}
