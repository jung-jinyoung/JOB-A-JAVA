package practices;

public class StringCheck {
    static void main() {
        String a = "hello";
        String b = a;
        a = a + " world";

        System.out.println(a); // hello world
        System.out.println(b); // hello

        String x = "hello";
        String y = "hello";

        System.out.println(x == y); // true
    }
}
