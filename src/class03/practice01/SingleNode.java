package class03.practice01;

import util.Node;

public class SingleNode {

    public static Node reverse(Node head) {
        if (head == null || head.next == null) return head;
        Node pre = null;

        while (head != null) {
            Node next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }

    public static Node remove(Node head, int n) {
        while (head != null) {
            if (head.value == n) {
                head = head.next;
            } else {
                break;
            }
        }

        Node pre = head;
        Node cur = head;
        while (cur != null) {
            if (cur.value == n) {
                pre.next = cur.next;
            } else {
                pre = cur;
            }
            cur = cur.next;
        }

        return head;
    }

    public static void main(String[] args) {

//        for (int i = 0; i < Tester.testTimes; i++) {
//            Node h = Tester.generateRandomNodes();
//            List<Integer> ans = Tester.nodeToList(h);
//            h = reverse(h);
//            if (!Tester.checkNodeReverse(ans, h)){
//                System.out.println("Oops!");
//                Tester.printArray(ans);
//                break;
//            }
//        }
//        System.out.println("Nice");

//        for (int i = 0; i < Tester.testTimes; i++) {
//            Node h = Tester.generateRandomNodes();
//            int n = Tester.generateRandomInteger() ;
//
//            Node node =  remove(h, n);
//            if (!Tester.isValueRemovedInNodes(node, n) ){
//                System.out.println("Oops!");
//                System.out.println("n is " +n);
//                Tester.printArray(Tester.nodeToList(node));
//                break;
//            }
//
//        }
//        System.out.println("Nice");


//        HashMap<Integer, String> m = new HashMap<>();
//        m.put(1, "One") ;
//        m.put(2, "Two") ;
//        m.put(3, "Three") ;
//
//        m.forEach((k,v)->{
//            System.out.println("Key is " + k + " Value is " + v);
//        }) ;
//
//        for (Map.Entry<Integer,String> entry: m.entrySet()){
//            System.out.println("Key is " + entry.getKey() + " value " + entry.getValue());
//        }
//
//        for ( Integer i :  m.keySet()){
//            System.out.println(i);
//            System.out.println(m.get(i));
//        }
//
//        for (String s: m.values()){
//            System.out.println(s);
//        }
//
//        Iterator<Map.Entry<Integer, String>> iterator = m.entrySet().iterator();
//        while(iterator.hasNext()){
//            Map.Entry<Integer, String> next = iterator.next();
//            System.out.println("key " + next.getKey() + " value "+ next.getValue());
//        }


    }
}


