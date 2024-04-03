package com.example.project_v2.user;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class SessionUser {
    private Integer id;
    private String username;
    private String name;
    private String tel;
    private String birth;
    private String email;
    private String address;
    private String image; // 이미지 경로만 저장
    private Integer role;
    private Timestamp createdAt;

    public SessionUser(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.name = user.getEmail();
        this.tel = user.getTel();
        this.birth = user.getBirth();
        this.email = user.getEmail();
        this.address = user.getAddress();
        this.image = user.getImage();
        this.role = user.getRole();
        this.createdAt = user.getCreatedAt();
    }

    public static User toEntity(SessionUser sessionUser){
        User user = new User();
        user.setId(sessionUser.getId());
        user.setUsername(sessionUser.getUsername());
        user.setName(sessionUser.getName());
        user.setTel(sessionUser.getTel());
        user.setBirth(sessionUser.getBirth());
        user.setEmail(sessionUser.getEmail());
        user.setAddress(sessionUser.getAddress());
        user.setImage(sessionUser.getImage());
        user.setRole(sessionUser.getRole());
        user.setCreatedAt(sessionUser.getCreatedAt());

        return user;
    }
}