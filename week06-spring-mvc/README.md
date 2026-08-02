# 6주차 — Spring MVC & REST API

> 오너: C / 마감: 금 23:59 (PR) · 일 23:59 (리뷰·머지)

## 학습 목표

- HTTP 요청이 스프링 내부에서 처리되는 흐름을 설명할 수 있다
- CRUD REST API를 설계하고 구현할 수 있다
- 예외를 **어느 계층에서 잡아야 하는지** 판단할 수 있다

## 학습 주제

### 1. MVC 요청 흐름

- `DispatcherServlet` → `HandlerMapping` → `HandlerAdapter` → `Controller` → `MessageConverter`
- `@Controller` vs `@RestController`
- `@RequestBody` / `@ModelAttribute` / `@RequestParam` / `@PathVariable` 바인딩 차이

### 2. REST API 설계

- 자원 중심 URI 설계, HTTP 메서드와 상태 코드 선택
- 201 Created + Location 헤더, 204 No Content
- **DTO를 엔티티와 분리하는 이유** (순환 참조, 스펙 노출, 검증 위치)

### 3. 검증과 예외 처리

- `@Valid` + Bean Validation (`@NotNull`, `@Size` 등)
- 커스텀 예외 계층 설계
- `@RestControllerAdvice` + `@ExceptionHandler`로 **전역 처리**
- 일관된 에러 응답 포맷 설계

### 4. 테스트

- `@WebMvcTest` — 슬라이스 테스트
- `MockMvc`로 요청/응답 검증
- `@SpringBootTest`와의 차이 (무엇을 언제 쓰는가)

---

## 핵심 질문

1. `@RequestBody`와 `@ModelAttribute`는 각각 어떤 방식으로 값을 바인딩하는가? 언제 무엇을 쓰는가?
2. 엔티티를 그대로 응답으로 내보내면 어떤 문제가 생기는가? 3가지 이상.
3. 서비스 계층에서 던진 예외를 컨트롤러에서 try-catch 하지 않고 처리하려면 어떻게 하는가? 그게 더 나은 이유는?

---

## 필수 제출 파일

```
week06-spring-mvc/{이름}/
├── notes.md
├── src/main/java/
│   ├── controller/
│   ├── dto/
│   ├── service/
│   ├── repository/
│   └── exception/            # 커스텀 예외 + GlobalExceptionHandler
├── src/test/java/
│   └── controller/           # @WebMvcTest
├── build.gradle
└── algorithm/                # 2문제
```

### 과제 — CRUD REST API

**요구사항**

- 5주차 프로젝트에 **Controller 계층 추가**
- 최소 5개 엔드포인트 (조회 목록 / 조회 단건 / 생성 / 수정 / 삭제)
- **요청·응답 DTO 분리** (엔티티 직접 노출 금지)
- `@Valid`로 입력 검증
- `@RestControllerAdvice`로 전역 예외 처리 + 일관된 에러 응답 포맷
- `@WebMvcTest` 테스트 **3개 이상** (성공 / 검증 실패 / 없는 리소스)

**API 명세 작성 (필수)**

`notes.md`에 엔드포인트 표를 작성하세요.

| 메서드 | 경로 | 설명 | 성공 코드 | 실패 코드 |
|---|---|---|---|---|
| GET | /api/... | | 200 | 404 |

---

## 알고리즘 문제 (2개)

오너가 백준 실버 난이도로 2문제 선정 후 아래에 링크를 채웁니다.

| 유형 | 문제 |
|---|---|
| (선정) | |
| (선정) | |

---

## 참고 자료

- 김영한 - 스프링 MVC 1편/2편
- [Spring Web MVC Docs](https://docs.spring.io/spring-framework/reference/web/webmvc.html)
