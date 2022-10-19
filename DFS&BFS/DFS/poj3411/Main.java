package poj3411;

import java.util.Scanner;

/**
 * @Author jinjun99
 * @Date Created in 2022/10/17 22:44
 * @Description
 * @Since version-1.0
 */
public class Main {
    static int n;
    static int m;
    static class Rode{
        int a;
        int b;
        int c;
        int p;
        int r;

    }
    static Rode[] rodes;
    static int[] visit;
    static int minCost = 2000;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        m = sc.nextInt();
        rodes = new Rode[m+5];
        visit = new int[m+5];
        for (int i = 0; i < m; i++) {
            Rode rode = new Rode();
            rode.a = sc.nextInt();
            rode.b = sc.nextInt();
            rode.c = sc.nextInt();
            rode.p = sc.nextInt();
            rode.r = sc.nextInt();
            rodes[i] = rode;
        }
        dfs(1,0);
        if (minCost==2000){
            System.out.println("impossible");
        }else {
            System.out.println(minCost);
        }

    }

    private static void dfs(int city, int cost) {

        if (city==n){
            if (cost<minCost){
                minCost = cost;
            }
            return;
        }
        for (int i = 0; i < m; i++) {
            int b = rodes[i].b;
            if (city == rodes[i].a&&visit[b]<=3){
                visit[b]++;
                if (visit[rodes[i].c]>0){
                    dfs(b,cost+rodes[i].p);
                }else {
                    dfs(b,cost+rodes[i].r);
                }
                visit[b]--;
            }
        }
    }

}
/*
4 5
1 2 1 10 10
2 3 1 30 50
3 4 3 80 80
2 1 2 10 10
1 3 2 10 50
*/