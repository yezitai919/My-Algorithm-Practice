package knapsack_problems;

import java.util.Arrays;
import java.util.Random;
import java.util.Stack;

/**
 * @Author jinjun99
 * @Date Created in 2021/11/19 9:35
 * @Description
 * @Since version-1.0
 */
public class Backtrack {
    private static int n = 10;//物品数量
    private static int c = 50;//背包容量
    private static int[] w = new int[n+1];//各个物品重量，为方便计算，数组下标从1开始使用。
    private static int[] v = new int[n+1];//各个物品价值
    private static int currentW = 0;//当前背包中物品的总重量
    private static int currentV = 0;//当前背包中物品的总价值
    private static int bestV = 0;//当前背包中物品的最优总价值(最优值)
    private static Stack<Integer> currentX = new Stack<>();//当前装入背包中的物品编号
    private static Stack<Integer> bestX = new Stack<>();//最优值时装入背包的物品编号(最优解)


    private static void sortByValue(){
        //升序
        double[] temp1 = new double[n+1];
        //记录原下标
        double[] temp2 = new double[n+1];
        int[] tempW = new int[n+1];
        int[] tempV = new int[n+1];

        for (int i = 1; i <= n; i++) {
            temp1[i]=(double) v[i]/w[i];
            temp2[i]=(double) v[i]/w[i];
            tempW[i] = w[i];
            tempV[i] = v[i];
        }
        Arrays.sort(temp1);
        int c = n;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (temp1[i]==temp2[j]){
                    w[c]=tempW[j];
                    v[c]=tempV[j];
                    c--;
                    break;
                }
            }
        }
    }
    private static int upperBound(int i){
        int cwi = currentW;
        int cvi = currentV;
        while (i<n && w[i]<c-cwi){
            cwi += w[i];
            cvi += v[i];
            i++;
        }
        if (i<n){
            cvi += (c-cwi)/w[i]*v[i];
        }
        return cvi;
    }
    private static void backTrack(int i){
        if (i>n){
            if (currentV>bestV){
                bestV = currentV;
                bestX = (Stack<Integer>) currentX.clone();
            }
            return;
        }
        if (w[i]<= c-currentW){
            currentX.push(i);
            currentV += v[i];
            currentW += w[i];
            backTrack(i+1);
            currentX.pop();
            currentV -= v[i];
            currentW -= w[i];
        }
        if (upperBound(i+1)>bestV){
            backTrack(i+1);
        }
    }
    public static void main(String[] args) {
        Random r = new Random();
        for (int i = 1; i <=n ; i++) {//随机赋值
            w[i] = r.nextInt(10)+1;
            v[i] = r.nextInt(100)+1;
        }
        sortByValue();//按单位重量的物品价值降序排序
        backTrack(1);//从第一个物品开始回溯求解
        System.out.println("物品数量为："+n);
        System.out.println("背包容量为："+c);
        int[] w1 = Arrays.copyOfRange(w,1,n+1);//消去0下标元素，方便打印
        int[] v1 = Arrays.copyOfRange(v,1,n+1);
        System.out.println("物品重量为："+Arrays.toString(w1));
        System.out.println("物品价值为："+Arrays.toString(v1));
        System.out.println("最优值为："+bestV);
        System.out.println("最优解为："+bestX);
    }

}
