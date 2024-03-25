import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BOJ20926_�����̷� { // 936ms
    // �̷� ũ��
    static int W, H;
    // ���
    static int[][] result;
    // �̷�
    static String[][] maze;
    // ����
    static int[][] dirs = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };
    // �׶� ��ġ
    static int tx, ty;
    // Ż�� ��ġ
    static int ex, ey;

    static class Info implements Comparable<Info> {
        int x;
        int y;
        int time;

        Info(int x, int y, int time) {
            this.x = x;
            this.y = y;
            this.time = time;
        }

        @Override
        public int compareTo(Info o) {
            // TODO Auto-generated method stub
            return this.time - o.time;
        }

        @Override
        public String toString() {
            return "Info [x=" + x + ", y=" + y + ", time=" + time + "]";
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        W = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());

        maze = new String[H][W];
        result = new int[H][W];

        for (int i = 0; i < H; i++) {
            maze[i] = br.readLine().split("");
            for (int j = 0; j < W; j++) {
                if (maze[i][j].equals("T")) {
                    tx = i;
                    ty = j;
                    maze[i][j] = "0";
                } else if (maze[i][j].equals("E")) {
                    ex = i;
                    ey = j;
                }
            }
        }
        // result ��ü Integer.MAX_VALUE�� �ʱ�ȭ
        for (int i = 0; i < H; i++)
            Arrays.fill(result[i], Integer.MAX_VALUE);

        getResult();
        // E ��ġ���� �������� �������� -1, �ƴϸ� ���ŵ� �� ���
        if (result[ex][ey] == Integer.MAX_VALUE) {
            System.out.println(-1);
        } else {
            System.out.println(result[ex][ey]);
        }
    }

    public static void getResult() {
        PriorityQueue<Info> pq = new PriorityQueue<>();
        // ���� ���� pq�� �ְ� ������. 
        pq.offer(new Info(tx, ty, 0));
        
        while (!pq.isEmpty()) {
            Info now = pq.poll();
            for (int i = 0; i < 4; i++) {
                int sum = now.time;
                // ���� �ð��� �ش� ��ġ�� result ���� ũ�� Ž���� �ʿ䰡 ���� 
                if (sum >= result[ex][ey])
                    continue;
                // ���ι���
                if (i == 0 || i == 1) {
                    for (int j = 1; j < W; j++) {
                        int ny = now.y + j * dirs[i][1];
                        // ����� ��쳪 ������ ��� break
                        if (ny < 0 || ny >= W || maze[now.x][ny].equals("H"))
                            break;
                        // �ⱸ�� ��� �� �������ְ� break;
                        if (maze[now.x][ny].equals("E")) {
                            if (sum < result[ex][ey]) {
                                result[ex][ey] = sum;
                            }
                            break;
                        }
                        // ���� ��� �������� ���� ������ pq�� �־���
                        else if (maze[now.x][ny].equals("R")) {
                        	// sum�� result���� ũ�� �����ʿ� ����, j�� 1�̸� �ٷ� ���� ���̹Ƿ� �ʿ����
                            if (sum >= result[now.x][ny - dirs[i][1]] || j == 1) {
                                break;
                            }
                            // ���� ���� �����ϰ� pq�� �־���
                            else {
                                result[now.x][ny - dirs[i][1]] = sum;
                                pq.offer(new Info(now.x, ny - dirs[i][1], sum));
                                break;
                            }
                         
                        }
                        // ������ ��� sum�� �ش� �̲��ð� �� ������
                        else {
                            sum += Integer.parseInt(maze[now.x][ny]);
                        }
                    }
                }
                // ���ι��� �����ϰ� ����
                else {
                    for (int j = 1; j < H; j++) {
                        int nx = now.x + j * dirs[i][0];
                        
                        if (nx < 0 || nx >= H || maze[nx][now.y].equals("H"))
                            break;
                        
                        if (maze[nx][now.y].equals("E")) {
                            if (sum < result[ex][ey]) {
                                result[ex][ey] = sum;
                            }
                            break;
                        }
                        
                        else if (maze[nx][now.y].equals("R")) {
                            if (sum >= result[nx - dirs[i][0]][now.y] || j == 1) {
                                break;
                            }
                            else {
                                result[nx - dirs[i][0]][now.y] = sum;
                                pq.offer(new Info(nx - dirs[i][0], now.y, sum));
                                break;
                            }
                        }
                        
                        else {
                            sum += Integer.parseInt(maze[nx][now.y]);
                        }
                    }
                }
            }
        }
    }
}