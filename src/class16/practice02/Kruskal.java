package class16.practice02;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

public class Kruskal {


    public static Set<Edge> KruskalMST(Graph graph){
        UnionFind uf = new UnionFind(graph.nodes.values()) ;
        PriorityQueue<Edge> pq = new PriorityQueue<>(new EdgeComparator()) ;
        Set<Edge> ans = new HashSet<>();
        for(Edge e : graph.edges){
            pq.add(e);
        }

        while(!pq.isEmpty()){
            Edge e = pq.poll() ;
            if(!uf.isSameSet(e.from, e.to)){
                uf.union(e.from, e.to);
                ans.add(e) ;
            }
        }

        return ans ;
    }



    public static class UnionFind{
        HashMap<Node, Node> parentMap = new HashMap<>() ;
        HashMap<Node, Integer> sizeMap = new HashMap<>() ;

        public UnionFind(Collection<Node> nodes){
            for(Node n : nodes){
                parentMap.put(n, n) ;
                sizeMap.put(n, 1) ;
            }
        }

        public Node findParent(Node n){
            
            Stack<Node> stk = new Stack<>(); 
            Node current = n ;
            while(current != parentMap.get(current)){
                stk.push(current) ;
                current = parentMap.get(current) ;
            }

            while(!stk.isEmpty()){
                Node temp = stk.pop();
                parentMap.put(temp, current) ;
            }

            return current ;
        }

        public boolean isSameSet(Node n1, Node n2){
            Node p1 = findParent(n1) ;
            Node p2 = findParent(n2) ;
            return p1.equals(p2) ;
        }

        public void union(Node n1, Node n2){
            if(n1.equals(n2)) return ;

            Node p1 = findParent(n1) ; 
            Node p2 = findParent(n2) ;

            if(!p1.equals(p2)){
                int sz1 = sizeMap.get(p1) ;
                int sz2 = sizeMap.get(p2) ;
                Node bigger = sz1 >= sz2 ? p1 : p2 ; 
                Node smaller = bigger.equals(p1)? p2 : p1 ; 

                parentMap.put(smaller, bigger) ;
                sizeMap.put(bigger, sz1 + sz2) ;
            }
        }
    }

    public static class EdgeComparator implements Comparator<Edge>{
        @Override
        public int compare(Edge e1, Edge e2){
            return e1.weight - e2.weight ;
        }
    }

}
