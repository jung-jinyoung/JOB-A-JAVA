/*
 * 전략
 * 1. 부서별 신청 금액을 오름차순으로 정렬한다.
 * 2. 신청 금액이 작은 부서부터 하나씩 확인한다.
 * 3. 남은 예산으로 지원할 수 있다면 신청 금액을 차감하고
 *    지원한 부서 수를 증가시킨다.
 * 4. 현재 신청 금액을 지원할 수 없다면 반복을 종료한다.
 *    배열이 오름차순이므로 뒤의 더 큰 금액도 지원할 수 없기 때문이다.
 *
 * 시간복잡도: O(n log n)
 * - Arrays.sort() 정렬: O(n log n)
 * - 정렬된 배열 순회: O(n)
 * - 전체 시간복잡도는 더 큰 항인 O(n log n)
 *
 * 공간복잡도: O(log n)
 * - 새로운 배열이나 컬렉션을 만들지 않는다.
 * - Arrays.sort(int[])의 정렬 과정에서 사용하는 호출 스택을 고려한다.
 */

import java.util.Arrays;
class Solution {
    public int solution(int[] d, int budget) {
        // 오름차순 정렬
        Arrays.sort(d);
        
        int count = 0;
        
        for (int amount : d) {
            if (amount > budget) {
                break;
            }
            budget -= amount;
            count++;
        }
        
        return count;
    }
}