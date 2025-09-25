package class14.practice02;
import java.util.Arrays;
import java.util.Comparator;
public class BestArrange {

    public static class Program {
        public int start, end ;
        public Program(int s, int e){
            start =s ;
            end =e ;
        }
    }
    
    public static int bestArrange1(Program[] programs){
        if(programs == null || programs.length ==0 ){
            return 0 ;
        }
        return process(programs, 0, 0) ;
    }

    // private static int process(Program[] programs, int done, int timeline){
    //     if(programs.length ==0){
    //         return done ;
    //     }

    //     int max = done ; 
    //     for(int i = 0; i< programs.length ; i++){
    //         if(programs[i].start >= timeline){
    //             max = Math.max(max, process(except(programs, i), done + 1, programs[i].end));
    //         }
    //     }

    //     return max ;
    // }

   public static int process(Program[] programs, int done, int timeLine) {
        // If no meetings left, return the number done so far
        if (programs.length == 0) {
            return done;
        }
        // There are still meetings left
        int max = done; // Track the maximum number of meetings
        // Try to arrange each meeting as the next one
        for (int i = 0; i < programs.length; i++) {
            // If the meeting can be scheduled after the current timeline
            if (programs[i].start >= timeLine) {
                Program[] next = copyButExcept(programs, i); // Remove the scheduled meeting
                // This line is necessary. Although done+1 is usually greater than done,
                // max will also be compared with the result of recursion, which may be even greater.
                // So we must use Math.max to compare.
                max = Math.max(max, process(next, done + 1, programs[i].end));
            }
        }
        return max; // Return the maximum found
    }

    private static Program[] copyButExcept(Program[] programs, int i){
        Program[] res = new Program[programs.length - 1];
        int index = 0;
        for(int j = 0; j < programs.length; j++){
            if(j != i){
                res[index++] = programs[j];
            }
        }
        return res;
    }

    // public static int bestArrange2(Program[] programs){
    //     if(programs == null || programs.length == 0)
    //         return 0; 
    //     Arrays.sort(programs, (p1, p2)-> Integer.compare(p1.end, p2.end)) ;
    //     int result = 0 ; 
    //     int timeline =0 ; 
    //     for(int i=0; i<programs.length; i++){
    //         if(programs[i].start >= timeline){
    //             timeline = programs[i].end ;
    //             result ++ ;
    //         }
    //     }

    //     return result ;
    // }

    public static int bestArrange2(Program[] programs) {
        // Sort meetings by end time (earliest first)
        Arrays.sort(programs, new ProgramComparator());
        int timeLine = 0; // Current time
        int result = 0;   // Number of meetings arranged
        // Traverse each meeting in order of end time
        for (int i = 0; i < programs.length; i++) {
            // If the meeting can be scheduled after the current timeline
            if (timeLine <= programs[i].start) {
                result++; // Arrange this meeting
                timeLine = programs[i].end; // Move timeline to the end of this meeting
            }
        }
        return result; // Return the total number arranged
    }

    // public static Program[] generateRandomPrograms(int maxValue, int maxLen){
    //     Random rand = new Random(); 
    //     int len = rand.nextInt(maxLen) + 1 ; 

    //     Program[] res = new Program[len] ;
    //     for(int i = 0 ; i< len; i++){
    //         int start = rand.nextInt(maxValue);
    //         int end =  rand.nextInt(maxValue) ;
    //         if(start == end ) 
    //             end += 1 ;
    //         res[i] = new Program(Math.min(start, end), Math.max(start, end)) ;
    //     }
    //     return res ;

    // } 


        public static Program[] generatePrograms(int programSize, int timeMax) {
        Program[] ans = new Program[(int) (Math.random() * (programSize + 1))];
        for (int i = 0; i < ans.length; i++) {
            int r1 = (int) (Math.random() * (timeMax + 1));
            int r2 = (int) (Math.random() * (timeMax + 1));
            if (r1 == r2) {
                ans[i] = new Program(r1, r1 + 1); // Ensure end > start
            } else {
                ans[i] = new Program(Math.min(r1, r2), Math.max(r1, r2));
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int programSize = 12; // Maximum number of meetings
        int timeMax = 20;     // Maximum time value
        int timeTimes = 1000000; // Number of test cases
        for (int i = 0; i < timeTimes; i++) {
            Program[] programs = generatePrograms(programSize, timeMax);
            // Compare brute-force and greedy results
            if (bestArrange1(programs) != bestArrange2(programs)) {
                System.out.println("Oops!"); // Print if results differ
            }
        }
        System.out.println("finish!"); // All tests done
    }
    // public static void main(String[] args){
    //     int testTimes = 100_000 ; 
    //     int maxValue =20 ;
    //     int maxLen = 10 ;
    //     System.out.println("{") ;
    //     for(int i=0; i< testTimes; i++){
    //         Program[] programs = generateRandomPrograms(maxValue, maxLen) ;
    //         int n1 = bestArrange1(programs) ;
    //         int n2 = bestArrange2(programs) ;
    //         if(n1!=n2){
    //             System.out.println("\tOops " + n1 + ", " + n2) ;
    //             break ;
    //         }else{
    //             System.out.println("OK!");
    //         }
    //     }
    //     System.out.println("}") ;
    // }


    public static class ProgramComparator implements Comparator<Program>{
        @Override
        public int compare(Program p1, Program p2){
            return p2.end - p1.end ;
        }
    }

 
}
