package class11;

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
 * 
 * Example:
 * N-ary Tree:        Binary Tree:
 *      1                  1
 *    / | \              /
 *   3  2  4           3
 *  / \              / \
 * 5   6           5   2
 *                  \   \
 *                   6   4
 */
public class Code03_EncodeNaryTreeToBinaryTree {

    /**
     * N-ary tree node definition (don't submit this class)
     */
    public static class Node {
        public int val;              // Node value
        public List<Node> children;  // List of all children
        
        public Node() {}
        
        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    }

    /**
     * Binary tree node definition (don't submit this class)
     */
    public static class TreeNode {
        int val;           // Node value
        TreeNode left;     // Left child pointer
        TreeNode right;    // Right child pointer

        TreeNode(int x) {
            val = x;
        }
    }

    /**
     * Main codec class for encoding/decoding (submit only this class)
     */
    class Codec {
        
        /**
         * Encodes an n-ary tree to a binary tree using left-child right-sibling representation
         * 
         * Strategy:
         * - Each node's first child becomes its left child in binary tree
         * - All other children become right siblings (connected via right pointers)
         * 
         * @param root Root of N-ary tree
         * @return Root of encoded binary tree
         */
        public TreeNode encode(Node root) {
            if (root == null) {
                return null;  // Empty tree case
            }
            
            // Create binary tree node with same value
            TreeNode head = new TreeNode(root.val);
            // Encode all children and attach as left subtree
            head.left = en(root.children);
            return head;
        }

        /**
         * Helper method to encode a list of N-ary children into binary tree structure
         * 
         * The children are connected as follows:
         * - First child becomes the leftmost node
         * - Each subsequent child becomes right sibling of previous
         * - Each child's own children are encoded recursively as left subtree
         * 
         * Example: Children [A, B, C] become:
         *   A
         *    \
         *     B  
         *      \
         *       C
         * 
         * @param children List of N-ary tree children
         * @return Root of binary subtree representing these children
         */
        private TreeNode en(List<Node> children) {
            TreeNode head = null;   // Head of the sibling chain
            TreeNode cur = null;    // Current node in the chain
            
            // Process each child in order
            for (Node child : children) {
                // Create binary node for this child
                TreeNode tNode = new TreeNode(child.val);
                
                if (head == null) {
                    head = tNode;     // First child becomes head
                } else {
                    cur.right = tNode; // Link as right sibling of previous child
                }
                cur = tNode;          // Update current pointer
                
                // Recursively encode this child's children as left subtree
                cur.left = en(child.children);
            }
            return head;
        }

        /**
         * Decodes a binary tree back to an n-ary tree
         * 
         * @param root Root of binary tree
         * @return Root of decoded N-ary tree
         */
        public Node decode(TreeNode root) {
            if (root == null) {
                return null;  // Empty tree case
            }
            
            // Create N-ary node with same value and decode children from left subtree
            return new Node(root.val, de(root.left));
        }

        /**
         * Helper method to decode binary tree siblings back into N-ary children list
         * 
         * Traverses the right-sibling chain and recursively decodes each node's left subtree
         * 
         * @param root Root of binary subtree representing siblings
         * @return List of N-ary children
         */
        public List<Node> de(TreeNode root) {
            List<Node> children = new ArrayList<>();
            
            // Traverse the right-sibling chain
            while (root != null) {
                // Create N-ary node for current sibling
                // Recursively decode its left subtree as its children
                Node cur = new Node(root.val, de(root.left));
                children.add(cur);
                
                // Move to next sibling
                root = root.right;
            }
            return children;
        }
    }
}
