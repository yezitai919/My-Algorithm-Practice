package puyopuyo;

import java.util.Arrays;
import java.util.Stack;

/**
 * @Author jinjun99
 * @Date Created in 2021/11/21 17:36
 * @Description
 * @Since version-1.0
 */
public class Demo02 {
    /**
     * 宽度
     */
    private static int width = 4;
    /**
     * 高度
     */
    private static int height = 6;
    /**
     * 地图
     */
    private static int[][] map = new int[height][width];
    /**
     * 标记图
     */
    private static int[][] mark = new int[height][width];
    /**
     * 列的高度，y坐标
     */
    private static int[] tail = new int[width];
    private static int[] dirX = {1,0,-1,0};
    private static int[] dirY = {0,1,0,-1};
    /**
     * 当前块
     */
    private static Block block;
    /**
     * 地图初始化
     */
    private static void inti(int w){
        width = w;
        map = new int[height][width];
    }
    /**
     * 旋转一个角度
     * @param angle
     */
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
    /**
     * 移动一段距离
     * @param distance
     */
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
       // block.event = 2;
        int h = height-1;
        for (int i = 0; i < count; i++) {
            map[h-tail[i]][i] = -1*life;
            tail[i]++;
        }
    }
    /**
     * 更新当前块下落后的地图，记录列高度
     * @return
     */
    private static int land(){
        //1.将一个圆叠在另一个圆上，不留空隙。
        Point p1 = new Point();
        Point p2 = new Point();
        int h = height-1;
        if (block.event==0){
            switch (block.dir){
                case 0:
                {
                    p1.x = block.col;
                    p1.y = h-tail[block.col];
                    map[p1.y][p1.x] = block.val[0];
                    p2.x = block.col+1;
                    p2.y = h-tail[block.col];
                    map[p2.y][p2.x] = block.val[1];
                    tail[block.col]++;
                    tail[block.col+1]++;
                    break;
                }
                case 1:
                {
                    p1.x = block.col;
                    p1.y = h-(tail[block.col]+1);
                    map[p1.y][p1.x] = block.val[0];
                    p2.x = block.col;
                    p2.y = h-tail[block.col];
                    map[p2.y][p2.x] = block.val[1];
                    tail[block.col]+=2;
                    break;
                }
                case 2:
                {
                    p1.x = block.col;
                    p1.y = h-tail[block.col];
                    map[p1.y][p1.x] = block.val[0];
                    p2.x = block.col-1;
                    p2.y = h-tail[block.col-1];
                    map[p2.y][p2.x] = block.val[1];
                    tail[block.col]++;
                    tail[block.col-1]++;
                    break;
                }
                case 3:
                {
                    p1.x = block.col;
                    p1.y = h-tail[block.col];
                    map[p1.y][p1.x] = block.val[0];
                    p2.x = block.col;
                    p2.y = h-(tail[block.col]+1);
                    map[p2.y][p2.x] = block.val[1];
                    tail[block.col]+=2;
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
    private static Point[] drop = new Point[width*height];

    /**
     * 块下落后的消除
     * @param p1
     * @param p2
     */
    private static void solution(Point p1,Point p2){
        int dropNum = 2;
        drop[0] = p1;
        drop[1] = p2;

        while (dropNum!=0){
            for (int i = 0; i < dropNum; i++) {
                //一组两个颜色相同
                if (mark[drop[i].y][drop[i].x] == 1){
                    continue;
                }
                dfs(drop[i].x,drop[i].y);
                clear();
            }
            markHinder();
            dropNum = updateMap();
        }
    }
    private static Stack<Point> elimP = new Stack<>();
    private static int[][] mark2 = new int[height][width];

    /**
     * 搜索4个同色相连块，放入搜索队列
     * @param x
     * @param y
     */
    private static void dfs(int x,int y){
        mark2[y][x] = 1;
        elimP.push(new Point(x,y));
        for (int i = 0; i < 4; i++) {
            int a = x+dirX[i];
            int b = y+dirY[i];
            if (a>=0&&a<width&&b>=0&&b<height&&map[b][a]==map[y][x]&&mark2[b][a]==0){
                dfs(a,b);
            }
        }
    }

    /**
     * 根据搜索结果清理同色块
     */
    private static void clear(){
        int s = elimP.size();
        if (s>=4){
            for (int i = 0; i < s; i++) {
                Point p = elimP.pop();
                map[p.y][p.x]=0;
                mark[p.y][p.x]=1;
                tail[p.x]--;
            }
        }
        elimP.removeAllElements();

    }
    /**
     * 消除同色块后，更新阻碍块寿命和位置，顺便消除标记
     */
    private static void markHinder(){
        int h = height-1;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j <= tail[i]; j++) {
                if (map[h-j][i]<0){
                    if (check(h-j,i)==1){
                        map[h-j][i] += 1;
                        if (map[h-j][i]==0){
                            tail[i]--;
                        }
                    }
                }
            }
        }
        mark2 = new int[height][width];
        mark = new int[height][width];
    }

    /**
     * 同色块消除后，把空中的块放下来，顺便放入搜索队列
     * @return 空中块的数量
     */
    private static int updateMap(){
        int c = 0;
        int s = 0;
        int sum = 0;
        int h = height-1;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < tail[i]; j++) {
                int l = h-j;
                if (map[l][i]==0){
                    if (j>=tail[i]){
                        break;
                    }
                    //j=1,c=3
                    while (map[l-c][i]==0&&j+c<tail[i]){
                        c++;
                    }
                    if (j+c+1==tail[i]){
                        tail[i]=j;
                        break;
                    }
                    if (map[l-c][i]>0) {
                        map[l][i] = map[l - c][i];
                        map[l - c][i] = 0;
                        drop[s].x = i;
                        drop[s++].y = l;
                        sum++;
                    }
                    if (map[l-c][i]<0) {
                        map[l][i] = map[l - c][i];
                        map[l - c][i] = 0;
                    }
                }
            }
        }
        return sum;

    }

    /**
     * 查看阻碍块四周是否有爆裂的同色块
     * @param y
     * @param x
     * @return
     */
    private static int check(int y,int x){
        for (int i = 0; i < 4; i++) {
            int a = y+dirY[i];
            int b = x+dirX[i];
            if (a>=0&&b>=0&&a<height&&b<width&&mark[a][b]==1){
                return 1;
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        int sum = 0;
        block = new Block(new int[]{1,1});
        rotate(0);
        move(1);
        sum = land();
       hinder(2,2);
        hinder(4,1);
        block = new Block(new int[]{2,2});
        rotate(0);
        move(2);
        sum = land();

        block = new Block(new int[]{1,1});
        rotate(1);
        move(2);
        sum = land();
        block = new Block(new int[]{2,2});
        rotate(1);
        move(3);
        sum = land();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (map[i][j]<0){
                    System.out.print(map[i][j]);
                }else {
                    System.out.print(" "+map[i][j]);
                }
                if (j==width-1){
                    System.out.println();
                }
            }
        }
        System.out.println("最高列长度是："+sum);
    }
}
