package class06;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * This class demonstrates heap data structures:
 * 1. Java's built-in PriorityQueue
 * 2. A custom max heap implementation
 * 3. A naive max heap implementation for validation
 */
public class Code02_Heap {

    public static void main(String[] args) {

        // Using Java's PriorityQueue with a custom comparator to create a max heap
        // (By default, PriorityQueue in Java is a min heap)
        PriorityQueue<Integer> heap = new PriorityQueue<>(new MyComparator());
        heap.add(5); // Add 5 to the heap
        heap.add(5); // Add another 5
        heap.add(5); // Add another 5
        heap.add(3); // Add 3
        // At this point, we have [5,5,5,3] with 5 as the max value
        System.out.println(heap.peek()); // Print the max element without removing it
        heap.add(7); // Add 7
        heap.add(0); // Add 0
        heap.add(7); // Add another 7
        heap.add(0); // Add another 0
        heap.add(7); // Add another 7
        heap.add(0); // Add another 0
        System.out.println(heap.peek()); // Print the max element (should be 7)

        // Remove and print all elements from the heap in descending order
        while (!heap.isEmpty()) {
            System.out.println(heap.poll());
        }

        // Test our custom heap implementation against the naive implementation
        int value = 1000; // Max random value
        int limit = 100;  // Max heap size
        int testTimes = 1000000; // Number of tests

        // Run multiple tests to ensure our implementation is correct
        for (int i = 0; i < testTimes; i++) {
            int curLimit = (int) (Math.random() * limit) + 1; // Random heap size limit
            MyMaxHeap my = new MyMaxHeap(curLimit); // Our optimized heap
            RightMaxHeap test = new RightMaxHeap(curLimit); // Naive implementation for validation
            int curOpTimes = (int) (Math.random() * limit); // Random number of operations

            // Perform random operations and verify both implementations behave the same
            for (int j = 0; j < curOpTimes; j++) {
                // Check if isEmpty() returns the same for both implementations
                if (my.isEmpty() != test.isEmpty()) {
                    System.out.println("Oops!");
                }
                // Check if isFull() returns the same for both implementations
                if (my.isFull() != test.isFull()) {
                    System.out.println("Oops!");
                }

                // Different scenarios based on heap state
                if (my.isEmpty()) {
                    // If heap is empty, push a random value to both heaps
                    int curValue = (int) (Math.random() * value);
                    my.push(curValue);
                    test.push(curValue);
                } else if (my.isFull()) {
                    // If heap is full, pop from both heaps and verify values match
                    if (my.pop() != test.pop()) {
                        System.out.println("Oops!");
                    }
                } else {
                    // If heap is neither empty nor full, randomly push or pop
                    if (Math.random() < 0.5) {
                        // Push a random value to both heaps
                        int curValue = (int) (Math.random() * value);
                        my.push(curValue);
                        test.push(curValue);
                    } else {
                        // Pop from both heaps and verify values match
                        if (my.pop() != test.pop()) {
                            System.out.println("Oops!");
                        }
                    }
                }
            }
        }
        System.out.println("finish!"); // All tests passed
    }

    /**
     * Efficient max-heap implementation using an array-based binary tree
     * Time complexity: O(log N) for push and pop operations
     */
    public static class MyMaxHeap {
        private final int limit;   // Maximum size of the heap
        private int[] heap;        // Array to store heap elements
        private int heapSize;      // Current number of elements in the heap

        /**
         * Constructor for the max heap with a size limit
         *
         * @param limit Maximum number of elements the heap can hold
         */
        public MyMaxHeap(int limit) {
            heap = new int[limit]; // Create an array of the specified size
            this.limit = limit;    // Store the size limit
            heapSize = 0;          // Initialize heap size to 0 (empty)
        }

        /**
         * Checks if the heap is empty
         *
         * @return true if the heap has no elements, false otherwise
         */
        public boolean isEmpty() {
            return heapSize == 0;
        }

        /**
         * Checks if the heap is full
         *
         * @return true if the heap has reached its capacity, false otherwise
         */
        public boolean isFull() {
            return heapSize == limit;
        }

        /**
         * Adds a new value to the heap
         *
         * @param value The value to add
         */
        public void push(int value) {
            if (heapSize == limit) {
                throw new RuntimeException("heap is full");
            }
            heap[heapSize] = value;  // Add the new value at the end of the heap

            // Move the new value up to its correct position (heapify up)
            heapInsert(heap, heapSize++);
        }

        /**
         * Removes and returns the maximum value from the heap
         *
         * @return The maximum value in the heap
         */
        public int pop() {
            int ans = heap[0];  // The max value is always at index 0

            // Move the last element to the root position and decrease heap size
            swap(heap, 0, --heapSize);

            // Reorganize the heap to maintain the max-heap property (heapify down)
            heapify(heap, 0, heapSize);
            return ans;
        }

        /**
         * Heapify up: moves a value up the tree until the heap property is satisfied
         * This is used when adding a new element to the heap
         *
         * @param arr   The heap array
         * @param index The index of the element to move up
         */
        private void heapInsert(int[] arr, int index) {
            // Keep moving up while the current value is greater than its parent
            // Parent index of a node at index i is (i-1)/2
            while (arr[index] > arr[(index - 1) / 2]) {
                // Swap with parent if current value is greater
                swap(arr, index, (index - 1) / 2);
                // Update index to the parent position for next iteration
                index = (index - 1) / 2;
            }
        }

        /**
         * Heapify down: moves a value down the tree until the heap property is satisfied
         * This is used after removing the root element
         *
         * @param arr      The heap array
         * @param index    The index to start heapifying from
         * @param heapSize Current size of the heap
         */
        private void heapify(int[] arr, int index, int heapSize) {
            // Left child index of a node at index i is 2*i+1
            int left = index * 2 + 1;

            // Continue while there is at least a left child
            while (left < heapSize) {
                // Find the larger of the left and right children (if right child exists)
                // Right child index is left+1 (or 2*i+2)
                int largest = left + 1 < heapSize && arr[left + 1] > arr[left] ? left + 1 : left;

                // Compare the larger child with the current node
                largest = arr[largest] > arr[index] ? largest : index;

                // If current node is already the largest, we're done
                if (largest == index) {
                    break;
                }

                // Swap with the largest child
                swap(arr, largest, index);

                // Update indices for next iteration
                index = largest;
                left = index * 2 + 1;
            }
        }

        /**
         * Helper method to swap two elements in an array
         *
         * @param arr The array
         * @param i   First index
         * @param j   Second index
         */
        private void swap(int[] arr, int i, int j) {
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
    }

    /**
     * Naive implementation of a max heap used for testing
     * This implementation has O(N) pop operations which is inefficient
     * but straightforward for validation purposes
     */
    public static class RightMaxHeap {
        private final int limit;  // Maximum size of the heap
        private int[] arr;        // Array to store elements
        private int size;         // Current number of elements

        /**
         * Constructor for the naive max heap
         *
         * @param limit Maximum number of elements the heap can hold
         */
        public RightMaxHeap(int limit) {
            arr = new int[limit];
            this.limit = limit;
            size = 0;
        }

        /**
         * Checks if the heap is empty
         *
         * @return true if the heap has no elements, false otherwise
         */
        public boolean isEmpty() {
            return size == 0;
        }

        /**
         * Checks if the heap is full
         *
         * @return true if the heap has reached its capacity, false otherwise
         */
        public boolean isFull() {
            return size == limit;
        }

        /**
         * Adds a value to the heap (simply appends it to the array)
         *
         * @param value The value to add
         */
        public void push(int value) {
            if (size == limit) {
                throw new RuntimeException("heap is full");
            }
            arr[size++] = value;  // Just add at the end, no heapify needed
        }

        /**
         * Removes and returns the maximum value from the heap
         * This searches the entire array to find the max value - O(N) time complexity
         *
         * @return The maximum value in the heap
         */
        public int pop() {
            // Find index of the maximum value by linear search
            int maxIndex = 0;
            for (int i = 1; i < size; i++) {
                if (arr[i] > arr[maxIndex]) {
                    maxIndex = i;
                }
            }

            // Store the max value before overwriting it
            int ans = arr[maxIndex];

            // Replace the max value with the last element and decrease size
            arr[maxIndex] = arr[--size];
            return ans;
        }
    }

    /**
     * Custom comparator to turn Java's PriorityQueue into a max heap
     * (By default, PriorityQueue in Java is a min heap)
     */
    public static class MyComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer o1, Integer o2) {
            // Reverse comparison to create a max heap
            // Returns positive if o2 > o1, negative if o2 < o1, zero if equal
            return o2 - o1;
        }
    }


}
