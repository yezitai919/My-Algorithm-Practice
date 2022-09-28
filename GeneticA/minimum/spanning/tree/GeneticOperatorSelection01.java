package minimum.spanning.tree;

/**选择父母染色体
 * @author 矜君
 * @date 2020/9/29 10:09.
 */
public class GeneticOperatorSelection01 {
    /**从当前种群中选择较优的染色作为下一代的父母
     * @param ga01 资源对象
     */
    public static void rouletteWheelSelect(GeneticAlgResources01 ga01){
        double[] survivalRate = new double[ga01.populationNum];
        //计算适应度区间,路程越远，适应度越差，但是路程必须记录，所以在求生存率时再取倒数
        for (int i = 0; i < ga01.populationNum; i++) {
            double temp= ga01.adaptiveness[i]/ga01.adaptivenessSum;
            survivalRate[i]=i==0?temp:temp+survivalRate[i-1];
        }
        for (int i = 0; i < ga01.populationNum; i++) {
            double temp = Math.random();
            for (int j = 0; j < ga01.populationNum; j++) {
                if (temp<survivalRate[j]){
                    ga01.parent[i]=j;
                    break;
                }
            }
        }
    }
}
