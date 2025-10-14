package class16;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

// OJ链接：https://www.lintcode.com/problem/topological-sorting
public class Code03_TopologicalOrderBFS1 {

    // 提交下面的
    public static ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        // Step 1: Initialize indegree map - tracks how many incoming edges each node has
        HashMap<DirectedGraphNode, Integer> indegreeMap = new HashMap<>();
        for (DirectedGraphNode cur : graph) {
            indegreeMap.put(cur, 0); // Initially all nodes have 0 indegree
        }

        // Step 2: Calculate actual indegrees by counting incoming edges
        /*
         * Process diagram (example):
         * Graph: A → B A → C B → D C → D
         * Initial indegree: A:0 B:0 C:0 D:0
         * Pass 1 (cur=A): bump B, C A:0 B:1 C:1 D:0
         * Pass 2 (cur=B): bump D A:0 B:1 C:1 D:1
         * Pass 3 (cur=C): bump D A:0 B:1 C:1 D:2
         * Pass 4 (cur=D): no neighbors
         * Resulting indegrees: A:0 B:1 C:1 D:2
         * Meaning: Indegree = number of incoming arrows. Later the queue starts with all nodes whose indegree == 0.
         */
        for (DirectedGraphNode cur : graph) {
            for (DirectedGraphNode next : cur.neighbors) { // For each outgoing edge
                indegreeMap.put(next, indegreeMap.get(next) + 1); // Increment target node's indegree
            }
        }

        // Step 3: Find all nodes with 0 indegree (no dependencies) - these can be processed first
        Queue<DirectedGraphNode> zeroQueue = new LinkedList<>();
        for (DirectedGraphNode cur : indegreeMap.keySet()) {
            if (indegreeMap.get(cur) == 0) {
                zeroQueue.add(cur); // Add to queue for processing
            }
        }

        // Step 4: Process nodes in topological order
        ArrayList<DirectedGraphNode> ans = new ArrayList<>();
        while (!zeroQueue.isEmpty()) {
            DirectedGraphNode cur = zeroQueue.poll(); // Remove node with 0 indegree
            ans.add(cur); // Add to result (this node can be processed now)

            // Step 5: Update indegrees of neighbors and add newly available nodes
            for (DirectedGraphNode next : cur.neighbors) {
                indegreeMap.put(next, indegreeMap.get(next) - 1); // Reduce indegree (remove dependency)
                if (indegreeMap.get(next) == 0) { // If no more dependencies
                    zeroQueue.offer(next); // Add to queue for processing
                }
            }
        }
        return ans; // Return topologically sorted order
    }

    // 不要提交这个类
    public static class DirectedGraphNode {
        public int label; // Node identifier
        public ArrayList<DirectedGraphNode> neighbors; // List of nodes this node points to

        public DirectedGraphNode(int x) {
            label = x;
            neighbors = new ArrayList<DirectedGraphNode>(); // Initialize empty neighbor list
        }
    }

}
