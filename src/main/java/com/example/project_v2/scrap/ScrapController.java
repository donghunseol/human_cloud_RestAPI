package com.example.project_v2.scrap;

import com.example.project_v2._core.util.ApiUtil;
import com.example.project_v2.notice.Notice;
import com.example.project_v2.notice.NoticeJPARepository;
import com.example.project_v2.notice.NoticeService;
import com.example.project_v2.resume.Resume;
import com.example.project_v2.resume.ResumeJPARepository;
import com.example.project_v2.resume.ResumeService;
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
        User sessionUser = (User) session.getAttribute("sessionUser");
        scrapService.delete(id, sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(null));
    }

    // 스크랩 등록
    @PostMapping("/api/scraps/{id}")
    public ResponseEntity<?> save(@PathVariable Integer id, @RequestBody ScrapRequest.SaveDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        ScrapResponse.DTO respDTO = null;
        if (sessionUser.getRole() == 0) {
            Optional<Notice> noticeOP = noticeJPARepository.findById(id);
            if (noticeOP.isPresent()) {
                Notice notice = noticeOP.get();
                respDTO = scrapService.save(notice, reqDTO, sessionUser);
            }
        } else {
            Optional<Resume> resumeOP = resumeJPARepository.findById(id);
            if (resumeOP.isPresent()) {
                Resume resume = resumeOP.get();
                respDTO = scrapService.save(resume, reqDTO, sessionUser);
            }
        }
        return ResponseEntity.ok(new ApiUtil<>(respDTO));
    }
}