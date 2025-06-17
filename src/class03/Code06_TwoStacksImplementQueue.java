package class03;

import java.util.Stack;

/**
 * Implements a queue using two stacks.
 * Principles:
 * 1. Only two stacks are used to implement queue functionality.
 * 2. Data can only be transferred from stackPush to stackPop, and must be transferred all at once.
 * 3. Only when stackPop is empty, data can be transferred from stackPush to stackPop.
 * 4. add operation always pushes data to stackPush; poll and peek ensure stackPop has data.
 */
public class Code06_TwoStacksImplementQueue {

    /**
     * Main method to test the TwoStacksQueue implementation.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        TwoStacksQueue test = new TwoStacksQueue();
        test.add(1);
        test.add(2);
        test.add(3);
        System.out.println(test.peek());
        System.out.println(test.poll());
        System.out.println(test.peek());
        System.out.println(test.poll());
        System.out.println(test.peek());
        System.out.println(test.poll());
    }

    /**
     * Queue implementation using two stacks.
     */
    public static class TwoStacksQueue {
        public Stack<Integer> stackPush;
        public Stack<Integer> stackPop;

        /**
         * Constructs an empty TwoStacksQueue.
         */
        public TwoStacksQueue() {
            stackPush = new Stack<Integer>();
            stackPop = new Stack<Integer>();
        }

        /**
         * Transfers all elements from stackPush to stackPop if stackPop is empty.
         * Ensures the oldest elements are on top of stackPop for queue behavior.
         */
        private void pushToPop() {
            if (stackPop.empty()) {
                while (!stackPush.empty()) {
                    stackPop.push(stackPush.pop());
                }
            }
        }

        /**
         * Adds an element to the end of the queue.
         *
         * @param pushInt the element to add
         */
        public void add(int pushInt) {
            stackPush.push(pushInt);
            pushToPop();
        }

        /**
         * Removes and returns the element at the front of the queue.
         *
         * @return the front element
         * @throws RuntimeException if the queue is empty
         */
        public int poll() {
            if (stackPop.empty() && stackPush.empty()) {
                throw new RuntimeException("Queue is empty!");
            }
            pushToPop();
            return stackPop.pop();
        }

        /**
         * Returns the element at the front of the queue without removing it.
         *
         * @return the front element
         * @throws RuntimeException if the queue is empty
         */
        public int peek() {
            if (stackPop.empty() && stackPush.empty()) {
                throw new RuntimeException("Queue is empty!");
            }
            pushToPop();
            return stackPop.peek();
        }
    }

}