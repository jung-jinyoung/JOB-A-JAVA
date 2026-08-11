import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * [접근 방법]
 * 시험 문제가 많아봐야 10,000개라 O(N)으로 완탐만 해도 터질 일 없는 문제
 * answers를 한번 순회하며 각 수포자들의 정답과 비교해보자
 * <p>
 * 시간 복잡도: O(N)
 */
public class 모의고사 {

    public static void main(String[] args) {
        Solution solution = new Solution();

        int[][] testCase = {{1, 2, 3, 4, 5}, {1, 3, 2, 4, 2}};

        for (int t = 0; t < testCase.length; t++) {
            System.out.println(Arrays.toString(solution.solution(testCase[t])));
        }
    }

    static class Solution {
        public int[] solution(int[] answers) {
            // 패턴 미리 선언
            int[][] patterns = {{1, 2, 3, 4, 5},
                    {2, 1, 2, 3, 2, 4, 2, 5},
                    {3, 3, 1, 1, 2, 2, 4, 4, 5, 5}};

            // answers 배열을 쭉 탐색하며 패턴과 비교하여 점수 계산
            int[] score = new int[3];
            for (int i = 0; i < answers.length; i++) {
                for (int j = 0; j < 3; j++) {
                    if (answers[i] == patterns[j][i % patterns[j].length]) {
                        score[j]++;
                    }
                }
            }

            // 최고 점수 구하기
            int max = Math.max(score[0], Math.max(score[1], score[2]));

            // 최고 점수를 받은 사람 찾기
            List<Integer> answer = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                if (score[i] == max) {
                    answer.add(i + 1);
                }
            }

            // return 형식 맞게 변경
            return answer.stream().mapToInt(Integer::intValue).toArray();
        }
    }

}
