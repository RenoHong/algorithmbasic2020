package class16.practice02;

import class16.Code03_TopologicalOrderBFS1;

import java.util.ArrayList;

public class TopologicalOrderBFS1 {


    // 不要提交这个类
    public static class DirectedGraphNode {
        public int label; // Node identifier
        public ArrayList<Code03_TopologicalOrderBFS1.DirectedGraphNode> neighbors; // List of nodes this node points to

        public DirectedGraphNode(int x) {
            label = x;
            neighbors = new ArrayList<Code03_TopologicalOrderBFS1.DirectedGraphNode>(); // Initialize empty neighbor list
        }
    }

}
