package class15.practice02;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
public class NumberOfIslands {

    public static int numberOfIslands3(char[][] board){
        int res =0 ; 
        for(int i =0 ;i< board.length; i++){
            for(int j=0; j<board[i].length ; j++){
                res += infect(board, i,j);
            }
        }
        return res ;
    }

    private static int infect(char[][] board, int row, int col){
        if(row < 0 || row >= board.length || col < 0 || col >= board[row].length){
            return  0;
        }
        if(board[row][col] == '1'){
            board[row][col] = '2' ;
            infect(board, row-1, col);
            infect(board, row+1, col);
            infect(board, row, col-1);
            infect(board, row, col+1);
            return 1 ;
        }
        return 0 ;
    }

    public static int numberOfIslands1(char[][] board){
        if(board == null || board.length < 1 || board[0].length < 1) 
            return 0; 

        int rows = board.length;
        int cols = board[0].length;
        Dot[][] dots = new Dot[rows][cols];

        List<Dot> list = new ArrayList<>();
        for(int r = 0; r < rows; r++){
            for(int c = 0; c < cols; c++){
                if(board[r][c] == '1'){
                    dots[r][c] = new Dot();
                    list.add(dots[r][c]);
                }
            }
        }
        
        UnionFind1<Dot> uf = new UnionFind1<>(list); 

        // Check vertical connections in first column
        for(int r = 1; r < rows; r++){
            if(board[r][0] == '1' && board[r-1][0] == '1'){
                uf.union(dots[r][0], dots[r-1][0]);
            }  
        }

        // Check horizontal connections in first row
        for(int c = 1; c < cols; c++){
            if(board[0][c-1] == '1' && board[0][c] == '1'){
                uf.union(dots[0][c-1], dots[0][c]);
            }
        }

        // Check all other connections
        for(int r = 1; r < rows; r++){
            for(int c = 1; c < cols; c++){
                if(board[r][c] == '1'){
                    if(board[r-1][c] == '1'){
                        uf.union(dots[r][c], dots[r-1][c]);
                    }
                    if(board[r][c-1] == '1'){
                        uf.union(dots[r][c], dots[r][c-1]); 
                    }
                }
            }
        } 

        return uf.sets();
    }

    public static class Dot{

    }
    public static class Node<V>{
        V value ;
        public Node(V v){
            value = v ;
        }
    }

    public static class UnionFind1<V>{
        private int sets ; 
        HashMap<Node<V>, Node<V>> parentMap ;
        HashMap<Node<V>, Integer> sizeMap ;
        HashMap<V, Node<V>> nodes ;

        public UnionFind1(List<V> list){
            sets = list.size();
            parentMap = new HashMap<>();
            sizeMap = new HashMap<>();
            nodes = new HashMap<>();
            for(V v : list){
                Node<V> n = new Node<V>(v);
                parentMap.put(n, n) ;
                sizeMap.put(n, 1) ;
                nodes.put(v, n);
            }
        }

        public Node<V> findParent(Node<V> n){
            List<Node<V>> list = new ArrayList<>() ;
            Node<V> current = n ;
            while(current != parentMap.get(current)){
                list.add(current);
                current = parentMap.get(current);
            }
            for(Node<V> c: list){
                parentMap.put(c, current);
            }
            return current;
        }

        public void union(V v1, V v2){
            Node<V> p1 = findParent(nodes.get(v1)) ;
            Node<V> p2 = findParent(nodes.get(v2)) ;
            if(!p1.equals(p2)){
                int sz1 = sizeMap.get(p1) ;
                int sz2 = sizeMap.get(p2) ;
                
                if(sz1 >= sz2){
                    parentMap.put(p2, p1);
                    sizeMap.put(p1, sz1 + sz2) ;
                }else{
                    parentMap.put(p1, p2);
                    sizeMap.put(p2, sz1 + sz2) ;
                }
                sets--;
            }
        }

        public int sets(){
            return sets ;
        }
    }

    public static int numberOfIslands2(char[][] board){
        if(board == null || board.length == 0 || board[0].length == 0)
            return 0;
        
        UnionFind2 uf = new UnionFind2(board);
        int rows = board.length;
        int cols = board[0].length;
       
        // Check vertical connections in first column
        for(int r = 1; r < rows; r++){
            if(board[r-1][0] == '1' && board[r][0] == '1'){
                uf.union(r-1, 0, r, 0);
            }
        }

        // Check horizontal connections in first row
        for(int c = 1; c < cols; c++){
            if(board[0][c-1] == '1' && board[0][c] == '1'){
                uf.union(0, c-1, 0, c);
            }
        }

        // Check all other connections
        for(int r = 1; r < rows; r++){
            for(int c = 1; c < cols; c++){
                if(board[r][c] == '1'){
                    if(board[r-1][c] == '1'){
                        uf.union(r, c, r-1, c);
                    }
                    if(board[r][c-1] == '1'){
                        uf.union(r, c, r, c-1);  // Fixed parameter order
                    }
                }
            }
        }
        return uf.sets();
    }

    public static class UnionFind2{
        int[] parents;
        int[] help;
        int[] size;
        int cols, sets;

        public UnionFind2(char[][] board){
            int rows = board.length;
            cols = board[0].length;
            
            int len = cols * rows;
            parents = new int[len];
            help = new int[len]; // Should be same size as parents for worst case
            size = new int[len];

            for(int r = 0; r < rows; r++){
                for(int c = 0; c < cols; c++){
                    if(board[r][c] == '1'){
                        int idx = index(r, c);
                        parents[idx] = idx;
                        size[idx] = 1;
                        sets++;
                    }
                }
            }
        }

        public int find(int n){
            int current = n;
            int i = 0;
            while(current != parents[current]){
                help[i++] = current;
                current = parents[current];
            }
            while(--i >= 0){
                parents[help[i]] = current;
            }
            return current;
        }

        public void union(int r1, int c1, int r2, int c2){
            int index1 = index(r1, c1);
            int index2 = index(r2, c2);
            int p1 = find(index1);
            int p2 = find(index2);
            if(p1 != p2){
                int s1 = size[p1];
                int s2 = size[p2];
                if(s1 >= s2){
                    parents[p2] = p1;
                    size[p1] = s1 + s2;
                }else{
                    parents[p1] = p2;
                    size[p2] = s1 + s2;
                }
                sets--;
            }
        }

        // Missing method - add this
        public int sets(){
            return sets;
        }

        private int index(int r, int c){
            return r * cols + c;
        }
    }


    public static char[][] generateRandomMatrix(int rows, int cols){
        char[][] matrix = new char[rows][cols];
        Random rand = new Random() ;

        for(int r =0 ; r< rows ; r++){
            for(int c =0 ; c < cols ; c++){
                char cr = rand.nextDouble() > 0.5 ?'1':'0';
                matrix[r][c] = cr ;
            }
        }
        return matrix ;
    }

    public static char[][] copy(char[][] chars){
        if(chars == null || chars.length == 0 || chars[0].length ==0) return null ;
        int rows = chars.length ;
        int cols = chars[0].length ;
        char[][] res = new char[rows][cols] ;
        for(int r =0 ; r< rows; r++){
            for(int c = 0; c< cols; c++){
                res[r][c] = chars[r][c] ;
            }
        }

        return res ;
    }

    public static void main(String[] args){

        int rows = 5000 ;
        int cols = 10000 ;
        char[][] board = generateRandomMatrix(rows, cols);
        char[][] board3 = copy(board) ;
        char[][] board2 = copy(board) ;

        long st =  System.currentTimeMillis() ;
        int res = numberOfIslands1(board) ;
        long end = System.currentTimeMillis() ;
        System.out.printf("Using numberOfIslands1: %d, time taken %d\n", res, end - st) ;

        st = System.currentTimeMillis() ; 
        res = numberOfIslands2(board2) ;
        end = System.currentTimeMillis() ;
        System.out.printf("Using numberOfIslands2: %d, time taken %d\n", res, end - st) ;

        st = System.currentTimeMillis() ; 
        res = numberOfIslands3(board3) ;
        end = System.currentTimeMillis() ;
        System.out.printf("Using numberOfIslands2: %d, time taken %d\n", res, end - st) ;

    }

}
