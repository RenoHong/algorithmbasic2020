package class01.practice03;

public class EvenTimesOddTimes {
    
    public static void printOddTimesNum1(int[] arr){
        int eor = 0 ; 
        for(int i =0 ;i< arr.length ; i++){
            eor ^= arr[i] ;
        }
        
        System.out.println(eor) ;
    }

    public static void printOddTimesNum2(int[] arr){
        int eor =0 ; 
        for(int i =0 ; i< arr.length ; i++){
            eor ^= arr[i] ;
        }

        int rightOne = eor & (~eor + 1) ;
        int onlyOne =  0; 
        for(int i=0;i< arr.length;i++){
            if((rightOne & arr[i]) !=0){
                onlyOne ^= arr[i] ;
            }
        } 
        System.out.println(onlyOne + " " + (eor ^ onlyOne)) ;
        
    }

    public static void main(String[] args){
        int[] arr1 = {3, 3, 2, 3, 1, 1, 1, 3, 1, 1, 1};
        printOddTimesNum1(arr1);

        int[] arr2 = {4, 3, 4, 2, 2, 2, 4, 1, 1, 1, 3, 3, 1, 1, 1, 4, 2, 2};
        printOddTimesNum2(arr2);
    }
}
