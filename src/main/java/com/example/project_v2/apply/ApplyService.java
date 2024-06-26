package com.example.project_v2.apply;

import com.example.project_v2._core.errors.exception.Exception403;
import com.example.project_v2._core.errors.exception.Exception404;
import com.example.project_v2._core.util.ApiUtil;
import com.example.project_v2.notice.Notice;
import com.example.project_v2.notice.NoticeJPARepository;
import com.example.project_v2.resume.Resume;
import com.example.project_v2.resume.ResumeJPARepository;
import com.example.project_v2.user.SessionUser;
import com.example.project_v2.user.User;
import com.example.project_v2.user.UserJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ApplyService {
    private final ApplyJPARepository applyJPARepository;
    private final NoticeJPARepository noticeJPARepository;
    private final ResumeJPARepository resumeJPARepository;
    private final UserJPARepository userJPARepository;

    // 지원 취소
    @Transactional
    public void delete(Integer applyId, Integer sessionUserId) {
        Apply apply = applyJPARepository.findById(applyId)
                .orElseThrow(() -> new Exception404("지원번호 찾을 수 없음"));

        if (apply.getUser().getId().equals(sessionUserId)) {
            throw new Exception403("지원 취소 권한이 없습니다.");

        }
        applyJPARepository.save(apply);
    }

    // 합격, 불합격
    @Transactional
    public ApplyResponse.DTO resumePass(ApplyRequest.PassDTO passDTO, SessionUser sessionUser) {
        User user = userJPARepository.findById(sessionUser.getId())
                .orElseThrow(() -> new Exception404("존재 하지 않는 계정입니다"));

        Apply apply = applyJPARepository.findById(passDTO.getId())
                .orElseThrow(() -> new Exception404("지원 번호를 찾을 수 없습니다"));

        if (apply.getNotice() == null) {
            throw new Exception404("해당 공고가 없습니다");
        }

        if (user.getRole() != 1) {
            throw new Exception403("권한이 없습니다");
        } // 권한(기업 로그인 했을때만 유효)이 없으면 안됨


        apply.setPass(passDTO.isPass());
        Apply passApply = applyJPARepository.save(apply);
        return new ApplyResponse.DTO(passApply);
    }


    // 이력서 조회
    public ApplyResponse.SelectResumeDTO findById(Integer applyId, SessionUser sessionUser) {
        User user = userJPARepository.findById(sessionUser.getId())
                .orElseThrow(() -> new Exception404("존재 하지 않는 계정입니다"));
        Apply apply = applyJPARepository.findById(applyId)
                .orElseThrow(() -> new Exception404("이력서를 찾을 수 없습니다"));
        return new ApplyResponse.SelectResumeDTO(apply, user);
    }

    // 지원 하기
    @Transactional
    public ApplyResponse.DTO save(ApplyRequest.SaveDTO reqDTO, SessionUser sessionUser) {
        User user = userJPARepository.findById(sessionUser.getId())
                .orElseThrow(() -> new Exception404("존재 하지 않는 계정입니다"));
        Optional<Notice> optionalNotice = reqDTO.getNoticeId() == null ? Optional.empty() : noticeJPARepository.findById(reqDTO.getNoticeId());
        if (!optionalNotice.isPresent()) {
            // 공고 ID가 null이 아니지만 찾을 수 없는 경우의 처리 로직
            throw new Exception404("공고를 찾을 수 없습니다");
        }

        Optional<Resume> optionalResume = reqDTO.getResumeId() == null ? Optional.empty() : resumeJPARepository.findById(reqDTO.getResumeId());
        if (!optionalResume.isPresent()) {
            // 이력서 ID가 null이 아니지만 찾을 수 없는 경우의 처리 로직
            throw new Exception404("이력서를 찾을 수 없습니다");
        }

        // Apply 엔티티 생성 시, Notice와 Resume이 null일 수 있으므로 Optional의 orElse(null)을 사용하여 처리
        Apply apply = applyJPARepository.save(reqDTO.toEntity(user, optionalNotice.orElse(null), optionalResume.orElse(null)));
        return new ApplyResponse.DTO(apply);
    }


}
