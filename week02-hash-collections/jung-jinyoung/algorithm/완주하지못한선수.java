import java.util.HashMap;

/*
 * 전략
 * HashMap에 선수 이름을 key, 참가자 수를 value로 저장
 * 동명이인이 있을 경우 +1
 * 완주햇을 경우 -1
 * 참가자 목록을 순회하며 참가자 수가 0보다 큰 선수를 조회
 */

// 시간복잡도 : O(N) - 각각 for문 순회
// 공간복잡도 : O(N) - 참가자 수만큼 저장
public class 완주하지못한선수 {
    class Solution {
        public String solution(String[] participant, String[] completion) {
            String answer = "";
            // 참가자 정보 저장 hashMap 초기화
            HashMap<String, Integer> participants = new HashMap<>();
            // 참가자 저장
            for (String p : participant) {
                // 동명이인이 이미 저장되어 있다면
                if(participants.containsKey(p)){
                    // 기존값 ++
                    participants.put(p, participants.get(p)+1);
                } else {
                    participants.put(p, 1);
                }
            }

            // 완주했을 경우 기존 값 -1
            for (String c : completion) {
                participants.put(c, participants.get(c)-1);
            }

            // 순회 - 0이 아닐 경우 answer할당 후 종료
            for (String p : participant){
                if(participants.get(p)>0){
                    answer = p;
                    break;
                }
            }
            return answer;
        }
    }
}
