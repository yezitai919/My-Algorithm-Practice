public class Demo01{
public static void main(String[] args) {
    //数塔
    int[][] data = {
            {15, 0, 0, 0, 0},
            {13, 6, 0, 0, 0},
            { 7, 8, 9, 0, 0},
            {14, 5,11, 7, 0},
            {15,12, 4, 9,10}
    };
    pyramidOfNumber(data);
}
/**计算数塔最大值路径
 * @param data 数塔数据
 * T(n)=O(n^2),S(n)=O(n^2)
 */
private static void pyramidOfNumber(int[][]data){
    //数塔层数
    int n = data.length;
    //记录每一步的状态
    int[][] dp = new int[n][n];
    //先输入最后一层的数据
    System.arraycopy(data[n - 1], 0, dp[n - 1], 0, n);
    //自底向上遍历数塔，由下一层的数据计算上一层的数据
    for (int i = n-2; i >=0; i--) {
        for (int j = 0; j <= i; j++) {
            //计算每一步的状态
            dp[i][j] = data[i][j]+Math.max(dp[i+1][j],dp[i+1][j+1]);
        }
    }
    //记录每一层选择的结点
    int[] route = new int[n];
    //记录第一个结点值
    route[0]=data[0][0];
    //j为当前结点在本层的位置
    int j = 1;
    for (int i = 0; i < n; i++) {
        //如果右结点大就往右一格，否则和原来一样。
        if (dp[i][j] < dp[i][j + 1]) {
            j ++;
        }
        route[i] = data[i][j];
    }
    System.out.println("最优值为："+dp[0][0]);
    System.out.println("最优解为："+Arrays.toString(route));
}
}
