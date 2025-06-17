package class04;

/**
 * This class provides implementations of the Merge Sort algorithm (recursive and non-recursive).
 * <p>
 * 递归的 Master 公式（Master Theorem）用于分析形如：
 * <p>
 * ```
 * T(N) = a * T(N/b) + O(N^d)
 * ```
 * <p>
 * 的递归时间复杂度，其中：
 * <p>
 * - `a`：每次递归分成的子问题个数
 * - `b`：每个子问题的规模是原问题的 1/b
 * - `d`：每层合并/处理的复杂度
 * <p>
 * 主定理结论：
 * <p>
 * - 若 `log_b(a) > d`，则 `T(N) = O(N^{log_b(a)})`
 * - 若 `log_b(a) = d`，则 `T(N) = O(N^d * logN)`
 * - 若 `log_b(a) < d`，则 `T(N) = O(N^d)`
 * <p>
 * 这是分析归并排序、快速排序等递归算法复杂度的常用方法。
 */
public class Code01_MergeSort {

    /**
     * Recursively sorts the given array using merge sort.
     *
     * @param arr the array to be sorted
     */
    public static void mergeSort1(int[] arr) {
        // If the array is null or has less than 2 elements, no need to sort
        if (arr == null || arr.length < 2) {
            return;
        }
        // Start the recursive process
        process(arr, 0, arr.length - 1);
    }

    /**
     * Recursively sorts the subarray arr[L..R].
     *
     * @param arr the array to be sorted
     * @param L   left index
     * @param R   right index
     */
    public static void process(int[] arr, int L, int R) {
        // Base case: if the subarray has only one element, it's already sorted
        if (L == R) {
            return;
        }
        // Calculate the middle index
        int mid = L + ((R - L) >> 1);
        // Recursively sort the left half
        process(arr, L, mid);
        // Recursively sort the right half
        process(arr, mid + 1, R);
        // Merge the two sorted halves
        merge(arr, L, mid, R);
    }

    /**
     * Merges two sorted subarrays arr[L..M] and arr[M+1..R] into a single sorted subarray.
     *
     * @param arr the array containing the subarrays
     * @param L   left index of the first subarray
     * @param M   right index of the first subarray
     * @param R   right index of the second subarray
     */
    public static void merge(int[] arr, int L, int M, int R) {
        // Temporary array to store merged result
        int[] help = new int[R - L + 1];
        int i = 0; // Index for help array
        int p1 = L; // Pointer for left subarray
        int p2 = M + 1; // Pointer for right subarray
        // Merge elements from both subarrays in sorted order
        while (p1 <= M && p2 <= R) {
            help[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }
        // Copy remaining elements from left subarray, if any
        while (p1 <= M) {
            help[i++] = arr[p1++];
        }
        // Copy remaining elements from right subarray, if any
        while (p2 <= R) {
            help[i++] = arr[p2++];
        }
        // Copy merged elements back to original array
        //System.arraycopy(help, 0, arr, L, help.length);
        for (i = 0; i < help.length; i++) {
            arr[L + i] = help[i];
        }
    }

    /**
     * Iteratively sorts the given array using merge sort.
     * 理解 `mergeSort2`（非递归归并排序）时，建议注意以下几点，避免混乱：
     * <p>
     * 1. **mergeSize 的含义**：它表示当前每组要合并的子数组长度，每轮循环后翻倍。
     * 2. **L、M、R 的计算**：L 是左子数组起点，M 是左子数组终点，R 是右子数组终点。注意边界处理，防止越界。
     * 3. **循环终止条件**：外层 while 控制 mergeSize 不超过数组长度，内层 while 控制 L 不超过数组长度。
     * 4. **merge 的调用**：每次合并的是相邻的两段，注意最后一组可能长度不足 mergeSize。
     * 5. **防止溢出**：mergeSize > N / 2 时直接 break，避免 mergeSize 溢出。
     * <p>
     * 建议画图理解每一轮合并的分组和下标变化，有助于理清流程。
     *
     * @param arr the array to be sorted
     */
    public static void mergeSort2(int[] arr) {
        // If the array is null or has less than 2 elements, no need to sort
        if (arr == null || arr.length < 2) {
            return;
        }
        int N = arr.length; // Length of the array
        int mergeSize = 1; // Initial size of subarrays to merge
        // Double the merge size each iteration
        while (mergeSize < N) {
            int L = 0; // Start index of the left subarray
            while (L < N) {
                // If the remaining elements are less than mergeSize, don't merge.
                if (mergeSize >= N - L) {
                    break;
                }
                int M = L + mergeSize - 1; // End index of the left subarray
                int R = M + Math.min(mergeSize, N - M - 1); // End index of the right subarray
                merge(arr, L, M, R); // Merge the two subarrays
                L = R + 1; // Move to the next pair of subarrays
            }
            // Prevent integer overflow, if N is already close to Integer.MAX_VALUE
            if (mergeSize > N / 2) {
                break;
            }
            mergeSize <<= 1; // Double the merge size
        }
    }

    /**
     * Generates a random array for testing.
     *
     * @param maxSize  maximum possible size of the array
     * @param maxValue maximum possible value of an element
     * @return the generated random array
     */
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
    }

    /**
     * Copies the given array.
     *
     * @param arr the array to copy
     * @return a new array with the same elements as arr
     */
    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    /**
     * Checks if two arrays are equal.
     *
     * @param arr1 first array
     * @param arr2 second array
     * @return true if arrays are equal, false otherwise
     */
    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Prints the elements of the array.
     *
     * @param arr the array to print
     */
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    /**
     * Main method to test the merge sort implementations.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        int testTime = 500000; // Number of tests
        int maxSize = 100; // Maximum array size
        int maxValue = 100; // Maximum element value
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue); // Generate random array
            int[] arr2 = copyArray(arr1); // Copy the array
            mergeSort1(arr1); // Sort using recursive merge sort
            mergeSort2(arr2); // Sort using iterative merge sort
            if (!isEqual(arr1, arr2)) { // Compare results
                System.out.println("出错了！");
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println("测试结束");
    }

}
