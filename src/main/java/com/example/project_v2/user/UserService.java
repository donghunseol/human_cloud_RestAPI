package com.example.project_v2.user;

import com.example.project_v2._core.errors.exception.Exception400;
import com.example.project_v2._core.errors.exception.Exception401;
import com.example.project_v2._core.errors.exception.Exception404;
import com.example.project_v2._core.util.JwtUtil;
import com.example.project_v2.apply.Apply;
import com.example.project_v2.apply.ApplyJPARepository;
import com.example.project_v2.notice.Notice;
import com.example.project_v2.notice.NoticeJPARepository;
import com.example.project_v2.notice.NoticeResponse;
import com.example.project_v2.resume.Resume;
import com.example.project_v2.resume.ResumeJPARepository;
import com.example.project_v2.resume.ResumeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserJPARepository userJPARepository;
    private final ResumeJPARepository resumeJPARepository;
    private final NoticeJPARepository noticeJPARepository;
    private final ApplyJPARepository applyJPARepository;

    public User sameCheck(String username) {
        User user = userJPARepository.findByUsername(username)
                .orElseThrow(() -> new Exception401("사용할 수 있는 아이디입니다"));
        return user;
    }

    @Transactional
    public UserResponse.DTO join(UserRequest.JoinDTO reqDTO) {
        Optional<User> userOP = userJPARepository.findByUsername(reqDTO.getUsername());

        if(userOP.isPresent()){
            throw new Exception400("중복된 유저네임입니다");
        }

        User user = userJPARepository.save(reqDTO.toEntity());

        return new UserResponse.DTO(user);
    }

    public String login(UserRequest.LoginDTO reqDTO) {
        User user = userJPARepository.findByUsernameAndPassword(reqDTO.getUsername(), reqDTO.getPassword())
                .orElseThrow(() -> new Exception401("인증되지 않았습니다"));

        String jwt = JwtUtil.create(user);
        JwtUtil.verify(jwt);

        return jwt;
    }

    @Transactional
    public SessionUser update(Integer id, UserRequest.UpdateDTO reqDTO) {
        User user = userJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("회원정보를 찾을 수 없습니다"));

        user.setUsername(reqDTO.getUsername());
        user.setPassword(reqDTO.getPassword());
        user.setTel(reqDTO.getTel());
        user.setEmail(reqDTO.getEmail());
        user.setAddress(reqDTO.getAddress());

        try {
            // 베이스 64로 들어오는 문자열을 바이트로 디코딩하기
            // 1. 데이터 전달
            byte[] decodedBytes = Base64.getDecoder().decode(reqDTO.getEncodedData());

            // 이미지 파일 넣기
            String imgFilename = UUID.randomUUID() + "_" + reqDTO.getImageName(); // 이미지 파일 오리지널 이름

            // 파일 저장 위치 설정
            Path imgPath = Paths.get("./src/main/resources/static/images/" + imgFilename);

            Files.write(imgPath, decodedBytes);
            user.setImage(imgFilename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new SessionUser(user);
    }

    // 사용자의 role 에 따라 메인페이지 화면 변경
    @Transactional
    public List<?> getMainPageByUserRole(SessionUser sessionUser, Pageable pageable) {
        List<?> resultList = new ArrayList<>();
        User user = userJPARepository.findById(sessionUser.getId())
                .orElseThrow(() -> new Exception404("존재 하지 않는 계정입니다"));

        if (user != null) { // 로그인시
            if (user.getRole() == 1) {
                // Role이 1인 경우 Resume 리스트 반환
                resultList = resumeJPARepository.findAll(pageable).stream()
                        .map(resume -> new ResumeResponse.ResumeListDTO((Resume) resume))
                        .toList();
            } else {
                // Role이 0인 경우 Notice 리스트 반환
                resultList = noticeJPARepository.findAll(pageable).stream()
                        .map(notice -> new NoticeResponse.NoticeListDTO((Notice) notice))
                        .toList();
            }
        } else { // 로그인하지 않은 경우 Notice 리스트 반환
            resultList = noticeJPARepository.findAll(pageable).stream()
                    .map(notice -> new NoticeResponse.NoticeListDTO((Notice) notice))
                    .toList();
        }
        return resultList;
    }

    // 메인페이지 스킬 검색 서비스
    @Transactional
    public List<?> getMainPageByUserRoleAndSkill(SessionUser sessionUser, String skillName, Pageable pageable) {
        User user = userJPARepository.findById(sessionUser.getId())
                .orElseThrow(() -> new Exception404("존재 하지 않는 계정입니다"));
        List<?> resultList = new ArrayList<>();

        if (user != null) { // 로그인시
            if (user.getRole() == 1) {
                // Role이 1인 경우 Resume 리스트 반환
                resultList = resumeJPARepository.findBySkill(skillName, pageable).stream()
                        .map(resume -> new ResumeResponse.ResumeListDTO((Resume) resume))
                        .toList();
            } else {
                // Role이 0인 경우 Notice 리스트 반환
                resultList = noticeJPARepository.findBySkill(skillName, pageable).stream()
                        .map(notice -> new NoticeResponse.NoticeListDTO((Notice) notice))
                        .toList();
            }
        } else { // 로그인하지 않은 경우 Notice 리스트 반환
            resultList = noticeJPARepository.findBySkill(skillName, pageable).stream()
                    .map(notice -> new NoticeResponse.NoticeListDTO((Notice) notice))
                    .toList();
        }
        return resultList;
    }

    // 마이페이지
    @Transactional
    public List<?> getMyPage(SessionUser sessionUser, Pageable pageable) {
        User user = userJPARepository.findById(sessionUser.getId())
                .orElseThrow(() -> new Exception404("존재 하지 않는 계정입니다"));
        List<?> myPageList = new ArrayList<>();

        if (user != null) { // 로그인시
            if (user.getRole() == 1) { // 기업
                // Role이 1인 경우 Resume 리스트 반환
                myPageList = noticeJPARepository.findByUser(user, pageable).stream()
                        .map(notice -> new NoticeResponse.NoticeListDTO((Notice) notice))
                        .toList();
            } else {
                // Role이 0인 경우 Notice 리스트 반환
                myPageList = resumeJPARepository.findByUser(user, pageable).stream()
                        .map(resume -> new ResumeResponse.ResumeListDTO((Resume) resume))
                        .toList();
            }
        } else { // 로그인을 하지 않을 경우
            throw new Exception401("회원정보를 찾을 수 없습니다.");
        }
        return myPageList;
    }

    @Transactional
    public List<Apply> findAppliesByUserId(Integer userId){
        return applyJPARepository.findAppliesByUserId(userId);
    }
}
