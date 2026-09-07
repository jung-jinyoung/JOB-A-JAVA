public class BinarySearch {

    /**
     * target 이상인 값이 처음 나타나는 인덱스를 반환한다.
     */
    public static int lowerBound(
        int[] arr,
        int target
    ) {
        int left = 0;
        int right = arr.length;

        while (left < right) {
            int mid = (left + right) / 2;

            if (arr[mid] >= target) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    /**
     * target보다 큰 값이 처음 나타나는 인덱스를 반환한다.
     */
    public static int upperBound(
        int[] arr,
        int target
    ) {
        int left = 0;
        int right = arr.length;

        while (left < right) {
            int mid = (left + right) / 2;

            if (arr[mid] > target) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }
}