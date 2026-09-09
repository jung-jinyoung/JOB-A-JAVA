import java.util.Deque;
import java.util.ArrayList;
import java.util.List;
import java.util.ArrayDeque;
import java.util.Arrays;

public class Graph {
    // 각 정점 이웃 목록
    private final List<List<Integer>> adjacency;
    // 방향 그래프 여부
    private final boolean directed;

    public Graph(int vertexCount, boolean directed) {
        this.directed = directed;
        this.adjacency = new ArrayList<>();
        for (int i = 0 ; i < vertexCount ; i++) {
            this.adjacency.add(new ArrayList<>());
        }
    }
    // 간선 추가 메서드
    public void addEdge(int u, int v) {
        this.adjacency.get(u).add(v);
        // 무방향이면 추가
        if (!directed) {
            this.adjacency.get(v).add(u);
        }
    }

    // DFS
    public List<Integer> dfs(int start) {
        // 방문 배열
        boolean[] visited = new boolean[adjacency.size()];
        List<Integer> order =  new ArrayList<>();
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(start);

        while (!stack.isEmpty()) {
            int v = stack.pop();
            if (visited[v]) {
                continue;
            }
            visited[v] = true;
            order.add(v);
            for(Integer neighbor : adjacency.get(v)) {
                if (!visited[neighbor]) {
                    stack.push(neighbor);
                }
            }
        }
        return order;
    }

    // BFS
    public List<Integer> bfs(int start) {
        boolean[] visited = new boolean[adjacency.size()];
        List<Integer> order =  new ArrayList<>();
        Deque<Integer> queue = new ArrayDeque<>();

        visited[start] = true;
        queue.add(start);
        while (!queue.isEmpty()) {
            int v = queue.pollFirst();
            order.add(v);
            for(int neighbor : adjacency.get(v)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.add(neighbor);
                }
            }
        }

        return order;
    }

    // 최단 거리 메서드 (BFS를 활용하여 구할 수 있음)
    public int[] shortestPath(int start) {
        int[] distance = new int[adjacency.size()];
        Arrays.fill(distance, -1);

        Deque<Integer> queue = new ArrayDeque<>();
        distance[start] = 0;
        queue.add(start);
        while (!queue.isEmpty()) {
            int v = queue.pollFirst();
            for(int neighbor : adjacency.get(v)) {
                // 아직 아무도 방문하지 않았으면
                if (distance[neighbor] == -1) {
                    distance[neighbor] = distance[v] + 1;
                    queue.add(neighbor);
                }
            }
        }
        return distance;
    }


    // 출력
    public static void main(String[] args) {
        Graph graph = new Graph(4, false);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 3);

        System.out.println(graph.dfs(0)); // [0, 2, 3, 1]
        System.out.println(graph.bfs(0)); // [0, 1, 2, 3]
        System.out.println(Arrays.toString(graph.shortestPath(0))); // [0, 1, 1, 2]
    }

}
