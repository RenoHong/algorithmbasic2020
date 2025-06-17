package class03;

import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;

/**
 * Demonstrates the usage of HashMap, HashSet, and TreeMap in Java,
 * including their behaviors with custom objects and integer caching.
 */
public class HashMapAndSortedMap {

    /**
     * Main method demonstrating HashMap, HashSet, and TreeMap usage.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {

        HashMap<Integer, String> test = new HashMap<>(); // Create a HashMap with Integer keys and String values
        Integer a = 19000000; // Create Integer object a
        Integer b = 19000000; // Create Integer object b with same value as a
        System.out.println(a == b); // Compare references of a and b (false, different objects)

        test.put(a, "我是3"); // Put a key-value pair into the map
        System.out.println(test.containsKey(b)); // Check if b is considered the same key (true, equals/hashCode)

        Zuo z1 = new Zuo(1); // Create Zuo object z1
        Zuo z2 = new Zuo(1); // Create Zuo object z2 with same value as z1
        HashMap<Zuo, String> test2 = new HashMap<>(); // Create HashMap with Zuo keys
        test2.put(z1, "我是z1"); // Put z1 as key
        System.out.println(test2.containsKey(z2)); // Check if z2 is considered the same key (false, no equals/hashCode override)

        // UnSortedMap
        HashMap<Integer, String> map = new HashMap<>(); // Create another HashMap
        map.put(1000000, "我是1000000"); // Add key-value pairs
        map.put(2, "我是2");
        map.put(3, "我是3");
        map.put(4, "我是4");
        map.put(5, "我是5");
        map.put(6, "我是6");
        map.put(1000000, "我是1000001"); // Overwrite value for key 1000000

        System.out.println(map.containsKey(1)); // Check if key 1 exists (false)
        System.out.println(map.containsKey(10)); // Check if key 10 exists (false)

        System.out.println(map.get(4)); // Get value for key 4
        System.out.println(map.get(10)); // Get value for key 10 (null)

        map.put(4, "他是4"); // Update value for key 4
        System.out.println(map.get(4)); // Get updated value for key 4

        map.remove(4); // Remove key 4
        System.out.println(map.get(4)); // Get value for removed key (null)

        // key
        HashSet<String> set = new HashSet<>(); // Create a HashSet for Strings
        set.add("abc"); // Add "abc" to set
        set.contains("abc"); // Check if "abc" is in set
        set.remove("abc"); // Remove "abc" from set

        // 哈希表，增、删、改、查，在使用时，O（1）
        // Hash table operations: add, delete, update, query are O(1) on average

        System.out.println("====================="); // Separator

        Integer c = 100000; // Create Integer c
        Integer d = 100000; // Create Integer d with same value
        System.out.println(c.equals(d)); // Compare values (true)

        Integer e = 127; // -128 ~ 127 are cached in Integer.valueOf
        Integer f = 127; // e and f refer to same cached object
        System.out.println(e == f); // Compare references (true)

        HashMap<Node, String> map2 = new HashMap<>(); // HashMap with Node keys
        Node node1 = new Node(1); // Create Node node1
        Node node2 = node1; // node2 references same object as node1
        map2.put(node1, "我是node1"); // Put node1 as key
        map2.put(node2, "我是node1"); // Put node2 as key (same object)
        System.out.println(map2.size()); // Should print 1

        System.out.println("======================"); // Separator

        // TreeMap 有序表：接口名
        // 红黑树、avl、sb树、跳表
        // O(logN)
        // TreeMap is a sorted map, implemented as a red-black tree (O(logN) operations)
        System.out.println("有序表测试开始"); // Start of TreeMap test
        TreeMap<Integer, String> treeMap = new TreeMap<>(); // Create TreeMap

        treeMap.put(3, "我是3"); // Add key-value pairs
        treeMap.put(4, "我是4");
        treeMap.put(8, "我是8");
        treeMap.put(5, "我是5");
        treeMap.put(7, "我是7");
        treeMap.put(1, "我是1");
        treeMap.put(2, "我是2");

        System.out.println(treeMap.containsKey(1)); // Check if key 1 exists (true)
        System.out.println(treeMap.containsKey(10)); // Check if key 10 exists (false)

        System.out.println(treeMap.get(4)); // Get value for key 4
        System.out.println(treeMap.get(10)); // Get value for key 10 (null)

        treeMap.put(4, "他是4"); // Update value for key 4
        System.out.println(treeMap.get(4)); // Get updated value for key 4

        // treeMap.remove(4);
        System.out.println(treeMap.get(4)); // Get value for key 4 (still exists)

        System.out.println("新鲜："); // Fresh output

        System.out.println(treeMap.firstKey()); // Get smallest key
        System.out.println(treeMap.lastKey()); // Get largest key
        // <= 4
        System.out.println(treeMap.floorKey(4)); // Get greatest key <= 4
        // >= 4
        System.out.println(treeMap.ceilingKey(4)); // Get smallest key >= 4
        // O(logN) for all TreeMap operations

    }

    /**
     * Node class with a single integer value.
     */
    public static class Node {
        public int value; // Value stored in the node

        /**
         * Constructor for Node.
         *
         * @param v the value to store
         */
        public Node(int v) {
            value = v; // Assign value
        }
    }

    /**
     * Zuo class with a single integer value.
     */
    public static class Zuo {
        public int value; // Value stored in the Zuo object

        /**
         * Constructor for Zuo.
         *
         * @param v the value to store
         */
        public Zuo(int v) {
            value = v; // Assign value
        }
    }

}
