package class11.practice02;

import java.util.ArrayList;
import java.util.List;

/**
 * Leetcode 431: Encode N-ary Tree to Binary Tree
 * 
 * Problem: Design an algorithm to encode an N-ary tree into a binary tree and decode back.
 * 
 * Strategy: 
 * - Encode: For each node, put first child as left child, and siblings as right children
 * - This creates a "left-child right-sibling" representation
 */
public class EncodeNaryTreeToBinaryTree {
    // 提交时不要提交这个类
    public static class Node {
        public int val;
        public List<Node> children;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    }


    // 提交时不要提交这个类
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public static class Codec {

        /**
         * Encodes an n-ary tree to a binary tree using left-child right-sibling representation
         */
        public TreeNode encode(Node root) {
            if (root == null) {
                return null;
            }
            
            TreeNode tree = new TreeNode(root.val);
            tree.left = en(root.children);
            return tree;
        }

        /**
         * Helper method to encode a list of N-ary children into binary tree structure
         * Children are connected as right siblings in the binary tree
         */
        private TreeNode en(List<Node> children) {
            TreeNode head = null;
            TreeNode current = null;
            
            for (Node c : children) {
                TreeNode thisNode = new TreeNode(c.val);
                if (head == null) {
                    head = thisNode;
                } else {
                    current.right = thisNode;
                }
                current = thisNode;
                current.left = en(c.children);
            }
            return head;
        }

        /**
         * Decodes a binary tree back to an n-ary tree
         */
        public Node decode(TreeNode root) {
            if (root == null) {
                return null;
            }
            return new Node(root.val, de(root.left));
        }

        /**
         * Helper method to decode binary tree siblings back into N-ary children list
         */
        private List<Node> de(TreeNode head) {
            List<Node> nodes = new ArrayList<>();

            TreeNode current = head;
            while (current != null) {
                Node n = new Node(current.val, de(current.left));
                nodes.add(n);
                current = current.right;
            }
            return nodes;
        }
    }
}
