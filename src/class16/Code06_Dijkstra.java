/**
 * Dijkstra's Algorithm Implementation
 *
 * PURPOSE: Find shortest paths from a source node to all other nodes in a weighted directed graph
 * REQUIREMENT: All edge weights must be non-negative
 *
 * KEY ALGORITHM CONCEPTS:
 * 1. Greedy Strategy: Always select the unvisited node with minimum known distance
 * 2. Relaxation: Update neighbor distances if a shorter path is found
 * 3. Finalization: Once a node is selected, its shortest distance is guaranteed optimal
 *
 * TWO IMPLEMENTATIONS PROVIDED:
 * - dijkstra1(): Basic O(V²) version using HashMap + HashSet
 * - dijkstra2(): Optimized O((V+E)logV) version using custom min-heap
 */

package class16;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

// no negative weight
public class Code06_Dijkstra {

    /**
     * BASIC DIJKSTRA IMPLEMENTATION - O(V²) Time Complexity
     * Uses HashMap for distance tracking and HashSet for visited nodes
     *
     * @param from - source node to compute shortest paths from
     * @return HashMap mapping each reachable node to its shortest distance from source
     */
    public static HashMap<Node, Integer> dijkstra1(Node from) {
        // STEP 1: Initialize distance map with source node
        HashMap<Node, Integer> distanceMap = new HashMap<>();
        distanceMap.put(from, 0); // Base case: distance from source to itself is 0

        // STEP 2: Track nodes whose shortest distances have been finalized
        HashSet<Node> selectedNodes = new HashSet<>();

        // STEP 3: Main algorithm loop - continue until all reachable nodes processed
        Node minNode = getMinDistanceAndUnselectedNode(distanceMap, selectedNodes);
        while (minNode != null) {
            // STEP 4: Get the finalized shortest distance to current node
            int distance = distanceMap.get(minNode);

            // STEP 5: RELAXATION - Update distances to all neighbors
            for (Edge edge : minNode.edges) {
                Node toNode = edge.to;
                if (!distanceMap.containsKey(toNode)) {
                    // First time discovering this neighbor - set initial distance
                    distanceMap.put(toNode, distance + edge.weight);
                } else {
                    // RELAXATION STEP: Update if we found a shorter path
                    // Compare current known distance vs. new path through minNode
                    distanceMap.put(edge.to, Math.min(distanceMap.get(toNode), distance + edge.weight));
                }
            }

            // STEP 6: Mark current node as finalized (shortest distance guaranteed)
            selectedNodes.add(minNode);

            // STEP 7: Select next candidate node for processing
            minNode = getMinDistanceAndUnselectedNode(distanceMap, selectedNodes);
        }

        // STEP 8: Return final shortest distances from source to all reachable nodes
        return distanceMap;
    }

    /**
     * GREEDY SELECTION HELPER - Core of Dijkstra's correctness
     *
     * WHY THIS WORKS: In graphs with non-negative weights, the unfinalized node
     * with minimum tentative distance is guaranteed to have found its optimal path.
     * This is because any other path to it would have to go through an unfinalized
     * node with higher distance, making the total path longer.
     *
     * @param distanceMap - current best distances to discovered nodes
     * @param touchedNodes - nodes whose optimal distances are already finalized
     * @return unfinalized node with minimum distance, or null if all nodes finalized
     *
     * TIME COMPLEXITY: O(V) per call, called V times = O(V²) total
     * SPACE COMPLEXITY: O(1) additional space
     */
    public static Node getMinDistanceAndUnselectedNode(HashMap<Node, Integer> distanceMap, HashSet<Node> touchedNodes) {
        Node minNode = null;                    // Track node with minimum distance
        int minDistance = Integer.MAX_VALUE;    // Track the minimum distance found

        // LINEAR SCAN: Examine all discovered nodes to find minimum
        for (Entry<Node, Integer> entry : distanceMap.entrySet()) {
            Node node = entry.getKey();         // Current node being examined
            int distance = entry.getValue();    // Current best distance to this node

            // SELECTION CRITERIA: Node must be unfinalized AND have smaller distance
            if (!touchedNodes.contains(node) && distance < minDistance) {
                minNode = node;                 // Update candidate for selection
                minDistance = distance;         // Update minimum distance threshold
            }
        }

        // Return selected node (null indicates all discovered nodes are finalized)
        return minNode;
    }

    /**
     * OPTIMIZED DIJKSTRA WITH CUSTOM HEAP - O((V+E)logV) Time Complexity
     * Uses specialized min-heap that supports decrease-key operations
     *
     * @param head - source node for shortest path computation
     * @param size - expected maximum number of nodes (for heap initialization)
     * @return HashMap with shortest distances from head to all reachable nodes
     */
    public static HashMap<Node, Integer> dijkstra2(Node head, int size) {
        // STEP 1: Initialize custom heap data structure
        NodeHeap nodeHeap = new NodeHeap(size);

        // STEP 2: Add source node with distance 0 to heap
        nodeHeap.addOrUpdateOrIgnore(head, 0);

        // STEP 3: Initialize result container
        HashMap<Node, Integer> result = new HashMap<>();

        // STEP 4: Main processing loop - extract minimum until heap empty
        while (!nodeHeap.isEmpty()) {
            // STEP 5: Extract node with minimum distance (greedy selection)
            NodeRecord record = nodeHeap.pop();
            Node cur = record.node;           // Current node being processed
            int distance = record.distance;   // Its finalized shortest distance

            // STEP 6: RELAXATION - Update all neighbors in heap
            for (Edge edge : cur.edges) {
                // Attempt to improve distance to neighbor through current node
                nodeHeap.addOrUpdateOrIgnore(edge.to, edge.weight + distance);
            }

            // STEP 7: Record finalized shortest distance
            result.put(cur, distance);
        }

        // STEP 8: Return complete shortest distance mapping
        return result;
    }

    /**
     * DATA CONTAINER - Pairs a node with its current shortest distance
     * Used by heap to return extraction results
     */
    public static class NodeRecord {
        public Node node;      // The graph node
        public int distance;   // Current shortest known distance to this node

        public NodeRecord(Node node, int distance) {
            this.node = node;
            this.distance = distance;
        }
    }

    /**
     * CUSTOM MIN-HEAP FOR DIJKSTRA OPTIMIZATION
     *
     * PURPOSE: Efficiently maintain nodes ordered by shortest distance
     * KEY OPERATIONS:
     * - Extract minimum distance node: O(logV)
     * - Update node distance (decrease-key): O(logV)
     * - Check if node is in heap: O(1)
     *
     * HEAP INVARIANT: Parent distance ≤ children distances
     */
    public static class NodeHeap {
        private Node[] nodes;                           // Heap array storing nodes
        private HashMap<Node, Integer> heapIndexMap;    // Maps node to its heap index
        private HashMap<Node, Integer> distanceMap;     // Maps node to current distance
        private int size;                               // Current number of nodes in heap

        /**
         * HEAP CONSTRUCTOR
         * @param size - maximum expected heap capacity
         */
        public NodeHeap(int size) {
            nodes = new Node[size];             // Initialize heap array
            heapIndexMap = new HashMap<>();     // Track node positions in heap
            distanceMap = new HashMap<>();      // Track node distances
            this.size = 0;                      // Heap starts empty
        }

        /**
         * HEAP STATUS CHECK
         * @return true if heap contains no nodes
         */
        public boolean isEmpty() {
            return size == 0;
        }

        /**
         * CORE HEAP OPERATION - Add new node or update existing distance
         *
         * THREE CASES HANDLED:
         * 1. Node in heap: Update distance if smaller (decrease-key)
         * 2. Node never seen: Add to heap with given distance
         * 3. Node already processed: Ignore (distance already finalized)
         *
         * @param node - target node
         * @param distance - new distance to consider
         */
        public void addOrUpdateOrIgnore(Node node, int distance) {
            if (inHeap(node)) {
                // CASE 1: Node currently in heap - potentially update distance
                // Only update if new distance is smaller (relaxation principle)
                distanceMap.put(node, Math.min(distanceMap.get(node), distance));
                // Restore heap property after potential distance decrease
                insertHeapify(heapIndexMap.get(node));
            }
            if (!isEntered(node)) {
                // CASE 2: First encounter with this node - add to heap
                nodes[size] = node;                    // Place at end of heap
                heapIndexMap.put(node, size);          // Record its position
                distanceMap.put(node, distance);       // Record its distance
                insertHeapify(size++);                 // Bubble up and increment size
            }
            // CASE 3: Node previously processed (removed from heap) - ignore
        }

        /**
         * EXTRACT MINIMUM - Remove and return node with smallest distance
         *
         * ALGORITHM:
         * 1. Save root (minimum) node and distance
         * 2. Move last node to root position
         * 3. Mark extracted node as removed from heap
         * 4. Restore heap property by bubbling down
         *
         * @return NodeRecord containing extracted node and its distance
         */
        public NodeRecord pop() {
            // STEP 1: Create result record from heap root (minimum element)
            NodeRecord nodeRecord = new NodeRecord(nodes[0], distanceMap.get(nodes[0]));

            // STEP 2: Move last element to root position
            swap(0, size - 1);

            // STEP 3: Mark extracted node as no longer in heap
            heapIndexMap.put(nodes[size - 1], -1);  // -1 indicates "processed"
            distanceMap.remove(nodes[size - 1]);    // Remove distance tracking
            nodes[size - 1] = null;                 // Clear reference

            // STEP 4: Restore heap property and decrease size
            heapify(0, --size);

            return nodeRecord;
        }

        /**
         * BUBBLE UP OPERATION - Restore heap property after insertion/update
         *
         * WHEN USED: After adding new node or decreasing existing node's distance
         * MECHANISM: Compare with parent, swap if smaller, repeat until heap property restored
         *
         * @param index - starting position for bubble up operation
         */
        private void insertHeapify(int index) {
            // Continue while current node has smaller distance than its parent
            while (distanceMap.get(nodes[index]) < distanceMap.get(nodes[(index - 1) / 2])) {
                swap(index, (index - 1) / 2);    // Swap with parent
                index = (index - 1) / 2;         // Move up to parent's position
            }
        }

        /**
         * BUBBLE DOWN OPERATION - Restore heap property after extraction
         *
         * WHEN USED: After removing minimum element from heap root
         * MECHANISM: Compare with children, swap with smaller child, repeat downward
         *
         * @param index - starting position for bubble down
         * @param size - current heap size boundary
         */
        private void heapify(int index, int size) {
            int left = index * 2 + 1;    // Left child index

            while (left < size) {        // While current node has at least one child
                // STEP 1: Find smaller child
                int smallest = left + 1 < size && distanceMap.get(nodes[left + 1]) < distanceMap.get(nodes[left])
                        ? left + 1    // Right child is smaller
                        : left;       // Left child is smaller (or only child)

                // STEP 2: Compare smallest child with current node
                smallest = distanceMap.get(nodes[smallest]) < distanceMap.get(nodes[index])
                        ? smallest    // Child is smaller than parent
                        : index;      // Parent is smaller than both children

                // STEP 3: If heap property satisfied, stop
                if (smallest == index) {
                    break;
                }

                // STEP 4: Swap and continue down
                swap(smallest, index);
                index = smallest;
                left = index * 2 + 1;
            }
        }

        /**
         * NODE STATUS CHECK - Has this node ever been added to heap?
         * @param node - node to check
         * @return true if node has been encountered before
         */
        private boolean isEntered(Node node) {
            return heapIndexMap.containsKey(node);
        }

        /**
         * HEAP MEMBERSHIP CHECK - Is this node currently in the heap?
         * @param node - node to check
         * @return true if node is currently in heap (not yet processed)
         */
        private boolean inHeap(Node node) {
            return isEntered(node) && heapIndexMap.get(node) != -1;
        }

        /**
         * ATOMIC SWAP OPERATION - Exchange two nodes in heap and update indices
         *
         * CRITICAL: Must update both heap array AND index mapping for consistency
         *
         * @param index1 - first position to swap
         * @param index2 - second position to swap
         */
        private void swap(int index1, int index2) {
            // STEP 1: Update index mappings
            heapIndexMap.put(nodes[index1], index2);
            heapIndexMap.put(nodes[index2], index1);

            // STEP 2: Swap nodes in heap array
            Node tmp = nodes[index1];
            nodes[index1] = nodes[index2];
            nodes[index2] = tmp;
        }
    }
}
