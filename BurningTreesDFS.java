package burning_trees;

/**
 * @Author jinjun99
 * @Date Created in 2021/12/7 12:37
 * @Description
 * @Since version-1.0
 */
public class BurningTreesDFS {
    /**
     * 长
     */
     int n;
    /**
     * 宽
     */
     int m;
     int[] fire;
    /**
     * 地图
     */
     int[][] map;
    /**
     * dfs标记数组，防止搜索重复的坐标
     */
     int[][] dfsMark;

    public BurningTreesDFS(int n, int m, int[] fire, int[][] map) {
        this.n = n;
        this.m = m;
        this.fire = fire;
        this.map = map;
    }
     int[] dirX = {0,1,0,-1};
     int[] dirY = {-1,0,1,0};
    /**
     * 存储当前可燃树的集合
     */
     PointArrays trees = new PointArrays();
     public PointArrays getTrees(){
         dfsMark = new int[n][m];
         dfsMark[fire[0]][fire[1]]=1;
         dfs(fire);
         return trees;
     }
    /**
     * 初始输入火的坐标，搜索当前状态下能烧掉的所有树的坐标放入可燃树集合
     * @param pos
     */
     void dfs(int[] pos){
        for (int i = 0; i < 4; i++) {
            int a = pos[0]+dirX[i];
            int b = pos[1]+dirY[i];
            boolean bl = a>=0&&b>=0&&a<n&&b<m;
            if (bl&&map[a][b]==1&&dfsMark[a][b]==0){
                dfsMark[a][b] = 1;
                int[] t = {a,b};
                trees.add(t);
                dfs(t);
            }
        }
    }
}
