public class Demo01{
/** 计算矩阵的最小相乘次数
 * @param mrc 矩阵数据
 *  T(n)=O(n^3),S(n)=O(n^2)
 */
private static void matrix(int[] mrc){
    //矩阵个数
    int n = mrc.length-2;
    //记录每个阶段的状态值
    int[][] m = new int[n+1][n+1];
    for (int r = 1; r < n; r++) {
        for (int i = 1; i <= n-r; i++) {
            //因为规模必须严格地从小到大，所以要设置变量使for循环沿对角线方向遍历数组
            int j = i+r;
            //先设个最大值，然后遍历k寻找最小值
            m[i][j] = Integer.MAX_VALUE;
            for (int k = i; k < j; k++) {
                m[i][j] = Math.min(m[i][j],m[i][k]+m[k+1][j]+mrc[i]*mrc[k+1]*mrc[j+1]);
            }
        }
    }
    System.out.println(m[1][n]);
}/*3层循环，与n正相关，T(n)=O(n^3),辅助数组长度n^2,S(n)=O(n^2)*/
}
