package push_box;

/**
 * @Author jinjun99
 * @Date Created in 2021/11/28 19:17
 * @Description
 * @Since version-1.0
 */
public class Node {
    Node parent;

    /**
     * 决策：0,1,2,3表示上下左右四种方向
     */
    int dir = 0;
    /**
     * 状态：决策后的位置
     */
    int[][] position = new int[4][2];

    /**
     * 阶段：当前走过的步数；
     */
    int dist = 0;

    /**
     * 进洞箱子数量
     */
    int intoNum = 0;

    // int[][] mark;

    /**
     * 优先级
     */
    int priority = 0;

    public Node(Node parent, int dir, int[][] position, int dist, int intoNum, int priority) {
        this.parent = parent;
        this.dir = dir;
        this.position = position;
        this.dist = dist;
        this.intoNum = intoNum;
        this.priority = priority;
    }

    public Node() {
    }
}
