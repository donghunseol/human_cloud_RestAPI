-- user
-- 개인
insert into user_tb(username, password, name, tel, birth, email, address, image, role, created_at)
values ('ssar', '1234', '김진환', '01012345678', '010101', 'ssar@nate.com', '쌀도 쌀시 쌀구 쌀동', '/images/google.png', 0, now());
insert into user_tb(username, password, name, tel, birth, email, address, image, role, created_at)
values ('cos', '1234', '온빛나', '01087654321', '020202', 'cos@nate.com', '코스도 코스시 코스구 코스동', '/images/google.png', 0,
        now());
insert into user_tb(username, password, name, tel, birth, email, address, image, role, created_at)
values ('love', '1234', '제갈민지', '01012341234', '030303', 'love@nate.com', '러브도 러브시 러브구 러브동', '/images/google.png', 0,
        now());
insert into user_tb(username, password, name, tel, birth, email, address, image, role, created_at)
values ('hello', '1234', '남궁채', '01012341234', '030303', 'love@nate.com', '러브도 러브시 러브구 러브동', '/images/google.png', 0,
        now());
-- 기업
insert into user_tb(username, password, name, tel, birth, email, address, image, role, created_at)
values ('comssar', '1234', '쌀회사', '01012344321', '111111', 'ssar@naver.com', '쌀도 쌀시 쌀구 쌀동', '/images/google.png', 1,
        now());
insert into user_tb(username, password, name, tel, birth, email, address, image, role, created_at)
values ('comcos', '1234', '코스회사', '01043211234', '110222', 'cos@naver.com', '코스도 코스시 코스구 코스동', '/images/kakao.jpg', 1,
        now());
insert into user_tb(username, password, name, tel, birth, email, address, image, role, created_at)
values ('comlove', '1234', '러브회사', '01011111111', '130313', 'love@naver.com', '러브도 러브시 러브구 러브동', '/images/naver.png', 1,
        now());

-- board
insert into board_tb(user_id, title, content, created_at)
values (1, '안녕하십니까', '처음 왔습니다 인사드립니다!', now());
insert into board_tb(user_id, title, content, created_at)
values (1, '자소서는 어떻게 하면 잘 쓸 수 있을까?', '잘 쓰면 된다 ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ', now());
insert into board_tb(user_id, title, content, created_at)
values (2, '오늘 날씨가 좋죠', '맑은 날씨만큼 열정적으로 일하겠습니다.', now());
insert into board_tb(user_id, title, content, created_at)
values (2, '반갑습니다.', '반가웠어요', now());
insert into board_tb(user_id, title, content, created_at)
values (2, '개발하면 나', '나는 개발이 많이 좋아요. 저를 뽑아주세요.', now());
insert into board_tb(user_id, title, content, created_at)
values (2, '취업하고 싶다', '진짜진짜진짜 하고 싶어요', now());
values (2, '로또 1등 되고 싶다~~~~~~~~', '언제 되려나요.......ㅜㅜㅜㅜㅜㅜㅜ', now());
insert into board_tb(user_id, title, content, created_at)
values (2, '기업 그 곳 가곳 싶다.', '나 뽑아줄래요?', now());
insert into board_tb(user_id, title, content, created_at)
values (2, '용의 해', '용띠는 아니지만 용용 열심히 할게요', now());
insert into board_tb(user_id, title, content, created_at)
values (2, '저는 회사, 주변환경이 최신이 아니어도  따지지 않고 열심히 하겠습니다.', '네xx가고 싶다', now());
insert into board_tb(user_id, title, content, created_at)
values (2, '3조 팀장 설동훈입니다', '팀장의 말이 법이다!!!!!', now());
insert into board_tb(user_id, title, content, created_at)
values (2, '3조 오른팔 서지민 입니다', '열심히 합시다!!!!', now());
insert into board_tb(user_id, title, content, created_at)
values (2, '3조의 왼팔 김완준 입니다', '담배 한대 피고 오겠습니다', now());
insert into board_tb(user_id, title, content, created_at)
values (2, '3조의 왼다리 이소연 입니다', '너 되게 똑똑하구나~~~~~', now());
insert into board_tb(user_id, title, content, created_at)
values (2, '3조의 오른다리 박동기 입니다', '토 할거 같아요 ㅜㅜㅜㅜㅜㅜㅜㅜ', now());
insert into board_tb(user_id, title, content, created_at)
values (2, '최강 3조 화이팅!!!!!', '열심히 배우고 공부합시다!!!!!!!!', now());

-- love
insert into love_tb(board_id, user_id, created_at)
values (1, 1, now());
insert into love_tb(board_id, user_id, created_at)
values (1, 2, now());
insert into love_tb(board_id, user_id, created_at)
values (3, 2, now());
insert into love_tb(board_id, user_id, created_at)
values (4, 1, now());
insert into love_tb(board_id, user_id, created_at)
values (5, 1, now());
insert into love_tb(board_id, user_id, created_at)
values (6, 1, now());
insert into love_tb(board_id, user_id, created_at)
values (7, 1, now());
insert into love_tb(board_id, user_id, created_at)
values (8, 1, now());
insert into love_tb(board_id, user_id, created_at)
values (9, 1, now());
insert into love_tb(board_id, user_id, created_at)
values (10, 1, now());
insert into love_tb(board_id, user_id, created_at)
values (11, 1, now());


-- reply
insert into reply_tb(user_id, board_id, comment, created_at)
values (1, 1, '안녕하세요', now());
insert into reply_tb(user_id, board_id, comment, created_at)
values (1, 2, '반가워요', now());
insert into reply_tb(user_id, board_id, comment, created_at)
values (2, 1, '졸리네요', now());

-- resume
insert into resume_tb(user_id, title, career, license, education, major, created_at)
values (1, '열정과 꿈이 가득한 저입니다.', '1년', '정보처리기사, SQLD', '쌀대학교, 졸업', '컴퓨터공학과', now());
insert into resume_tb(user_id, title, career, license, education, major, created_at)
values (2, '숨겨진 인재, 잘 찾아오셨습니다', '신입', '정보처리기사', '코스대학교, 졸업', '소프트웨어과', now());
insert into resume_tb(user_id, title, career, license, education, major, created_at)
values (1, 'AI보다 뛰어난 융통성있는 저입니다', '2년', 'SQLD', '러브대학교, 3학년', '정보처리데이터과', now());
insert into resume_tb(user_id, title, career, license, education, major, created_at)
values (2, '맡은 임무에 대해 책임감 있는 태도와 밝은 웃음이 있습니다', '3년', '데이터분석기사', '러브대학교, 4학년', '시스템공학과', now());
insert into resume_tb(user_id, title, career, license, education, major, created_at)
values (1, '소통하는 회사에 걸맞은 인재입니다.', '4년', '전기기사', '러브대학교, 1학년', '전기공학과', now());
insert into resume_tb(user_id, title, career, license, education, major, created_at)
values (1, '생각하고 배울 자세가 되어있습니다.', '5년', '정보처리산업기사', '러브대학교, 2학년', '기계공학과', now());

-- notice
insert into notice_tb(user_id, title, type, field, work_place, content, deadline, created_at)
values (7, '(주)네이버', '고용형태1', '개발분야1', '근무지1', '공고 내용1', '20240303', now());
insert into notice_tb(user_id, title, type, field, work_place, content, deadline, created_at)
values (5, '구글', '고용형태2', '개발분야2', '근무지2', '공고 내용2', '20240404', now());
insert into notice_tb(user_id, title, type, field, work_place, content, deadline, created_at)
values (7, '(주)네이버', '고용형태3', '개발분야3', '근무지3', '공고 내용3', '20240505', now());
insert into notice_tb(user_id, title, type, field, work_place, content, deadline, created_at)
values (5, '구글', '고용형태4', '개발분야4', '근무지4', '공고 내용4', '20240606', now());
insert into notice_tb(user_id, title, type, field, work_place, content, deadline, created_at)
values (6, '(주)KAKAO', '고용형태5', '개발분야5', '근무지5', '공고 내용5', '20240707', now());

-- scrap
insert into scrap_tb(user_id, resume_id, notice_id, created_at)
values (1, null, 1, now());
insert into scrap_tb(user_id, resume_id, notice_id, created_at)
values (1, null, 2, now());
insert into scrap_tb(user_id, resume_id, notice_id, created_at)
values (2, null, 3, now());
insert into scrap_tb(user_id, resume_id, notice_id, created_at)
values (4, 1, null, now());
insert into scrap_tb(user_id, resume_id, notice_id, created_at)
values (4, 2, null, now());
insert into scrap_tb(user_id, resume_id, notice_id, created_at)
values (5, 2, null, now());
insert into scrap_tb(user_id, resume_id, notice_id, created_at)
values (5, 3, null, now());




-- skill
-- 이력서 (개인)
insert into skill_tb(resume_id, notice_id, name, role)
values (1, null, 'JAVA', 0);
insert into skill_tb(resume_id, notice_id, name, role)
values (1, null, 'MySQL', 0);
insert into skill_tb(resume_id, notice_id, name, role)
values (1, null, 'HTML', 0);
insert into skill_tb(resume_id, notice_id, name, role)
values (1, null, 'CSS', 0);
insert into skill_tb(resume_id, notice_id, name, role)
values (3, null, 'C#', 0);
insert into skill_tb(resume_id, notice_id, name, role)
values (3, null, 'C++', 0);
insert into skill_tb(resume_id, notice_id, name, role)
values (3, null, 'JAVA', 0);
insert into skill_tb(resume_id, notice_id, name, role)
values (5, null, 'PYTHON', 0);
insert into skill_tb(resume_id, notice_id, name, role)
values (5, null, 'MariaDB', 0);
-- 공고 (회사)
insert into skill_tb(resume_id, notice_id, name, role)
values (null, 1, 'java', 0);
insert into skill_tb(resume_id, notice_id, name, role)
values (null, 2, 'java', 0);
insert into skill_tb(resume_id, notice_id, name, role)
values (null, 2, 'sql', 0);

-- apply
insert into apply_tb(user_id, resume_id, notice_id, pass, created_at)
values (1 ,1, 1, false, now());
insert into apply_tb(user_id, resume_id, notice_id, pass, created_at)
values (1, 2, 1, false, now());
insert into apply_tb(user_id, resume_id, notice_id, pass, created_at)
values (2, 2, 2, false, now());
