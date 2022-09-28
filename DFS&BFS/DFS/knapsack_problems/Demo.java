package knapsack_problems;

import java.util.Arrays;
import java.util.Random;
import java.util.Stack;

/**
 * @Author jinjun99
 * @Date Created in 2021/11/19 9:56
 * @Description
 * @Since version-1.0
 */
public class Demo {
    private static int n = 20;//物品数量
    private static int c = 50;//背包容量
    private static int[] w = new int[n+1];//各个物品重量，为方便计算，数组下标从1开始使用。
    private static int[] v = new int[n+1];//各个物品价值
    private static double[] sortByV= new double[n+1];//单位重量的物品价值(降序)
    private static int currentW = 0;//当前背包中物品的总重量
    private static int currentV = 0;//当前背包中物品的总价值
    private static int bestV = 0;//当前背包中物品的最优总价值(最优值)
    private static Stack<Integer> currentX = new Stack<>();//当前装入背包中的物品编号
    private static Stack<Integer> bestX = new Stack<>();//最优值时装入背包的物品编号(最优解)
    private static long s,e;//回溯算法运行时间
    public static void main(String[] args) {
        Random r = new Random();
        for (int i = 1; i <=n ; i++) {//随机赋值
            w[i] = r.nextInt(10)+1;
            v[i] = r.nextInt(100)+1;
        }
        SortByValue();//按单位重量的物品价值降序排序
        s=System.nanoTime();//回溯开始时间
        backtrack(1);//从第一个物品开始回溯求解
        e=System.nanoTime();//回溯结束时间
        System.out.println("物品数量为："+n);
        System.out.println("背包容量为："+c);
        int[] w1 = Arrays.copyOfRange(w,1,n+1);//消去0下标元素，方便打印
        int[] v1 = Arrays.copyOfRange(v,1,n+1);
        System.out.println("物品重量为："+Arrays.toString(w1));
        System.out.println("物品价值为："+Arrays.toString(v1));
        System.out.println("最优值为："+bestV);
        System.out.println("最优解为："+bestX);
        System.out.println("遍历解空间树用时："+(e-s)+"(纳秒)");
    }
    public static void SortByValue (){//为方便计算上界函数，将物品按单位重量的价值降序排序
        double[] temp1 = new double[n+1];//临时数组
        double[] temp2 = new double[n+1];
        int[] temp3 = Arrays.copyOf(w,w.length);
        int[] temp4 = Arrays.copyOf(v,v.length);
        for (int i = 1; i <=n ; i++) {
            temp1[i]=(double) v[i]/w[i];//获得单位重量物品的价值,强制转换成浮点数。
            temp2[i]=(double) v[i]/w[i];
        }
        Arrays.sort(temp1);//升序
        int a =n;//数组下标最大值
        for (int i = 1; i <=n ; i++) {
            sortByV[i]=temp1[a];a--;//降序
        }
        //同步给w[]v[]排序，保持物品编号统一。
        for (int i = 1; i <=n ; i++) {//遍历sortByV[]
            for (int j = 1; j <=n ; j++) {//遍历temp2[]
                if (sortByV[i]==temp2[j]){//按sortByV[]的顺序寻找i物品原来的编号
                    w[i]=temp3[j];v[i]=temp4[j];/*按i物品原来的编号找到对应物品的重量和价值把w[] v[]重新排序，使其编号对应的物品与sortByV[]一样*/
                }
            }
        }
    }
    private static int upperBound(int i)   {/*上界函数值=当前节点的背包价值currentV +背包剩余容量c-currentW能装下的最大价值(只能装剩下未选过的物品)*/
        int ci = c - currentW; //当前节点的背包剩余容量
        int cvi = currentV;//当前节点的背包总价值
        // 把剩下未选过的物品以单位重量价值递减序装入背包
        while (i <= n &&  w[i] <= ci)      {
            ci -= w[i];
            cvi += v[i];
            i++;
        }
        // 最后一个装不下的物品按重量比例计算价值，完全装满背包。
        if (i <= n) cvi += v[i] / w[i] * ci;//
        return cvi;
    }
    private static void backtrack (int  i) {  /*回溯搜索空间树，一层结点表示对一个物品的选择，搜索第 i 层结点，选择当前物品是否放入。若当前物品i放入背包，则i+1就是i节点的左子树根节点。若当前物品i不放入背包，则i+1就是i节点的右子树根节点。*/
        if (i > n) {  // 到达叶结点为递归终止条件
            if (currentV > bestV) {//更新最优值和最优解
                bestV = currentV;
                bestX = (Stack<Integer>)currentX.clone();
            }
            return;
        }
        if (w[i] <= c-currentW) { //如果当前物品i的重量不大于背包剩余容量，则放入背包，进入左子树。
            currentX.push(i);//物品i装入背包
            currentV += v[i];//更新当前背包的总重量和总价值。
            currentW += w[i];
            backtrack(i + 1);//递归搜索左子树
            currentX.pop();//左子树搜索完就回溯到上一节点准备搜索右子树。
            currentW -= w[i];
            currentV -= v[i];
        }
        if (upperBound(i + 1) > bestV){  /* 先计算右子树根节点的上界，
        如果其上界函数值>当前的最优值bestV，右子树中就可能存在最优解，则进入右子树。*/
            backtrack(i + 1);  //递归搜索右子树
        }
    }
}
