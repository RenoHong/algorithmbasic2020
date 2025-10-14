package class16.practice02;

import java.util.ArrayList;
import java.util.HashMap;

public class TopologicalOrderDFS {


    public static ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
      
        ArrayList<DirectedGraphNode> ans = new ArrayList<>() ;
        ArrayList<Record> records = new ArrayList<>();
         
        HashMap<DirectedGraphNode, Record> map = new HashMap<>(); 

        for(DirectedGraphNode node : graph){
            records.add(process(node, map));
        }  
        records.sort((r1, r2)-> r1.depth - r2.depth) ;

        for(Record r: records){
            ans.add(r.node);
        }
        return ans;
    }

    private static Record process(DirectedGraphNode node, HashMap<DirectedGraphNode, Record> cache) 
    {
        if(cache.containsKey(node)){
            return cache.get(node) ;
        }

        int depth =0 ;
        for(DirectedGraphNode c: node.neighbors){
            depth = Math.max(depth, process(c, cache).depth);
        }
 
        Record r =  new Record(node, depth +1) ;
        cache.put(node, r) ;
        return r ;
    }

    public static class Record{
        DirectedGraphNode node ;
        int depth = 0 ;
        public Record(DirectedGraphNode node, int depth){
            this.node = node;
            this.depth = depth ;
        }
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

}
