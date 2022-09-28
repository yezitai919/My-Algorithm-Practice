package job_scheduling;

import java.util.Map;
import java.util.Stack;

/**
 * @Author jinjun99
 * @Date Created in 2021/11/19 15:34
 * @Description
 * @Since version-1.0
 */
public class JobSche {
    //作业数
    private static int n =3;
    //每个作业的在1，2机器上的加工时长
    private static int[][] machiningT = {{0,0},{2,1},{3,1},{2,3}};
    //确定安排过的作业
    private static boolean[] arranged = new boolean[n+1];
    //当前调度下第i个作业在机器1上完成处理的时间
    private static int[] f1 = new int[n+1];
    //当前调度下第i个作业在机器2上完成处理的时间
    private static int[] f2 = new int[n+1];
    //当前调度下所有作业的完成时间和
    private static int currentFtS = 0;
    //最优调度时所有作业的完成时间和
    private static int optimalFtS = Integer.MAX_VALUE;
    //当前作业调度方案
    private static Stack<Integer> currentSS = new Stack<>();
    //最优调度方案
    private static Stack<Integer> optimalSS = new Stack<>();

    public static void main(String[] args) {
        backTrack(1);
        System.out.println("最小时间和为："+optimalFtS);
        System.out.println("最优调度方案为："+optimalSS);
    }

    private static void backTrack(int i){
        if (i>n){
            if (currentFtS<optimalFtS){
                optimalFtS = currentFtS;
                optimalSS = (Stack<Integer>) currentSS.clone();
            }
            return;
        }
        for (int j = 1; j <=n ; j++) {
            //如果j作业没被安排，则将j作业安排到调度序列的第i个位置。
            if (!arranged[j]){
                //记录j作业在机器1上完成的时间
                f1[i] = f1[i-1]+machiningT[j][0];
                //记录j作业在机器2上完成时间
                if (i==1){
                    f2[i] = f1[1] + machiningT[j][1];
                }else {
                    f2[i] = Math.max(f2[i-1],f1[i])+machiningT[j][1];
                }
                currentFtS += f2[i];
                arranged[j] = true;
                currentSS.push(j);
                if (currentFtS<optimalFtS){
                    backTrack(i+1);
                }
                currentFtS -= f2[i];
                arranged[j] = false;
                currentSS.pop();
                f1[i] = 0;
                f2[i] = 0;
            }
        }
    }
}
