package knapsack_problem;

/**
 * @Author jinjun99
 * @Date Created in 2021/11/25 15:11
 * @Description
 * @Since version-1.0
 */
public class Node {
    /**
     * 父结点
     */
    Node parent;

    /**
     * 结点状态：当前放入背包的物品总重量
     */
    int nodeState1 = 0;

    /**
     * 当前放入背包的物品总价值
     */
    int nodeState2 = 0;


    /**
     * 阶段：结点在解空间树中的层数，即当前要决策的物品编号
     */
    int level = 0;

    /**
     * 结点决策：左子结点为1，表示当前物品放入背包
     */
    int decision = 0;
    /**
     * 优先级：上界函数值
     */
    int priority = 0;

    public Node(Node parent, int nodeState1, int nodeState2, int level, int decision, int priority) {
        this.parent = parent;
        this.nodeState1 = nodeState1;
        this.nodeState2 = nodeState2;
        this.level = level;
        this.decision = decision;
        this.priority = priority;
    }

    public Node() {
    }
}
