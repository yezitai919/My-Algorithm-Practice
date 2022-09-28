package eight_queens;

/**
 * @Author jinjun99
 * @Date Created in 2021/11/19 16:49
 * @Description
 * @Since version-1.0
 */
public class Demo {
    //棋盘边长
    private static int n = 8;
    //临时记录一种方案，确保不冲突
    private static int[] x = new int[n+1];
    //方案数
    private static int scheme = 0;
    private static void backtrack (int t) {
        //搜索第 t 层子树，每层子树表示在第t列的皇后
        if (t > n){
            //到达叶子结点，方案增加1。
            scheme++;
            return;
        }
        for (int i = 1; i <= n; i++) {
            //确定在第t列的皇后的行号i
            x[t] = i;
            //判断当前皇后在i行是否会与前面的皇后冲突
            if (feasibility(t)) {
                //不冲突则递归搜索下一列的皇后
                backtrack(t + 1);
            }
            //回溯过程省略了，因为下一层自动刷新x[t]的值。
        }
    }
    private static boolean feasibility(int j) {
        //当前皇后与之前选的皇后不能处于同一正、反对角线。
        for (int k = 1; k < j; k++) {
            if ((Math.abs(j - k) == Math.abs(x[k] - x[j])) || (x[k] == x[j])) {
                return false;
            }
        }
        return true;
    }
    public static void main(String[] args) {
        backtrack(1);
        System.out.println("方案数为："+scheme);
    }
}
