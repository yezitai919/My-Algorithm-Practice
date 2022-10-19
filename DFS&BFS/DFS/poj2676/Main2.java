package poj2676;

import java.util.Scanner;

/**
 * @Author jinjun99
 * @Date Created in 2022/10/6 20:14
 * @Description
 * @Since version-1.0
 */
public class Main2 {
    static int n = 10;
    static int[][] map;
    static boolean[][] row;
    static boolean[][] column;
    static boolean[][] grid;
    /**
     * 找到一行的解标记为true
     */
    static boolean filledRow;

    private static void dfs(int x, int y) {

        if (x == n) {
            /*第10行越界，说明已经填完9行找到一个可行解，
            结束递归，并标记成功，取消一切回溯递归操作*/
            filledRow = true;
            return;
        }

        /*当前格子有数字*/
        if (map[x][y] != 0) {
            /*一行的最后一格有数字，直接跳到下一行*/
            if (y == 9) {
                dfs(x + 1, 1);
            } else {/*否则在当前行向右推进*/
                dfs(x, y + 1);
            }
        } else {
            /*如果没数字就可以填*/
            /*k表示第几个方格*/
            int k = 3 * ((x - 1) / 3) + (y - 1) / 3 + 1;
            for (int i = 1; i < n; i++) {
                if (!row[x][i] && !column[y][i] && !grid[k][i]) {
                    /*三个条件都满足则填入数字*/
                    map[x][y] = i;
                    row[x][i] = true;
                    column[y][i] = true;
                    grid[k][i] = true;
                    /*一行的最后一格有数字，直接跳到下一行*/
                    if (y == 9) {
                        dfs(x + 1, 1);
                    } else {/*否则在当前行向右推进*/
                        dfs(x, y + 1);
                    }
                    /*如果前面的递归填完9行找到一个解，就在这里截断递归和回溯*/
                    if (filledRow){
                        return;
                    }
                    map[x][y] = 0;
                    row[x][i] = false;
                    column[y][i] = false;
                    grid[k][i] = false;
                }
            }
        }


    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int m = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < m; i++) {
            initData();

            for (int j = 1; j < n; j++) {
                String str = sc.nextLine();
                for (int k = 1; k < n; k++) {
                    int num = str.charAt(k - 1) - '0';
                    map[j][k] = num;
                    if (num > 0) {
                        row[j][num] = true;
                        column[k][num] = true;
                        int g = 3 * ((j - 1) / 3) + (k - 1) / 3 + 1;
                        grid[g][num] = true;
                    }

                }
            }
            dfs(1, 1);
            for (int j = 1; j < n; j++) {
                for (int k = 1; k < n; k++) {
                    if (k == n - 1) {
                        System.out.println(map[j][k]);
                    } else {
                        System.out.print(map[j][k]);
                    }
                }
            }
        }

    }

    private static void initData() {
        map = new int[n + 5][n + 5];
        row = new boolean[n + 5][n + 5];
        column = new boolean[n + 5][n + 5];
        grid = new boolean[n + 5][n + 5];
        filledRow = false;
    }
}
/*
1
103000509
002109400
000704000
300502006
060000050
700803004
000401000
009205800
804000107

*/

