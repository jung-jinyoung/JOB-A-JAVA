## 타입 소거 (type erasure)와 그 결과

제네렉이 처음부터 있던 게 아니라 제네릭을 만들 당시 하위 호환성을 생각해야 했음  
자바가 작고 소중할때 제네릭이 나왔다면 그냥 엎었을수도 있지만 이미 자라버린 상황  
그래서 타입 소거 라는 방법을 채택함

타입 소거는 컴파일 단계에서만 타입을 검사하고, 바이트코드에서 타입을 지워버림  
처음에는 `List<Integer> list`로 선언해도, 바이트코드에서는 `List list`로 바뀌어 있음  
이런 이슈로 인해 항상 제네릭을 사용할때는 타입을 신경써야 함

예를 들어, 메서드를 오버로딩 하는데 '리스트가 숫자면 다 더해서 알려주고 문자열이면 쭉 이어붙여서 출력해야지~' 라는 마음을 먹었다고 치자  
그럼 단순히 생각하기로는 `void print(List<Integer> list)`, `void print(List<String> list)`로 하면 될거같음  
그런데 우리는 타입 소거가 있다는 것을 아니까 한번 더 변환해서 생각해보면?  
둘 다 타입 소거 후에는 `void print(List list)`가 되어 충돌이 발생하게 됨  
항상 조심하자

## 와일드카드: `? extends T`(생산자) / `? super T`(소비자) — PECS

제네릭은 기본적으로 무공변임  
무공변이 뭔데  
`String`이나 `Integer`가 `Object`의 하위 타입이지만, `List<String>`, `List<Integer>`은 `List<Object>`의 하위 타입이 아니라는 것  
컴파일 후 타입이 소거되기에 이렇게 엄격한 규칙을 사용하고 있음  
이런 규칙을 상황에 따라 우회하기 위해 와일드카드 `?`를 사용하는 것

와일드카드가 없으면 어떤 상황에 불편할까  
간단하게 리스트에 있는 숫자를 다 더하고 출력해주는 메서드가 있다고 치자  
만약 와일드 카드가 없다면 `public void add(List<Integer> numbers)`, `public void add(List<Double> numbers)`, ... 등등 모든 숫자별로 다 메서드를
만들어야 함  
어차피 로직도 다 똑같을텐데 아주 의미 없는 행동을 해야 함

하지만 와일드 카드가 있다면?  
`public void add(List<? extends Number> numbers)` 하나로 모두 끝  
`Number`를 상속받은 클래스면 모두 사용할 수 있는 메서드가 완성됨

만약 반대로 `Integer`에 `Number`, `Object`를 모두 넣고싶다?
`public void test(List<? super Integer>)` 이렇게 하면 Integer의 상위 타입을 모두 허용함

이제 이걸 외우기 귀찮으니 알기 쉽게 만들어보자 하고 만든게 PECS임  
Producer-Extends, Consumer-Super의 약자  
이 컬렉션이 producer (제공자)면 extends 사용, consumer (소비자)면 super 사용 이런거같다  
컬렉션이 제공자이니 나는 값을 꺼내 쓰겠지? 그러니까 extends는 데이터를 읽을때 안전함  
반대로 컬렉션이 소비자면 나는 값을 주겠지? 그러니까 super는 데이터를 쓸때 안전함

사실 이미 자바에선 extends와 super를 잘 쓰고있고, 그 의미와 이거도 동일함  
클래스에 쓰는 문법 그대로 생각해보면 `? extends Number`면 `?`가 `Number`를 상속받는다는 것  
그럼 `Number` 하위 타입 말하는거겠지  
그리고 본인 부모 클래스 접근할때 `super` 쓰니까 상위 타입 말하는거겠지

## `new T[]`가 안 되는 이유

넘 길어서.. 저번에 적은거로 대체하겠슴다...  
https://github.com/jung-jinyoung/JOB-A-JAVA/pull/4#discussion_r3801486243  
