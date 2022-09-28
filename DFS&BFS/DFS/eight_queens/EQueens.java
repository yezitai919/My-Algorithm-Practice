package eight_queens;

/**
 * @Author jinjun99
 * @Date Created in 2021/11/19 16:51
 * @Description
 * @Since version-1.0
 */
public class EQueens {
    private static int n = 8;
    //临时记录一种方案，确保不冲突
    private static int[]x = new int[n+1];
    //方案数
    private static int scheme = 0;
    private static void backTrack(int t){
        //搜索第t层子树
        if (t>n){
            scheme++;
            return;
        }
        for (int i = 1; i <= n; i++) {
            x[t]=i;
            if (feasibility(t)){
                backTrack(t+1);
            }
        }
    }
    private static boolean feasibility(int t){
        for (int i = 1; i < t; i++) {
            if (Math.abs(i-t)==Math.abs(x[i]-x[t])||x[i]==x[t]){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        backTrack(1);
        System.out.println("方案数为："+scheme);
    }
}
