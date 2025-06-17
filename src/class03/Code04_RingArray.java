package class03;

/**
 * Implements a queue using a ring (circular) array.
 */
public class Code04_RingArray {

    /**
     * Queue implementation using a circular array.
     */
    public static class MyQueue {
        private final int limit;
        private int[] arr;
        private int pushi;// end
        private int polli;// begin
        private int size;

        /**
         * Constructs a queue with the given capacity.
         *
         * @param limit The maximum number of elements.
         */
        public MyQueue(int limit) {
            arr = new int[limit];
            pushi = 0;
            polli = 0;
            size = 0;
            this.limit = limit;
        }

        /**
         * Pushes a value into the queue.
         *
         * @param value The value to push.
         */
        public void push(int value) {
            if (size == limit) {
                throw new RuntimeException("队列满了，不能再加了");
            }
            size++;
            arr[pushi] = value;
            pushi = nextIndex(pushi);
        }

        /**
         * Pops a value from the queue.
         *
         * @return The value popped.
         */
        public int pop() {
            if (size == 0) {
                throw new RuntimeException("队列空了，不能再拿了");
            }
            size--;
            int ans = arr[polli];
            polli = nextIndex(polli);
            return ans;
        }

        /**
         * Checks if the queue is empty.
         *
         * @return True if empty, false otherwise.
         */
        public boolean isEmpty() {
            return size == 0;
        }

        /**
         * Returns the next index in the circular array.
         *
         * @param i The current index.
         * @return The next index.
         */
        private int nextIndex(int i) {
            return i < limit - 1 ? i + 1 : 0;
        }

    }

}