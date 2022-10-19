package poj3278;

import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author jinjun99
 * @Date Created in 2022/9/30 23:13
 * @Description
 * @Since version-1.0
 */
public class Main2 {
    static int N = 100005;
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int k = in.nextInt();
        Queue<Point> q = new LinkedBlockingQueue<Point>();
        int vis[] = new int[N];//标记是否走过
        Point p = new Point(n,0);
        q.add(p);
        while(!q.isEmpty()) {
            Point e = q.peek();
            if(e.x==k) {
                System.out.println(e.step);
                break;
            }
            if(e.x-1>=0&&vis[e.x-1]==0) {
                Point t = new Point(e.x-1,e.step+1);
                vis[e.x-1]=1;
                q.add(t);
            }
            if(e.x+1<N&&vis[e.x+1]==0) {
                Point t = new Point(e.x+1,e.step+1);
                vis[e.x+1]=1;
                q.add(t);
            }
            if(e.x*2<N&&vis[e.x*2]==0) {
                Point t = new Point(e.x*2,e.step+1);
                vis[e.x*2]=1;
                q.add(t);
            }
            q.poll();//队头出列
        }
        in.close();
    }

}
class Point{
    int x;//坐标
    int step;//步数

    public Point(int x, int step) {
        this.x = x;
        this.step = step;
    }
}
