package travelling.salesman.problem;

import java.util.Arrays;

/**遗传算法主线程
 * @author 矜君
 * @date 2020/9/29 21:20.
 */
public class GeneticAlgDemo01 {
    public static void main(String[] args) {
        int n = 4;
        int starCity = 1;
        double[][] map = new double[n+1][n+1];
        //地图数据初始化
        map[1][2] = 30;map[1][3] = 6; map[1][4] = 4;
        map[2][1] = 30;map[2][3] = 5; map[2][4] = 10;
        map[3][1] = 6; map[3][2] = 5; map[3][4] = 20;
        map[4][1] = 4; map[4][2] = 10;map[4][3] = 20;
        //种群个体数
        int populationNum = 50;
        //繁衍代数
        int generationNum = 50;
        //交叉率
        double crossoverRate = 0.6;
        //变异率
        double mutationRate = 0.01;

        //创建遗传算法的资源对象
        GeneticAlgResources01 ga01 = new GeneticAlgResources01(n,starCity,map,populationNum,generationNum,crossoverRate,mutationRate);
        //计算开始时间
        long s = System.currentTimeMillis();
        //初始化种群数据
        GeneticAlgInitialization01.initialization(ga01);
        //计算初代种群适应度
        travelling.salesman.problem.GeneticAlgAdaptiveness01.countAdaptiveness(ga01);
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
        System.out.println("最优解为：" + Arrays.toString(ga01.optimalSolution));
        System.out.println("计算时间为："+(e-s)+"毫秒。");
    }
}
