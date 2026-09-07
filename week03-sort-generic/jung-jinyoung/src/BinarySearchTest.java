public class BinarySearchTest {

    public static void main(String[] args) {
        testMissingTarget();
        testTargetAtBeginning();
        testTargetAtEnd();
        testDuplicatedTarget();

        System.out.println("모든 테스트 통과");
    }

    static void testMissingTarget() {
        int[] arr = {1, 3, 3, 3, 7, 9};

        assertEquals(
            4,
            BinarySearch.lowerBound(arr, 5),
            "없는 값 lowerBound"
        );

        assertEquals(
            4,
            BinarySearch.upperBound(arr, 5),
            "없는 값 upperBound"
        );
    }

    static void testTargetAtBeginning() {
        int[] arr = {1, 3, 3, 3, 7, 9};

        assertEquals(
            0,
            BinarySearch.lowerBound(arr, 1),
            "맨 앞 lowerBound"
        );

        assertEquals(
            1,
            BinarySearch.upperBound(arr, 1),
            "맨 앞 upperBound"
        );
    }

    static void testTargetAtEnd() {
        int[] arr = {1, 3, 3, 3, 7, 9};

        assertEquals(
            5,
            BinarySearch.lowerBound(arr, 9),
            "맨 뒤 lowerBound"
        );

        assertEquals(
            6,
            BinarySearch.upperBound(arr, 9),
            "맨 뒤 upperBound"
        );
    }

    static void testDuplicatedTarget() {
        int[] arr = {1, 3, 3, 3, 7, 9};

        assertEquals(
            1,
            BinarySearch.lowerBound(arr, 3),
            "중복 lowerBound"
        );

        assertEquals(
            4,
            BinarySearch.upperBound(arr, 3),
            "중복 upperBound"
        );
    }

    static void assertEquals(
        int expected,
        int actual,
        String testName
    ) {
        if (expected != actual) {
            throw new AssertionError(
                testName
                + " 실패: expected="
                + expected
                + ", actual="
                + actual
            );
        }

        System.out.println(testName + " 통과");
    }
}