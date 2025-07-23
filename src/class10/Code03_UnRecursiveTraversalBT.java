package class10;

import java.util.Stack;

public class Code03_UnRecursiveTraversalBT {

    /**
     * 前序遍历（非递归实现）
     * 遍历顺序：根 -> 左 -> 右
     * 算法思路：使用栈，先压右子树，再压左子树，保证左子树先弹出
     */
    public static void pre(Node head) {
        System.out.print("pre-order: ");
        if (head != null) {
            Stack<Node> stack = new Stack<Node>();
            stack.push(head); // 1. 根节点入栈
            while (!stack.isEmpty()) {
                head = stack.pop(); // 2. 弹出节点并处理
                System.out.print(head.value + " "); // 3. 立即打印（前序：根先处理）
                // 4. 先压右子树，再压左子树（栈的LIFO特性保证左子树先弹出）
                if (head.right != null) {
                    stack.push(head.right); // 右子树后处理
                }
                if (head.left != null) {
                    stack.push(head.left);  // 左子树先处理
                }
            }
        }
        System.out.println();
    }

    /**
     * 中序遍历（非递归实现）
     * 遍历顺序：左 -> 根 -> 右
     * 算法思路：一直往左走到底，回退时处理节点，然后转向右子树
     */
    public static void in(Node cur) {
        System.out.print("in-order: ");
        if (cur != null) {
            Stack<Node> stack = new Stack<Node>();
            // 循环条件：栈非空 或 当前节点非空
            while (!stack.isEmpty() || cur != null) {
                if (cur != null) {
                    // 1. 当前节点非空：压栈并继续向左
                    stack.push(cur);     // 当前节点入栈
                    cur = cur.left;      // 继续向左子树
                } else {
                    // 2. 当前节点为空：从栈中弹出节点处理
                    cur = stack.pop();   // 弹出栈顶节点
                    System.out.print(cur.value + " "); // 处理节点（中序：左子树处理完后处理根）
                    cur = cur.right;     // 转向右子树
                }
            }
        }
        System.out.println();
    }

    /**
     * 后序遍历方法1（使用两个栈）
     * 遍历顺序：左 -> 右 -> 根
     * 算法思路：第一个栈产生"根->右->左"的顺序，第二个栈逆序得到"左->右->根"
     */
    public static void pos1(Node head) {
        System.out.print("pos-order: ");
        if (head != null) {
            Stack<Node> s1 = new Stack<Node>(); // 辅助栈1：用于遍历
            Stack<Node> s2 = new Stack<Node>(); // 辅助栈2：用于收集结果
            s1.push(head);
            while (!s1.isEmpty()) {
                head = s1.pop();    // 从s1弹出节点
                s2.push(head);      // 放入s2（用于最后逆序输出）
                // 先压左子树，再压右子树（与前序相反，产生"根->右->左"的顺序）
                if (head.left != null) {
                    s1.push(head.left);
                }
                if (head.right != null) {
                    s1.push(head.right);
                }
            }
            // s2中的顺序是"根->右->左"，逆序输出得到"左->右->根"
            while (!s2.isEmpty()) {
                System.out.print(s2.pop().value + " ");
            }
        }
        System.out.println();
    }

    /**
     * 后序遍历方法2（使用一个栈）
     * 遍历顺序：左 -> 右 -> 根
     * 算法思路：记录最近处理的节点，只有当左右子树都处理完才处理当前节点
     */
    public static void pos2(Node h) {
        System.out.print("pos-order: ");
        if (h != null) {
            Stack<Node> stack = new Stack<Node>();
            stack.push(h);
            Node c = null; // 当前栈顶节点
            while (!stack.isEmpty()) {
                c = stack.peek(); // 查看栈顶节点（不弹出）
                // 情况1：左子树存在且未处理（h不是c的左子树也不是c的右子树）
                if (c.left != null && h != c.left && h != c.right) {
                    stack.push(c.left); // 压入左子树
                }
                // 情况2：右子树存在且未处理（h不是c的右子树）
                else if (c.right != null && h != c.right) {
                    stack.push(c.right); // 压入右子树
                }
                // 情况3：左右子树都已处理完毕，可以处理当前节点
                else {
                    System.out.print(stack.pop().value + " "); // 弹出并处理
                    h = c; // 更新最近处理的节点
                }
            }
        }
        System.out.println();
    }

    /**
     * 二叉树节点定义
     */
    public static class Node {
        public int value;   // 节点值
        public Node left;   // 左子树
        public Node right;  // 右子树

        public Node(int v) {
            value = v;
        }
    }

}
