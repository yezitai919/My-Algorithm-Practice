package battle_city;

/**
 * @Author jinjun99
 * @Date Created in 2021/11/28 22:29
 * @Description
 * @Since version-1.0
 */
public class Node {
    Node parent;
    /**
     * 决策：0-3表示向上右下左四个方向移动，4-7表示向四个方向射击。
     */
    int dir = 0;
    /**
     * 状态：决策后的位置
     */
    int[] position = new int[2];

    /**
     * 阶段:回合数
     */
    int turns = 0;
    /**
     * 标记
     */
    char[][] mark;
    /**
     * 优先级
     */
    int priority = 0;

    public Node(Node parent, int dir, int[] position, int dist, char[][] mark, int priority) {
        this.parent = parent;
        this.dir = dir;
        this.position = position;
        this.turns = dist;
        this.mark = mark;
        this.priority = priority;
    }

    public Node() {
    }
}
