package class01.practice02;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Provides methods to find the number that appears K times in an array where all other numbers appear M times.
 */
public class KM {

    static HashMap<Integer, Integer> map;

    /**
     * Finds the number that appears K times using bitwise operations.
     *
     * @param arr The input array.
     * @param k   The unique occurrence count.
     * @param m   The common occurrence count.
     * @return The number that appears K times, or -1 if not found.
     */
    public static int onlyKTimes(int[] arr, int k, int m) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        if (k <= 0 || m <= 0 || k == m) {
            return -1;
        }

        //Fill in array
        int[] bits = new int[32];
        for (int i : arr) {
            for (int j = 0; j < bits.length; j++) {
                if ((i >> j) != 0)
                    bits[j] += (i >> j) & 1;
            }
        }

        int ans = 0;
        for (int i = 0; i < bits.length; i++) {
            if (bits[i] % m != 0) {
                ans |= (1 << i);
            }
        }
        //System.out.printf("\tFound the right number in onlyKTimes: k=%d\n", ans);
        return ans;
    }

    /**
     * Creates a frequency map for the given array.
     *
     * @param arr The input array.
     */
    private static void recreateMap(int[] arr) {
        map = new HashMap<>();
        for (int i : arr) {
            if (map.containsKey(i))
                map.put(i, map.get(i) + 1);
            else
                map.put(i, 1);
        }
        //System.out.println("map is " + map.toString());
    }

    /**
     * Brute-force method to find the number that appears K times.
     *
     * @param arr The input array.
     * @param k   The unique occurrence count.
     * @param m   The common occurrence count.
     * @return The number that appears K times, or -1 if not found.
     */
    public static int test(int[] arr, int k, int m) {
        recreateMap(arr);
        for (int i : map.keySet()) {
            if (map.get(i) == k) {
                //System.out.printf("\tFound the right number after k=%d -> value=%d%n\n", k, map.get(i));
                return i;
            }
        }
        return -1;
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
     * Generates a random array for testing, with one number appearing K times and others M times.
     *
     * @param kinds Number of different numbers.
     * @param range Value range for numbers.
     * @param k     The unique occurrence count.
     * @param m     The common occurrence count.
     * @return The generated array.
     */
    public static int[] randomArray(int kinds, int range, int k, int m) {

        int nForK = randomNumber(range);

        // int kTimes = Math.random() < 0.5 ? k : ((int) (Math.random() * (m - 1))) + 1;

        int nOfKinds = (int) (Math.random() * kinds) + 2;

        int[] arr = new int[k + m * (nOfKinds - 1)];
        int index = 0;
        //k numbers
        for (; index < k; index++) {
            arr[index] = nForK;
        }
        nOfKinds--;
        HashSet<Integer> set = new HashSet<>();
        set.add(nForK);

        //m numbers
        while (nOfKinds != 0) {
            int nForM = 0;
            do {
                nForM = randomNumber(range);
            } while (set.contains(nForM));
            set.add(nForM);
            for (int i = 0; i < m; i++) {
                arr[index++] = nForM;
            }
            nOfKinds--;
        }

        for (int i = 0; i < arr.length; i++) {
            int replaceIndex = (int) (Math.random() * arr.length);
            int temp = arr[i];
            arr[i] = arr[replaceIndex];
            arr[replaceIndex] = temp;
        }
        return arr;
    }


    /**
     * Main method to test the onlyKTimes algorithm.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        int kind = 5;
        int range = 200;
        int times = 10000;
        int max = 9;

        System.out.println("{\n\tTest start");
        for (int i = 0; i < times; i++) {
            int a = (int) (Math.random() * max) + 1;
            int b = (int) (Math.random() * max) + 1;
            int k = Math.min(a, b);
            int m = Math.max(a, b);
            if (k == m) m++;

            int[] arr = randomArray(kind, range, k, m);
            int testResult = test(arr, k, m);
            int onlyKTimesResult = onlyKTimes(arr, k, m);

            if (testResult != onlyKTimesResult) {
                System.out.println(Arrays.toString(arr));
                System.out.printf("k=%d, m=%d, %d != %d\n", k, m, testResult, onlyKTimesResult);
                System.out.println("Opps!!! ");
                return;
            }
        }

        System.out.println("\tTest Completed \n}");
    }
}