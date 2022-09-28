package poj1088;

import java.util.Scanner;

/**
 * @Author jinjun99
 * @Date Created in 2022/9/26 18:38
 * @Description POJ1088
 * @Since version-1.0
 */
public class Main {
    /**
     * 行数
     */
    static int r;
    /**
     * 列数
     */
    static int c;
    /**
     * 滑雪场
     */
    static int[][] skiFacility;
    /**
     * 方向数组
     */
    static int[] dirX = {0,1,0,-1};
    static int[] dirY = {1,0,-1,0};
    static int currLength;
    static int maxLength;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
      //  while (sc.hasNext()){
            r = sc.nextInt();
            c = sc.nextInt();
            skiFacility = new int[105][105];
            sc.nextLine();
            for (int i = 0; i < r; i++) {
                String str = sc.nextLine();

                String[] s = str.split(" ");
//                System.out.println(Arrays.toString(s));
                for (int j = 0; j < c; j++) {
                    int temp = Integer.parseInt(s[j]);
//                    System.out.println(temp);
                    skiFacility[i][j]=temp;
                }
            }
            sc.close();
            currLength = 1;
            maxLength = 1;
            for (int i = 0; i < r; i++) {
                for (int j = 0; j < c; j++) {
                    dfs(i,j);
                }
            }
            System.out.println(maxLength);
       // }
    }

    private static void dfs(int x,int y) {
        /*是否有下一步*/
        boolean next = false;
        for (int i = 0; i < 4; i++) {
            int a = x+dirX[i];
            int b = y+dirY[i];
            boolean bl = a>=0&&b>=0&&a<r&&b<c;
            if (bl&&skiFacility[x][y]>skiFacility[a][b]){
                next = true;
                currLength++;
                dfs(a,b);
                currLength--;
            }
        }
        if (!next){
            if (currLength>maxLength){
                maxLength = currLength;
            }
        }
    }
}
/*
5 5
1 2 3 4 5
16 17 18 19 6
15 24 25 20 7
14 23 22 21 8
13 12 11 10 9

*/