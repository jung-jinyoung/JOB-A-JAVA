package algorithm;

/*
    1. 전략
    - 앞에서부터 한 글자씩 확인하면서, 문자를 두 종류로 구분
        - 숫자 문자 : 그대로 결과에 붙인다
        - 영문자 : temp에 쌓아두고, 완성된 영단어가 되면 숫자로 치환해 붙인다
    - 영단어 매핑은 Map<String,Integer>로 만들어 O(1) 조회한다.

    2.복잡도
    - 시간 복잡도 : O(n^2)
        - 매번 전체를 복사한 새 객체를 만들어 누적 비용이 O(n^2)이 된다.
        - (s.length() <= 50 이라 통과. StringBuilder로 바꾸면 O(n))
    - 공간 복잡도 :  O(n)

    3. 학습 메모
    - StringBuilder로 리팩토링까지 학습 완료
    - "", '' 사용 방법 확인

 */

import java.util.Map;
import java.util.HashMap;

public class 숫자문자열과영단어 {

    class Solution {
        public int solution(String s) {
            String answer = "";

            String[] arr = {
                    "zero", "one", "two", "three", "four",
                    "five", "six", "seven", "eight", "nine"
            };

            Map<String, Integer> map = new HashMap<>();
            for (int i = 0; i < arr.length; i++) {
                map.put(arr[i], i);
            }


            String temp = "" ;

            for (int i = 0 ; i < s.length() ; i++){
                if (Character.isDigit(s.charAt(i))) {
                    if (temp != "") {
                        answer += map.get(temp);
                        temp = "";
                    }

                    answer += s.charAt(i);
                } else {
                    if (map.containsKey(temp)){
                        answer += map.get(temp);
                        temp = "";
                    }
                    temp += s.charAt(i);

                }
            }

            if (temp != ""){
                answer += map.get(temp);
            }

            return Integer.parseInt(answer);
        }
    }
}
