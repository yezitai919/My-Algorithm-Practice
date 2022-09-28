package knapsack.problem;

/**种群初始化
 * @author 矜君
 * @date 2020/9/29 7:51.
 */
public class GeneticAlgInitialization01 {
    /** 初始化资源类数据，生成初代种群。
     * @param ga01 资源类对象
     */
    public static void initialization(GeneticAlgResources01 ga01){
        //初始化资源类数据
        ga01.gene=ga01.goodNum;
        ga01.chromosome=new int[ga01.populationNum][ga01.gene];
        ga01.adaptiveness= new int[ga01.populationNum];
        ga01.parent = new int[ga01.populationNum];
        ga01.childChromosome=new int[ga01.populationNum][ga01.gene];
        ga01.optimalSolution = new int[ga01.gene];

        //生成初代种群，直接遍历每条染色体的每个基因位置(数组元素)，随机生成0和1基因。
        for (int i = 0; i < ga01.populationNum; i++) {
            for (int j = 0; j < ga01.gene; j++) {
                double r = Math.random();
                ga01.chromosome[i][j]=Math.random()>r?1:0;
            }
        }
    }
}
