/**
 * [전략]
 * - 그래프 탐색(DFS)을 이용해 연결된 컴퓨터(네트워크)의 개수를 구하는 문제
 * - 인접 행렬(computers)을 기반으로, 방문하지 않은 노드를 시작점 삼아 DFS 수행
 * - 한 번의 DFS로 도달 가능한 모든 노드는 같은 네트워크로 간주 -> answer++
 * - 방문 배열(visited)로 중복 탐색 방지
 *
 * [시간 복잡도] O(n^2)
 * - 바깥 for문: 최대 n번 (각 노드를 시작점으로 확인)
 * - DFS 내부에서 각 노드마다 인접 행렬 전체 행(n개)을 순회 -> O(n)
 * - 결국 모든 노드가 한 번씩 방문되며 각 방문마다 O(n) 탐색 -> 전체 O(n^2)
 *
 * [공간 복잡도] O(n)
 * - visited 배열: O(n)
 * - stack: 최악의 경우(모두 연결된 그래프) 최대 n개의 노드를 담음 -> O(n)
 */


import java.util.Deque;
import java.util.ArrayDeque;


public class 네트워크 {

    class Solution {
        public int solution(int n, int[][] computers) {
            int answer = 0;

            // 방문 배열
            boolean[] visited = new boolean[n];
            Deque<Integer> stack = new ArrayDeque<>();

            for(int i = 0 ; i < n ; i++){
                // 이미 다른 노드와 연결되어 있으면
                if(visited[i]) {
                    continue;
                }
                stack.add(i);
                answer ++;

                while (!stack.isEmpty()){
                    int v = stack.pop();
                    visited[v] = true;
                    for(int j = 0 ; j < n ; j++){
                        if (!visited[j] && computers[v][j] == 1) {
                            stack.add(j);
                        }
                    }
                }
            }


            return answer;
        }
    }
}
