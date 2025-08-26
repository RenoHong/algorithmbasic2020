package class14.practice02;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class UnionFind<V> { 
    private HashMap<V, V> parents ;
    private HashMap<V, Integer> size ;

    public UnionFind(List<V> list){
        parents = new HashMap<>(); 
        size = new HashMap<>();

        for(V v: list){
            parents.put(v, v) ;
            size.put(v, 1) ;
        }
    } 

    public V findParent(V v){
         Stack<V> stack = new Stack<>() ;
         V current = parents.get(v) ;
         while(!current.equals(parents.get(current))){
            stack.push(current) ;
            current = parents.get(current) ;
         }

         while(!stack.isEmpty()){
            parents.put(stack.pop(),current) ;
         }
         return current ;
    }

    public boolean isSameset(V v1, V v2){
        return findParent(v1) == findParent(v2) ;
    }

    public void union(V v1, V v2){

        V parent1 = findParent(v1) ;
        V parent2 = findParent(v2) ;
        if(!parent1.equals(parent2)){
            int size1 = size.get(parent1) ;
            int size2 = size.get(parent2) ;

            if(size1 >= size2) {
                parents.put(v2, v1) ;
                size.put(v1, size.get(v1) + size.get(v2));
                size.remove(v2);
            }else{
                parents.put(v1, v2) ;
                size.put(v2, size.get(v1)+ size.get(v2));
                size.remove(v1);
            }
        }
    }

    public int sets(){
        return size.size();
    }
}
