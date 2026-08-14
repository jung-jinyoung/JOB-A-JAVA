import java.util.*;

/**
 * [접근 방법]
 * 주식의 가격과 시간을 담은 정보를 스택에 넣어둠
 * 매 초마다 스택 맨 위 주식과 현재 주식 가격을 비교하여, 떨어진 주식은 (현재 시간) - (주식 당시 시간) 을 기반으로 정답에 기록
 * 이를 스택이 비거나 아직 떨어지지 않은 주식이 나올때까지 반복함
 * 마지막까지 떨어진 적 없는 주식들까지 잊지말고 처리해주자
 * Stock 클래스를 만든 이유는 스택에서 편리하게 사용하기 위함
 * <p>
 * 시간 복잡도: O(N)
 */
public class 주식가격 {

    public static void main(String[] args) {
        Solution solution = new Solution();

        int[][] testCase = {{1, 2, 3, 2, 3}};

        for (int t = 0; t < testCase.length; t++) {
            System.out.println(Arrays.toString(solution.solution(testCase[t])));
        }
    }

    static class Solution {
        public int[] solution(int[] prices) {
            int[] answer = new int[prices.length];
            Deque<Stock> stack = new ArrayDeque<>();

            for (int i = 0; i < prices.length; i++) {
                while (!stack.isEmpty() && stack.peekLast().price > prices[i]) {
                    Stock top = stack.pollLast();
                    answer[top.time] = i - top.time;
                }

                stack.add(new Stock(prices[i], i));
            }

            while (!stack.isEmpty()) {
                Stock top = stack.pollLast();
                answer[top.time] = prices.length - 1 - top.time;
            }
            return answer;
        }

        class Stock {
            public Stock(int price, int time) {
                this.price = price;
                this.time = time;
            }

            int price;
            int time;
        }
    }
}
