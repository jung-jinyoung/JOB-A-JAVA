/*
 * 전략
 * 1. 아직 가격이 떨어지지 않은 주식의 인덱스 스택에 저장
 * 현재 가격이 스택의 가장 마지막 가격보다 낮으면 pop -> answer 값 저장
 * 가격이 덜어지지 않은 인덱스는 계속 스택에 저장
 * 마지막까지 떨어지지 않은 인덱스 점검 후 answer 리턴
 */

// 시간 복잡도 : O(N) - 각 인덱스는 스택에 한번 push, pop.
// 공간 복잡도 : O(N) - 최악의 경우 모든 인덱스가 스택에 저장

import java.util.Deque;
import java.util.ArrayDeque;

public class 주식가격 {
    public int[] solution(int[] prices) {
        int l = prices.length;
        // 길이 만큼 정답 배열 초기화
        int[] answer = new int[l];

        Deque<Integer> stack = new ArrayDeque<>();
        for(int i = 0 ; i < l ; i++) {
            while (!stack.isEmpty() && prices[i] < prices[stack.peek()] ) {
                int j = stack.pop();
                answer[j] = i - j;
            }
            stack.push(i);
        }
        for (int i : stack) {
            answer[i] = l - i - 1 ;
        }
        return answer;
    }
}
