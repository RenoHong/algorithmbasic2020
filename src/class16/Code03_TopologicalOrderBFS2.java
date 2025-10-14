package class16;

// 课上没讲这个实现
// 因为是一样的，都是根据入度来求拓扑排序，只不过是牛客网的测试数据
// 测试链接 : https://www.nowcoder.com/questionTerminal/88f7e156ca7d43a1a535f619cd3f495c
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下所有代码，把主类名改成Main，可以直接通过

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Code03_TopologicalOrderBFS2 {

    public static int MAXN = 200001; // Maximum number of nodes supported

    public static int[] queue = new int[MAXN]; // Array-based queue for better performance

    public static int[] indegree = new int[MAXN]; // Array to store indegrees of nodes

    public static int[] ans = new int[MAXN]; // Array to store the topological order result

    public static int n, m, from, to; // n=nodes, m=edges, from/to for edge endpoints

    public static void main(String[] args) throws IOException {
        // High-performance I/O setup for competitive programming
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(br); // Fast input parsing
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out)); // Fast output

        while (in.nextToken() != StreamTokenizer.TT_EOF) { // Read until end of file
            n = (int) in.nval; // Number of nodes
            in.nextToken();
            m = (int) in.nval; // Number of edges

            // Build adjacency list representation of graph
            ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
            for (int i = 0; i <= n; i++) {
                graph.add(new ArrayList<>()); // Initialize adjacency list for each node
            }

            // Read edges and build graph
            for (int i = 0; i < m; i++) {
                in.nextToken();
                from = (int) in.nval; // Source node
                in.nextToken();
                to = (int) in.nval; // Target node
                graph.get(from).add(to); // Add directed edge from -> to
            }

            // Perform topological sort and output result
            if (!topoSort(graph)) {
                out.println(-1); // No valid topological order (cycle detected)
            } else {
                // Output the topological order
                for (int i = 0; i < n - 1; i++) {
                    out.print(ans[i] + " ");
                }
                out.println(ans[n - 1]); // Last element without space
            }
            out.flush(); // Ensure output is written
        }
    }

    // 有拓扑排序返回true
    // 没有拓扑排序返回false
    public static boolean topoSort(ArrayList<ArrayList<Integer>> graph) {
        // Initialize indegrees to 0 for nodes 1 to n
        Arrays.fill(indegree, 1, n + 1, 0);

        // Calculate indegrees by counting incoming edges
        for (ArrayList<Integer> nexts : graph) {
            for (int next : nexts) {
                indegree[next]++; // Increment indegree for each incoming edge
            }
        }

        // Array-based queue implementation for better performance
        int l = 0; // Left pointer (head of queue)
        int r = 0; // Right pointer (tail of queue)

        // Add all nodes with 0 indegree to queue
        for (int i = 1; i <= n; i++) {
            if (indegree[i] == 0) {
                queue[r++] = i; // Add to queue and increment tail pointer
            }
        }

        int cnt = 0; // Count of processed nodes
        while (l < r) { // While queue is not empty
            int cur = queue[l++]; // Dequeue node and increment head pointer
            ans[cnt++] = cur; // Add to result array

            // Process all neighbors of current node
            for (int next : graph.get(cur)) {
                if (--indegree[next] == 0) { // Decrement indegree and check if it becomes 0
                    queue[r++] = next; // Add newly available node to queue
                }
            }
        }
        return cnt == n; // Return true if all nodes were processed (no cycles)
    }
}
