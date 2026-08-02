# 2주차 — 해시와 컬렉션 ⭐ 최중요

> 오너: B / 마감: 금 23:59 (PR) · 일 23:59 (리뷰·머지)

## 학습 목표

- `equals`와 `hashCode`의 계약을 알고, 어겼을 때 무엇이 깨지는지 설명할 수 있다
- `HashMap`이 O(1)인 이유와 O(n)이 되는 조건을 안다
- **직접 만든 해시맵이 동작한다**

> 8주 전체에서 **가장 중요한 주차**입니다. 다른 주가 밀려도 여기는 밀지 마세요.

## 학습 주제

### 1. equals / hashCode 계약

- `Object.equals`의 5가지 규약 (반사·대칭·추이·일관·null)
- `hashCode` 계약: equals가 같으면 hashCode도 같아야 한다
- IDE 자동 생성 코드 뜯어보기, `record`가 대신 해주는 것

### 2. HashMap 내부

- 버킷 배열 + 해시 함수 + 인덱스 계산
- 충돌 처리: **체이닝(연결 리스트)**
- 로드 팩터(0.75)와 리사이징 시점
- 자바 8 이후 **버킷 트리화**(연결 리스트 → 레드-블랙 트리) 조건

### 3. 컬렉션 비교

- `HashMap` / `LinkedHashMap` / `TreeMap` — 순서와 복잡도
- `HashSet`은 사실 `HashMap`이다
- `ArrayDeque` vs `LinkedList` — 왜 큐/스택에 `ArrayDeque`를 권하는가
- `Stack` 클래스가 레거시인 이유

---

## 핵심 질문

1. `equals`만 재정의하고 `hashCode`는 그대로 두면, 그 객체를 `HashMap` 키로 썼을 때 무슨 일이 벌어지는가?
2. `HashMap` 키로 **가변 객체**를 쓰면 안 되는 이유는? (필드를 바꾸면 어떻게 되는가)
3. 모든 키의 `hashCode`가 같다면 조회 시간복잡도는 어떻게 되는가? 자바 8 이후에는?

---

## 필수 제출 파일

```
week02-hash-collections/{이름}/
├── notes.md
├── src/
│   ├── MyHashMap.java
│   └── (필요 시) Node.java
├── test/
│   └── MyHashMapTest.java
└── algorithm/
    ├── B10816.java
    ├── B1764.java
    └── B17298.java
```

### 구현 과제 — `MyHashMap`

**요구사항**

- 체이닝 방식 충돌 처리
- `put(K, V)` / `get(K)` / `remove(K)` / `size()` / `containsKey(K)`
- 로드 팩터 초과 시 **자동 리사이징**
- 제네릭 사용 (`MyHashMap<K, V>`)
- JUnit5 테스트 필수 — 충돌 상황 테스트 포함

**추가 실험 (필수)**

`hashCode()`가 **항상 0을 리턴**하는 클래스를 만들어 키로 넣고, 정상 클래스와 조회 성능을 비교하세요. 결과를 `notes.md`에 기록합니다.

**마무리**

구현이 끝난 뒤 OpenJDK의 `HashMap.java`를 열어 본인 코드와 비교하고, **다른 점 3가지**를 `notes.md`에 쓰세요.

---

## 알고리즘 문제 (3개)

| 유형 | 문제 |
|---|---|
| 해시 | [10816 - 숫자 카드 2](https://www.acmicpc.net/problem/10816) |
| 집합 | [1764 - 듣보잡](https://www.acmicpc.net/problem/1764) |
| 스택 | [17298 - 오큰수](https://www.acmicpc.net/problem/17298) |

> 17298은 **모노토닉 스택**입니다. 난이도가 한 단계 높으니 시간 배분 주의.

---

## 참고 자료

- 『이펙티브 자바』 아이템 10, 11 (equals, hashCode)
- OpenJDK `HashMap.java` — `putVal()`, `resize()`, `treeifyBin()`
