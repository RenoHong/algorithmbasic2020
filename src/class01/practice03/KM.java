package class01.practice03;
import java.util.HashMap;

public class KM {
    
    public static int onlyKTimes(int[] arr, int K, int M){
        if(arr == null || arr.length == 0)
            return -1 ;

        HashMap<Integer, Integer> bits = new HashMap<>() ;
        int ans = 0;
        
        // Fill map 
        int value = 1 ;
        for(int i = 0 ; i< 32; i++){
            bits.put(value, 0);
            value <<= 1 ;
        }

        for(int i =0 ;i< arr.length; i++){
             int num = arr[i] ; 
             while(num != 0){ 
                int rightOne =  num & (-num) ;
                int count = bits.get(rightOne) + 1 ;
                bits.put(rightOne, count);
                num ^= rightOne ;
             }
        }

        for(int i =0 ; i< 32; i++){
            int key = 1 << i ;
            int count = bits.get(key);
            if(count % M != 0 && count % M == K ){
                ans |= key ;
            }else{
                return -1;
            }
        }

        if(ans == 0){
            int count = 0 ;
            for(int i : arr){
                if(i == 0) count ++ ;
            }
            if(count != K) ans = -1 ;
        }

        return ans ;

    }

}
