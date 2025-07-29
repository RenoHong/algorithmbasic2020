package class13.practice02;

public class LowestAncestor {

    public static class Node {
        public int value;
        public Node left, right;

        public Node(int val) {
            value = val;
        }
    }

    public static class Info {
        public boolean findA;
        public boolean findB;
        public Node ancestor;

        public Info(boolean a, boolean b, Node anc) {
            findA = a;
            findB = b;
            ancestor = anc;
        }
    }

    public static Node lowestAncestor1(Node head, Node n1, Node n2) {
        return process(head, n1, n2).ancestor;
    }

    private static Info process(Node head, Node a, Node b) {
        if (head == null) {
            return new Info(false, false, null);
        }
        Info leftInfo = process(head.left, a, b);
        Info rightInfo = process(head.right, a, b);

        boolean findA = false;
        boolean findB = false;
        if (leftInfo.findA || rightInfo.findA || head == a) {
            findA = true;
        }
        if (leftInfo.findB || rightInfo.findB || head == b) {
            findB = true;
        }
        Node ancestor = null;
        if (leftInfo.ancestor != null)
            ancestor = leftInfo.ancestor;
        else if (rightInfo.ancestor != null) {
            ancestor = rightInfo.ancestor;
        } else if (findB && head == a) {
            ancestor = a;
        } else if (findA && head == b) {
            ancestor = b;
        }else if(findA && findB){
            ancestor = head ;
        }
        return new Info(findA, findB, ancestor);
    }

}
