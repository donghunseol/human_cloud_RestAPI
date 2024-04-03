package com.example.project_v2.scrap;

import com.example.project_v2._core.util.ApiUtil;
import com.example.project_v2.notice.Notice;
import com.example.project_v2.notice.NoticeJPARepository;
import com.example.project_v2.notice.NoticeService;
import com.example.project_v2.resume.Resume;
import com.example.project_v2.resume.ResumeJPARepository;
import com.example.project_v2.resume.ResumeService;
import com.example.project_v2.user.SessionUser;
import com.example.project_v2.user.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class ScrapController {

    private final HttpSession session;
    private final ScrapService scrapService;
    private final NoticeJPARepository noticeJPARepository;
    private final ResumeJPARepository resumeJPARepository;

    // 스크랩 삭제
    @DeleteMapping("/api/scraps/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        scrapService.delete(id, sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(null));
    }

    // 스크랩 등록
    @PostMapping("/api/scraps/{id}")
    public ResponseEntity<?> save(@PathVariable Integer id, @RequestBody ScrapRequest.SaveDTO reqDTO) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        ScrapResponse.DTO respDTO = scrapService.save(id, reqDTO, sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(respDTO));
    }
}