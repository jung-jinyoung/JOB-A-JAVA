# 3주차 학습 정리 — 정진영

## 1. 핵심 질문 답변

### Q1. 왜 자바는 원시 타입과 객체 배열에 서로 다른 정렬 알고리즘을 사용하는가?

`int[]`와 같은 원시 타입 배열은 같은 값끼리 위치가 바뀌어도 값만으로는 차이가 없다. 따라서 같은 값의 기존 순서를 유지하는 안정 정렬의 이점이 크지 않다. 자바는 원시 타입 배열에 적은 추가 메모리로 원본 배열 안에서 값을 교환하는 듀얼 피벗 퀵소트 계열을 사용한다.

반면 객체는 정렬 기준이 같더라도 서로 다른 정보를 가진 객체일 수 있다. 예를 들어 철수와 민수의 점수가 같더라도 서로 다른 학생이므로, 점수 정렬 후에도 기존 순서를 유지하는 것이 의미가 있다. 그래서 객체 배열에는 안정 정렬인 TimSort 계열을 사용한다. TimSort는 이미 정렬된 구간인 `run`을 찾아 병합하므로 부분적으로 정렬된 데이터도 활용할 수 있다.

```text
Arrays.sort(int[])
→ 듀얼 피벗 퀵소트 중심
→ 불안정 정렬, in-place

Arrays.sort(Object[])
→ TimSort 계열
→ 안정 정렬
```

### Q2. `Comparator`에서 `(a, b) -> a.value - b.value`가 위험한 상황은 언제이고, 어떻게 고치는가?

뺄셈 결과가 `int`의 범위를 벗어나면 오버플로우가 발생해 결과의 부호가 바뀔 수 있다. Comparator는 반환값의 부호로 두 값의 순서를 결정하므로, 부호가 바뀌면 큰 값을 작은 값으로 잘못 판단할 수 있다.

예를 들어 다음 계산의 수학적인 결과는 `2,147,483,648`이지만 `int` 범위를 넘어 음수가 된다.

```java
int a = Integer.MAX_VALUE;
int b = -1;

int result = a - b;
```

따라서 뺄셈 대신 두 값을 직접 비교하는 `Integer.compare()`를 사용한다.

```java
// 위험
(a, b) -> a.value - b.value

// 안전한 오름차순
(a, b) -> Integer.compare(a.value, b.value)

// 안전한 내림차순
(a, b) -> Integer.compare(b.value, a.value)
```

### Q3. `List<Object> list = new ArrayList<String>()`이 컴파일 에러인 이유는? 문자열 리스트를 읽기 전용으로 받으려면 어떻게 해야 하는가?

`String`은 `Object`의 하위 타입이지만 `List<String>`은 `List<Object>`의 하위 타입이 아니다. 이를 허용하면 `List<Object>` 변수를 통해 정수 등 다른 타입을 추가할 수 있고, 실제로 같은 객체를 가리키는 `List<String>`의 타입 안전성이 깨진다.

```java
List<String> strings = new ArrayList<>();

// 허용된다고 가정하면
List<Object> objects = strings;
objects.add(100);

// List<String>에 Integer가 들어가게 됨
String value = strings.get(0);
```

타입의 상한을 명확하게 표현해야 한다면 `? extends T`를 사용한다.

```java
double sum(List<? extends Number> values) {
    double result = 0;

    for (Number value : values) {
        result += value.doubleValue();
    }

    return result;
}
```

값을 제공하는 생산자는 `extends`, 값을 받아들이는 소비자는 `super`를 사용한다.

```text
Producer Extends, Consumer Super
```

---

## 2. 학습 내용 정리

- [자바의 정렬 방식](./topics/01.정렬.md)
- [Comparable과 Comparator](./topics/02.Comparator.md)
- [자바 제네릭](./topics/03.제네릭.md)
- [힙과 정렬된 컬렉션](./topics/04.힙과컬렉션.md)

---

## 3. 구현 과제 회고

### 이분 탐색 유틸

구현 파일: [BinarySearch.java](./src/BinarySearch.java)

- 탐색 범위를 `[left, right)`로 두고 `right`를 `arr.length`로 초기화했다.
- `lowerBound`는 `arr[mid] >= target`일 때 `right = mid`로 이동하여 target 이상인 첫 위치를 찾는다.
- `upperBound`는 `arr[mid] > target`일 때 `right = mid`로 이동하여 target 초과인 첫 위치를 찾는다.
- 두 메서드는 조건의 등호 하나만 다르며 시간복잡도는 `O(log n)`, 공간복잡도는 `O(1)`이다.
- 없는 값, 맨 앞, 맨 뒤, 중복이 많은 경우를 테스트했다.

### `Arrays.binarySearch()`와 차이

`Arrays.binarySearch()`는 target을 찾으면 해당 인덱스를 반환하지만, 같은 값이 여러 개라면 어느 인덱스를 반환할지 보장하지 않는다. target이 없다면 `-(삽입 위치)-1` 형태의 음수를 반환한다.

반면 `lowerBound`와 `upperBound`는 항상 `0`부터 배열 길이까지의 경계 인덱스를 반환한다.

```text
lowerBound
→ target 이상인 값이 처음 나오는 인덱스

upperBound
→ target보다 큰 값이 처음 나오는 인덱스
```

두 결과의 차이를 이용하면 중복된 target의 개수도 구할 수 있다.

```java
int count = upperBound(arr, target)
          - lowerBound(arr, target);
```

### PriorityQueue 소스 읽기

`PriorityQueue`는 완전 이진 트리를 배열로 표현한다. 원소를 삽입하면 배열 마지막에 추가한 뒤 부모보다 작을 동안 위로 이동하는 `siftUp`을 수행한다. 최솟값을 제거하면 마지막 값을 루트로 옮긴 뒤 두 자식 중 더 작은 값과 비교하며 내려가는 `siftDown`을 수행한다.

최소 힙은 부모가 자식보다 작거나 같다는 관계만 보장한다. 내부 배열 전체가 정렬된 것은 아니므로 반복자로 순회하면 정렬 순서가 보장되지 않는다. 작은 값부터 처리하려면 `poll()`을 반복해야 한다.

- [OpenJDK `PriorityQueue.java`](https://github.com/openjdk/jdk/blob/master/src/java.base/share/classes/java/util/PriorityQueue.java)

---

## 4. 알고리즘 풀이

| 문제                                    | 접근 방식                                        |   시간복잡도 | 공간복잡도 | 결과 |
| --------------------------------------- | ------------------------------------------------ | -----------: | ---------: | ---- |
| [가장 큰 수](./algorithm/가장큰수.java) | 문자열을 이어 붙인 두 결과를 Comparator로 비교   | `O(n log n)` |     `O(n)` | 통과 |
| [예산](./algorithm/예산.java)           | 신청 금액을 오름차순 정렬하고 작은 금액부터 누적 | `O(n log n)` | `O(log n)` | 통과 |
| [더 맵게](./algorithm/더맵게.java)      | 최소 힙에서 가장 작은 두 값을 반복해서 혼합      | `O(n log n)` |     `O(n)` | 통과 |

### 추가 연습

| 문제                                                                               | 학습 내용                   | 결과 |
| ---------------------------------------------------------------------------------- | --------------------------- | ---- |
| [K번째수](./algorithm/practices/K번째수.java)                                      | 배열 구간 복사와 기본 정렬  | 통과 |
| [문자열 내 마음대로 정렬하기](./algorithm/practices/문자열내마음대로정렬하기.java) | Comparator와 복수 정렬 기준 | 통과 |

---

## 5. 아직 모르겠는 것

타입 소거가 Java 5 이전 코드 및 JVM과의 호환성을 유지하기 위해 사용된다는 배경과, 구체적인 타입 정보가 컴파일 시점의 검사에 활용된다는 큰 방향은 이해했다.

그런데 `Box<String>`이 컴파일 과정에서 타입 검사, 타입 소거, 자동 형 변환을 거쳐 실행되는 전체 흐름을 아직 막힘없이 설명하기 어렵다. 특히 실행 시 실제 객체는 `String`으로 유지되지만 제네릭 코드의 `T`는 `Object`로 처리되는 관계를 추가로 복습할 필요가 있어보인다!
