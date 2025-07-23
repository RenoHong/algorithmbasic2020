package class06.practice02;

import java.util.*;

public class Heap02 {

    public static class Student {
        int age;
        String name;
        String classNo;

        public Student(int age, String name, String classNo) {
            this.age = age;
            this.name = name;
            this.classNo = classNo;
        }

        @Override
        public String toString() {
            return String.format("%s:age %d classNo %s", name, age, classNo);
        }

    }

    public static class StudentComparator implements Comparator<Student> {
        @Override
        public int compare(Student s1, Student s2) {
            return s2.age - s1.age;
        }
    }

    public static class Heap2<T> {
        List<T> heap;
        Map<T, Integer> indexMap;
        Comparator<T> comparator;
        int heapSize;

        public Heap2(Comparator<T> comparator) {
            heap = new LinkedList<>();
            indexMap = new HashMap<>();
            this.comparator = comparator;
            this.heapSize = 0;
        }

        public static void main(String[] args) {

            StudentComparator stuComparator = new StudentComparator();
            Heap2<Student> heap = new Heap2<>(stuComparator);
            for (int i = 0; i < 10; i++) {

                Random rand = new Random();
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < 10; j++) {
                    sb.append((char) ('A' + rand.nextInt('z' - 'A' + 1)));
                }

                Student student = new Student(i, sb.toString(), String.valueOf(rand.nextInt()));

                heap.push(student);
                if (i == 5) {
                    student.age = 1;
                    heap.resign(student);
                }


            }


            for (int i = 0; i < 10; i++) {
                System.out.println(heap.pop());
            }


        }

        private void swap(int i1, int i2) {
            T o1 = heap.get(i1);
            T o2 = heap.get(i2);

            heap.set(i1, o2);
            heap.set(i2, o1);

            indexMap.put(o2, i1);
            indexMap.put(o1, i2);

        }

        public int size() {
            return heap.size();
        }

        public boolean isEmpty() {
            return heap.isEmpty();
        }

        public boolean contains(T value) {
            return indexMap.containsKey(value);
        }

        public void push(T value) {
            heap.add(value);
            indexMap.put(value, heapSize);
            heapifyUp(heapSize++);
        }

        private void heapifyUp(int index) {
            while (comparator.compare(heap.get(index), heap.get((index - 1) / 2)) < 0) {
                swap(index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }

        private void heapifyDown(int index) {
            int left = index * 2 + 1;
            while (left < heapSize) {
                int greaterIndex = left + 1 < heapSize && comparator.compare(heap.get(left + 1), heap.get(left)) < 0
                        ? left + 1
                        : left;
                greaterIndex = comparator.compare(heap.get(greaterIndex), heap.get(index)) < 0
                        ? greaterIndex
                        : index;
                if (greaterIndex == index)
                    break;
                swap(index, greaterIndex);
                index = greaterIndex;
                left = index * 2 + 1;
            }
        }

        public T pop() {
            if (isEmpty()) {
                throw new RuntimeException("Empty");
            }
            T value = heap.get(0);
            int end = heapSize - 1;
            swap(0, end);
            heap.remove(end);
            indexMap.remove(value);
            heapSize--;
            heapifyDown(0);
            return value;
        }

        public void resign(T value) {
            int i = indexMap.get(value);
            heapifyDown(i);
            heapifyUp(i);
        }

    }
}
