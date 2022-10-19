package poj3126;

import java.util.LinkedList;
import java.util.Scanner;


/**
 * @Author jinjun99
 * @Date Created in 2022/10/2 14:46
 * @Description 每次改变一位数，有多种改法，求最少次数，选BFS，先把第i次的所有可能性遍历，再看i+1步
 * @Since version-1.0
 */
public class Main {
    static int n;
    static int a;
    static int b;
    static int maxInt = 10000;
    /**
     * 标记素数 false 表示是素数
     */
    static boolean[] nonPrimeNum = new boolean[maxInt+5];
    /**
     * 标记是否走过
     */
    static boolean[] visit;
    /**
     * 标记所有素数的步数
     */
    static int[] allSteps;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        earSieve();
        for (int i = 0; i < n; i++) {
            a = sc.nextInt();
            b = sc.nextInt();

            initData();
            bfs();
            if (allSteps[b]!=100000){
                System.out.println(allSteps[b]);
            }else {
                System.out.println("impossible");
            }

        }
    }

    private static void initData() {
        visit = new boolean[maxInt+5];
        allSteps = new int[maxInt+5];
        for (int i = 0; i < maxInt+5; i++) {
            allSteps[i] = 100000;
        }
        allSteps[a] = 0;
        visit[a] = true;
    }
    /**
     * 埃氏法求素数
     */
    private static void earSieve() {

        for (int i = 2; i < maxInt+5; i++) {
            if (!nonPrimeNum[i]){
                for (int j = i*2; j < maxInt+5; j+=i) {
                    nonPrimeNum[j] = true;
                }
            }
        }
    }

    private static void bfs() {
        if (a==b) {
            return;
        }
        LinkedList<Integer> list = new LinkedList<Integer>();
        list.offer(a);

        while (!list.isEmpty()){
            int parent = list.poll();
            int[] num = {parent/1000%10, parent/100%10,
                    parent/10%10, parent%10};
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 10; j++) {
                    if (j==num[i]||(i==3&&j==0)){
                        continue;
                    }
                    int newNum = getNewNum(num,i,j);

                    if (!nonPrimeNum[newNum]&&!visit[newNum]&&allSteps[newNum]>allSteps[parent]+1){
//                        System.out.println(newNum);
                        allSteps[newNum] = allSteps[parent]+1;
                        list.offer(newNum);
                        visit[newNum] = true;

                    }
                }
            }
        }
    }

    private static int getNewNum(int[] num,int digit,int bit) {
        if (digit==0){
            return num[0]*1000+num[1]*100+num[2]*10+bit;
        }
        if (digit==1){
            return num[0]*1000+num[1]*100+bit*10+num[3];
        }
        if (digit==2){
            return num[0]*1000+bit*100+num[2]*10+num[3];
        }
        if (digit==3){
            return bit*1000+num[1]*100+num[2]*10+num[3];
        }
        return 0;
    }

}


/*
4
1033 8179
1373 8017
1033 1033
1029 1014

             if (newNum==1733){
                            System.out.println("1733 step="+allSteps[newNum]);
                        }
                        if (newNum==3733){
                            System.out.println("3733 step="+allSteps[newNum]);
                        }
*/