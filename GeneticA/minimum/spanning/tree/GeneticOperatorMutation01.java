package minimum.spanning.tree;

/**染色体变异
 * @author 矜君
 * @date 2020/9/29 10:53.
 */
public class GeneticOperatorMutation01 {
    /**子代染色体变异
     * @param ga01 资源类
     */
    public static void mutation(GeneticAlgResources01 ga01){
        for (int i = 0; i < ga01.populationNum; i++) {
            for (int j = 0; j < ga01.gene; j++) {
                if (Math.random() < ga01.mutationRate && ga01.childChromosome[i][j] != ga01.vertex) {
                    int temp;
                    do {
                        temp = (int) (Math.random() * ga01.populationNum);
                    } while (temp == i);
                    int k = ga01.chromosome[temp][j];
                    if (k != ga01.vertex && ga01.childChromosome[i][k - 1] != j + 1) {
                        ga01.childChromosome[i][j] = k;
                    }
                    if (k == ga01.vertex) {
                        ga01.childChromosome[i][j] = k;
                    }
                }
            }
        }

        //变异完后子代染色体就完成了，子代染色体替换父代，准备下一次迭代
        for (int i = 0; i < ga01.populationNum; i++) {
            ga01.chromosome[i]=ga01.childChromosome[i].clone();
        }
        ga01.maxToV = ga01.childMaxToV.clone();
    }
}
