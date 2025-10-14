package class16.practice02;

import java.util.*;

public class NetworkDelayTime {

    public static int networkDelayTime1(int[][] times, int n, int k) {

        ArrayList<ArrayList<int[]>> nexts = new ArrayList<>(); 
        
        for(int i =0 ; i<= n; i++){
             nexts.add(new ArrayList<int[]>()) ;
        }
        for(int[] delay : times){
            nexts.get(delay[0]).add(new int[]{ delay[1], delay[2]});
        }

        PriorityQueue<int[]> heap = new PriorityQueue<>((n1, n2)-> n1[1] - n2[1]);
        heap.add(new int[]{k, 0});

        boolean[] visited = new boolean[n + 1] ;
        int num = 0 ;
        int max = 0 ;

        while(!heap.isEmpty() && num < n){
            int[] record = heap.poll() ;
            int current = record[0] ;
            int delay = record[1];

            if(visited[current]){
                continue ;
            }
            visited[current] = true ;
            num++ ;
            max = Math.max(max, delay) ;

            for(int[] next : nexts.get(current)){
                if(!visited[next[0]]){
                    heap.add(new int[]{next[0], next[1] + delay});
                }
            }

        }

        return num < n ? -1: max;

    }

    public static int networkDelayTime2(int[][] times, int n, int k) {

        ArrayList<ArrayList<int[]>>  nexts = new ArrayList<>();
        for(int i =0; i<n; i++){
            nexts.add(new ArrayList<>());
        }

        for(int[] delay: times){
            nexts.get(delay[0]).add(new int[]{delay[1], delay[2]});
        }
        
        Heap heap = new Heap(n);
        heap.add(k, 0);

        int num =0 ;
        int max = 0;;
        while(!heap.isEmpty()){
            int[] record = heap.poll();
            int current = record[0];
            int delay = record[1];

            num++;
            max = Math.max(max, delay) ;

            for(int[] next: nexts.get(current)){
                heap.add(next[0], delay+next[1]);
            } 
        }

        return num > n?max: -1;

    }
    

    public static class Heap{

        int[][] heap ;
        int[] hIndex ;
        boolean[] visited ;
        int size ; 

        public Heap(int n){
            heap = new int[n+1][2];
            visited = new boolean[n+1] ;
            hIndex = new int[n+1] ;
            Arrays.fill(hIndex, -1) ;
            size =0;
        }

        public void add(int current, int delay){
            if(visited[current]){
                return ;
            }
            if(hIndex[current] == -1){
                hIndex[current] = size ;
                heap[size][0] = current ;
                heap[size][1] = delay ;
                heapInsert(size++) ;
            }else{
                int i = hIndex[current] ;
                if(delay <= heap[i][1]){
                    heap[i][1] = delay ;
                    heapInsert(i) ;
                }
            }
        }

        public void heapInsert(int i){
            int parent = (i -1) /2 ;
            while(heap[i][1] < heap[parent][1]){
                swap(i, parent);
                i = parent ;
                parent = (i-1) /2 ;
            }
        }

        public void heapify(int i){
            int left = i * 2 + 1;
            while(left < size){
                int smallest = left + 1 < size && heap[left+1][1] < heap[left][1]?
                    left +1 :
                    left ;

                smallest = heap[i][1] < heap[smallest][1]? i: smallest ;
                if(smallest == i){
                    break ;
                }
                swap(smallest, i) ;
                i = smallest ;
                left = i * 2 + 1 ;
            }
        }

        public int[] poll(){
            int[] res = heap[0] ;
            visited[res[0]] = true ;
            hIndex[res[0]] = -1;
            swap(0, size-1) ;
            size --; 
            heapify(0); 
            return res ;
        }

        public boolean isEmpty(){
            return size == 0 ; 
        }

        public void swap(int i, int j){
            int[] o1 = heap[i] ;
            int[] o2 = heap[j] ;
            heap[j] = o1 ;
            heap[i] = o2 ;

            int o1hi = hIndex[o1[0]] ;
            int o2hi = hIndex[o2[0]] ; 
             hIndex[o2[0]] = o1hi ;
             hIndex[o1[0]] = o2hi ;
        }

    }

}
