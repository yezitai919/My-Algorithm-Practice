package maze;

import java.util.Arrays;
import java.util.Stack;

/**
 * @Author jinjun99
 * @Date Created in 2021/11/19 18:11
 * @Description
 * @Since version-1.0
 */
public class Demo01 {
    private static int n = 8;//结点数
    //当前路径，以二维数组存储一步的坐标,size就是步数
    private static Stack<Integer[]> currPath = new Stack<>();
    //最优路径
    private static Stack<Integer[]> shortestPath = new Stack<>();
    //迷宫图
    private static int[][] maze = {
            {1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,1,0,0,0,0,1},
            {1,0,0,0,1,0,1,0,0,1},
            {1,0,1,1,1,0,1,0,0,1},
            {1,0,0,0,0,0,1,0,0,1},
            {1,1,1,1,1,1,1,0,0,1},
            {1,0,0,0,0,0,0,0,0,1},
            {1,0,0,1,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,0,0,1},
            {1,1,1,1,1,1,1,1,1,1}
    };
    public static void main(String[] args) {
        backtrack(1,1);
        System.out.println("最短路径为：");
        for (int i = 0; i < shortestPath.size(); i++) {
            Integer[] step = shortestPath.get(i);
            maze[step[0]][step[1]]=i+1;
            if (i== shortestPath.size()-1){
                System.out.println(Arrays.toString(step));
            }else if ((i+1)%5==0){
                System.out.println(Arrays.toString(step)+"-->");
            }else {
                System.out.print(Arrays.toString(step)+"-->");
            }
        }
        System.out.println("在迷宫中表示为：");
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (j==9){
                    if (maze[i][j]>=10) {
                        System.out.println(maze[i][j]+" ");
                    }else {
                        System.out.println(" "+maze[i][j]+" ");
                    }
                }else {
                    if (maze[i][j]>=10){
                        System.out.print(maze[i][j]+" ");
                    }else {
                        System.out.print(" "+maze[i][j]+" ");
                    }
                }
            }
        }
    }
    private static void backtrack(int i, int j){
        //屏蔽当前落脚位置
        maze[i][j]= 1;
        currPath.add(new Integer[]{i,j});
        //到达出口
        if (i == n && j == n){
            if (shortestPath.size()==0||currPath.size() < shortestPath.size()){
                shortestPath = (Stack<Integer[]>) currPath.clone();
            }
            return;
        }
        //向右一步
        if (maze[i+1][j] == 0) {
            backtrack(i+1,j);
        }
        //向上一步
        if (maze[i][j+1] == 0) {
            backtrack(i,j+1);
        }
        //向左一步
        if (maze[i-1][j] == 0) {
            backtrack(i-1,j);
        }
        //向下一步
        if (maze[i][j-1] == 0) {
            backtrack(i,j-1);
        }
        //回溯复原
        maze[i][j]=0;
        currPath.pop();
    }
}
