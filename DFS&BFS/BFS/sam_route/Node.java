package sam_route;

/**
 * @Author jinjun99
 * @Date Created in 2021/11/28 0:14
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
    int[] position = new int[2];

    /**
     * 阶段：当前路径行驶的距离
     */
    int dist = 0;

    /**
     * 标记
     */
    int[][] mark;

    /**
     * 优先级，当前位置到终点的距离越小优先级越高
     */
    int priority = 0;

    public Node(Node parent, int dir, int[] position, int dist, int[][] mark, int priority) {
        this.parent = parent;
        this.dir = dir;
        this.position = position;
        this.dist = dist;
        this.mark = mark;
        this.priority = priority;
    }

    public Node() {
    }
}
