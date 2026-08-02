# 3주차 — 정렬 · 제네릭 · 힙

> 오너: C / 마감: 금 23:59 (PR) · 일 23:59 (리뷰·머지)

## 학습 목표

- 자바가 원시타입과 객체에 **다른 정렬 알고리즘**을 쓰는 이유를 설명할 수 있다
- `Comparator`를 자유롭게 조합할 수 있다
- 제네릭 타입 소거의 결과로 무엇이 불가능해지는지 안다

## 학습 주제

### 1. 정렬

- `Arrays.sort(int[])` → **듀얼 피벗 퀵소트** (불안정, in-place)
- `Arrays.sort(Object[])` / `Collections.sort` → **팀소트** (안정 정렬)
- 왜 다른가? → **정렬 안정성**이 객체에서만 의미가 있기 때문
- `int[]` 퀵소트 저격(anti-quicksort) 문제와 회피법

### 2. Comparator

- `Comparable`(자연 순서) vs `Comparator`(외부 기준)
- `Comparator.comparing().thenComparing().reversed()`
- `compareTo`에서 `a - b`가 위험한 경우 (오버플로우) → `Integer.compare` 사용

### 3. 제네릭

- 타입 소거(type erasure)와 그 결과
- 와일드카드: `? extends T`(생산자) / `? super T`(소비자) — PECS
- `new T[]`가 안 되는 이유

### 4. 힙과 정렬된 컬렉션

- 이진 힙 구조, sift up / sift down
- `PriorityQueue` — 순회 시 정렬 순서가 아닌 이유
- `TreeMap` / `TreeSet`과 레드-블랙 트리, `floorKey`/`ceilingKey`

---

## 핵심 질문

1. 왜 자바는 원시타입과 객체 배열에 서로 다른 정렬 알고리즘을 사용하는가?
2. `Comparator`에서 `(a, b) -> a.value - b.value`가 위험한 상황은 언제이고, 어떻게 고치는가?
3. `List<Object> list = new ArrayList<String>()`이 컴파일 에러인 이유는? 어떻게 하면 문자열 리스트를 읽기 전용으로 받을 수 있는가?

---

## 필수 제출 파일

```
week03-sort-generic/{이름}/
├── notes.md
├── notes/                     # (선택) 주제가 여러 개면 01-xxx.md로 나눠서
├── src/
│   └── BinarySearch.java     # lowerBound / upperBound
├── test/
│   └── BinarySearchTest.java
└── algorithm/
    ├── B11650.java
    ├── B2805.java
    └── B1927.java
```

### 구현 과제 — 이분 탐색 유틸

**요구사항**

- `lowerBound(int[] arr, int target)` — target 이상이 처음 나오는 인덱스
- `upperBound(int[] arr, int target)` — target 초과가 처음 나오는 인덱스
- 경계 조건 테스트 필수 (없는 값, 맨 앞, 맨 뒤, 중복 다수)
- `Arrays.binarySearch`와 동작이 어떻게 다른지 `notes.md`에 정리

### 소스 읽기 과제 (구현 대체)

`PriorityQueue.java`의 `siftUp` / `siftDown`을 읽고, **힙에 원소가 삽입될 때의 동작을 그림이나 글로 설명**하세요.

---

## 알고리즘 문제 (3개)

| 유형 | 문제 |
|---|---|
| 정렬/Comparator | [11650 - 좌표 정렬하기](https://www.acmicpc.net/problem/11650) |
| 매개변수 탐색 | [2805 - 나무 자르기](https://www.acmicpc.net/problem/2805) |
| 우선순위 큐 | [1927 - 최소 힙](https://www.acmicpc.net/problem/1927) |

> 2805는 **답의 범위를 이분 탐색**하는 유형입니다. `long` 사용에 주의하세요 (1주차 오버플로우와 연결).

---

## 참고 자료

- 『이펙티브 자바』 아이템 14 (Comparable), 31 (PECS)
- OpenJDK `PriorityQueue.java`, `DualPivotQuicksort.java`
