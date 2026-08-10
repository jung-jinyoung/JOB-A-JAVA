## 배열의 메모리 배치, `System.arraycopy`

Java에서 배열은 연속된 메모리 공간에 배치됨  
그래서 배열을 그냥 출력해보면 해당 배열이 시작되는 메모리 주소가 나오고, a[2] 이런건 그 주소에서 자료형 크기만큼 이동하고 가져오는 것

이 구조를 이용해서 배열을 복사하는 메서드가 `System.arraycopy`  
C 언어 명령어를 사용해서 해당 배열이 있는 메모리를 통째로 떠다 복사함  
배열이 메모리상 한 곳에 모여있기에 가능한 방법

## `ArrayList` grow 전략 (1.5배 확장)

grow 메서드는 이렇게 생김

```java
private Object[] grow(int minCapacity) {
    int oldCapacity = elementData.length;
    if (oldCapacity > 0 || elementData != DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
        int newCapacity = ArraysSupport.newLength(oldCapacity,
                minCapacity - oldCapacity, /* minimum growth */
                oldCapacity >> 1           /* preferred growth */);
        return elementData = Arrays.copyOf(elementData, newCapacity);
    } else {
        return elementData = new Object[Math.max(DEFAULT_CAPACITY, minCapacity)];
    }
}
```

minCapacity - OldCapacity는 최소 필요 용량에서 현재 용량을 뺀 용량  
즉, 늘려야하는 용량의 크기  
oldCapacity >> 1 은 현재 용량의 0.5배

정리하자면 그냥 현재 용량을 1.5배를 가진 새로운 리스트를 만들고, 그 리스트에 내용을 복사한 후 기존 리스트를 없애는 방식

## `add` / `remove(index)` / `get`의 실제 시간복잡도

add는 맨 끝에 값을 넣기만 하면 되므로 보통 `O(1)`  
그런데 특정 인덱스를 지정해서 넣으면 이런 로직이 실행됨

```java
public void add(int index, E element) {
    rangeCheckForAdd(index);
    modCount++;
    final int s;
    Object[] elementData;
    if ((s = size) == (elementData = this.elementData).length)
        elementData = grow();
    System.arraycopy(elementData, index,
            elementData, index + 1,
            s - index);
    elementData[index] = element;
    size = s + 1;
}
```

아예 새로운 배열을 만들어버려서 시간 복잡도가 `O(N)` 까지 늘어나게 됨  

remove(index)는 해당 원소 이후 원소들을 모두 당겨와야 하므로, 당연히 새로운 배열을 만들어야 함  
```java
private void fastRemove(Object[] es, int i) {
    modCount++;
    final int newSize;
    if ((newSize = size - 1) > i)
        System.arraycopy(es, i + 1, es, i, newSize - i);
    es[size = newSize] = null;
}
```
역시나 시간 복잡도 `O(N)`  

get은 주소값과 인덱스와 데이터 크기를 아니까 바로 해당 주소로 가서 가져올 수 있음  
시간 복잡도는 `O(1)`  

## `Arrays.asList()`의 함정 (고정 크기, `add` 불가)

`Arrays.asList()`로 생성되는 객체는 그냥 우리가 아는 배열과 같은 구조임  
당연히 크기도 변경 안되고, `add`도 안됨  
`ArrayList`처럼 사용하려면 다시 `ArrayList` 생성자에 넣어서 새로 만들자  