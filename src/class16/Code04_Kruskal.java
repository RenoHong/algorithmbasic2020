// File: class16/Code04_Kruskal.java
package class16;

import java.util.*;

//undirected graph only
/***
 * **Kruskal's Algorithm: Edge-Centric**
-------------------------------------

*   **Starts with edges**: Sorts all edges by weight and processes them globally
    
*   **Greedy choice**: Always picks the minimum weight edge that doesn't create a cycle
    
*   **Forest approach**: Builds multiple disconnected components that eventually merge into one tree
    
*   **Data structure**: Uses Union-Find to detect cycles
    

**Prim's Algorithm: Node-Centric**
----------------------------------

*   **Starts with a node**: Picks any starting vertex and grows the tree from there
    
*   **Greedy choice**: Always picks the minimum weight edge connecting the current tree to a new vertex
    
*   **Tree growth**: Maintains one connected component that expands incrementally
    
*   **Data structure**: Uses priority queue to track edges from current tree to unvisited nodes
*/
public class Code04_Kruskal {

    public static Set<Edge> kruskalMST(Graph graph) {
        // Kruskal 思想: 始终挑当前权重最小、且不会形成环的边加入生成树
        // 核心步骤:
        // 1. 全部边按权重从小到大排序
        // 2. 依次尝试加入边，若两个端点不在同一集合(不成环)则选入
        // 3. 用 Union-Find 快速判断是否成环
        // 结果: 得到最小生成树(MST)，若图连通则包含 (N-1) 条边
        UnionFind unionFind = new UnionFind();
        unionFind.makeSets(graph.nodes.values()); // 初始化并查集，每个节点单独成集合
        // 从小的边到大的边，依次弹出，小根堆！
        // 使用优先级队列(最小堆)替代一次性排序数组也是等价形式
        // 若先把所有边放入堆，整体复杂度: O(M log M) (M 为边数)
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(new EdgeComparator());
        for (Edge edge : graph.edges) { // M 条边
            priorityQueue.add(edge);  // O(logM) 加入堆
        }
        Set<Edge> result = new HashSet<>();
        // 循环弹出当前最小权重的边，贪心选择
        // 正确性依据: "切分定理(Cut Property)" —— 对任意一个割，其最小跨越边一定在某个 MST 中
        while (!priorityQueue.isEmpty()) { // M 条边
            Edge edge = priorityQueue.poll(); // O(logM) 取当前最小边
            // 判断加入该边是否成环 -> 看两个端点是否已在同一集合
            if (!unionFind.isSameSet(edge.from, edge.to)) { // O(1) 近似 (路径压缩 + 按规模合并)
                result.add(edge);  // 选择此边
                unionFind.union(edge.from, edge.to); // 合并两个集合
            }
            // 若已在同一集合则跳过该边 (形成环), 继续处理下一条
        }
        // 若原图连通, result.size() == 节点数 - 1
        // 若不连通, 返回为最小生成森林(每个连通分量一棵生成树)
        return result;
    }

    // Union-Find Set
    public static class UnionFind {
        // key 某一个节点， value key节点往上的节点
        // fatherMap 维护父指针: 支持路径压缩实现近似 O(1) 查询
        private HashMap<Node, Node> fatherMap;
        // key 某一个集合的代表节点, value key所在集合的节点个数
        // sizeMap 用于按集合规模合并(Union by Size)，降低树高度
        private HashMap<Node, Integer> sizeMap;

        public UnionFind() {
            fatherMap = new HashMap<Node, Node>();
            sizeMap = new HashMap<Node, Integer>();
        }

        public void makeSets(Collection<Node> nodes) {
            // 初始化: 每个节点父指针指向自身，自成一棵树
            fatherMap.clear();
            sizeMap.clear();
            for (Node node : nodes) {
                fatherMap.put(node, node);
                sizeMap.put(node, 1);
            }
        }

        private Node findFather(Node n) {
            // 路径压缩: 用栈暂存沿途节点，最终全部直接挂到根节点上
            // 这样将后续同集合的查询复杂度摊还到近似常数
            Stack<Node> path = new Stack<>();
            while (n != fatherMap.get(n)) {
                path.add(n);
                n = fatherMap.get(n);
            }
            // 压缩整条路径
            while (!path.isEmpty()) {
                fatherMap.put(path.pop(), n);
            }
            return n; // 返回代表元素(集合根)
        }

        public boolean isSameSet(Node a, Node b) {
            // 判断是否在同一集合 -> 根是否相同
            return findFather(a) == findFather(b);
        }

        public void union(Node a, Node b) {
            if (a == null || b == null) {
                return;
            }
            Node aDai = findFather(a);
            Node bDai = findFather(b);
            if (aDai != bDai) {
                int aSetSize = sizeMap.get(aDai);
                int bSetSize = sizeMap.get(bDai);
                // 按规模合并(Union by Size):
                // 小集合挂到大集合根下，保持树高度更低
                if (aSetSize <= bSetSize) {
                    fatherMap.put(aDai, bDai);
                    sizeMap.put(bDai, aSetSize + bSetSize);
                    sizeMap.remove(aDai); // 旧代表失效
                } else {
                    fatherMap.put(bDai, aDai);
                    sizeMap.put(aDai, aSetSize + bSetSize);
                    sizeMap.remove(bDai);
                }
            }
            // 若已经同根，说明本就同集合 -> 不做处理 (避免成环)
        }
    }

    public static class EdgeComparator implements Comparator<Edge> {

        @Override
        public int compare(Edge o1, Edge o2) {
            // 根据边权重升序排序
            // 若权重大可考虑 long 避免减法溢出, 但此处按题意假设安全
            return o1.weight - o2.weight;
        }

    }
}