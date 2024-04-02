package com.example.project_v2.reply;

import com.example.project_v2._core.util.ApiUtil;
import com.example.project_v2.user.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ReplyController {
    private final ReplyService replyService;
    private final HttpSession session;

    // 댓글 작성
    @PostMapping("/api/replies")
    public ResponseEntity<?> save(@RequestBody ReplyRequest.SaveDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        Reply reply = replyService.save(reqDTO, sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(reqDTO));
    }

    // 댓글 삭제
    @DeleteMapping("/api/replies/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        replyService.delete(id, sessionUser.getId());
        return ResponseEntity.ok(new ApiUtil<>(null));
    }
}