package class03.practice02;

import java.util.Stack;

public class GetMinStack {

    Stack<Integer> stackData;
    Stack<Integer> stackMin;

    public GetMinStack() {
        stackData = new Stack<>();
        stackMin = new Stack<>();
    }

    public void push(int num) {
        if (stackMin.isEmpty() || stackMin.peek() <= num) {
            stackMin.push(num);
        }
        stackData.push(num);
    }

    public int pop() {
        if (stackData.isEmpty())
            throw new RuntimeException("Empty!");

        int p = stackData.pop();
        if (getMin() == p)
            stackMin.pop();
        return p;
    }

    public int getMin() {
        if (stackMin.isEmpty())
            throw new RuntimeException("stackMin is empty!");
        return stackMin.peek();
    }

}
