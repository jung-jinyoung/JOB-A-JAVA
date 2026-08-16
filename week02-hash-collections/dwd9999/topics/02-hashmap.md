## 버킷 배열 + 해시 함수 + 인덱스 계산

- **버킷 배열**  
  HashMap에서 값을 저장할때, 값을 저장하는 곳을 버킷이라고 함  
  그 버킷들을 모아둔 것이 버킷 배열
- **해시 함수**  
  어떤 길이의 값을 넣나 동일한 길이의 무작위 값을 반환해주는 함수
- **인덱스 계산**  
  해시 함수로 나온 값을 다 담을만큼의 버킷 배열을 만들 수는 없음  
  그래서 어떤 버킷을 가져올지 계산식을 통해 한번 인덱스를 계산함  
  자바에서는 `first = tab[(n - 1) & (hash = hash(key))]` 이 부분을 보니, & 연산으로 계산하는 듯?

## 충돌 처리: **체이닝 (연결 리스트)**

해시 함수는 어떤 길이의 값을 넣나 동일한 길이의 무작위 해시값을 반환함  
이 과정에서 우연히 서로 다른 값을 넣어도 같은 해시값을 반환할 수 있음  
이것이 해시 충돌

해시 충돌이 발생하면 같은 버킷에 들어가야 함  
같은 버킷에 여러 데이터를 넣기 위해 LinkedList로 데이터를 넣어둠  
코드로 보면 명확하게 알 수 있음

```java
static class Node<K, V> implements Map.Entry<K, V> {
    final int hash;
    final K key;
    V value;
    Node<K, V> next;

    Node(int hash, K key, V value, Node<K, V> next) {
        this.hash = hash;
        this.key = key;
        this.value = value;
        this.next = next;
    }

    public final K getKey() {
        return key;
    }

    public final V getValue() {
        return value;
    }

    public final String toString() {
        return key + "=" + value;
    }

    public final int hashCode() {
        return Objects.hashCode(key) ^ Objects.hashCode(value);
    }

    public final V setValue(V newValue) {
        V oldValue = value;
        value = newValue;
        return oldValue;
    }

    public final boolean equals(Object o) {
        if (o == this)
            return true;

        return o instanceof Map.Entry<?, ?> e
                && Objects.equals(key, e.getKey())
                && Objects.equals(value, e.getValue());
    }
}
```

다른건 신경 안써도 되고, 초반 선언부 보면 `Node<K,V> next`가 내부에 있음  
LinkedList와 구조가 동일한 모습  
버킷에서 첫 노드만 불러오면, 그 이후는 next 노드로 쭉 들어가며 정확히 일치하는 노드를 찾게 됨

## 로드 팩터 (0.75)와 리사이징 시점

위에서 말했듯이 버킷 배열은 모든 해시 값을 담아내지 못함  
결국, 버킷 배열이 작을수록 해시 충돌이 더 많이 일어나 성능이 저하될 수 밖에 없음  
그래서 적당히 어느정도 차면 배열을 늘리는데, 그 시점이 로드 팩터인 75%인 것  
즉, 전체 버킷의 75%가 사용되면 용량을 늘리는 리사이징을 시작함

그렇다면 버킷 배열이 커지면, 인덱스 계산도 다시 해야하는거 아닌가?  
맞음. 그래서 리사이징은 엄청 안에 있는 값을 싹다 다시 인덱스 계산하는 엄청나게 무거운 작업이 일어남

```java
final Node<K, V>[] resize() {
    Node<K, V>[] oldTab = table;
    if (oldTab != null) {
        for (int j = 0; j < oldCap; ++j) {
            Node<K, V> e;
            if ((e = oldTab[j]) != null) {
                oldTab[j] = null;
                if (e.next == null)
                    newTab[e.hash & (newCap - 1)] = e;
                else if (e instanceof TreeNode)
                    ((TreeNode<K, V>) e).split(this, newTab, j, oldCap);
                else { // preserve order
                    Node<K, V> loHead = null, loTail = null;
                    Node<K, V> hiHead = null, hiTail = null;
                    Node<K, V> next;
                    do {
                        next = e.next;
                        if ((e.hash & oldCap) == 0) {
                            if (loTail == null)
                                loHead = e;
                            else
                                loTail.next = e;
                            loTail = e;
                        } else {
                            if (hiTail == null)
                                hiHead = e;
                            else
                                hiTail.next = e;
                            hiTail = e;
                        }
                    } while ((e = next) != null);
                    if (loTail != null) {
                        loTail.next = null;
                        newTab[j] = loHead;
                    }
                    if (hiTail != null) {
                        hiTail.next = null;
                        newTab[j + oldCap] = hiHead;
                    }
                }
            }
        }
    }
    return newTab;
}
```

보다시피 for문으로 처음부터 싹다 돌림  
그러니까 처음 HashMap 만들때부터 용량 잘 생각해서 만들자

## 자바 8 이후 **버킷 트리화**(연결 리스트 → 레드-블랙 트리) 조건

이런 연결 리스트 방식은 우연히 특정 버킷으로 값이 몰리게 되면, 최악으로 시간 복잡도가 `O(N)`까지 올라감  
그래서 최악의 경우에도 시간 복잡도가 `O(log N)`으로 유지되는 레드-블랙 트리를 사용하는 것   
레드-블랙 트리가 무엇인지는 알아서 공부해보자

조건은 크게 2가지임  
1. `TREEIFY_THRESHOLD` 만큼 하나의 버킷에 값이 몰리는 경우  
2. `MIN_TREEIFY_CAPACITY` 만큼 테이블 크기가 커진 경우

버킷에 값을 넣은 후 `if (binCount >= TREEIFY_THRESHOLD - 1)`를 통해서 해당 버킷의 크기가 `TREEIFY_THRESHOLD` 이상으로 커졌는지 확인함  
이후 `(n = tab.length) < MIN_TREEIFY_CAPACITY)`를 통해 해시 테이블 전체의 크기가 `MIN_TREEIFY_CAPACITY` 이상으로 커졌다면, 그때 레드-블랙 트리로 변경  