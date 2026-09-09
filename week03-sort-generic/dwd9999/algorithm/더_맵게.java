import java.util.PriorityQueue;

/**
 * [접근 방법]
 * 우선순위 큐를 사용할 수 있는지 공부하는 문제
 * 제일 작은 원소 2개를 뽑아서 두번째로 작은 원소에 2를 곱해 다시 넣자
 * 진짜 코테였으면 int말고 long으로 썼을듯?
 * <p>
 * 시간 복잡도: O(N)
 */
public class 더_맵게 {
    public static void main(String[] args) {
        Solution solution = new Solution();

        int[][] testCase = {{1, 2, 3, 9, 10, 12}};
        int[] testCase2 = {7};

        for (int t = 0; t < testCase.length; t++) {
            System.out.println(solution.solution(testCase[t], testCase2[t]));
        }
    }

    static class Solution {
        public int solution(int[] scoville, int K) {
            
            // 힙에 싹다 넣고
            PriorityQueue<Integer> pq = new PriorityQueue<>();
            for (int i = 0; i < scoville.length; i++) {
                pq.add(scoville[i]);
            }
            
            // 제일 낮은거 2개 빼고 곱하고 더하고 반복
            int count = 0;
            while (!pq.isEmpty() && pq.peek() < K) {
                if (pq.size() < 2) return -1;
                
                pq.add(pq.poll() + (pq.poll() * 2));
                count++;
            }
            return count;
        }
    }
}
