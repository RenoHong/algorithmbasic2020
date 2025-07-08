package class06.practice01;

import util.Tester;

public class Heap {

    final int limit;
    int size = 0;
    int[] arr;

    public Heap(int limit) {
        this.limit = limit;
        arr = new int[limit];
        size = 0;
    }

    public static void main(String[] args) {
        int value = 1000;
        int limit = 100;
        for (int i = 0; i < Tester.testTimes; i++) {
            int curLimit = (int) (Math.random() * limit) + 1;
            Heap h = new Heap(curLimit);
            RightHeap r = new RightHeap(curLimit);
            int curOptTimes = (int) (Math.random() * limit);
            for (int j = 0; j < curOptTimes; j++) {
                if (h.isEmpty() != r.isEmpty() ||
                        h.isFull() != r.isFull()) {
                    System.out.println("Oops!");
                }
                if (h.isEmpty()) {
                    int curValue = (int) (Math.random() * value);
                    h.push(curValue);
                    r.push(curValue);
                } else if (h.isFull()) {
                    if (r.pop() != h.pop())
                        System.out.println("Oops!");
                } else {
                    if (Math.random() < 0.5) {
                        int curValue = (int) (Math.random() * value);
                        r.push(curValue);
                        h.push(curValue);
                    } else {
                        if (r.pop() != h.pop())
                            System.out.println("Oops");
                    }
                }
            }
        }

        System.out.println("Test completed!");
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return limit == size;
    }

    public void push(int i) {
        if (isFull()) {
            throw new RuntimeException("The heap is full!");
        }
        arr[size] = i;
        heapInsert(size++);
    }

    private void swap(int i, int j) {
        int t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

    private void heapInsert(int index) {
        while (arr[index] > arr[(index - 1) / 2]) {
            swap(index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    public int pop() {
        if (isEmpty())
            throw new RuntimeException("The heap is empty!");
        int res = arr[0];
        swap(0, --size);
        heapify(0);
        return res;
    }

    private void heapify(int index) {
        int left = (index << 1) + 1;
        while (left < size) {
            int largestIndex = left + 1 < size && arr[left + 1] > arr[left] ? left + 1 : left;
            largestIndex = arr[largestIndex] > arr[index] ? largestIndex : index;
            if (largestIndex == index) {
                break;
            }
            swap(largestIndex, index);
            index = largestIndex;
            left = (index << 1) + 1;
        }
    }

    public static class RightHeap {

        int[] arr;
        int size = 0;
        int limit = 0;

        public RightHeap(int limit) {
            this.limit = limit;
            arr = new int[limit];
            size = 0;
        }

        public boolean isFull() {
            return size == limit;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public void push(int value) {
            if (isFull()) {
                throw new RuntimeException("Array is full!");
            }
            arr[size++] = value;
        }

        public int pop() {
            int max = 0;
            for (int i = 0; i < size; i++) {
                max = arr[i] > arr[max] ? i : max;
            }
            int ans = arr[max];
            arr[max] = arr[--size];
            return ans;
        }

    }


}