import java.util.Arrays;

/**
 * [접근 방법]
 * 예산 한에서 최대한 많은 돈을 쓰고싶다 이런게 아니라 최대한 많은 부서를 지원하기만 되는 문제
 * 그래서 그냥 제일 적은 예산 원하는 부서부터 쭉 지원해주면 됨
 * <p>
 * 시간 복잡도: O(N)
 */
public class 예산 {
    public static void main(String[] args) {
        Solution solution = new Solution();

        int[][] testCase = {{1, 3, 2, 5, 4}, {2, 2, 3, 3}};
        int[] testCase2 = {9, 10};

        for (int t = 0; t < testCase.length; t++) {
            System.out.println(solution.solution(testCase[t], testCase2[t]));
        }
    }

    static class Solution {
        public int solution(int[] d, int budget) {
            
            // 정렬 후 싼 부서부터 가능한 모두 지원
            Arrays.sort(d);
            for (int i = 0; i < d.length; i++) {
                if (budget >= d[i]) {
                    budget -= d[i];
                } else {
                    return i;
                } 
            }
            
            // 다 하고 돈 남음
            return d.length;
        }
    }
}
