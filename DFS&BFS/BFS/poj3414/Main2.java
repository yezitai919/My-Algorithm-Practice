package poj3414;


import java.io.BufferedInputStream;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Vector;


/**
 * @Author jinjun99
 * @Date Created in 2022/10/3 15:29
 * @Description
 * @Since version-1.0
 */
public class Main2 {


    static int x, y, z;
    static int[] say;
    static int[][] vis;


    static String[] words = {
            "",
            "DROP(1)",
            "FILL(1)",
            "POUR(1,2)",
            "DROP(2)",
            "FILL(2)",
            "POUR(2,1)"
    };


    static class Node {
        int a1;
        int a2;
        int step;
        int[] way = new int[1003];
    }

    static int bfs() {
        Queue<Node> q = new LinkedList<Node>();
        Node node = new Node();
        node.a1 = 0;
        node.a2 = 0;
        node.step = 0;
        vis[0][0] = 1;
        q.add(node);
        while (!q.isEmpty()) {

            Node top = q.poll();
            if (top.a1 == z || top.a2 == z) {
                System.out.println(top.step);
                for (int i = 1; i <= top.step; i++) {
                    System.out.println(words[top.way[i]]);
                }
                return top.step;

            }
            //System.out.print(top.a1); System.out.print(" "); System.out.println(top.a2);System.out.println();
            for (int i = 0; i < 2; i++) {
                if (i == 0) {
                    for (int j = 0; j < 3; j++) {
                        if (j == 0) {
                            if (top.a1 != 0) {
                                Node temp = new Node();
                                temp.a1 = 0;
                                temp.a2 = top.a2;
                                temp.step = top.step + 1;
                                System.arraycopy(top.way, 1, temp.way, 1, 1000);
                                temp.way[temp.step] = 1;
                                if (vis[temp.a1][temp.a2] == 0) {
                                    vis[temp.a1][temp.a2] = 1;
                                    q.add(temp);
                                }
                            }
                        }
                        if (j == 1) {
                            if (top.a1 != x) {
                                Node temp = new Node();
                                temp.a1 = x;
                                temp.a2 = top.a2;
                                temp.step = top.step + 1;
                                System.arraycopy(top.way, 1, temp.way, 1, 1000);

                                temp.way[temp.step] = 2;
                                if (vis[temp.a1][temp.a2] == 0) {
                                    vis[temp.a1][temp.a2] = 1;
                                    q.add(temp);
                                }
                            }
                        }
                        if (j == 2) {
                            if (top.a1 != 0 && top.a2 != y) {
                                Node temp = new Node();
                                temp.a1 = Math.max(0, top.a1 + top.a2 - y);
                                temp.a2 = Math.min(y, top.a2 + top.a1);
                                temp.step = top.step + 1;
                                System.arraycopy(top.way, 1, temp.way, 1, 1000);

                                temp.way[temp.step] = 3;
                                if (vis[temp.a1][temp.a2] == 0) {
                                    vis[temp.a1][temp.a2] = 1;
                                    q.add(temp);
                                }
                            }
                        }
                    }
                } else if (i == 1) {
                    for (int j = 0; j < 3; j++) {
                        if (j == 0) {
                            if (top.a2 != 0) {
                                Node temp = new Node();
                                temp.a1 = top.a1;
                                temp.a2 = 0;
                                temp.step = top.step + 1;
                                System.arraycopy(top.way, 1, temp.way, 1, 1000);

                                temp.way[temp.step] = 4;
                                if (vis[temp.a1][temp.a2] == 0) {
                                    vis[temp.a1][temp.a2] = 1;
                                    q.add(temp);
                                }
                            }
                        }
                        if (j == 1) {
                            if (top.a2 != y) {
                                Node temp = new Node();
                                temp.a1 = top.a1;
                                temp.a2 = y;
                                temp.step = top.step + 1;
                                System.arraycopy(top.way, 1, temp.way, 1, 1000);

                                temp.way[temp.step] = 5;
                                if (vis[temp.a1][temp.a2] == 0) {
                                    vis[temp.a1][temp.a2] = 1;
                                    q.add(temp);
                                }
                            }
                        }
                        if (j == 2) {
                            if (top.a2 != 0 && top.a1 != x) {
                                Node temp = new Node();
                                temp.a1 = Math.min(x, top.a2 + top.a1);
                                temp.a2 = Math.max(0, top.a1 + top.a2 - x);
                                temp.step = top.step + 1;
                                System.arraycopy(top.way, 1, temp.way, 1, 1000);

                                temp.way[temp.step] = 6;
                                if (vis[temp.a1][temp.a2] == 0) {
                                    vis[temp.a1][temp.a2] = 1;
                                    q.add(temp);
                                }
                            }
                        }
                    }
                }
            }


        }
        return -1;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));
        x = sc.nextInt();
        y = sc.nextInt();
        z = sc.nextInt();
        vis = new int[1000][1000];

        int res = bfs();
        if (res == -1) {
            System.out.println("impossible");
        }
/*		for(int i=1;i<=res;i++) {
			//System.out.println(words[say[i]]);
			System.out.println(say[i]);
		}*/
    }
}


