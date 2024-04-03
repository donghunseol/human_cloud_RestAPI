package com.example.project_v2.board;

import com.example.project_v2.user.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

public class BoardRequest {
    @Data
    public static class SaveDTO{
        @Size(min = 1, max = 20, message = "제목은 20자를 초과할 수 없습니다.")
        @NotEmpty(message = "제목을 입력하셔야 합니다")
        private String title;
        @NotEmpty(message = "내용을 입력하셔야 합니다")
        private String content;

        // 빌더 패턴
        public Board toEntity(User user) {
            return Board.builder()
                    .title(title)
                    .content(content)
                    .user(user)
                    .build();
        }
    }

    @Data
    public static class UpdateDTO{
        @Size(min = 1, max = 20, message = "제목은 1자 미만, 20자를 초과할 수 없습니다.")
        @NotEmpty(message = "제목을 입력하셔야 합니다")
        private String title;
        @NotEmpty(message = "내용을 입력하셔야 합니다")
        private String content;
    }
}
