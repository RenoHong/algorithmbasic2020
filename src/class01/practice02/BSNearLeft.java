package class01.practice02;

public class BSNearLeft {
    //在arr上，找满足>=value的最左位置
    public static int find(int[] arr, int value) {
        if (arr == null || arr.length == 0)
            return -1;
        int l = 0;
        int r = arr.length - 1;
        int index = -1;
        while (l <= r) {
            int m = l + ((r - l) >> 1);
            if (arr[m] >= value) {
                index = m;
                r = m - 1;
            } else {
                l = m + 1;
            }
        }
        return index;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{1,2,3,4,5,6,7,8,9,10,11,23,56,80,700};
        int res = find(arr, 3) ;
        System.out.println(res) ;
    }

}
