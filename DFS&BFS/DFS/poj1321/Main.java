package poj1321;

import java.util.Scanner;

/**
 * @Author jinjun99
 * @Date Created in 2022/10/4 14:13
 * @Description
 * @Since version-1.0
 */
public class Main {
    static int n;
    static int k;
    /**
     * 棋盘
     */
    static char[][] board;
    /**
     * 标记被用过的列
     */
    static int[] check;
    static long schemes;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            n = sc.nextInt();
            k = sc.nextInt();
            if (n == -1 && k == -1) {
                break;
            }
            board = new char[n + 5][n + 5];
            check = new int[n + 5];
            for (int i = 0; i < n; i++) {
                String str = sc.next();
                for (int j = 0; j < n; j++) {
                    board[j][i] = str.charAt(j);
                }
            }
            schemes = 0;

            dfs(0, 0);
            System.out.println(schemes);


        }
    }

    /**
     * @param c  从第几列开始
     * @param ck 当前已放置棋子数
     */
    private static void dfs(int c, int ck) {
        if (ck == k) {
            schemes++;
            return;
        }
        if (c == n) {
            /*出界*/
            return;
        }
        for (int i = 0; i < n; i++) {
            if (board[c][i] == '#' && check[i] == 0) {
                check[i] = 1;
                dfs(c + 1, ck + 1);
                check[i] = 0;
            }
        }
        dfs(c + 1, ck);
    }
}
/*
2 1
#.
.#
4 2
...#
..#.
.#..
#...
8 3
..#.#.#.
...##.#.
.#.#..#.
#.#....#
.##....#
....#.#.
..#.#...
.#...#..
-1 -1
这题被数据坑了，案例太完美不容易测出毛病
第一次错误：写太复杂了，一层是从i列开始，一层是跳过i列，有重复
第二次错误：标记了每一列，应该标记棋子行数。
*/


/* for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (j==n-1){
                        System.out.println(board[j][i]);
                    }else {
                        System.out.print(board[j][i]);
                    }
                }
            }*/
/*
栽跟头
    /**
     * @param c 当前第c列
     * @param f 从第f列开始
     * @param ck 当前放了几个棋子
     *//*
private static void dfs(int c ,int f ,int ck) {
 *//*初始层，因为k<=n,所以开始的列可以是0,1...n-k*//*
        if (f==-1){
            int column = n-k+1;
            for (int i = 0; i < column; i++) {
                dfs(0,i,0);
            }
        }else {
            if (ck==k){
                schemes++;
                return;
            }
            *//*当前列*//*
            int j = c+f;
            if (j<n&&(n-j)>(k-ck)){
                *//*遍历行找到当前列中的'#'格*//*
                for (int i = 0; i < n; i++) {
                    if (board[j][i]=='#'&&check[i]==0){
                        check[i] = 1;
                        dfs(c+1,f,ck+1);
                        check[i] = 0;
                    }
                }
                if (j!=f){
                    check[j] = 1;
                    dfs(c+1,f,ck);
                    check[j] = 0;
                }
            }
        }
    }
}*/

