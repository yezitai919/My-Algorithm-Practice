package trade.association;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author 矜君
 * @date 2020/9/13 16:32.
 */
public class GA02 {
    //物品数量
    private int goodNum;
    //背包容量(隐约束)
    private int capacity;
    //各个物品的重量
    private int[] weight;
    //各个物品的价值
    private int[] value;

    public GA02(int goodNum, int capacity, int[] weight, int[] value, int populationNum, int generationNum, double matingRate, double variationRate) {
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
    private int[][] adaptiveness;
    /**
     * 种群适应度总和
     */
    private int adaptivenessSum;

    /*一般种群规模和代数取50~200左右。*/

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
            adaptiveness= new int[populationNum][2];
            survivalRate = new double[populationNum];
            parent = new int[populationNum];
            childChromosome=new int[populationNum][gene];
            for (int i = 0; i < populationNum; i++) {
                for (int j = 0; j < gene; j++) {
                    chromosome[i][j]=Math.random()>0.5?1:0;
                }
            }
            countAdaptiveness();
        }
    }

    /**
     * 计算适应度
     */
    private void countAdaptiveness(){

        System.out.print("种群：");
        for (int[] ints : chromosome) {
            System.out.print(Arrays.toString(ints)+" ");
        }
        System.out.println(" ");

        adaptivenessSum=0;
        for (int i = 0; i < populationNum; i++) {
            int temp1 = 0;
            int temp2 = 0;
            for (int j = 0; j < gene; j++) {
                temp1+=chromosome[i][j]*weight[j];
                if (temp1<=capacity){
                    temp2+=chromosome[i][j]*value[j];
                }else {
                    temp2=0;
                    break;
                }
            }
            adaptiveness[i][0]=i;
            adaptiveness[i][1]=temp2;
            adaptivenessSum+=temp2;
        }
        Arrays.sort(adaptiveness,((o1, o2) -> o2[1]-o1[1]));
  /*      System.out.print("适应力：");
        System.out.println(Arrays.toString(adaptiveness));*/
    }


    /**
     * 轮盘赌选择
     */
    private void rouletteWheelSelect(){
        for (int i = 0; i < populationNum; i++) {
            double temp=(double) adaptiveness[i][1]/adaptivenessSum;
            survivalRate[i]=i==0?temp:temp+survivalRate[i-1];
        }
      /*  System.out.print("生存率：");
        System.out.println(Arrays.toString(survivalRate));
        System.out.print("指针：[");*/

        for (int i = 0; i < populationNum; i++) {
            double temp = Math.random();
            // System.out.print(temp+",");
            for (int j = 0; j < populationNum; j++) {
                if (temp<survivalRate[j]){
                    //  System.out.print(j+"😂");
                    parent[i]=j;
                    break;
                }
            }
        }

/*        System.out.println("]");
        System.out.print("父母：");
        System.out.println(Arrays.toString(parent));*/
/*        if (a==5){
            parent[populationNum]=1;
        }*/
    }

    /**
     * 生存者交换或复制染色体产生子代。
     */
    private void mating(){
        for (int i = 0; i < populationNum * 0.1; i++) {
            childChromosome[i]=chromosome[adaptiveness[i][0]].clone();
        }
        for (int i = (int)(populationNum * 0.1); i < populationNum; i+=2) {
            if (i+1<populationNum){
                if (Math.random()<matingRate){
                    int temp = (int)(Math.random()*gene);
                    System.arraycopy(chromosome[parent[i]],temp+1,childChromosome[i],temp+1,gene-temp-1);
                    System.arraycopy(chromosome[parent[i+1]],0,childChromosome[i],0,temp);
                    System.arraycopy(chromosome[parent[i]],0,childChromosome[i+1],0,temp);
                    System.arraycopy(chromosome[parent[i+1]],temp+1,childChromosome[i+1],temp+1,gene-temp-1);
                }else {
                    childChromosome[i]=chromosome[parent[i]].clone();
                    childChromosome[i+1]=chromosome[parent[i+1]].clone();
                }
            }else {
                childChromosome[i]=chromosome[parent[i]].clone();
            }
        }
    }

    /**
     * 基因变异
     */
    private void variation(){
        for (int i = 0; i < populationNum; i++) {
            for (int j = 0; j < gene; j++) {
                if (Math.random()<variationRate){
                    if (childChromosome[i][j]==0){
                        childChromosome[i][j]=1;
                    }else {
                        childChromosome[i][j]=0;
                    }
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
        if (currGeneration>generationNum){
            optimalSolution = new int[gene];
            for (int i = 0; i < populationNum; i++) {
                int temp = (int) (Math.random() * gene);

                if (adaptiveness[temp][1]==adaptiveness[temp+1][1]&&
                        adaptiveness[temp][1]==adaptiveness[temp+2][1]&&
                        adaptiveness[temp][1]==adaptiveness[temp+3][1]){
                    optimalValue = adaptiveness[temp][1];
                    optimalSolution = chromosome[temp].clone();
                    break;
                }
            }
            System.out.println("最优值为："+optimalValue);
            System.out.println("最优解为："+ Arrays.toString(optimalSolution));
        }
    }
    public void geneticAlg(){
        while (currGeneration<= generationNum){
            initialization();
            rouletteWheelSelect();
            mating();
            variation();
            update();
            optimal();
        }
    }
    public static void main(String[] args) {
        //物品数量
        int goodNum = 5;
        //背包容量(隐约束)
        int capacity = 10;
        //各个物品的重量
        int[] weight = {3,1,4,5,3};
        //各个物品的价值
        int[] value = {10,6,3,11,12};
        GA02 ga02 = new GA02(goodNum,capacity,weight,value,500,1000,0.5,0.01);
        ga02.geneticAlg();
    }

}
