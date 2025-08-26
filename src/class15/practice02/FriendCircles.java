package class15.practice02;

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

           while(hi-- >=0){
                parents[help[hi]]= i ;
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

}
