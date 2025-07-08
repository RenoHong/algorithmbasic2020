package class09;

import java.util.ArrayList;

public class Code01_LinkedListMid {

    /**
     * 找到链表的中点或上中点
     * 奇数个节点返回中点，偶数个节点返回上中点
     * 例如：1->2->3 返回2，1->2->3->4 返回2
     * 使用快慢指针算法，时间复杂度O(n)，空间复杂度O(1)
     */
    public static Node midOrUpMidNode(Node head) {
        // 边界情况：空链表、单节点、双节点直接返回头节点
        if (head == null || head.next == null || head.next.next == null) {
            return head;
        }
        // 链表有3个点或以上，使用快慢指针
        Node slow = head.next;     // 慢指针从第2个节点开始
        Node fast = head.next.next; // 快指针从第3个节点开始
        // 快指针每次走2步，慢指针每次走1步
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;      // 慢指针走1步
            fast = fast.next.next; // 快指针走2步
        }
        return slow; // 当快指针到达末尾时，慢指针正好在中点位置
    }

    /**
     * 找到链表的中点或下中点
     * 奇数个节点返回中点，偶数个节点返回下中点
     * 例如：1->2->3 返回2，1->2->3->4 返回3
     */
    public static Node midOrDownMidNode(Node head) {
        // 边界情况：空链表或单节点直接返回
        if (head == null || head.next == null) {
            return head;
        }
        Node slow = head.next; // 慢指针从第2个节点开始
        Node fast = head.next; // 快指针也从第2个节点开始（与上一个方法的区别）
        // 快慢指针移动
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;      // 慢指针走1步
            fast = fast.next.next; // 快指针走2步
        }
        return slow;
    }

    /**
     * 找到链表中点或上中点的前一个节点
     * 例如：1->2->3 返回1，1->2->3->4 返回1，1->2->3->4->5 返回2
     * 用于需要删除中点节点的场景
     */
    public static Node midOrUpMidPreNode(Node head) {
        // 边界情况：少于3个节点时，没有中点的前驱节点
        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }
        Node slow = head;          // 慢指针从头节点开始
        Node fast = head.next.next; // 快指针从第3个节点开始
        // 快慢指针移动
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;      // 慢指针走1步
            fast = fast.next.next; // 快指针走2步
        }
        return slow; // 慢指针指向中点的前一个节点
    }

    /**
     * 找到链表中点或下中点的前一个节点
     * 例如：1->2->3 返回1，1->2->3->4 返回2
     */
    public static Node midOrDownMidPreNode(Node head) {
        // 边界情况：空链表或单节点时，没有前驱节点
        if (head == null || head.next == null) {
            return null;
        }
        // 特殊情况：只有2个节点时，返回头节点
        if (head.next.next == null) {
            return head;
        }
        Node slow = head;     // 慢指针从头节点开始
        Node fast = head.next; // 快指针从第2个节点开始
        // 快慢指针移动
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;      // 慢指针走1步
            fast = fast.next.next; // 快指针走2步
        }
        return slow;
    }

    /**
     * 辅助测试方法1：使用数组方式找上中点
     * 将链表节点存入数组，然后通过索引计算中点位置
     * 公式：(length-1)/2 得到上中点索引
     */
    public static Node right1(Node head) {
        if (head == null) {
            return null;
        }
        Node cur = head;
        ArrayList<Node> arr = new ArrayList<>(); // 存储所有节点
        // 遍历链表，将所有节点加入数组
        while (cur != null) {
            arr.add(cur);
            cur = cur.next;
        }
        // 返回上中点：(数组长度-1)/2 的位置
        return arr.get((arr.size() - 1) / 2);
    }

    /**
     * 辅助测试方法2：使用数组方式找下中点
     * 公式：length/2 得到下中点索引
     */
    public static Node right2(Node head) {
        if (head == null) {
            return null;
        }
        Node cur = head;
        ArrayList<Node> arr = new ArrayList<>();
        // 遍历链表，将所有节点加入数组
        while (cur != null) {
            arr.add(cur);
            cur = cur.next;
        }
        // 返回下中点：数组长度/2 的位置
        return arr.get(arr.size() / 2);
    }

    /**
     * 辅助测试方法3：使用数组方式找上中点的前驱节点
     * 公式：(length-3)/2 得到上中点前驱的索引
     */
    public static Node right3(Node head) {
        // 少于3个节点时，没有中点的前驱
        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }
        Node cur = head;
        ArrayList<Node> arr = new ArrayList<>();
        // 遍历链表，将所有节点加入数组
        while (cur != null) {
            arr.add(cur);
            cur = cur.next;
        }
        // 返回上中点的前驱：(数组长度-3)/2 的位置
        return arr.get((arr.size() - 3) / 2);
    }

    /**
     * 辅助测试方法4：使用数组方式找下中点的前驱节点
     * 公式：(length-2)/2 得到下中点前驱的索引
     */
    public static Node right4(Node head) {
        // 少于2个节点时，没有前驱
        if (head == null || head.next == null) {
            return null;
        }
        Node cur = head;
        ArrayList<Node> arr = new ArrayList<>();
        // 遍历链表，将所有节点加入数组
        while (cur != null) {
            arr.add(cur);
            cur = cur.next;
        }
        // 返回下中点的前驱：(数组长度-2)/2 的位置
        return arr.get((arr.size() - 2) / 2);
    }

    /**
     * 测试主方法：验证快慢指针算法与数组方法的结果是否一致
     */
    public static void main(String[] args) {
        // 构建测试链表：0->1->2->3->4->5->6->7->8
        Node test = null;
        test = new Node(0);
        test.next = new Node(1);
        test.next.next = new Node(2);
        test.next.next.next = new Node(3);
        test.next.next.next.next = new Node(4);
        test.next.next.next.next.next = new Node(5);
        test.next.next.next.next.next.next = new Node(6);
        test.next.next.next.next.next.next.next = new Node(7);
        test.next.next.next.next.next.next.next.next = new Node(8);

        Node ans1 = null;
        Node ans2 = null;

        // 测试找上中点的方法
        ans1 = midOrUpMidNode(test);
        ans2 = right1(test);
        System.out.println("上中点：" + (ans1 != null ? ans1.value : "无"));
        System.out.println("验证：" + (ans2 != null ? ans2.value : "无"));

        // 测试找下中点的方法
        ans1 = midOrDownMidNode(test);
        ans2 = right2(test);
        System.out.println("下中点：" + (ans1 != null ? ans1.value : "无"));
        System.out.println("验证：" + (ans2 != null ? ans2.value : "无"));

        // 测试找上中点前驱的方法
        ans1 = midOrUpMidPreNode(test);
        ans2 = right3(test);
        System.out.println("上中点前驱：" + (ans1 != null ? ans1.value : "无"));
        System.out.println("验证：" + (ans2 != null ? ans2.value : "无"));

        // 测试找下中点前驱的方法
        ans1 = midOrDownMidPreNode(test);
        ans2 = right4(test);
        System.out.println("下中点前驱：" + (ans1 != null ? ans1.value : "无"));
        System.out.println("验证：" + (ans2 != null ? ans2.value : "无"));
    }

    /**
     * 链表节点定义
     */
    public static class Node {
        public int value; // 节点值
        public Node next; // 指向下一个节点的指针

        public Node(int v) {
            value = v;
        }
    }

}
