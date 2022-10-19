package poj1724;

import java.util.Scanner;

/**
 * @Author jinjun99
 * @Date Created in 2022/10/18 23:40
 * @Description
 * @Since version-1.0
 */
public class Main2 {
    /**
     * 钱数
     */
    static int k;
    /**
     * 城市数
     */
    static int n;
    /**
     * 道路数
     */
    static int r;
    /**
     * 道路邻接矩阵
     */
    static int[][][] roads;
    /**
     * 最短路径
     */
    static int shortestLen = -1;

    /**
     * 访问过的点
     */
    static boolean[] visit;
    /**
     * 存储i点在花费k元时的路程
     */
    static int[][] save;

    private static void dfs(int city, int spend, int length) {

        if (city == n) {
            if (shortestLen == -1 || shortestLen > length) {
                shortestLen = length;
            }
            return;
        }

        for (int i = 1; i <= n; i++) {
            if (!visit[i] && roads[city][i][0] != 0) {
                /*重复边中最短的*/
                int t1 = spend + roads[city][i][2];
                if (t1 <= k) {
                    int l1 = length + roads[city][i][1];
                    /*如果当前l1大于最短路径剪枝*/
                    if (l1 < shortestLen || shortestLen == -1) {
                        /*如果之前记录的i点在t1元时的路程比现在的l1小就剪枝*/
                        if (save[i][t1] == 0 || save[i][t1] > l1) {
                            save[i][t1] = l1;
                            visit[i] = true;
                            dfs(i, t1, l1);
                            visit[i] = false;
                        }
                    }

                }
                /*重复边中最便宜的*/
                int t2 = spend + roads[city][i][4];
                /*如果不重复，t2<t1一定成立*/
                if (t2 < t1 && t2 <= k) {
                    int l2 = length + roads[city][i][3];
                    /*如果当前l2大于最短路径剪枝*/
                    if (l2 < shortestLen || shortestLen == -1) {

                        /*如果之前记录的i点在t2元时的路程比现在的l2小就剪枝*/
                        if (save[i][t2] == 0 || save[i][t2] > l2) {
                            save[i][t2] = l2;
                            visit[i] = true;
                            dfs(i, t2, l2);
                            visit[i] = false;
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        k = sc.nextInt();
        n = sc.nextInt();
        r = sc.nextInt();
        roads = new int[n + 5][n + 5][5];
        visit = new boolean[n + 5];
        save = new int[n + 5][k + 5];
        for (int i = 0; i < r; i++) {
            int s = sc.nextInt();
            int e = sc.nextInt();
            int l = sc.nextInt();
            int t = sc.nextInt();
            if (roads[s][e][0] == 1) {

                /*筛选重负边中最短的*/
                if (l == roads[s][e][1]) {
                    if (t < roads[s][e][2]) {
                        roads[s][e][1] = l;
                        roads[s][e][2] = t;
                    }
                } else if (l < roads[s][e][1]) {
                    roads[s][e][1] = l;
                    roads[s][e][2] = t;
                }


                /*筛选重负边中最便宜的*/
                if (t == roads[s][e][4]) {
                    if (l < roads[s][e][3]) {
                        roads[s][e][3] = l;
                        roads[s][e][4] = t;
                    }
                } else if (t < roads[s][e][4]) {
                    roads[s][e][3] = l;
                    roads[s][e][4] = t;
                }

            } else {
                roads[s][e][1] = l;
                roads[s][e][2] = t;
                roads[s][e][3] = l;
                roads[s][e][4] = t;
                /*标记边存在*/
                roads[s][e][0] = 1;
            }

        }
        dfs(1, 0, 0);
        System.out.println(shortestLen);
    }
}
/*
5
6
7
1 2 2 3
2 4 3 3
3 4 2 4
1 3 4 1
4 6 2 1
3 5 2 0
5 4 3 2

11

19
6
7
1 2 2 2
1 3 2 2
1 4 2 2
2 5 5 10
4 5 7 7
3 5 10 5
5 6 5 10

14

15
12
16
1 8 20 0
1 2 10 0
2 8 5 0
1 7 100 0
2 3 5 1
3 4 5 1
4 5 5 1
5 6 5 1
6 12 5 1
8 12 10 20
8 9 1 1
9 10 1 1
10 11 1 1
11 7 2 10
7 12 10 1
11 4 1 0

30
*/