# 1주차 — 자바 실행 구조와 기본 자료형

## 학습 목표

- 자바 코드가 실행되기까지의 과정을 설명할 수 있다
- 알고리즘 문제에서 **시간 초과와 오버플로우를 스스로 진단**할 수 있다
- `ArrayList`가 배열 위에서 어떻게 동작하는지 안다

## 학습 주제

### 1. 실행 구조

- `.java` → `.class` → JVM 로딩 → 실행
- JVM / JRE / JDK 차이
- 클래스 로더가 하는 일 (개념 수준)

### 2. 원시타입과 오버플로우

- `int` / `long` 범위, 언제 `long`으로 승격해야 하는가
- 정수 나눗셈, 형변환 시 손실
- `double` 부동소수점 오차 → 비교 시 주의

### 3. String

- 불변(immutable)인 이유와 이점
- String Constant Pool, `==` vs `equals`
- 반복문 내 `+=`의 비용 → `StringBuilder`

### 4. 배열과 ArrayList 내부

- 배열의 메모리 배치, `System.arraycopy`
- `ArrayList` grow 전략 (1.5배 확장)
- `add` / `remove(index)` / `get`의 실제 시간복잡도
- `Arrays.asList()`의 함정 (고정 크기, `add` 불가)

### 5. 입출력

- `Scanner` vs `BufferedReader` + `StringTokenizer`
- `System.out.println` vs `BufferedWriter`

---

## 핵심 질문

`notes.md`에 **본인 문장으로** 답변합니다.

1. `String`이 불변인 것이 왜 이득인가? (힌트: 캐싱, 해시, 스레드)
2. 반복문 안에서 `s += x`를 10만 번 돌리면 내부적으로 무슨 일이 일어나는가? 시간복잡도는?
3. `int` 두 개를 더했는데 음수가 나왔다. 왜 그런가? 어떻게 막는가?

---

## 필수 제출 파일

```
week01-java-basics/{이름}/
├── notes.md              # 핵심 질문 3개 답변 + 학습 정리
├── notes/                 # (선택) 주제가 여러 개면 01-xxx.md로 나눠서
├── src/
│   └── FastIO.java       # 본인만의 입출력 템플릿 클래스
└── algorithm/
    ├── B1157.java
    ├── B2941.java
    └── B2559.java
```

### 구현 과제

**`FastIO` 입출력 템플릿 작성**

- `BufferedReader` + `StringTokenizer` 기반
- `nextInt()`, `nextLong()`, `nextLine()`, `println()` 정도 제공
- 앞으로 모든 알고리즘 문제에서 이 클래스를 재사용합니다

**성능 측정 (필수)**

10만 줄 정수 입력을 `Scanner`와 `BufferedReader`로 각각 읽어 **소요 시간을 직접 측정**하고 `notes.md`에 기록하세요. 숫자를 눈으로 봐야 이후 8주가 편해집니다.

> JVM은 처음 실행될 때(워밍업 전) 느리게 동작할 수 있습니다. 한 번만 측정하지 말고 **같은 실행 안에서 3~5회 반복 측정 후 평균**을 내세요. (매번 새로 실행해서 비교하면 안 됩니다.)

---

## 알고리즘 문제 (3개)

| 유형   | 문제                                                             |
| ------ | ---------------------------------------------------------------- |
| 문자열 | [1157 - 단어 공부](https://www.acmicpc.net/problem/1157)         |
| 구현   | [2941 - 크로아티아 알파벳](https://www.acmicpc.net/problem/2941) |
| 누적합 | [2559 - 수열](https://www.acmicpc.net/problem/2559)              |

> 풀이 코드에 **접근 방식 + 시간복잡도**를 주석으로 남기세요.
