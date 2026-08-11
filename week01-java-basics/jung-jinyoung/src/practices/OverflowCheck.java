package practices;

public class OverflowCheck {
    static void main() {
        // int overflow check
        int a = 2_000_000_000;
        int b = 2_000_000_000;
        int c = a + b;

        System.out.println(c) ; // result : -294967296

        // long overflow check
        int d = 2_000_000_000;
        int e = 2_000_000_000;
        long f = d + e;

        System.out.println(f); // result : -294967296

        long c1 = a + b;
        long c2 = (long) a + b;
        System.out.println(c1); // result : -294967296
        System.out.println(c2); // result : 4000000000
    }
}
