public class Demo02{
/**双重0/1背包问题
 * @param n 物品个数
 * @param c 背包容量
 * @param v 背包容积
 * @param weight 物品重量
 * @param volume 物品体积
 * @param value 物品价值
 * T(n)=O(ncv),S(n)=O(ncv)
 */
private static void optimalLoading(int n, int c, int v, int[] weight, int[] volume, int[] value) {
    //创建一个三维数组记录
    int [][][] cv = new int[n+1][c+1][v +1];
    //遍历物品
    for (int i = 1; i <=n ; i++) {
        //背包容量
        for (int j = 1; j <=c ; j++) {
            //背包体积
            for (int k = 1; k <= v; k++) {
                //当前物品i的重量比背包容量j大或体积比背包容积k大
                if (weight[i] > j || volume[i] > k) {
                    cv[i][j][k] = cv[i - 1][j][k];
                } else {//装得下，Max{装物品i， 不装物品i}
                    cv[i][j][k] = Math.max(cv[i - 1][j][k],
                            cv[i - 1][j - weight[i]][k - volume[i]] + value[i]);
                }
            }
        }
    }
    System.out.println(cv[n][c][v]);
}/*第一层循环遍历n个物品，第二层循环遍历c的所有值，第三层循环遍历v的所有值，T(n)=O(ncv),需要一个长度为ncv的三维数组辅助，S(n)=O(ncv)*/
}
