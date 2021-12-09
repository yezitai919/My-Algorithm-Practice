package burning_trees;

import java.util.Scanner;

/**
 * @Author jinjun99
 * @Date Created in 2021/12/7 12:48
 * @Description
 * @Since version-1.0
 */
public class Demo01 {
    /**
     * 长
     */
    static int n;
    /**
     * 宽
     */
    static int m;
    static int[] fire = new int[2];
    /**
     * 地图
     */
    static int[][] map;

    /**
     * 要砍几颗树
     */
    static int cutNum;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int cases = sc.nextInt();
        System.out.println();
        for (int i = 0; i < cases; i++) {
            n = sc.nextInt();
            m = sc.nextInt();
            map = new int[n][m];
            fire[0] = sc.nextInt();
            fire[1] = sc.nextInt();
            cutNum = sc.nextInt();
            for (int j = 0; j < m; j++) {
                for (int k = 0; k < n; k++) {
                    map[k][j] = sc.nextInt();
                }
            }
            /*计算初始能烧的树的坐标*/
            BurningTreesDFS treesDFS = new BurningTreesDFS(n,m,fire,map);
            PointArrays t = treesDFS.getTrees();
            /*计算最优砍树方案*/
            CutTreesBFS treesBFS = new CutTreesBFS(n,m,fire,map,t,cutNum);
            Node cut = treesBFS.getCuttingScheme();
            System.out.println("#"+(i+1)+": "+cut.priority);
        }

    }
}
