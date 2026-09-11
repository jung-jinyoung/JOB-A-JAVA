// 전략 : 재귀 보단 DP 활용 -> 값이 커지는 것을 방지 (오버 플로우 방지)하기 위해 리스트에 저장할 때 마다 바로 연산
// 시간복잡도 : O(N)
// 공간복잡도 : O(N)

public class 피보나치수 {
    class Solution {
        public int solution(int n) {
            int mod = 1234567;
            int[] dp = new int[n+1];
            dp[0] = 0;
            dp[1] = 1;
            for(int i = 2 ; i <= n ; i++){
                dp[i] = (dp[i-1] + dp[i-2]) % mod;
            }
            return dp[n];
        }
    }
}
