package knapsack.problem;

/**染色体交叉
 * @author 矜君
 * @date 2020/9/29 10:48.
 */
public class GeneticOperatorCrossover01 {
    /**父母染色体交叉或复制繁衍子代
     * @param ga01 资源对象
     */
    public static void crossover(GeneticAlgResources01 ga01){
        //遍历父母染色体
        for (int i = 0; i < ga01.populationNum; i++) {
            //如果随机数小于交叉率就对i和i+1这对染色体进行交叉，产生的子代复制到临时记录子代染色体的数组。
            if (i+1<ga01.populationNum && Math.random()<ga01.crossoverRate){
                //单点交叉，随机生成交叉点，交叉点分割的后半段基因交换
                int temp = (int)(Math.random()*ga01.gene);
                System.arraycopy(ga01.chromosome[ga01.parent[i]],temp+1,ga01.childChromosome[i],temp+1,ga01.gene-temp-1);
                System.arraycopy(ga01.chromosome[ga01.parent[i+1]],0,ga01.childChromosome[i],0,temp);
                System.arraycopy(ga01.chromosome[ga01.parent[i]],0,ga01.childChromosome[i+1],0,temp);
                System.arraycopy(ga01.chromosome[ga01.parent[i+1]],temp+1,ga01.childChromosome[i+1],temp+1,ga01.gene-temp-1);
                i++;
            }else {
                //如果随机数大于交叉率就直接复制产生子代
                ga01.childChromosome[i]=ga01.chromosome[ga01.parent[i]].clone();
            }
        }
    }
}
