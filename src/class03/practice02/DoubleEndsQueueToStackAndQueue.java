package class03.practice02;

public class DoubleEndsQueueToStackAndQueue {

    public static class Node<T> {
        T value;
        Node<T> last, next;

        public Node(T data) {
            this.value = data;
        }
    }

    public static class DoubleEndNode<T> {
        Node<T> head, tail;
        int size = 0;

        public void addFromHead(T value) {
            Node<T> newNode = new Node<>(value);
            if (head == null) {
                head = tail = newNode;
            } else {
                head.last = newNode;
                newNode.next = head;
                head = newNode;
            }
            size++;
        }

        public T popFromHead() {
            Node<T> current = head;
            if (head != null) {
                current = head.next;
                if (current != null) {
                    current.last = null;
                }
                head.next = null;
                T res = head.value;
                head = current;
                return res;
            } else {
                throw new RuntimeException("No more elements from head!");
            }
        }

        public void addFromTail(T value) {
            Node<T> newNode = new Node<>(value);
            if (tail == null) {
                head = tail = newNode;
            } else {
                tail.next = newNode;
                newNode.last = tail;
                tail = newNode;
            }
            size++;
        }

        public T popFromTail() {
            if (tail == null) {
                throw new RuntimeException("No more elements from tail to pop!");
            }
            Node<T> current = tail.last;
            T res = tail.value;
            tail = current;
            if (tail != null) tail.next = null;
            size--;
            return res;

        }

        public boolean isEmpty() {
            return head == null;
        }
    }

    public static class MyStack<T> {
        DoubleEndNode<T> nodes = new DoubleEndNode<>();

        public void push(T value) {
            nodes.addFromHead(value);
        }

        public T pop() {
            return nodes.popFromHead();
        }

        public int size() {
            return nodes.size;
        }

        public boolean isEmpty() {
            return nodes.isEmpty();
        }

    }


    public static class MyQueue<T>{

        DoubleEndNode<T> nodes = new DoubleEndNode<>() ;
        public void push(T value){
            nodes.addFromHead(value);
        }
        public T poll(){
           return nodes.popFromTail();
        }

        public boolean isEmpty(){
            return nodes.isEmpty() ;
        }
        public int size(){ return nodes.size ;}
    }

    public static void main(String[] args) {
        MyStack<Integer>  stack = new MyStack<>() ;
        MyQueue<Integer>  queue = new MyQueue<>() ;
        for (int i = 0; i < 10; i++) {
            stack.push(i);
        }
        for (int i = 0; i < 10; i++) {
            System.out.println(stack.pop());
        }

        for (int i = 0; i < 10; i++) {
            queue.push(i);
        }
        for (int i = 0; i < 11; i++) {
            System.out.println(queue.poll());
        }

    }


}
