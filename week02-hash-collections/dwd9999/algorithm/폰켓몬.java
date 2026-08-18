import java.util.*;

/**
 * [접근 방법]
 * 문제가 쓸모 없이 긴데 결국 종류 별로 하나씩 고르는게 제일 많은 종류를 가지게 됨  
 * 종류별로 고를 수 없는 경우는 딱 하나
 * N/2마리만 고를 수 있는데 종류가 N/2마리 보다 많아서 못 고르는 경우
 * 그러니 총 몇종류인지만 세고, 종류 개수와 N/2 중 작은 값이 정답
 * <p>
 * 시간 복잡도: O(N)
 */
public class 폰켓몬 {

    public static void main(String[] args) {
        Solution solution = new Solution();

        int[][] testCase = {{3, 1, 2, 3}, {3, 3, 3, 2, 2, 4}, {3, 3, 3, 2, 2, 2}};

        for (int t = 0; t < testCase.length; t++) {
            System.out.println(solution.solution(testCase[t]));
        }
    }

    static class Solution {
        public int solution(int[] nums) {
            Set<Integer> numSet = new HashSet<>();

            // 총 몇 종류인지 세기
            for (int i = 0; i < nums.length; i++) {
                numSet.add(nums[i]);
            }

            // 총 종류 개수와 N/2 중 작은 값 반환
            return Math.min(numSet.size(), nums.length / 2);
        }
    }
}
