package stamps;

import java.util.Scanner;

/**
 * @Author jinjun99
 * @Date Created in 2021/12/5 20:21
 * @Description
 * @Since version-1.0
 */
public class Demo01 {
    private static int[] stamps = new int[4];
    //private static int staNum = 3;
    private static int[] cusNeed = new int[10];
   // private static int cusNum = 2;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        for (int k = 0; k < 2; k++) {
            int staNum = 0;
            for (int i = 0; i < 4; i++) {
                int a=sc.nextInt();
                if (a==0){
                    break;
                }
                stamps[i] = a;
                staNum++;
            }

            int cusNum = 0;
            for (int i = 0; i < 10; i++) {
                int a = sc.nextInt();
                if (a==0){
                    break;
                }
                cusNeed[i]=a;
                cusNum++;
            }
            System.out.println(" ");
            for (int i = 0; i < cusNum; i++) {
                initialization();
                dfs(0,cusNeed[i],staNum);

                if (bestType==0){
                    System.out.println(cusNeed[i]+"----none");
                    continue;
                }
                System.out.print(cusNeed[i]+" ("+bestType+"): ");
                if (tie==1){
                    System.out.println("tie");
                    continue;
                }
                for (int j = 0; j < 4; j++) {
                    if (bestAlloca[j]==0){
                        break;
                    }
                    System.out.print(bestAlloca[j]+" ");
                }
                System.out.println(" ");
            }
        }




    }
    /**
     * 当前顾客获得的面额
     */
    private static int cusGet;

    /**
     * 判断选择的邮票是否重复
     */
    private static int[] repeat;
    /**
     * 当前选择的邮票种类数
     */
    private static int staType;
    /**
     * 当前邮票分配情况
     */
    private static int[] allocation;

    /**
     * 最优分配种类数
     */
    private static int bestType;
    /**
     * 最优分配情况
     */
    private static int[] bestAlloca;
    /**
     * 是否平局
     */
    private static int tie;
    private static void initialization(){
        cusGet = 0;
        repeat = new int[26];
        staType = 0;
        allocation = new int[4];
        bestType = 0;
        bestAlloca = new int[4];
        tie = 0;
    }
    /**
     * @param stamp1 当前选择第几张邮票，
     * @param need1 当前顾客需要的面额
     * @param staNum 当前邮票类型数
     */
    private static void dfs(int stamp1,int need1,int staNum){
        if (stamp1==4||cusGet==need1){
           if (cusGet==need1){
               if (staType>bestType){
                   bestType=staType;
                   bestAlloca = arrCopy(allocation,4);
                   tie = 0;
               }
               if (staType==bestType){
                   int c1 = compare(allocation,bestAlloca,4);
                   if (c1==1){
                       bestAlloca = arrCopy(allocation,4);
                       tie = 0;
                   }
                   if (c1==2){
                       tie=1;
                   }

               }
           }
           return;
        }
        /*遍历所有邮票类型*/
        for (int i = 0; i < staNum; i++) {
            /*选择当前邮票不超面额*/
            if (cusGet+stamps[i]<=need1){
                /*该类型邮票是否被选过，记录种类数*/
                int sta1 = 0;//回溯标记
                if (repeat[i]==0){
                    repeat[i]=1;
                    staType++;
                    sta1++;
                }
                /*记录分配*/
                cusGet+=stamps[i];
                allocation[stamp1] = stamps[i];
                dfs(stamp1+1,need1,staNum);
                allocation[stamp1] = 0;
                cusGet-=stamps[i];
                if (sta1==1){
                    repeat[i]=0;
                    staType--;
                }
            }
        }
    }

    private static int[] arrCopy(int[] a,int n){
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = a[i];
        }
        return arr;
    }
    private static int compare(int[]a,int[] b,int n){
        int identical = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (b[j]==a[i]){
                    identical++;
                    break;
                }
            }
        }
        if (identical==n){
            return 2;
        }
        int a1 = 0;
        int b1 = 0;
        for (int i = 0; i < n; i++) {
            if (a[i]>a1){
                a1 = a[i];
            }
            if (b[i]>b1){
                b1 = b[i];
            }
        }
        if (a1>b1){
            return 1;
        }else if (a1==b1){
            return 2;
        }else {
            return 3;
        }
    }
/*
1 2 3 0
7 4 0
1 1 0
6 2 3 0
*/
}
