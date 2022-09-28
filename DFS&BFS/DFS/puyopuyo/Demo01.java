package puyopuyo;

import java.util.Stack;

/**
 * @Author jinjun99
 * @Date Created in 2021/11/20 21:46
 * @Description
 * @Since version-1.0
 */
public class Demo01 {
   /* *//**
     * 宽度
     *//*
    private static int width = 4;
    *//**
     * 地图
     *//*
    private static int[][] map = new int[width*10][width];
    *//**
     * 标记图
     *//*
    private static int[][] mark = new int[width*10][width];
    *//**
     * 列的高度，y坐标
     *//*
    private static int[] tail = new int[width*10];
    private static int[] dirX = {1,0,-1,0};
    private static int[] dirY = {0,1,0,-1};
    *//**
     * 当前块
     *//*
    private static Block block;
    *//**
     * 地图初始化
     *//*
    private static void inti(int w){
        width = w;
        map = new int[width*10][width];
    }

    *//**
     * 旋转一个角度
     * @param angle
     *//*
    private static void rotate(int angle){
        block.dir=(block.dir+angle)%4;
        //右边界
        if (block.dir==0&&block.col==width-1){
            block.col=width-2;
        }
        if (block.dir==2&&block.col==0){
            block.col=1;
        }
    }

    *//**
     * 移动一段距离
     * @param distance
     *//*
    private static void move(int distance){
        block.col+=distance;
        //中间区域不用考虑
        if (block.col>=1 && block.col<=width-2){
            return;
        }
        //右方向看右边界
        if (block.dir==0 && block.col>=width-1){
            block.col=width-2;
            return;
        }
        //左方向看左边界
        if (block.dir==2 && block.col<=0){
            block.col=1;
            return;
        }
        //其他方向右越界（dis过大）
        if (block.col > width-1){
            block.col = width-1;
        }
        //其他方向左越界（dis过大）
        if (block.col < 0){
            block.col = 0;
        }
    }
    private static void hinder(int count,int life){
        block.event = 2;
        for (int i = 0; i < count; i++) {
            map[i][tail[i]++] = -1*life;
        }
    }
    private static int land(){
        //1.将一个圆叠在另一个圆上，不留空隙。
        Point p1 = new Point();
        Point p2 = new Point();
        if (block.event==0){
            switch (block.dir){
                case 0:
                {
                    p1.x = block.col;
                    p1.y = tail[block.col];
                    map[block.col][tail[block.col]++] = block.val[0];
                    p2.x = block.col+1;
                    p2.y = tail[block.col+1];
                    map[block.col+1][tail[block.col+1]++] = block.val[1];
                    break;
                }
                case 1:
                {
                    p1.x = block.col;
                    p1.y = tail[block.col];
                    map[block.col][tail[block.col]++] = block.val[0];
                    p2.x = block.col;
                    p2.y = tail[block.col]+1;
                    map[block.col][tail[block.col]+2] = block.val[1];
                    break;
                }
                case 2:
                {
                    p1.x = block.col;
                    p1.y = tail[block.col];
                    map[block.col][tail[block.col]++] = block.val[0];
                    p2.x = block.col-1;
                    p2.y = tail[block.col-1];
                    map[block.col-1][tail[block.col-1]++] = block.val[1];
                    break;
                }
                case 3:
                {
                    p1.x = block.col;
                    p1.y = tail[block.col];
                    map[block.col][tail[block.col]++] = block.val[0];
                    p2.x = block.col;
                    p2.y = tail[block.col]-1;
                    map[block.col][tail[block.col]] = block.val[1];
                    break;
                }
                default:
                    break;
            }
            solution(p1,p2);
        }
        int j=0;
        for (int i = 0; i < width; i++) {
            j= Math.max(tail[i], j);
        }
        return j;
    }
    private static void solution(Point p1,Point p2){
        int dropNum = 2;
        Point[] drop = {p1,p2};
        while (dropNum!=0){
            for (int i = 0; i < dropNum; i++) {
                if (mark[drop[i].x][drop[i].y] == 1){
                    continue;
                }
                dfs(drop[i].x,drop[i].y);

                if (elimP.size() >= 4){
                    clear(elimP);
                }else {
                    elimP.removeAllElements();
                }
            }
            markHinder();
            updateMap();
        }
    }
    private static Stack<Point> elimP = new Stack<>();
    private static int[][] mark2 = new int[width*10][width];

    private static void dfs(int x,int y){

        elimP.push(new Point(x,y));
        for (int i = 0; i < 4; i++) {
            int a = x+dirX[i];
            int b = y+dirY[i];
            if (map[a][b]==map[x][y]&&mark2[a][b]==0){
                mark2[a][b]=1;
                dfs(a,b);
            }
        }

    }
    private static void clear(Stack<Point> elim){

    }
    private static void markHinder(){
       // int j = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < tail[i]; j++) {
                if (map[i][j]<0){
                    if (check(i,j)!=0){
                        map[i][j] += 1;
                        mark[i][j] = map[i][j] == 0?1:0;
                    }
                }
            }
        }
    }
    private static void updateMap(){

    }
    private static int check(int x,int y){
        for (int i = 0; i < 4; i++) {
            if (map[x+dirX[i]][y+dirY[i]]==0){
                return 0;
            }
        }
        return 1;
    }

    public static void main(String[] args) {

    }*/
}
