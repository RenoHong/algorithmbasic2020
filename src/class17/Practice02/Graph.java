package class17.Practice02;
import java.util.HashMap;
import java.util.HashSet;
public class Graph {
    HashMap<Integer, Node> nodes ;
    HashSet<Edge> edges ; 

    public Graph(){
        this.nodes = new HashMap<>();
        this.edges = new HashSet<>();
    }

}
