## `Comparable`(자연 순서) vs `Comparator`(외부 기준)

정렬을 하려면 정렬 기준을 줘야할거 아님  
그래서 정렬 기준을 주는 방법이 2가지로 나뉨

1. **Comparable**  
   이미 그 객체가 정렬 기준을 가지고 있는 것  
   영어만 해석해도 "비교 가능한" 이라는 직관적인 이름  
   Integer 이런건 이미 Comparable이 구현되어 있고, 만약 본인이 만든 객체도 이렇게 만들고 싶다면 `implements Comparable<>` 붙이고 `compareTo()` 메서드 구현해두면 됨
2. **Comparator**  
   이미 있는 정렬 기준을 안 쓸 예정이거나, 아니면 내가 Comparable 객체로 만들 수 없는 외부 객체인 경우 사용함  
   직접 이번에 사용할 정렬 기준을 지정해주는 것  
   그냥 `compare()` 메서드를 구현하여 정렬 기준 적어주면 됨

## `Comparator.comparing().thenComparing().reversed()`

원래는 `Comparator`를 사용하려면 직접 `compare()` 메서드를 구현해야 했음  
지금 이 문제에 있는 것을 사용하려고 하면 아래와 같은 긴 코드가 필요함

```java
public static void main(String[] args) {
    Collections.sort(tests, new Comparator<Test>() {
        @Override
        public int compare(Test o1, Test o2) {
            if (o1.a == o2.a) {
                return Integer.compare(o2.b, o1.b);
            }
            return Integer.compare(o1.a, o2.a);
        }
    });
}
```

좀 익숙한 사람이 보면 괜찮은데, 어지간한 사람들은 보고서 이게 뭘까 싶을거같이 생김  
그래서 좀 편하게 쓰라고 여러 메서드들을 체이닝으로 사용할 수 있게 해준 것  
저 긴 코드가 아래와 같이 짧아짐

```java
public static void main(String[] args) {
    tests.sort(Comparator.comparing(Test::getA)
            .thenComparing(Test::getB)
            .reversed());
}
```

계속해서 Stream 밀어주는거나 여러 라이브러리들에서 이런식의 체이닝을 지원해주는 걸 보면, 확실히 사람이 읽기 편하도록 데이터의 흐름이 명확하게 보이는 방향으로 발전하는 기분  
스프링 하다보면 진짜 진짜 긴 체이닝들 자주 보게됨 참고용으로 트리토리 Security 관련 코드 이랬음

```java

@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers("/api/auth/**").permitAll()
                    .requestMatchers(GET, "/api/trees/*", "/api/docs/**", "/api/members", "/api/ornaments", "/api/ornaments/{ornamentId}").permitAll()
                    .requestMatchers(POST, "/api/trees/*/ornaments").permitAll()
                    .requestMatchers("/api/**").authenticated()
                    .requestMatchers(GET, "/actuator/health").permitAll()
                    .anyRequest().denyAll()
            )
            .headers(headers -> headers
                    .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
            )
            .cors(corsConfigurer -> {
                corsConfigurer.configurationSource(corsConfigurationSource());
            })
            .csrf(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session
                    .sessionCreationPolicy(STATELESS)
            )
            .exceptionHandling(ex -> ex
                    .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
            )
            .addFilterBefore(new JwtAuthenticationFilter(jwtUtils, cookieUtils, userDetailsService),
                    UsernamePasswordAuthenticationFilter.class
            )
            .oauth2Login(oauth2Configurer -> oauth2Configurer
                    .authorizationEndpoint(authEndPoint -> authEndPoint
                            .baseUri("/api/auth/login"))
                    .redirectionEndpoint(authEndPoint -> authEndPoint
                            .baseUri("/api/auth/oauth2/*"))
                    .userInfoEndpoint(userInfo -> userInfo
                            .userService(oAuth2UserService))
                    .successHandler(oauth2SuccessHandler)
            );

    return http.build();
}
```

놀랍게도 return 제외하면 단 한 줄ㅋㅋ  
미리 체이닝과 람다에 익숙해져보자

## `compareTo`에서 `a - b`가 위험한 경우 (오버플로우) → `Integer.compare` 사용

`compareTo` 구현할때 `a - b`로 하면, 얼핏 보면 의미가 맞을 것 같이 생김  
a가 더 크면 양수가 나올거고, 둘이 같으면 0이 나올거고, b가 더 크면 음수가 나올테니 맞지 않아? 싶은데 함정이 있음  
다 알거같긴한데 a가 엄청 큰 양수고, b가 엄청 작은 음수라면 `a - b`가 int의 범위를 넘어가 오버플로우가 발생할 가능성이 있음  
그럼 a가 b보다 훨씬 큰데, `a - b`의 결과는 음수가 나와버려 정반대의 결과가 나오게 되는 것

사실 그냥 `a < b ? -1 : ((x == y) ? 0 : 1)` 하면 될거같은데 왜 안할까  
좀 구질구질해보이고 길고 안 예쁨,, 일단 난 이런 이유로 안씀  
이런 이유로 안 쓰는 사람들을 위해 친절하게 `Integer.compare()` 메서드를 만들어 둠  