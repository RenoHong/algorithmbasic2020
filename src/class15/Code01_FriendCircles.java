package class15;

// 本题为leetcode原题
// 测试链接：https://leetcode.cn/problems/friend-circles/
//  https://leetcode.cn/problems/number-of-provinces/
// 可以直接通过
/**
 * Problem: Find the number of friend circles (connected components) in a friendship adjacency matrix M.
 * Why Union-Find: We need to count connected components efficiently while merging connections.
 *   - Each student starts as its own set.
 *   - For every friendship M[i][j] == 1, we union the two sets.
 *   - The number of disjoint sets at the end is the number of friend circles.
 * Complexity:
 *   - Time: O(N^2 α(N)) scanning upper triangle and unions; α is inverse Ackermann (very small).
 *   - Space: O(N) for DSU arrays.
 * Intuition (diagram):
 *   M =
 *     i\j 0 1 2
 *      0  1 1 0
 *      1  1 1 0
 *      2  0 0 1
 *   Unions: (0,1) → sets {0,1}, {2} → answer 2.
 */
public class Code01_FriendCircles {

    /**
     * What: Counts connected components (friend circles) in adjacency matrix M using Union-Find.
     * Why: Union-Find provides near-constant-time merging and component counting.
     * How:
     *   - Initialize DSU with N nodes.
     *   - Scan only j > i (upper triangle) since M is symmetric and diagonal is self.
     *   - Union i and j when M[i][j] == 1.
     *   - Return number of disjoint sets.
     * Complexity: O(N^2 α(N)) time; O(N) space.
     */
    public static int findCircleNum(int[][] M) {
        int N = M.length;
        // {0} {1} {2} {N-1}
        UnionFind unionFind = new UnionFind(N);
        // 只遍历矩形上三角区域， 因为00，11，22 都是在对角线上，且M[i][j] == M[j][i]
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                if (M[i][j] == 1) { // i和j互相认识
                    unionFind.union(i, j);
                }
            }
        }
        return unionFind.sets();
    }

    /**
     * Disjoint Set Union (Union-Find) with:
     * - parent: parent pointers
     * - size: size of set for representative nodes (used for union by size/rank)
     * - help: auxiliary array for path compression
     * - sets: current number of disjoint sets
     * Why: Efficient merging and component counting for graph connectivity problems.
     */
    public static class UnionFind {
        // parent[i] = k ： i的父亲是k
        private int[] parent;
        // size[i] = k ： 如果i是代表节点，size[i]才有意义，否则无意义
        // i所在的集合大小是多少
        private int[] size;
        // 辅助结构
        private int[] help;
        // 一共有多少个集合
        private int sets;

        /**
         * What: Initialize DSU with N singleton sets.
         * Why: Each student is initially in its own friend circle.
         * How: parent[i] = i; size[i] = 1; sets = N.
         * Space: O(N).
         */
        public UnionFind(int N) {
            parent = new int[N];
            size = new int[N];
            help = new int[N];
            sets = N;
            for (int i = 0; i < N; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        /**
         * What: Finds the representative (root) of node i with path compression.
         * Why: Path compression flattens the tree for near-constant-time future operations.
         * How:
         *   - Walk up parents until i == parent[i].
         *   - Record path in help[], then compress all nodes on the path to point directly to root.
         * Complexity: Amortized inverse Ackermann per call.
         *
         */
        // 从i开始一直往上，往上到不能再往上，代表节点，返回
        // 这个过程要做路径压缩
        private int find(int i) {
            int hi = 0;
            // Example tree (parent pointers):
            // index:   0  1  2  3
            // parent: [0, 0, 1, 2]
            // Path if i starts at 3: 3 -> 2 -> 1 -> 0 (root)

            // Phase 1: climb and record
            // hi=0, i=3  -> help[0]=3, i=2
            // hi=1, i=2  -> help[1]=2, i=1
            // hi=2, i=1  -> help[2]=1, i=0 (found root)
            // hi=3 after loop

            // Phase 2: compress (hi-- first -> hi=2)
            // parent[help[2]]=i -> parent[1]=0
            // parent[help[1]]=i -> parent[2]=0
            // parent[help[0]]=i -> parent[3]=0

            // Resulting parent pointers:
            // index:   0  1  2  3
            // parent: [0, 0, 0, 0]
            // All nodes on the path now point directly to the root (0).
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
         * What: Merge the sets containing i and j using union by size.
         * Why: Union by size keeps trees shallow, improving performance with path compression.
         * How:
         *   - Find roots f1 and f2.
         *   - If different, attach smaller tree to larger; update size and decrement sets.
         * Complexity: Amortized near-constant time.
         * Diagram:
         *   f1(0..2) size=3      f2(3..4) size=2
         *        f1                    f2
         *       /|\                   / \
         *      0 1 2                 3   4
         *   union → parent[f2]=f1, size[f1]=5, sets--.
         */
        public void union(int i, int j) {
            int f1 = find(i);
            int f2 = find(j);
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
         * What: Returns the current number of disjoint sets (friend circles).
         * Why: Final answer needed by the problem.
         * Complexity: O(1).
         */
        public int sets() {
            return sets;
        }
    }

}
