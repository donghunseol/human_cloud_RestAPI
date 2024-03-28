package com.example.project_v2.notice;

import com.example.project_v2._core.errors.exception.Exception404;
import com.example.project_v2.skill.Skill;
import com.example.project_v2.skill.SkillJPARepository;
import com.example.project_v2.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class NoticeService {
    private final NoticeJPARepository noticeJPARepository;
    private final SkillJPARepository skillJPARepository;

    public NoticeResponse.DetailDTO noticeDetail(Integer noticeId, User sessionUser) {
        Notice notice = noticeJPARepository.findByIdJoinUser(noticeId)
                .orElseThrow(() -> new Exception404("공고 글을 찾을 수 없음"));

        return new NoticeResponse.DetailDTO(notice, sessionUser);
    }

    @Transactional
    public Notice save(NoticeRequest.SaveDTO reqDTO, User sessionUser) {
        Notice notice = noticeJPARepository.save(reqDTO.toEntity(sessionUser));

        // 1번 방법 -> skill 로 안받으면 reqDTO 의 id 값이 null 로 json이 뜬다
//        reqDTO.getSkills().forEach(skill -> {
//            skill.setNotice(notice);
//            skillJPARepository.save(skill.toEntity());
//        });

        // 2번 방법
        List<Skill> skills = new ArrayList<>();
        for (NoticeRequest.SaveDTO.SkillDTO skill : reqDTO.getSkills()) {
            Skill skillBuild = Skill.builder()
                    .name(skill.getName())
                    .role(skill.getRole())
                    .notice(notice)
                    .build();
            skills.add(skillBuild);
        }
        skills = skillJPARepository.saveAll(skills);
        notice.setSkills(skills);

        return noticeJPARepository.save(notice);
    }
}
