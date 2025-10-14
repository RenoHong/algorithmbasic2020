package class16.practice02;
 
import java.util.*;

public class TopologySort { 

     public static List<Node> sortedTopology(Graph graph) {

       HashMap<Node, Integer> inMap = new HashMap<>(); 
       Queue<Node> zeroQ = new LinkedList<>();
       List<Node> ans = new ArrayList<>(); 

       for(Node n: graph.nodes.values()){
            inMap.put(n, n.in);
            if(n.in == 0){
                zeroQ.offer(n); 
            }
       }

       while(!zeroQ.isEmpty()){
            Node n = zeroQ.poll();
            ans.add(n) ;
            for(Node c: n.nexts){
                int in = inMap.get(c) -1;
                if(in == 0){ 
                    zeroQ.offer(c) ;
                }
                inMap.put(c, in);
            }
       }
        
       return ans ;
      

     }
}
