package practices;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class ArrayListCheck {

    static void main() {
        List<Integer> list = new ArrayList<>();
        System.out.println(((ArrayList<Integer>) list).size()); // result 0

        /*
        List<Integer> list2 = Arrays.asList(1, 2, 3);
        list2.add(4);
        */
        // UnsupportedOperationException 실행 예외 에러 발생

        List<Integer> fixedList = Arrays.asList(1, 2, 3);        // 크기 고정
        List<Integer> realList = new ArrayList<>(fixedList);      // 진짜 ArrayList로 복사
        realList.add(4);   // 정상 동작
    }
}
