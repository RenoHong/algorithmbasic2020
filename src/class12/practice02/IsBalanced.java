package class12.practice02;

public class IsBalanced {

    public static class Info {
        public boolean isBalanced;
        public int height;

        public Info(boolean isBalanced, int height) {
            this.isBalanced = isBalanced;
            this.height = height;
        }
    }

    public static class Node {
        public int value;
        public Node left, right;

        public Node(int val) {
            value = val;
        }
    }

    public static boolean isBalanced1(Node head) {
        return process1(head).isBalanced;
    }

    private static Info process1(Node head) {
        if (head == null) {
            return new Info(true, 0);
        }

        Info leftInfo = process1(head.left);
        Info rightInfo = process1(head.right);
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        return new Info(leftInfo.isBalanced && rightInfo.isBalanced
                && Math.abs(rightInfo.height - leftInfo.height) <= 1
                , height);
    }


}
