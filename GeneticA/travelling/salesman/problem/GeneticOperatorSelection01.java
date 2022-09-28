package travelling.salesman.problem;

/**选择父母染色体
 * @author 矜君
 * @date 2020/9/29 10:09.
 */
public class GeneticOperatorSelection01 {
    /**从当前种群中选择较优的染色作为下一代的父母
     * @param ga01 资源对象
     */
    public static void rouletteWheelSelect(GeneticAlgResources01 ga01){
        int n = ga01.populationNum;
        double[] survivalRate = new double[n];
        //计算适应度区间,路程越远，适应度越差，但是路程必须记录，所以在求生存率时再取倒数
        for (int i = 0; i < n; i++) {
            double temp= ga01.adaptiveness[i]/ga01.adaptivenessSum;
            survivalRate[i]=i==0?temp:temp+survivalRate[i-1];
        }
        //转动次数=种群个体数
        for (int i = 0; i < n; i++) {
            //用随机数代表轮盘上的指针
            double temp = Math.random();
            //寻找被指针选中的个体编号
            for (int j = 0; j < n; j++) {
                if (temp<survivalRate[j]){
                    //被选中个体拥有繁衍后代的权力，放入父母数组，
                    ga01.parent[i]=j;
                    break;
                }
            }
        }
    }
}
