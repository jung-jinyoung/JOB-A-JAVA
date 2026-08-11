## 불변 (immutable)인 이유와 이점

String 내부를 까보면 이렇게 선언되어 있음

```java
public final class String
        implements java.io.Serializable, Comparable<String>, CharSequence,
        Constable, ConstantDesc {
    @Stable
    private final byte[] value;
}
```

final이 붙어있으니 이건 바꿀 수 없음

그럼 왜 이렇게 만들었을까

1. **메모리 절약**  
   이건 아래 String Constant Pool에서 다룸
2. **Thread-Safe**  
   값이 바뀌지 않으니 여러 스레드에서 동시에 접근해도 알빠아님
3. **보안**  
   Java에서 String은 민감한 정보를 담고있는 경우가 많음  
   이런 값을 중간에 변경할 수 없도록 예방하는 효과도 있음
4. **Hash Caching**  
   해시코드 까보면 이렇게 생김

```java
public int hashCode() {
    int h = hash;
    if (h == 0 && !hashIsZero) {
        h = isLatin1() ? StringLatin1.hashCode(value)
                : StringUTF16.hashCode(value);
        if (h == 0) {
            hashIsZero = true;
        } else {
            hash = h;
        }
    }
    return h;
}
```

전역 변수인 hash에 해시값을 저장하고, 만약 h != 0 이라면 바로 return h;로 넘어감  
변하지 않기 떄문에 이런 요상한 방법을 사용할 수 있음

## String Constant Pool, `==` vs `equals`

Java의 Heap 메모리에는 아예 문자열 전용으로 String Constant Pool 이라는 곳이 있음  
여기는 String을 선언할때 만약 Pool에 없다면 새로 만들고, Pool에 있다면 이미 있는 객체를 그대로 반환함  
그래서 동일한 String을 여러번 선언하더라도 메모리를 알차게 사용할 수 있음

보통 `==` 연산자는 **객체의 주소를 비교**하고, `equals()`는 **객체 안의 값을 비교**함  
그래서 다른 Object에서는 `equals()`가 같아도 `==`이 다른 경우가 거의 대부분임  
하지만 String의 경우 값이 같으면 주소값도 같으므로 `==`도 사용 가능한 것

물론 new를 사용해서 어거지로 새 객체를 만들면 그건 `==`을 사용했을때 false가 나올거임  
근데 굳이? 어째서? 메모리의 한계를 시험하려는거 아니면 하지 말자

## 반복문 내 `+=`의 비용 → `StringBuilder`

첫번째 주제에서 이어지는 내용  
**String은 불변**이므로, `+=` 연산자를 사용하면 기존 문자열에 대상 문자열을 붙이는게 아님  
아예 새로운 String 객체를 만들어서 그 안에서 이어버림  
즉, 이 연산이 반복될수록 **쓸모없는 객체가 마구마구 생성**되고, 메모리 낭비로 이어짐

알고리즘에서 경험했으면 알겠지만 `StringBuilder`를 사용하면 실행 속도가 아주 눈에 띄게 빨라지는데 이것이 그 이유  
그 이유는 `StringBuilder`는 가변 객체이기 때문  
내부에서 크기가 변하는 배열을 사용해서, 새로운 객체를 만들지 않고 자유로운 변형이 가능함

궁금해서 뜯어보니까 StringBuilder는 AbstractStringBuilder를 상속받고 있음  
그리고 그 안에 용량을 의미하는 capacity 변수가 기본으로 16으로 설정되어 있고, 용량을 초과하는 경우 아래와 같은 함수가 실행됨  
```java
private void ensureCapacityInternal(int minimumCapacity) {
    int oldCapacity = value.length >> coder;
    if (minimumCapacity - oldCapacity > 0) {
        value = Arrays.copyOf(value,
                newCapacity(minimumCapacity) << coder);
    }
}
```
자세히는 모르겠는데 새로운 배열을 만들고, 내부적으로 `Arrays.copyOf()`를 사용해서 그 안에 붙여넣는듯  
