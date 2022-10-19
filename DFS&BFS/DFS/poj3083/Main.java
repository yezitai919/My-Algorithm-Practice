package poj3083;

import java.util.LinkedList;
import java.util.Scanner;

/**
 * @Author jinjun99
 * @Date Created in 2022/10/4 11:21
 * @Description 靠左靠右的路径，因为只有一条路，限制方向，不限制长短，可以用dfs或循环。
 * 求最短路径，多阶段决策问题求最少决策应该用BFS，因为以前先学的DFS，而且很多地图类题目，下意识用DFS，超时了。
 * @Since version-1.0
 */
public class Main {
    static int n;
    static int w;
    static int h;
    static char[][] map;
    static int[] start;
    static int[] end;
    static int rightPath;
    static int liftPath;

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
            minPath = 1;
            bfs();
            System.out.println(liftPath + " " + rightPath + " " + minPath);

        }

    }

    private static void bfs() {
        Node root = new Node(start[0],start[1],1 );
        LinkedList<Node> list = new LinkedList<Node>();
        list.offer(root);
        while (!list.isEmpty()){
            Node parent = list.poll();
            int childX,childY;
            int childStep = parent.step+1;
            if (minPath==1||childStep<minPath){
                for (int i = 0; i < 4; i++) {
                    childX = parent.x+leftDir[i][0];
                    childY = parent.y+leftDir[i][1];
                    boolean bl1 = childX>=0&&childX<w&&childY>=0&&childY<h;
                    if (bl1){
                        if (map[childX][childY]=='E'){
                            minPath = childStep;
                            break;
                        }
                        if (map[childX][childY]=='.'){
                            map[childX][childY]='#';
                            list.offer(new Node(childX,childY,childStep));
                        }
                    }
                }
            }
        }
    }
    static class Node{
        int x;
        int y;
        int step;

        public Node(int x, int y, int step) {
            this.x = x;
            this.y = y;
            this.step = step;
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
*/