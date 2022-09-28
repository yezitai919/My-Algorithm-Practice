package tsp;

import java.util.Stack;

/**
 * @Author jinjun99
 * @Date Created in 2021/11/19 14:32
 * @Description
 * @Since version-1.0
 */
public class TravelingSP {
    //城市数量
    private static int n = 4;
    //开始的城市
    private static int startCity = 1;
    //地图
    private static int[][] map = new int[n+1][n+1];
    //走过的城市
    private static boolean[] over = new boolean[n+1];
    //当前路程
    private static int currS = 0;
    //全程最短路径
    private static int bestS = Integer.MAX_VALUE;
    //记录当前路径
    private static Stack<Integer> currX = new Stack<>();
    //记录最优值的路径
    private static Stack<Integer> bestX = new Stack<>();

    public static void main(String[] args) {
        //地图数据初始化
        map[1][2] = 30;map[1][3] = 6; map[1][4] = 4;
        map[2][1] = 30;map[2][3] = 5; map[2][4] = 10;
        map[3][1] = 6; map[3][2] = 5; map[3][4] = 20;
        map[4][1] = 4; map[4][2] = 10;map[4][3] = 20;
        currX.push(startCity);
        over[startCity]=true;
        backTrack(startCity);
        System.out.println("最优值是："+bestS);
        System.out.println("最优解是："+bestX);
    }
    private static void backTrack(int cityNode){
        if (currX.size()==n){
            if (map[currX.peek()][startCity]!=0){
                currS += map[currX.peek()][startCity];
                currX.push(startCity);
                if (currS<bestS){
                    bestS=currS;
                    bestX = (Stack<Integer>) currX.clone();
                }
                currX.pop();
                currS -= map[currX.peek()][startCity];
            }
            return;
        }
        for (int i = 1; i <= n; i++) {
            if (map[cityNode][i]!=0&& !over[i]){
                currS += map[cityNode][i];
                over[i] = true;
                currX.push(i);
                if (currS<bestS){
                    backTrack(i);
                }
                currS-=map[cityNode][i];
                over[i] = false;
                currX.pop();
            }
        }
    }
}
