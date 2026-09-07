/*
 * 전략
 * 1. 모든 스코빌 지수를 최소 힙에 저장한다.
 * 2. 최솟값이 K보다 작으면 가장 작은 두 값을 꺼낸다.
 * 3. 두 값을 섞어 다시 최소 힙에 넣는다.
 * 4. 모든 값이 K 이상이면 혼합 횟수를 반환한다.
 * 5. 값이 하나만 남았는데 K 미만이면 -1을 반환한다.
 *
 * 시간복잡도: O(n log n)
 * - n개를 PriorityQueue에 추가: O(n log n)
 * - 혼합은 최대 n-1번이며 매번 poll/add: O(log n)
 *
 * 공간복잡도: O(n)
 * - PriorityQueue에 최대 n개의 값을 저장
 */


import java.util.PriorityQueue;

class Solution {
    public int solution(int[] scoville, int K) {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        // 우선순위 큐에 값 추가
        for (int value : scoville) { 
            queue.add(value);
        }
        
        int count = 0;
        while (!queue.isEmpty() & queue.peek() < K) {
            if (queue.size() < 2){
                count = -1;
                break;
            }
            int numA = queue.poll();
            int numB = queue.poll();
            
            queue.add(numA+numB*2);
            count++;
        }
        return count;
    }
}