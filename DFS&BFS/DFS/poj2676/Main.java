package poj2676;

import java.util.Scanner;

/**
 * @Author jinjun99
 * @Date Created in 2022/10/11 15:09
 * @Description
 * @Since version-1.0
 */
public class Main {
    static int n = 10;
    static int spaceNum;
    static int[][] map;
    static boolean[][] row;
    static boolean[][] column;
    static boolean[][] grid;
    /**
     * 每行剩余的空位数
     */
    static int[] rowSpace;
    /**
     * 找到一行的解标记为true
     */
    static boolean filled;

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
                        rowSpace[j]--;
                        spaceNum--;
                        row[j][num] = true;
                        column[k][num] = true;
                        int g = 3 * ((j - 1) / 3) + (k - 1) / 3 + 1;
                        grid[g][num] = true;
                    }

                }
            }
            dfs(spaceNum);
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

    private static void dfs(int sn) {
        if (sn==0){
            filled = true;
            return;
        }
        int[] index = findIndex();
        int x = index[0];
        int y = index[1];
        int k = 3 * ((x - 1) / 3) + (y - 1) / 3 + 1;
        for (int i = 1; i < n; i++) {
            if (!row[x][i] && !column[y][i] && !grid[k][i]) {
                /*三个条件都满足则填入数字*/
                map[x][y] = i;
                row[x][i] = true;
                column[y][i] = true;
                grid[k][i] = true;
                rowSpace[x]--;
                dfs(sn-1);
                /*如果前面的递归填完9行找到一个解，就在这里截断递归和回溯*/
                if (filled){
                    return;
                }
                rowSpace[x]++;
                map[x][y] = 0;
                row[x][i] = false;
                column[y][i] = false;
                grid[k][i] = false;
            }
        }

    }

    private static int[] findIndex(){
        int temp = 0;
        int[] index = new int[4];
        for (int i = 1; i < n; i++) {
            if (rowSpace[i]>temp){
                temp = rowSpace[i];
                index[0] = i;
            }
        }
        for (int i = 1; i < n; i++) {
            if (map[index[0]][i]==0){
                index[1]=i;
            }
        }
        return index;
    }
    private static void initData() {
        map = new int[n + 5][n + 5];
        row = new boolean[n + 5][n + 5];
        column = new boolean[n + 5][n + 5];
        grid = new boolean[n + 5][n + 5];
        rowSpace = new int[n];
        for (int i = 1; i < n; i++) {
            rowSpace[i] = 9;
        }
        spaceNum = 81;
        filled = false;

    }
}

/*
（1）用DFS搜索每个空格子。
（2）用位运算记录格子状态。每行、每列、每个九宫格，分别用一个9位的二进制数保存哪些数字还可以填。
对于每个位置，把它在的行，列，九宫格对应的数取 & 运算就可以得到剩余哪些数可以填。并用lowbit(x)取出能填的数。
（3）优化搜索顺序剪枝。从最容易确定数字的行（或列）开始填数，也就是0最少的行（或列）；
在后续每个状态下，也选择0最少的行（或列）填数。
（4）可行性剪枝。每格填的数只能是对应行、列和宫中没出现过的。

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
