package class16.practice02;
import java.util.HashSet;
import java.util.LinkedList;

public class BFS {

    public static void bfs(Node start){
        if(start == null) 
            return ;

        LinkedList<Node> queue = new LinkedList<>();
        HashSet<Node> set = new HashSet<>(); 
        queue.add(start); 
        
        while(!queue.isEmpty()){
            Node current = queue.poll();
            System.out.println(current);
            for(Node child : current.nexts){
                if(!set.contains(child)){
                    queue.add(child);
                    set.add(child);
                }
            }
        }
    }
}
