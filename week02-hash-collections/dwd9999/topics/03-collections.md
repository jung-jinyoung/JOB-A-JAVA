## `HashMap` / `LinkedHashMap` / `TreeMap` — 순서와 복잡도

### HashMap

데이터 순서를 보장하지 않으며, 보통 `O(1)`의 시간 복잡도를 가짐  
순서는 알빠 아니고 성능만 잘나오면 되는 경우 사용  
어떻게 보면 가장 정석적인 Map 구조

### LinkedHashMap

데이터 순서를 들어온 순서대로 보장하며, 보통 `O(1)`의 시간 복잡도를 가짐  
HashMap 구조를 거의 유지하면서 노드만 양방향으로 연결해둔 것  
데이터를 순서대로 순회해야 하거나, 최근에 들어온 데이터를 확인해야 하는 경우 주로 사용  
LRU 캐시같은거 구현할때 사용한다고 함

### TreeMap

정렬 기준에 따라 순서를 보장하며, `O(log N)`의 시간 복잡도를 보장함  
데이터를 정렬된 상태로 유지해야하거나, 보장된 시간 복잡도가 필요한 경우 사용함

특히 정렬된 상태로 유지되는데, 시간 복잡도까지 보장된다는 점으로 가끔 알고리즘 문제에서 사용됨  
백준의 이중 우선순위 큐라는 문제를 구현할때 사용한 적이 있음

```java
public class SA7662 {
    public static void main(String[] args) throws Exception {
        TreeMap<Integer, Integer> dualQueue;
        for (int tc = 0; tc < testCase; tc++) {
            dualQueue = new TreeMap<>();
            for (int i = 0; i < opeCount; i++) {
                if (st.nextToken().equals("I")) {
                    number = Integer.parseInt(st.nextToken());
                    dualQueue.put(number, dualQueue.getOrDefault(number, 0) + 1);
                } else if (!dualQueue.isEmpty()) {
                    if (st.nextToken().equals("1")) {
                        number = dualQueue.lastKey();
                    } else {
                        number = dualQueue.firstKey();
                    }
                    dualQueue.put(number, dualQueue.get(number) - 1);
                    if (dualQueue.get(number) == 0) {
                        dualQueue.remove(number);
                    }
                }
            }
        }
    }
}
```

이런 자료구조가 있다 라는것만 생각해두면 나중에 도움이 되는 경우가 있음

## `HashSet`은 사실 `HashMap`이다

코드를 까보면 `HashSet` 안에는 `HashMap`이 선언되어 있음

```java
public class HashSet<E>
        extends AbstractSet<E>
        implements Set<E>, Cloneable, java.io.Serializable {
    @java.io.Serial
    static final long serialVersionUID = -5024744406713321676L;

    transient HashMap<E, Object> map;
}
```

안에 메서드들도 보면 그냥 HashMap에 있던 메서드들을 가져다 씀

```java
public int size() {
    return map.size();
}

public boolean contains(Object o) {
    return map.containsKey(o);
}

public boolean add(E e) {
    return map.put(e, PRESENT) == null;
}
```

이렇게 성의 없을수가

그렇다면 왜 이렇게 했을까?  
이는 구조적으로 너무 유사하기 때문  
이미 `HashMap`을 완벽하게 만들어두었는데, HashMap에서 Key 개념만 없애면 HashSet이 되는데 굳이 다시 만들 필요가 없음  
이렇게 함으로써 `HashMap`만 잘 관리해도 알아서 `HashSet`까지 관리되도록 한 것

## `ArrayDeque` vs `LinkedList` — 왜 큐/스택에 `ArrayDeque`를 권하는가

`LinkedList`는 원소를 저장할때, 각 Node에서 다음과 이전 Node를 포인터로 연결해둔 구조

```java
private static class Node<E> {
    E item;
    Node<E> next;
    Node<E> prev;

    Node(Node<E> prev, E element, Node<E> next) {
        this.item = element;
        this.next = next;
        this.prev = prev;
    }
}
```

그에 반해 `ArrayDeque`는 배열 안에 저장해두는 방식

```java
public class ArrayDeque<E> extends AbstractCollection<E>
        implements Deque<E>, Cloneable, Serializable {
    transient Object[] elements;
}
```

LinkedList 방식은 데이터들이 메모리에서 다 흩어져있어 캐시 히트가 거의 발생하지 않음  
그에 반해 ArrayDeque는 배열에 저장해서 메모리 내에서도 붙어있기 때문에, 캐시 히트가 자주 발생함

## `Stack` 클래스가 레거시인 이유

Stack은 내부적으로 Vector라는 List를 구현한 얘로 만들어짐  
문제는 List를 구현한 놈이기 때문에, 특정 인덱스에 바로 접근하거나 중간에 데이터를 넣는 전혀 stack 답지 않은 메서드가 있음  

또한, 내부 메서드들을 보면 다 `synchronized`가 붙어있음  
이는 멀티 스레드에서나 유용하지, 단일 스레드나 알고리즘 풀때는 하등 쓸모없는 시간 오래 걸리는 작업임  
예전에 실제로 백준 문제 풀때 모르고 썼다가, 시간 초과난 경험이 있음..  