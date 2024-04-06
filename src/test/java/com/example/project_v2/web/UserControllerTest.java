package com.example.project_v2.web;

import com.example.project_v2._core.util.JwtUtil;
import com.example.project_v2.user.User;
import com.example.project_v2.user.UserJPARepository;
import com.example.project_v2.user.UserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test") // 테스트 시 "test" 프로파일을 사용하도록 설정
@Sql("classpath:db/data.sql") // 테스트 실행 전 "db/data.sql" 스크립트 실행
@AutoConfigureMockMvc // Spring MVC 테스트를 위해 MockMvc 자동 구성
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK) // 스프링 부트 테스트 환경 설정, 웹 환경은 MOCK으로 설정
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private EntityManager em;

    @Autowired
    private UserJPARepository userJPARepository;

    @Test
    public void join_success_test() throws Exception {
        // given
        String username = "happy";
        String password = "1234";
        String name = "김행복";
        String tel = "01012341234";
        String birth = "051225";
        String email = "happy@nate.com";
        String address = "경상남도 창원시 성산구";
        Integer role = 0;

        UserRequest.JoinDTO joinUser = new UserRequest.JoinDTO();
        joinUser.setUsername(username);
        joinUser.setPassword(password);
        joinUser.setName(name);
        joinUser.setTel(tel);
        joinUser.setBirth(birth);
        joinUser.setEmail(email);
        joinUser.setAddress(address);
        joinUser.setRole(role);

        String jwt = JwtUtil.create(joinUser.toEntity()); // jwt 생성
        System.out.println();

        // when
        // Spring MVC 테스트를 위한 MockMvc 객체를 사용하여 /users/login 경로로 POST 요청을 보냅니다.
        // 이 때 "Authorization" 헤더에 "Bearer "와 함께 jwt 토큰을 붙여서 보냅니다.
        ResultActions resultActions = mvc.perform(post("/users/join")
                .header("Authorization", "Bearer " + jwt)
                .contentType(MediaType.APPLICATION_JSON) // 컨텐츠 타입을 JSON으로 설정
                .content(new ObjectMapper().writeValueAsString(joinUser))); // JSON 문자열을 요청 본문에 추가

        // 위에서 보낸 요청의 결과로 받은 응답 본문을 문자열로 가져옵니다.
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("join_jwt_test/responseBody : " + responseBody);

        // then
        resultActions.andExpect(status().isOk());
    }

    @Test
    public void join_fail_test() throws Exception {
        // given
        String username = "no";
        String password = "1234";
        String name = "김아니";
        String tel = "01012341234";
        String birth = "051225";
        String email = "no@nate.com";
        String address = "경상남도 창원시 성산구";
        Integer role = 0;

        UserRequest.JoinDTO joinUser = new UserRequest.JoinDTO();
        joinUser.setUsername(username);
        joinUser.setPassword(password);
        joinUser.setName(name);
        joinUser.setTel(tel);
        joinUser.setBirth(birth);
        joinUser.setEmail(email);
        joinUser.setAddress(address);
        joinUser.setRole(role);

        String jwt = JwtUtil.create(joinUser.toEntity()); // jwt 생성
        System.out.println();

        // when
        // Spring MVC 테스트를 위한 MockMvc 객체를 사용하여 /users/login 경로로 POST 요청을 보냅니다.
        // 이 때 "Authorization" 헤더에 "Bearer "와 함께 jwt 토큰을 붙여서 보냅니다.
        ResultActions resultActions = mvc.perform(post("/users/join")
                .header("Authorization", "Bearer " + jwt)
                .contentType(MediaType.APPLICATION_JSON) // 컨텐츠 타입을 JSON으로 설정
                .content(new ObjectMapper().writeValueAsString(joinUser))); // JSON 문자열을 요청 본문에 추가

        // 위에서 보낸 요청의 결과로 받은 응답 본문을 문자열로 가져옵니다.
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("join_jwt_test/responseBody : " + responseBody);

        // then
        resultActions.andExpect(status().isOk());
    }
}
