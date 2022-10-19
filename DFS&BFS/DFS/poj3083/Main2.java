package poj3083;

import java.util.Scanner;

/**
 * @Author jinjun99
 * @Date Created in 2022/10/3 20:05
 * @Description
 * @Since version-1.0
 */
public class Main2 {
    static int n;
    static int w;
    static int h;
    static char[][] map;
    static int[] start;
    static int[] end;
    static int rightPath;
    static int liftPath;
    static int currPath;
    static int minPath;
    static int frontDir;
    /**
     * 左前右后顺序的方向数组
     */
    static int[][] leftDir = {{-1, 0}, {0, -1}, {1, 0}, {0, 1}};
    /**
     * 右前左后顺序的方向数组
     */
    static int[][] rightDir = {{1, 0}, {0, -1}, {-1, 0}, {0, 1}};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        for (int i = 0; i < n; i++) {
            w = sc.nextInt();
            h = sc.nextInt();
            map = new char[w + 5][h + 5];
            start = new int[5];
            end = new int[5];
            for (int j = 0; j < h; j++) {
                String str = sc.next();
                for (int k = 0; k < w; k++) {
                    /*读取地图*/
                    map[k][j] = str.charAt(k);
                    /*读取起点和终点坐标*/
                    if (map[k][j] == 'S') {
                        start[0] = k;
                        start[1] = j;
                    }
                    if (map[k][j] == 'E') {
                        end[0] = k;
                        end[1] = j;
                    }
                }
            }
            rightPath = 1;
            liftPath = 1;
            frontDir = getFrontDir();

            getLeftOrRightPath(0);
            getLeftOrRightPath(1);
            currPath = 1;
            minPath = 1;
            dfs(start[0], start[1]);
            System.out.println(liftPath + " " + rightPath + " " + minPath);

        }

    }

    /**
     * 获取一直靠左/右墙行走的路径长度
     *
     * @param searchDir 0是靠左，1是靠右
     */
    private static void getLeftOrRightPath(int searchDir) {
        /*当前坐标*/
        int x = start[0];
        int y = start[1];
        /*获取与当前正前方相对应的左边方向编号，按照左前右后的方向搜索*/
        int nextDir = turnLeft(frontDir);
        /*开始靠左或右墙走迷宫*/
        while (true) {
            for (int i = 0; i < 4; i++) {
                int nextX, nextY;
                /*下一步坐标*/
                if (searchDir == 0) {
                    nextX = x + leftDir[nextDir][0];
                    nextY = y + leftDir[nextDir][1];
                } else {
                    nextX = x + rightDir[nextDir][0];
                    nextY = y + rightDir[nextDir][1];
                }

                // 防止越界
                boolean bl1 = nextX >= 0 && nextY >= 0 && nextX < w && nextY < h;
                if (bl1) {
                    /* 到达终点*/
                    if (map[nextX][nextY] == 'E') {
                        if (searchDir == 0) {
                            liftPath++;
                        } else {
                            rightPath++;
                        }
                        return;
                    }
                    /*下一步可行*/
                    if (map[nextX][nextY] == '.') {
                        x = nextX;
                        y = nextY;
                        /*如果当前方向可行，则下一步从当前方向相对应的左/右方开始搜索*/
                        nextDir = turnLeft(nextDir);
                        if (searchDir == 0) {
                            liftPath++;

                        } else {
                            rightPath++;
                        }
                        break;
                    }
                }
                /*右转一个方向，按左前右后遍历4个方向*/
                nextDir = turnRight(nextDir);
            }
        }
    }

    /**
     * 左转
     *
     * @param dir 当前方向
     * @return
     */
    private static int turnLeft(int dir) {
        dir -= 1;
        if (dir >= 0) {
            return dir;
        } else {
            return 3;
        }
    }

    /**
     * 右转
     *
     * @param dir 当前方向
     * @return
     */
    private static int turnRight(int dir) {
        dir += 1;
        if (dir <= 3) {
            return dir;
        } else {
            return 0;
        }
    }

    /**
     * @return 找到起点的正前方的方向号码
     */
    private static int getFrontDir() {
        int fDir = 0;
        int a, b;
        for (int j = 0; j < 4; j++) {
            a = start[0] + leftDir[j][0];
            b = start[1] + leftDir[j][1];
            boolean bl = a >= 0 && a < w && b >= 0 && b < h;
            if (bl && map[a][b] == '.') {
                fDir = j;
                break;
            }
        }
        return fDir;
    }

    /**
     * @param x
     * @param y
     */
    private static void dfs(int x, int y) {
        /*剪枝：比当前最优解还长的就剪掉*/
        if (minPath == 1 || currPath + 1 < minPath) {
            for (int i = 0; i < 4; i++) {
                int nextX = x + leftDir[i][0];
                int nextY = y + leftDir[i][1];
                boolean bl1 = nextX >= 0 && nextY >= 0 && nextX < w && nextY < h;
                if (bl1) {
                    if (map[nextX][nextY] == 'E') {
                        currPath++;
                        minPath = currPath;
                        return;
                    }
                    if (map[nextX][nextY] == '.') {
                        map[nextX][nextY] = '#';
                        currPath++;
                        dfs(nextX, nextY);
                        currPath--;
                        map[nextX][nextY] = '.';
                    }
                }
            }
        }
    }
}

/*
2
8 8
########
#......#
#.####.#
#.####.#
#.####.#
#.####.#
#...#..#
#S#E####
9 5
#########
#.#.#.#.#
S.......E
#.#.#.#.#
#########

读取地图又踩一坑浪费30分，地图是一行一行读，所以外层循环是列h/c，
数组也要map[j][i]来读，使用时map[i][j]表示(r,c)没问题。

            int d = 0;
            int a;
            int b;
            for (int i = 0; i < 4; i++) {
                a = x + dirXY[i][0];
                b = y + dirXY[i][1];
                boolean bl = a >= 0 && a < w && b >= 0 && b < h;
                if (bl && map[a][b] == '.') {
                    d = i;
                }
            }
*/
/*              int frontX = x + dirXY[i][0];
                int frontY = y + dirXY[i][1];
                *//*左边坐标*//*
                int lDir = i-1;
                if (lDir<0){
                    *//*根据设计的方向数组可知
                    左方向一般是前方向-1，
                    前方0时左边3*//*
                    lDir=3;
                }
                int leftX = x+dirXY[lDir][0];
                int leftY = y+dirXY[lDir][1];
                *//*左前方坐标*//*
                int lFrontX = x + dirXY[i][0]+dirXY[lDir][0];
                int lFrontY = y + dirXY[i][1]+dirXY[lDir][1];
                *//*靠左边墙行走就是左边一格或左前方一格是墙'#'*//*
                boolean bl1 = frontX >= 0 && frontX < w && frontY >= 0 && frontY < h;
                boolean bl2 = leftX >= 0 && leftX < w && leftY >= 0 && leftY < h;
                boolean bl3 = lFrontX >= 0 && lFrontX < w && lFrontY >= 0 && lFrontY < h;
                if (bl1&&bl2&&bl3){
                    boolean bl4 = map[frontX][frontY] == '.';
                    boolean bl5 = map[frontX][frontY] == 'E';
                    boolean bl6 = map[leftX][leftY] == '#'||map[lFrontX][lFrontY]=='#';
                    boolean bl7 = mark[leftX][leftY] == 0;
                    boolean bl8 = mark[lFrontX][lFrontY]==0;
                    boolean bl9 = (bl4||bl5)&&bl6&&(bl7||bl8);
                    *//*符合条件向i方向前进一步*//*
                    if (bl9){
                        liftPath++;
                        if (bl5){
                            return;
                        }else {
                            x = frontX;
                            y = frontY;
                            if (bl7){
                                mark[leftX][leftY] =1;
                            }
                     *//*       if (bl8){
                                mark[lFrontX][lFrontY] =1;
                            }*//*
                            System.out.println("("+x+","+y+")");
                            break;
                        }
                    }
                }*/