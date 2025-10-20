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

package class16; // Package declaration - organizes code into namespace

import java.util.HashMap; // Import HashMap - stores key-value pairs with O(1) average lookup
import java.util.HashSet; // Import HashSet - stores unique elements with O(1) average lookup
import java.util.Map.Entry; // Import Entry - represents key-value pair in map for iteration

// no negative weight
public class Code06_Dijkstra { // Class declaration - contains Dijkstra algorithm implementations

    /**
     * BASIC DIJKSTRA IMPLEMENTATION - O(V²) Time Complexity
     * Uses HashMap for distance tracking and HashSet for visited nodes
     *
     * @param from - source node to compute shortest paths from
     * @return HashMap mapping each reachable node to its shortest distance from source
     */
    public static HashMap<Node, Integer> dijkstra1(Node from) { // Method signature - takes source node, returns distance map
        // STEP 1: Initialize distance map with source node
        HashMap<Node, Integer> distanceMap = new HashMap<>(); // Create new HashMap to store shortest distances
        distanceMap.put(from, 0); // Base case: distance from source to itself is 0
        // Explanation: This is the starting point - we know the distance to source is always 0

        // STEP 2: Track nodes whose shortest distances have been finalized
        HashSet<Node> selectedNodes = new HashSet<>(); // Create set to track processed (locked) nodes
        // Explanation: Once a node is in this set, we've found its optimal shortest path

        // STEP 3: Main algorithm loop - continue until all reachable nodes processed
        Node minNode = getMinDistanceAndUnselectedNode(distanceMap, selectedNodes); // Get unprocessed node with minimum distance
        // Explanation: This greedy selection ensures we always process the closest unprocessed node

        while (minNode != null) { // Loop continues while there are unprocessed reachable nodes
            // Explanation: null means all discovered nodes have been processed

            // STEP 4: Get the finalized shortest distance to current node
            int distance = distanceMap.get(minNode); // Retrieve the shortest distance to current node
            // Explanation: This distance is now guaranteed to be optimal (proven by induction)

            // STEP 5: RELAXATION - Update distances to all neighbors
            for (Edge edge : minNode.edges) { // Iterate through all outgoing edges from current node
                // Explanation: We check if going through current node gives shorter paths to neighbors

                Node toNode = edge.to; // Get the destination node of this edge
                // Explanation: This is the neighbor we're trying to improve the path to

                if (!distanceMap.containsKey(toNode)) { // Check if this neighbor has never been discovered
                    // First time discovering this neighbor - set initial distance
                    distanceMap.put(toNode, distance + edge.weight); // Add new node with distance = current distance + edge weight
                    // Explanation: First path found to this node becomes its initial best distance
                } else { // Neighbor was already discovered before
                    // RELAXATION STEP: Update if we found a shorter path
                    // Compare current known distance vs. new path through minNode
                    distanceMap.put(edge.to, Math.min(distanceMap.get(toNode), distance + edge.weight)); // Keep the minimum of old distance and new path distance
                    // Explanation: If new path through current node is shorter, update; otherwise keep old distance
                }
            }

            // STEP 6: Mark current node as finalized (shortest distance guaranteed)
            selectedNodes.add(minNode); // Add current node to the set of processed nodes
            // Explanation: This node's shortest distance is now locked and won't change

            // STEP 7: Select next candidate node for processing
            minNode = getMinDistanceAndUnselectedNode(distanceMap, selectedNodes); // Get next unprocessed node with minimum distance
            // Explanation: Repeat greedy selection for next iteration
        }

        // STEP 8: Return final shortest distances from source to all reachable nodes
        return distanceMap; // Return the complete map of shortest distances
        // Explanation: Unreachable nodes won't appear in this map
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
    public static Node getMinDistanceAndUnselectedNode(HashMap<Node, Integer> distanceMap, HashSet<Node> touchedNodes) { // Method to find unprocessed node with minimum distance
        Node minNode = null; // Track node with minimum distance
        // Explanation: Initialize to null - will remain null if all nodes are processed

        int minDistance = Integer.MAX_VALUE; // Track the minimum distance found
        // Explanation: Start with maximum possible value so any real distance will be smaller

        // LINEAR SCAN: Examine all discovered nodes to find minimum
        for (Entry<Node, Integer> entry : distanceMap.entrySet()) { // Iterate through all entries in distance map
            // Explanation: Check every discovered node to find the minimum unprocessed one

            Node node = entry.getKey(); // Current node being examined
            // Explanation: Extract the node from the map entry

            int distance = entry.getValue(); // Current best distance to this node
            // Explanation: Extract the distance value from the map entry

            // SELECTION CRITERIA: Node must be unfinalized AND have smaller distance
            if (!touchedNodes.contains(node) && distance < minDistance) { // Check if node is unprocessed AND has smaller distance than current minimum
                // Explanation: We want unprocessed node (not in touchedNodes) with minimum distance

                minNode = node; // Update candidate for selection
                // Explanation: This node becomes our new best candidate

                minDistance = distance; // Update minimum distance threshold
                // Explanation: Update the bar that future candidates must beat
            }
        }

        // Return selected node (null indicates all discovered nodes are finalized)
        return minNode; // Return the node with minimum distance, or null if none found
        // Explanation: null means all discovered nodes have been processed
    }

    /**
     * OPTIMIZED DIJKSTRA WITH CUSTOM HEAP - O((V+E)logV) Time Complexity
     * Uses specialized min-heap that supports decrease-key operations
     *
     * @param head - source node for shortest path computation
     * @param size - expected maximum number of nodes (for heap initialization)
     * @return HashMap with shortest distances from head to all reachable nodes
     */
    public static HashMap<Node, Integer> dijkstra2(Node head, int size) { // Optimized version using custom heap
        // STEP 1: Initialize custom heap data structure
        NodeHeap nodeHeap = new NodeHeap(size); // Create heap with expected capacity
        // Explanation: Custom heap provides O(logV) extraction and update operations

        // STEP 2: Add source node with distance 0 to heap
        nodeHeap.addOrUpdateOrIgnore(head, 0); // Insert source node with distance 0
        // Explanation: Starting point - distance to source is always 0

        // STEP 3: Initialize result container
        HashMap<Node, Integer> result = new HashMap<>(); // Create map to store final results
        // Explanation: Will store shortest distances for all processed nodes

        // STEP 4: Main processing loop - extract minimum until heap empty
        while (!nodeHeap.isEmpty()) { // Continue while heap has nodes to process
            // Explanation: Heap becomes empty when all reachable nodes are processed

            // STEP 5: Extract node with minimum distance (greedy selection)
            NodeRecord record = nodeHeap.pop(); // Remove and return node with minimum distance from heap
            // Explanation: Heap.pop() gives us the closest unprocessed node in O(logV) time

            Node cur = record.node; // Current node being processed
            // Explanation: Extract node from the record

            int distance = record.distance; // Its finalized shortest distance
            // Explanation: Extract the distance - this is now optimal

            // STEP 6: RELAXATION - Update all neighbors in heap
            for (Edge edge : cur.edges) { // Iterate through all outgoing edges
                // Explanation: Try to improve paths to all neighbors through current node

                // Attempt to improve distance to neighbor through current node
                nodeHeap.addOrUpdateOrIgnore(edge.to, edge.weight + distance); // Add neighbor or update its distance if shorter path found
                // Explanation: Heap handles three cases: new node, update existing, or ignore if already processed
            }

            // STEP 7: Record finalized shortest distance
            result.put(cur, distance); // Store the final shortest distance for current node
            // Explanation: Once popped from heap, this distance is guaranteed optimal
        }

        // STEP 8: Return complete shortest distance mapping
        return result; // Return map of all shortest distances
        // Explanation: Contains optimal distances for all reachable nodes from source
    }

    /**
     * DATA CONTAINER - Pairs a node with its current shortest distance
     * Used by heap to return extraction results
     */
    public static class NodeRecord { // Inner class to hold node-distance pair
        public Node node; // The graph node
        // Explanation: Reference to the actual graph node

        public int distance; // Current shortest known distance to this node
        // Explanation: The distance value associated with this node

        public NodeRecord(Node node, int distance) { // Constructor to create node-distance pair
            this.node = node; // Initialize node field
            this.distance = distance; // Initialize distance field
            // Explanation: Simple data container - no complex logic needed
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
    public static class NodeHeap { // Custom heap implementation for efficient Dijkstra
        private Node[] nodes; // Heap array storing nodes
        // Explanation: Array-based binary heap - index i has children at 2i+1 and 2i+2

        private HashMap<Node, Integer> heapIndexMap; // Maps node to its heap index
        // Explanation: Allows O(1) lookup of node's position in heap for updates

        private HashMap<Node, Integer> distanceMap; // Maps node to current distance
        // Explanation: Stores current shortest known distance for each node

        private int size; // Current number of nodes in heap
        // Explanation: Tracks how many elements are currently in the heap

        /**
         * HEAP CONSTRUCTOR
         * @param size - maximum expected heap capacity
         */
        public NodeHeap(int size) { // Constructor initializes heap with given capacity
            nodes = new Node[size]; // Initialize heap array
            // Explanation: Pre-allocate array to avoid resizing overhead

            heapIndexMap = new HashMap<>(); // Track node positions in heap
            // Explanation: Essential for O(1) position lookup during updates

            distanceMap = new HashMap<>(); // Track node distances
            // Explanation: Stores the distance values that determine heap ordering

            this.size = 0; // Heap starts empty
            // Explanation: No nodes initially - will grow as nodes are added
        }

        /**
         * HEAP STATUS CHECK
         * @return true if heap contains no nodes
         */
        public boolean isEmpty() { // Check if heap is empty
            return size == 0; // Heap is empty when size is 0
            // Explanation: Simple check - size 0 means no nodes to process
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
        public void addOrUpdateOrIgnore(Node node, int distance) { // Handles all three cases for node operations
            if (inHeap(node)) { // CASE 1: Node currently in heap - potentially update distance
                // Explanation: Node is in heap but not yet processed - may need distance update

                // Only update if new distance is smaller (relaxation principle)
                distanceMap.put(node, Math.min(distanceMap.get(node), distance)); // Keep minimum of current and new distance
                // Explanation: Relaxation - only improve distance if new path is shorter

                // Restore heap property after potential distance decrease
                insertHeapify(heapIndexMap.get(node)); // Bubble up if distance decreased
                // Explanation: Smaller distance may violate heap property, need to bubble up
            }
            if (!isEntered(node)) { // CASE 2: First encounter with this node - add to heap
                // Explanation: This node has never been seen before - add it fresh

                nodes[size] = node; // Place at end of heap
                // Explanation: New element goes at end of array (next available position)

                heapIndexMap.put(node, size); // Record its position
                // Explanation: Update index map so we can find this node later in O(1)

                distanceMap.put(node, distance); // Record its distance
                // Explanation: Store the distance that will be used for heap ordering

                insertHeapify(size++); // Bubble up and increment size
                // Explanation: Restore heap property by bubbling up, then increment size for next insertion
            }
            // CASE 3: Node previously processed (removed from heap) - ignore
            // Explanation: If node was already popped, its distance is finalized - do nothing
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
        public NodeRecord pop() { // Extract and return minimum distance node
            // STEP 1: Create result record from heap root (minimum element)
            NodeRecord nodeRecord = new NodeRecord(nodes[0], distanceMap.get(nodes[0])); // Save root node and its distance
            // Explanation: Root of min-heap always contains the minimum distance element

            // STEP 2: Move last element to root position
            swap(0, size - 1); // Swap root with last element
            // Explanation: Standard heap deletion - replace root with last element

            // STEP 3: Mark extracted node as no longer in heap
            heapIndexMap.put(nodes[size - 1], -1); // -1 indicates "processed"
            // Explanation: Special value -1 marks node as removed from heap (finalized)

            distanceMap.remove(nodes[size - 1]); // Remove distance tracking
            // Explanation: No longer need distance for processed node

            nodes[size - 1] = null; // Clear reference
            // Explanation: Help garbage collector by removing reference

            // STEP 4: Restore heap property and decrease size
            heapify(0, --size); // Bubble down from root and decrement size
            // Explanation: New root (former last element) needs to bubble down to restore heap property

            return nodeRecord; // Return the extracted minimum node with its distance
            // Explanation: This node's shortest distance is now finalized
        }

        /**
         * BUBBLE UP OPERATION - Restore heap property after insertion/update
         *
         * WHEN USED: After adding new node or decreasing existing node's distance
         * MECHANISM: Compare with parent, swap if smaller, repeat until heap property restored
         *
         * @param index - starting position for bubble up operation
         */
        private void insertHeapify(int index) { // Bubble up to restore heap property
            // Continue while current node has smaller distance than its parent
            while (distanceMap.get(nodes[index]) < distanceMap.get(nodes[(index - 1) / 2])) { // Compare current node's distance with parent's distance
                // Explanation: Heap property requires parent ≤ children; if violated, must swap

                swap(index, (index - 1) / 2); // Swap with parent
                // Explanation: Move smaller distance node upward toward root

                index = (index - 1) / 2; // Move up to parent's position
                // Explanation: Update index to continue checking at parent's level
            }
            // Explanation: Loop stops when heap property is satisfied or we reach root
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
        private void heapify(int index, int size) { // Bubble down to restore heap property
            int left = index * 2 + 1; // Left child index
            // Explanation: In array-based heap, left child of index i is at 2i+1

            while (left < size) { // While current node has at least one child
                // Explanation: If left child index exceeds size, node has no children (is leaf)

                // STEP 1: Find smaller child
                int smallest = left + 1 < size && distanceMap.get(nodes[left + 1]) < distanceMap.get(nodes[left])
                        ? left + 1 // Right child is smaller
                        : left; // Left child is smaller (or only child)
                // Explanation: Need to compare with smaller child to maintain heap property

                // STEP 2: Compare smallest child with current node
                smallest = distanceMap.get(nodes[smallest]) < distanceMap.get(nodes[index])
                        ? smallest // Child is smaller than parent
                        : index; // Parent is smaller than both children
                // Explanation: If parent is smaller than both children, heap property satisfied

                // STEP 3: If heap property satisfied, stop
                if (smallest == index) { // Parent is smaller than or equal to both children
                    break; // Heap property restored - exit loop
                    // Explanation: No more swaps needed - heap is valid
                }

                // STEP 4: Swap and continue down
                swap(smallest, index); // Swap parent with smaller child
                // Explanation: Move larger distance node downward

                index = smallest; // Update current position
                // Explanation: Continue from child's position

                left = index * 2 + 1; // Calculate new left child
                // Explanation: Update for next iteration to check children at new level
            }
            // Explanation: Loop exits when node has no children or heap property is restored
        }

        /**
         * NODE STATUS CHECK - Has this node ever been added to heap?
         * @param node - node to check
         * @return true if node has been encountered before
         */
        private boolean isEntered(Node node) { // Check if node was ever added to heap
            return heapIndexMap.containsKey(node); // Check if node exists in index map
            // Explanation: If node is in index map, it was added at some point (current or past)
        }

        /**
         * HEAP MEMBERSHIP CHECK - Is this node currently in the heap?
         * @param node - node to check
         * @return true if node is currently in heap (not yet processed)
         */
        private boolean inHeap(Node node) { // Check if node is currently in heap (not processed)
            return isEntered(node) && heapIndexMap.get(node) != -1; // Node exists AND index is not -1
            // Explanation: -1 index means node was processed and removed; any other value means still in heap
        }

        /**
         * ATOMIC SWAP OPERATION - Exchange two nodes in heap and update indices
         *
         * CRITICAL: Must update both heap array AND index mapping for consistency
         *
         * @param index1 - first position to swap
         * @param index2 - second position to swap
         */
        private void swap(int index1, int index2) { // Swap two nodes in heap
            // STEP 1: Update index mappings
            heapIndexMap.put(nodes[index1], index2); // Update first node's index to second position
            // Explanation: Node at index1 will be at index2 after swap

            heapIndexMap.put(nodes[index2], index1); // Update second node's index to first position
            // Explanation: Node at index2 will be at index1 after swap

            // STEP 2: Swap nodes in heap array
            Node tmp = nodes[index1]; // Save first node temporarily
            // Explanation: Need temporary variable for swap

            nodes[index1] = nodes[index2]; // Move second node to first position
            // Explanation: Copy second node to first location

            nodes[index2] = tmp; // Move saved first node to second position
            // Explanation: Complete the swap using saved value
        }
    }
}
