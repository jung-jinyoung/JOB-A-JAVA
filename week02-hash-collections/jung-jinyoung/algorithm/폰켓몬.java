import java.util.HashMap;
/*
 * 전략
 * HashMap에 종류를 key로 저장하여 전체 개수를 value로 처리
 * 선택 가능한 폰켓몬 수는 N/2 또는 전체 종류 수 중 가장 작은 값
 */
// 시간복잡도 : O(N) - nums를 한번 순회하며 저장
// 공간복잡도 : O(N) - 최악의 경우 모든 폰켓몬이 서로 다른 종류일 때 N개 저장

public class 폰켓몬 {
    class Solution {
        public int solution(int[] nums) {
            // 연구실 폰켓몬 총 수
            int N = nums.length;
            int total = N/2 ; // 가질 수 있는 폰켓몬 총 수

            // 폰켓몬 종류 정보 HashMap 초기화
            HashMap<Integer, Integer> info = new HashMap<>();

            for(int num : nums) {
                if(info.containsKey(num)){
                    info.put(num, info.get(num) + 1);
                } else {
                    info.put(num, 1);
                }
            }
            return Math.min(info.size(), total);
        }
    }
}
