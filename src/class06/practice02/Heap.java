package class06.practice02;


import java.util.Arrays;
import java.util.PriorityQueue;

public class Heap {

    public static void main(String[] args) {
        MyMaxHeap maxHeap = new MyMaxHeap(20);
        PriorityQueue<Integer> pq = new PriorityQueue<>(20, (o1, o2) -> o2 - o1);
        for (int i = 0; i < 20; i++) {
            maxHeap.push(i);
            pq.add(i);
        }
        for (int i = 0; i < 5; i++) {
            maxHeap.pop();
            pq.poll();
        }

        for (int i = 0; i < 4; i++) {
            maxHeap.push(i + 100);
            pq.add(i + 100);
        }

        System.out.println(maxHeap);
        System.out.println(pq);

        System.out.println(maxHeap.size());
        System.out.println(pq.size());

    }

    public static class MyMaxHeap {
        int[] heap;
        int heapSize;
        int limit;

        public MyMaxHeap(int limit) {
            heap = new int[limit];
            heapSize = 0;
            this.limit = limit;
        }

        public int size() {
            return heapSize;
        }

        public boolean isFull() {
            return heapSize == limit;
        }

        public boolean isEmpty() {
            return heapSize == 0;
        }

        @Override
        public String toString() {
            return Arrays.toString(heap);
        }

        private void swap(int x, int y) {
            int temp = heap[x];
            heap[x] = heap[y];
            heap[y] = temp;
        }

        private void heapifyUp(int index) {
            int newValue = heap[index];
            while (newValue > heap[(index - 1) / 2]) {
                swap(index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }

        private void heapifyDown(int index) {
            int newValue = heap[index];
            int left = (index << 1) + 1;
            while (left < heapSize) {
                int greaterIndex = left + 1 < heapSize && heap[left + 1] > heap[left] ? left + 1 : left;
                greaterIndex = heap[greaterIndex] > newValue ? greaterIndex : index;

                if (greaterIndex == index)
                    break;

                swap(greaterIndex, index);
                index = greaterIndex;
                left = (index << 1) + 1;
            }

        }

        private void push(int value) {
            if (isFull())
                throw new RuntimeException("Full!");
            heap[heapSize] = value;
            heapifyUp(heapSize++);
        }

        private int pop() {
            if (isEmpty())
                throw new RuntimeException("Empty!");
            int poping = heap[0];
            swap(0, --heapSize);
            heapifyDown(0);
            return poping;
        }


    }
}
