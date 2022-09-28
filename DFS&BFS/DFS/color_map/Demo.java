package color_map;

/**
 * @Author jinjun99
 * @Date Created in 2021/11/19 17:15
 * @Description
 * @Since version-1.0
 */
public class Demo {
    //结点数
    private static int n = 6;
    //颜色数
    private static int m = 3;
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
        backtrack(0);
        System.out.println("方案数为："+scheme);
    }
    private static void backtrack(int t){
        //到达叶子节点
        if (t == n){
            scheme++;
            return;
        }//遍历每种颜色
        for (int i = 1; i <= m; i++) {
            //给当前顶点着i色
            x[t] = i;
            //如果当前顶点与之前着色的顶点不冲突
            if (feasibility(t)) {
                //递归下一顶点
                backtrack(t+1);
            } //回溯过程省略了，因为下一层自动刷新x[t]的值。
        }
    }
    private static boolean feasibility( int t){
        for (int k = 0; k < n; k++) {
            if (edge[t][k] == 1 && x[t]!=x[k]) {
                return true;
            }
        }
        return false;
    }
}
