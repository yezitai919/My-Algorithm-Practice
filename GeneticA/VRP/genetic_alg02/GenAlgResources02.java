package genetic_alg02;

/**
 * @Author jinjun99
 * @Date Created in 2022/3/19 16:43
 * @Description
 * @Since version-1.0
 */
public class GenAlgResources02 {

    /**
     * 客户数量
     */
    public int k;
    /**
     * 货车数量
     */
    public int m;
    /**
     * 客户需求货量
     */
    public double[] needs;

    /**
     * 客户需求时限
     */
    public double[][] timeLimit;
    /**
     * 仓库和客户坐标，0是仓库
     */
    public double[][] coordinate;

    /**
     * 货车载量
     */
    public double loading;

    /**
     * 货车速度
     */
    public double speed;
    /**
     * 每超一小时惩罚的钱
     */
    public double punish;
    /**
     * 行驶一公里的成本
     */
    public double drivingCost;


//遗传算法的标配资源：
    /**
     * 染色体的基因数
     */
    public int gene;
    /**
     * 种群的个体数
     */
    public int populationNum;
    /**
     * 种群的染色体的基因序列
     */
    public int[][] chromosome;
    /**
     * 种群适应度
     */
    public double[] fitness;
    /**
     * 种群适应度总和
     */
    public double fitnessSum;
    /**
     * 总的演化代数
     */
    public int generationNum;

    /**
     * 生存者编号(有重复，足够优秀的个体可能被选中多次，这意味着它可以有多个孩子)
     */
    public int[] parent;
    /**
     * 交叉概率
     */
    public double crossoverRate;
    /**
     * 变异概率
     */
    public double mutationRate;
    /**
     * 临时记录下一代的染色体
     */
    public int[][] childChromosome;
    /**
     * 最优染色体的适应度(在本题最优适应度=最优值)
     */
    public double optimalValue = 0;
    /**
     * 最优染色体的基因序列(最优解)
     */
    public int[] optimalSolution;


    public GenAlgResources02(int k, int m, double[] needs, double[][] timeLimit,
                             double[][] coordinate, double loading, double speed,
                             double punish, double drivingCost, int populationNum,
                             int generationNum, double crossoverRate, double mutationRate) {
        this.k = k;
        this.m = m;
        this.needs = needs;
        this.timeLimit = timeLimit;
        this.coordinate = coordinate;
        this.loading = loading;
        this.speed = speed;
        this.punish = punish;
        this.drivingCost = drivingCost;
        this.populationNum = populationNum;
        this.generationNum = generationNum;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
    }
}
