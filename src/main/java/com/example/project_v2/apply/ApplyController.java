package com.example.project_v2.apply;

import com.example.project_v2._core.interceptor.LoginInterceptor;
import com.example.project_v2._core.util.ApiUtil;
import com.example.project_v2._core.util.JwtUtil;
import com.example.project_v2.user.SessionUser;
import com.example.project_v2.user.User;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ApplyController {

    private final ApplyService applyService;
    private final HttpSession session;

    // 합격 불합격
    @PostMapping("/api/applies/pass/{id}")
    public ResponseEntity<?> resumePass(@PathVariable Integer id, @Valid @RequestBody ApplyRequest.PassDTO passDTO, Errors errors) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        ApplyResponse.DTO respDTO = applyService.resumePass(passDTO, sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(respDTO));
    }

    // 지원할 이력서 선택
    @GetMapping("/api/applies/{id}/resume-save")
    public ResponseEntity<?> resumeSave(@PathVariable Integer id) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        ApplyResponse.SelectResumeDTO selectResumeDTO = applyService.findById(id, sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(selectResumeDTO));
    }

    // 지원 취소
    @DeleteMapping("/api/applies/{id}/delete")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        applyService.delete(id, sessionUser.getId());
        return ResponseEntity.ok(new ApiUtil<>(null));
    }

    // 지원하기
    @PostMapping("/api/applies/{id}")
    public ResponseEntity<?> save(@Valid @RequestBody ApplyRequest.SaveDTO reqDTO, Errors errors) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        ApplyResponse.DTO respDTO = applyService.save(reqDTO, sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(respDTO));
    }
}

