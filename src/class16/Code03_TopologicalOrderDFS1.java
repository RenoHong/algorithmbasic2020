package class16;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

// OJ链接：https://www.lintcode.com/problem/topological-sorting
public class Code03_TopologicalOrderDFS1 {

    public static ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        // Step 1: Calculate depth for each node using DFS with memoization
        HashMap<DirectedGraphNode, Record> order = new HashMap<>();
        for (DirectedGraphNode cur : graph) {
            f(cur, order); // Calculate depth for each node
        }

        // Step 2: Collect all records into a list
        ArrayList<Record> recordArr = new ArrayList<>();
        for (Record r : order.values()) {
            recordArr.add(r);
        }

        // Step 3: Sort by depth (descending order - deeper nodes first)
        recordArr.sort(new MyComparator());

        // Step 4: Extract nodes in topological order
        ArrayList<DirectedGraphNode> ans = new ArrayList<DirectedGraphNode>();
        for (Record r : recordArr) {
            ans.add(r.node); // Add nodes in sorted order
        }
        return ans;
    }

    // DFS function to calculate maximum depth from current node
    public static Record f(DirectedGraphNode cur, HashMap<DirectedGraphNode, Record> order) {
        if (order.containsKey(cur)) {
            return order.get(cur); // Return cached result if already computed
        }

        // Find maximum depth among all neighbors
        int follow = 0;
        for (DirectedGraphNode next : cur.neighbors) {
            follow = Math.max(follow, f(next, order).deep); // Recursive call to get neighbor's depth
        }

        // Current node's depth is 1 + maximum depth of neighbors
        Record ans = new Record(cur, follow + 1);
        order.put(cur, ans); // Cache the result for future use
        return ans;
    }

    // 不要提交这个类
    public static class DirectedGraphNode {
        public int label; // Node identifier
        public ArrayList<DirectedGraphNode> neighbors; // Outgoing edges

        public DirectedGraphNode(int x) {
            label = x;
            neighbors = new ArrayList<DirectedGraphNode>();
        }
    }

    // 提交下面的
    public static class Record {
        public DirectedGraphNode node; // The graph node
        public int deep; // Maximum depth from this node

        public Record(DirectedGraphNode n, int o) {
            node = n;
            deep = o;
        }
    }

    // Comparator to sort records by depth in descending order
    public static class MyComparator implements Comparator<Record> {
        @Override
        public int compare(Record o1, Record o2) {
            return o2.deep - o1.deep; // Sort by depth descending (deeper nodes first)
        }
    }
}
