package travelling.salesman.problem;

/**计算适应度
 * @author 矜君
 * @date 2020/9/29 21:18.
 */
public class GeneticAlgAdaptiveness01 {
    /**计算当前种群每条染色体的适应度
     * @param ga01 资源对象
     */
    public static void countAdaptiveness(GeneticAlgResources01 ga01){
        //适应度总和是直接用+=计算的，所以要先初始化。
        ga01.adaptivenessSum=0;
        //临时变量，记录当前种群的最优适应度及其编号
        double bestAdaptiveness = Double.MAX_VALUE;
        int bestAdaptivenessNo = 0;
        //遍历种群中的染色体
        for (int i = 0; i < ga01.populationNum; i++) {
            //记录上一个经过的城市
            int previousCity = ga01.startCity;
            //记录当前路程
            double currDistance = 0;
            //遍历染色体i的基因(经过的城市)
            for (int j = 1; j < ga01.gene; j++) {
                //记录当前经过的城市
                int currCity = ga01.chromosome[i][j];
                //在地图中寻找上一个城市到当前城市的路程并累加
                if (ga01.map[previousCity][currCity]!=0){
                    currDistance+=ga01.map[previousCity][currCity];
                }else {
                    //地图数据(邻接矩阵)的值为0表示此路不通，舍弃当前染色体表示的路线
                    currDistance=Double.MAX_VALUE;
                    break;
                }
                //遍历到最后一个城市时要看看他是否能回到出发城市，能就累加路程，不能就舍弃路线
                if (j==ga01.gene-1 ){
                    if (ga01.map[currCity][ga01.startCity]!=0){
                        currDistance+=ga01.map[currCity][ga01.startCity];
                    }else {
                        currDistance=Double.MAX_VALUE;
                        break;
                    }
                }
                //更新上一个城市
                previousCity = currCity;
            }
            //记录当前种群的最优适应度及其编号
            if (currDistance<bestAdaptiveness){
                bestAdaptiveness = currDistance;
                bestAdaptivenessNo = i;
            }

            //记录染色体的适应度，因为路程越短适应度越高，所以用1除以路程作为适应度的值
            if (currDistance<Double.MAX_VALUE){
                ga01.adaptiveness[i] = (1.0/currDistance);
                ga01.adaptivenessSum += ga01.adaptiveness[i];
            }
        }
        //如果当前种群的最优适应度大于当前之前每一代种群的最优适应度就更新当前的最优值和最优解
        if (bestAdaptiveness < ga01.optimalValue){
            ga01.optimalValue = bestAdaptiveness;
            ga01.optimalSolution = ga01.chromosome[bestAdaptivenessNo].clone();
        }
    }
}
