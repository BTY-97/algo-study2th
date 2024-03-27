import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ1275_Ŀ�Ǽ�2 {
    static int N, Q;
    static int[] arr;
    static long[] segTree;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        N = Integer.parseInt(st.nextToken());
        Q = Integer.parseInt(st.nextToken());
        arr = new int[N + 1];
        segTree = new long[N * 4];

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++)
            arr[i] = Integer.parseInt(st.nextToken());

        // ���׸�Ʈ Ʈ�� �ʱ�ȭ
        init(1, 1, N);


        for (int i = 0; i < Q; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            // �Է°����� x > y�� ��� swap
            if (x > y) {
                int temp = y;
                y = x;
                x = temp;
            }
            sb.append(sum(x, y, 1, 1, N)+"\n");
            // a��° idx�� value�� b�� �ٲ�
            update(a, b, 1, 1, N);
        }
        System.out.println(sb);
    }

    public static long init(int node, int start, int end) {
        // ��������̸� �� ����
        if (start == end) {
            return segTree[node] = arr[start];
        }
        // �ƴ� ��� ���� ���鼭 �ʱ�ȭ
        int mid = (start + end) / 2;
        return segTree[node] = init(node * 2, start, mid) + init(node * 2 + 1, mid + 1, end);
    }

    public static long update(int idx, long value, int node, int start, int end) {
        // update�Ϸ��� idx�� ���� ���� ���� ��� return
        if (idx < start || idx > end) {
            return segTree[node];
        }
        // ��������� ��� ������� �� ����
        if (start == end) {
            return segTree[node] = value;
        }
        int mid = (start + end) / 2;
        // segTree�� node ���� ���ʲ� + �����ʲ��� update
        return segTree[node] = update(idx, value, node * 2, start, mid) + update(idx, value, node * 2 + 1, mid + 1, end);
    }

    public static long sum(int left, int right, int node, int start, int end) {
        // ���� ���Ϸ��� ������ ���� Ž���ϴ� ����� ���� �ۿ� ���� �� return
        if (left > end || right < start) {
            return 0;
        }
        // ���� ���Ϸ��� ������ ���� Ž���ϴ� ��带 �����ϰ� ���� �� ���� ����� �� return
        if (left <= start && end <= right) {
            return segTree[node];
        }
        int mid = (start + end) / 2;
        // segTree�� node ���� ���ʲ� + �����ʲ�
        return sum(left, right, node * 2, start, mid) + sum(left, right, node * 2 + 1, mid + 1, end);
    }
}