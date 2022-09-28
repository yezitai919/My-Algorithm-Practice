package loading_problem;

import java.util.Arrays;
import java.util.Stack;

/**
 * @Author jinjun99
 * @Date Created in 2021/11/19 9:03
 * @Description
 * @Since version-1.0
 */
public class Backtrack {
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

    public static void main(String[] args) {

        backTrack(1);
        int[] w1 = Arrays.copyOfRange(w,1,6);//消去0下标元素，方便打印
        System.out.println("各个集装箱的重量："+ Arrays.toString(w1));
        System.out.println("第一艘船载重量是："+c1+" ; "+"第二艘船载重量是："+c2+" ;");
        System.out.println("装入第一艘船的集装箱重量是："+best1w);
        System.out.println("装入第一艘船的集装箱编号是："+best1x);
        System.out.println("装入第二艘船的集装箱重量是："+best2w);
        System.out.println("装入第二艘船的集装箱编号是："+best2x);
    }
    private static void backTrack(int i){
        if (i>n){
            if(c1w>best1w){
                best1w=c1w;
                best2w=c2w;
                best1x = (Stack<Integer>)c1x.clone();
                best2x = (Stack<Integer>)c2x.clone();
            }
            return;
        }
        if (w[i]<=c1-c1w){
            c1x.push(i);
            c1w += w[i];
            backTrack(i+1);
            c1x.pop();
            c1w -= w[i];
        }
        if (upperBound(i+1)>best1w){
            c2x.push(i);
            c2w += w[i];
            backTrack(i+1);
            c2x.pop();
            c2w-=w[i];
        }
    }
    private static int upperBound(int i){
        int ci = c1w;
        while (i<=n){
            ci+=w[i];
            i++;
        }
        return ci;
    }
}
