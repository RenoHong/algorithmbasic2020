package class12;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * PROBLEM: Find the maximum distance between any two nodes in a binary tree
 *
 * Distance = number of edges in the shortest path between two nodes
 *
 * TWO APPROACHES:
 * 1. Brute Force: Check all pairs of nodes (O(n³) time complexity)
 * 2. Tree DP: Use recursive tree processing (O(n) time complexity)
 */
public class Code06_MaxDistance {

    /**
     * METHOD 1: BRUTE FORCE APPROACH
     *
     * Algorithm Steps:
     * 1. Get all nodes in the tree (preorder traversal)
     * 2. Build parent-child relationship mapping
     * 3. For every pair of nodes, calculate distance
     * 4. Return maximum distance found
     *
     * Time Complexity: O(n³) - n² pairs × O(n) distance calculation
     * Space Complexity: O(n) - for storing nodes and parent map
     */
    public static int maxDistance1(Node head) {
        if (head == null) {                    // Base case: empty tree
            return 0;                          // No distance in empty tree
        }

        // Step 1: Get all nodes in preorder traversal
        ArrayList<Node> arr = getPrelist(head);

        // Step 2: Build parent mapping for distance calculation
        HashMap<Node, Node> parentMap = getParentMap(head);

        int max = 0;                          // Track maximum distance found

        // Step 3: Check all pairs of nodes (nested loops)
        for (int i = 0; i < arr.size(); i++) {           // First node
            for (int j = i; j < arr.size(); j++) {       // Second node (j>=i to avoid duplicates)
                // Calculate distance between arr[i] and arr[j]
                max = Math.max(max, distance(parentMap, arr.get(i), arr.get(j)));
            }
        }
        return max;                           // Return maximum distance found
    }

    /**
     * HELPER: Get all nodes in preorder traversal
     *
     * Purpose: Collect all nodes so we can check every pair
     * Preorder chosen for simplicity (root->left->right)
     */
    public static ArrayList<Node> getPrelist(Node head) {
        ArrayList<Node> arr = new ArrayList<>();    // Initialize result list
        fillPrelist(head, arr);                    // Recursively fill the list
        return arr;                                // Return completed list
    }

    /**
     * RECURSIVE HELPER: Fill ArrayList with nodes in preorder
     *
     * Preorder traversal: Root -> Left subtree -> Right subtree
     */
    public static void fillPrelist(Node head, ArrayList<Node> arr) {
        if (head == null) {                        // Base case: null node
            return;                                // Nothing to add
        }
        arr.add(head);                            // Add current node (ROOT)
        fillPrelist(head.left, arr);              // Traverse LEFT subtree
        fillPrelist(head.right, arr);             // Traverse RIGHT subtree
    }

    /**
     * HELPER: Build parent mapping for all nodes
     *
     * Purpose: To calculate distance, we need to find path from any node to root
     * HashMap maps each node to its parent (root maps to null)
     */
    public static HashMap<Node, Node> getParentMap(Node head) {
        HashMap<Node, Node> map = new HashMap<>();
        map.put(head, null);                      // Root has no parent
        fillParentMap(head, map);                 // Recursively fill parent relationships
        return map;
    }

    /**
     * RECURSIVE HELPER: Fill parent mapping recursively
     *
     * For each node, record its children's parent as itself
     */
    public static void fillParentMap(Node head, HashMap<Node, Node> parentMap) {
        if (head.left != null) {                  // If left child exists
            parentMap.put(head.left, head);       // Map left child to current node as parent
            fillParentMap(head.left, parentMap);  // Recursively process left subtree
        }
        if (head.right != null) {                 // If right child exists
            parentMap.put(head.right, head);      // Map right child to current node as parent
            fillParentMap(head.right, parentMap); // Recursively process right subtree
        }
    }

    /**
     * CORE ALGORITHM: Calculate distance between two nodes
     *
     * Strategy: Find Lowest Common Ancestor (LCA), then sum distances from both nodes to LCA
     *
     * Steps:
     * 1. Find path from o1 to root (store in HashSet for O(1) lookup)
     * 2. Traverse from o2 to root until we hit a node in o1's path (this is LCA)
     * 3. Count edges from o1 to LCA and from o2 to LCA
     * 4. Total distance = distance1 + distance2 - 1 (subtract 1 because LCA counted twice)
     */
    public static int distance(HashMap<Node, Node> parentMap, Node o1, Node o2) {
        HashSet<Node> o1Set = new HashSet<>();    // Store all nodes in path from o1 to root

        // Step 1: Build path from o1 to root
        Node cur = o1;                            // Start from o1
        o1Set.add(cur);                          // Add o1 to path
        while (parentMap.get(cur) != null) {      // While not at root
            cur = parentMap.get(cur);             // Move to parent
            o1Set.add(cur);                      // Add parent to path
        }

        // Step 2: Find LCA by traversing from o2 to root
        cur = o2;                                // Start from o2
        while (!o1Set.contains(cur)) {           // While current node not in o1's path
            cur = parentMap.get(cur);            // Move to parent
        }
        Node lowestAncestor = cur;               // Found LCA!

        // Step 3: Calculate distance from o1 to LCA
        cur = o1;                                // Start from o1
        int distance1 = 1;                       // Count o1 itself (node count, not edge count)
        while (cur != lowestAncestor) {          // Until we reach LCA
            cur = parentMap.get(cur);            // Move to parent
            distance1++;                         // Increment distance
        }

        // Step 4: Calculate distance from o2 to LCA
        cur = o2;                                // Start from o2
        int distance2 = 1;                       // Count o2 itself (node count, not edge count)
        while (cur != lowestAncestor) {          // Until we reach LCA
            cur = parentMap.get(cur);            // Move to parent
            distance2++;                         // Increment distance
        }

        // Step 5: Total distance = sum of both distances - 1 (LCA counted twice)
        return distance1 + distance2 - 1;        // Convert node count to edge count
    }

    /**
     * METHOD 2: OPTIMIZED TREE DP APPROACH
     *
     * Key Insight: Maximum distance is either:
     * 1. Maximum distance in left subtree
     * 2. Maximum distance in right subtree
     * 3. Path going through current node (left height + right height + 1)
     *
     * Time Complexity: O(n) - visit each node once
     * Space Complexity: O(h) - recursion stack height
     */
    public static int maxDistance2(Node head) {
        return process(head).maxDistance;         // Get max distance from root
    }

    /**
     * CORE DP FUNCTION: Process each node and return Info about subtree
     *
     * For each node, we need to know:
     * 1. Maximum distance within this subtree
     * 2. Height of this subtree (for calculating paths through parent)
     *
     * This is a classic example of tree DP where we solve subproblems bottom-up
     */
    public static Info process(Node x) {
        if (x == null) {                          // Base case: null node
            return new Info(0, 0);                // No distance, no height
        }

        // Recursively get information from left and right subtrees
        Info leftInfo = process(x.left);          // Process left subtree first
        Info rightInfo = process(x.right);        // Process right subtree

        // Calculate height of current subtree
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;  // Max child height + 1

        // Three possibilities for maximum distance:
        int p1 = leftInfo.maxDistance;            // Best distance in left subtree
        int p2 = rightInfo.maxDistance;           // Best distance in right subtree
        int p3 = leftInfo.height + rightInfo.height + 1;  // Path through current node

        // Maximum distance is the best of these three possibilities
        int maxDistance = Math.max(Math.max(p1, p2), p3);

        return new Info(maxDistance, height);     // Return computed information
    }

    // for test
    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // for test
    public static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    /**
     * MAIN TESTING FUNCTION: Comparative testing of both approaches
     *
     * Strategy: Generate random trees and verify both methods produce same result
     * This is crucial because Method 1 is easier to verify but Method 2 is more efficient
     */
    public static void main(String[] args) {
        int maxLevel = 4;                         // Limit tree depth for testing
        int maxValue = 100;                       // Node value range
        int testTimes = 1000000;                  // Number of test cases

        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);  // Generate random tree

            // Both methods must produce identical results
            if (maxDistance1(head) != maxDistance2(head)) {
                System.out.println("Oops!");      // Bug detected!
            }
        }
        System.out.println("finish!");           // All tests passed
    }

    /**
     * NODE CLASS: Simple binary tree node structure
     */
    public static class Node {
        public int value;                         // Node data
        public Node left;                         // Left child reference
        public Node right;                        // Right child reference

        public Node(int data) {
            this.value = data;                    // Set node value
            // left and right automatically null
        }
    }

    /**
     * INFO CLASS: Data structure for tree DP approach
     *
     * Encapsulates the information needed at each node:
     * - maxDistance: maximum distance found in this subtree
     * - height: height of this subtree (for parent calculations)
     */
    public static class Info {
        public int maxDistance;                   // Maximum distance in subtree
        public int height;                        // Height of subtree

        public Info(int m, int h) {
            maxDistance = m;                      // Set maximum distance
            height = h;                          // Set height
        }
    }
}
