package practices;

import java.util.Arrays;

public class ArrayCopyCheck {
    public static void main(String[] args) {
        int[] oldArr = {1, 2, 3};
        int[] newArr = new int[4];
        /*
        System.arraycopy(원본배열, 원본시작위치, 대상배열, 대상시작위치, 복사할개수)
        */

        System.arraycopy(oldArr, 0, newArr, 0, oldArr.length);

        newArr[3] = 4;

        System.out.println("oldArr = " + Arrays.toString(oldArr));
        System.out.println("newArr = " + Arrays.toString(newArr));
    }
}