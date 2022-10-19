package poj3414;

import java.io.BufferedInputStream;

import java.util.LinkedList;
import java.util.Scanner;

/**
 * @Author jinjun99
 * @Date Created in 2022/10/2 22:06
 * @Description 多选择求最少选择次数，BFS 已AC
 * @Since version-1.0
 */
public class Main {
    static int maxInt = 110;
    /**
     * 标记一种状态是否入队过
     */
    static int[][] visit;
    /**
     * 水杯容量
     */
    static int a, b, c;
    /**
     * 最小步数
     */
    static Node optimalN;
    static LinkedList<Node> list;
    /**
     * 标记路径
     */
    static int[] path;
    /**
     * 打印可选单词
     */
    static String[] word = {"", "FILL(1)", "FILL(2)", "DROP(1)",
            "DROP(2)", "POUR(1,2)", "POUR(2,1)", "impossible", "", "", ""};

    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedInputStream(System.in));

            a = sc.nextInt();
            b = sc.nextInt();
            c = sc.nextInt();
            if (a == c || b == c) {
                System.out.println(1);
                if (a==c){
                    System.out.println(word[1]);
                }else {
                    System.out.println(word[2]);
                }
            } else {
                visit = new int[maxInt][maxInt];
                optimalN = new Node(null, 0, 0, 0, 0);
                list = new LinkedList<Node>();
                bfs();
                if (optimalN.step == 0) {
                    System.out.println(word[7]);
                } else {
                    System.out.println(optimalN.step);
                    int index = optimalN.step;
                    int len = index;
                    path = new int[len + 5];

                    while (optimalN.parent != null) {
                        path[index] = optimalN.op;
                        optimalN = optimalN.parent;
                        index--;
                    }
                    for (int i = 1; i <= len; i++) {
                        System.out.println(word[path[i]]);
                    }
                }
            }

    }

    private static void bfs() {
        Node root = new Node(null, 0, 0, 0, 0);
        visit[0][0] = 1;
//        LinkedList<Node> list = new LinkedList<Node>();
        list.offer(root);
        while (!list.isEmpty()) {
            Node parent = list.poll();
            int a1 = parent.a1;
            int b1 = parent.b1;
            int step1 = parent.step;
            int childA;
            int childB;
            for (int i = 1; i <= 6; i++) {
                childA = a1;
                childB = b1;

                /*操作一：a杯倒满*/
                if (i == 1) {
                    childA = a;
                }
                /*操作二：b杯倒满*/
                if (i == 2) {
                    childB = b;
                }
                /*操作三：a杯倒空*/
                if (i == 3) {
                    childA = 0;
                }
                /*操作四：b杯倒空*/
                if (i == 4) {
                    childB = 0;
                }
                /*操作五：a倒入b*/
                if (i == 5) {
                    /*全倒入*/
                    if (childA + childB <= b) {
                        childB += childA;
                        childA = 0;
                    } else {/*剩一点*/
                        childA -= b - childB;
                        childB = b;
                    }
                }
                /*操作六：b倒入a*/
                if (i == 6) {
                    /*全倒入*/
                    if (childA + childB <= a) {
                        childA += childB;
                        childB = 0;
                    } else {/*剩一点*/
                        childB -= a - childA;
                        childA = a;
                    }
                }
                /*如果当前两杯的状态未出现过*/
                if (visit[childA][childB] == 0) {
                    /*标记该状态*/
                    visit[childA][childB] = 1;

                    /*当前步数比最优节点小才有必要入队*/
                    if (optimalN.step == 0 || step1 + 1 < optimalN.step) {
                        /*创建子节点*/
                        Node child = new Node(parent, childA, childB, i, step1 + 1);

                        /*如果找到c水量就记录一次最优解*/
                        if (childA == c || childB == c) {
                            optimalN = child;
                        }else {
                            list.offer(child);
                        }
                    }
                }
            }
        }
    }

    static class Node {
        Node parent;
        /**
         * a杯当前水量
         */
        int a1;
        /**
         * b杯当前水量
         */
        int b1;
        /**
         * 当前操作
         */
        int op;
        /**
         * 记录步数
         */
        int step;

        public Node(Node parent, int a1, int b1, int op, int step) {
            this.parent = parent;
            this.a1 = a1;
            this.b1 = b1;
            this.op = op;
            this.step = step;
        }
    }
}
/*
3 5 4
3 5 5
4 8 9
7 6 4
AC经验：忘了初始是空杯子，355的情况直接输出0了，还有题目只给一个案例，就不要加 while (sc.hasNext())


*/