import java.util.*;

/**
 * [접근 방법]
 * 문자열을 한글자씩 읽으면서 숫자인지 영어인지, 영어라면 어떤 숫자인지 찾자
 * 영어를 바로 숫자로 변환할 수 있도록 사전을 정의해두면 빠른 변환 가능
 * 그냥 replace() 사용하면 쉽게 풀리겠지만, 매 실행마다 문자열을 순회하므로 성능에 좋지 않을듯
 * <p>
 * 시간 복잡도: O(N)
 */
public class 숫자_문자열과_영단어 {
    public static void main(String[] args) {
        Solution solution = new Solution();
        String[] testCases = {"one4seveneight", "23four5six7", "2three45sixseven", "123"};

        for (int t = 0; t < testCases.length; t++) {
            System.out.println(solution.solution(testCases[t]));
        }
    }

    static class Solution {
        public int solution(String s) {
            // 영어 -> 숫자 사전 정의
            Map<String, Integer> dict = Map.of(
                    "zero", 0,
                    "one", 1,
                    "two", 2,
                    "three", 3,
                    "four", 4,
                    "five", 5,
                    "six", 6,
                    "seven", 7,
                    "eight", 8,
                    "nine", 9);

            int answer = 0;

            int idx = 0;
            while (idx < s.length()) {
                // 다음 자리가 숫자인 경우 그냥 더해주기
                if (s.charAt(idx) < 58) {
                    answer = answer * 10 + (s.charAt(idx) & 15);
                    idx++;
                } else { // 다음 자리가 글자인 경우 
                    
                    // 단어가 3~5글자니까 3글자부터 하나씩 늘려가며 사전에서 찾기 
                    int i = 3;
                    while (!dict.containsKey(s.substring(idx, idx + i))) {
                        i++;
                    }
                    
                    // 더해주고 단어 건너뛰기
                    answer = answer * 10 + dict.get(s.substring(idx, idx + i));
                    idx += i;
                }
            }

            return answer;
        }
    }
}
