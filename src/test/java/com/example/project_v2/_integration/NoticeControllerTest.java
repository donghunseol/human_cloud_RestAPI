package com.example.project_v2._integration;

import com.example.project_v2._core.util.JwtUtil;
import com.example.project_v2.notice.NoticeRequest;
import com.example.project_v2.user.SessionUser;
import com.example.project_v2.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class NoticeControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @Test
    public void save_test() throws Exception {
        // given
        Integer userId = 1;
        String username = "ssar";
        User user = User.builder()
                .id(userId)
                .username(username)
                .build();
        SessionUser sessionUser = new SessionUser(user);

        NoticeRequest.SaveDTO reqDTO = new NoticeRequest.SaveDTO();
        reqDTO.setTitle("title");
        reqDTO.setType("type");
        reqDTO.setField("field");
        reqDTO.setWorkPlace("workPlace");
        reqDTO.setDeadline("deadline");
        reqDTO.setContent("content");

        String requestBody = om.writeValueAsString(reqDTO);

        String jwt = JwtUtil.create(user); // jwt 생성

        ResultActions resultActions = mvc.perform(post("/api/notices")
                .header("Authorization", "Bearer " + jwt)
                .content(requestBody)
                .sessionAttr("sessionUser", sessionUser)
                .contentType(MediaType.APPLICATION_JSON));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("detailForm_test/test : " + responseBody);

        // then
        resultActions.andExpect(status().isOk());
    }
}
