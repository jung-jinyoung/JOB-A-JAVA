import java.util.Arrays;

/**
 * [접근 방법]
 * 배열을 어떤 순서대로 정렬해야 원하는 목표를 달성할 수 있는지를 공부하는 문제
 * 코테에서 람다식 헷갈리면 실수할바에 그냥 냅다 중괄호 열고 하는게 나음
 * 모두 0인 경우 반례 찾아낸다고 한참 걸렸네...
 * <p>
 * 시간 복잡도: O(N)
 */
public class 가장_큰_수 {
    public static void main(String[] args) {
        Solution solution = new Solution();

        int[][] testCase = {{6, 10, 2}, {3, 30, 34, 5, 9}};

        for (int t = 0; t < testCase.length; t++) {
            System.out.println(solution.solution(testCase[t]));
        }
    }

    static class Solution {
        public String solution(int[] numbers) {
            StringBuilder answer = new StringBuilder();

            String[] strNumbers = new String[numbers.length];

            // 비교 연산을 위해 String 배열로 변환
            int zeroCount = 0;
            for (int i = 0; i < numbers.length; i++) {
                strNumbers[i] = Integer.toString(numbers[i]);
                if (numbers[i] == 0) zeroCount++;
            }

            // 모두 0으로 이루어진 경우 예외 처리
            if (zeroCount == numbers.length) return "0";
            
            // 문자열 2개를 이어붙였을때 더 큰 수가 나오는 기준으로 정렬하면, 결국 앞에 오는 수부터 이어 붙였을때 가장 큰 수가 됨
            Arrays.sort(strNumbers, (a, b) -> Integer.parseInt(b + a) - Integer.parseInt(a + b));

            for (int i = 0; i < strNumbers.length; i++) {
                answer.append(strNumbers[i]);
            }
            
            return answer.toString();
        }
    }
}
