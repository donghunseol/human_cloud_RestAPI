package com.example.project_v2.apply;

import com.example.project_v2.notice.Notice;
import com.example.project_v2.resume.Resume;
import com.example.project_v2.user.User;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

public class ApplyRequest {

    @Data
    public static class PassDTO{
        private Integer id; // 합격 번호

        @NotEmpty(message = "합격 여부를 선택해주세요")
        private boolean pass; // 합격 true,false

        private Integer resumeId; // 합격, 불합격 이력서 번호
        private Integer noticeId; // 지원 공고 번호

        public Apply toEntity(User user, Notice notice, Resume resume){
            return Apply.builder()
                    .user(user)
                    .pass(pass)
                    .resume(resume)
                    .notice(notice)
                    .build();
        }
    }

    @Data
    public static class SaveDTO{
        private Integer id; // 지원 번호
        private Integer noticeId; // 공고 번호
        private Integer resumeId; // 이력서 번호

        public Apply toEntity(User user, Notice notice, Resume resume){
            return Apply.builder()
                    .user(user)
                    .notice(notice)
                    .resume(resume)
                    .build();
        }
    }
}
