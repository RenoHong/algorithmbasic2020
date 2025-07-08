package class04.practice02;

import java.util.Random;

public class BiggerThanRightTwice {

    public static int bigger(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }

        return process(arr, 0, arr.length - 1);
    }

    private static int process(int[] arr, int l, int r) {
        if (l == r)
            return 0;

        int m = l + ((r - l) >> 1);

        return process(arr, l, m) + process(arr, m + 1, r) + merge(arr, l, m, r);
    }

    private static int merge(int[] arr, int l, int m, int r) {
        int ans = 0;
        int windowR = m + 1;
        for (int i = l; i <= m; i++) {
            while (windowR <= r && (long) arr[i] > (long) arr[windowR] * 2) {
                windowR++;
            }

            ans += windowR - m - 1;
        }

        int p1 = l;
        int p2 = m + 1;
        int i = 0;
        int[] helper = new int[r - l + 1];

        while (p1 <= m && p2 <= r) {
            helper[i++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
        }

        while (p1 <= m) {
            helper[i++] = arr[p1++];
        }
        while (p2 <= r) {
            helper[i++] = arr[p2++];
        }
        for (int j = 0; j < helper.length; j++) {
            arr[l + j] = helper[j];
        }

        return ans;
    }


//    public static int compare(int[] arr) {
//        if (arr == null || arr.length < 2)
//            return 0;
//
//        int res = 0;
//        for (int i = 0; i < arr.length; i++) {
//            for (int j = i + 1; j < arr.length; j++) {
//                if ((long) arr[i] > arr[j] * 2) {
//                    res++;
//                }
//            }
//        }
//        return res;
//    }

    public static int comparator(int[] arr) {
        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if ((long) arr[i] > (((long) arr[j]) << 1)) {
                    ans++;
                }
            }
        }
        return ans;
    }

    public static int[] randomArray(int maxLen, int maxValue) {
        Random rand = new Random();
        int len = rand.nextInt(maxLen) + 1;
        int[] ans = new int[len];
        for (int i = 0; i < len; i++) {
            ans[i] = rand.nextInt(maxValue + 1) - rand.nextInt(maxValue + 1);
        }
        return ans;
    }

    private static int[] copy(int[] origin) {
        if (origin == null)
            return null;
        int[] res = new int[origin.length];
        for (int i = 0; i < origin.length; i++) {
            res[i] = origin[i];
        }
        return res;
    }

    private static boolean equals(int[] origin, int[] newArray) {
        if (origin == null && newArray == null)
            return true;
        if (origin != null && newArray != null) {
            if (origin.length != newArray.length)
                return false;
            for (int i = 0; i < origin.length; i++) {
                if (origin[i] != newArray[i])
                    return false;
            }
            return true;
        }
        return false;
    }

    private static void sort(int[] arr) {
        if (arr == null || arr.length < 2)
            return;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length - 2; j++) {
                if (arr[i] > arr[j])
                    swap(arr, i, j);
            }
        }
    }

    private static void swap(int[] arr, int x, int y) {
        int tmp = arr[x];
        arr[x] = arr[y];
        arr[y] = tmp;
    }

    public static void print(int[] arr) {
        if (arr == null || arr.length == 0)
            return;
        System.out.print("{");
        for (int i = 0; i < arr.length - 1; i++) {
            System.out.print(arr[i] + ",");
        }
        System.out.println("\b}");
    }


    public static void main(String[] args) {
//        int[] arr =new int[] {2,5,3,8,9,7,3,5,7,0} ;
//        sort(arr);
//        System.out.println(Arrays.toString(arr));
        int times = 500_000;
        int maxLen = 100;
        int maxValue = 1000;
        System.out.println(">>>");
        for (int i = 0; i < times; i++) {
            int[] arr = randomArray(maxLen, maxValue);
            int[] cpArr = copy(arr);
            int res = bigger(arr);
            int res2 = comparator(cpArr);
            if (res != res2) {
                System.out.println("Ops!");
                print(arr);
                print(cpArr);
                break;
            }
        }
        System.out.println("<<<");
    }

}
