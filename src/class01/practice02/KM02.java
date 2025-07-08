package class01.practice02;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;

public class KM02 {

    public static int findK(int[] arr, int k, int m){
        Map<Integer, Integer> map = new HashMap<>();
        createMap(map);
        int[] T = new int[32] ;
        int ans =0 ; 

        for(int n : arr){
           while(n!=0){
            int rightMost = n & (-n);
            T[map.get(rightMost)]++;
            n ^= rightMost ;
           }
        }

        for(int i=0; i<32; i++){
            if(T[i] %m !=0){
                if(T[i]%m ==k){
                    ans |= ( 1 << i) ;
                }else{
                    return -1 ;
                }
            } 
        }
        if(ans == 0 ){
            int c =0 ; 
            for(int i =0 ; i < arr.length; i++){
                if(arr[i] == 0)
                    c++;
            }
            if(c !=k ){
                return -1;
            }
        }
        return ans ;

    }

    private static void createMap(Map<Integer, Integer> map){
        int value =1 ; 
        for(int i =0; i< 32; i++){
            map.put((value << i), i);
        }
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
            int ans2 = findK(arr, k, m);
            if (ans1 != ans2) {
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("出错了！");
                break ;
            }
        }
        System.out.println("测试结束");

    }

}
