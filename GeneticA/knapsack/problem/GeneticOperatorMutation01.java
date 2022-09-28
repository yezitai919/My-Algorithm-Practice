package knapsack.problem;

/**染色体变异
 * @author 矜君
 * @date 2020/9/29 10:53.
 */
public class GeneticOperatorMutation01 {
    /**子代染色体变异
     * @param ga01 资源类
     */
    public static void mutation(GeneticAlgResources01 ga01){
        //遍历子代每个染色体的每个基因
        for (int i = 0; i < ga01.populationNum; i++) {
            for (int j = 0; j < ga01.gene; j++) {
                //如果随机数小于变异率就把当前基因进行01转换
                if (Math.random()<ga01.mutationRate){
                    ga01.childChromosome[i][j]=1-ga01.childChromosome[i][j];
                }
            }
        }
        //变异完后子代染色体就完成了，子代染色体替换父代，准备下一次迭代
        for (int i = 0; i < ga01.populationNum; i++) {
            ga01.chromosome[i]=ga01.childChromosome[i].clone();
        }
    }
}
