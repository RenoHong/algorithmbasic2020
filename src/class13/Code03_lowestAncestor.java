package class13; // Package declaration

import java.util.ArrayList; // For storing nodes in preorder traversal
import java.util.HashMap; // For mapping nodes to their parents
import java.util.HashSet; // For storing ancestors of a node

// This class finds the Lowest Common Ancestor (LCA) of two nodes in a binary tree
// LCA is the deepest node that is an ancestor of both given nodes
public class Code03_lowestAncestor {

    /**
     * Method 1: Parent pointer approach to find LCA
     * Key Concept:
     * 1. Build a parent map for all nodes
     * 2. Collect all ancestors of o1 in a set
     * 3. Traverse ancestors of o2 until finding one in o1's ancestor set
     * Time Complexity: O(N) for building map + O(H) for traversal = O(N)
     * Space Complexity: O(N) for parent map
     *
     * @param head - root of the binary tree
     * @param o1 - first target node
     * @param o2 - second target node
     * @return lowest common ancestor of o1 and o2
     */
    public static Node lowestAncestor1(Node head, Node o1, Node o2) {
        // Base case: empty tree has no LCA
        if (head == null) {
            return null;
        }
        // key的父节点是value
        // Create a map where key is a node and value is its parent
        HashMap<Node, Node> parentMap = new HashMap<>();
        // Root node has no parent (null)
        parentMap.put(head, null);
        // Recursively fill the parent map for all nodes
        fillParentMap(head, parentMap);
        // Create a set to store all ancestors of o1
        HashSet<Node> o1Set = new HashSet<>();
        // Start from o1 and traverse up to root
        Node cur = o1;
        o1Set.add(cur); // Add o1 itself as its own ancestor
        // Collect all ancestors of o1 by following parent pointers
        while (parentMap.get(cur) != null) {
            cur = parentMap.get(cur); // Move to parent
            o1Set.add(cur); // Add parent to ancestor set
        }
        // Now traverse from o2 upward until we find a node in o1's ancestor set
        cur = o2;
        // The first common ancestor we encounter is the LCA
        while (!o1Set.contains(cur)) {
            cur = parentMap.get(cur); // Move to parent
        }
        // cur is now the LCA
        return cur;
    }

    /**
     * Helper method to recursively build the parent map
     * Performs DFS to map each child to its parent node
     *
     * @param head - current node being processed
     * @param parentMap - map to store parent relationships
     */
    public static void fillParentMap(Node head, HashMap<Node, Node> parentMap) {
        // If left child exists, map it to current node and recurse
        if (head.left != null) {
            parentMap.put(head.left, head); // Left child's parent is head
            fillParentMap(head.left, parentMap); // Recurse on left subtree
        }
        // If right child exists, map it to current node and recurse
        if (head.right != null) {
            parentMap.put(head.right, head); // Right child's parent is head
            fillParentMap(head.right, parentMap); // Recurse on right subtree
        }
    }

    /**
     * Method 2: Recursive tree DP approach to find LCA
     * Key Concept: Post-order traversal collecting information from subtrees
     * - If both nodes found in different subtrees, current node is LCA
     * - If LCA already found in a subtree, propagate it up
     * Time Complexity: O(N) - visit each node once
     * Space Complexity: O(H) - recursion stack height
     *
     * @param head - root of the binary tree
     * @param a - first target node
     * @param b - second target node
     * @return lowest common ancestor of a and b
     */
    public static Node lowestAncestor2(Node head, Node a, Node b) {
        // Call recursive helper and extract answer from Info object
        return process(head, a, b).ans;
    }

    /**
     * Recursive helper that collects information about whether nodes are found
     * Returns Info containing:
     * - findA: whether node a is found in this subtree
     * - findB: whether node b is found in this subtree
     * - ans: the LCA if found, null otherwise
     *
     * Logic for determining LCA:
     * 1. If LCA already found in left or right subtree, return it
     * 2. If both a and b are found in current subtree for first time, current node is LCA
     * 3. Otherwise, propagate the find status upward
     *
     * @param x - current node being processed
     * @param a - first target node to find
     * @param b - second target node to find
     * @return Info object with find status and LCA
     */
    public static Info process(Node x, Node a, Node b) {
        // Base case: null node means neither a nor b found, no answer
        if (x == null) {
            return new Info(false, false, null);
        }
        // Recursively process left subtree
        Info leftInfo = process(x.left, a, b);
        // Recursively process right subtree
        Info rightInfo = process(x.right, a, b);
        // Check if 'a' is found: either current node is 'a', or found in left/right subtree
        boolean findA = (x == a) || leftInfo.findA || rightInfo.findA;
        // Check if 'b' is found: either current node is 'b', or found in left/right subtree
        boolean findB = (x == b) || leftInfo.findB || rightInfo.findB;
        // Initialize answer as null
        Node ans = null;
        // Priority 1: If LCA already found in left subtree, that's the answer
        if (leftInfo.ans != null) {
            ans = leftInfo.ans;
        // Priority 2: If LCA already found in right subtree, that's the answer
        } else if (rightInfo.ans != null) {
            ans = rightInfo.ans;
        // Priority 3: If both a and b found in current subtree for first time
        } else {
            // Current node is the LCA (first common ancestor)
            if (findA && findB) {
                ans = x;
            }
        }
        // Return Info with updated find status and answer
        return new Info(findA, findB, ans);
    }

    // for test
    /**
     * Generates a random binary tree for testing
     * @param maxLevel - maximum depth of tree
     * @param maxValue - maximum node value
     * @return root of generated tree
     */
    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // for test
    /**
     * Recursive helper to generate random binary tree
     * Each node has 50% probability of being null
     *
     * @param level - current depth level
     * @param maxLevel - maximum allowed depth
     * @param maxValue - maximum value for nodes
     * @return generated node or null
     */
    public static Node generate(int level, int maxLevel, int maxValue) {
        // Stop if max level exceeded or randomly decide to stop (50% chance)
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        // Create node with random value
        Node head = new Node((int) (Math.random() * maxValue));
        // Recursively generate left and right subtrees
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    // for test
    /**
     * Randomly selects one node from the tree
     * Used to pick random nodes for testing LCA
     *
     * @param head - root of the tree
     * @return randomly selected node or null
     */
    public static Node pickRandomOne(Node head) {
        // Empty tree returns null
        if (head == null) {
            return null;
        }
        // Collect all nodes in a list
        ArrayList<Node> arr = new ArrayList<>();
        fillPrelist(head, arr);
        // Pick a random index and return that node
        int randomIndex = (int) (Math.random() * arr.size());
        return arr.get(randomIndex);
    }

    // for test
    /**
     * Fills list with preorder traversal of tree nodes
     * Preorder: node -> left -> right
     *
     * @param head - current node in traversal
     * @param arr - list to collect nodes
     */
    public static void fillPrelist(Node head, ArrayList<Node> arr) {
        // Base case: null node
        if (head == null) {
            return;
        }
        arr.add(head); // Add current node first (preorder)
        fillPrelist(head.left, arr); // Traverse left subtree
        fillPrelist(head.right, arr); // Traverse right subtree
    }

    /**
     * Main method to test both LCA implementations
     * Runs 1 million random tests comparing both methods
     */
    public static void main(String[] args) {
        int maxLevel = 4; // Maximum tree depth
        int maxValue = 100; // Maximum node value
        int testTimes = 1000000; // Number of test cases
        // Run extensive testing
        for (int i = 0; i < testTimes; i++) {
            // Generate random tree
            Node head = generateRandomBST(maxLevel, maxValue);
            // Pick two random nodes from the tree
            Node o1 = pickRandomOne(head);
            Node o2 = pickRandomOne(head);
            // Compare results from both methods
            if (lowestAncestor1(head, o1, o2) != lowestAncestor2(head, o1, o2)) {
                System.out.println("Oops!"); // Report mismatch
            }
        }
        System.out.println("finish!"); // All tests passed
    }

    /**
     * Node class representing a node in the binary tree
     */
    public static class Node {
        public int value; // Node's value
        public Node left; // Left child pointer
        public Node right; // Right child pointer

        /**
         * Constructor to create node with given value
         * @param data - value for the node
         */
        public Node(int data) {
            this.value = data;
        }
    }

    /**
     * Info class to encapsulate information from subtree processing
     * Used in the recursive tree DP approach
     */
    public static class Info {
        public boolean findA; // Whether node 'a' is found in this subtree
        public boolean findB; // Whether node 'b' is found in this subtree
        public Node ans; // The LCA if found, null otherwise

        /**
         * Constructor to create Info object
         * @param fA - whether node a is found
         * @param fB - whether node b is found
         * @param an - the LCA answer
         */
        public Info(boolean fA, boolean fB, Node an) {
            findA = fA;
            findB = fB;
            ans = an;
        }
    }

}
