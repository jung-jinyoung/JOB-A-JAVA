import java.io.*;
import java.util.StringTokenizer;

public class FastIO {

    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private StringTokenizer st;

    public String nextWord() {
        String line;
        while (st == null || !st.hasMoreTokens()) {
            try {
                line = br.readLine();
                if (line == null) return null;
                st = new StringTokenizer(line);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return st.nextToken();
    }

    public int nextInt() {
        return Integer.parseInt(nextWord());
    }

    public long nextLong() {
        return Long.parseLong(nextWord());
    }

    public String nextLine() {
        try {
            return br.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void write(String str) {
        try {
            bw.write(str);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void println() {
        try {
            bw.newLine();
            bw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
