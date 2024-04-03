package com.example.project_v2.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

public class UserRequest {

    @Data
    public static class JoinDTO{
        @Size(min = 3, max = 20, message = "아이디는 3자 미만이거나 20자를 초과할 수 없습니다")
        @NotEmpty(message = "아이디를 입력하세요")
        private String username; // 로그인 ID

        @NotEmpty(message = "비밀번호를 입력하세요")
        private String password; // 비밀번호

        @NotEmpty(message = "성명을 입력하세요")
        private String name; // 유저 성명

        @NotEmpty(message = "전화번호를 입력하세요")
        private String tel; // 전화번호

        @NotEmpty(message = "생년월일을 입력하세요")
        private String birth; // 생년월일

        @NotEmpty(message = "이메일을 입력하세요")
        private String email; // 이메일

        @NotEmpty(message = "주소를 입력하세요")
        private String address; // 주소
        private Integer role; // 개인, 기업 구분

        public User toEntity(){
            return User.builder()
                    .username(username)
                    .password(password)
                    .name(name)
                    .tel(tel)
                    .birth(birth)
                    .email(email)
                    .address(address)
                    .role(role)
                    .build();
        }
    }

    @Data
    public static class LoginDTO {
        @Size(min = 3, max = 20, message = "아이디는 3자 미만이거나 20자를 초과할 수 없습니다")
        @NotEmpty(message = "아이디를 입력하세요")
        private String username; // 로그인 ID

        @NotEmpty(message = "비밀번호를 입력하세요")
        private String password; // 비밀번호
    }

    @Data
    public static class UpdateDTO {
        @Size(min = 3, max = 20, message = "아이디는 3자 미만이거나 20자를 초과할 수 없습니다")
        @NotEmpty(message = "아이디를 입력하세요")
        private String username; // 로그인 ID

        @NotEmpty(message = "비밀번호를 입력하세요")
        private String password; // 비밀번호

        @NotEmpty(message = "전화번호를 입력하세요")
        private String tel; // 전화번호

        @NotEmpty(message = "이메일을 입력하세요")
        private String email; // 이메일

        @NotEmpty(message = "주소를 입력하세요")
        private String address; // 주소

        // 이미지를 받기 위한 함수
        private String imageName; // 사진 이름
        private String encodedData; // base64 저장
    }
}
