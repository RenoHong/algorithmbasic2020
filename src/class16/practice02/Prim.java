package class16.practice02;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class Prim {

    public static Set<Edge> PrimMST(Graph graph) {
        Set<Edge> ans = new HashSet<>();
        Set<Node> visited = new HashSet<>();
        PriorityQueue<Edge> q = new PriorityQueue<>((e1, e2) -> e1.weight - e2.weight);

        for (Node n : graph.nodes.values()) {
            if (!visited.contains(n)) {
                visited.add(n);
                q.addAll(n.edges);
                while (!q.isEmpty()) {
                    Edge e = q.poll();
                    Node toNode = e.to;
                    if (!visited.contains(toNode)) {
                        visited.add(toNode);
                        ans.add(e);
                        q.addAll(toNode.edges);
                    }
                }
            }
        }
        return ans;
    }


}
