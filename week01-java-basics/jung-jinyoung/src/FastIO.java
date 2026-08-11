import java.io.*;
import java.util.StringTokenizer;

public class FastIO {
    private BufferedReader br;
    private StringTokenizer st;
    private BufferedWriter bw;

    public FastIO() {
        br = new BufferedReader((new InputStreamReader(System.in)));
        bw = new BufferedWriter((new OutputStreamWriter(System.out)));
    }

    // 토큰이 없으면 새 줄을 읽어 채워주는 보조 메서드 (nextInt/nextLong 공통 로직)
    private void refillIfEmpty() throws IOException {
        if (st == null || !st.hasMoreTokens()) {
            st = new StringTokenizer(br.readLine());
        }
    }

    public int nextInt() throws IOException {
        refillIfEmpty();
        return Integer.parseInt(st.nextToken());
    }

    public long nextLong() throws IOException {
        refillIfEmpty();
        return Long.parseLong(st.nextToken());
    }

    public double nextDouble() throws IOException {
        refillIfEmpty();
        return Double.parseDouble(st.nextToken());
    }

    public String nextLine() throws IOException {
        return br.readLine();
    }

    public void println(Object obj) throws IOException {
        bw.write(obj.toString());
        bw.newLine();
    }

    public void flush() throws IOException {
        bw.flush();
    }
}
