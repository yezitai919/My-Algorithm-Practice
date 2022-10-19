package POJ2698;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @Author jinjun99
 * @Date Created in 2022/9/29 16:47
 * @Description
 * @Since version-1.0
 */
public class Main2 {
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
     * 当前插入次数
     */
    static int currInsertions;
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
            /*先放k张碟进去*/
            for (int j = 0; j < k; j++) {
                driver[j] = sequence[j];
            }
            currInsertions = 0;
            minInsertions = 0;

            dfs(k);
            System.out.println(minInsertions);
        }

    }

    /**
     * @param index 当前请求DVD的序列下标
     */
    private static void dfs(int index) {
        if (index == l){
            if (minInsertions==0||currInsertions<minInsertions){
                minInsertions = currInsertions;
            }
            return;
        }
        /*当前请求的DVD号码是否在DVD机里*/
        boolean exist = false;
        for (int i = 0; i < k; i++) {
            if (driver[0]==sequence[index]){
                exist = true;
                break;
            }
        }
        if (exist){
            dfs(index+1);
        }else {
            for (int i = 0; i < k; i++) {
                int temp = driver[i];
                driver[i] = sequence[index];
                currInsertions++;
                dfs(index+1);
                currInsertions--;
                driver[i] = temp;
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
*/