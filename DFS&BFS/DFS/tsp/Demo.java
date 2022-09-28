package tsp;

import java.util.Stack;

/**
 * @Author jinjun99
 * @Date Created in 2021/11/19 15:01
 * @Description
 * @Since version-1.0
 */
public class Demo {
    private static int n = 4;//城市数量
    private static  int starCity = 1;//开始的城市
    private static int[][] map = new int[n+1][n+1];//地图数据
    private static boolean[] citybool = new boolean[n+1];//确定走过的城市
    private static int currentS =0;//记录当前路程
    private static int bestS =Integer.MAX_VALUE;//全部走完后的最短路程(最优值)
    private static Stack<Integer> currentX = new Stack<Integer>();//记录当前路径
    private static Stack<Integer> bestX = new Stack<Integer>();//最优值时的路径(最优解)
    private static long s,e;//记录回溯算法运行时间
    public static void main(String[] args) {
        //地图数据初始化
        map[1][2] = 30;map[1][3] = 6; map[1][4] = 4;
        map[2][1] = 30;map[2][3] = 5; map[2][4] = 10;
        map[3][1] = 6; map[3][2] = 5; map[3][4] = 20;
        map[4][1] = 4; map[4][2] = 10;map[4][3] = 20;
        currentX.push(starCity);//开始城市入栈
        citybool[starCity]=true;//开始城市确定已走过
        s=System.nanoTime();//回溯开始时间
        backtrack(starCity);//开始回溯求解
        e=System.nanoTime();//回溯结束时间
        System.out.println("最优值是："+bestS);
        System.out.println("最优解是："+bestX);
        System.out.println("遍历解空间树用时："+(e-s)+"纳秒");
    }
    private static void backtrack(int cityNode) {//回溯搜索空间树
        if (currentX.size() == n) {
            //每个城市都走过了，准备返回开始城市。
            if (map[currentX.peek()][starCity] != 0) {//判断最后到达的城市是否能直接返回开始城市
                currentS += map[currentX.peek()][starCity];//记录当前路程
                currentX.push(starCity);//记录途径点
                if (currentS < bestS) {//更新最优值和最优解
                    bestS = currentS;
                    bestX = (Stack<Integer>) currentX.clone();
                }
                currentX.pop();//回溯复原
                currentS -= map[currentX.peek()][starCity];
            }
            return;
        }
        for (int i = 1; i <= n; i++) {
            if (map[cityNode][i] != 0 && !citybool[i]) {//如果存在当前城市直达i城的路且没走去过i城就去i城
                currentS += map[cityNode][i];//记录开始城市到i城的路程
                citybool[i] = true;//确定i城走过
                currentX.push(i);//记录途径点
                if (currentS < bestS) {//剪枝
                    backtrack(i); }//递归遍历i节点的子树
                currentS -= map[cityNode][i];//回溯复原
                citybool[i] = false;
                currentX.pop();
            }
        }
    }
}
