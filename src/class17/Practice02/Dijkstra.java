package class17.Practice02;

import java.util.*;

public class Dijkstra {

    public static HashMap<Node, Integer> dijkstra1(Node from) {

        HashMap<Node, Integer> distanceMap = new HashMap<>();
        HashSet<Node> selectedNodes = new HashSet<>(); 
        distanceMap.put(from, 0) ;

        Node minNode = from  ;
        while(minNode != null){
            List<Edge> edges = minNode.edges;
            for(Edge edge:edges){
                Node node = edge.to ;
                int weight = edge.weight + distanceMap.get(minNode) ;
                if(!distanceMap.containsKey(node)){
                    distanceMap.put(node, weight) ;
                }else{
                    distanceMap.put(node, Math.min(weight, distanceMap.get(node)));
                }
            }
            selectedNodes.add(minNode);
            minNode = getMinDistanceNodeFromUnselectedNodes(distanceMap, selectedNodes) ;
        }
        return distanceMap ;
    }

    private static Node getMinDistanceNodeFromUnselectedNodes(HashMap<Node, Integer> distanceMap, HashSet<Node> selectedNodes){
        Node minNode = null ;
        int distance = Integer.MAX_VALUE ; 
        for(Map.Entry<Node,Integer> entry: distanceMap.entrySet()){
             Node node = entry.getKey();
             int value = entry.getValue() ;
             if(!selectedNodes.contains(node) && distance > value){
                minNode = node ;
                distance = value ;
             }
        }
        return minNode ;
    }

    public static HashMap<Node, Integer> dijkstra2(Node head, int size){
        HashMap<Node, Integer> res = new HashMap<>() ;  
        Heap heap = new Heap(size) ;
        heap.addOrUpdateOrIgnore(head, 0) ;
 
        while(!heap.isEmpty()){
            NodeRecord nr = heap.pop();
            Node minNode = nr.node ;
            int distance = nr.weight ;
            for(Edge edge : minNode.edges){
                Node toNode = edge.to ; 
                heap.addOrUpdateOrIgnore(toNode, distance + edge.weight) ;
            }  
            res.put(minNode, distance) ;
        } 
        return res ; 
    }

    public static class NodeRecord{
        int weight ;
        Node node ;
        public NodeRecord(Node n, int w){
            node = n ;
            weight = w ; 
        }
    }

    public static class Heap{
        HashMap<Node, Integer> heapIndexMap = new HashMap<>() ;
        HashMap<Node, Integer> distanceMap = new HashMap<>() ;
        Node[] nodes ;
        int size = 0 ; 

        public Heap(int length){
            nodes = new Node[length] ;
        }

        public boolean isEntered(Node node){
            return heapIndexMap.containsKey(node) ;
        }

        public boolean inHeap(Node node){
            Integer index = heapIndexMap.get(node);
            return index != null && index != -1 ;
        }

        public void addOrUpdateOrIgnore(Node node, int distance){
            if(inHeap(node)){
                distanceMap.put(node, Math.min(distanceMap.get(node), distance)) ;
                heapifyUp(heapIndexMap.get(node));
            }else if(!isEntered(node)){
                nodes[size] = node ;
                heapIndexMap.put(node, size) ;
                distanceMap.put(node, distance); 
                heapifyUp(size++);
            } 
        }

        public NodeRecord pop(){
            NodeRecord nr = null ;
            if(!isEmpty()){
                Node zeroNode = nodes[0] ;
                nr = new NodeRecord(zeroNode, distanceMap.get(zeroNode));
                swap(0, size -1) ;
                nodes[size -1] = null ;
                heapIndexMap.put(zeroNode, -1) ;
                distanceMap.remove(zeroNode) ; 
                heapifyDown(0, --size);  
            }
            return nr ;
        }

        public boolean isEmpty(){
            return size == 0 ;
        }

        private void heapifyUp(int index){
            while(index >0 && distanceMap.get(nodes[index]) <  distanceMap.get(nodes[(index-1)/2])){
                swap(index, (index-1)/2) ;
                index = (index - 1) /2 ;
            }
        }
        private void heapifyDown(int index, int size){
            int left = index * 2 + 1 ; 
            while(left < size){
                int smallest = left + 1 < size && distanceMap.get(nodes[left + 1]) < distanceMap.get(nodes[left])?
                    left + 1 
                    : left ;

                smallest = distanceMap.get(nodes[index]) < distanceMap.get(nodes[smallest]) ? index : smallest ;
                if(smallest == index) {
                    break ;
                }
                swap(index, smallest) ;
                index = smallest ;
                left = index * 2  + 1 ; 
            } 
        }

        private void swap(int x, int y){
            Node xNode = nodes[x] ;
            Node yNode = nodes[y];
            heapIndexMap.put(yNode, x) ;
            heapIndexMap.put(xNode, y) ;
 
            nodes[x] = yNode;
            nodes[y] = xNode;
        }

 

    }


}
