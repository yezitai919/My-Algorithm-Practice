package knapsack.problem;


import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author 矜君
 * @date 2020/9/14 16:50.
 */
public class GA01 {
    //物品数量
    private int goodNum;
    //背包容量(隐约束)
    private int capacity;
    //各个物品的重量
    private int[] weight;
    //各个物品的价值
    private int[] value;

    public GA01(int goodNum, int capacity, int[] weight, int[] value, int populationNum, int generationNum, double matingRate, double variationRate) {
        this.goodNum = goodNum;
        this.capacity = capacity;
        this.weight = weight;
        this.value = value;
        this.populationNum = populationNum;
        this.generationNum = generationNum;
        this.matingRate = matingRate;
        this.variationRate = variationRate;
    }

    /**
     * 基因数
     */
    private int gene;
    /**
     * 初代种群数量
     */
    private int populationNum;
    /**
     * 初代种群的染色体的基因序列
     */
    private int[][] chromosome;
    /**
     * 初代种群适应度
     */
    private int[] adaptiveness;
    /**
     * 种群适应度总和
     */
    private int adaptivenessSum;
    /**
     * 适应度的平均数
     */
    //public double averageAdaptiveness;
    /**
     * 总的演化代数
     */
    private int generationNum;
    /**
     * 当前演化代数
     */
    private int currGeneration = 0;
    /**
     * 生存率
     */
    private double[] survivalRate;
    /**
     * 生存者编号(有重复，足够优秀的个体可能被选中多次，这意味着它可以有多个孩子)
     */
    private int[] parent;
    /**
     * 交叉概率
     */
    private double matingRate;
    /**
     * 变异概率
     */
    private double variationRate;
    /**
     * 初代种群的染色体的基因序列
     */
    private int[][] childChromosome;
    /**
     * 最优染色体的适应度
     */
    private int optimalValue = 0;
    /**
     * 最优染色体的基因序列
     */
    private int[] optimalSolution;


    /**
     * 生成初始种群
     */
    private void initialization(){
        if (currGeneration==0){
            gene=goodNum;
            chromosome=new int[populationNum][gene];
            adaptiveness= new int[populationNum];
            survivalRate = new double[populationNum];
            parent = new int[populationNum];
            childChromosome=new int[populationNum][gene];
            optimalSolution = new int[gene];
            for (int i = 0; i < populationNum; i++) {
                for (int j = 0; j < gene; j++) {
                    double r = Math.random();
                    chromosome[i][j]=Math.random()>r?1:0;
                }
            }
            countAdaptiveness();
        }
    }

    /**
     * 计算适应度
     */
    private void countAdaptiveness(){
        /*System.out.print("种群：");
        for (int[] ints : chromosome) {
            System.out.print(Arrays.toString(ints)+" ");
        }
        System.out.println(" ");*/

        adaptivenessSum=0;
        int bestAdaptiveness = 0;
        int bestAdaptivenessNo = 0;
        //int worstAdaptiveness = Integer.MAX_VALUE;
        for (int i = 0; i < populationNum; i++) {
            int currW = 0;
            int currV = 0;
            for (int j = 0; j < gene; j++) {
                currW+=chromosome[i][j]*weight[j];
                if (currW<=capacity){
                    currV+=chromosome[i][j]*value[j];
                }else {
                    currV=0;
                    break;
                }
            }
            adaptiveness[i]=currV;
            adaptivenessSum+=currV;
            if (currV>bestAdaptiveness){
                bestAdaptiveness = currV;
                bestAdaptivenessNo = i;
            }
            /*if (currV<worstAdaptiveness){
                worstAdaptiveness = currV;
            }*/
        }
       // averageAdaptiveness = (double) adaptivenessSum/populationNum;
        if (bestAdaptiveness > optimalValue){
            optimalValue = bestAdaptiveness;
            optimalSolution = chromosome[bestAdaptivenessNo].clone();
        }
  /*      System.out.print("适应力：");
        System.out.println(Arrays.toString(adaptiveness));*/
    }


    /**
     * 轮盘赌选择
     */
    private void rouletteWheelSelect(){
        //计算适应度区间
        for (int i = 0; i < populationNum; i++) {
            double temp=(double) adaptiveness[i]/adaptivenessSum;
            survivalRate[i]=i==0?temp:temp+survivalRate[i-1];
        }
       /* System.out.print("生存率：");
        System.out.println(Arrays.toString(survivalRate));
        System.out.print("指针：[");*/

/*        int number = 0;
        while (number<populationNum){
            double temp = Math.random();
            for (int j = 0; j < populationNum; j++) {
                if (temp<survivalRate[j] && adaptiveness[j]>=averageAdaptiveness){
                    parent[number]=j;
                    number++;
                    break;
                }
            }
        }*/
        //转动次数=种群个体数
        for (int i = 0; i < populationNum; i++) {
            double temp = Math.random();
           //  System.out.print(temp+",");
            for (int j = 0; j < populationNum; j++) {
                if (temp<survivalRate[j]){
               //       System.out.print(j+"😂");
                    parent[i]=j;
                    break;
                }
            }
        }
/*
        System.out.println("]");
        System.out.print("父母：");
        System.out.println(Arrays.toString(parent));*/

    }

    /**
     * 生存者交换或复制染色体产生子代。
     */
    private void mating(){
        for (int i = 0; i < populationNum; i++) {
            if (i+1<populationNum && Math.random()<matingRate){
                    int temp = (int)(Math.random()*gene);
                    System.arraycopy(chromosome[parent[i]],temp+1,childChromosome[i],temp+1,gene-temp-1);
                    System.arraycopy(chromosome[parent[i+1]],0,childChromosome[i],0,temp);
                    System.arraycopy(chromosome[parent[i]],0,childChromosome[i+1],0,temp);
                    System.arraycopy(chromosome[parent[i+1]],temp+1,childChromosome[i+1],temp+1,gene-temp-1);
                    variation(i);
                    i++;
            }else {
                childChromosome[i]=chromosome[parent[i]].clone();
            }
        }
    }

    /**
     * 基因变异
     */
    private void variation(int i){
            for (int j = 0; j < gene; j++) {
                if (Math.random()<variationRate){
                    if (childChromosome[i][j]==0){
                        childChromosome[i][j]=1;
                    }else {
                        childChromosome[i][j]=0;
                    }
                    if (childChromosome[i+1][j]==0){
                        childChromosome[i+1][j]=1;
                    }else {
                        childChromosome[i+1][j]=0;
                    }
                }
            }
    }
    private void update(){
        for (int i = 0; i < populationNum; i++) {
            chromosome[i]=childChromosome[i].clone();
        }
        countAdaptiveness();
        currGeneration++;
    }
    private void optimal(){
        if (currGeneration>generationNum) {
            //System.out.println(Arrays.toString(adaptiveness));
            System.out.println("最优值为：" + optimalValue);
            System.out.println("最优解为：" + Arrays.toString(parent));

        }
    }
    public void geneticAlg(){
        while (currGeneration<= generationNum){
            initialization();
            rouletteWheelSelect();
            mating();
            update();
            optimal();
        }
    }
    public static void main(String[] args) {
        //物品数量
        int goodNum = 10;
        //背包容量(隐约束)
        int capacity = 20;
        //各个物品的重量
        int[] weight = {3,1,4,5,3,6,7,8,9,7};
        //各个物品的价值
        int[] value = {10,6,3,11,12,20,15,8,19,21};
        for (int i = 0; i < 10; i++) {
            GA01 ga01 = new GA01(goodNum,capacity,weight,value,50,100,0.6,0.02);
            long s = System.currentTimeMillis();
            ga01.geneticAlg();
            long e = System.currentTimeMillis();
            System.out.println("计算时间："+(e-s)+"毫秒");
            System.out.println("-------------------------------------------");
        }
    }

}
