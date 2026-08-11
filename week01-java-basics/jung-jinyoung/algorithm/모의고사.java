package algorithm;


/*
    1. 접근방식
    - 세 수포자는 찍는 패턴이 고정 -> 문제 수가 패턴 길이보다 길면 패턴이 처음부터 반복
    - 패턴 길이로 나눈 나머지 연산으로 인덱스 순환

    2. 복잡도
    - 시간 : O(n) -> n = answer.length
    - 공간 : O(1) -> 패턴 배열은 크기 고정

    3. 학습 메모
    - 자바로 알고리즘을 푸는 것이 처음이라, 메서드를 활용하기보다
      배열과 리스트를 다루는 연습을 목적으로 진행했습니다!
 */


import java.util.ArrayList;
import java.util.List;

public class 모의고사 {
    public int[] solution(int[] answers) {
        // 세 수포자의 찍기 패턴
        int[][] patterns = {
                {1, 2, 3, 4, 5},
                {2, 1, 2, 3, 2, 4, 2, 5},
                {3, 3, 1, 1, 2, 2, 4, 4, 5, 5}
        };

        // 1) 채점 — 패턴 순서대로 점수를 쌓으므로 인덱스는 필요 없다
        List<Integer> scores = new ArrayList<>();
        for (int[] pattern : patterns) {
            int len = pattern.length;
            int cnt = 0;
            for (int i = 0; i < answers.length; i++) {
                // answers는 처음부터 끝까지 한 번만 지나가므로 i를 그대로 쓰고,
                // pattern만 i % len 으로 순환시킨다
                if (answers[i] == pattern[i % len]) {
                    cnt++;
                }
            }
            scores.add(cnt);
        }

        // 2) 최댓값 찾기
        int maxValue = scores.get(0);
        for (int i = 1; i < scores.size(); i++) {
            if (scores.get(i) > maxValue) {
                maxValue = scores.get(i);
            }
        }

        // 3) 최고점자 번호 수집
        List<Integer> picked = new ArrayList<>();
        for (int i = 0; i < scores.size(); i++) {
            // 왼쪽 Integer, 오른쪽 int → 자동 언박싱되어 값 비교가 된다.
            // 양쪽 다 Integer였다면 주소 비교가 되어 위험한 자리.
            if (scores.get(i) == maxValue) {
                picked.add(i + 1);
            }
        }

        // 4) List<Integer> → int[] 변환
        int[] answer = new int[picked.size()];
        for (int i = 0; i < picked.size(); i++) {
            answer[i] = picked.get(i);  // Integer → int (언박싱)
        }
        return answer;
    }
}
