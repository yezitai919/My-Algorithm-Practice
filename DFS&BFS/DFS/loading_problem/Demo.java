package loading_problem;

import java.util.Arrays;
import java.util.Stack;

/**
 * @Author jinjun99
 * @Date Created in 2021/11/19 15:00
 * @Description
 * @Since version-1.0
 */
public class Demo {
    private static int n = 5;//集装箱数量
    private static int[] w = {0,15,10,25,15,30} ;//各个集装箱重量,数组下标表示物品编号，忽略下标0的元素。
    private static int c1 = 50;//第一艘船的载重量
    private static int c2 = 50;//第二艘船的载重量
    private static int c1w = 0;//记录当前装入第一艘船的集装箱重量
    private static int c2w = 0;//记录当前装入第二艘船的集装箱重量
    private static int best1w = 0; //最终装入第一艘船的集装箱重量(最优值)
    private static int best2w = 0; //最终装入第二艘船的集装箱重量
    private static Stack<Integer> c1x = new Stack<>();//记录当前装入第一艘船的集装箱代号
    private static Stack<Integer> best1x = new Stack<>(); //最终装入第一艘船的集装箱代号(最优解)
    private static Stack<Integer> c2x = new Stack<>();//记录当前装入第一艘船的集装箱代号
    private static Stack<Integer> best2x = new Stack<>(); //最终装入第二艘船的集装箱代号
    private static long s,e;//算法计时
    public static void main(String[] args) {
        s=System.nanoTime();//回溯开始时间
        backtrack(1);//回溯法遍历解空间树
        e=System.nanoTime();//回溯结束时间
        int[] w1 = Arrays.copyOfRange(w,1,6);//消去0下标元素，方便打印
        System.out.println("各个集装箱的重量："+ Arrays.toString(w1));
        System.out.println("第一艘船载重量是："+c1+" ; "+"第二艘船载重量是："+c2+" ;");
        System.out.println("装入第一艘船的集装箱重量是："+best1w);
        System.out.println("装入第一艘船的集装箱编号是："+best1x);
        System.out.println("装入第二艘船的集装箱重量是："+best2w);
        System.out.println("装入第二艘船的集装箱编号是："+best2x);
        System.out.println("遍历解空间树用时："+(e-s)+"纳秒");
    }
    private static void backtrack (int i) { /*回溯搜索空间树，每一层结点表示对一个集装箱的选择，搜索第i层结点，就是选择当前集装箱i装入第几艘船。本题思路是先尽可能装满第一艘船，剩余的集装箱再装第二艘船，只需考虑第一艘船的最优装载就好了，因此左右子树的判断条件都以第一艘船为准。若当前集装箱i装第一艘船，则i+1就是i节点的左子树根节点。若当前集装箱i不装入第一艘船就装入第二艘船，则i+1就是i节点的右子树根节点。*/
        if (i > n) {  //到达叶结点为递归终止条件
            if (c1w > best1w) {// 更新最优值和最优解
                best1w = c1w;
                best2w = c2w;
                best1x = (Stack<Integer>)c1x.clone();
                best2x = (Stack<Integer>)c2x.clone();
            }
            return;
        }
        if (w[i] <= c1 - c1w) { //如果i集装箱重量不大于第一艘船剩余载重量就进入左子树
            c1x.push(i);//集装箱i装入第一艘船
            c1w += w[i];//更新第一艘船当前载重量
            backtrack(i + 1);//递归搜索左子树
            c1x.pop();//左子树搜索完就回溯到上一节点准备搜索右子树。
            c1w -= w[i];
        }
        if (upperBound(i + 1) > best1w) {/*先计算右子树根节点的上界，
        如果其上界函数值>当前的最优值best1w，右子树中就可能存在第一艘船的装载最优解，则进入右子树。*/
            c2x.push(i);//集装箱i不装入第一艘船就装入第二艘船。
            c2w += w[i];//更新第二艘船当前重量
            backtrack(i + 1);//递归搜索右子树
            c2x.pop();//右子树搜索完就回溯到上一节点，发现左右子树都搜索完就继续回溯上一节点准备搜索右子树。
            c2w -= w[i];
        }
    }
    private static int upperBound(int i) {
        int ci =  c1w;//当前节点的上界值 = 当前载重量c1w + 剩余未装船集装箱的重量
        while (i <=n ){
            ci += w[i];
            i++;
        }  return ci;
    }
}
