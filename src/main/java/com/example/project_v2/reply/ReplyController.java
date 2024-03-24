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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
    @DeleteMapping("/api/replies/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        replyService.댓글삭제(id, sessionUser.getId());
        return ResponseEntity.ok(new ApiUtil<>(null));

        @Transactional
        public void 댓글삭제 ( int replyID, int sessionUserId){
            Reply reply = replyJPARepository.findById(replyid)
                    .orElseThrow(() -> new Exception404("없는 댓글을 삭제할 수 업어요"));
            if (reply.getUser().getId() != SessionUserId) {
                throw new Exception403("댓글을 삭제할 권한이 없어요");
            }
            replyJPARepository.deletedById(replyId);
        }
    }
}