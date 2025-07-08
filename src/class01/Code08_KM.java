package class01;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Provides methods to find the number that appears K times in an array where all other numbers appear M times.
 */
public class Code08_KM {

    public static HashMap<Integer, Integer> map = new HashMap<>();

    /**
     * Brute-force method to find the number that appears K times.
     *
     * @param arr The input array.
     * @param k   The unique occurrence count.
     * @param m   The common occurrence count.
     * @return The number that appears K times, or -1 if not found.
     */
    public static int test(int[] arr, int k, int m) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : arr) {
            if (map.containsKey(num)) {
                map.put(num, map.get(num) + 1);
            } else {
                map.put(num, 1);
            }
        }
        for (int num : map.keySet()) {
            if (map.get(num) == k) {
                return num;
            }
        }
        return -1;
    }

    /**
     * Finds the number that appears K times using bitwise operations.
     * Assumes only one number appears K times, others appear M times.
     *
     * @param arr The input array.
     * @param k   The unique occurrence count.
     * @param m   The common occurrence count.
     * @return The number that appears K times, or -1 if not found.
     */
    public static int onlyKTimes(int[] arr, int k, int m) {
        if (map.size() == 0) {
            mapCreater(map);
        }
        int[] t = new int[32];
        // t[0] 0位置的1出现了几个
        // t[i] i位置的1出现了几个
        for (int num : arr) {
            while (num != 0) {
                int rightOne = num & (-num);
                t[map.get(rightOne)]++;
                num ^= rightOne;
            }
        }
        int ans = 0;
        for (int i = 0; i < 32; i++) {
            if (t[i] % m != 0) {
                if (t[i] % m == k) {
                    ans |= (1 << i);
                } else {
                    return -1;
                }
            }
        }
/*
 * 详细解释：

你的代码逻辑大概率是在找“数组中恰好出现k次的数”，并且用ans变量存储了这个数。
但如果这个数是0，ans就会等于0。此时你无法区分“真的有k个0”还是“没有任何数出现k次，ans只是默认值0”。
所以需要额外判断：如果ans == 0，就统计数组中0的个数。如果0的个数不是k，说明其实没有任何数出现k次，应该返回-1。
举例说明：

假设arr = [0, 0, 0, 1, 2]，k = 3，那么0出现了3次，应该返回0。

但如果arr = [1, 2, 3, 4, 5]，k = 3，没有任何数出现3次，ans可能还是0（因为没找到），但这时0其实没出现3次，所以要返回-1。

总结： 这段代码就是为了正确处理“0出现k次”这种特殊情况，避免误判。
 */

        if (ans == 0) {
            int count = 0;
            for (int num : arr) {
                if (num == 0) {
                    count++;
                }
            }
            if (count != k) {
                return -1;
            }
        }
        return ans;
    }

    /**
     * Creates a mapping from bit value to its index for 32 bits.
     *这个方法 `mapCreater` 的主要作用是**创建一个从二进制位值到其索引的映射**，针对32位整数。

    具体来说，它会把每个只有一位为1的整数（即 1, 2, 4, 8, 16, ...，对应二进制的 0001, 0010, 0100, 1000, ...）映射到它在32位整数中的索引（0 到 31）。  
    例如：  
    - 1 (0b0001) 映射到 0  
    - 2 (0b0010) 映射到 1  
    - 4 (0b0100) 映射到 2  
    - ...  
    - 2147483648 (0b10000000000000000000000000000000) 映射到 31

    **用途：**  
    这种映射常用于位运算相关的算法，比如快速判断某个位是第几位，或者在处理位掩码时查找对应的位索引。

    **小结：**  
    `mapCreater` 方法的作用是**为每个单一二进制位值建立其对应的索引映射**，方便后续位运算操作。
     * @param map The map to populate.
     */
    public static void mapCreater(HashMap<Integer, Integer> map) {
        int value = 1;
        for (int i = 0; i < 32; i++) {
            map.put(value, i);
            value <<= 1;
        }
    }

    /**
     * Generates a random array for testing, with one number appearing K times and others M times.
     *
     * @param maxKinds Maximum number of different numbers.
     * @param range    Value range for numbers.
     * @param k        The unique occurrence count.
     * @param m        The common occurrence count.
     * @return The generated array.
     */
    public static int[] randomArray(int maxKinds, int range, int k, int m) {
        int ktimeNum = randomNumber(range);
        // 真命天子出现的次数. 50% chance to use k, otherwise we can use m-1 as k.
        int times = Math.random() < 0.5 ? k : ((int) (Math.random() * (m - 1)) + 1);
        // 2
        int numKinds = (int) (Math.random() * maxKinds) + 2;
        // k * 1 + (numKinds - 1) * m
        int[] arr = new int[times + (numKinds - 1) * m];
        int index = 0;
        for (; index < times; index++) {
            arr[index] = ktimeNum;
        }
        numKinds--;
        HashSet<Integer> set = new HashSet<>();
        set.add(ktimeNum);
        while (numKinds != 0) {
            int curNum = 0;
            do {
                curNum = randomNumber(range);
            } while (set.contains(curNum));
            set.add(curNum);
            numKinds--;
            for (int i = 0; i < m; i++) {
                arr[index++] = curNum;
            }
        }
        // arr 填好了
        for (int i = 0; i < arr.length; i++) {
            // i 位置的数，我想随机和j位置的数做交换
            int j = (int) (Math.random() * arr.length);// 0 ~ N-1
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
        return arr;
    }

    /**
     * Generates a random number in the range [-range, +range].
     *
     * @param range The range for the random number.
     * @return The generated random number.
     */
    public static int randomNumber(int range) {
        return ((int) (Math.random() * range) + 1) - ((int) (Math.random() * range) + 1);
    }

    /**
     * Main method to test the onlyKTimes algorithm.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        int kinds = 5;
        int range = 30;
        int testTime = 100000;
        int max = 9;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int a = (int) (Math.random() * max) + 1; // a 1 ~ 9
            int b = (int) (Math.random() * max) + 1; // b 1 ~ 9
            int k = Math.min(a, b);
            int m = Math.max(a, b);
            // k < m
            if (k == m) {
                m++;
            }
            int[] arr = randomArray(kinds, range, k, m);
            int ans1 = test(arr, k, m);
            int ans2 = onlyKTimes(arr, k, m);
            if (ans1 != ans2) {
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("出错了！");
            }
        }
        System.out.println("测试结束");

    }

}