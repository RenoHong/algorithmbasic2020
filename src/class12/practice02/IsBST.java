package class12.practice02;

public class IsBST {

    public static class Node {
        public int value;
        public Node left, right;

        public Node(int v) {
            value = v;
        }
    }

    public static class Info {
        public int max, min;
        public boolean isBST;

        public Info(int max, int min, boolean isBST) {
            this.max = max;
            this.min = min;
            this.isBST = isBST;
        }
    }

    public static boolean isBST(Node head) {
        return process(head).isBST;
    }

    private static Info process(Node head) {
        if (head == null) {
            return null;
        }

        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);

        int max = head.value;
        int min = head.value;
        boolean isBST = true;
        if (leftInfo != null) {
            if (!leftInfo.isBST || leftInfo.max > head.value)
                isBST = false;
            min = Math.min(leftInfo.min, min);
            max = Math.max(leftInfo.max, max);
        }
        if (rightInfo != null) {
            if (!rightInfo.isBST || rightInfo.min < head.value) {
                isBST = false;
            }
            max = Math.max(max, rightInfo.max);
            min = Math.min(min, rightInfo.min);
        }

        return new Info(max, min, isBST);
    }

}
