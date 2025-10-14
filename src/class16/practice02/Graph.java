package class16.practice02;
import java.util.HashMap;
import java.util.HashSet;

public class Graph {
    HashMap<Integer, Node> nodes ;
    HashSet<Edge> edges ;

    public Graph(){
        nodes = new HashMap<>(); 
        edges = new HashSet<>() ;
    }

}
