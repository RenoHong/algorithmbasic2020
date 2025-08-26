package class15;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// 本题为leetcode原题
// 测试链接：https://leetcode.cn/problems/number-of-islands-ii/
// 所有方法都可以直接通过
/**
 * Problem: Given an initially water grid (m x n) and a sequence of land additions (positions),
 * return the number of islands after each addition.
 * Key: Dynamic connectivity — each connect(r,c) may create a new island or merge with neighbors.
 * Approaches:
 *   - UnionFind1: Pre-allocated arrays sized m*n; good when m*n is manageable.
 *   - UnionFind2: Sparse map-based DSU; avoids O(m*n) init when k (positions) is small vs. m*n.
 */
public class Code03_NumberOfIslandsII {

    /**
     * What: Returns island counts after each land addition using array-based DSU.
     * Why: Efficient for moderate m*n; O(1) per neighbor union amortized.
     * How:
     *   - Initialize DSU with all sizes 0 (water).
     *   - For each (r,c), connect() activates land and unions with 4 neighbors.
     * Complexity: Each operation ~ O(α(N)).
     */
    public static List<Integer> numIslands21(int m, int n, int[][] positions) {
        UnionFind1 uf = new UnionFind1(m, n);
        List<Integer> ans = new ArrayList<>();
        for (int[] position : positions) {
            ans.add(uf.connect(position[0], position[1]));
        }
        return ans;
    }

    /**
     * What: Returns island counts using sparse map-based DSU; optimized when grid is huge and k is small.
     * Why: Avoids O(m*n) memory and initialization; stores only visited land cells.
     * How:
     *   - Keys are "r_c" strings; connect() lazily creates nodes and unions with existing neighbors.
     * Complexity: O(α(K)) per operation, with HashMap overhead.
     */
    // 课上讲的如果m*n比较大，会经历很重的初始化，而k比较小，怎么优化的方法
    public static List<Integer> numIslands22(int m, int n, int[][] positions) {
        UnionFind2 uf = new UnionFind2();
        List<Integer> ans = new ArrayList<>();
        for (int[] position : positions) {
            ans.add(uf.connect(position[0], position[1]));
        }
        return ans;
    }

    /**
     * Array-based DSU specialized for dynamic island connectivity on an m x n grid.
     * Design:
     *   - parent/size/help arrays sized m*n.
     *   - size[i] == 0 indicates water; size[i] >= 1 indicates land (component size).
     *   - sets tracks current number of islands.
     * Rationale: Fast and cache-friendly for dense or moderate grids.
     */
    public static class UnionFind1 {
        private final int row;
        private final int col;
        private int[] parent;
        private int[] size;
        private int[] help;
        private int sets;

        /**
         * What: Initialize DSU arrays but do not activate any land.
         * Why: Land is activated lazily in connect() when positions arrive.
         * Space: O(m*n).
         */
        public UnionFind1(int m, int n) {
            row = m;
            col = n;
            sets = 0;
            int len = row * col;
            parent = new int[len];
            size = new int[len];
            help = new int[len];
        }

        /**
         * What: Map (r, c) to 1D index in DSU arrays.
         * Why: Flatten 2D grid into 1D storage.
         * Complexity: O(1).
         */
        private int index(int r, int c) {
            return r * col + c;
        }

        /**
         * What: Find with path compression.
         * Why: Standard DSU optimization for near-constant amortized cost.
         */
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
         * What: Union two cells if both are land and within bounds.
         * Why: Merge neighboring land components when a new land touches existing land.
         * How:
         *   - Bounds and water checks; union by size; decrement sets when merged.
         */
        private void union(int r1, int c1, int r2, int c2) {
            if (r1 < 0 || r1 == row || r2 < 0 || r2 == row || c1 < 0 || c1 == col || c2 < 0 || c2 == col) {
                return;
            }
            int i1 = index(r1, c1);
            int i2 = index(r2, c2);
            if (size[i1] == 0 || size[i2] == 0) {
                return;
            }
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
         * What: Turn (r,c) into land if not already, and union with its 4 neighbors.
         * Why: Dynamic connectivity—each addition may create a new island or merge existing ones.
         * How:
         *   - If size[index] == 0 → activate: parent[index]=index, size=1, sets++.
         *   - Union with up/down/left/right.
         * Returns: Current number of islands after this addition.
         * Complexity: O(α(N)) per addition.
         * Diagram:
         *   Step 1: add (1,1) → sets=1
         *   Step 2: add (1,2) → union right → sets=1
         *   Step 3: add (0,1) → union up with (1,1) → sets=1
         */
        public int connect(int r, int c) {
            int index = index(r, c);
            if (size[index] == 0) {
                parent[index] = index;
                size[index] = 1;
                sets++;
                union(r - 1, c, r, c);
                union(r + 1, c, r, c);
                union(r, c - 1, r, c);
                union(r, c + 1, r, c);
            }
            return sets;
        }

    }

    /**
     * Sparse DSU keyed by "r_c" strings.
     * Why: Avoids O(m*n) memory/time when the number of additions k << m*n.
     * Design:
     *   - parent: key → parent key
     *   - size: root key → component size
     *   - help: temp list for path compression
     *   - sets: current number of islands
     */
    public static class UnionFind2 {
        private HashMap<String, String> parent;
        private HashMap<String, Integer> size;
        private ArrayList<String> help;
        private int sets;

        /**
         * What: Initialize empty DSU (no land).
         * Why: Lands are added lazily via connect.
         * Space: O(k) where k is number of lands added.
         */
        public UnionFind2() {
            parent = new HashMap<>();
            size = new HashMap<>();
            help = new ArrayList<>();
            sets = 0;
        }

        /**
         * What: Find root key with path compression along recorded path.
         * Why: Optimize DSU operations over time.
         */
        private String find(String cur) {
            while (!cur.equals(parent.get(cur))) {
                help.add(cur);
                cur = parent.get(cur);
            }
            for (String str : help) {
                parent.put(str, cur);
            }
            help.clear();
            return cur;
        }

        /**
         * What: Union two keys if both exist (both are land).
         * Why: Merge neighboring lands when a new land touches an existing one.
         * How: Union by size, update sets.
         */
        private void union(String s1, String s2) {
            if (parent.containsKey(s1) && parent.containsKey(s2)) {
                String f1 = find(s1);
                String f2 = find(s2);
                if (!f1.equals(f2)) {
                    int size1 = size.get(f1);
                    int size2 = size.get(f2);
                    String big = size1 >= size2 ? f1 : f2;
                    String small = big == f1 ? f2 : f1;
                    parent.put(small, big);
                    size.put(big, size1 + size2);
                    sets--;
                }
            }
        }

        /**
         * What: Turn (r,c) into land if new, and union with 4 neighbors.
         * Why: Dynamic connectivity update; returns the current island count.
         * How:
         *   - Build key "r_c". If absent, create singleton set; sets++.
         *   - Try union with up/down/left/right keys if present.
         * Complexity: Amortized near-constant; HashMap dominates.
         */
        public int connect(int r, int c) {
            String key = String.valueOf(r) + "_" + String.valueOf(c);
            if (!parent.containsKey(key)) {
                parent.put(key, key);
                size.put(key, 1);
                sets++;
                String up = String.valueOf(r - 1) + "_" + String.valueOf(c);
                String down = String.valueOf(r + 1) + "_" + String.valueOf(c);
                String left = String.valueOf(r) + "_" + String.valueOf(c - 1);
                String right = String.valueOf(r) + "_" + String.valueOf(c + 1);
                union(up, key);
                union(down, key);
                union(left, key);
                union(right, key);
            }
            return sets;
        }

    }

}
