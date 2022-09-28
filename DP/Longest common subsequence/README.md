给定两个序列 Xm = {x1, x2, …, xm}和 Yn = {y1, y2, …, yn}，利用动态规划算法求解 Xm和 Yn 的最长公共子序列。
当xm=yn时，Xm和Yn的LCS=Xm-1和Yn-1的LCS在尾部加上xm(yn)。当xm≠yn时，Xm和Yn的LCS=Xm-1和Yn的LCS与Xm和Yn-1的LCS中的较长者。LCS具有最优子结构性质且子问题重复。先用动态规划算出LCS的长度，即最优值，并记录获得最优值的子序列，再根据最优值和对应的子序列递归地构造最优解。
设一数组c[m][n]记录子问题的最优值，c[i][j]表示Xi和Yj的LCS长度，空序列的LCS也是空序列，所以c[0][0]=0。根据上面的分析可以得出最优值的动态规划函数：
![image](https://user-images.githubusercontent.com/55118194/192785862-b4fc7111-6491-44b3-8b7a-47d56e414318.png)
