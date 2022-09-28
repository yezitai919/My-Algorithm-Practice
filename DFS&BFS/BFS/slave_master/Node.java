package slave_master;

/**
 * @Author jinjun99
 * @Date Created in 2021/11/26 19:34
 * @Description
 * @Since version-1.0
 */
public class Node {

    Node parent;

    /**
     * 决策：本次乘船安排
     * 下标0是野人数，1是传教士数
     */
    int[] decision = new int[2];

    /**
     * 船到岸后的状态：
     * 下标0,1分别是野人在左岸，右岸的人数
     * 下标2,3分别是传教士在左岸，右岸的人数
     */
    int[] state = new int[4];

    /**
     * 阶段：船往返的次数
     */
    int level = 0;

    public Node(Node parent, int[] decision, int[] state, int level) {
        this.parent = parent;
        this.decision = decision;
        this.state = state;
        this.level = level;
    }

    public Node(int n) {
        state[0] = n;
        state[2] = n;
    }
}
