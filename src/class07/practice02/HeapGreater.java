package class07.practice02;
import java.util.*;

public class HeapGreater<T> {
    
    List<T> heap ;
    Map<T, Integer> indexMap ;
    int heapSize ;
    Comparator<? super T> comparator ;

    public HeapGreater(Comparator<? super T> c){
        heap = new ArrayList<>();
        indexMap = new HashMap<>();
        heapSize =0 ;
        comparator = c ;
    }

    public int size(){
        return heapSize ;
    }

    public boolean contains(T obj){
        return indexMap.containsKey(obj);
    }

    public boolean isEmpty(){
        return heap.isEmpty();
    }

    public void push(T obj){
        heap.add(obj);
        indexMap.put(obj, heapSize);
        heapInsert(heapSize++);
    }

    public T pop(){
        if(isEmpty())
            throw new RuntimeException("Empty!");
        T ans = heap.get(0);
        swap(0, heapSize--);
        heap.remove(ans) ;
        indexMap.remove(ans);
        heapify(0);
        return ans ;
    }

    private void swap(int x, int y){
        T ox = heap.get(x) ;
        T oy = heap.get(y);

        heap.set(x, oy);
        heap.set(y, ox);

        indexMap.put(ox, y);
        indexMap.put(oy, x);
    } 

    private void heapInsert(int index){
        int parent =(index-1)/2 ;
        while(comparator.compare(heap.get(index), heap.get(parent)) > 0){
            swap(index, parent );
            index = parent ;
        }
    }

    public void heapify(int index){
        int left = index *2+1;
        while(left < heapSize){
            int larger = left +1 < heapSize && comparator.compare( heap.get(left+1), heap.get(left)) <0 ?
                left +1 :
                left ;
            larger = comparator.compare(heap.get(index), heap.get(larger)) <0?
                index :
                larger;
            
            if(larger == index)
                break;
            swap(larger, index);
            index =larger ;
            left = index *2 +1;
        }
    }

    public void remove(T obj){
      int index = indexMap.get(obj) ;
      T replace = heap.get(heapSize -1 );
      indexMap.remove(obj);
      heap.remove(--heapSize);
      if(replace != obj){
        heap.set(index ,replace);
        indexMap.put(replace, index);
        resign(replace);
      }
    }

    public void resign(T obj){
        int index = indexMap.get(obj);
        heapify(index);
        heapInsert(index);
    }

    public List<T> getAllElements(){
        List<T> ans = new ArrayList<T>();
        for(T obj: heap){
            ans.add(obj);
        }
        return ans ;
    }

}

 
