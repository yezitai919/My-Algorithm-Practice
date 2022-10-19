package poj2657;

import java.util.Scanner;

/**
 * @Author jinjun99
 * @Date Created in 2022/10/11 13:01
 * @Description
 * @Since version-1.0
 */
public class Main1 {


    static int N, Z, M;
    static boolean[] vis;

    static int ans;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        Z = sc.nextInt() - 1;
        M = sc.nextInt();

        vis = new boolean[N];
        for (int i = 0; i < M; i++)
            vis[sc.nextInt() - 1] = true;

        sc.close();

        ans = Z;
        for (int i = Z - 1; i > 0; i--)
            dfs(0, i);
        System.out.println(ans);
    }

    private static void dfs(int index, int step) {
        if (index == Z) {
            ans = step;
            return;
        }

        if (index == 0)
            return;

        if (vis[index])
            return;

        dfs((index + step) % N, step);
    }

}
