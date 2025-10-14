package class16.practice02;
import java.util.*;

public class Dijkstra2 {
  
    public HashMap<Node, Integer> dijkstra1(Node from){
        HashMap<Node, Integer> distanceMap = new HashMap<>(); 
        HashSet<Node> selectedNodes = new HashSet<>();

        distanceMap.put(from, 0) ;
        Node minNode = from ;
        while(minNode != null){
            for(Edge edge : minNode.edges){
                Node toNode = edge.to ;
                if(!distanceMap.containsKey(toNode)){
                    distanceMap.put(toNode, distanceMap.get(minNode) + edge.weight);
                }else{
                    distanceMap.put(toNode, Math.min(distanceMap.get(toNode), 
                        distanceMap.get(minNode) + edge.weight)) ;
                }
            }
            selectedNodes.add(minNode);
            minNode = getMinDistanceFromUnselectedNodes(distanceMap, selectedNodes) ;
        }
        return distanceMap ;
    }

    private Node getMinDistanceFromUnselectedNodes(HashMap<Node, Integer> distanceMap, HashSet<Node> selectedNodes){
        Node minNode = null ;
        int minDistance = Integer.MAX_VALUE; 
        for(Map.Entry<Node, Integer> entry: distanceMap.entrySet()){
            Node node = entry.getKey() ;
            int  distance = entry.getValue() ;
            if(!selectedNodes.contains(node) &&  distance < minDistance){
                minNode = node ; 
                minDistance = distance ;
            }
       } 
       return minNode ;
    }

    public static HashMap<Node,Integer> dijkstra2(Node node, int size){
        NodeHeap nodeHeap = new NodeHeap(size) ;
        HashMap<Node,Integer> result = new HashMap<>() ;

        nodeHeap.addOrUpdateOrIgnore(node, 0) ;

        while(!nodeHeap.isEmpty()){
            NodeRecord record = nodeHeap.pop() ; 
            Node n = record.node ; 
            int distance = record.distance ; 
            
            for(Edge e: n.edges){
                nodeHeap.addOrUpdateOrIgnore(e.to, distance + e.weight);
            }
          
            result.put(n,distance) ;
        }
        return result;

    }


    public static class NodeRecord{
        Node node ;
        int distance ;
        public NodeRecord(Node n, int d){
            node = n;
            distance = d ;
        }
    }

    public static class NodeHeap{
        HashMap<Node,Integer> distanceMap ; 
        HashMap<Node,Integer> heapIndexMap ; 
        Node[] nodes ;
        int size ;
        public NodeHeap(int sz){
            size = 0 ; 
            nodes = new Node[sz] ;
            distanceMap = new HashMap<>() ;
            heapIndexMap = new HashMap<>() ; 
        }
        public boolean isEmpty(){
            return size == 0;
        }
        public boolean isEntered(Node n){
            return heapIndexMap.containsKey(n);
        }
        public boolean inHeap(Node n){
            return isEntered(n) && heapIndexMap.get(n) != -1 ;
        }

        private void insertHeapify(int index){
            while(distanceMap.get(nodes[index]) < distanceMap.get(nodes[(index -1)/2])){
                swap(index, (index -1) /2) ;
                index = (index -1) /2 ;
            }
        }

        private void heapify(int index, int size){
            int left = index * 2 + 1 ;
            while(left < size){
                int smallest = left + 1 < size && distanceMap.get(nodes[left + 1]) < distanceMap.get(nodes[left]) ?
                                left + 1 :
                                left ;
                smallest = distanceMap.get(nodes[smallest]) < distanceMap.get(nodes[index]) ? smallest : index ;
                if(smallest == index){
                    break ;
                }
                swap(smallest, index) ;
                index = smallest ;
                left = index * 2 +1 ;
            }
        }

        public NodeRecord pop(){
            Node res = nodes[0] ;
            swap(0, size -1) ;
            heapIndexMap.put(res, -1) ;
            int distance = distanceMap.get(res) ;
            NodeRecord nr = new NodeRecord(res, distance) ;
            distanceMap.remove(res) ;
            nodes[size -1] = null ;
            heapify(0, --size);
            return nr ;
        }

        public void addOrUpdateOrIgnore(Node node, int distance){
            if(inHeap(node) && distance < distanceMap.get(node)){
                distanceMap.put(node, distance) ;
                insertHeapify(heapIndexMap.get(node)) ;
            }
            if(!isEntered(node)){
                nodes[size] = node ;
                heapIndexMap.put(node, size);
                distanceMap.put(node, distance) ;
                insertHeapify(size++) ;
            }
        }


        private void swap(int x, int y){
            Node xNode = nodes[x] ;
            Node yNode = nodes[y] ;
            
            heapIndexMap.put(xNode, y);
            heapIndexMap.put(yNode, x);

            Node tmp = xNode ;
            nodes[x] = yNode ;
            nodes[y] = tmp ;
        }

    }

}
