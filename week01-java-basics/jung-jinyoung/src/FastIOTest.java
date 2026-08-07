import java.io.IOException;

public class FastIOTest {
    static void main() throws IOException {
        FastIO io = new FastIO();

        int a = io.nextInt();
        int b = io.nextInt();

        io.println(a+b);
        io.flush();


    }
}
