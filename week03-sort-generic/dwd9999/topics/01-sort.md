## 원시 타입과 객체 타입의 정렬 방법 차이

첫 3개의 주제가 결국 이 주제를 다루는 것 같아서 통합하여 작성

IDE에서 `Arrays.sort()` 치고서 코드 보려고 해보면 모든 원시 타입 + Object + T 이렇게 타입별로 다 구현되어 있음  
들어가보면 알겠지만 원시 타입들은 이런 모양으로 타입별로 다 선언되어 있음

```java
public static void sort(int[] a) {
    DualPivotQuicksort.sort(a, 0, 0, a.length);
}
```

그리고 Object랑 제네릭으로는 아래와 같이 선언되어 있음

```java
public static void sort(Object[] a) {
    if (LegacyMergeSort.userRequested)
        legacyMergeSort(a);
    else
        ComparableTimSort.sort(a, 0, a.length, null, 0, 0);
}
```

위에 LegacyMergeSort는 구 버전과의 호환성을 위한 부분이니 신경 안써도 됨  
아무튼 원시 타입은 `DualPivotQuicksort`를, Object와 제네릭은 `TimSort`를 사용하는 모습

> **딴소리**  
> Java6까지는 MergeSort를 사용했고, MergeSort는 비교할때 지켜야 할 규칙들을 안지켜도 그냥 어영부영 넘어갔다고 함  
> 당시에 작성된 문서를 보면 **The new sort implementation may throw an IllegalArgumentException if it detects a Comparable that
violates the Comparable contract.** 라고 적혀있음  
> 참고: https://www.oracle.com/java/technologies/compatibility.html  
> 그런데 이 상태로 바로 검사가 엄격한 TimSort로 변경하자니 기존에 허술하게 짜둔 코드가 싹다 에러를 뱉는 상황 발생  
> LegacyMergeSort는 그런 코드들 쓰라고 남겨둔 잔여물이라 보면 될 듯

왜 다른걸 쓸까?  
원시 타입의 특성을 고려해보면 됨  
원시 타입은 값 하나만 딸랑 저장하지만, 객체는 값을 여러개 가지고 있을 수 있음

즉, 원시 타입은 정렬 기준으로 동일하기만 하면 그냥 같은 얘고, 객체는 정렬 기준으로 보면 동일할지라도 엄연히 다른 객체임  
간단하게 int를 예시로 보자  
`[1, 2, 3, 3]` 이 객체에 있는 3이 서로 위치가 바뀌든 말든 우리가 알빠일까?  
하지만 객체는 다름  
나이, 이름을 저장한 객체가 있을때, 나이가 같다고 같은 사람이라고 치면 좀 곤란함;;

그래서 원시 타입은 값만 같으면 순서가 상관없는 단순함 때문에 안정성은 가져다 버리고 속도 몰빵 정렬인 `DualPivotQuickSort`를 채용함  
객체는 여러 복합적인 상태를 가지고 있으므로, 메모리를 더 사용하더라도 안정적인 정렬을 보장하는 `TimSort`를 채용함

각 정렬이 왜 저런 특성을 가지는지 다 적으면 너무 복잡해서 생략  
대충 요약하면 `DualPivotQuicksort`는 배열을 추가로 만들지도 않고, 같은 배열 내에서 데이터를 스왑하면서 정렬하는거라 메모리도 덜 쓰고 캐시 히트도 자주 나서 속도도 빠름  
`TimSort`는 데이터를 싹다 쪼개고, 삽입 정렬과 합병 정렬을 사용하여 합치느라 추가적인 공간을 사용하게 되지만 기존 순서가 보존됨

## `int[]` 퀵소트 저격 (anti-quicksort) 문제와 회피법

`QuickSort`는 기준 값을 고른 후 그 값보다 작은 값은 왼쪽, 큰 값은 오른쪽으로 나누며 정렬함  
이상적인 경우 배열이 예쁘게 반반 나눠지면서 `O(N logN)`으로 정렬 가능  
그런데, 데이터를 악의적으로 넣어 항상 데이터 1개와 나머지 모두 이렇게 나뉘게 된다면, 총 연산이 1 + 2 + .. + N이 되어서 결국 시간복잡도 `O(N^2)`을 찍음  
`DualPivotQuickSort`도 태생적으로 `QuickSort` 이므로 이러한 단점을 똑같이 가지고 있음

실제 서비스에서는 이렇게 악의적으로 데이터가 들어올 확률이 높지는 않음  
하지만, 알고리즘 문제들은 우리를 어떻게든 시간초과내려고 혈안인 사람들이 있음  
그런 사람들을 위해 안정적인 시간을 뽑아낼 수 있는 방법을 알아두자

뭐 말은 거창하게 했는데 그냥 저 정렬 안쓰면 됨  
저 정렬 안쓰려면? 원시 타입이 아니면 되겠네?

1. `Collections.sort()` 쓰기
2. `int[]`말고 `Integer[]` 쓰기

가끔 백준에서 이상하게 한 케이스에서만 시간초과 나면 의심해보자

회사 코테에서 이딴짓은 안할거같긴한데, 면접에서 혹시나 질문 던졌을때  
'그냥 `int[]`로 주셔서 그대로 정렬했습니다.'  
이거보단  
'원시 타입의 배열은 퀵 정렬을 사용해 악의적인 데이터가 들어오는 경우 시간 복잡도가 상승할 수 있지만, 이 문제의 핵심 요구사항이 이 점이 아니라고 판단해 사용하지 않았습니다.'  
라고 하면 멋지자너
