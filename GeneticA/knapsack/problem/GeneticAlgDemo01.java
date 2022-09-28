package knapsack.problem;

import java.util.Arrays;

/**遗传算法主线程
 * @author 矜君
 * @date 2020/9/29 11:09.
 */
public class GeneticAlgDemo01 {
    public static void main(String[] args) {
        //物品数量
        int goodNum = 10;
        //背包容量(隐约束)
        int capacity = 20;
        //各个物品的重量
        int[] weight = {3,1,4,5,3,6,7,8,9,7};
        //各个物品的价值
        int[] value = {10,6,3,11,12,20,15,8,19,21};
        //种群个体数
        int populationNum = 50;
        //繁衍代数
        int generationNum = 50;
        //交叉率
        double crossoverRate = 0.6;
        //变异率
        double mutationRate = 0.01;

        //创建遗传算法的资源对象
        GeneticAlgResources01 ga01 = new GeneticAlgResources01(goodNum,capacity,weight,value,populationNum,generationNum,crossoverRate,mutationRate);
        //计算开始时间
        long s = System.currentTimeMillis();
        //初始化种群数据
        GeneticAlgInitialization01.initialization(ga01);
        //计算初代种群适应度
        GeneticAlgAdaptiveness01.countAdaptiveness(ga01);
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
