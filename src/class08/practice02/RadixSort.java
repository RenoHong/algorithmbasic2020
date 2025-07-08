package class08.practice02;
import java.util.Arrays;
public class RadixSort {

    public static void main(String[] args){
        int[] arr = new int[]{6,2,3,4,1,0,9,5,6,9,29,233};
        new RadixSort().radixSort(arr);
        System.out.println(Arrays.toString(arr));
    }
    
    public void radixSort(int[] arr){
        radixSort(arr, 0, arr.length -1, maxbits(arr));
    }

    public int getDigit(int num, int d){
       return num / ((int) Math.pow(10, d-1)) % 10;
    }
    public void radixSort(int[] arr, int l, int r, int digits){
        if(arr == null || arr.length <2 || l == r)
            return ;
       
        int[] helper = new int[r - l + 1];
        int i =0 ;
        int j =0 ;
        
        for(int d =1 ; d <= digits ; d++){
            int[] buckets = new int[10];

            for(i = l ; i <= r; i++){
                j = getDigit(arr[i], d) ;
                buckets[j]++ ;
            }

            for(i =1; i< buckets.length ; i++){
                buckets[i] = buckets[i -1] + buckets[i];
            }
            
            for(i = r; i >= l; i--){
                j= getDigit(arr[i], d);
                helper[ buckets[j]-1 ] = arr[i] ;
                buckets[j] --;
            }

            for(i =l, j=0; i<= r; i++, j++){
                arr[i] = helper[j];
            }
        }
    }


    public  int maxbits(int[] arr){
        int ans =0 ;
        int max = Integer.MIN_VALUE;
        for(int i =0 ;i < arr.length ; i++){
            max = Math.max(max, arr[i]);
        }

        while(max!=0){
            ans++;
            max /= 10;
        }
        return ans ;
    }

}
    