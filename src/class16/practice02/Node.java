package class16.practice02;
import java.util.ArrayList;
import java.util.List;

public class Node {
    int in, out ;
    List<Edge> edges ;
    List<Node> nexts; 
    int value ; 

    public Node(int v){
        edges = new ArrayList<>();
        nexts = new ArrayList<>();
        in = out = 0 ;
        value = v ; 
    }

}
