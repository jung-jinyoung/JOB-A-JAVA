import java.util.HashMap;
import java.util.Map;

/**
 * [접근 방법]
 * participant를 순회하며 수를 세고, completion을 순회하며 다시 까주자
 * <p>
 * 시간 복잡도: O(N)
 */
public class 완주하지_못한_선수 {

    public static void main(String[] args) {
        Solution solution = new Solution();

        String[][] testCase = {{"leo", "kiki", "eden"}, {"marina", "josipa", "nikola", "vinko", "filipa"}, {"mislav", "stanko", "mislav", "ana"}};
        String[][] testCase2 = {{"eden", "kiki"}, {"josipa", "filipa", "marina", "nikola"}, {"stanko", "ana", "mislav"}};

        for (int t = 0; t < testCase.length; t++) {
            System.out.println(solution.solution(testCase[t], testCase2[t]));
        }
    }

    static class Solution {
        public String solution(String[] participant, String[] completion) {
            Map<String, Integer> counter = new HashMap<>();

            // 사람 수 세기
            for (int i = 0; i < participant.length; i++) {
                counter.put(participant[i], counter.getOrDefault(participant[i], 0) + 1);
            }

            // 도착하면 다시 빼기
            for (int i = 0; i < completion.length; i++) {
                counter.put(completion[i], counter.get(completion[i]) - 1);
            }

            // 남아있는 사람 찾기
            for (String key : counter.keySet()) {
                if (counter.get(key) != 0) {
                    return key;
                }
            }

            // 조건 상 여기 올 일 없음
            return "";
        }
    }
}
