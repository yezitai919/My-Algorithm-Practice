如下图是一个数塔，从顶部出发在每一个节点可以选择向左或者向右走，一直走到底层，要求找出一条路径，使得路径上的数字之和最大。
![image](https://user-images.githubusercontent.com/55118194/192787543-1110b9a3-0d94-4ea9-9c2c-222880934426.png)
如果自顶向下算要用贪心算法，显然每步的最优决策不一定能得出全局的最优决策。如果用动态规划算法自底向上计算。先对倒数第一层的每个结点分别算出两个选择的值再对比得出最优值并记录以便上一层的结点调用，然后层数减一再重复这个过程就可以一步步递推得出全局最优值。
先把数塔存储到一个数组int[][] data = new int[5][5]:
![image](https://user-images.githubusercontent.com/55118194/192787599-c402318d-9a7c-421f-a27e-55b8b4ff1b92.png)
再设一个数组记录每一步决策的状态(最优值)int[][] dp = new int[5][5];
先把data最后一层的数据复制到dp，例如第四层第一个结点的决策为：
dp[3][0] = data[3][0]+ max(dp[4][0],dp[4][1])
故由此推出状态转移方程为:
dp[i][j] = data[i][j] + max(dp[i+1][j],dp[i+1][j+1]);
![image](https://user-images.githubusercontent.com/55118194/192787652-f1810621-9f73-42cd-948e-42dfef3f52cd.png)
算出最优值再算最优解，另设一数组记录最优解：
int[] route = new int[5];
先记录第一个值
route[0]=data[0][0];
设一变量记录当前结点在当前层的位置
int j = 1;
循环遍历i
if(dp[i][j] < dp[i][j+1])j++; route[i]=j;
记录每一步的选择的节点
route[i]=data[i][j]

