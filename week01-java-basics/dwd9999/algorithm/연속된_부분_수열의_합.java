import java.util.Arrays;

/**
 * [접근 방법]
 * 투포인터를 사용하여 부분합을 구하면 되는 문제
 * 배열의 원소가 무조건 양수이므로 왼쪽 포인터가 움직이면 무조건 총합이 감소, 오른쪽은 무조건 증가
 * k보다 크면 왼쪽 옮기고, k보다 작으면 오른쪽 옮기면서 총합이 같아질때를 찾자
 * <p>
 * 시간 복잡도: O(N)
 */
public class 연속된_부분_수열의_합 {

    public static void main(String[] args) {
        Solution solution = new Solution();

        int[][] a = {{1, 2, 3, 4, 5}, {1, 1, 1, 2, 3, 4, 5}, {2, 2, 2, 2, 2}};
        int[] b = {7, 5, 6};

        for (int t = 0; t < a.length; t++) {
            System.out.println(Arrays.toString(solution.solution(a[t], b[t])));
        }
    }

    static class Solution {
        public int[] solution(int[] sequence, int k) {
            int[] answer = {0, sequence.length};

            int start = 0;
            int end = 0;
            int sum = 0;
            while (end <= sequence.length) {
                if (sum > k) { // 총합이 더 크면 왼쪽 포인터 이동
                    sum -= sequence[start++];
                } else { // 총합이 더 작거나 같으면 오른쪽 포인터 이동
                    if (sum == k) {
                        // 총합이 같은데 더 짧은 배열인 경우 정답 갱신
                        if (end - start - 1 < answer[1] - answer[0]) {
                            answer[0] = start;
                            answer[1] = end - 1;
                        }
                    }

                    // 이미 끝에 도착했으면 중단
                    if (end == sequence.length) break;
                    
                    sum += sequence[end++];
                }
            }

            return answer;
        }
    }
}
