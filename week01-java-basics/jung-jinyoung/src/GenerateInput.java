import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class GenerateInput {
    public static void main(String[] args) throws IOException {
        FileWriter fw = new FileWriter("input.txt");
        Random rand = new Random();

        for (int i = 0; i < 100000; i++) {
            fw.write(rand.nextInt(1000) + "\n");
        }
        fw.close();
    }
}