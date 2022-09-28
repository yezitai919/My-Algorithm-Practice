package loading_problem;

/**
 * @Author jinjun99
 * @Date Created in 2021/11/24 17:28
 * @Description
 * @Since version-1.0
 */
public class Node {
    /**
     * 父结点
     */
    Node parent;
    /**
     * 当前结点状态：第一艘船当前载重量
     */
    int nodeW = 0;
    /**
     * 结点层号：集装箱编号
     */
    int level = 0;
    /**
     * 左结点为1，其他结点为0
     */
    int left = 0;

    public Node(Node parent, int nodeW, int level, int left) {
        this.parent = parent;
        this.nodeW = nodeW;
        this.level = level;
        this.left = left;
    }

    public Node() {
    }
}
