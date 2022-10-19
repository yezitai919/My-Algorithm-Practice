package poj1011;

import java.util.Scanner;

/**
 * @Author jinjun99
 * @Date Created in 2022/10/5 20:17
 * @Description
 * @Since version-1.0
 */
public class Main {
    /**
     * 记录当前case的棒数
     */
    static int n;
    /**
     * 每根棒长度
     */
    static int[] sticks;

    /**
     * 被选中的短棍
     */
    static boolean[] check;

    /**
     * 当前限制的组合棒长度
     */
    static int stickLen;

    /**
     * 当前长度限制下组合棍数量
     */
    static int stickNum;

    /**
     * 最短组合棍长度
     */
    static int shortestLen;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            n = sc.nextInt();
            if (n == 0) {
                break;
            }
            sticks = new int[n + 5];
            /*统计所有短棍总长度*/
            int perimeter = 0;
            for (int i = 0; i < n; i++) {
                sticks[i] = sc.nextInt();
                perimeter += sticks[i];
            }
            bubbleSort();
            /*合成棍最短是最长的那根短棍，最长是len/2，否则不用算*/
            for (int i = sticks[0]; i <= perimeter / 2; i++) {
                /*如果当前长度能被len整除就是有可能的*/
                if (perimeter % i == 0) {
                    /*初始化标记数组*/
                    check = new boolean[n + 5];
                    shortestLen = 0;
                    stickLen = i;
                    /*组合棍数*/
                    stickNum = perimeter / i;
                    dfs(0, 0, 0);
                    if (shortestLen > 0) {
                        break;
                    }
                }
            }
            if (shortestLen == 0) {
                shortestLen = 1;
            }
            System.out.println(shortestLen);
        }
    }

    /**
     * @param index     每次从前一根棒的下标i+1开始搜索
     * @param currLen   当前棒已完成长度
     * @param finishNum 当前完成组合棒的数量
     */
    private static void dfs(int index, int currLen, int finishNum) {
        if (shortestLen == 0) {
            if (currLen == 0) {
                int a = 0;
                while (check[a]) {
                    a++;
                }
                check[a] = true;
                dfs(a + 1, sticks[a], finishNum);
            }
            /*记录匹配不成功的小棒长度*/
            int same = 0;
            for (int i = index; i < n; i++) {
                /*如果某根长度的小棒不选，跳过所有等长的小棒*/
                if (!check[i] && same != sticks[i]) {
                    if (currLen + sticks[i] <= stickLen) {
                        check[i] = true;
                        currLen += sticks[i];
                        if (currLen == stickLen) {
                            finishNum++;
                            if (finishNum == stickNum - 1) {
                                shortestLen = stickLen;
                                return;
                            }
                            dfs(0, 0, finishNum);
                            finishNum--;
                        }
                        dfs(i + 1, currLen, finishNum);
                        check[i] = false;
                        currLen -= sticks[i];


                    }
                    /*剪枝点，如果第一根没选上，说明当前组合长度不会成功*/
                    if (currLen == 0) {
                        return;
                    }
                    /*剪枝点，如果已经拼成了一个组合棒，应该进入递归变成0的，
                    但是下一个递归却失败了，说明当前棒能成，下一根不行*/
                    if (currLen + sticks[i] == stickLen) {
                        return;
                    }
                    /*记录匹配不成功的小棒长度*/
                    same = sticks[i];
                    /* 如果剩下的和比我需要的棒子还小的话，就不用凑了*/
                    int remain = 0;
                    int ind = i;
                    while (ind < n) {
                        if (!check[ind]) {
                            remain += sticks[ind];
                        }
                        ind++;
                    }
                    if (remain < stickLen - currLen) {
                        return;
                    }
                }
            }
        }
    }

    public static void bubbleSort() {
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n - i; j++) {
                if (sticks[j] < sticks[j + 1]) {
                    int temp = sticks[j];
                    sticks[j] = sticks[j + 1];
                    sticks[j + 1] = temp;
                }
            }
        }
    }
}
/*
9
5 2 1 5 2 1 5 2 1
4
1 2 3 4
0

9
5 2 1 5 2 1 5 2 1
0

*/
