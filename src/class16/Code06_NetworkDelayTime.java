// Network Delay Time problem (Leetcode 743): Find the time it takes for all nodes to receive a signal sent from a starting node in a weighted directed graph.
// Key ideas:
// 1. Run Dijkstra to get the shortest arrival time to every node; answer = max of these shortest times (worst-case delay).se Dijkstra's algorithm to find the shortest path to each node, then return the longest time among all nodes. path to each node, then return the longest time among all nodes.
// 2. If any node is unreachable, return -1.
// 3. Two implementations: one with a standard heap and visited array, one with a custom heap for efficiency.

package class16;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

// 课上没有讲这个题，这是我给同学们找的练习题
// leetcode 743题，可以用这道题来练习Dijkstra算法
// 测试链接 : https://leetcode.cn/problems/network-delay-time
public class Code06_NetworkDelayTime {

    // 方法一 : 普通堆 + 屏蔽已经计算过的点
    public static int networkDelayTime1(int[][] times, int n, int k) {
        /* nexts: adjacency list, nexts[i] contains pairs [neighbor, weight] for node i

           nexts is an adjacency list: each index i (1..n) holds a list of outgoing directed edges
           from node i. Each edge is stored as an int[] of length 2: [toNode, weight].

            Why use it:

            Space efficient: only O(n + m) vs. adjacency matrix O(n^2).
            Fast iteration over outgoing edges of a node (needed in Dijkstra).
            Easy to build from input edge list.
            Cache friendly: small int[] objects holding exactly what Dijkstra needs.
            Structure shape (example): Suppose n = 4 and edges:
            1 -> 2 (5)
            1 -> 3 (2)
            2 -> 4 (7)
            3 -> 2 (1)

            Then: nexts[1]: [ [2,5], [3,2] ] nexts[2]: [ [4,7] ] nexts[3]: [ [2,1] ] nexts[4]: [ ]

            ASCII view: index: 1 2 3 4 | | | | v v v v [ [2,5], [3,2] ] [ [4,7] ] [ [2,1] ] [ ]
            How Dijkstra uses it: For the current node cur, iterate for (int[] next : nexts.get(cur)):

            next[0] = neighbor
            next[1] = edge weight and relax distances (push/update heap).
            Alternatives and why not used here:

            Adjacency matrix: wastes space and slower to scan all n possibilities.
            Object wrappers (Edge class): clearer but more allocation overhead.
            Map of maps: unnecessary hashing cost.
            So this layout balances speed, memory, and simplicity for dense-enough competitive
            programming style graphs.
         */
        ArrayList<ArrayList<int[]>> nexts = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            nexts.add(new ArrayList<>());
        }
        // Build the adjacency list from input edges
        for (int[] delay : times) {
            nexts.get(delay[0]).add(new int[]{delay[1], delay[2]});
        }
        // Min-heap: stores [node, current delay], sorted by delay
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        heap.add(new int[]{k, 0}); // Start from node k with delay 0
        boolean[] used = new boolean[n + 1]; // Track visited nodes
        int num = 0; // Number of nodes reached
        int max = 0; // Maximum delay among all reached nodes
        // While there are nodes to process and not all nodes reached
        while (!heap.isEmpty() && num < n) {
            int[] record = heap.poll(); // Get node with smallest delay
            int cur = record[0];
            int delay = record[1];
            if (used[cur]) {
                continue; // Skip if already visited
            }
            used[cur] = true; // Mark as visited
            num++;
            max = Math.max(max, delay); // Update maximum delay
            // For each neighbor, if not visited, add to heap with updated delay
            for (int[] next : nexts.get(cur)) {
                if (!used[next[0]]) {
                    heap.add(new int[]{next[0], delay + next[1]});
                }
            }
        }
        // If not all nodes reached, return -1; otherwise, return max delay
        return num < n ? -1 : max;
    }

    // 方法二 : 加强堆的解法
    public static int networkDelayTime2(int[][] times, int n, int k) {
        // Build adjacency list
        ArrayList<ArrayList<int[]>> nexts = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            nexts.add(new ArrayList<>());
        }
        for (int[] delay : times) {
            nexts.get(delay[0]).add(new int[]{delay[1], delay[2]});
        }
        // Custom heap for efficient min extraction and updates
        Heap heap = new Heap(n);
        heap.add(k, 0); // Start from node k with delay 0
        int num = 0; // Number of nodes reached
        int max = 0; // Maximum delay among all reached nodes
        // While there are nodes to process
        while (!heap.isEmpty()) {
            int[] record = heap.poll(); // Get node with smallest delay
            int cur = record[0];
            int delay = record[1];
            num++;
            max = Math.max(max, delay); // Update maximum delay
            // For each neighbor, add/update in heap with updated delay
            for (int[] next : nexts.get(cur)) {
                heap.add(next[0], delay + next[1]);
            }
        }
        // If not all nodes reached, return -1; otherwise, return max delay
        return num < n ? -1 : max;
    }

    // 加强堆
    public static class Heap {
        public boolean[] used; // Track visited nodes
        public int[][] heap; // Heap array storing [node, delay]
        public int[] hIndex; // Maps node to its index in the heap
        public int size; // Number of nodes in the heap

        public Heap(int n) {
            used = new boolean[n + 1];
            heap = new int[n + 1][2];
            hIndex = new int[n + 1];
            Arrays.fill(hIndex, -1); // -1 means not in heap
            size = 0;
        }

        // Add a node with a delay, or update its delay if smaller, or ignore if already finalized
        public void add(int cur, int delay) {
            if (used[cur]) {
                return; // Ignore if already visited
            }
            if (hIndex[cur] == -1) {
                // If node not in heap, add it
                heap[size][0] = cur;
                heap[size][1] = delay;
                hIndex[cur] = size;
                heapInsert(size++);
            } else {
                // If node in heap, update its delay if smaller
                int hi = hIndex[cur];
                if (delay <= heap[hi][1]) {
                    heap[hi][1] = delay;
                    heapInsert(hi);
                }
            }
        }

        // Pop the node with the smallest delay from the heap
        public int[] poll() {
            int[] ans = heap[0];
            swap(0, --size);
            heapify(0);
            used[ans[0]] = true; // Mark as visited
            hIndex[ans[0]] = -1; // Remove from heap index
            return ans;
        }

        // Check if heap is empty
        public boolean isEmpty() {
            return size == 0;
        }

        // Heapify up: maintain heap property after insertion or update
        private void heapInsert(int i) {
            int parent = (i - 1) / 2;
            while (heap[i][1] < heap[parent][1]) {
                swap(i, parent);
                i = parent;
                parent = (i - 1) / 2;
            }
        }

        // Heapify down: maintain heap property after removal
        private void heapify(int i) {
            int l = (i * 2) + 1;
            while (l < size) {
                int smallest = l + 1 < size && heap[l + 1][1] < heap[l][1] ? (l + 1) : l;
                smallest = heap[smallest][1] < heap[i][1] ? smallest : i;
                if (smallest == i) {
                    break;
                }
                swap(smallest, i);
                i = smallest;
                l = (i * 2) + 1;
            }
        }

        // Swap two nodes in the heap and update their indices
        private void swap(int i, int j) {
            int[] o1 = heap[i];
            int[] o2 = heap[j];
            int o1hi = hIndex[o1[0]];
            int o2hi = hIndex[o2[0]];
            heap[i] = o2;
            heap[j] = o1;
            hIndex[o1[0]] = o2hi;
            hIndex[o2[0]] = o1hi;
        }

    }

}
