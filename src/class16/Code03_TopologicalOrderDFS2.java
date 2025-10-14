package class16;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

// OJ链接：https://www.lintcode.com/problem/topological-sorting

/***
 * Description
 * Given an directed graph, a topological order of the graph nodes is defined as follow:
 *
 * For each directed edge A -> B in graph, A must before B in the order list.
 * The first node in the order can be any node in the graph with no nodes direct to it.
 * Find any topological order for the given graph.
 *<br/>
 * <b>Challenge</b>
 * <br/>
 * <br> Can you do it in both BFS and DFS? <br>
 */
public class Code03_TopologicalOrderDFS2 {

    public static ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        // Step 1: Calculate total reachable nodes for each node using DFS
        HashMap<DirectedGraphNode, Record> order = new HashMap<>();
        for (DirectedGraphNode cur : graph) {
            f(cur, order); // Calculate node count for each node
        }

        // Step 2: Collect all records
        ArrayList<Record> recordArr = new ArrayList<>();
        for (Record r : order.values()) {
            recordArr.add(r);
        }

        // Step 3: Sort by node count (descending - nodes with more reachable nodes first)
        recordArr.sort(new MyComparator());

        // Step 4: Extract nodes in topological order
        ArrayList<DirectedGraphNode> ans = new ArrayList<DirectedGraphNode>();
        for (Record r : recordArr) {
            ans.add(r.node);
        }
        return ans;
    }

    // 当前来到cur点，请返回cur点所到之处，所有的点次！
    // 返回（cur，点次）
    // 缓存！！！！！order
    //  key : 某一个点的点次，之前算过了！
    //  value : 点次是多少
    public static Record f(DirectedGraphNode cur, HashMap<DirectedGraphNode, Record> order) {
        if (order.containsKey(cur)) {
            return order.get(cur); // Return cached result if already computed
        }

        // cur的点次之前没算过！
        // Calculate total number of nodes reachable from current node
        long nodes = 0;
        for (DirectedGraphNode next : cur.neighbors) {
            nodes += f(next, order).nodes; // Sum up all reachable nodes from neighbors
        }

        // Total nodes = 1 (current node) + all nodes reachable from neighbors
        Record ans = new Record(cur, nodes + 1);
        order.put(cur, ans); // Cache the result
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
        public long nodes; // Total number of nodes reachable from this node (including itself)

        public Record(DirectedGraphNode n, long o) {
            node = n;
            nodes = o;
        }
    }

    // Comparator to sort by node count in descending order
    public static class MyComparator implements Comparator<Record> {
        @Override
        public int compare(Record o1, Record o2) {
            // Sort by node count descending (nodes with more reachable nodes first)
            return o1.nodes == o2.nodes ? 0 : (o1.nodes > o2.nodes ? -1 : 1);
        }
    }
}
