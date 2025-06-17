package class03;

import java.util.HashSet;
import java.util.Stack;

/**
 * Implements a stack that supports retrieving the minimum value in O(1) time.
 * Uses two stacks: one for data and one for tracking minimums.
 */
public class Code05_GetMinStack {

    /**
     * Main method to test the Min Stack implementations.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        MyStack1 stack1 = new MyStack1();
        stack1.push(3);
        System.out.println(stack1.getmin());
        stack1.push(4);
        System.out.println(stack1.getmin());
        stack1.push(1);
        System.out.println(stack1.getmin());
        System.out.println(stack1.pop());
        System.out.println(stack1.getmin());

        System.out.println("=============");

        MyStack2 stack2 = new MyStack2();
        stack2.push(3);
        System.out.println(stack2.getmin());
        stack2.push(4);
        System.out.println(stack2.getmin());
        stack2.push(1);
        System.out.println(stack2.getmin());
        System.out.println(stack2.pop());
        System.out.println(stack2.getmin());

        HashSet<Integer> set = new HashSet<>();

    }

    /**
     * First implementation of Min Stack.
     */
    public static class MyStack1 {
        private Stack<Integer> stackData;
        private Stack<Integer> stackMin;

        /**
         * Constructs an empty MyStack1.
         */
        public MyStack1() {
            stackData = new Stack<Integer>();
            stackMin = new Stack<Integer>();
        }

        /**
         * Pushes a new number onto the stack.
         *
         * @param newNum The number to push.
         */
        public void push(int newNum) {
            if (stackMin.isEmpty() || newNum <= this.getmin()) {
                stackMin.push(newNum);
            }
            stackData.push(newNum);
        }

        /**
         * Pops the top number from the stack.
         *
         * @return The number popped.
         */
        public int pop() {
            if (stackData.isEmpty()) {
                throw new RuntimeException("Your stack is empty.");
            }
            int value = stackData.pop();
            if (value == getmin()) {
                stackMin.pop();
            }
            return value;
        }

        /**
         * Retrieves the current minimum value in the stack.
         *
         * @return The minimum value.
         */
        public int getmin() {
            if (stackMin.isEmpty()) {
                throw new RuntimeException("Your stack is empty.");
            }
            return stackMin.peek();
        }
    }

    /**
     * Second implementation of Min Stack.
     */
    public static class MyStack2 {
        private Stack<Integer> stackData;
        private Stack<Integer> stackMin;

        /**
         * Constructs an empty MyStack2.
         */
        public MyStack2() {
            stackData = new Stack<Integer>();
            stackMin = new Stack<Integer>();
        }

        /**
         * Pushes a new number onto the stack.
         *
         * @param newNum The number to push.
         */
        public void push(int newNum) {
            if (stackMin.isEmpty() || newNum < getmin()) {
                stackMin.push(newNum);
            } else {
                stackMin.push(stackMin.peek());
            }
            stackData.push(newNum);
        }

        /**
         * Pops the top number from the stack.
         *
         * @return The number popped.
         */
        public int pop() {
            if (stackData.isEmpty()) {
                throw new RuntimeException("Your stack is empty.");
            }
            stackMin.pop();
            return stackData.pop();
        }

        /**
         * Retrieves the current minimum value in the stack.
         *
         * @return The minimum value.
         */
        public int getmin() {
            if (stackMin.isEmpty()) {
                throw new RuntimeException("Your stack is empty.");
            }
            return stackMin.peek();
        }
    }

}