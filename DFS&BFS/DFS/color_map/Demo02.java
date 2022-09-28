package color_map;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @Author jinjun99
 * @Date Created in 2022/9/25 13:48
 * @Description
 * @Since version-1.0
 */
public class Demo02 {
    static int[][] map;
    static boolean[] colored;
    static int n;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            n = sc.nextInt();
            if (n == 0)break;
            //初始化数组
            map = new int[n][n];
            colored = new boolean[n];
            //接收输入，给数组赋值
            for (int k = 0; k < n; k++) {
                String str = sc.next();

                int s = str.charAt(0) - 'A';
                for (int i = 2; i < str.length(); i++) {
                    int e = str.charAt(i) - 'A';
                    map[s][e] = 1;
                }
            }

            int cnum = dfs();
            if(cnum ==1) {
                System.out.println("1 channel needed.");
            } else {
                System.out.println(cnum+" channels needed.");
            }
        }

    }

    private static int dfs() {

        int color_num = 1;//颜色数目
        boolean no_side = true;//一条边都没有？？

        //如果不存在边，只需要一种颜色即可
        for(int i = 0;i<n;i++)
            for(int j = i+1;j<n;j++)
                if(map[i][j]==1){
                    no_side = false;
                    break;
                }
        if(no_side)return color_num;

        //存在边----------四色定理
        for(color_num = 2;color_num<=4;color_num++){
            int[] x = new int[n];//保存每个顶点所着的颜色
            Arrays.fill(x, -1);
            if(solve(x,0,color_num,n))
                return color_num;
        }
        return -1;
    }

    /*
     * @vnum:着色的顶点
     * @color_num：颜色数目
     * @n：顶点数目
     * */
    private static boolean solve(int[] x, int vnum, int color_num, int n) {
        if(vnum==n)return true;//如果已经着色的顶点==顶点数目表示着色完成

        for(int i = 0;i<color_num;i++){// 如果某个顶点没有颜色填，返回上一层
            x[vnum] = i;
            if(check(x,vnum,i,n))
                if(solve(x,vnum+1,color_num,n))// 合法，枚举下一个顶点
                    return true;
        }
        return false;
    }

    private static boolean check(int[] x, int vnum, int t, int n) {
        // TODO Auto-generated method stub
        boolean find = true;
        for (int i = 0; i < n && find; i++) {
            if (map[vnum][i] == 1 && x[i] == t)
                //如果顶点vnum和i之间有边，并且i已经着上了颜色t，那么vnum就不能着颜色t了。
            {
                find = false;
            }
        }
        return find;
    }

}
