package knights_journey;

import java.util.Stack;

/**
 * @Author jinjun99
 * @Date Created in 2021/11/20 15:32
 * @Description
 * @Since version-1.0
 */
public class Demo {
    private static int n = 4;
    static int m = 3;
    /**
     * 棋盘
     */
    private static int[][] cb = new int[n][m];
    /**
     * 方向
     */
    private static int[][] dir = {{-1,-2},{1,-2},{-2,-1},{2,-1},{-2,1},{2,1},{-1,2},{1,2}};
    private static Stack<Integer[]> currPath = new Stack<>();
    private static Stack<Integer[]> finalPath = new Stack<>();
    private static int end = 0;
    private static void dfs(int a,int b){
        if (currPath.size()==n*m){
            finalPath = (Stack<Integer[]>) currPath.clone();
            end =1;
            return ;
        }
        for (int i = 0; i < 8 && end ==0; i++) {
            int q = a+dir[i][0];
            int p = b+dir[i][1];
            if (q<n && q>-1 && p<m && p>-1 && cb[q][p]==0){
                cb[q][p] = 1;
                currPath.push(new Integer[]{q,p});
                dfs(q,p);
                cb[q][p] = 0;
                currPath.pop();
            }
        }
    }
    public static void main(String[] args) {
        cb[2][1] = 1;
        currPath.push(new Integer[]{2,1});
        dfs(2,1);
        int[][] path = new int[n][m];
        path[2][1] = 1;
        for (int i = n*m; i > 0; i--) {
            Integer[] a = finalPath.pop();
            int p = (int)a[0];
            int q = (int)a[1];
            path[p][q] = i;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (path[i][j]<10){
                    System.out.print(" "+path[i][j]+" ");
                }else {
                    System.out.print(path[i][j]+" ");
                }

                if (j==n-1){
                    System.out.println();
                }
            }
        }
    }
}
/*输出：
 2  5 16 13 36 25
 7 12  3 26 17 14
 4  1  6 15 24 35
11  8 31 20 27 18
32 21 10 29 34 23
 9 30 33 22 19 28  */