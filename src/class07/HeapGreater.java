package class07;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class HeapGreater<T> {

    private ArrayList<T> heap;
    private HashMap<T, Integer> indexMap;
    private int heapSize;
    private Comparator<? super T> comp;

    /**
     * 构造函数，初始化堆和辅助结构
     * @param c 比较器
     */
    public HeapGreater(Comparator<? super T> c) {
        heap = new ArrayList<>();
        indexMap = new HashMap<>();
        heapSize = 0;
        comp = c;
    }

    /**
     * 判断堆是否为空
     */
    public boolean isEmpty() {
        return heapSize == 0;
    }

    /**
     * 返回堆中元素数量
     */
    public int size() {
        return heapSize;
    }

    /**
     * 判断元素是否在堆中
     */
    public boolean contains(T obj) {
        return indexMap.containsKey(obj);
    }

    /**
     * 返回堆顶元素（不弹出）
     */
    public T peek() {
        return heap.get(0);
    }

    /**
     * 向堆中插入新元素
     */
    public void push(T obj) {
        heap.add(obj);
        indexMap.put(obj, heapSize);
        heapInsert(heapSize++);
    }

    /**
     * 弹出堆顶元素
     */
    public T pop() {
        T ans = heap.get(0);
        swap(0, heapSize - 1); // 将堆顶和最后一个元素交换
        indexMap.remove(ans);  // 移除堆顶元素的索引
        heap.remove(--heapSize); // 删除最后一个元素
        heapify(0); // 从堆顶向下调整
        return ans;
    }

    /**
     * 移除堆中的指定元素
     */
    public void remove(T obj) {
        T replace = heap.get(heapSize - 1); // 取最后一个元素
        int index = indexMap.get(obj);      // 获取要删除元素的位置
        indexMap.remove(obj);               // 移除索引
        heap.remove(--heapSize);            // 删除最后一个元素
        if (obj != replace) {               // 如果不是最后一个元素本身
            heap.set(index, replace);       // 用最后一个元素填补
            indexMap.put(replace, index);   // 更新索引
            resign(replace);                // 重新调整堆
        }
    }

    /**
     * 元素值变化后，重新调整其在堆中的位置
     */
    public void resign(T obj) {
        heapInsert(indexMap.get(obj)); // 向上调整
        heapify(indexMap.get(obj));    // 向下调整
    }

    /**
     * 返回堆中所有元素的列表
     */
    public List<T> getAllElements() {
        List<T> ans = new ArrayList<>();
        for (T c : heap) {
            ans.add(c);
        }
        return ans;
    }

    /**
     * 从index位置向上调整堆
     */
    private void heapInsert(int index) {
        while (comp.compare(heap.get(index), heap.get((index - 1) / 2)) < 0) {
            swap(index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    /**
     * 从index位置向下调整堆
     */
    private void heapify(int index) {
        int left = index * 2 + 1;
        while (left < heapSize) {
            int best = left + 1 < heapSize && comp.compare(heap.get(left + 1), heap.get(left)) < 0 ? (left + 1) : left;
            best = comp.compare(heap.get(best), heap.get(index)) < 0 ? best : index;
            if (best == index) {
                break;
            }
            swap(best, index);
            index = best;
            left = index * 2 + 1;
        }
    }

    /**
     * 交换堆中两个元素的位置，并更新索引映射
     */
    private void swap(int i, int j) {
        T o1 = heap.get(i);
        T o2 = heap.get(j);
        heap.set(i, o2);
        heap.set(j, o1);
        indexMap.put(o2, i);
        indexMap.put(o1, j);
    }

}
