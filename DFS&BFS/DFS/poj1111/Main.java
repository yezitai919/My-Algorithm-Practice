package poj1111;

import java.util.Scanner;

/**
 * @Author jinjun99
 * @Date Created in 2022/9/27 9:49
 * @Description
 * @Since version-1.0
 */
public class Main {
    /**
     * 行
     */
    static int r;
    /**
     * 列
     */
    static int c;
    /**
     * 初始坐标
     */
    static int x;
    static int y;
    /**
     * 图像数组
     */
    static char[][] img;
    /**
     * 标记数组
     */
    static int[][] mark;
    /**
     * 周长
     */
    static int perimeter;



    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()){
            String str = sc.nextLine();
            String[] s = str.split(" ");
            r = Integer.parseInt(s[0]);
            c = Integer.parseInt(s[1]);
            x = Integer.parseInt(s[2])-1;
            y = Integer.parseInt(s[3])-1;
            if (r==0 && c==0 && x==-1 && y==-1) {
                break;
            }
            img = new char[r][c];
            mark = new int[r][c];
            for (int i = 0; i < r; i++) {
                String s1 = sc.nextLine();
                char[] c1 = s1.toCharArray();
                for (int j = 0; j < c; j++) {
                    img[i][j] = c1[j];
                }
            }
            perimeter = 0;
            dfs(x,y);
            System.out.println(perimeter);
        }
    }
    /**
     * 方向数组
     */
    private static int[] dirX = {0,1,0,-1,-1,1,-1,1};
    private static int[] dirY = {1,0,-1,0,-1,1,1,-1};
    private static void dfs(int x, int y) {
        /*dfs所有'X'*/
        if (img[x][y]=='X'&&mark[x][y]==0){
            mark[x][y]=1;
            for (int i = 0; i < 8; i++) {
                int a = x+dirX[i];
                int b = y+dirY[i];
                boolean bl1 = a>=0&&b>=0&&a<r&&b<c;

                /*如果出界或者是'.'*/
                if (!bl1 || img[a][b] == '.'){
                    /*累计边长*/
                    if (i<4){
                        perimeter++;
                    }
                }else {
                    dfs(a,b);
                }
            }
        }
    }
}
/*

2 2 2 2
XX
XX
6 4 2 3
.XXX
.XXX
.XXX
...X
..X.
X...
5 6 1 3
.XXXX.
X....X
..XX.X
.X...X
..XXX.
7 7 2 6
XXXXXXX
XX...XX
X..X..X
X..X...
X..X..X
X.....X
XXXXXXX
7 7 4 4
XXXXXXX
XX...XX
X..X..X
X..X...
X..X..X
X.....X
XXXXXXX
0 0 0 0

*/