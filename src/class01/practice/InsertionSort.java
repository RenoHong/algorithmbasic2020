package class01.practice;

import java.util.Arrays;

public class InsertionSort {

    public static void sort(int[] arr){
        if (arr == null || arr.length <2) return ;

        for (int i = 1; i < arr.length; i++) {
             int key = arr[i];
             int j = i -1 ;
             //一旦j位置的值比 key 大，那就要把左边腾出来每次往左移。覆盖了j+1的位置。j+1 是i的位子也就是 key的位置。所以不怕值丢失。
             // j一定要先判断是否 >= 0 否者 arr[j]会越界。
             while (j >= 0 && key < arr[j] ) {
                 arr[j + 1] = arr[j];
                 j--;
             }
             arr[j+1] = key ;
        }

    }

    public static void main(String[] args) {
        int [] arr = {9,3,4,2,1,7,6,8} ;
        sort(arr);
        Arrays.stream(arr).forEach(System.out::print);
    }
}
