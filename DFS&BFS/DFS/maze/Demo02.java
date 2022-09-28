package maze;


/**
 * @Author jinjun99
 * @Date Created in 2021/11/20 10:38
 * @Description 求迷宫从起点到终点共有多少条路径
 * @Since version-1.0
 */
public class Demo02 {
    //迷宫行列数
    private static int n = 8;
    //迷宫图
    private static int[][] maze = {
            {0,0,0,1,0,0,0,0},
            {0,0,0,1,0,1,0,0},
            {0,1,1,1,0,1,0,0},
            {0,0,0,0,0,1,0,0},
            {1,1,1,1,1,1,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,1,1,1,1,1,1},
            {0,0,0,0,0,0,0,0},};
    //标记走过的位置
    private static int[][] sign = new int[n][n];



    private static int dfs(int x,int y){
        //如果越界，撞墙或已走过，返回0
        if (x==n||x<0||y==n||y<0||sign[x][y]==1||maze[x][y]==1){
            return 0;
        }
        //如果走到终点，返回1
        if (x==n-1&&y==n-1){
            return 1;
        }
        //标记走过的点
        sign[x][y]=1;
        int count = 0;
        count += dfs(x+1,y);
        count += dfs(x-1,y);
        count += dfs(x,y+1);
        count += dfs(x,y-1);
        //回溯
        sign[x][y]=0;
        return count;
    }

    public static void main(String[] args) {
        int sum = dfs(0,0);
        System.out.println("路径数："+sum);
    }
}
/*路径数：384*/
