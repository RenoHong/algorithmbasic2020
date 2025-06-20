package class03.practice02;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class TwoQueueImplementStack {


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
                    if (!myStack.pop().equals(test.pop())) {
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

    public static class TwoQueueStack<T> {
        Queue<T> queue;
        Queue<T> helper;

        public TwoQueueStack() {
            queue = new LinkedList<>();
            helper = new LinkedList<>();
        }

        public void push(T value) {
            queue.add(value);
        }

        public T pop() {
            while (queue.size() > 1) {
                helper.offer(queue.poll());
            }
            T ans = queue.poll();
            Queue<T> tmp = queue;
            queue = helper;
            helper = tmp;
            return ans;
        }

        public T peek() {
            while (queue.size() > 1) {
                helper.offer(queue.poll());
            }
            T ans = queue.poll();
            helper.offer(ans);
            Queue<T> tmp = queue;
            queue = helper;
            helper = tmp;
            return ans;

        }

        public boolean isEmpty() {
            return queue.isEmpty();
        }

    }

}
