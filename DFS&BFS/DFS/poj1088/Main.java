package poj1088;

import java.util.Arrays;
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
    static int[][] storage;
    /**
     * 方向数组
     */
    static int[] dirX = {0,1,0,-1};
    static int[] dirY = {1,0,-1,0};
    /**
     * 当前长度
     */
    static int currLength;
    /**
     * 最大长度
     */
    static int maxLength;
    /**
     * 每个格子能滑的最大长度
     */
    static int indexLength;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()){
            r = sc.nextInt();
            c = sc.nextInt();

            skiFacility = new int[r+5][c+5];
            storage = new int[r+5][c+5];
            sc.nextLine();
            for (int i = 0; i < r; i++) {
               /* String str = sc.nextLine();
                String[] s = str.split(" ");*/
                for (int j = 0; j < c; j++) {
//                    int temp = Integer.parseInt(s[j]);
                    skiFacility[i][j]=sc.nextInt();
                }
            }
            maxLength = 1;
            for (int i = 0; i < r; i++) {
                for (int j = 0; j < c; j++) {
                    indexLength = 1;
                    currLength = 1;
                    dfs(i,j);
                    storage[i][j] = indexLength;
                }
            }
            System.out.println(maxLength);
        }

    }
    private static void dfs(int x,int y) {
        /*是否有存储*/
        boolean save = false;
        /*是否有下一步*/
        boolean next = false;

        if (storage[x][y]!=0){
            /*当前格子有存储直接加上存储长度结束*/
            currLength+=storage[x][y]-1;
            save = true;
        }else {
            /*没有存储就四个方向搜索一下*/
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
        }
        if (!next){
            /*记录当前格子的最长路径*/
            if (currLength> indexLength){
                indexLength = currLength;
            }
            /*记录总的最长路径*/
            if (currLength>maxLength){
                maxLength = currLength;
            }
            if (save){
                /*对于有存储的子树的回溯*/
                currLength-=storage[x][y]-1;

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
计算时长：2218ms
计算时长：1125ms
计算时长：606ms
storage[0][1] = indexLength;
storage[0][0]=1;
storage[1][0]=2;
storage[2][0]=3;
storage[3][0]=4;
storage[4][0]=5;
indexLength = 1;
currLength = 1;
dfs(1,1);

if (storage[x][y]!=0){
            currLength+=storage[x][y]-1;
            save = true;
        }else
  这一步忘记回溯了
5 5
1 2 3 4 5
16 17 18 19 6
15 24 25 20 7
14 23 22 21 8
13 12 11 10 9
5 5
1 2 3 4 5
16 18 18 19 6
15 24 25 30 7
14 23 22 21 8
13 12 12 10 9
第二个错：输入错了，就正常的双循环x=i，y=j就行了，没必要反过来

   for (int i = 0; i < r; i++) {
                for (int j = 0; j < c; j++) {

                    if (j==c-1){
                        System.out.println(storage[j][i]);

                    }else {

                        System.out.print(storage[j][i]+" ");
                    }
                }

            }
*/
//        long st = System.currentTimeMillis();
  /*      long e = System.currentTimeMillis();
        System.out.println("计算时长："+(e-st)+"ms");*/
