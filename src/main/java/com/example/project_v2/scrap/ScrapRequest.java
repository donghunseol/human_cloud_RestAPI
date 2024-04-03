package com.example.project_v2.scrap;

import com.example.project_v2.notice.Notice;
import com.example.project_v2.resume.Resume;
import com.example.project_v2.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ScrapRequest {


    @NoArgsConstructor
    @Data
    public static class SaveDTO {
        private Integer id;
        private User user;
        private Notice notice;
        private Resume resume;

        public Scrap toEntity(User sessionUser, Notice notice) {
            return Scrap.builder()
                    .user(sessionUser)
                    .notice(notice)
                    .build();
        }

        public Scrap toEntity(User sessionUser, Resume resume){
            return Scrap.builder()
                    .user(sessionUser)
                    .resume(resume)
                    .build();
        }
    }
}
