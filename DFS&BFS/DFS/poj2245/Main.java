package poj2245;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @Author jinjun99
 * @Date Created in 2022/9/27 12:52
 * @Description
 * @Since version-1.0
 */
public class Main {
    /**
     * 集合长度
     */
    static int k;
    /**
     * 集合
     */
    static int[] set;

    /**
     * 固定取6个数
     */
    static int m = 6;
    /**
     * 所有子集
     */
    static ArrayList<int[]> sub;

    /**
     * 当前子集
     */
    static int[] currSub;
    /**
     * 被选过的数
     */
    static boolean[] selected;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        long st = System.currentTimeMillis();
        while (sc.hasNext()){
            /*数据输入和初始化*/
            String str = sc.nextLine();
            String[] s1 = str.split(" ");
            k = Integer.parseInt(s1[0]);
            if (k==0){
                break;
            }
            set = new int[k];
            currSub = new int[m];
            selected = new boolean[k];
            sub = new ArrayList<int[]>();
            for (int i = 0; i < k; i++) {
                set[i] = Integer.parseInt(s1[i+1]);
            }
            /*搜索*/
            dfs(0);
            /*打印*/
            for (int i = 0; i < sub.size(); i++) {
                int[] subSetI = sub.get(i);
                for (int j = 0; j < m; j++) {
                    if (j==m-1){
                        System.out.println(subSetI[j]);
                    }else {
                        System.out.print(subSetI[j]+" ");
                    }
                }
            }
            System.out.println("");
        }

        long e = System.currentTimeMillis();
        System.out.println("计算时长："+(e-st)+"ms");
    }

    /**
     * @param l 解空间树的层数，当前子集的第几个数
     */
    private static void dfs(int l) {
        if (l==m){
            /*到达叶子节点，放入子集集合中*/
            int[] sub1 = new int[m];
            System.arraycopy(currSub, 0, sub1, 0, m);
            sub.add(sub1);
            return;
        }
        for (int i = 0; i < k; i++) {
            /*当前set[i]没被选过并且大于当前子集的前一个数(字典序)*/
            boolean bl = !selected[i]&&(l==0||(l>0&&set[i]>currSub[l-1]));
            if (bl){
                /*填入当前子集，并锁定set中的当前数字*/
                currSub[l]=set[i];
                selected[i]=true;
                dfs(l+1);
                /*回溯*/
                selected[i]=false;
                currSub[l]=0;
            }
        }
    }
}
/*
7 1 2 3 4 5 6 7
8 1 2 3 5 8 13 21 34
0

*/