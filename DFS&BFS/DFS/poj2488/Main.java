package poj2488;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @Author jinjun99
 * @Date Created in 2022/9/26 14:43
 * @Description
 * @Since version-1.0
 */
public class Main {
    /**
     * 案例数
     */
    static int n;
    /**
     * 长
     */
    static int p;
    /**
     * 宽
     */
    static int q;
    /**
     * 棋盘
     */
    static int[][] board;
    /**
     * 字典序方向
     */
    static int[][] dir = {{-2,-1},{-2,1},{-1,-2},{-1,2},{1,-2},{1,2},{2,-1},{2,1}};
    /**
     * 当前路径
     */
    static String[] currPath;
    /**
     * 最终路径
     */
    static String[] finalPath;
    /**
     * 格子数
     */
    static int cells;
    /**
     * 搜索开关
     */
    static boolean search;
    /**
     * 可能性
     */
    static boolean possibility;
    public static void main(String[] args) {
  /*      String str = (char)('A'+1)+""+2;
        System.out.println(str);*/
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();

        for (int i = 0; i < n; i++) {
            q = sc.nextInt();
            p = sc.nextInt();
            board = new int[p][q];
            board[0][0]=1;
            cells = p*q;
            currPath = new String[cells];
            finalPath = new String[cells];
            currPath[0] = "A1";
            search = true;
            possibility = false;
            System.out.println("Scenario #"+(i+1)+":");
            if (cells==1){
                System.out.println("A1");
                System.out.println(" ");
            }else {

                dfs(0,0,1);
                if (possibility){
                    for (int j = 0; j < cells; j++) {
                        if (j==cells-1){
                            System.out.println(finalPath[j]);
                        }else {
                            System.out.print(finalPath[j]);
                        }
                    }
                }else {
                    System.out.println("impossible");
                }
                System.out.println("");
            }

        }
    }


    private static void dfs(int x,int y,int c) {

        if (c==cells){
            search = false;
            possibility = true;
            for (int i = 0; i < cells; i++) {
                finalPath[i]=currPath[i];
            }
            return;
        }
        for (int i = 0; i < 8 && search; i++) {
            int a = x+dir[i][0];
            int b = y+dir[i][1];
            if (a>=0&&a<p&&b>=0&&b<q&&board[a][b]==0){
                board[a][b] = 1;
                String str = (char)('A'+a)+""+(b+1);
                currPath[c] = str;
                dfs(a,b,c+1);
                board[a][b] = 0;
                currPath[c] = "";
            }
        }
    }
}
/*
3
1 1
4 3
2 3
3
1 1
2 3
4 3
*/