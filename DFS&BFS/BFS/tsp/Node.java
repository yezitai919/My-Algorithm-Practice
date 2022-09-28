package tsp;

/**
 * @Author jinjun99
 * @Date Created in 2021/11/26 13:40
 * @Description
 * @Since version-1.0
 */
public class Node {

    Node parent;
    /**
     * 状态：走过的路程，也作为优先级，越短优先级越高
     */
    int dist = 0;

    /**
     * 阶段：解空间树层号，在第几个城市
     */
    int station = 0;

    /**
     * 决策：当前选择的城市编号
     */
    int cityNum = 0;

    /**
     * 走过的城市,1表示走过
     */
    int[] passingCity;

    public Node(Node parent, int dist, int station, int cityNum, int[] passingCity) {
        this.parent = parent;
        this.dist = dist;
        this.station = station;
        this.cityNum = cityNum;
        this.passingCity = passingCity;
    }

    public Node() {
    }
}
