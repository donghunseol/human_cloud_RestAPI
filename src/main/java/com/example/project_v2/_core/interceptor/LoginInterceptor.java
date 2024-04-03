package com.example.project_v2._core.interceptor;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.project_v2._core.errors.exception.Exception401;
import com.example.project_v2._core.util.JwtUtil;
import com.example.project_v2.user.SessionUser;
import com.example.project_v2.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // jwt 토근
        String jwt = request.getHeader("Authorization");

        // interceptor 에서 안하면 throw 날리기가 힘들다!
        // 토큰 전달 검사
        if (jwt == null) {
            throw new Exception401("jwt 토큰을 전달해주세요");
        }

        // Bearer jwt토큰 -> 띄워쓰기를 유의해서 보자! 프로토콜 이다!
        jwt = jwt.replace("Bearer ", "");

        // 검증
        try {
            SessionUser sessionUser = JwtUtil.verify(jwt);

            System.out.println(jwt);

            // 임시 세선 (jsessionId 는 필요 없다!)
            HttpSession session = request.getSession();

            System.out.println(sessionUser.getId());

            session.setAttribute("sessionUser", sessionUser);
            return true;
        } catch (TokenExpiredException e) {
            throw new Exception401("토큰 만료시간이 지났어요. 다시 로그인하세요");
        } catch (JWTDecodeException e) {
            throw new Exception401("토큰이 유효하지 않습니다");
        } catch (Exception e) {
            e.printStackTrace(); // 개발 진행 시 TEST 보기
            // throw new Exception500(e.getMessage()); // 알 수 없는 오류 이니깐 500으로 다 던져 준다.
            throw new Exception401("비정상적인 접근입니다");
        }
    }
}

