package travelling.salesman.problem;

/**遗传算法资源类
 * @author 矜君
 * @date 2020/9/29 21:19.
 */
public class GeneticAlgResources01 {
    //求解问题的资源：
    /**
     * 城市数量
     */
    public int n;
    /**
     * 出发城市
     */
    public int startCity;
    /**
     * 城市的邻接矩阵
     */
    public  double[][] map;

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
    public double[] adaptiveness;
    /**
     * 种群适应度总和
     */
    public double adaptivenessSum;
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
    public double optimalValue = Double.MAX_VALUE;
    /**
     * 最优染色体的基因序列(最优解)
     */
    public int[] optimalSolution;

    /** 构造方法，只需要传入与问题相关的变量和遗传算法的四个主要影响变量
     * @param n 城市数量
     * @param startCity 开始城市
     * @param map 地图数据(城市邻接矩阵)
     * @param populationNum 种群数量
     * @param generationNum 遗传代数
     * @param crossoverRate 交叉率
     * @param mutationRate 变异率
     */
    public GeneticAlgResources01(int n, int startCity, double[][] map,
                                 int populationNum, int generationNum, double crossoverRate, double mutationRate) {
        this.n = n;
        this.startCity = startCity;
        this.map = map;
        this.populationNum = populationNum;
        this.generationNum = generationNum;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
    }
}
