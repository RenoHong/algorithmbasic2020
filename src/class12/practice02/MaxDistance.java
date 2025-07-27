package class12.practice02;

public class MaxDistance {

    public static class Node {
        public int value;
        public Node left, right;

        public Node(int val) {
            value = val;
        }
    }

    public static class Info {
        public int height;
        public int maxDistance;

        public Info(int h, int maxDistance) {
            this.height = h;
            this.maxDistance = maxDistance;
        }
    }

    public static int MaxDistance1(Node head) {
        return process(head).maxDistance;
    }

    private static Info process(Node head) {
        if (head == null) {
            return new Info(0, 0);
        }
        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);

        int height = Math.max(leftInfo.height, rightInfo.height) + 1;

        int maxDistance = Math.max(leftInfo.maxDistance,
                Math.max(rightInfo.maxDistance, leftInfo.height + rightInfo.height + 1)
        );
        return new Info(height, maxDistance);

    }

}
