package poj1321;

import java.util.Scanner;

/**
 * @Author jinjun99
 * @Date Created in 2022/10/4 17:07
 * @Description
 * @Since version-1.0
 */
public class Main2 {
    public static long count = 0;		//答案数
    public static int n,k;				//棋盘宽度和棋子数
    public static int[][] map;			//棋盘数组
    public static int[] status;			//根据此数组判定此列是否已被占用
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while(sc.hasNext()) {
            count = 0;
            n = sc.nextInt();
            k = sc.nextInt();
            if(n==-1 && k==-1)return;
            status = new int[n+1];
            map = new int[n+1][n+1];		//初始化

            for(int i = 1; i<=n;i++){
                String temp = sc.next();
                for(int j = 0;j<n;j++){
                    char ch = temp.charAt(j);
                    if(ch=='#')map[i][j+1] = 1;
                    else if(ch=='.')map[i][j+1] = 0;
                }
            }								//棋盘初始化

            dbs(1,0);						//初始参数：从第一行开始，已用0个棋子。
            System.out.println(count);

        }
    }

    private static void dbs(int i,int num) {
        if(num==k) {
            count++;
            return;							//当棋子用完时，结果加一，结束本次操作
        }
        if(i>n)
            return;							//越界处理，若行数大于棋盘宽度则取消此次操作

        for(int j = 1;j<=n;j++) {					//j代表一行中的第几列
            if(map[i][j]==1&&status[j]!=1) {		//判定当前格是否符合条件：当前格可使用且当前列未被使用
                status[j] = 1;						//若判定成功则将当前列占用
                dbs(i+1,num+1);						//继续下一行操作
                status[j] = 0;						//回溯：当前状况已使用完毕，恢复到初始状态
            }
        }

        dbs(i+1,num);								//当棋子数小于空位数时，先假设此行为空，直接去到下一行进行操作。
    }
}
