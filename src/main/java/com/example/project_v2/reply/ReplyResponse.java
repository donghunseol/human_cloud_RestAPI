package com.example.project_v2.reply;

import com.example.project_v2.board.Board;
import com.example.project_v2.user.User;
import lombok.Data;

import java.sql.Timestamp;

public class ReplyResponse {
    @Data
    public static class DTO{
        private Integer id;
        private String comment;
        private Integer userId;
        private Integer boardId;
        private Timestamp createdAt;

        public DTO(Reply reply, User sessionUser, Board board) {
            this.id = reply.getId();
            this.comment = reply.getComment();
            this.userId = sessionUser.getId();
            this.boardId = board.getId();
            this.createdAt = reply.getCreatedAt();
        }
    }
}
