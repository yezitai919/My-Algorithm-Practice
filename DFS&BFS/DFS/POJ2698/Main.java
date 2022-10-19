package POJ2698;

import java.util.Scanner;

/**
 * @Author jinjun99
 * @Date Created in 2022/9/30 13:21
 * @Description
 * @Since version-1.0
 */
public class Main {

    /**
     * 案例数
     */
    static int n;
    /**
     * DVD机数
     */
    static int k;
    /**
     * 当前DVD机里的DVD编号
     */
    static int[] driver;
    /**
     * 一个序列的DVD数
     */
    static int l;
    /**
     * DVD序列
     */
    static int[] sequence;
    /**
     * 最小插入次数
     */
    static int minInsertions;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        for (int i = 0; i < n; i++) {

            k = sc.nextInt();
            l = sc.nextInt();
            driver = new int[k+5];
            sequence = new int[l+5];
            for (int j = 0; j < l; j++) {
                sequence[j] = sc.nextInt();
            }
            minInsertions = k;
            /*先放k张碟进去*/
            for (int j = 0; j < k; j++) {
                /*加个保险*/
                if (j<l){
                    driver[j] = sequence[j];
                }else {
                    minInsertions = l;
                }
            }

            if (k<l){
                optimalPermutation();
            }
            System.out.println(minInsertions);
        }

    }

    private static void optimalPermutation() {
        /*遍历剩下的DVD序列*/
        for (int i = k; i < l; i++) {
            /*要取出的DVD机编号*/
            int index = 0;
            /*当前机内的DVD编号与后面序列中同编号的DVD的间隔*/
            int interval = 0;
            /*当前DVD编号不在机内*/
            boolean outside = true;
            /*遍历DVD机*/
            for (int j = 0; j < k; j++) {
                int dvd = driver[j];
                int dist = 0;
                /*当前DVD在机内*/
                if (dvd==sequence[i]){
                    outside = false;
                    break;
                }
                /*当前机内DVD已不在剩下的序列中*/
                boolean end = false;
                /*计算当前机内DVD与剩下的序列中最近同编号DVD的距离*/
                for (int m = i; m < l; m++) {
                    if (sequence[m]==dvd){
                        break;
                    }else {
                        dist++;
                        if (m==l-1){
                            end = true;
                        }
                    }
                }
                if (dist>interval||end){
                    index = j;
                    interval = dist;
                }
            }
            if (outside){
                driver[index] = sequence[i];
                minInsertions++;
            }
        }
    }
}
/*
2
2 7
1
2
3
1
3
1
3
3 9
1
2
3
4
1
2
1
2
4
1
2 20
7 0 1 2 0 3 0 4 2 3 0 3 2 1 2 0 1 7 0 1

1
3 8
1 2 3 4 1 2 3 4

  System.out.println("driver["+j+"]="+dvd+",  sequence["+i+"]="+sequence[i]);

    System.out.println("DVD:"+sequence[i]+"替代"+driver[index]);
*/