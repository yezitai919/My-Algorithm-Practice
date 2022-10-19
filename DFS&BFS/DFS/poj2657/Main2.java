package poj2657;

import java.util.Scanner;

/**
 * @Author jinjun99
 * @Date Created in 2022/9/30 11:27
 * @Description
 * @Since version-1.0
 */
public class Main2 {
    /**
     * 圆环格数
     */
    static int n;
    /**
     * 目标编号
     */
    static int z;
    /**
     * 临时标记数组
     */
    static boolean[] m;

    /**
     * 阻碍块
     */
    static int[] block;

    /**
     * 找到目标
     */
    static boolean succeed;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
//        long st = System.currentTimeMillis();
        while (sc.hasNext()){

            n = sc.nextInt();
            z = sc.nextInt();
            int t = sc.nextInt();
            if (t!=0){
                block = new int[t+5];

                /*标记阻碍格*/
                for (int i = 0; i < t; i++) {
                   block[i]=sc.nextInt()-1;
                }
            }
            succeed = false;
            for (int i = 1; i < n; i++) {
                /*初始化标记数组*/
                m = new boolean[n+5];
                for (int j = 0; j < t; j++) {
                    m[block[j]]=true;
                }
                int inedx = 0;
                while (true){
                    if (inedx == z-1){
                        succeed = true;
                        break;
                    }
                    if (m[inedx]){
                        break;
                    }
                    m[inedx] = true;
                    if (inedx+i<n){
                        inedx+=i;
                    }else {
                        inedx=(inedx+i)%(n-1);
                    }
                }
                if (succeed){
                    System.out.println(i);
                    break;
                }
            }
         /*   long e = System.currentTimeMillis();
            System.out.println("计算时长："+(e-st)+"ms");*/
        }
    }
}
/*
9 7 2
2 3

13 9 2
7 8



*/