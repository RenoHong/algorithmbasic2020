package class16;

import java.util.*;


/***
 * Uses Kahn's algorithm (BFS in-degree method):
 *
 * 1. Compute remaining in-degree for every node.
 * 2. Initialize a queue with all nodes whose in-degree is 0.
 * 3. Repeatedly dequeue a node, append to result, decrement in-degree of its outgoing neighbors.
 * 4. When a neighbor\'s in-degree drops to 0, enqueue it.
 * 5. Collected order is a valid topological ordering (for DAGs).
 *
 * Time: O(V + E). Space: O(V).
 */
public class Code03_TopologySort {

    // directed graph and no loop
    public static List<Node> sortedTopology(Graph graph) {
        // key 某个节点   value 剩余的入度
        HashMap<Node, Integer> inMap = new HashMap<>();
        // 只有剩余入度为0的点，才进入这个队列
        Queue<Node> zeroInQueue = new LinkedList<>();
        for (Node node : graph.nodes.values()) {
            inMap.put(node, node.in);
            if (node.in == 0) {
                zeroInQueue.add(node);
            }
        }
        List<Node> result = new ArrayList<>();
        while (!zeroInQueue.isEmpty()) {
            Node cur = zeroInQueue.poll();
            result.add(cur);
            for (Node next : cur.nexts) {
                inMap.put(next, inMap.get(next) - 1);
                if (inMap.get(next) == 0) {
                    zeroInQueue.add(next);
                }
            }
        }
        return result;
    }
}
