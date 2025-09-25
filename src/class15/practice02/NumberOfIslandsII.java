
package class15.practice02;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NumberOfIslandsII {

    public static List<Integer> numberOfIsland21(int m, int n, int[][]positions){
        UnionFind1 uf = new UnionFind1(m, n);
        List<Integer> res = new ArrayList<>();
        for(int[] p: positions){
            res.add(uf.connect(p[0], p[1]));
        }
        return res ;
    }


    public static class UnionFind1{
        int[] parents ;
        int[] help ;
        int[] size ;
        int sets ;
        final private int rows;
        final private int cols ; 

        public UnionFind1(int rs, int cs){
            this.rows = rs ;
            this.cols = cs ;

            int len = rows * cols ;
            parents = new int[len] ;
            size = new int[len] ;
            help = new int[len] ;  
        }

        private int index(int row, int col){
            return row * cols + col ;
        }
        
        public int sets(){
            return sets ;
        }

        public int find(int i){
            int index =0 ;
            while(i != parents[i]){
                help[index++] = i ;
                i = parents[i];
            }
            for(index--; index >=0 ; index--){
                parents[help[index]] = i;
            }
            return i ;
        }

        public void union(int r1, int c1, int r2, int c2){
            if(r1 <0|| r2<0||r1>=rows||r2>=rows ||c1<0||c2<0||c1>=cols||c2>=cols){
                return ;
            }
            int i1 = index(r1, c1) ;
            int i2 = index(r2, c2) ;
            if(size[i1] == 0 || size[i2]==0)
                return ;
            int p1 = find(i1);
            int p2 = find(i2);

            if(p1 != p2){
                int sz1 = size[p1] ;
                int sz2 = size[p2] ;

                int big = sz1 >= sz2? p1: p2;
                int small = big == p1?p2: p1;
                parents[small] = big ;
                size[big] = sz1+sz2 ;
                sets --;
            }

        }

        public int connect(int r, int c){
            if(r <0||c<0||r>=rows||c>=cols){
                return sets;
            }
            int i = index(r, c) ;
            if(size[i] == 0){
                parents[i] = i ; 
                size[i] = 1;
                sets++;
                union(r-1, c, r, c);
                union(r, c-1, r, c);
                union(r+1, c, r, c);
                union(r, c+1, r, c);
            }

            return sets ;
        }
    }

    public static List<Integer> numberOfIsland22(int m, int n, int[][] positions){
        List<Integer> res = new ArrayList<>();
        UnionFind2 uf = new UnionFind2();
        for(int[] p:positions){
            res.add(uf.connect(p[0], p[1]));
        }
        return res ;
    }

    public static class UnionFind2{
        HashMap<String, String> parents ;
        HashMap<String, Integer> size ;
        List<String> help ;
        int sets ;

        public UnionFind2(){
            parents = new HashMap<>();
            size = new HashMap<>();
            help = new ArrayList<>() ;
            sets = 0 ; 
        }

        private String find(String current){
            while(!current.equals(parents.get(current))){
                help.add(current);
                current = parents.get(current) ;
            }
            for(String item: help){
                parents.put(item, current);
            }
            help.clear() ;
            return current ;
        }

        private void union(String s1, String s2){
            if(parents.containsKey(s1) && parents.containsKey(s2)){
                String p1 = find(s1) ;
                String p2 = find(s2) ;
                if(!p1.equals(p2)){
                    int sz1 = size.get(p1);
                    int sz2 = size.get(p2);
                    String big = sz1 >= sz2 ? p1:p2 ;
                    String small = big.equals(p1)? p2: p1;
                    parents.put(small, big);
                    size.put(big, sz1+sz2);
                    sets--;
                }
            }
        }
        public int connect(int r, int c){
            String key =r + "_" + c ;
            if(!size.containsKey(key)){
                parents.put(key, key) ;
                size.put(key, 1) ;
                sets++ ;

                String up = (r-1) + "_" + c ;
                String left = r + "_" + (c-1) ;
                String down = (r+1) +"_" + c ;
                String right = r +"_" + (c+1) ;
                union(key, up);
                union(key, left);
                union(key, down) ;
                union(key, right);
            }

            return sets ;
        }

    }

}
