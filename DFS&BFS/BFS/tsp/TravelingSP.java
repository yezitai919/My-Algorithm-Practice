package tsp;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @Author jinjun99
 * @Date Created in 2021/11/26 13:38
 * @Description
 * @Since version-1.0
 */
public class TravelingSP {
    /**
     * 城市数量
     */
    private static int n = 4;
    /**
     * 出发城市
     */
    private static int startCity = 1;
    /**
     * 地图邻接矩阵
     */
    private static int[][] map = {
            { 0,30, 6, 4},
            {30, 0, 5,10},
            { 6, 5, 0,20},
            { 4,10,20, 0}};
    /**
     * 最优值结点
     */
    private static Node  optimalValue = new Node(null,Integer.MAX_VALUE,0,0,null);

    /**
     *
     */
    private static void bfs(){
        //活结点队列
       // PriorityQueue<Node> liveNode = new PriorityQueue<>(Comparator.comparingInt(node -> node.dist));
        PriorityList liveNode = new PriorityList();
        int[] pass = new int[n];
        pass[startCity-1] = 1;
        liveNode.offer(new Node(null,0,0,startCity,pass));
        while (!liveNode.isEmpty()){
            Node parent = liveNode.poll();
            for (int i = 1; i <= n; i++) {
                if (map[parent.cityNum-1][i-1]!=0&&parent.passingCity[i-1]==0){
                    int dist = parent.dist + map[parent.cityNum-1][i-1];
                    int station = parent.station + 1;
                    int[] passCity = Arrays.copyOf(parent.passingCity,n);
                    passCity[i-1] = 1;
                    Node child = new Node(parent,dist,station,i,passCity);
                    //非叶子结点入队
                    if (station < n-1){
                       // System.out.println("dist"+child.dist);
                      //  System.out.println("station"+child.station);
                        liveNode.offer(child);
                    }else {
                        //叶子结点才比较最短距离
                        child.dist += map[i-1][startCity-1];
                       // System.out.println(child.dist);
                        if (child.dist < optimalValue.dist){
                            optimalValue = child;
                        }
                        //如果当前叶子结点的最短距离比所有活结点都短，那就不用遍历剩下的活结点了
                        if (!liveNode.isEmpty()&&optimalValue.dist<liveNode.peek().dist){
                            break;
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        bfs();
        System.out.println("最短距离为："+optimalValue.dist);
        int[] s = new int[n+1];
        s[0] = startCity;
        s[n] = startCity;
        for (int i = n-1; i > 0; i--) {
            s[i] = optimalValue.cityNum;
            optimalValue = optimalValue.parent;
        }
        System.out.println(Arrays.toString(s));

    }

}
