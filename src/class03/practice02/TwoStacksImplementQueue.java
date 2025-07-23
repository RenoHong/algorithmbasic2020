package class03.practice02;

import java.util.Stack;

public class TwoStacksImplementQueue {

    public static void main(String[] args) {
        TwoStackQueue queue = new TwoStackQueue();
        queue.push(1);
        queue.push(2);
        queue.push(3);

        System.out.println(queue.peek());
        System.out.println(queue.poll());
        System.out.println(queue.peek());
        System.out.println(queue.poll());
        System.out.println(queue.peek());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
    }


    public static class TwoStackQueue {
        Stack<Integer> stackPush;
        Stack<Integer> stackPop;

        public TwoStackQueue() {
            stackPush = new Stack<>();
            stackPop = new Stack<>();
        }

        private void pushToPop() {
            if (stackPop.isEmpty()) {
                while (!stackPush.isEmpty())
                    stackPop.push(stackPush.pop());
            }
        }

        public void push(int num) {
            stackPush.push(num);
            pushToPop();
        }

        public int poll() {
            if (stackPop.isEmpty() && stackPush.isEmpty())
                throw new RuntimeException("Empty");
            pushToPop();
            return stackPop.pop();
        }

        public int peek() {
            if (stackPop.isEmpty() && stackPush.isEmpty())
                throw new RuntimeException("Empty");
            pushToPop();
            return stackPop.peek();
        }


    }
}
