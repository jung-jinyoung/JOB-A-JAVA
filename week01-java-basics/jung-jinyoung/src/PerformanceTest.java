import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class PerformanceTest {
    static void main() throws IOException {

        // Scanner
        for (int r = 1 ; r <= 5 ; r++) {
            long start = System.nanoTime();

            Scanner sc = new Scanner(new FileReader("input.txt"));
            while (sc.hasNextInt()){
                sc.nextInt();
            }
            sc.close();

            long elapsed = (System.nanoTime()-start)/1_000_000;
            System.out.println(r + "회차 Scanner : " + elapsed + "ms");
        }
        System.out.println("--------------------------------------");
        // BufferReader
        for (int r = 1 ; r <= 5 ; r++) {
            long start = System.nanoTime();

            BufferedReader br = new BufferedReader(new FileReader("input.txt"));
            String line ;
            while ((line = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line);
                while (st.hasMoreTokens()) {
                    Integer.parseInt(st.nextToken());
                }
            }
            br.close();

            long elapsed = (System.nanoTime()-start)/1_000_000;
            System.out.println(r + "회차 BufferedReader : " + elapsed + "ms");
        }

    }
}
