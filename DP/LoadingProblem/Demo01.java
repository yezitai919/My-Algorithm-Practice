public class Demo01{
/**最优装载-动态规划
 * @param n
 * @param c1
 * @param w
 * T(n)=O(nc1),S(n)=O(nc1)
 */
private static void optimalLoading(int n, int c1,int[] w){
    //存储每个阶段所有状态值的表
    int[][] cw = new int[n+1][c1+1];
    //遍历每个集装箱
    for (int i = 1; i <= n; i++) {
        //遍历一船剩余容量的每个情况
        for (int j = 1; j <= c1; j++) {
            //如果当前集装箱重量大于剩余容量肯定不装
            if (w[i]>j){
                //当前集装箱不装的决策造成的状态等于上一阶段同情况下的状态
                cw[i][j]=cw[i-1][j];
            }else {//当前集装箱能装下就比较装和不装哪个决策的状态值大
                cw[i][j]=Math.max(cw[i-1][j],cw[i-1][j-w[i]]+w[i]);
            }
        }
    }
    System.out.println(cw [n][c]);
}/*第一层循环遍历n个集装箱，第二层循环遍历1船容量c1的所有值T(n)=O(nc1),需要一个长度为nc1的二维数组辅助，S(n)=O(nc1)*/
}
