## `Object.equals`의 5가지 규약 (반사·대칭·추이·일관·null)

1. **반사성**  
   객체는 자기 자신과 동등해야 함  
   즉, `x.equals(x) == true`여야 함
2. **대칭성**  
   두 객체는 방향과 무관하게 동등 여부가 같아야 함  
   `x.equals(y) == y.equals(x)` 여야 한다는 것
3. **추이성**  
   객체 x, y, z가 있을때, `x.equals(y) == true`, `y.equals(z) == true`라면 반드시 `x.equals(z) == true` 여야 함  
   그냥 x = y, y = z면 x = z여야 한다는 것
4. **일관성**  
   객체의 상태가 변하지 않았다면 동등 여부가 변하지 않아야 함  
   즉, `x.equals(y)`를 몇번 실행하던 결과가 같아야 함
5. **null에 대한 비동등성**  
   객체와 null은 동등하지 않아야 함  
   `x.equals(null) == false`를 반환해야 한다는 것

모아두고 보니 당연한 소리밖에 없음

## `hashCode` 계약: equals가 같으면 hashCode도 같아야 한다

Hash를 사용하는 Collection들이 이 2가지가 같다고 가정하고 설계되어 있기 때문에 `equals()`를 재정의했다면, 반드시 `hashCode()`도 재정의해야 함  

통상적으로 많이 사용하는 `HashMap`의 코드를 까보면 이해에 도움이 됨  
`HashMap`의 `get()` 메서드를 보자  
```java
final Node<K,V> getNode(Object key) {
   Node<K,V>[] tab; Node<K,V> first, e; int n, hash; K k;
   if ((tab = table) != null && (n = tab.length) > 0 &&
           (first = tab[(n - 1) & (hash = hash(key))]) != null) {
      if (first.hash == hash && // always check first node
              ((k = first.key) == key || (key != null && key.equals(k))))
         return first;
      if ((e = first.next) != null) {
         if (first instanceof TreeNode)
            return ((TreeNode<K,V>)first).getTreeNode(hash, key);
         do {
            if (e.hash == hash &&
                    ((k = e.key) == key || (key != null && key.equals(k))))
               return e;
         } while ((e = e.next) != null);
      }
   }
   return null;
}
```
중요한 부분은 첫 if문에 있는 `(hash = hash(key))`  
저기서 `hash(key)` 메서드를 뜯어보자  
```java
static final int hash(Object key) {
   int h;
   return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
}
```
key의 `hashCode()` 메서드를 실행하고, 그 내용을 return하는 것  

이후 같은 if문에서 `first = tab[(n - 1) & (hash = hash(key))]`로 해시 테이블에서 해시값을 기반으로 노드를 꺼냄  
참고로 `tab`은 해시 테이블, `first`는 해당 버킷의 첫번째 노드가 들어가게 됨  

이제 두번쨰 if문인 이 부분  
`((k = first.key) == key || (key != null && key.equals(k)))`  
메모리 주소가 완전히 같아서 동일 객체인게 자명하거나, `key.equals(k)`를 통과하는 경우 return  

정리하자면, `hashCode()` 메서드를 사용해 해시 테이블에서 버킷을 찾고, 그 버킷에서 `equals()` 메서드를 사용해 해당하는 객체를 찾음  
만약 `equals()`만 재정의한다면, 해시코드가 계속 달라져 버킷을 못 찾음!  
반대로 `hashCode()`만 재정의한다면, 버킷은 찾는데 안에서 객체를 못 찾음!  

## IDE 자동 생성 코드 뜯어보기, `record`가 대신 해주는 것

Spring에서 DTO 만들다보면 IDE가 알아서 노란줄 띄워주기도 하고, 나중엔 만들기 귀찮아서 써보게 됨  
DTO에서 주로 넣는 `getter`, `equals()`, `hashCode()`, `toString()` 등 데이터 보관 및 관리에 필요한 메서드들을 알아서 구현해주고, 구현해줌으로서 사람에게서 실수가 나오는 것도 방지해줌  

단순하게 코드 길이로만 봐도 Dto로 직접 만들면 아래와 같음  
```java
import java.util.Objects;

public class OriginalDto {
    private final int test;

    public OriginalDto(int test) {
        this.test = test;
    }

    public int getTest() {
        return test;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OriginalDto that = (OriginalDto) o;
        return test == that.test;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(test);
    }

    @Override
    public String toString() {
        return "OriginalDto{" +
                "test=" + test +
                '}';
    }
}
```

그런데 record 딸깍하면 이만함  
```java
record RecordDto(int test) {
}
```
레전드  
참고로 바이트 코드 까보면 INVOKEDYNAMIC라는 걸 사용해서, 알아서 최적화된 방법으로 메서드를 런타임에서 구현해줌  
찾아보니 직접 구현하는거보다 안정적이고 빠르다고 함  