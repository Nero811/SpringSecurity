package com.example.demo.movie_security.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberPojo {

    private Integer memberId;
    private Integer roleId;
    private String roleName;

    // public MemberPojo(Integer memberId, Integer roleId,  String roleName) {
    //     this.memberId = memberId;
    //     this.roleId = roleId;
    //     this.roleName = roleName;
    // }
}
