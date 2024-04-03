package com.example.project_v2.board;

import com.example.project_v2.love.Love;
import com.example.project_v2.reply.Reply;
import com.example.project_v2.user.User;
import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class BoardResponse {

    @Data
    public static class DTO{
        private Integer id;
        private String title;
        private String content;
        private Integer userId;
        private Timestamp createdAt;

        public DTO(Board board) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.userId = board.getUser().getId();
            this.createdAt = board.getCreatedAt();
        }
    }

    @Data
    public static class DetailDTO {
        private int id;
        private String title;
        private String content;
        private int userId;
        private String username;
        private boolean isBoardOwner;

        private List<ReplyDTO> replies = new ArrayList<>();
        private List<LoveDTO> loves = new ArrayList<>();

        public DetailDTO(Board board, User sessionUser) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.userId = board.getUser().getId();
            this.username = board.getUser().getUsername();
            this.isBoardOwner = false;
            if (sessionUser != null) {
                if (sessionUser.getId() == userId) {
                    isBoardOwner = true;
                }
            }
            this.replies = board.getReplies().stream().map(reply -> new ReplyDTO(reply, sessionUser)).toList();
            this.loves = board.getLoves().stream().map(love -> new LoveDTO(love, sessionUser)).toList();
        }

        @Data
        public static class ReplyDTO {
            private int id;
            private String comment;
            private int userId;
            private String username;
            private boolean isReplyOwner;

            public ReplyDTO(Reply reply, User sessionUser) {
                this.id = reply.getId();
                this.comment = reply.getComment();
                this.userId = reply.getUser().getId();
                this.username = reply.getUser().getUsername();
                this.isReplyOwner = false;
                if (sessionUser != null) {
                    if (sessionUser.getId() == userId) {
                        isReplyOwner = true;
                    }
                }
            }
        }

        @Data
        public static class LoveDTO {
            private int id;
            private int userId;
            private int boardId;
            private boolean isLoveOwner;

            public LoveDTO(Love love, User sessionUser) {
                this.id = love.getId();
                this.userId = love.getUser().getId();
                this.boardId = love.getBoard().getId();
                this.isLoveOwner = false;
                if (sessionUser != null) {
                    if (sessionUser.getId() == userId) {
                        isLoveOwner = true;
                    }
                }
            }
        }
    }

    @Data
    public static class MainDTO {

        private int id; // 게시판 번호
        private String title; // 게시글 제목
        private String username; // 작성자


        public MainDTO(Board board) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.username = board.getUser().getUsername();
        }
    }
}
