package com.example.project_v2.love;

import com.example.project_v2.board.Board;
import com.example.project_v2.user.User;
import lombok.Data;


public class LoveResponse {

    @Data
    public static class DTO{
        private Integer id;
        private Integer userId;
        private Integer boardId;

        public DTO(Love love, User sessionUser, Board board) {
            this.id = love.getId();
            this.userId = sessionUser.getId();
            this.boardId = board.getId();
        }
    }

}
