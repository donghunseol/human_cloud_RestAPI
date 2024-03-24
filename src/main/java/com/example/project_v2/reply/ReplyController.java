package com.example.project_v2.reply;

import com.example.project_v2._core.errors.exception.Exception403;
import com.example.project_v2._core.errors.exception.Exception404;
import com.example.project_v2._core.util.ApiUtil;
import com.example.project_v2.user.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class ReplyController {
    private final ReplyService replyService;
    private final ReplyJPARepository replyJPARepository;
    private final HttpSession session;

    // 댓글 작성
    @PostMapping("/api/replies")
    public ResponseEntity<?> save(@RequestBody ReplyRequest.SaveDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        Reply reply = replyService.댓글쓰기(reqDTO, sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(reply));
    }

    // 댓글 삭제
//    @DeleteMapping("/api/replies/{id}")
//    public ResponseEntity<?> delete(@PathVariable Integer replyId) {
//        User sessionUser = (User) session.getAttribute("sessionUser");
//        replyService.댓글삭제(replyId, sessionUser.getId());
////        return ResponseEntity.ok(new ApiUtil<>(null));
//            return "redirect:/board/"+ boardId;
//    }

    @PostMapping("/board/{boardId}/reply/{replyId}/delete")
    public String delete(@PathVariable Integer boardId, @PathVariable Integer replyId) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        replyService.댓글삭제(replyId, sessionUser.getId());
        return "redirect:/board/" + boardId;
    }
}
