import java.util.*;

public class MyHashMapTest {
    
    static class Normal {
        private final int id;

        public Normal(int id) { this.id = id; }

        @Override
        public int hashCode() {
            return Integer.hashCode(id); // 고유한 해시코드 반환
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            return id == ((Normal) o).id;
        }
    }

    static class Zero {
        private final int id;

        public Zero(int id) { this.id = id; }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            return id == ((Zero) o).id;
        }
    }

    public static void main(String[] args) {
        int dataSize = 50000;

        Map<Normal, Integer> normalMap = new HashMap<>();
        Map<Zero, Integer> zeroMap = new HashMap<>();

        for (int i = 0; i < dataSize; i++) {
            normalMap.put(new Normal(i), i);
            zeroMap.put(new Zero(i), i);
        }

        long normalStart = System.nanoTime();
        for (int i = 0; i < dataSize; i++) {
            normalMap.get(new Normal(i));
        }
        long normalEnd = System.nanoTime();
        long normalDuration = (normalEnd - normalStart) / 1000000;

        
        long zeroStart = System.nanoTime();
        for (int i = 0; i < dataSize; i++) {
            zeroMap.get(new Zero(i));
        }
        long zeroEnd = System.nanoTime();
        long zeroDuration = (zeroEnd - zeroStart) / 1000000;

        // 결과 출력
        System.out.println("정상: " + normalDuration + "ms");
        System.out.println("비정상: " + zeroDuration + "ms");
    }
}
