package poj1724;

import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * @Author jinjun99
 * @Date Created in 2022/10/18 17:53
 * @Description
 * @Since version-1.0
 */
public class Main {
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

    static class Node implements Comparable<Node> {
        int city;
        int spend;
        int length;
        boolean[] visit;

        public Node(int city, int spend, int length, boolean[] visit) {
            this.city = city;
            this.spend = spend;
            this.length = length;
            this.visit = visit;
        }

        @Override
        public int compareTo(Node o) {
            /*路径长度短的节点优先，如果一样就花钱少的优先*/
            if (this.length == o.length) {
                return this.spend - o.spend;
            } else {
                return this.length - o.length;
            }
        }
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        k = sc.nextInt();
        n = sc.nextInt();
        r = sc.nextInt();
        roads = new int[n + 5][n + 5][5];
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

        bfs();
        System.out.println(shortestLen);
    }

    private static void bfs() {
        PriorityQueue<Node> list = new PriorityQueue<Node>();
        Node top = new Node(1, 0, 0, new boolean[n + 5]);
        top.visit[1] = true;
        list.offer(top);
        while (!list.isEmpty()) {
            Node parent = list.poll();
            if (parent.city == n) {
                shortestLen = parent.length;
                return;
            }

            for (int i = 1; i <= n; i++) {
                /*下一站未走过并且该边存在*/
                if (!parent.visit[i] && roads[parent.city][i][0] != 0) {
                    /*重复边中最短的*/
                    int l1 = parent.length + roads[parent.city][i][1];
                    int t1 = parent.spend + roads[parent.city][i][2];
                    /*重复边中最便宜的*/
                    int l2 = parent.length + roads[parent.city][i][3];
                    int t2 = parent.spend + roads[parent.city][i][4];
                    boolean[] visit = parent.visit.clone();
                    visit[i] = true;
                    if (t1 <= k) {
                        Node child1 = new Node(i, t1, l1, visit);
                        list.offer(child1);
                    }

                    if (!(t1 == t2 && l1 == l2)) {
                        if (t2 <= k) {
                            Node child2 = new Node(i, t2, l2, visit);
                            list.offer(child2);
                        }

                    }
                }
            }
        }
    }
}
/*Notice that different roads may have the same source and destination cities.
* 请注意，不同的道路可能具有相同的源城市和目的城市。没看到这条被坑惨了*/
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