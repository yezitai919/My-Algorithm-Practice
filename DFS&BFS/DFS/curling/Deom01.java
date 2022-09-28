package curling;

import java.util.Stack;

/**
 * @Author jinjun99
 * @Date Created in 2021/11/20 19:47
 * @Description
 * @Since version-1.0
 */
public class Deom01 {
    private static int n = 8;
    private static int[][] map = {
            {2,0,0,1,0,0,0,0},
            {0,0,0,1,0,1,0,0},
            {0,1,1,1,0,1,0,0},
            {0,0,0,0,0,0,0,1},
            {1,1,1,1,1,1,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,1,1,1,1,3,1},
            {0,0,0,0,0,0,0,0},};
    private static int[] dirX = {0,1,0,-1};
    private static int[] dirY = {1,0,-1,0};

    private static int currStep = 0;
    private static int minStep = 0;
    public static void main(String[] args) {
        map[0][0] = 2;
        map[6][7] = 3;
        dfs(0,0);
        System.out.println("最小步数为："+minStep);
    }
    private static void dfs(int x, int y){

        /*四个方向*/
        for (int i = 0; i < 4; i++) {
            int a = x;
            int b = y;
            /*移动步数*/
            for (int j = 0; j <10; j++) {
                /*更新本次移动后的位置*/
                a+=dirX[i];
                b+=dirY[i];
                /*如果出界则退出游戏*/
                if (a<0||a>=n||b<0||b>=n){
                    break;
                }
                /*如果到达终点*/
                if (map[a][b]==3){
                // 统计当前步数
                    currStep+=(j+1);
                    if (currStep<minStep||minStep==0){
                        minStep = currStep;
                    }
                    currStep-=(j+1);
                    break;
                }
                /*如果撞到墙，则停下然后墙消失*/
                if (map[a][b]==1){
                    currStep+=j;
                    map[a][b]=0;
                    dfs(a-dirX[i],b-dirY[i]);
                    //currStep-=j;
                   // map[a][b]=1;
                   // break;
                }
            }
        }
    }
}
/*最小步数为：12*/
