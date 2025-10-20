package class14;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

// 课上讲的并查集实现
// 请务必看补充的Code06_UnionFind
// 那是数组实现的并查集，并且有测试链接
// 可以直接通过
// 这个文件的并查集是用map实现的
// 但是笔试或者平时用的并查集一律用数组实现
// 所以Code06_UnionFind更具实战意义
// 一定要看！
public class Code05_UnionFind {

    // 课上讲的时候
    // 包了一层
    // 其实不用包一层哦
    public static class UnionFind<V> {
        // father: maps each node to its parent in the union-find structure
        public HashMap<V, V> father;
        // size: maps each root node to the size of its set
        public HashMap<V, Integer> size;

        // Constructor: initializes the union-find structure with each value as its own parent
        public UnionFind(List<V> values) {
            father = new HashMap<>(); // create the parent map
            size = new HashMap<>();   // create the size map
            for (V cur : values) {    // iterate through all values
                father.put(cur, cur); // set each node's parent to itself
                size.put(cur, 1);     // set each node's set size to 1
            }
        }

        // Finds the representative (root) of the set containing 'cur'
        // Uses path compression to flatten the structure for efficiency
        public V findFather(V cur) {
            Stack<V> path = new Stack<>(); // stack to record the path from cur to root
            while (cur != father.get(cur)) { // while cur is not its own parent
                path.push(cur);              // record cur in the path
                cur = father.get(cur);       // move up to parent
            }
            while (!path.isEmpty()) {        // path compression: set all nodes on the path to point directly to root
                father.put(path.pop(), cur);
            }
            return cur; // return the root representative
        }

        // Checks if two nodes are in the same set
        public boolean isSameSet(V a, V b) {
            return findFather(a) == findFather(b); // compare roots
        }

        // Unions the sets containing nodes a and b
        public void union(V a, V b) {
            V aFather = findFather(a); // find root of a
            V bFather = findFather(b); // find root of b
            if (aFather != bFather) {  // only union if roots are different
                int aSize = size.get(aFather); // get size of a's set
                int bSize = size.get(bFather); // get size of b's set
                if (aSize >= bSize) {          // attach smaller set to larger set
                    father.put(bFather, aFather); // b's root points to a's root
                    size.put(aFather, aSize + bSize); // update size of new root
                    size.remove(bFather);            // remove old root size
                } else {
                    father.put(aFather, bFather); // a's root points to b's root
                    size.put(bFather, aSize + bSize); // update size of new root
                    size.remove(aFather);             // remove old root size
                }
            }
        }

        // Returns the number of disjoint sets currently in the structure
        public int sets() {
            return size.size(); // each root in size map represents a set
        }

    }
}
