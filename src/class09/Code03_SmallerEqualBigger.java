package class09;

public class Code03_SmallerEqualBigger {

    /**
     * 方法1：使用数组辅助进行链表分区
     * 将链表节点放入数组，使用荷兰国旗问题的思路进行分区，然后重新连接
     * 时间复杂度：O(n)，空间复杂度：O(n)
     * 
     * @param head 链表头节点
     * @param pivot 分区的基准值
     * @return 分区后的链表头节点
     */
    public static Node listPartition1(Node head, int pivot) {
        if (head == null) {
            return head;
        }
        
        // 第一步：计算链表长度
        Node cur = head;
        int i = 0;
        while (cur != null) {
            i++;                // 统计节点个数
            cur = cur.next;     // 遍历到下一个节点
        }
        
        // 第二步：将链表节点存入数组
        Node[] nodeArr = new Node[i];  // 创建节点数组
        i = 0;
        cur = head;
        for (i = 0; i != nodeArr.length; i++) {
            nodeArr[i] = cur;   // 将当前节点存入数组
            cur = cur.next;     // 移动到下一个节点
        }
        
        // 第三步：对数组进行分区（荷兰国旗问题）
        arrPartition(nodeArr, pivot);
        
        // 第四步：重新连接节点形成链表
        for (i = 1; i != nodeArr.length; i++) {
            nodeArr[i - 1].next = nodeArr[i];  // 连接相邻节点
        }
        nodeArr[i - 1].next = null;  // 最后一个节点的next设为null
        return nodeArr[0];           // 返回新链表的头节点
    }

    /**
     * 荷兰国旗问题：将数组分为三个区域
     * 小于pivot的区域 | 等于pivot的区域 | 大于pivot的区域
     * 
     * @param nodeArr 节点数组
     * @param pivot 分区基准值
     */
    public static void arrPartition(Node[] nodeArr, int pivot) {
        int small = -1;              // 小于区域的右边界（不包含）
        int big = nodeArr.length;    // 大于区域的左边界（不包含）
        int index = 0;               // 当前处理的位置
        
        // 遍历数组，将元素分配到对应区域
        while (index != big) {
            if (nodeArr[index].value < pivot) {
                // 当前值小于pivot，放到小于区域
                swap(nodeArr, ++small, index++);
            } else if (nodeArr[index].value == pivot) {
                // 当前值等于pivot，保持在等于区域
                index++;
            } else {
                // 当前值大于pivot，放到大于区域
                swap(nodeArr, --big, index);
                // 注意：这里index不自增，因为从大于区域换过来的元素还需要重新判断
            }
        }
    }

    /**
     * 交换数组中两个位置的元素
     * @param nodeArr 节点数组
     * @param a 位置a
     * @param b 位置b
     */
    public static void swap(Node[] nodeArr, int a, int b) {
        Node tmp = nodeArr[a];
        nodeArr[a] = nodeArr[b];
        nodeArr[b] = tmp;
    }

    /**
     * 方法2：直接在链表上进行分区，不使用额外数组
     * 使用6个指针分别维护小于、等于、大于三个区域的头尾
     * 时间复杂度：O(n)，空间复杂度：O(1)
     * 
     * @param head 链表头节点
     * @param pivot 分区基准值
     * @return 分区后的链表头节点
     */
    public static Node listPartition2(Node head, int pivot) {
        Node sH = null; // small head - 小于区域的头节点
        Node sT = null; // small tail - 小于区域的尾节点
        Node eH = null; // equal head - 等于区域的头节点
        Node eT = null; // equal tail - 等于区域的尾节点
        Node mH = null; // more(big) head - 大于区域的头节点
        Node mT = null; // more(big) tail - 大于区域的尾节点
        Node next = null; // save next node - 保存下一个节点
        
        // 第一步：遍历链表，将每个节点分配到对应的区域
        while (head != null) {
            next = head.next;  // 保存下一个节点
            head.next = null;  // 断开当前节点的连接
            
            if (head.value < pivot) {
                // 当前节点值小于pivot，加入小于区域
                if (sH == null) {
                    sH = head;     // 第一个节点，既是头也是尾
                    sT = head;
                } else {
                    sT.next = head; // 连接到小于区域的尾部
                    sT = head;      // 更新尾节点
                }
            } else if (head.value == pivot) {
                // 当前节点值等于pivot，加入等于区域
                if (eH == null) {
                    eH = head;     // 第一个节点，既是头也是尾
                    eT = head;
                } else {
                    eT.next = head; // 连接到等于区域的尾部
                    eT = head;      // 更新尾节点
                }
            } else {
                // 当前节点值大于pivot，加入大于区域
                if (mH == null) {
                    mH = head;     // 第一个节点，既是头也是尾
                    mT = head;
                } else {
                    mT.next = head; // 连接到大于区域的尾部
                    mT = head;      // 更新尾节点
                }
            }
            head = next;  // 移动到下一个节点
        }
        
        // 第二步：连接三个区域
        // 小于区域的尾巴，连等于区域的头，等于区域的尾巴连大于区域的头
        if (sT != null) { // 如果有小于区域
            sT.next = eH;  // 小于区域的尾连接等于区域的头
            eT = eT == null ? sT : eT; // 下一步，谁去连大于区域的头，谁就变成eT
        }
        
        // 下一步，一定是需要用eT 去接 大于区域的头
        // 有等于区域，eT -> 等于区域的尾结点
        // 无等于区域，eT -> 小于区域的尾结点
        // eT 尽量不为空的尾巴节点
        if (eT != null) { // 如果小于区域和等于区域，不是都没有
            eT.next = mH;  // 连接大于区域的头
        }
        
        // 第三步：返回最终的头节点
        // 优先返回小于区域的头，其次是等于区域的头，最后是大于区域的头
        return sH != null ? sH : (eH != null ? eH : mH);
    }

    /**
     * 打印链表的所有节点值
     * @param node 链表头节点
     */
    public static void printLinkedList(Node node) {
        System.out.print("Linked List: ");
        while (node != null) {
            System.out.print(node.value + " ");
            node = node.next;
        }
        System.out.println();
    }

    /**
     * 测试主方法
     */
    public static void main(String[] args) {
        // 构建测试链表：7->9->1->8->5->2->5
        Node head1 = new Node(7);
        head1.next = new Node(9);
        head1.next.next = new Node(1);
        head1.next.next.next = new Node(8);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(2);
        head1.next.next.next.next.next.next = new Node(5);
        
        printLinkedList(head1);
        // head1 = listPartition1(head1, 4);  // 使用方法1
        head1 = listPartition2(head1, 5);     // 使用方法2，以5为基准分区
        printLinkedList(head1);
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
