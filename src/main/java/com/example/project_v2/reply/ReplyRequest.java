package com.example.project_v2.reply;

import com.example.project_v2.board.Board;
import com.example.project_v2.user.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

public class ReplyRequest {

    @Data
    public static class SaveDTO {
        private Integer boardId;

        @Size(max = 100, message = "댓글은 100자를 초과할 수 없습니다")
        @NotEmpty(message = "댓글을 입력해주세요")
        private String comment;

        public Reply toEntity(User sessionUser, Board board){
            return Reply.builder()
                    .comment(comment)
                    .board(board)
                    .user(sessionUser)
                    .build();
        }
    }
}
