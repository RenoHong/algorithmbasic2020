package class16.practice02;

import java.util.*;

public class Dijkstra {

    public static HashMap<Node, Integer> dijkstra1(Node from) {
        HashMap<Node, Integer> distanceMap = new HashMap<>();
        Set<Node> selectedNodes = new HashSet<>();

        distanceMap.put(from, 0) ;
        Node minNode = getMinDistanceAndUnselectedNode(distanceMap, selectedNodes) ;
        while(minNode!=null){
            int distance = distanceMap.get(minNode) ;
            for(Edge e: minNode.edges){
                Node toNode = e.to ;
                if(!distanceMap.containsKey(toNode)){
                    distanceMap.put(toNode, distance + e.weight);
                }else{
                    distanceMap.put(toNode, Math.min(distance+ e.weight, distanceMap.get(toNode)));
                }
            } 
            selectedNodes.add(minNode);
            minNode = getMinDistanceAndUnselectedNode(distanceMap, selectedNodes);
        }
        return distanceMap ;
    }


    public static Node getMinDistanceAndUnselectedNode(HashMap<Node, Integer> distanceMap, Set<Node> selectedNodes){
        Node minNode = null ;
        int minDistance = Integer.MAX_VALUE ; 

        for(Map.Entry<Node, Integer> entry: distanceMap.entrySet()){ 
            Node node = entry.getKey() ; 
            int distance = entry.getValue();
            if(!selectedNodes.contains(node) && distance < minDistance){
                minDistance = distance ; 
                minNode = node ;
            }
        }
        return minNode ;
    }

    public static class NodeRecord{
        Node node ; 
        int distance ;
        public NodeRecord(Node n, int distance){
            this.node = n ; 
            this.distance = distance ;
        }
    }

    public static HashMap<Node, Integer> Dijkstra2(Node heap, int size){

        NodeHeap nodeHeap = new NodeHeap(size) ;
        nodeHeap.addOrUpdateOrIgnore(heap, 0) ;
        HashMap<Node, Integer> result = new HashMap<>();

        while(!nodeHeap.isEmpty()){
            NodeRecord nr = nodeHeap.pop() ;
            
            for(Edge edge : nr.node.edges){
                nodeHeap.addOrUpdateOrIgnore(edge.to, nr.distance + edge.weight); 
            }
            result.put(nr.node, nr.distance) ;
        }
        return result ;

    }

    public static class NodeHeap{

        private Node[] nodes ;
        private HashMap<Node, Integer> distanceMap ; 
        private HashMap<Node, Integer> heapIndexMap ;
        private int size ;

        public NodeHeap(int size){
            nodes = new Node[size] ;
            heapIndexMap = new HashMap<>() ;
            distanceMap = new HashMap<>();
            this.size = 0 ;
        }

        public boolean isEmpty(){
            return size == 0 ;
        }

        private boolean isEntered(Node node){
            return distanceMap.containsKey(node) ;
        }

        private boolean inHeap(Node node){
            return isEntered(node) && heapIndexMap.get(node) != -1 ;
        }

        public void swap(int i1, int i2){
            heapIndexMap.put(nodes[i1], i2) ;
            heapIndexMap.put(nodes[i2], i1) ;

            Node temp = nodes[i1] ;
            nodes[i1] = nodes[i2] ;
            nodes[i2] = temp ;
        }

        private void insertHeapify(int index){
            while(distanceMap.get(nodes[index]) < distanceMap.get(nodes[(index -1) / 2])){
                swap(index, (index -1) /2) ;
                index = (index -1) /2 ;
            }
        }

        private void heapify(int index, int size){
            int left = index *2 +1 ;
            while(left < size){
                int smallest = left +1 < size && distanceMap.get(nodes[left + 1]) < distanceMap.get(nodes[left])       
                            ? left +1 
                            : left ;

                smallest = distanceMap.get(nodes[smallest]) < distanceMap.get( nodes[index] )? smallest : index ;
                if (smallest == index){
                    break ;
                }

                swap(index, smallest) ;
                index = smallest ;
                left = index *2 +1 ;
            }
        }

        public void addOrUpdateOrIgnore(Node node, int distance){
            if(inHeap(node)){
                distanceMap.put(node, Math.min(distanceMap.get(node), distance)) ;
                insertHeapify(heapIndexMap.get(node));
            }
            if(!isEntered(node)){
                nodes[size] = node ;
                heapIndexMap.put(node, size) ;
                distanceMap.put(node, distance) ;
                insertHeapify(size++) ;
            } 
        }

        public NodeRecord pop(){
            if(size == 0) return null ;
            Node node = nodes[0] ;
            NodeRecord nr = new NodeRecord(node, distanceMap.get(node));
            swap(0, --size);
            heapify(0, size) ;
            heapIndexMap.put(node, -1) ;
            distanceMap.remove(node) ;
            return nr ;
        }


    }



}
