package minimum.spanning.tree;

/**计算适应度
 * @author 矜君
 * @date 2020/9/29 21:18.
 */
public class GeneticAlgAdaptiveness01 {
    /**计算当前种群每条染色体的适应度
     * @param ga01 资源对象
     */
    public static void countAdaptiveness(GeneticAlgResources01 ga01){
        //初始化
        ga01.adaptivenessSum=0;
        //记录当前种群中最优适应度个体
        int bestAdaptiveness = Integer.MAX_VALUE;
        int bestAdaptivenessNo = 0;

        for (int i = 0; i < ga01.populationNum; i++) {
            int dist = 0;
            for (int j = 0; j < ga01.gene; j++) {
                dist+=ga01.edge[j+1][ga01.chromosome[i][j]];
            }

            if (dist<bestAdaptiveness){
                bestAdaptiveness = dist;
                bestAdaptivenessNo = i;
            }

            ga01.adaptiveness[i] = 1.0/(double)dist;
            ga01.adaptivenessSum += ga01.adaptiveness[i];
        }
        //如果当前种群中适应度最高的个体比最优值个体好好，就更新
        if (bestAdaptiveness < ga01.optimalValue){
            ga01.optimalValue = bestAdaptiveness;
            ga01.optimalSolution = ga01.chromosome[bestAdaptivenessNo].clone();
        }
    }
}
