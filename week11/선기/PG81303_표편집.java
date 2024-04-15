import java.util.*;

/*
 * ���Ḯ��Ʈ�� ȿ�����׽�Ʈ ����� ������ �� �����µ�
 * "cmd�� �����ϴ� ��� X���� ���� ��ģ ����� 1,000,000 ������ ��츸 �Է����� �־����ϴ�." �� ������ ���ƾ��׿�.
 */

class PG81303_ǥ���� {
    public String solution(int n, int k, String[] cmd) {
        Table table = new Table(n, k);
        for (String curCmd : cmd) {
            if ("Z".equals(curCmd)) {
                table.revert();
            } else if ("C".equals(curCmd)) {
                table.delete();
            } else {
                String[] cmdAndParam = curCmd.split(" ");
                curCmd = cmdAndParam[0];
                int param = Integer.parseInt(cmdAndParam[1]);
                if ("U".equals(curCmd)) {
                    table.selectPrev(param);
                } else if ("D".equals(curCmd)) {
                    table.selectNext(param);
                }
            }
        }
        return table.status.toString();
    }

    static class Table {
        Node pointer;
        Node header;
        Node trailer;
        Deque<Node> deque;
        StringBuilder status;

        Table(int n, int k) {
            status = new StringBuilder(n);
            header = new Node(null, -1, trailer);
            trailer = new Node(header, -1, null);
            deque = new ArrayDeque<>();
            init(n);
            pointer = header;
            selectNext(k + 1);
        }

        void init(int n) {
            Node prev = header;
            for (int i = 0; i < n; ++i) {
                status.append("O");
                Node t = new Node(prev, i, trailer);
                prev.next = t;
                prev = t;
            }
            trailer.prev = prev;
        }

        void delete() {
            status.setCharAt(pointer.no, 'X');
            deque.push(pointer);
            Node prev = pointer.prev;
            Node next = pointer.next;
            prev.next = next;
            next.prev = prev;
            if (next == trailer) {
                pointer = prev;
            } else {
                pointer = next;
            }
        }

        void selectPrev(int n) {
            for (int i = 0; i < n; ++i) {
                pointer = pointer.prev;
            }
        }

        void selectNext(int n) {
            for (int i = 0; i < n; ++i) {
                pointer = pointer.next;
            }
        }

        void revert() {
            Node u = deque.pop();
            Node next = u.next;
            Node prev = u.prev;
            next.prev = u;
            prev.next = u;
            status.setCharAt(u.no, 'O');
        }
    }

    static class Node {
        int no;
        Node prev;
        Node next;

        Node(Node prev, int no, Node next) {
            this.prev = prev;
            this.no = no;
            this.next = next;
        }
    }
}