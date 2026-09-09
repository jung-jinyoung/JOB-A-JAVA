/**
 * [전략]
 * - BFS를 이용해 (0,0)에서 (n-1,m-1)까지의 최단 거리를 구함
 * - BFS는 시작점에서부터 거리 1, 2, 3... 순서로 퍼져나가며 탐색하므로,
 *   맨 처음 도착점에 도달했을 때의 거리가 곧 최단 거리가 됨
 * - distances 배열로 방문 여부(=거리) 체크 및 저장을 동시에 처리 (-1이면 미방문)
 * - 상하좌우 4방향(directions)을 순회하며 벽(0)이 아니고 아직 방문 안 한 칸이면 큐에 추가
 * - 큐가 빌 때까지 반복 후, 도착점의 거리값을 반환 (도달 못 했으면 -1 그대로 반환됨)
 *
 * [시간 복잡도] O(n * m)
 * - 전체 칸(n*m)이 각각 최대 한 번씩만 큐에 들어가고 처리됨 (distances로 중복 방문 차단)
 * - 각 칸 처리 시 4방향만 확인하므로 상수 시간(O(4)) 소요 -> 전체 O(n*m)
 *
 * [공간 복잡도] O(n * m)
 * - distances 배열: O(n*m)
 * - queue: 최악의 경우 모든 칸이 큐에 들어갈 수 있음 -> O(n*m)
 */

import java.util.Deque;
import java.util.ArrayDeque;
import java.util.Arrays;


 public class 게임맵최단거리 {

    class Solution {
        public int solution(int[][] maps) {
            int n = maps.length;
            int m = maps[0].length;
            int answer = 0;

            // 거리 저장 이중 행렬 초기화
            int[][] distances = new int[n][m];
            for(int i = 0 ; i < n ; i++){
                Arrays.fill(distances[i], -1);
            }

            Deque<int[]> queue = new ArrayDeque<>();
            queue.add(new int[]{0, 0});
            distances[0][0] = 1; // 시작 거리 1

            // 동서남북 방향 이동값
            int[][] directions = new int[][]{
                    {0,1},
                    {1,0},
                    {0,-1},
                    {-1,0}
            };

            while (!queue.isEmpty()){
                int[] cur = queue.poll();
                int x = cur[0];
                int y = cur[1];

                for(int[] dir : directions){
                    int dx = dir[0];
                    int dy = dir[1];
                    int nx = x + dx;
                    int ny = y + dy;
                    // 영역 밖이 아니고 벽이 없다면
                    if (0 <= nx && nx < n && 0 <= ny && ny < m && maps[nx][ny] == 1){
                        // 이전에 방문하지 않았으면 이동
                        if (distances[nx][ny] == -1){
                            distances[nx][ny] = distances[x][y] + 1;
                            queue.add(new int[]{nx, ny});
                        }
                    }
                }

            }
            return distances[n-1][m-1];
        }
    }
}
