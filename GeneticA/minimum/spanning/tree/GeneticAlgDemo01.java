package minimum.spanning.tree;

/**遗传算法主线程
 * @author 矜君
 * @date 2020/9/29 23:42.
 */
public class GeneticAlgDemo01 {
    public static void main(String[] args) {
        //点数
        int vertex = 6;
        //无向图G的边集合E
        double[][] edge = new double[vertex+1][vertex+1];
        //输入图数据
        edge[1][2]=10; edge[2][1]=10; edge[1][3]=21; edge[3][1]=21;
        edge[1][5]=8;  edge[5][1]=8;  edge[2][3]=18; edge[3][2]=18;
        edge[2][4]=5;  edge[4][2]=5;  edge[2][6]=6;  edge[6][2]=6;
        edge[3][5]=25; edge[5][3]=25; edge[3][6]=19; edge[6][3]=19;
        edge[4][6]=7;  edge[6][4]=7;  edge[5][6]=33; edge[6][5]=33;
        //种群个体数
        int populationNum = 50;
        //繁衍代数
        int generationNum = 50;
        //交叉率
        double crossoverRate = 0.6;
        //变异率
        double mutationRate = 0.01;

        //创建遗传算法的资源对象
       GeneticAlgResources01 ga01 = new GeneticAlgResources01(vertex,edge,populationNum,generationNum,crossoverRate,mutationRate);
        //计算开始时间
        long s = System.currentTimeMillis();
        //初始化种群数据
        GeneticAlgInitialization01.initialization(ga01);
        //计算初代种群适应度
        minimum.spanning.tree.GeneticAlgAdaptiveness01.countAdaptiveness(ga01);
        //繁衍迭代
        for (int i = 0; i < generationNum; i++) {
            //从i代中选择第i+1代的父母染色体
            GeneticOperatorSelection01.rouletteWheelSelect(ga01);
            //父母染色体交叉或复制繁衍子代
            GeneticOperatorCrossover01.crossover(ga01);
            //子代染色体变异并替换父代染色体
            GeneticOperatorMutation01.mutation(ga01);
            //计算i+1代的适应度
            GeneticAlgAdaptiveness01.countAdaptiveness(ga01);
        }
        //计算结束时间
        long e = System.currentTimeMillis();
        System.out.println("最优值为：" + ga01.optimalValue);
        System.out.print("最优解为：" );
        for (int i = 0; i < ga01.gene; i++) {
            System.out.print("["+(i+1)+"--"+ga01.optimalSolution[i]+"] ");
            if ((i+1)%5==0){
                System.out.println(" ");
            }
        }
        System.out.println(" ");
        System.out.println("计算时间为："+(e-s)+"毫秒。");
    }
}
