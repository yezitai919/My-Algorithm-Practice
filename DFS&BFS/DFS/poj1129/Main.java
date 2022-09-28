package poj1129;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @Author jinjun99
 * @Date Created in 2022/9/24 17:49
 * @Description
 * @Since version-1.0
 */
public class Main {
    /**
     * 中继器数量
     */
    static int n;
    /**
     * 中继器地图数组
     */
    static int[][] map;
    /**
     * 频道分配数组
     */
    static int[] distribute;
    /**
     * 用来记录频道数为2,3,4时分别的分配方案数
     */
    static int[] scheme = new int[3];
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()){
            /*获取第一个数字，即中继器数量*/
            n=sc.nextInt();
            /*如果数量为0，则结束*/
            if (n==0){
                break;
            }
            /*初始化中继器地图数组和频道数组*/
            map = new int[n][n];
            distribute = new int[n];
            scheme = new int[3];
            boolean side = false;
            /*接收一组数据输入，给数组赋值*/
            for (int i = 0; i < n; i++) {
                String str = sc.next();
                /*每个中继器按字母表顺序用字母表示，可以用第i个字母-第一个
                字母A，利用ASCII码之差得到对应字母在字母表中的序数，*/
                int charNum = str.charAt(0) - 'A';
                /*获取第一个字母表示的中继器相邻的其他中继器*/
                for (int j = 2; j < str.length(); j++) {
                    /*获取字母对应的序数*/
                    int adja = str.charAt(j)-'A';
                    map[charNum][adja]=1;
                    map[adja][charNum]=1;
                    side=true;
                }
            }
            int channel = 1;
            if (side){
               dfs(0,0,distribute);
               /*排错点1：*/
                /*System.out.println(Arrays.toString(scheme));*/
                for (int i = 0; i < 3; i++) {
                    if (scheme[i]!=0){
                        channel=i+2;
                        System.out.println(channel+" channels needed.");
                        break;
                    }
                }
            }else {
                System.out.println("1 channel needed.");
            }


        }
    }


    /**
     * @param r 当前中继器编号
     * @param c 当前子树能使用的频道数
     * @param d 当前的频道安排情况
     * @return
     */
    private static void dfs(int r,int c,int[] d){
        if (r==0){
            for (int i = 2; i <= 4; i++) {
                d=new int[n];
                dfs(1,i,d);
            }
        }else {
            if (r==n+1){
                scheme[c-2]++;
                return;
            }
            /*遍历当前能使用的频道数*/
            for (int i = 1; i <= c; i++) {
                /*对第r个中继器安排i频道*/
                d[r-1]=i;
                if (satisfied(r,c,d)){
                    /*排错点2：*/
/*                    System.out.println(r+""+c+Arrays.toString(d)+Arrays.toString(scheme));*/
                    dfs(r+1,c,d);
                }
                /*回溯省略，下一层循环自动刷新*/
            }
        }


    }

    /**
     * @param r 当前中继器编号
     * @param c 当前子树能使用的频道数
     * @param d 当前的频道安排情况
     * @return
     */
    private static boolean satisfied(int r,int c,int[]d){
        /*剪枝条件，如果当前限制的频道数(或前一个限制)还未找到一个符合条件的方案*/
        boolean condition = (c==2&&scheme[c-2]==0)||(c>2&&scheme[c-2]==0&&scheme[c-3]==0);
        if (condition){
            /*遍历地图找到当前中继器邻接的其他中继器，依次对比是否符合条件*/
            for (int i = 0; i < n; i++) {
                if (map[r-1][i]==1&&d[r-1]==d[i]){
                    /*排错点3：*/
                    /*System.out.println("r-1="+(r-1)+";  i="+i+"; d[r-1]="+d[r-1]+";  d[i]="+d[i]+";  map[r-1][i]="+map[r-1][i]);*/
                    return false;
                }
            }
            return true;
        }
        return false;
    }

}
