package knapsack.problem;

/**计算适应度
 * @author 矜君
 * @date 2020/9/29 8:26.
 */
public class GeneticAlgAdaptiveness01 {
    /**计算当前种群每条染色体的适应度
     * @param ga01 资源对象
     */
    public static void countAdaptiveness(GeneticAlgResources01 ga01){
        //适应度总和是直接用+=计算的，所以要先初始化。
        ga01.adaptivenessSum=0;
        //临时变量，记录当前种群的最优适应度及其编号
        int bestAdaptiveness = 0;
        int bestAdaptivenessNo = 0;
        //遍历种群个体
        for (int i = 0; i < ga01.populationNum; i++) {
            //记录当前染色体的物品重量和价值
            int currW = 0;
            int currV = 0;
            //遍历基因，计算当前染色体的物品重量和价值
            for (int j = 0; j < ga01.gene; j++) {
                currW+=ga01.chromosome[i][j]*ga01.weight[j];
                if (currW<=ga01.capacity){
                    currV+=ga01.chromosome[i][j]*ga01.value[j];
                }else {
                    //如果装入物品的重量超出背包重量，则当前染色体适应度为0
                    currV=0;
                    break;
                }
            }
            //记录染色体i的适应度
            ga01.adaptiveness[i]=currV;
            ga01.adaptivenessSum+=currV;
            //记录当前种群的最优适应度及其编号
            if (currV>bestAdaptiveness){
                bestAdaptiveness = currV;
                bestAdaptivenessNo = i;
            }
        }

        //如果当前种群的最优适应度大于当前之前每一代种群的最优适应度就更新当前的最优值和最优解
        if (bestAdaptiveness > ga01.optimalValue){
            ga01.optimalValue = bestAdaptiveness;
            ga01.optimalSolution = ga01.chromosome[bestAdaptivenessNo].clone();
        }
    }
}
