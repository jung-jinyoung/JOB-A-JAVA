# 3주차 학습 정리 — dwd9999

## 1. 핵심 질문 답변

> 검색한 문장을 붙여넣지 말 것. 본인 문장으로 쓰고, 예시 코드를 곁들이면 좋음.

### Q1. 왜 자바는 원시타입과 객체 배열에 서로 다른 정렬 알고리즘을 사용하는가?

원시 타입은 값만 같으면 순서가 상관없는 단순함 때문에 속도가 빠른 정렬인 `DualPivotQuickSort`를 채용  
객체는 여러 복합적인 상태를 가지고 있으므로, 메모리를 더 사용하더라도 안정적인 정렬을 보장하는 `TimSort`를 채용

### Q2. `Comparator`에서 `(a, b) -> a.value - b.value`가 위험한 상황은 언제이고, 어떻게 고치는가?

`a.value - b.value`의 결과가 int 범위를 초과하는 경우 오버플로우가 발생해 결과가 다르게 나올 수 있음  
`<`, `>` 부호를 사용하여 정확히 구현하거나, `Integer.compare()` 메서드를 이용해야 함

### Q3. `List<Object> list = new ArrayList<String>()`이 컴파일 에러인 이유는? 어떻게 하면 문자열 리스트를 읽기 전용으로 받을 수 있는가?

무공변 성질로 인해 `<String>`은 `<Object>`의 하위 타입이 아니기 때문  
`List<? extends String> list = new ArrayList<String>()`과 같이 와일드카드를 사용해야 함

---

## 2. 학습 내용 정리

[정렬](topics/01-sort.md)  
[Comparator](topics/02-comparator.md)  
[제네릭](topics/03-generic.md)  
[힙과 정렬된 컬렉션](topics/04-heap.md)

---

## 3. 구현 과제 회고

- 어떻게 설계했는가  
  right를 갱신하는 조건에서 현재 mid에 있는 값이 목표값보다 큰지, 목표값보다 크거나 같은지를 구분하여 lower bound, upper bound를 구현함  
- 막혔던 지점  
  다행히 바로 잘 작동함
- JDK 실제 구현과 비교했을 때 다른 점  
  JDK는 mid의 값이 목표 값과 일치하는 순간 바로 반환함  
  JDK는 찾는 값이 없으면 음수를 반환함

---

## 4. 알고리즘 풀이

| 문제                                                                          | 접근 방식 | 시간복잡도 | 결과 |
|-------------------------------------------------------------------------------|-----------|------------|------|
| [가장 큰 수](https://school.programmers.co.kr/learn/courses/30/lessons/42746) | 정렬      | O(N)       | 통과 |
| [예산](https://school.programmers.co.kr/learn/courses/30/lessons/12982)       | 힙        | O(N)       | 통과 |
| [더 맵게](https://school.programmers.co.kr/learn/courses/30/lessons/42626)    | 그리디    | O(N)       | 통과 |

---

## 5. 아직 모르겠는 것

1. `DualPivotQuicksort`는 최신 Java에서 성능이 `O(N^2)`까지 저하되는 것을 최대한 해결했다고 들음  
그럼 그냥 뇌빼고 쓰면 되는 수준으로 해결 한것인지?  
아직 내부 로직이 정확히 어떻게 되는지 몰라 알아보고 싶음  
