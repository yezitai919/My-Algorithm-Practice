package poj1020;

import java.util.Scanner;

/**
 * @Author jinjun99
 * @Date Created in 2022/10/11 20:21
 * @Description
 * @Since version-1.0
 */
public class Main {

    /**
     * 大蛋糕的边 [1,40]
     */
    static int lCakeSide;
    /**
     * 大蛋糕的列，用列的高度来记录分配小蛋糕的情况
     */
    static int[] lCakeColumn;
    /**
     * 小蛋糕数量  [1,16]
     */
    static int sCakeNum;
    /**
     * 各种尺寸的蛋糕数量
     * 小蛋糕的边 [1,10]
     */
    static int[] sCakeSide;

    /**
     *
     */
    static boolean succeed;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        for (int i = 0; i < n; i++) {
            lCakeSide = sc.nextInt();
            sCakeNum = sc.nextInt();
            lCakeColumn = new int[lCakeSide+5];
            sCakeSide = new int[sCakeNum+5];
            for (int j = 0; j < sCakeNum; j++) {
                int ind = sc.nextInt();
                sCakeSide[ind]++;
            }
            succeed = false;
            dfs(0);
            if (succeed){
                System.out.println("KHOOOOB!");
            }else {
                System.out.println("HUTUTU!");
            }
        }
    }

    /**
     * @param layer 递归的层，放入小蛋糕的数量
     */
    private static void dfs(int layer) {
        if (layer==sCakeNum){
            succeed = true;
            return;
        }
        /*找出最小的列*/
        int min = 50;
        int colIndex = 0;
        for (int i = 0; i < lCakeSide; i++) {
            if (lCakeColumn[i]<min){
                min = lCakeColumn[i];
                colIndex = i;
            }
        }
        /*枚举各种尺寸的蛋糕自下而上地放入盒子*/
        for (int size = 10; size >=1 ; size--) {
            if (sCakeSide[size]<=0){
                continue;
            }
            /*检查尺寸为size的蛋糕放入盒子时在纵向和横向是否越界*/
            if (lCakeSide-lCakeColumn[colIndex]>=size&&lCakeSide-colIndex>=size){

                int wide = 0;
                int rigIndex = colIndex+size;
                /*判断有没有足够多的列，一开始是打算用bool，后来发现有漏洞*/
                for (int r = colIndex; r < rigIndex; r++) {
                   if (lCakeColumn[r] <= lCakeColumn[colIndex]){
                       wide++;
                   }
                }
                if (wide==size){
                    /*放入该尺寸的蛋糕*/
                    sCakeSide[size]--;
                    for (int r = colIndex; r < rigIndex; r++) {
                        lCakeColumn[r]+=size;
                    }
                    dfs(layer+1);
                    /*如果成功就剪枝*/
                    if (succeed) {
                        return;
                    }
                    System.out.println();
                    sCakeSide[size]++;
                    for (int r = colIndex; r < rigIndex; r++) {
                        lCakeColumn[r]-=size;
                    }
                }
            }
        }
    }
}
/*
2
4 8 1 1 1 1 1 3 1 1
5 6 3 3 2 1 1 1
用数组存储各种尺寸的小蛋糕的数量，比直接存储尺寸方便很多，少了排序，标记等步骤。
*/