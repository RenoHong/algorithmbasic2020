package class03.practice02;

public class RingArray {

    int[] data;
    int size;
    final int capacity;
    int pushi;
    int polli;

    public RingArray(int capacity) {
        this.capacity = capacity;
        data = new int[capacity];
        size = 0;
        pushi = 0;
        polli = 0;
    }

    public void push(int value) {
        if (size == capacity)
            throw new RuntimeException("Full!");
        data[pushi] = value;
        pushi = nextIndex(pushi);
        size++;
    }

    public int poll() {
        if (size == 0) {
            throw new RuntimeException("Empty");
        }
        int ans = data[polli];
        polli = nextIndex(polli);
        size--;
        return ans;
    }


    private int nextIndex(int index) {
        return index < capacity - 1 ? index + 1 : 0;
    }

    public boolean isEmpty(){
        return size ==0 ;
    }
    public int size(){
        return size ;
    }

}
