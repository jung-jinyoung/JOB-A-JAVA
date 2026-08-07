## Week 1 Review — 4주제: 배열과 ArrayList 내부

### 학습 내용
- 배열이 메모리에 배치되는 방식과, 크기가 고정될 수밖에 없는 이유
- `System.arraycopy`의 동작 원리와 실습
- `ArrayList`의 초기 용량과 1.5배 확장(grow) 전략
- `add`/`get`/`remove(index)`가 위치에 따라 시간복잡도가 달라지는 이유
- `Arrays.asList()`가 실제로는 배열을 감싼 것뿐이라 생기는 제약

### 실습 코드
- [`src/practices/ArrayCopyCheck.java`](../src/practices/ArrayCopyCheck.java) — `System.arraycopy` 동작 확인
- [`src/practices/ArrayListCheck.java`](../src/practices/ArrayListCheck.java) — `Arrays.asList()`의 `add()` 제약 확인

### 이해한 핵심 개념
- 배열은 Heap에 **연속된 공간**을 확보해서 만들어지고, 그 바깥 자리는 다른 데이터가 이미 차지하고 있을 수 있어 크기를 나중에 늘릴 수 없다. 대신 `arr[i]`는 "시작 주소 + (i × 한 칸 크기)"라는 계산 한 번으로 바로 접근할 수 있어 O(1)이다.
- 배열 크기를 "늘리는" 것처럼 보이는 모든 동작(ArrayList, StringBuilder 등)은 실제로는 **더 큰 새 배열을 만들고 기존 내용을 통째로 복사**하는 방식이며, 이 복사는 `System.arraycopy()`로 수행된다. 이 메서드는 JVM 내부(네이티브 코드)에서 메모리를 블록 단위로 복사해 직접 짠 반복문보다 빠르다.
- `System.arraycopy(src, srcPos, dest, destPos, length)`는 원본을 변형하지 않고 값을 새 배열에 복사한다. `int[]`처럼 원시타입 배열은 값 자체가 복사되므로, 복사 이후 원본을 수정해도 복사본에는 영향이 없다.
- `ArrayList`는 첫 `add()` 시점에 capacity 10짜리 배열을 만들고, 꽉 차면 "기존 용량 + 기존 용량/2"(1.5배)로 재할당한다. `size`(실제 담긴 개수)는 `add()`마다 매번 +1 되지만, `capacity`(내부 배열 전체 크기)는 꽉 찰 때만 계단식으로 뛴다. 재할당이 매번이 아니라 가끔 일어나기 때문에 `add()`의 시간복잡도는 상각(amortized) O(1)이다.
- `get(index)`는 위치와 무관하게 항상 O(1)이지만, `add(index, x)`와 `remove(index)`는 맨 뒤가 아닌 위치를 건드릴 경우 뒤(또는 앞) 원소들을 전부 밀거나 당겨야 해서 O(n)이다. 맨 뒤에서의 추가/삭제만 O(1).
- `Arrays.asList()`는 새 `ArrayList`를 만드는 게 아니라 기존 배열을 `List` 인터페이스로 감싼 것뿐이다. 크기가 고정된 배열이 그 아래에 있기 때문에 `add()`/`remove()`처럼 크기를 바꾸는 동작은 `UnsupportedOperationException`을 던지고, `get()`/`set()`처럼 자리를 바꿔치기하는 동작만 허용된다.

### 실습으로 직접 확인한 것
- `System.arraycopy(oldArr, 0, newArr, 0, oldArr.length)` 실행 결과: `oldArr = [1, 2, 3]`, `newArr = [1, 2, 3, 4]` — 원본은 그대로 남고 복사만 이루어짐을 확인.
- 복사 이후 `oldArr`을 수정해도 `newArr`에는 영향이 없다는 것 — 원시타입 배열은 값 자체가 복사되기 때문(원리 확인, 코드 실행은 생략).
- `Arrays.asList(1, 2, 3)`에 `add(4)`를 호출했을 때 실제로 `UnsupportedOperationException`이 발생하는 것을 실행으로 확인함.

### 어려웠던 부분
- `ArrayList`의 `size`와 `capacity`가 다른 개념이라는 걸 처음엔 헷갈려, "0에서 어떻게 늘어나는지"를 다시 질문하며 정리함. `capacity`는 재할당 시점에만 계단식으로 바뀌고, `size`는 `add()`마다 매끄럽게 +1 된다는 차이를 확인하고서야 명확해짐.
- `add(index, x)` / `remove(index)`가 실제로 원소를 밀거나 당기는 동작은 원리(추론)로만 확인했고, 코드 실행으로 직접 검증하지는 않음 — 다음에 시간 나면 보강할 부분.

### 새롭게 알게 된 Java 특징
- `ArrayList`의 초기 capacity는 10이고, 확장 배율은 1.5배다(3주차에서 배운 `StringBuilder`의 약 2배 확장과는 배율이 다름).
- `System.arraycopy()`는 자바 코드가 아니라 네이티브로 구현되어 있어 반복문보다 빠르며, `ArrayList`/`StringBuilder`의 내부 재할당, `Arrays.copyOf()` 등이 전부 이를 감싼 것이다.
- `Arrays.asList()`로 만든 리스트를 진짜 가변 리스트로 쓰려면 `new ArrayList<>(Arrays.asList(...))`처럼 한 번 더 감싸야 한다.

