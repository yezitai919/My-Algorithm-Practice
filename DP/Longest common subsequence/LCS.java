import java.util.ArrayList;
/**
 * @author 矜君
 * @date 2020/6/6 11:07.
 */
public class LCS {
    public static void main(String[] args) {
        //设一个空值防止数组下标越界
        char[] x = {' ','w','e','c','h','a','t'};
        char[] y = {' ','h','u','a','w','e','i'};
        int m = x.length;
        int n = y.length;
        //记录最长公共子序列
        ArrayList<Character> lcs = new ArrayList<>();
        //记录获得最优值的子序列，辅助构造LCS
        int[][] b = new int[m][n];
        lcsLength(x,y,m,n,b);
        findLCS(lcs,b,x,m-1,n-1);
        System.out.println(lcs);
    }
    /**动态规划求两字符数组的最长公共子序列的长度
     * @param x
     * @param y
     * @param m x的长度
     * @param n y的长度
      * @param b 记录每个阶段获得最优值的子序列，长度b[m][n]
     * T(n)=O(mn),S(n)=O(mn)
     */
    private static void lcsLength(char[] x,char[] y,int m,int n,int[][]b){
        //记录子序列的最优值
        int[][] len = new int[m][n];
        //遍历两个序列
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                //如果两(子)序列的尾元素相等，就可以确定一个属于LCS的元素
                if (x[i]==y[j]){
                    len[i][j] = len[i-1][j-1] + 1;
                    b[i][j] = 1;
                    /*如果两(子)序列的尾元素相等，则其中至少有一个尾元素肯定不属于LCS，
                    根据前面已经记录的更小的子序列的LCS长度可以判断哪个尾元素不属于LCS*/
                }else if (len[i-1][j]>=len[i][j-1]){
                    len[i][j] = len[i-1][j];
                    b[i][j] = 2;
                }else {
                    len[i][j] = len[i][j-1];
                    b[i][j] = 3;
                }
            }
        }
        //打印LCS的长度
        System.out.println(len[m-1][n-1]);
    }/*双层循环(m-1)(n-1)次，T(n)=O(mn)。需要辅助空间len+b=2mn，S(n)=O(mn)*/

    /** 根据最优值的情况排除不属于LCS的元素，递归地构造最长公共子序列
     * @param lcs 记录最长公共子序列
     * @param b 记录最优值对应的子序列
     * @param x 其中一个字符数组
     * @param i b[i][j]，初始时i=m，j=n，从最后的元素开始往前搜索
     * @param j
     * T(n)=O(m+n),S(n)=O(mn)
     */
    private static void findLCS(ArrayList<Character> lcs, int[][] b, char[]x, int i , int j){
        if (i==0 || j==0){
            return;
        }
        //b[i][j] == 1说明x[i]=y[j],把x[i]放入LCS
        if (b[i][j] == 1){
            findLCS(lcs,b,x,i-1,j-1);
            lcs.add(x[i]);
            //b[i][j] == 2说明当前x的(子)序列的尾元素x[i]不属于LCS，删掉继续递归
        }else if (b[i][j] == 2){
            findLCS(lcs,b,x,i-1,j);
        }else {//b[i][j] == 3说明当前y的(子)序列的尾元素y[j]不属于LCS，删掉继续递归
            findLCS(lcs,b,x,i,j-1);
        }
    }/*因为i=m,j=n,每次递归-1，最多递归m+n次T(n)=O(m+n)。需要辅助空间b长度mn，lcs长度最多n或m，S(n)=O(mn)。*/
}
