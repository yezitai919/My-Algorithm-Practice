package burning_trees;

/**
 * @Author jinjun99
 * @Date Created in 2021/12/7 12:51
 * @Description
 * @Since version-1.0
 */
public class Node {
    /**
     * 父结点
     */
    Node parent;
    /**
     * 决策：当前结点砍的树的坐标
     */
    int[] cut;
    /**
     * 状态1：当前决策后的地图，用于传给dfs计算子结点的状态2；
     */
    int[][] map;
    /**
     * 状态2：当前决策后剩下的可燃树的坐标
     */
    PointArrays trees;
    /**
     * 阶段：当前结点砍了记棵树
     */
    int sum;
    /**
     * 优先级：剩下几棵树，越多优先级越高
     */
    int priority;

    public Node(Node parent, int[] cut, int[][] map, PointArrays trees, int sum, int priority) {
        this.parent = parent;
        this.cut = cut;
        this.map = map;
        this.trees = trees;
        this.sum = sum;
        this.priority = priority;
    }

    public Node() {
    }
}
