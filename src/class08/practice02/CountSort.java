package class08.practice02;

import java.util.Arrays;
import java.util.Random;

public class CountSort {

    public static void sort(int[] arr) {
        if (arr == null || arr.length < 2)
            return;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++)
            max = Math.max(max, arr[i]);

        int[] helper = new int[max + 1];
        for (int i = 0; i < arr.length; i++) {
            helper[arr[i]]++;
        }

        int i = 0;
        int j = 0;
        while (i < max + 1) {
            while (helper[i]-- > 0) {
                arr[j++] = i;
            }
            i++;
        }
    }

    public static void main(String[] args) {
        System.out.println(">>");
        for (int j = 0; j < 200; j++) {
            int[] arr = new int[200];
            for (int i = 0; i < 200; i++) {
                arr[i] = new Random().nextInt(200);
            }
            int[] cp = new int[arr.length];
            System.arraycopy(arr, 0, cp, 0, arr.length);
            sort(arr);
            Arrays.sort(cp);
            if (!Arrays.equals(arr, cp)) {
                System.out.println("Opps");
                System.out.println(Arrays.toString(arr));
                break;
            }
        }
        System.out.println("<<");
    }

}
