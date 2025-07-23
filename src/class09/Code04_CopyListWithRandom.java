package class09;

import java.util.HashMap;

// 测试链接 : https://leetcode.cn/problems/copy-list-with-random-pointer/
public class Code04_CopyListWithRandom {

    /**
     * 方法1：使用HashMap来复制带随机指针的链表
     * 时间复杂度：O(N)，空间复杂度：O(N)
     * <p>
     * 算法步骤：
     * 1. 第一次遍历：创建所有新节点，建立老节点到新节点的映射关系
     * 2. 第二次遍历：根据映射关系设置新节点的next和random指针
     */
    public static Node copyRandomList1(Node head) {
        // key 老节点
        // value 新节点
        HashMap<Node, Node> map = new HashMap<Node, Node>();
        Node cur = head;

        // 第一步：遍历原链表，为每个节点创建对应的新节点
        while (cur != null) {
            map.put(cur, new Node(cur.val));
            cur = cur.next;
        }

        cur = head;
        // 第二步：遍历原链表，设置新节点的next和random指针
        while (cur != null) {
            // cur是老节点，map.get(cur)是对应的新节点
            // 设置新节点的next：老节点的next对应的新节点
            map.get(cur).next = map.get(cur.next);
            // 设置新节点的random：老节点的random对应的新节点
            map.get(cur).random = map.get(cur.random);
            cur = cur.next;
        }
        return map.get(head); // 返回新链表的头节点
    }

    /**
     * 方法2：不使用额外空间的原地复制方法
     * 时间复杂度：O(N)，空间复杂度：O(1)
     * <p>
     * 算法步骤：
     * 1. 第一步：在每个原节点后面插入对应的复制节点
     * 原链表：1 -> 2 -> 3 -> null
     * 变成：  1 -> 1' -> 2 -> 2' -> 3 -> 3' -> null
     * 2. 第二步：设置复制节点的random指针
     * 3. 第三步：分离原链表和复制链表
     */
    public static Node copyRandomList2(Node head) {
        if (head == null) {
            return null;
        }
        Node cur = head;
        Node next = null;

        // 第一步：在每个原节点后面插入复制节点
        // 1 -> 2 -> 3 -> null 变成 1 -> 1' -> 2 -> 2' -> 3 -> 3' -> null
        while (cur != null) {
            next = cur.next;              // 保存下一个原节点
            cur.next = new Node(cur.val); // 创建复制节点并连接
            cur.next.next = next;         // 复制节点指向下一个原节点
            cur = next;                   // 移动到下一个原节点
        }

        cur = head;
        Node copy = null;
        // 第二步：设置复制节点的random指针
        // 对于每个复制节点，其random应该指向原节点random的复制节点
        while (cur != null) {
            next = cur.next.next;         // 下一个原节点
            copy = cur.next;              // 当前原节点的复制节点
            // 如果原节点有random指针，复制节点的random指向原节点random的复制节点
            // 原节点的random是老节点，老节点的next就是对应的复制节点
            copy.random = cur.random != null ? cur.random.next : null;
            cur = next;
        }

        Node res = head.next; // 保存复制链表的头节点
        cur = head;

        // 第三步：分离原链表和复制链表
        // 恢复原链表的结构，同时构建复制链表的正确next指针
        while (cur != null) {
            next = cur.next.next;         // 下一个原节点
            copy = cur.next;              // 当前复制节点
            cur.next = next;              // 恢复原链表的next指针
            // 设置复制节点的next指针：指向下一个复制节点
            copy.next = next != null ? next.next : null;
            cur = next;
        }
        return res; // 返回复制链表的头节点
    }

    /**
     * 链表节点定义
     * 包含值、next指针和random指针
     */
    public static class Node {
        int val;        // 节点值
        Node next;      // 指向下一个节点
        Node random;    // 指向链表中任意节点或null

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

}
