package class17.Practice02;
import java.util.ArrayList;
import java.util.List;
public class Node {
    int value ;
    List<Node> nexts ;
    int in, out ;
    List<Edge> edges ; 

    public Node(int value){
        this.value = value ;
        nexts = new ArrayList<>(); 
        edges = new ArrayList<>();
        in = out = 0 ;
    }

}
