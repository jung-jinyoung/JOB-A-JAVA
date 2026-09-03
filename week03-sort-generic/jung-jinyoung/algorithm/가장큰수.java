// compareTo() 메서드 : 문자열 비교 메서드 
// 문자열 처리해서 정렬하는 방법을 사용하여 가장 큰 수를 만들 수 있다.

/*
 * 전략
 * 1. int 배열을 String 배열로 변환한다.
 * 2. 두 문자열 a, b에 대해 a+b와 b+a를 비교한다.
 * 3. 더 큰 조합이 앞에 오도록 내림차순 정렬한다.
 * 4. 정렬된 문자열을 모두 이어 붙인다.
 * 5. 모든 값이 0이면 "000" 대신 "0"을 반환한다.
 *
 * 시간복잡도: O(nk log n)
 * - n은 숫자의 개수, k는 숫자 하나의 최대 자릿수
 * - k를 상수로 보면 O(n log n)
 *
 * 공간복잡도: O(nk)
 * - 문자열 배열과 최종 결과 문자열 저장
 * - k를 상수로 보면 O(n)
 */


import java.util.Arrays;

class Solution {
    public String solution(int[] numbers) {
        // 계산 편리를 위한 정수열 배열 ->  문자열 배열
        String[] values = new String[numbers.length];
        for (int i = 0 ; i < numbers.length ; i++){
            values[i] = String.valueOf(numbers[i]);
        }
        Arrays.sort(values, (a,b) -> {
            String ab = a + b;
            String ba = b + a;
            return ba.compareTo(ab);
        });
        
        // [0,0,0] ... 모든 값이 0 일 경우 처리
        if (values[0].equals("0")) {
            return "0";
        }

        return String.join("", values);
    }
}