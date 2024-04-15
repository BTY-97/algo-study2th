import java.io.*;
import java.util.*;

/*
 * 12440KB
 * 100ms
 *
 * 20��� ���̵��� 30��¥�� ���̱ⱸ 1���� Ż �� ������ ���̰� ž���� �ð��� (600�� - 30)���� �ȴ�.
 * ������ ���̰� ž���� �ð� endTime�� �Ű����� Ž������ ã�µ� �ش� �ð����� ž���� ���� �� riddenCnt��
 * n���� ũ�ų� �����鼭 ���� ���� ���� ����.
 * riddenCnt���� n��ŭ ���� �� �� ��ŭ ���̱ⱸ�� �ڿ��� ���� Ž���Ͽ�
 * ������ ���̰� ž���ϰ� �� ���̱ⱸ�� ã�´�.
 */
public class BOJ1561_���̰��� {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder sb = new StringBuilder();
    static StringTokenizer st;

    static int n, m;
    static int[] attractions;
    static int[] attractionCntByTime;

    public static void main(String[] args) throws IOException {
        n = nextInt();
        m = nextInt();
        attractions = new int[m + 1];
        attractionCntByTime = new int[31];
        for (int i = 1; i <= m; ++i) {
            attractions[i] = nextInt();
            ++attractionCntByTime[attractions[i]];
        }
        long low = 0;
        long high = 60_000_000_000L;
        long riddenCnt = 0;
        long endTime = 0;
        while (low <= high) {
            long mid = (low + high) >>> 1;
            riddenCnt = getRiddenCntByTime(mid);
            if (riddenCnt < n) {
                low = mid + 1L;
                endTime = low;
            } else {
                high = mid - 1L;
            }
        }
        int ans = 0;
        riddenCnt = getRiddenCntByTime(endTime);
        int diff = (int) (riddenCnt - n);
        for (int i = m; i >= 1; --i) {
            if (endTime % attractions[i] == 0) {
                --diff;
                if (diff < 0) {
                    ans = i;
                    break;
                }
            }
        }
        System.out.println(ans);
    }

    static long getRiddenCntByTime(long time) {
        long ans = 0;
        for (int i = 1; i <= 30; ++i) {
            int size = attractionCntByTime[i];
            if (size != 0) {
                ans += (time / i + 1) * size;
            }
        }
        return ans;
    }

    static String next() throws IOException {
        while (st == null || !st.hasMoreTokens()) {
            st = new StringTokenizer(br.readLine());
        }
        return st.nextToken();
    }

    static int nextInt() throws IOException {
        return Integer.parseInt(next());
    }
}