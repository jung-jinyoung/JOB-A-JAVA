# 5주차 — 스프링 코어 (DI / IoC / AOP)

> 오너: B / 마감: 금 23:59 (PR) · 일 23:59 (리뷰·머지)
> **후반부 시작** — 알고리즘은 주 2문제로 축소합니다.

## 학습 목표

- DI가 **없을 때 무엇이 불편한지**를 코드로 체감한다
- 스프링 빈의 생성·주입·소멸 흐름을 설명할 수 있다
- 전반부 자바 코드가 스프링 위에서 돌아간다

> 이번 주의 핵심은 강의 따라치기가 아니라 **본인 코드를 옮기는 것**입니다.

## 학습 주제

### 1. IoC / DI

- 제어의 역전이란 무엇으로부터의 역전인가
- 생성자 주입 / 필드 주입 / 세터 주입 — 왜 **생성자 주입**을 권장하는가
- `@Component` / `@Bean` / `@Configuration` 차이
- 컴포넌트 스캔 동작 방식

### 2. 빈 생명주기와 스코프

- 싱글톤 스코프와 **상태를 가지면 안 되는 이유**
- 싱글톤 빈이 프로토타입 빈을 주입받을 때의 문제
- `@PostConstruct` / `@PreDestroy`

### 3. AOP

- 횡단 관심사 개념
- 프록시 기반 동작 (JDK 동적 프록시 / CGLIB)
- **자기 호출(self-invocation) 시 AOP가 안 먹는 이유** → 7주차 `@Transactional`과 직결

### 4. 동시성 기초 (신규)

싱글톤 빈이 상태를 가지면 안 되는 이유가 결국 동시성 문제이므로, 이번 주에 기초를 붙여서 다룹니다.

- 프로세스 vs 스레드, 멀티스레드 환경에서 공유 자원 문제
- `synchronized`가 막아주는 것과 못 막는 것
- `volatile`의 역할 (가시성 vs 원자성)
- `ExecutorService`와 스레드 풀 기본 사용법
- `ConcurrentHashMap`이 `HashMap` + `synchronized`보다 나은 이유 (2주차 해시 내용과 연결)

> 심화(락 프리 자료구조, 스레드 풀 튜닝 등)는 다음 사이클로 미룹니다. 이번 주는 "왜 싱글톤 빈에 상태를 두면 위험한가"를 코드로 확인하는 수준까지만 갑니다.

### 5. Spring Boot 구조

- 프로젝트 생성 (start.spring.io)
- `@SpringBootApplication`이 하는 일
- `application.yml` 기본 설정
- 계층 구조: Controller / Service / Repository

---

## 핵심 질문

1. 생성자 주입이 필드 주입보다 권장되는 이유를 3가지 이상 쓰라.
2. 싱글톤 빈에 인스턴스 변수를 두면 어떤 문제가 생기는가? 구체적 시나리오로 설명하라.
3. 같은 클래스 안에서 AOP가 걸린 메서드를 호출하면 왜 어드바이스가 동작하지 않는가?
4. 싱글톤 빈에 인스턴스 변수를 두고 여러 스레드가 동시에 요청을 보내면 실제로 어떤 문제가 재현되는가? (`synchronized`나 `ConcurrentHashMap`으로 어떻게 고치는가)

---

## 필수 제출 파일

```
week05-spring-core/{이름}/
├── notes.md
├── src/main/java/...         # 스프링 부트 프로젝트
├── src/test/java/...
├── build.gradle
└── algorithm/
    ├── B11047.java
    └── B1541.java
```

### 과제 — 전반부 코드를 스프링 부트로 이식

**요구사항**

- Spring Boot 프로젝트 생성 (Gradle, Java 17 이상)
- 4주차 `Graph` 클래스를 감싸는 **간단한 도메인 서비스** 구성
  - 예: 정점/간선 등록, 두 정점 간 최단 거리 조회
- 저장소는 **인터페이스로 추상화** + 인메모리 구현 (7주차에 JPA로 교체 예정)
- 계층 분리: `Controller` 없이 `Service` + `Repository`까지만 (Controller는 6주차)
- **생성자 주입** 사용
- `@SpringBootTest`로 빈 주입 확인 테스트 1개 이상

**추가 정리 (필수)**

이식 전 순수 자바 코드에서 **의존성을 직접 `new`로 만들던 부분**을 찾아, 스프링 적용 후 무엇이 달라졌는지 `notes.md`에 비교해 쓰세요. 이게 이번 주의 진짜 학습 포인트입니다.

**동시성 재현 (필수)**

싱글톤 빈에 일부러 인스턴스 변수(예: 요청 카운터)를 두고, 멀티스레드 요청(간단한 반복 호출 테스트)으로 값이 꼬이는 것을 재현하세요. 그 다음 `synchronized` 또는 `AtomicInteger`로 고친 결과를 `notes.md`에 비교 기록하세요.

---

## 알고리즘 문제 (2개, 축소)

| 유형 | 문제 |
|---|---|
| 그리디 | [11047 - 동전 0](https://www.acmicpc.net/problem/11047) |
| 그리디 | [1541 - 잃어버린 괄호](https://www.acmicpc.net/problem/1541) |

---

## 참고 자료

- 김영한 - 스프링 핵심 원리 기본편
- [Spring Framework Core Docs](https://docs.spring.io/spring-framework/reference/core.html)
