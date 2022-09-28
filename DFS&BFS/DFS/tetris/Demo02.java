package tetris;

import java.util.Stack;

/**
 * @Author jinjun99
 * @Date Created in 2021/11/22 16:44
 * @Description
 * @Since version-1.0
 */
public class Demo02 {
    private static int width = 5;
    private static int height = 10;
    private static int[][] map = new int[width][height];
    private static int[][] mark1 = new int[width][height];
    private static int[][] mark2 = new int[width][height];
    private static Block block;
    private static int[] tail = new int[height];
    private static Point[] drop = new Point[width*height];

    /**
     * 跑马灯移动数组
     * @param num 移动位数
     */
    private static void rotate(int num){
        if (num == 1){
            int temp = 0;
            temp = block.val[0];
            block.val[0] = block.val[2];
            block.val[2] = block.val[1];
            block.val[1] = temp;
        }
        if (num == 2){
            int temp = 0;
            temp = block.val[0];
            block.val[0] = block.val[1];
            block.val[1] = block.val[2];
            block.val[2] = temp;
        }
    }

    /**
     * 移动
     * @param distance
     */
    private static void move(int distance){
        block.col+=distance;
        if (block.col>=width){
            block.col=width-1;
        }
        if (block.col<0){
            block.col=0;
        }
    }

    /**
     * 块落地后的坐标
     * @param args
     */
    private static void simpleLand(){
        if (block.event==0){
            drop[0]=new Point(block.col,tail[block.col]++);
            drop[1]=new Point(block.col,tail[block.col]++);
            drop[2]=new Point(block.col,tail[block.col]++);
            map[drop[0].x][drop[0].y] = block.val[2];
            map[drop[1].x][drop[1].y] = block.val[1];
            map[drop[2].x][drop[2].y] = block.val[0];
           // int dropNum = 3;
            solution(3);
        }
    }
    private static void solution(int dropNum){
        while (dropNum!=0){
            for (int i = 0; i < dropNum; i++) {
                if (map[drop[i].x][drop[i].y]==0){
                    continue;
                }
                dfs1(drop[i].x,drop[i].y);
                clear();
            }
            dropNum = updateMap();
        }
    }
    private static int[] dirX = {0,1,1,1,0,-1,-1,-1};
    private static int[] dirY = {1,1,0,-1,-1,-1,0,1};
    /**
     * 消除点集
     */
    private static Stack<Point> eliminate = new Stack<>();

    /**
     *
     * @param x
     * @param y
     */
    private static void dfs1(int x, int y){
        Stack<Point> temp = new Stack<>();
        if (map[x][y]!=0){
            for (int i = 0; i < 4; i++) {
                int l = i;
                int k = 0;
                int a = x;
                int b = y;
                while (k!=2){
                    a+=dirX[l];
                    b+=dirY[l];
                    if (a>=0&&b>=0&&a<width&&b<height&&map[x][y]==map[a][b]&& mark1[a][b]==0){
                        mark1[a][b]=1;
                        temp.push(new Point(a,b));
                    }else {
                        k++;
                        l+=4;
                        a=x;
                        b=y;
                    }
                }
                int s = temp.size();
                if (s>=2&& mark1[x][y]==0){
                    mark1[x][y]=1;
                    eliminate.push(new Point(x,y));

                }
                for (int j = 0; j < s; j++) {
                    Point p = temp.pop();
                    if (s>=2){
                        eliminate.push(p);
                        dfs1(p.x,p.y);
                    }
                    //回溯
                    mark1[p.x][p.y]=0;
                }
            }
            mark1[x][y]=0;
        }
    }
    private static void specialLand(){
        int x = block.col;
        int y = tail[block.col]-1;
        if (block.type==1){
            int color = map[x][y];
            for (int i = 0; i < width; i++) {
                int h = tail[i];
                for (int j = 0; j < h; j++) {
                    if (map[i][j]==color){
                        map[i][j]=0;
                        tail[i]--;
                    }
                }
            }
        }

        if (block.type==2){
            dfs2(x,y);
            clear();
        }
        int d = updateMap();
        solution(d);
    }

    private static void dfs2(int x,int y){
        eliminate.push(new Point(x,y));
        mark2[x][y]=1;
        for (int i = 0; i < 8; i++) {
            int a = x+dirX[i];
            int b = y+dirY[i];
            if (a>=0&&b>=0&&a<width&&b<height&&map[a][b]==map[x][y]&& mark2[a][b]==0){
                dfs2(a,b);
            }
        }
    }

    /**
     * 根据搜索结果清理同色块
     */
    private static void clear(){
        int s = eliminate.size();
        for (int i = 0; i < s; i++) {
            Point p = eliminate.pop();
           // System.out.println("clear:map["+p.x+"]["+p.y+"]   ");
            if (map[p.x][p.y]!=0){
                map[p.x][p.y]=0;
                tail[p.x]--;
            }

        }
        mark1 = new int[width][height];
        mark2 = new int[width][height];
        eliminate.removeAllElements();
    }

    /**
     * 更新块下落后的地图
     */
    private static int updateMap(){
        //下落数组下标，把所有下落的块按顺序放入下落数组
        int a = 0;
        //记录下落块数
        int sum = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < tail[i]; j++) {
                if (map[i][j]==0){
                    //下落高度
                    int h = 1;
                    while (map[i][j+h]==0&&j+h<height){
                        h++;
                    }
                    map[i][j] = map[i][j + h];
                    map[i][j + h] = 0;
                    drop[a++] = new Point(i, j);
                    sum++;
                }
            }
        }
        return sum;
    }


    private static void newSimpleBlock(int a, int b, int c){
        block = new Block(new int[]{a,b,c});
    }
    private static void newSpecialBlock(int type){
        block = new Block(1,type);
    }
    public static void main(String[] args) {
        newSimpleBlock(1,3,2);
        move(0);
        simpleLand();
       newSimpleBlock(3,4,2);
        move(1);
        simpleLand();

        newSimpleBlock(2,5,3);
        move(3);
        simpleLand();

        newSimpleBlock(5,3,2);
        move(2);
        simpleLand();

        newSimpleBlock(2,5,2);
        move(4);
        simpleLand();

        newSpecialBlock(2);
        move(3);
        specialLand();

        newSimpleBlock(1,2,1);
        move(2);
        simpleLand();
        newSpecialBlock(1);
        specialLand();

        for (int i = height-1; i >= 0; i--) {
            for (int j = 0; j < width; j++) {

                System.out.print(" "+map[j][i]+" ");
                if (j== width -1){
                    System.out.println();
                }
            }
        }
    }
}

