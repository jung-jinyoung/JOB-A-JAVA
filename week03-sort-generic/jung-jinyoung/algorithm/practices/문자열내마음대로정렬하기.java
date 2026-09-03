// 자바 배열 연습 2. 문자열 내 마음대로 정렬하기
// 문제 링크 : https://school.programmers.co.kr/learn/courses/30/lessons/12915
// Comparator를 활용한 정렬

import java.util.Arrays;

class Solution {
    public String[] solution(String[] strings, int n) {
        Arrays.sort(strings, (a,b) ->{
            char charA = a.charAt(n);
            char charB = b.charAt(n);
            // n번째 기준으로 오름차순
            // 같다면 사전순으로 
            if (charA==charB) {
                return a.compareTo(b);
            }
            return Character.compare(charA, charB);
        });
        return strings;
    }
}