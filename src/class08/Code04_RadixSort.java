package class08;

import java.util.Arrays;

public class Code04_RadixSort {

    /**
     * 基数排序主入口 - 只适用于非负整数
     * 时间复杂度: O(d * (n + k))，其中d是最大数的位数，n是数组长度，k是基数(这里是10)
     * 空间复杂度: O(n + k)
     */
    public static void radixSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        radixSort(arr, 0, arr.length - 1, maxbits(arr));
    }

    /**
     * 计算数组中最大值的十进制位数
     * 例如: [123, 45, 6789] 最大值6789有4位，返回4
     */
    public static int maxbits(int[] arr) {
        int max = Integer.MIN_VALUE;
        // 找到数组中的最大值
        for (int i = 0; i < arr.length; i++) {
            max = Math.max(max, arr[i]);
        }
        int res = 0;
        // 计算最大值的位数
        while (max != 0) {
            res++;
            max /= 10;
        }
        return res;
    }

    /**
     * 基数排序的核心实现
     * @param arr 待排序数组
     * @param L 排序范围的左边界
     * @param R 排序范围的右边界  
     * @param digit 最大数的位数
     */
    public static void radixSort(int[] arr, int L, int R, int digit) {
        final int radix = 10; // 十进制基数
        int i = 0, j = 0;
        // 辅助数组，用于存储每轮排序的结果
        int[] help = new int[R - L + 1];
        
        // 从个位开始，依次对每一位进行计数排序
        for (int d = 1; d <= digit; d++) {
            // 计数数组：count[i]表示当前位数字为0~i的元素个数（累计）
            int[] count = new int[radix]; // count[0..9]
            
            // 第一步：统计当前位上每个数字出现的次数
            for (i = L; i <= R; i++) {
                j = getDigit(arr[i], d); // 获取arr[i]在第d位上的数字
                count[j]++;
            }
            
            // 第二步：将count数组转换为累计数组
            // count[i]现在表示当前位数字≤i的元素个数
            for (i = 1; i < radix; i++) {
                count[i] = count[i] + count[i - 1];
            }
            
            // 第三步：从右到左遍历原数组，根据count数组确定每个元素的位置
            // 从右到左保证了排序的稳定性
            for (i = R; i >= L; i--) {
                j = getDigit(arr[i], d);
                help[count[j] - 1] = arr[i]; // 放到正确位置
                count[j]--; // 该数字的可用位置减1
            }
            
            // 第四步：将排序结果复制回原数组
            for (i = L, j = 0; i <= R; i++, j++) {
                arr[i] = help[j];
            }
        }
    }

    /**
     * 获取数字x在第d位上的数字
     * @param x 目标数字
     * @param d 位数（1表示个位，2表示十位，以此类推）
     * @return 第d位上的数字
     * 例如：getDigit(123, 2) = 2（十位上的数字）
     * # Radix Sort Implementation Analysis

        Let me explain the critical part of your radix sort implementation, focusing on the key operations.

        In the `getDigit` method:
        ```java
        return num / ((int) Math.pow(10, d-1)) % 10;
        ```

        This method extracts a specific digit from a number:
        1. `Math.pow(10, d-1)` calculates 10^(d-1)
        2. `num / ((int) Math.pow(10, d-1))` shifts the digit we want to the ones place
        3. `% 10` extracts just that digit

        For example, to get the 2nd digit of 3456:
        - 3456 / 10^(2-1) = 3456 / 10 = 345
        - 345 % 10 = 5

        In your radix sort implementation, there are a few issues to note:

        1. There's an infinite loop due to `i++` in the for loop condition: 
        `for(int d=1; d<=digits; i++)` - it should be `d++` instead.

        2. In the counting part:
        ```java
        for(i = l ; i <= r; i++){
            j = getDigit(arr[i], d);
            bucket[j]++;
        }
        ```
        This code:
        - Iterates through the array from index `l` to `r`
        - Gets the d-th digit of each number using the `getDigit` method
        - Increments a counter in the bucket array for that digit

        This is the counting step of radix sort where you determine how many numbers 
        have each digit in the current position. After this, you would typically use these 
        counts to determine the position of each element in the output array.
     * 
     */
    public static int getDigit(int x, int d) {
        return ((x / ((int) Math.pow(10, d - 1))) % 10);
    }

    /**
     * 用于测试的标准排序方法
     */
    public static void comparator(int[] arr) {
        Arrays.sort(arr);
    }

    /**
     * 生成随机测试数组
     */
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random());
        }
        return arr;
    }

    // for test
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

    // for test
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

    // for test
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // for test
    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100000;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            radixSort(arr1);
            comparator(arr2);
            if (!isEqual(arr1, arr2)) {
                succeed = false;
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");

        int[] arr = generateRandomArray(maxSize, maxValue);
        printArray(arr);
        radixSort(arr);
        printArray(arr);

    }

}
