package travelling.salesman.problem;

/**种群初始化
 * @author 矜君
 * @date 2020/9/29 21:18.
 */
public class GeneticAlgInitialization01 {
    /** 初始化资源类数据，生成初代种群。
     * @param ga01 资源类对象
     */
    public static void initialization(GeneticAlgResources01 ga01){
        //初始化资源类数据
        ga01.gene=ga01.n;
        ga01.chromosome=new int[ga01.populationNum][ga01.gene];
        ga01.adaptiveness= new double[ga01.populationNum];
        ga01.parent = new int[ga01.populationNum];
        ga01.childChromosome=new int[ga01.populationNum][ga01.gene];

        //生成初代种群
        //遍历种群所有染色体
        for (int i = 0; i < ga01.populationNum; i++) {
            /*染色体的编码方式是把旅行路线经过的城市编号按先后顺序排成
            一列放入整型数组，数组下标i的元素是第i个经过的城市编号*/
            //每条染色体的第一个基因是出发城市的编号
            ga01.chromosome[i][0] = ga01.startCity;
            //遍历每个城市编号
            int j = ga01.gene;
            while (j>0){
                //遍历到出发城市就跳过
                if (j==ga01.startCity){
                    j--;
                    continue;
                }
                //随机一个旅行路线上的位置r，如果该位置还没安排，就安排当前城市j
                int r = (int)(Math.random()*(ga01.gene-1))+1;
                if (ga01.chromosome[i][r]==0){
                    ga01.chromosome[i][r]=j;
                    j--;
                }
            }
        }
    }
}
