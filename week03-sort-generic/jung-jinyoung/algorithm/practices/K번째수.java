// 자바 배열 연습 1. K 번째 수 
// 문제 링크 : https://school.programmers.co.kr/learn/courses/30/lessons/42748

import java.util.Arrays;

class Solution {
    public int[] solution(int[] array, int[][] commands) {
        int[] answer = new int[commands.length];
        for (int idx = 0 ; idx < commands.length ; idx++) {
            int[] command = commands[idx];

            // 인덱스 계산을 위한 -1 처리
            int i = command[0] - 1;
            int j = command[1] ;
            int k = command[2] - 1;
            
            int[] splitArray = Arrays.copyOfRange(array, i, j);
            Arrays.sort(splitArray);
            answer[idx] = splitArray[k];
        }
        return answer;
    }
}