import java.util.*;

public class BinarySearchTest {
    public static void main(String[] args) {
        int[] arr = {1, 2, 2, 2, 3, 4};

        // 없는 값
        printResult(arr, 0);
        
        // 맨 앞
        printResult(arr, 1);
        
        // 맨 뒤
        printResult(arr, 4);
        
        // 중복 다수
        printResult(arr, 2);
    }

    private static void printResult(int[] arr, int target) {
        int lower = BinarySearch.lowerBound(arr, target);
        int upper = BinarySearch.upperBound(arr, target);
        System.out.println("lowerBound: " + lower + ", upperBound: " + upper);
    }
}
