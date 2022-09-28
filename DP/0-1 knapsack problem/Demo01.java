public class Demo01{
/**0/1背包问题-动态规划
 * @param n 物品数
 * @param c 背包容量
 * @param w 物品重量
 * @param v 物品价值
 * T(n)=O(nc),S(n)=O(nc)
 */
private static void optimalLoading(int n,int c,int[] w,int[]v){
    //存储每个阶段所有状态值的表
    int[][] cv = new int[n+1][c +1];
    //遍历每个物品
    for (int i = 1; i <= n; i++) {
        //遍历背包剩余容量的每个情况
        for (int j = 1; j <= c; j++) {
            //如果当前物品重量大于剩余容量肯定不装
            if (w[i]>j){
                //当前物品不装的决策造成的状态等于上一阶段同情况下的状态
                cv[i][j]= cv[i-1][j];
            }else {//当前物品能装下就比较装和不装哪个决策的状态值大
                cv[i][j]=Math.max(cv[i-1][j], cv[i-1][j-w[i]]+v[i]);
            }
        }
    }
System.out.println(cw [n][c]);
}/*第一层循环遍历n个物品，第二层循环遍历c的所有值，T(n)=O(nc),需要一个长度为nc的二维数组辅助，S(n)=O(nc)*/
}
