package flip_game;

import combine.Combine01;

import java.util.Arrays;

/**
 * @Author jinjun99
 * @Date Created in 2021/12/4 14:57
 * @Description
 * @Since version-1.0
 */
public class Demo01 {
    private static int n = 4;
    private static char[][] checkerboard = {
            {'b','w','w','b'},
            {'b','b','w','b'},
            {'b','w','w','b'},
            {'b','w','w','w'}
    };

    private static char[][] checkDemo;
    public static void main(String[] args) {
       flipGame();
       if (fea){
           System.out.println(leastFlipNum);
       }else {
           System.out.println("impossible");
       }
    }
    private static int currFlipNum = 0;
    private static int leastFlipNum = 0;
    private static void flipGame(){
        //第一行翻几个
        for (int i = 0; i <= n; i++) {
            //求组合
            Combine01 cb = new Combine01();
            int size= cb.getCombine(n,i);
            for (int j = 0; j < size; j++) {
                //初始化
                checkDemo = charArrCopy(checkerboard,n);
                currFlipNum = 0;
                for (int k = 0; k < i; k++) {
                    flip(cb.combineSet[j][k]-1,0);
                }
                currFlipNum+=i;
                flipNextRow();
            }
        }
    }
    private static boolean fea = false;
    private static void flipNextRow(){
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (checkDemo[j][i-1]=='b'){
                    flip(j,i);
                    currFlipNum++;
                }
            }
        }
        boolean bl = true;
        for (int i = 0; i < n; i++) {
            if (checkDemo[i][n-1]=='b'){
                bl = false;
                break;
            }
        }
        if (bl&&(currFlipNum<leastFlipNum||leastFlipNum==0)){
            fea = true;
            leastFlipNum = currFlipNum;
        }
    }

    private static int[] dirX = {0,1,0,-1};
    private static int[] dirY = {-1,0,1,0};
    private static void flip(int x, int y){
        for (int i = 0; i < 5; i++) {
            if (i==4){
                if (checkDemo[x][y]=='b'){
                    checkDemo[x][y]='w';
                }else {
                    checkDemo[x][y]='b';
                }
                continue;
            }
            int a = x+dirX[i];
            int b = y+dirY[i];
            if (a>=0&&b>=0&&a<n&&b<n){
                if (checkDemo[a][b]=='b'){
                    checkDemo[a][b]='w';
                }else {
                    checkDemo[a][b]='b';
                }
            }
        }
    }
    private static char[][] charArrCopy(char[][] c,int n){
        char[][] temp = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                temp[i][j] = c[i][j];
            }
        }
        return temp;
    }
}
