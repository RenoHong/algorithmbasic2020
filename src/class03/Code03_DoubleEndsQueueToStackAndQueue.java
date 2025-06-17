package class03;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Implements a double-ended queue and uses it to build stack and queue structures.
 */
public class Code03_DoubleEndsQueueToStackAndQueue {

    /**
     * Compares two Integer objects for equality, handling nulls.
     *
     * @param o1 First Integer.
     * @param o2 Second Integer.
     * @return True if equal, false otherwise.
     */
    public static boolean isEqual(Integer o1, Integer o2) {
        if (o1 == null && o2 != null) {
            return false;
        }
        if (o1 != null && o2 == null) {
            return false;
        }
        if (o1 == null && o2 == null) {
            return true;
        }
        return o1.equals(o2);
    }

    /**
     * Main method to test the stack and queue implementations.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        int oneTestDataNum = 100;
        int value = 10000;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            MyStack<Integer> myStack = new MyStack<>();
            MyQueue<Integer> myQueue = new MyQueue<>();
            Stack<Integer> stack = new Stack<>();
            Queue<Integer> queue = new LinkedList<>();
            for (int j = 0; j < oneTestDataNum; j++) {
                int nums = (int) (Math.random() * value);
                if (stack.isEmpty()) {
                    myStack.push(nums);
                    stack.push(nums);
                } else {
                    if (Math.random() < 0.5) {
                        myStack.push(nums);
                        stack.push(nums);
                    } else {
                        if (!isEqual(myStack.pop(), stack.pop())) {
                            System.out.println("oops!");
                        }
                    }
                }
                int numq = (int) (Math.random() * value);
                if (queue.isEmpty()) {
                    myQueue.push(numq);
                    queue.offer(numq);
                } else {
                    if (Math.random() < 0.5) {
                        myQueue.push(numq);
                        queue.offer(numq);
                    } else {
                        if (!isEqual(myQueue.poll(), queue.poll())) {
                            System.out.println("oops!");
                        }
                    }
                }
            }
        }
        System.out.println("finish!");
    }

    /**
     * Node class for double-ended queue.
     *
     * @param <T> The type of value stored.
     */
    public static class Node<T> {
        public T value;
        public Node<T> last;
        public Node<T> next;

        public Node(T data) {
            value = data;
        }
    }

    /**
     * Double-ended queue implementation.
     *
     * @param <T> The type of value stored.
     */
    public static class DoubleEndsQueue<T> {
        public Node<T> head;
        public Node<T> tail;

        /**
         * Adds a value to the head of the queue.
         *
         * @param value The value to add.
         */
        public void addFromHead(T value) {
            Node<T> cur = new Node<T>(value);
            if (head == null) {
                head = cur;
                tail = cur;
            } else {
                cur.next = head;
                head.last = cur;
                head = cur;
            }
        }

        /**
         * Adds a value to the tail of the queue.
         *
         * @param value The value to add.
         */
        public void addFromBottom(T value) {
            Node<T> cur = new Node<T>(value);
            if (head == null) {
                head = cur;
                tail = cur;
            } else {
                cur.last = tail;
                tail.next = cur;
                tail = cur;
            }
        }

        /**
         * Removes and returns the value from the head of the queue.
         *
         * @return The value removed, or null if empty.
         */
        public T popFromHead() {
            if (head == null) {
                return null;
            }
            Node<T> cur = head;
            if (head == tail) {
                head = null;
                tail = null;
            } else {
                head = head.next;
                cur.next = null;
                head.last = null;
            }
            return cur.value;
        }

        /**
         * Removes and returns the value from the tail of the queue.
         *
         * @return The value removed, or null if empty.
         */
        public T popFromBottom() {
            if (head == null) {
                return null;
            }
            Node<T> cur = tail;
            if (head == tail) {
                head = null;
                tail = null;
            } else {
                tail = tail.last;
                tail.next = null;
                cur.last = null;
            }
            return cur.value;
        }

        /**
         * Checks if the queue is empty.
         *
         * @return True if empty, false otherwise.
         */
        public boolean isEmpty() {
            return head == null;
        }

    }

    /**
     * Stack implementation using DoubleEndsQueue.
     *
     * @param <T> The type of value stored.
     */
    public static class MyStack<T> {
        private DoubleEndsQueue<T> queue;

        public MyStack() {
            queue = new DoubleEndsQueue<T>();
        }

        /**
         * Pushes a value onto the stack.
         *
         * @param value The value to push.
         */
        public void push(T value) {
            queue.addFromHead(value);
        }

        /**
         * Pops a value from the stack.
         *
         * @return The value popped.
         */
        public T pop() {
            return queue.popFromHead();
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

    /**
     * Queue implementation using DoubleEndsQueue.
     *
     * @param <T> The type of value stored.
     */
    public static class MyQueue<T> {
        private DoubleEndsQueue<T> queue;

        public MyQueue() {
            queue = new DoubleEndsQueue<T>();
        }

        /**
         * Pushes a value into the queue.
         *
         * @param value The value to push.
         */
        public void push(T value) {
            queue.addFromHead(value);
        }

        /**
         * Polls a value from the queue.
         *
         * @return The value polled.
         */
        public T poll() {
            return queue.popFromBottom();
        }

        /**
         * Checks if the queue is empty.
         *
         * @return True if empty, false otherwise.
         */
        public boolean isEmpty() {
            return queue.isEmpty();
        }

    }

}