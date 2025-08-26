package class15;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

// 本题为leetcode原题
// 测试链接：https://leetcode.cn/problems/number-of-islands/
// 所有方法都可以直接通过
/**
 * Problem: Count connected components of '1's in a 2D grid (4-directional connectivity).
 * Provided solutions:
 *   - numIslands3: DFS "infection" (flood fill). Simple and fast; modifies grid.
 *   - numIslands1: Union-Find using objects and HashMaps. Educational/reference.
 *   - numIslands2: Union-Find optimized with arrays and index mapping. Production-friendly.
 * Trade-offs:
 *   - DFS: O(R*C) time, O(R*C) worst-case recursion stack; no extra DSU structures.
 *   - DSU(Map): O(R*C α(N)) time but higher overhead due to boxing/HashMap.
 *   - DSU(Array): O(R*C α(N)) time and O(R*C) space; efficient and non-recursive.
 */
public class Code02_NumberOfIslands {

    /**
     * What: Counts islands using DFS flood fill (infect).
     * Why: Very straightforward; whenever we see a '1', it's a new island; flood it to avoid recounting.
     * How:
     *   - Scan all cells; when board[i][j] == '1', increment count and infect neighbors to mark the whole island.
     *   - infect() sets visited land to '0' to prevent double-counting.
     * Complexity: O(R*C) time; O(R*C) recursion depth worst-case.
     * Diagram:
     *   1 1 0       encounter (0,0) → infect whole 2x1 block
     *   1 0 1       next '1' at (1,2) → new island
     *   ⇒ answer = 2
     */
    public static int numIslands3(char[][] board) {
        int islands = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == '1') {
                    islands++;
                    infect(board, i, j);
                }
            }
        }
        return islands;
    }

    /**
     * What: Flood fill from (i, j) converting the entire connected component of '1's to '0's.
     * Why: Marks visited land so it won't be counted again; ensures each island contributes exactly 1.
     * How:
     *   - Stop at out-of-bounds or if current cell is not '1'.
     *   - Set current to '0' and recurse to 4 neighbors.
     * Complexity: Each cell visited at most once; overall O(R*C).
     */
    // 从(i,j)这个位置出发，把所有练成一片的'1'字符，变成0
    public static void infect(char[][] board, int i, int j) {
        if (i < 0 || i == board.length || j < 0 || j == board[0].length || board[i][j] != '1') {
            return;
        }
        board[i][j] = 0;
        infect(board, i - 1, j);
        infect(board, i + 1, j);
        infect(board, i, j - 1);
        infect(board, i, j + 1);
    }

    /**
     * What: Counts islands using Union-Find with object-based nodes (Dot) and HashMaps.
     * Why: Demonstrates DSU without relying on index math; maps are flexible but slower.
     * How:
     *   - Create Dot objects only for '1's; build a list to initialize UnionFind1.
     *   - Union adjacent '1's horizontally and vertically.
     *   - The number of sets in DSU is the number of islands.
     * Complexity: O(R*C α(N)) time; space proportional to number of '1's.
     * Note: Splits edge unions into top row, left column, and inner cells to minimize checks.
     */
    public static int numIslands1(char[][] board) {
        int row = board.length;
        int col = board[0].length;
        Dot[][] dots = new Dot[row][col];
        List<Dot> dotList = new ArrayList<>();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (board[i][j] == '1') {
                    dots[i][j] = new Dot();
                    dotList.add(dots[i][j]);
                }
            }
        }
        UnionFind1<Dot> uf = new UnionFind1<>(dotList);
        for (int j = 1; j < col; j++) {
            // (0,j)  (0,0)跳过了  (0,1) (0,2) (0,3)
            if (board[0][j - 1] == '1' && board[0][j] == '1') {
                uf.union(dots[0][j - 1], dots[0][j]);
            }
        }
        for (int i = 1; i < row; i++) {
            if (board[i - 1][0] == '1' && board[i][0] == '1') {
                uf.union(dots[i - 1][0], dots[i][0]);
            }
        }
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                if (board[i][j] == '1') {
                    if (board[i][j - 1] == '1') {
                        uf.union(dots[i][j - 1], dots[i][j]);
                    }
                    if (board[i - 1][j] == '1') {
                        uf.union(dots[i - 1][j], dots[i][j]);
                    }
                }
            }
        }
        return uf.sets();
    }

    /**
     * What: Counts islands using Union-Find optimized with arrays and index mapping.
     * Why: Avoids object/HashMap overhead; faster and memory efficient.
     * How:
     *   - Initialize DSU entries only for cells with '1' (others remain zero size).
     *   - Union neighbor pairs along first row, first column, and internal cells.
     *   - Return DSU set count.
     * Complexity: O(R*C α(N)) time; O(R*C) space.
     * Diagram (indexing):
     *   index(r,c) = r * col + c
     *   2x3 grid:
     *     (0,0)=0 (0,1)=1 (0,2)=2
     *     (1,0)=3 (1,1)=4 (1,2)=5
     */
    public static int numIslands2(char[][] board) {
        int row = board.length;
        int col = board[0].length;
        UnionFind2 uf = new UnionFind2(board);
        for (int j = 1; j < col; j++) {
            if (board[0][j - 1] == '1' && board[0][j] == '1') {
                uf.union(0, j - 1, 0, j);
            }
        }
        for (int i = 1; i < row; i++) {
            if (board[i - 1][0] == '1' && board[i][0] == '1') {
                uf.union(i - 1, 0, i, 0);
            }
        }
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                if (board[i][j] == '1') {
                    if (board[i][j - 1] == '1') {
                        uf.union(i, j - 1, i, j);
                    }
                    if (board[i - 1][j] == '1') {
                        uf.union(i - 1, j, i, j);
                    }
                }
            }
        }
        return uf.sets();
    }

    /**
     * What: Generates a random R x C grid of '1' and '0' for testing.
     * Why: Used to benchmark and validate methods on varied inputs.
     * Complexity: O(R*C).
     */
    // 为了测试
    public static char[][] generateRandomMatrix(int row, int col) {
        char[][] board = new char[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                board[i][j] = Math.random() < 0.5 ? '1' : '0';
            }
        }
        return board;
    }

    /**
     * What: Deep copy a grid.
     * Why: Preserve original data for fair comparisons across methods.
     * Complexity: O(R*C).
     */
    // 为了测试
    public static char[][] copy(char[][] board) {
        int row = board.length;
        int col = board[0].length;
        char[][] ans = new char[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                ans[i][j] = board[i][j];
            }
        }
        return ans;
    }

    /**
     * What: Benchmarks and compares three implementations on large random grids.
     * Why: Demonstrates performance characteristics and validates correctness.
     * Notes:
     *   - numIslands3 (DFS) can be faster on some inputs but uses recursion.
     *   - numIslands1 (Map DSU) is slower due to HashMap overhead.
     *   - numIslands2 (Array DSU) scales better for large grids.
     */
    // 为了测试
    public static void main(String[] args) {
        int row = 0;
        int col = 0;
        char[][] board1 = null;
        char[][] board2 = null;
        char[][] board3 = null;
        long start = 0;
        long end = 0;

        row = 1000;
        col = 1000;
        board1 = generateRandomMatrix(row, col);
        board2 = copy(board1);
        board3 = copy(board1);

        System.out.println("感染方法、并查集(map实现)、并查集(数组实现)的运行结果和运行时间");
        System.out.println("随机生成的二维矩阵规模 : " + row + " * " + col);

        start = System.currentTimeMillis();
        System.out.println("感染方法的运行结果: " + numIslands3(board1));
        end = System.currentTimeMillis();
        System.out.println("感染方法的运行时间: " + (end - start) + " ms");

        start = System.currentTimeMillis();
        System.out.println("并查集(map实现)的运行结果: " + numIslands1(board2));
        end = System.currentTimeMillis();
        System.out.println("并查集(map实现)的运行时间: " + (end - start) + " ms");

        start = System.currentTimeMillis();
        System.out.println("并查集(数组实现)的运行结果: " + numIslands2(board3));
        end = System.currentTimeMillis();
        System.out.println("并查集(数组实现)的运行时间: " + (end - start) + " ms");

        System.out.println();

        row = 10000;
        col = 10000;
        board1 = generateRandomMatrix(row, col);
        board3 = copy(board1);
        System.out.println("感染方法、并查集(数组实现)的运行结果和运行时间");
        System.out.println("随机生成的二维矩阵规模 : " + row + " * " + col);

        start = System.currentTimeMillis();
        System.out.println("感染方法的运行结果: " + numIslands3(board1));
        end = System.currentTimeMillis();
        System.out.println("感染方法的运行时间: " + (end - start) + " ms");

        start = System.currentTimeMillis();
        System.out.println("并查集(数组实现)的运行结果: " + numIslands2(board3));
        end = System.currentTimeMillis();
        System.out.println("并查集(数组实现)的运行时间: " + (end - start) + " ms");

    }

    public static class Dot {

    }

    /**
     * Node wrapper so that different V values map to their own nodes in DSU.
     * Used by UnionFind1 (map-based DSU).
     */
    public static class Node<V> {

        V value;

        public Node(V v) {
            value = v;
        }

    }

    /**
     * Union-Find (DSU) based on HashMaps.
     * Why: Works with arbitrary reference types (e.g., Dot) without index math.
     * Structure:
     *   - nodes: V → Node<V> mapping
     *   - parents: Node → parent Node
     *   - sizeMap: root Node → size
     */
    public static class UnionFind1<V> {
        public HashMap<V, Node<V>> nodes;
        public HashMap<Node<V>, Node<V>> parents;
        public HashMap<Node<V>, Integer> sizeMap;

        /**
         * What: Initialize DSU with each value as its own set.
         * Why: Start with all nodes disjoint; union merges later.
         * Complexity: O(N) where N is number of values.
         */
        public UnionFind1(List<V> values) {
            nodes = new HashMap<>();
            parents = new HashMap<>();
            sizeMap = new HashMap<>();
            for (V cur : values) {
                Node<V> node = new Node<>(cur);
                nodes.put(cur, node);
                parents.put(node, node);
                sizeMap.put(node, 1);
            }
        }

        /**
         * What: Find representative node with path compression using a stack.
         * Why: Flatten paths to speed up subsequent finds/unions.
         * How:
         *   - Walk up via parents until root.
         *   - Compress all nodes on the path to point to root.
         * Complexity: Amortized inverse Ackermann.
         */
        public Node<V> findFather(Node<V> cur) {
            Stack<Node<V>> path = new Stack<>();
            while (cur != parents.get(cur)) {
                path.push(cur);
                cur = parents.get(cur);
            }
            while (!path.isEmpty()) {
                parents.put(path.pop(), cur);
            }
            return cur;
        }

        /**
         * What: Union sets containing values a and b by size.
         * Why: Keep DSU trees shallow for performance.
         * How:
         *   - Find roots for a and b.
         *   - Attach smaller set under larger; update sizes; remove small from sizeMap.
         * Complexity: Amortized near-constant.
         */
        public void union(V a, V b) {
            Node<V> aHead = findFather(nodes.get(a));
            Node<V> bHead = findFather(nodes.get(b));
            if (aHead != bHead) {
                int aSetSize = sizeMap.get(aHead);
                int bSetSize = sizeMap.get(bHead);
                Node<V> big = aSetSize >= bSetSize ? aHead : bHead;
                Node<V> small = big == aHead ? bHead : aHead;
                parents.put(small, big);
                sizeMap.put(big, aSetSize + bSetSize);
                sizeMap.remove(small);
            }
        }

        /**
         * What: Current number of disjoint sets.
         * Why: Equals number of islands when initialized with all '1's and unions applied.
         * Complexity: O(1).
         */
        public int sets() {
            return sizeMap.size();
        }

    }

    /**
     * Array-based Union-Find for grids.
     * Fields:
     *   - parent, size: DSU arrays sized row*col
     *   - help: temp array for path compression
     *   - col: number of columns (for index mapping)
     *   - sets: current number of land components
     * Why: Efficient DSU tailored to grid indices.
     */
    public static class UnionFind2 {
        private int[] parent;
        private int[] size;
        private int[] help;
        private int col;
        private int sets;

        /**
         * What: Initialize DSU for all cells; activate entries only for '1's.
         * Why: Avoids creating sets for water cells; sets counts land components.
         * Complexity: O(R*C) initialization.
         */
        public UnionFind2(char[][] board) {
            col = board[0].length;
            sets = 0;
            int row = board.length;
            int len = row * col;
            parent = new int[len];
            size = new int[len];
            help = new int[len];
            for (int r = 0; r < row; r++) {
                for (int c = 0; c < col; c++) {
                    if (board[r][c] == '1') {
                        int i = index(r, c);
                        parent[i] = i;
                        size[i] = 1;
                        sets++;
                    }
                }
            }
        }

        /**
         * What: Map (r, c) to 1D index.
         * Why: Allows DSU arrays to represent 2D grid cells.
         * Complexity: O(1).
         */
        // (r,c) -> i
        private int index(int r, int c) {
            return r * col + c;
        }

        /**
         * What: Find with path compression using an auxiliary array.
         * Why: Improves amortized complexity of DSU operations.
         * Complexity: Amortized inverse Ackermann.
         */
        // 原始位置 -> 下标
        private int find(int i) {
            int hi = 0;
            while (i != parent[i]) {
                help[hi++] = i;
                i = parent[i];
            }
            for (hi--; hi >= 0; hi--) {
                parent[help[hi]] = i;
            }
            return i;
        }

        /**
         * What: Union two cells (r1,c1) and (r2,c2) if both are land and in bounds.
         * Why: Merge adjacent land components into larger islands.
         * How:
         *   - Bounds check; skip if either is water (size == 0).
         *   - Union by size; decrement sets when merged.
         * Complexity: Amortized near-constant.
         */
        public void union(int r1, int c1, int r2, int c2) {
            int i1 = index(r1, c1);
            int i2 = index(r2, c2);
            int f1 = find(i1);
            int f2 = find(i2);
            if (f1 != f2) {
                if (size[f1] >= size[f2]) {
                    size[f1] += size[f2];
                    parent[f2] = f1;
                } else {
                    size[f2] += size[f1];
                    parent[f1] = f2;
                }
                sets--;
            }
        }

        /**
         * What: Returns the current number of islands (disjoint land sets).
         * Complexity: O(1).
         */
        public int sets() {
            return sets;
        }

    }

}
