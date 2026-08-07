package practices;

public class ConversionCheck {
    static void main() {
        double d = 3.99;
        int i = (int) d;

        System.out.println(i); // result 3

        double d2 = 5_000_000_000.0;   // int 범위(약 21억)를 훌쩍 넘는 값
        int i2 = (int) d2;

        System.out.println(i2);
    }
}
