package class11.practice02;

public class PaperFolding {

    public static void printAllFolds(int  n){
        process(1, n, true);
    }

    private static void process(int i, int n, boolean upDown){
        if(i > n) return ;
        process(i +1, n, true) ;
        System.out.print(upDown? "凹 ":"凸 ");
        process(i+1, n, false) ;
    }

    public static void main(String[] args){
        printAllFolds(4) ;
    }

}
