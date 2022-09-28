package tank;

/**
 * @Author jinjun99
 * @Date Created in 2021/11/27 11:41
 * @Description
 * @Since version-1.0
 */
public class Node {
    Node parent;

    /**
     * 决策，1-8分别表示8个方向，0表示不动
     */
    int[] decision = new int[2];

    /**
     * 状态：[0,1],[2,3]分别是A,B在地图数组中的位置下标
     */
    int[] state = new int[4];

    /**
     * 阶段：第几秒
     */
    int level = 0;

    /**
     * 标记当前路径走过的点
     */
    int[][]mark;

    /**
     * 优先级
     */
    int priority = 0;


    public Node(Node parent, int[] decision, int[] state, int level, int[][] mark, int priority) {
        this.parent = parent;
        this.decision = decision;
        this.state = state;
        this.level = level;
        this.mark = mark;
        this.priority = priority;
    }

    public Node() {
    }
}
