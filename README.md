# 📓 social
스프링부트 + JSP 소셜로그인 + 무한댓글 가능한 게시판 구현

---

## 🗣 프로젝트 소개
팀 프로젝트를 하며 아쉬웠던 점을 보안, 추가를 위해 만들었습니다.
네이버, 카카오, 구글을 활용한 로그인 기능과 함께 게시판을 구현하였습니다.

## ⏳ 개발기간
- 2024.01.02 ~ 현재진행중

### ⚙️ 개발환경
- `Java 11`
- `war`
- `Maven`
- `JQuery`
- **IDE** : Eclipse
- **Framewordk** : Springboot(2.x)
- **Database** : MySql(8.0.28) Workbench 사용
- **ORM** : JPA

## 🖥️배포
- AWS ubuntu 사용
- https://www.hjsocial.com

## 📌 주요기능
#### 로그인
* DB값 검증
* 소셜 API(네이버,카카오,구글)
* 로그인 쿠키(cookie) 및 세션(Session) 생성
#### 게시판
* 게시판 목록
  - 타입별/ 검색어 필터
  - 페이지네이션
  - 새게시글 등록
* 게시판 상세
  - 좋아요 기능
  - 조회수 기능
  - 게시글 수정
  - 게시글 댓글달기, 무한 대댓글(계층형댓글)
  - 댓글 수정, 삭제
  - 댓글 페이지네이션

—

