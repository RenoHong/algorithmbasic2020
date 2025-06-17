package class03;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Implements a stack using two queues.
 */
public class Code07_TwoQueueImplementStack {

    /**
     * Main method to test the TwoQueueStack implementation.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        System.out.println("test begin");
        TwoQueueStack<Integer> myStack = new TwoQueueStack<>();
        Stack<Integer> test = new Stack<>();
        int testTime = 1000000;
        int max = 1000000;
        for (int i = 0; i < testTime; i++) {
            if (myStack.isEmpty()) {
                if (!test.isEmpty()) {
                    System.out.println("Oops");
                }
                int num = (int) (Math.random() * max);
                myStack.push(num);
                test.push(num);
            } else {
                if (Math.random() < 0.25) {
                    int num = (int) (Math.random() * max);
                    myStack.push(num);
                    test.push(num);
                } else if (Math.random() < 0.5) {
                    if (!myStack.peek().equals(test.peek())) {
                        System.out.println("Oops");
                    }
                } else if (Math.random() < 0.75) {
                    if (!myStack.poll().equals(test.pop())) {
                        System.out.println("Oops");
                    }
                } else {
                    if (myStack.isEmpty() != test.isEmpty()) {
                        System.out.println("Oops");
                    }
                }
            }
        }

        System.out.println("test finish!");

    }

    /**
     * Stack implementation using two queues.
     *
     * @param <T> The type of value stored.
     */
    public static class TwoQueueStack<T> {
        public Queue<T> queue;
        public Queue<T> help;

        /**
         * Constructs an empty TwoQueueStack.
         */
        public TwoQueueStack() {
            queue = new LinkedList<>();
            help = new LinkedList<>();
        }

        /**
         * Pushes a value onto the stack.
         *
         * @param value The value to push.
         */
        public void push(T value) {
            queue.offer(value);
        }

        /**
         * Pops a value from the stack.
         *
         * @return The value popped.
         */
        public T poll() {
            while (queue.size() > 1) {
                help.offer(queue.poll());
            }
            T ans = queue.poll();
            Queue<T> tmp = queue;
            queue = help;
            help = tmp;
            return ans;
        }

        /**
         * Peeks at the top value of the stack without removing it.
         *
         * @return The top value.
         */
        public T peek() {
            while (queue.size() > 1) {
                help.offer(queue.poll());
            }
            T ans = queue.poll();
            help.offer(ans);
            Queue<T> tmp = queue;
            queue = help;
            help = tmp;
            return ans;
        }

        /**
         * Checks if the stack is empty.
         *
         * @return True if empty, false otherwise.
         */
        public boolean isEmpty() {
            return queue.isEmpty();
        }

    }

}