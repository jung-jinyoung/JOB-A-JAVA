# 7주차 — JPA와 트랜잭션 ⚠️ 난이도 최고

> 오너: A / 마감: 금 23:59 (PR) · 일 23:59 (리뷰·머지)
> 이번 주는 JPA 비중이 크므로 알고리즘은 2문제로 최소화합니다.

## 학습 목표

- 영속성 컨텍스트가 대신 해주는 일을 설명할 수 있다
- N+1 문제를 **직접 재현하고 해결**해봤다
- `@Transactional`이 동작하지 않는 상황을 안다

## 학습 주제

### 1. 영속성 컨텍스트

- 엔티티 생명주기: 비영속 / 영속 / 준영속 / 삭제
- 1차 캐시, 동일성 보장
- **변경 감지(Dirty Checking)** — save 호출 없이 UPDATE가 나가는 이유
- 쓰기 지연, `flush` 시점

### 2. 연관관계 매핑

- `@OneToMany` / `@ManyToOne` / `@ManyToMany`(지양)
- **연관관계 주인**과 `mappedBy`
- 지연 로딩(LAZY) vs 즉시 로딩(EAGER) — **왜 LAZY가 기본이어야 하는가**
- 양방향 매핑 시 무한 순환 참조 → DTO 분리로 해결 (6주차와 연결)

### 3. N+1 문제

- 발생 원리
- 해결: **페치 조인**, `@EntityGraph`, `default_batch_fetch_size`
- 각 방법의 한계 (페치 조인 + 페이징 문제)

### 4. 트랜잭션

- `@Transactional` 기본 동작, `readOnly = true`
- **프록시 기반이라 발생하는 한계** (5주차 AOP 자기 호출과 동일한 원리)
- private 메서드에 붙이면 안 되는 이유

---

## 핵심 질문

1. 조회한 엔티티의 필드를 바꾸기만 했는데 UPDATE 쿼리가 나갔다. 어떤 원리인가?
2. N+1은 정확히 언제 발생하는가? 페치 조인으로 해결할 때의 한계는 무엇인가?
3. 같은 클래스의 다른 메서드를 호출했더니 `@Transactional`이 동작하지 않았다. 왜인가? 어떻게 해결하는가?

---

## 필수 제출 파일

```
week07-jpa/{이름}/
├── notes.md
├── notes/                     # (선택) 주제가 여러 개면 01-xxx.md로 나눠서
├── src/main/java/
│   ├── entity/
│   ├── repository/           # JpaRepository
│   └── ...
├── src/test/java/
│   └── repository/           # @DataJpaTest
├── src/main/resources/
│   └── application.yml       # show-sql, format-sql 활성화
├── build.gradle
└── algorithm/                # 2문제
```

### 과제 — 인메모리 저장소를 JPA로 교체

**요구사항**

- H2 인메모리 DB 사용
- 엔티티 2개 이상 + **연관관계 1개 이상** (`@ManyToOne` 필수)
- 5주차에 인터페이스로 추상화한 Repository를 **JPA 구현으로 교체** — 상위 계층 코드 변경 최소화
- `@DataJpaTest` 테스트 2개 이상
- `spring.jpa.show-sql=true` + `format_sql`로 **실제 쿼리 확인**

### N+1 재현 및 해결 (필수)

`notes.md`에 다음을 순서대로 기록하세요.

1. N+1이 발생하는 코드와 **실제 출력된 쿼리 로그**
2. 쿼리가 몇 번 나갔는지, 왜 그런지
3. 페치 조인 적용 후 **쿼리 로그 비교**
4. 이 방법의 한계

> 로그를 캡처하지 않고 "이론상 이렇습니다"로 쓰면 리뷰에서 반려하세요. 직접 보는 게 목적입니다.

---

## 알고리즘 문제 (2개, 축소)

JPA 주차는 부담이 크므로 쉬운 난이도로 2문제만 유지합니다. 밀린 문제가 있다면 이번 주에 우선 보충하세요.

> **BOJ 점검으로 인한 임시 대체 (프로그래머스)** — 백준이 복구되면 원래 문제(14889, 9251)로 되돌립니다. 아래 두 문제는 원본과 소재가 상당히 다른 **낮은 확신도의 대체**입니다. 기법(완전탐색 조합 / 2차원 DP 테이블) 연습용으로만 참고하세요.

| 유형 | 문제 | 비고 |
|---|---|---|
| 구현/시뮬레이션 | [메뉴 리뉴얼](https://school.programmers.co.kr/learn/courses/30/lessons/72411) | 완전탐색(조합) 기법만 유사, 소재는 원본(두 팀 최소 차이)과 많이 다름 |
| DP | [정수 삼각형](https://school.programmers.co.kr/learn/courses/30/lessons/43105) | 2차원 DP 테이블을 채우는 방식은 유사하나, LCS(두 문자열 비교)와는 점화식이 다름 |

---

## 참고 자료

- 김영한 - 자바 ORM 표준 JPA 프로그래밍 기본편
- [Spring Data JPA Docs](https://docs.spring.io/spring-data/jpa/reference/)
