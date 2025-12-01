package class18.practice02;

public class CardsInLine {

    public static int ways1(int[] arr){
        if(arr == null || arr.length ==0)
            return 0 ; 
        return Math.max(f1(arr, 0, arr.length-1), g1(arr, 0, arr.length -1));
    }

    private static int f1(int[] arr, int l, int r){
        if(l==r){
            return arr[l];
        }
        return Math.max(arr[l] + g1(arr, l+1, r), arr[r]+ g1(arr, l, r-1));
    }
    private static int g1(int[] arr, int l, int r){
        if(l==r){
            return 0;
        }
        return Math.min(f1(arr, l +1, r), f1(arr, l, r-1));
    }

    public static int ways2(int[] arr){
        if(arr == null || arr.length ==0)
            return 0 ; 
        int N = arr.length ; 
        int[][] fmap = new int[N][N];
        int[][] gmap = new int[N][N] ;

        for(int i =0 ; i< N; i++){
            for(int j=0;j<N; j++){
                fmap[i][j] = gmap[i][j] = -1;
            }
        }
        return Math.max(f2(arr, 0, N-1, fmap, gmap), g2(arr, 0, N-1, fmap, gmap)) ;
    }

    private static int f2(int[] arr, int l, int r, int[][] fmap, int[][] gmap){
        if(fmap[l][r]!=-1){
            return fmap[l][r] ;
        }
        int ans =0;
        if(l == r){
            return arr[l];
        }
        int p1 = arr[l] + g2(arr, l+1, r, fmap, gmap) ;
        int p2 = arr[r] + g2(arr, l, r-1, fmap, gmap) ;
        ans = Math.max(p1, p2) ;
        fmap[l][r] = ans;
        return ans;
    }

    public static int ways3(int[] arr){
        if(arr == null || arr.length == 0){
            return 0 ; 
        }
        int N = arr.length ;
        int[][] fmap = new int[N][N];
        int[][] gmap = new int[N][N];

        for(int i =0 ; i< N; i++){
            fmap[i][i] = arr[i] ;
            gmap[i][i] = 0; 
        }

        for(int i = 1; i < N; i++){ 
            int col = i ;
            int row = 0 ;
            while(col < N){
                fmap[row][col] = Math.max(arr[row] + gmap[row+1][col], 
                                arr[col] + gmap[row][col -1]);
                gmap[row][col] = Math.min(fmap[row+1][col], fmap[row][col-1]) ;
                row++;
                col++ ;
            }
        } 
        return Math.max(fmap[0][N-1], gmap[0][N-1]);
    }


    private static int g2(int[] arr, int l, int r, int[][] fmap, int[][] gmap){
        if(gmap[l][r]!=-1){
            return gmap[l][r];
        }
        int ans =0 ;
        if(l!=r){
            int p1 = f2(arr, l+1, r, fmap, gmap) ;
            int p2 = f2(arr, l, r-1, fmap, gmap) ;
            ans = Math.min(p1, p2) ;
            gmap[l][r] = ans ;
        }
        return ans ;
    }

    public static void main(String[] args){
        // Test array with 13 cards
        int[] arr = {5, 7, 4, 5, 8, 1, 6, 0, 3, 4, 6, 1, 7, 9,2,3,12,100, 18,2,33,5,56,90};

        long ct = System.currentTimeMillis();
        System.out.println(ways1(arr));
        long ce = System.currentTimeMillis();

        System.out.println("Time spend: " +  (ce -ct)) ; 

        ct = System.currentTimeMillis();
        System.out.println(ways2(arr));
        ce = System.currentTimeMillis();
        
        System.out.println("Time spend: " +  (ce -ct)) ; 

        ct = System.currentTimeMillis();
        System.out.println(ways3(arr));
        ce = System.currentTimeMillis();
        
        System.out.println("Time spend: " +  (ce -ct)) ; 
    }

}
