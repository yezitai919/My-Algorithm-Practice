package color_map;

/**
 * @Author jinjun99
 * @Date Created in 2021/11/19 17:17
 * @Description
 * @Since version-1.0
 */
public class ColorMap {
    //结点数
    private static int n = 6;
    //颜色数
    private static int m = 4;
    private static int[][] edge = {
            {0,1,0,0,0,1},
            {1,0,1,0,0,1},
            {0,1,0,1,1,0},
            {0,0,1,0,1,0},
            {0,0,1,1,0,1},
            {1,1,0,0,1,0}
    };//记录方案数
    private static int scheme = 0;
    //临时记录一种方案，确保不冲突
    private static int[] x = new int[n];
    public static void main(String[] args) {
        backTrack(0);
        System.out.println("方案数为："+scheme);
    }
    private static void backTrack(int i){
        if (i==n){
            scheme++;
            return;
        }
        for (int j = 1; j <= m; j++) {
            x[i] = j;
            if (feasibility(i)){
                backTrack(i+1);
            }
        }
    }
    private static boolean feasibility(int i){
        for (int j = 0; j < n; j++) {
            if (edge[i][j]==1&&x[i]==x[j]){
                return false;
            }
        }
        return true;
    }
}
