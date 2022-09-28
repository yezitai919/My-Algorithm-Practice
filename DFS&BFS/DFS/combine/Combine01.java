package combine;

import java.util.Arrays;

/**
 * @Author jinjun99
 * @Date Created in 2021/12/3 13:53
 * @Description
 * @Since version-1.0
 */
public class Combine01 {
    /**
     * 记录所有的组合，解空间
     */
    public int[][] combineSet = new int[1000][1000];
    /**
     * 记录组合数，解空间下标
     */
     int index1 = 0;

    /**
     * 临时解 防止一个解中的选择重复
     */
     int[] b;
    /**
     * 存储一个当前解
     */
     int[] cCombine;

    public Combine01() {
    }


    public static void main(String[] args) {
        Combine01 combine01 = new Combine01();
        combine01.getCombine(3,2);
        System.out.println(combine01.index1);
    }

    /**
     * 初始化成员变量，调用dfs求组合方法，返回组合树
     * @param n
     * @param m
     * @return
     */
    public int getCombine(int n, int m){

        b = new int[n];
        cCombine = new int[m];
        dfs(0,n,m);
        return index1;
    }

    private  void dfs(int lev,int n,int m){
        if (lev == m){
            if (index1 == 0||feasibility(m) == 1){
                for (int i = 0; i < m; i++) {
                    combineSet[index1][i]= cCombine[i];
                }
                index1++;
            }
            return;
        }
        for (int i = 0; i < n; i++) {
            if (b[i] == 0){
                b[i] = 1;
                cCombine[lev] = i+1;
                dfs(lev+1,n,m);
                b[i] = 0;
            }
        }
    }
    private  int feasibility(int m){
        for (int i = 0; i < index1; i++) {
            int sum = 0;
            for (int j = 0; j < m; j++) {
                for (int l = 0; l < m; l++) {
                    if (combineSet[i][j]== cCombine[l]){
                        sum++;
                    }
                }
            }
            if (sum==m){
                return 0;
            }
        }
        return 1;
    }

    /**
     * 求组合数
     * @param n
     * @param m
     * @return
     */
    public static int combineNum(int n,int m){
        return factorial(n)/(factorial(m)*factorial(n-m));
    }

    /**
     * 求阶乘
     * @param n
     * @return
     */
    public static int factorial(int n){
        if (n<=1){
            return 1;
        }
        return n*factorial(n-1);
    }

}
