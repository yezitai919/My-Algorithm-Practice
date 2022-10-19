package poj1011;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * @Author jinjun99
 * @Date Created in 2022/10/1 14:27
 * @Description 解空间树：第一层是k种可能的组合棍长度，k个分支，
 * 每个分支后续的任务是找齐当前限制长度下的所有组合棍，每个节点是一根棍。
 * @Since version-1.0
 */
public class Main2 {
    /**
     * 记录当前case的棒数
     */
    static int n;
    /**
     * 每根棒长度
     */
    static int[] stickLen;

    /**
     * 被选中的短棍
     */
    static int[] check;

    /**
     * 当前组合棍长度
     */
    static int combLen;
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
        while (true){
            n = sc.nextInt();
            if (n==0){
                break;
            }
            stickLen = new int[n+5];
            for (int i = 0; i < n; i++) {
                stickLen[i] = sc.nextInt();
            }
            bubbleSort();
            /*统计所有短棍总长度*/
            int len = 0;
            for (int i = 0; i < n; i++) {
                len+=stickLen[i];
            }
            /*合成棍最短是最长的那根短棍，最长是len/2，否则不用算*/
            for (int i = stickLen[0]; i <= len/2; i++) {
                /*如果当前长度能被len整除就是有可能的*/
                if (len%i==0){
                    /*初始化标记数组*/
                    check = new int[n+5];
                    /*当前组合棍长度*/
                    combLen = i;
                    /*组合棍数*/
                    stickNum = len/i;
                    dfs(0);
                    if (shortestLen!=0){
                        break;
                    }
                }
            }


            if (shortestLen==0){
                shortestLen=1;
            }
            System.out.println(shortestLen);
        }
    }

    /**
     * @param finish 当前完成组合棍数
     */
    private static void dfs(int finish) {

        int currLen = 0;
        for (int i = 0; i < n; i++) {
            if (check[i]==0&&currLen+stickLen[i]<=combLen){
                currLen+=stickLen[i];
                /*标记短棍*/
                check[i]=1;
            }
            /*如果组合棍已经足够长就跳出*/
            if (currLen==combLen){
                break;
            }
        }
        if (currLen==combLen){
            finish++;
            if (finish==stickNum){
                shortestLen = combLen;
            }else {
                dfs(finish);
            }
        }

    }

    public static void bubbleSort(){
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n - i; j++) {
                if (stickLen[j]<stickLen[j+1]){
                    int temp = stickLen[j];
                    stickLen[j] = stickLen[j+1];
                    stickLen[j+1] = temp;
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



