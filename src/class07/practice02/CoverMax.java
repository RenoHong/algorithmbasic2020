package class07.practice02;
import java.util.PriorityQueue;

import java.util.Arrays ;
import java.util.Comparator;

public class CoverMax {

    public static int maxCover1(int[][] lines){

        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for(int i=0; i< lines.length ; i++){
            int[] line = lines[i] ;
            min = Math.min(min, line[0]);
            max = Math.max(max, line[1]);
        }
        
        int cover = 0 ; 
        for(double s = min + 0.5; s <= max ; s+=1){
            int current =0 ; 
            for(int i =0 ; i < lines.length ;i++){
                if(lines[i][0] < s && lines[i][1] > s){
                    current++; 
                }
            }
            cover = Math.max(cover, current);
        }
        return cover ; 
    }

 

    public static class Line{
        int start, end ;
        public Line(int start, int end){
            this.start = start ;
            this.end = end ;
        }
    }


    public static int maxCover2(int[][] m){

        Line[] lines = new Line[m.length] ;
        for(int i =0; i< m.length; i++){
            lines[i] = new Line(m[i][0], m[i][1]);
        }

        PriorityQueue<Integer> heap = new PriorityQueue<>();

        Arrays.sort(lines, new StartComparator()) ;

        int max = 0 ;
        for(int i =0 ; i< lines.length; i++){
            while(!heap.isEmpty() && heap.peek() <= lines[i].start ){
                heap.poll(); 
            }
            
            heap.add(lines[i].end); 
            max = Math.max(max, heap.size());
        }
        return max ;
    }

    public static int maxCover3(int[][] m){
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        Arrays.sort(m , (o1, o2)-> o1[0] - o2[0]);
        int max =0 ;
        for(int[] line: m){
            while(!heap.isEmpty() && heap.peek() <= line[0]){
                heap.poll();
            }
            heap.add(line[1]) ;
            max = Math.max(heap.size(), max);
        }
        return max ;
    }

    public static int[][] generateLines(int N, int L, int R) {
        int size = (int) (Math.random() * N) + 1;
        int[][] ans = new int[size][2];
        for (int i = 0; i < size; i++) {
            int a = L + (int) (Math.random() * (R - L + 1));
            int b = L + (int) (Math.random() * (R - L + 1));
            if (a == b) {
                b = a + 1;
            }
            ans[i][0] = Math.min(a, b);
            ans[i][1] = Math.max(a, b);
        }
        return ans;
    }

    public static void main(String[] args) {
 
        System.out.println("test begin");
        int N = 100;
        int L = 0;
        int R = 200;
        int testTimes = 200000;
        for (int i = 0; i < testTimes; i++) {
            int[][] lines = generateLines(N, L, R);
            int ans1 = maxCover1(lines);
            int ans2 = maxCover2(lines);
            int ans3 = maxCover3(lines);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println( String.format("%d %d %d" ,ans1, ans2, ans3));
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("test end");
    }


    public static class StartComparator implements Comparator<Line>{
        @Override
        public int compare(Line l1, Line l2){
            return l1.start - l2.start ;
        }

    }
    
}
