package class10;

public class Code01_FindFirstIntersectNode {

    /**
     * 主函数：寻找两个链表的第一个相交节点
     * 该问题需要考虑链表是否有环的情况，共有三种可能：
     * 1. 两个链表都无环
     * 2. 两个链表都有环
     * 3. 一个有环一个无环（这种情况不可能相交）
     */
    public static Node getIntersectNode(Node head1, Node head2) {
        if (head1 == null || head2 == null) {  // 边界条件：任一链表为空则不可能相交
            return null;
        }
        Node loop1 = getLoopNode(head1);  // 检测链表1是否有环，有环则返回入环节点
        Node loop2 = getLoopNode(head2);  // 检测链表2是否有环，有环则返回入环节点

        if (loop1 == null && loop2 == null) {      // 情况1：两个链表都无环
            return noLoop(head1, head2);
        }
        if (loop1 != null && loop2 != null) {      // 情况2：两个链表都有环
            return bothLoop(head1, loop1, head2, loop2);
        }
        return null;  // 情况3：一个有环一个无环，不可能相交
    }

    /**
     * 检测链表是否有环，如果有环返回入环的第一个节点
     * 使用Floyd环检测算法（快慢指针）
     * 算法步骤：
     * 1. 快慢指针同时从head出发，快指针每次走2步，慢指针每次走1步
     * 2. 如果有环，快慢指针一定会在环内相遇
     * 3. 相遇后，让一个指针回到head，两指针以相同速度前进
     * 4. 再次相遇的点就是入环点
     */
    public static Node getLoopNode(Node head) {
        // 边界检查：至少需要3个节点才可能形成环
        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }

        Node slow = head.next;       // 慢指针：每次移动1步
        Node fast = head.next.next;  // 快指针：每次移动2步

        // 第一阶段：检测是否有环
        while (slow != fast) {
            // 如果快指针到达终点，说明无环
            if (fast.next == null || fast.next.next == null) {
                return null;
            }
            fast = fast.next.next;  // 快指针移动2步
            slow = slow.next;       // 慢指针移动1步
        }

        // 第二阶段：找到入环点
        // 数学证明：当快慢指针相遇时，让一个指针回到head
        // 两指针以相同速度前进，相遇点就是入环点
        fast = head;  // 快指针回到起点
        while (slow != fast) {
            slow = slow.next;  // 两指针都以1步/次的速度前进
            fast = fast.next;
        }
        return slow;  // 返回入环点
    }

    /**
     * 处理两个无环链表的相交问题
     * 算法思路：
     * 1. 先计算两个链表的长度差
     * 2. 让长链表先走长度差的步数
     * 3. 然后两链表同时前进，第一个相同的节点就是交点
     */
    public static Node noLoop(Node head1, Node head2) {
        if (head1 == null || head2 == null) {
            return null;
        }
        Node cur1 = head1;
        Node cur2 = head2;
        int n = 0;  // 记录两个链表的长度差

        // 计算链表1的长度，同时累加长度差
        while (cur1.next != null) {
            n++;              // 链表1每多一个节点，n增加1
            cur1 = cur1.next;
        }

        // 计算链表2的长度，同时累减长度差
        while (cur2.next != null) {
            n--;              // 链表2每多一个节点，n减少1
            cur2 = cur2.next;
        }

        // 检查两链表的尾节点是否相同
        // 如果尾节点不同，说明两链表不相交
        if (cur1 != cur2) {
            return null;
        }

        // 让cur1指向较长的链表，cur2指向较短的链表
        cur1 = n > 0 ? head1 : head2;  // n>0说明链表1更长
        cur2 = cur1 == head1 ? head2 : head1;  // 另一个指向较短链表
        n = Math.abs(n);  // n为长度差的绝对值

        // 让较长链表先走长度差的步数
        while (n != 0) {
            n--;
            cur1 = cur1.next;
        }

        // 两链表同时前进，找到第一个相同节点
        while (cur1 != cur2) {
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        return cur1;  // 返回第一个相交节点
    }

    /**
     * 处理两个有环链表的相交问题
     * 两个有环链表的相交情况：
     * 1. 两链表的入环点是同一个：在入环前就相交了
     * 2. 两链表的入环点不同：在环上的不同位置入环，但共享同一个环
     * 3. 两链表完全不相交：各自有独立的环
     */
    public static Node bothLoop(Node head1, Node loop1, Node head2, Node loop2) {
        Node cur1 = null;
        Node cur2 = null;

        if (loop1 == loop2) {  // 情况1：入环点相同，在入环前就相交
            cur1 = head1;
            cur2 = head2;
            int n = 0;

            // 计算从head到入环点的长度差
            while (cur1 != loop1) {
                n++;
                cur1 = cur1.next;
            }
            while (cur2 != loop2) {
                n--;
                cur2 = cur2.next;
            }

            // 使用与无环链表相同的方法找交点
            cur1 = n > 0 ? head1 : head2;
            cur2 = cur1 == head1 ? head2 : head1;
            n = Math.abs(n);
            while (n != 0) {
                n--;
                cur1 = cur1.next;
            }
            while (cur1 != cur2) {
                cur1 = cur1.next;
                cur2 = cur2.next;
            }
            return cur1;  // 返回入环前的交点
        } else {  // 情况2或3：入环点不同
            cur1 = loop1.next;
            // 从loop1开始，绕环一圈寻找loop2
            while (cur1 != loop1) {
                if (cur1 == loop2) {    // 找到了loop2，说明共享同一个环
                    return loop1;       // 返回任意一个入环点作为交点
                }
                cur1 = cur1.next;
            }
            return null;  // 情况3：没找到，说明两链表不相交
        }
    }

    public static void main(String[] args) {
        // 1->2->3->4->5->6->7->null
        Node head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(6);
        head1.next.next.next.next.next.next = new Node(7);

        // 0->9->8->6->7->null
        Node head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next.next.next.next.next; // 8->6
        System.out.println(getIntersectNode(head1, head2).value);

        // 1->2->3->4->5->6->7->4...
        head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(6);
        head1.next.next.next.next.next.next = new Node(7);
        head1.next.next.next.next.next.next = head1.next.next.next; // 7->4

        // 0->9->8->2...
        head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next; // 8->2
        System.out.println(getIntersectNode(head1, head2).value);

        // 0->9->8->6->4->5->6..
        head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next.next.next.next.next; // 8->6
        System.out.println(getIntersectNode(head1, head2).value);

    }

    /**
     * 链表节点定义
     */
    public static class Node {
        public int value;  // 节点值
        public Node next;  // 指向下一个节点的指针

        public Node(int data) {
            this.value = data;
        }
    }

}
