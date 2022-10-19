package poj2251;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * @Author jinjun99
 * @Date Created in 2022/10/3 14:45
 * @Description 多选择求最少选择次数：BFS，一次AC的典范
 * @Since version-1.0
 */
public class Main {
    static int l;
    static int r;
    static int c;
    static char[][][] map;
    static int[][][] visit;
    static int[][] dir = {{0, 0, -1}, {0, 0, 1}, {1, 0, 0}, {-1, 0, 0}, {0, 1, 0}, {0, -1, 0}};
    static int[] start;
    static int[] end;
    static LinkedList<Node> list;
    static Node optimal;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            l = sc.nextInt();
            r = sc.nextInt();
            c = sc.nextInt();
            if (l == 0 && r == 0 && c == 0) {
                break;
            }
            start = new int[6];
            end = new int[6];
            map = new char[l + 5][r + 5][c + 5];
            visit = new int[l + 5][r + 5][c + 5];
            sc.nextLine();
            for (int i = 0; i < l; i++) {
                for (int j = 0; j < r; j++) {
                    String str = sc.next();
                    for (int k = 0; k < c; k++) {
                        map[i][j][k] = str.charAt(k);
                        if (map[i][j][k] == 'S') {
                            start[0] = i;
                            start[1] = j;
                            start[2] = k;
                            visit[i][j][k] = 1;
                        }
                        if (map[i][j][k] == 'E') {
                            end[0] = i;
                            end[1] = j;
                            end[2] = k;
                        }
                    }
                }
            }
            list = new LinkedList<Node>();
            optimal = new Node();
            bfs();
            if (optimal.time==0){
                System.out.println("Trapped!");
            }else {
                System.out.println("Escaped in "+optimal.time+" minute(s).");
            }
        }

    }

    private static void bfs() {
        Node root = new Node(start[0], start[1], start[2], 0);
        list.offer(root);
        while (!list.isEmpty()) {
            Node parent = list.poll();
            /*当前的l，r，c，time*/
            int cl,cr,cc;
            int ct = parent.time + 1;
            for (int i = 0; i < 6; i++) {

                /*往i方向移动一格*/
                cl = parent.l + dir[i][0];
                cr = parent.r + dir[i][1];
                cc = parent.c + dir[i][2];
                /*判断有没有出界*/
                boolean b1 = cl>=0&&cl<l&&cr>=0&&cr<r&&cc>=0&&cc<c;
                if (b1){
                    /*判断该位置有没有走过*/
                    boolean b2 = visit[cl][cr][cc]==0;
                    /*判断该位置是不是石头*/
                    boolean b3 = map[cl][cr][cc]!='#';

                    if (b2&&b3){
                        visit[cl][cr][cc]=1;
                        /*判断当前耗时是否比最短耗时小，否则剪枝*/
                        if (optimal.time == 0 || ct < optimal.time) {
                            Node child = new Node(cl,cr,cc,ct);
                            /*到达终点就更新最优值节点*/
                            if (cl == end[0] && cr == end[1] && cc == end[2]) {
                                optimal = child;
                            }else {
                                /*否则入队*/
                                list.offer(child);
                            }
                        }
                    }
                }
            }
        }
    }

    static class Node {
        int l;
        int r;
        int c;
        int time = 0;

        public Node(int l, int r, int c, int time) {
            this.l = l;
            this.r = r;
            this.c = c;
            this.time = time;
        }

        public Node() {
        }
    }
}

/*
3 4 5
S....
.###.
.##..
###.#

#####
#####
##.##
##...

#####
#####
#.###
####E

1 3 3
S##
#E#
###

0 0 0

*/