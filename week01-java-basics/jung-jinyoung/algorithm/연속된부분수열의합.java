package algorithm;

/*
    1. 전략
    - 누적합 배열 pre 를 만든다 : pre[x] -> 0 ~ x-1번째 까지의 합
        - [i, j-1] 의 합을 이용하여 O(1)로 구한다.
    - pre[j] - pre[i] = k 구조 활용
        - pre[j] = pre[i] + k 로 j 값 찾기
    - 이분 탐색
        - O(log n)으로 접근 가능

    2. 복잡도
    - 시간복잡도 : O(n logn)
    - 공간복잡도 : O(n)

    3. 학습 메모
    - Arrays.binarySearch는 못 찾으면 -(위치)-1 을 반환
 */


import java.util.Arrays;

class 연속된부분수열의합 {
    class Solution {
        public int[] solution(int[] sequence, int k) {
            int[] answer = {};
            // 1. 누적합
            // pre[i] => 0~i-1까지의 합
            long[] pre = new long[sequence.length + 1];
            for (int i = 0 ; i < sequence.length ; i++) {
                pre[i+1] = pre[i] + sequence[i];
            }

            // 초기화
            int start = 0;
            int end = 0;
            int len = sequence.length + 1;

            // 2. 탐색
            for (int i = 0 ; i < pre.length; i++) {
                long target = pre[i] + k ;
                // 이분탐색
                int j = Arrays.binarySearch(pre, target); // 없으면 -1

                if (j >= 0) {
                    int l = j - i;
                    if (l < len) {
                        len = l;
                        start = i;
                        end = j - 1;
                    }
                }
            }


            return new int[]{start, end};
        }
    }
}