## ERD
![image](https://github.com/user-attachments/assets/97779e61-f3ba-4d1d-8047-2cc1152a88f2)

## 📜 API 명세서

- **Post man**: https://documenter.getpostman.com/view/40450969/2sAYX5Jgu5

# 📝 Todo API Project

## 📌 프로젝트 개요
Todo API 프로젝트는 사용자가 할 일을 관리할 수 있도록 도와주는 RESTful API 서비스입니다. Spring Boot 기반으로 개발되었으며, 사용자의 할 일을 생성, 조회, 수정, 삭제하는 기능을 제공합니다.

## 🚀 주요 기능
- **사용자(User) 관리**: 사용자 생성 및 조회
- **할 일(Todo) 관리**
  - 할 일 추가
  - 할 일 목록 조회 (필터링 지원)
  - 할 일 수정 (비밀번호 검증 포함)
  - 할 일 삭제 (비밀번호 검증 포함)
- **예외 처리**: `@ControllerAdvice` 기반 글로벌 예외 처리
---


## 📂 프로젝트 구조
```
📦 todo-api-project
 ┣ 📂 src
 ┃ ┣ 📂 main
 ┃ ┃ ┣ 📂 java/com/example/todo
 ┃ ┃ ┃ ┣ 📂 controller  # API 컨트롤러
 ┃ ┃ ┃ ┣ 📂 service      # 서비스 레이어
 ┃ ┃ ┃ ┣ 📂 repository   # 데이터 접근 레이어
 ┃ ┃ ┃ ┣ 📂 entity       # 엔티티 정의
 ┃ ┃ ┃ ┣ 📂 dto          # DTO 클래스
 ┃ ┃ ┃ ┣ 📂 exception    # 예외 처리
 ┃ ┃ ┣ 📂 resources
 ┃ ┃ ┃ ┣ 📜 application.yml # 환경설정
 ┣ 📜 README.md
```
---


