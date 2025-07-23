package class10.practice02;

import java.util.Stack;

public class UnRecursiveTraversalBT {

    public static void pre(TreeNode head) {
        if (head == null)
            return;
        Stack<TreeNode> stk = new Stack<>();
        stk.push(head);
        while (!stk.isEmpty()) {
            TreeNode current = stk.pop();
            System.out.println(current.value);
            if (current.right != null)
                stk.push(current.right);
            if (current.left != null)
                stk.push(current.left);
        }
    }

    public static void in(TreeNode head) {
        if (head == null)
            return;
        Stack<TreeNode> stk = new Stack<>();
        TreeNode current = head;
        while (!stk.isEmpty() || current != null) {
            if (current != null) {
                stk.push(current);
                current = current.left;
            } else {
                current = stk.pop();
                System.out.println(current.value);
                current = current.right;
            }
        }
    }

    public static void pos(TreeNode head) {
        if (head != null) {
            Stack<TreeNode> stk = new Stack<>();
            stk.push(head);
            while (!stk.isEmpty()) {
                TreeNode current = stk.pop();
                if (current.right != null) {
                    stk.push(current.right);
                } else if (current.left != null) {
                    stk.push(current.left);
                } else {
                    System.out.println(current.value);
                }
            }
        }
    }

    public static void pos1(TreeNode head) {
        if (head != null) {
            Stack<TreeNode> s1 = new Stack<>();
            Stack<TreeNode> s2 = new Stack<>();
            s1.push(head);
            while (!s1.isEmpty()) {
                TreeNode current = s1.pop();
                s2.push(current);
                if (current.left != null) {
                    s1.push(current.left);
                }
                if (current.right != null) {
                    s1.push(current.right);
                }
            }

            while (!s2.isEmpty()) {
                System.out.println(s2.pop().value);
            }
        }
    }

    public static class TreeNode {
        public int value;
        public TreeNode right, left;

        public TreeNode(int v) {
            this.value = v;
        }
    }
}
