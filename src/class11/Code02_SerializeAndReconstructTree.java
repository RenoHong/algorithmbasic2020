package class11;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Code02_SerializeAndReconstructTree {
    
    /**
     * 前序遍历序列化二叉树
     * 将二叉树按照"根-左-右"的顺序转换为字符串序列
     * @param head 二叉树根节点
     * @return 序列化后的字符串队列
     */
    public static Queue<String> preSerial(Node head) {
        Queue<String> ans = new LinkedList<>();  // 存储序列化结果的队列
        pres(head, ans);                         // 递归进行前序遍历序列化
        return ans;
    }

    /**
     * 前序遍历序列化的递归实现
     * @param head 当前节点
     * @param ans 结果队列
     */
    public static void pres(Node head, Queue<String> ans) {
        if (head == null) {
            ans.add(null);                       // 空节点用null表示
        } else {
            ans.add(String.valueOf(head.value)); // 先处理根节点
            pres(head.left, ans);                // 递归处理左子树
            pres(head.right, ans);               // 递归处理右子树
        }
    }

    /**
     * 中序遍历序列化二叉树
     * 将二叉树按照"左-根-右"的顺序转换为字符串序列
     * 注意：中序遍历无法唯一确定二叉树结构，仅用于演示
     */
    public static Queue<String> inSerial(Node head) {
        Queue<String> ans = new LinkedList<>();
        ins(head, ans);
        return ans;
    }

    /**
     * 中序遍历序列化的递归实现
     */
    public static void ins(Node head, Queue<String> ans) {
        if (head == null) {
            ans.add(null);
        } else {
            ins(head.left, ans);                 // 先处理左子树
            ans.add(String.valueOf(head.value)); // 再处理根节点
            ins(head.right, ans);                // 最后处理右子树
        }
    }

    /**
     * 后序遍历序列化二叉树
     * 将二叉树按照"左-右-根"的顺序转换为字符串序列
     */
    public static Queue<String> posSerial(Node head) {
        Queue<String> ans = new LinkedList<>();
        poss(head, ans);
        return ans;
    }

    /**
     * 后序遍历序列化的递归实现
     */
    public static void poss(Node head, Queue<String> ans) {
        if (head == null) {
            ans.add(null);
        } else {
            poss(head.left, ans);                // 先处理左子树
            poss(head.right, ans);               // 再处理右子树
            ans.add(String.valueOf(head.value)); // 最后处理根节点
        }
    }

    /**
     * 根据前序遍历序列化结果重建二叉树
     * 时间复杂度：O(N)，空间复杂度：O(N)
     * @param prelist 前序遍历的序列化结果
     * @return 重建的二叉树根节点
     */
    public static Node buildByPreQueue(Queue<String> prelist) {
        if (prelist == null || prelist.size() == 0) {
            return null;
        }
        return preb(prelist);                    // 递归重建
    }

    /**
     * 前序遍历重建的递归实现
     * 算法思路：按照"根-左-右"的顺序从队列中依次取出节点值进行重建
     * @param prelist 前序遍历序列
     * @return 当前子树的根节点
     */
    public static Node preb(Queue<String> prelist) {
        String value = prelist.poll();           // 取出队列头部元素（当前根节点）
        if (value == null) {
            return null;                         // 空节点直接返回null
        }
        Node head = new Node(Integer.valueOf(value)); // 创建根节点
        head.left = preb(prelist);               // 递归重建左子树
        head.right = preb(prelist);              // 递归重建右子树
        return head;                             // 返回当前子树根节点
    }

    /**
     * 根据后序遍历序列化结果重建二叉树
     * 由于后序遍历是"左-右-根"，需要反向处理
     * @param poslist 后序遍历的序列化结果
     * @return 重建的二叉树根节点
     */
    public static Node buildByPosQueue(Queue<String> poslist) {
        if (poslist == null || poslist.size() == 0) {
            return null;
        }
        // 核心思路：将后序序列"左-右-根"倒序得到"根-右-左"
        Stack<String> stack = new Stack<>();
        while (!poslist.isEmpty()) {
            stack.push(poslist.poll());          // 将队列元素全部压入栈中反序
        }
        return posb(stack);                      // 递归重建
    }

    /**
     * 后序遍历重建的递归实现
     * 算法思路：栈中的顺序是"根-右-左"，按此顺序重建
     * @param posstack 反序后的后序遍历栈
     * @return 当前子树的根节点
     */
    public static Node posb(Stack<String> posstack) {
        String value = posstack.pop();           // 取出栈顶元素（当前根节点）
        if (value == null) {
            return null;                         // 空节点直接返回null
        }
        Node head = new Node(Integer.valueOf(value)); // 创建根节点
        head.right = posb(posstack);             // 先重建右子树（栈中右子树在左子树前面）
        head.left = posb(posstack);              // 再重建左子树
        return head;                             // 返回当前子树根节点
    }

    /**
     * 层序遍历序列化二叉树
     * 按层次从上到下、从左到右的顺序序列化
     * @param head 二叉树根节点
     * @return 序列化后的字符串队列
     */
    public static Queue<String> levelSerial(Node head) {
        Queue<String> ans = new LinkedList<>();
        if (head == null) {
            ans.add(null);                       // 空树直接返回null
        } else {
            ans.add(String.valueOf(head.value)); // 根节点加入结果
            Queue<Node> queue = new LinkedList<Node>(); // 辅助队列用于层序遍历
            queue.add(head);                     // 根节点入队
            while (!queue.isEmpty()) {
                head = queue.poll();             // 取出队头节点作为父节点
                // 处理左子节点
                if (head.left != null) {
                    ans.add(String.valueOf(head.left.value)); // 左子节点值加入结果
                    queue.add(head.left);        // 左子节点入队等待处理其子节点
                } else {
                    ans.add(null);               // 左子节点为空，加入null
                }
                // 处理右子节点
                if (head.right != null) {
                    ans.add(String.valueOf(head.right.value)); // 右子节点值加入结果
                    queue.add(head.right);       // 右子节点入队等待处理其子节点
                } else {
                    ans.add(null);               // 右子节点为空，加入null
                }
            }
        }
        return ans;
    }

    /**
     * 根据层序遍历序列化结果重建二叉树
     * 时间复杂度：O(N)，空间复杂度：O(N)
     * @param levelList 层序遍历的序列化结果
     * @return 重建的二叉树根节点
     */
    public static Node buildByLevelQueue(Queue<String> levelList) {
        if (levelList == null || levelList.size() == 0) {
            return null;
        }
        Node head = generateNode(levelList.poll()); // 创建根节点
        Queue<Node> queue = new LinkedList<Node>(); // 辅助队列
        if (head != null) {
            queue.add(head);                     // 根节点入队
        }
        Node node = null;
        while (!queue.isEmpty()) {
            node = queue.poll();                 // 取出父节点
            // 为父节点创建左右子节点
            node.left = generateNode(levelList.poll());  // 创建左子节点
            node.right = generateNode(levelList.poll()); // 创建右子节点
            // 非空子节点入队，等待处理其子节点
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }
        return head;
    }

    /**
     * 辅助方法：根据字符串值创建节点
     * @param val 节点值的字符串表示
     * @return 创建的节点，如果val为null则返回null
     */
    public static Node generateNode(String val) {
        if (val == null) {
            return null;
        }
        return new Node(Integer.valueOf(val));
    }

    // for test
    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // for test
    public static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    // for test
    public static boolean isSameValueStructure(Node head1, Node head2) {
        if (head1 == null && head2 != null) {
            return false;
        }
        if (head1 != null && head2 == null) {
            return false;
        }
        if (head1 == null && head2 == null) {
            return true;
        }
        if (head1.value != head2.value) {
            return false;
        }
        return isSameValueStructure(head1.left, head2.left) && isSameValueStructure(head1.right, head2.right);
    }

    // for test
    public static void printTree(Node head) {
        System.out.println("Binary Tree:");
        printInOrder(head, 0, "H", 17);
        System.out.println();
    }

    public static void printInOrder(Node head, int height, String to, int len) {
        if (head == null) {
            return;
        }
        printInOrder(head.right, height + 1, "v", len);
        String val = to + head.value + to;
        int lenM = val.length();
        int lenL = (len - lenM) / 2;
        int lenR = len - lenM - lenL;
        val = getSpace(lenL) + val + getSpace(lenR);
        System.out.println(getSpace(height * len) + val);
        printInOrder(head.left, height + 1, "^", len);
    }

    public static String getSpace(int num) {
        String space = " ";
        StringBuilder buf = new StringBuilder("");
        for (int i = 0; i < num; i++) {
            buf.append(space);
        }
        return buf.toString();
    }

    public static void main(String[] args) {
        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 1000000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            Queue<String> pre = preSerial(head);
            Queue<String> pos = posSerial(head);
            Queue<String> level = levelSerial(head);
            Node preBuild = buildByPreQueue(pre);
            Node posBuild = buildByPosQueue(pos);
            Node levelBuild = buildByLevelQueue(level);
            if (!isSameValueStructure(preBuild, posBuild) || !isSameValueStructure(posBuild, levelBuild)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish!");

    }

    /*
     * 二叉树可以通过先序、后序或者按层遍历的方式序列化和反序列化，
     * 以下代码全部实现了。
     * 但是，二叉树无法通过中序遍历的方式实现序列化和反序列化
     * 因为不同的两棵树，可能得到同样的中序序列，即便补了空位置也可能一样。
     * 比如如下两棵树
     *         __2
     *        /
     *       1
     *       和
     *       1__
     *          \
     *           2
     * 补足空位置的中序遍历结果都是{ null, 1, null, 2, null}
     *
     * */
    public static class Node {
        public int value;   // 节点值
        public Node left;   // 左子节点
        public Node right;  // 右子节点

        public Node(int data) {
            this.value = data;
        }
    }
}
