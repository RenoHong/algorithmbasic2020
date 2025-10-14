package class16.practice02;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
public class DFS {

    public static void dfs(Node start) {
        if (start == null) return;

        Stack<Node> stk = new Stack<>();
        Set<Node> set = new HashSet<>();
        stk.push(start);
        set.add(start); 
        System.out.println(start);
        
        while (!stk.isEmpty()) {
            Node current = stk.pop();
            for (Node n : current.nexts) {
                if (!set.contains(n)) {
                    stk.push(current);
                    stk.push(n);
                    set.add(n);
                    System.out.println(n);
                    break;
                }
            }
        }
    }
}
