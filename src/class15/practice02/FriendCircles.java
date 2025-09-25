package class15.practice02;
import java.util.*;

public class FriendCircles {

    public static int findCircleNum(int[][] M){
        int N = M.length ;
        UnionFind uf = new UnionFind(N) ;
        for(int i =0 ; i< N; i++){
            for(int j = i+1; j< N; j++){
                 if(M[i][j] == 1){
                    uf.union(i,j);
                 }
            }
        }
        return uf.sets();
    }


    public static class UnionFind{
        private int[] parents;
        private int[] size;
        private int[] help ;
        private int sets ;

        public UnionFind(int N){
            parents = new int[N] ;
            size = new int[N];
            help = new int[N];
            sets = N ;

            for(int i =0; i<N; i++){
                parents[i] = i ;
                size[i] = 1 ;
            }
        }

        public int find(int i){
            int hi = 0 ; 
            while(i != parents[i]){
                help[hi++] = i ;
                i = parents[i];
            }

            for(hi--; hi>=0; hi--){
                parents[help[hi]] = i ;
            }
            return i ;

        }

        public void union(int i, int j){
            int p1 = find(i);
            int p2 = find(j);
            if(p1 != p2){
                if(size[p1] > size[p2]){
                    parents[p2] = p1 ;
                    size[p1] = size[p1]+size[p2]; 
                }else{
                    parents[p1] = p2 ;
                    size[p2] = size[p1] + size[p2] ; 
                }
                sets--;
            } 
        }

        public int sets(){
            return sets ;
        }

    }

    public static class UnionFind2{
 
        int sets ;

        HashMap<Integer, Integer> parents, size ;
        public UnionFind2(int N){ 
            parents = new HashMap<>() ;
            size = new HashMap<>();
            sets = N ; 
            for(int i = 0 ; i< N; i++){
                parents.put(i, i);
                size.put(i, 1);
            }
        }

        public int find(int i){
            int p = parents.get(i) ;
            Stack<Integer> stk = new Stack<>();
            while(p != i){
                stk.push(p);
                p = parents.get(p) ;
            }
            while(!stk.isEmpty()){
                parents.put(stk.pop(), p);
            }
            return p ;
        }

        public void union(int i, int j){
            int pi = find(i);
            int pj = find(j);

            if(pi != pj){
                int sizei = size.get(pi);
                int sizej = size.get(pj);
                if(sizei >= sizej){
                    parents.put(pj, pi) ;
                    size.put(pi, sizei + sizej) ; 
                }else{
                    parents.put(pi, pj);
                    size.put(pj, sizei+sizej);
                }
                sets--;
            }
             
        }

        public int size(){
            return sets ;
        } 
    }

    public static class UnionFind3{
        private int[] parents ; 
        private int[] help ;
        private int[] sz ;
        private int sets ;

        public int sets(){
            return sets ;
        }

        public UnionFind3(int N){
            parents = new int[N] ;
            help = new int[N] ;
            sz = new int[N];
            sets = N ;
            for(int i=0; i< N; i++){
                sz[i] = 1;
                parents[i] = i ;
            }
        }

        public int find(int i){
            int index = 0 ; 
            // BUG: Should start with i, not parents[i]
            while(i != parents[i]){
                help[index++] = i ;
                i = parents[i] ;
            }

            // Path compression
            for(index--; index >= 0; index--){
                parents[help[index]] = i ; 
            }

            return i ;
        }

        public void union(int i, int j){
            int pi = find(i);
            int pj = find(j);

            if(pi != pj){
                // BUG: Should use sz[pi] and sz[pj], not sz[i] and sz[j]
                int szi = sz[pi] ;
                int szj = sz[pj] ;
                if(szi >= szj){
                    parents[pj] = pi ;
                    sz[pi] += szj ; // BUG: Was sz[i]
                }else{
                    parents[pi] = pj ;
                    sz[pj] += szi ; // BUG: Was sz[j]
                }
                sets--;
            }
        }
    }


}
