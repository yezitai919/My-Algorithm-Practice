package travelling.salesman.problem;

import java.util.Arrays;

/**
 * @author 矜君
 * @date 2020/9/24 23:39.
 */
public class GA01 {
    /**
     * 城市数量
     */
    private int n;
    /**
     * 出发城市
     */
    private int startCity;
    /**
     * 城市的邻接矩阵
     */
    private  double[][] map;

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
    private double[] adaptiveness;
    /**
     * 种群适应度总和
     */
    private double adaptivenessSum;
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
    private double optimalValue = Double.MAX_VALUE;
    /**
     * 最优染色体的基因序列
     */
    private int[] optimalSolution;

    public GA01(int n, int startCity, double[][] map, int populationNum, int generationNum, double matingRate, double variationRate) {
        this.n = n;
        this.startCity = startCity;
        this.map = map;
        this.populationNum = populationNum;
        this.generationNum = generationNum;
        this.matingRate = matingRate;
        this.variationRate = variationRate;
    }

    /**
     * 生成初始种群
     */
    private void initialization(){
        if (currGeneration==0){
            gene=n;
            chromosome=new int[populationNum][gene];
            adaptiveness= new double[populationNum];
            survivalRate = new double[populationNum];
            parent = new int[populationNum];
            childChromosome=new int[populationNum][gene];
            for (int i = 0; i < populationNum; i++) {
                /*确保每个城市只出现一遍*/
                boolean[] b = new boolean[gene];
                chromosome[i][0] = startCity;
                b[0] = true;
                int j = gene;
                while (j>0){
                    if (j==startCity){
                        j--;
                        continue;
                    }
                    int r = (int)(Math.random()*(gene-1))+1;
                    if (!b[r]){
                        chromosome[i][r]=j;
                        j--;
                        b[r]=true;
                    }
                }
            }
            countAdaptiveness();
            /*rouletteWheelSelect();
            mating();*/
        }
    }

    /**
     * 计算适应度
     */
    private void countAdaptiveness(){
       /* System.out.print("种群：");
        for (int[] ints : chromosome) {
            System.out.print(Arrays.toString(ints)+" ");
        }
        System.out.println(" ");*/
        adaptivenessSum=0;
        double bestAdaptiveness = Double.MAX_VALUE;
        int bestAdaptivenessNo = 0;

        for (int i = 0; i < populationNum; i++) {
            int previousCity = startCity;
            double currCost = 0;
            for (int j = 1; j < gene; j++) {
                int currCity = chromosome[i][j];

                if (map[previousCity][currCity]!=0){
                    currCost+=map[previousCity][currCity];
                }else {
                    currCost=Double.MAX_VALUE;
                    break;
                }
                if (j==gene-1 ){
                    if (map[currCity][startCity]!=0){
                        currCost+=map[currCity][startCity];
                    }else {
                        currCost=Double.MAX_VALUE;
                        break;
                    }
                }
                previousCity = currCity;
            }

           /* if (currCost==22){
                System.out.println("出问题的代数："+currGeneration);
            }*/

            if (currCost<bestAdaptiveness){
                bestAdaptiveness = currCost;
                bestAdaptivenessNo = i;
            }

            if (currCost<Double.MAX_VALUE){
                adaptiveness[i] = (1.0/currCost);
                adaptivenessSum += adaptiveness[i];
            }
        }
        //averageAdaptiveness = (bestAdaptiveness+worstAdaptiveness)/2;
        if (bestAdaptiveness < optimalValue){
            optimalValue = bestAdaptiveness;
            optimalSolution = chromosome[bestAdaptivenessNo].clone();
        }
        /*System.out.print("适应力：");
        for (int i = 0; i < populationNum; i++) {
            System.out.print(1.0/adaptiveness[i]+" ");
        }*/
    }


    /**
     * 轮盘赌选择
     */
    private void rouletteWheelSelect(){
        //计算适应度区间,路程越远，适应度越差，但是路程必须记录，所以在求生存率时再取倒数
        for (int i = 0; i < populationNum; i++) {
            double temp= adaptiveness[i]/adaptivenessSum;
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
        //测试
       /* System.out.println("种群：");
        double allAdap1 = 0;
        for (int i = 0; i < populationNum; i++) {
            double a1 = 1.0/adaptiveness[i];
            allAdap1+=a1;
            System.out.print((i+1)+"号，适应度:"+a1+"|");
            if ((i+1)%5==0){
                System.out.println(" ");
            }
        }
        System.out.println("总适应度："+allAdap1);
        System.out.println("父母：");
        double allAdap2 = 0;
        for (int i = 0; i < populationNum; i++) {
            double a2 = 1.0/adaptiveness[parent[i]];
            allAdap2 += a2;
            System.out.print((parent[i]+1)+"号，适应度："+a2+"|");
            if ((i+1)%5==0){
                System.out.println(" ");
            }
        }
        System.out.println("总适应度："+allAdap2);
        System.out.println("最优值："+optimalValue);
*/
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
                //双点交叉
                int temp1 = (int)(Math.random()*(gene-1))+1;
                int temp2 = (int)(Math.random()*(gene-1))+1;
                if (temp2 - temp1>0){
                    temp2 -= temp1;
                }else if (temp2-temp1<0){
                    int t1 = temp1;
                    temp1 = temp2;
                    temp2 = -temp2+t1;
                }else {
                    temp2=1;
                }
                //先复制交叉部分到子代
                System.arraycopy(chromosome[parent[i]],temp1,childChromosome[i+1],temp1,temp2);
                System.arraycopy(chromosome[parent[i+1]],temp1,childChromosome[i],temp1,temp2);


                int[] c1 = Arrays.copyOfRange(childChromosome[i+1],temp1,temp1+temp2);
                int[] c2 = Arrays.copyOfRange(childChromosome[i],temp1,temp1+temp2);

                for (int j = 0; j < temp2; j++) {
                    for (int k = 0; k < temp2; k++) {
                        if (c1[j]==c2[k]){
                            c1[j]=0;
                            c2[k]=0;
                        }
                    }
                }

                for (int j = 0; j < gene; j++) {
                        if (j>=temp1&&j<temp1+temp2){
                            continue;
                        }
                        boolean b1 = false;
                        boolean b2 = false;
                        for (int k = temp1; k < temp1+temp2; k++) {
                            if (chromosome[parent[i]][j] == childChromosome[i][k]){
                                for (int l = 0; l < temp2; l++) {
                                    if (c1[l]!=0){
                                        childChromosome[i][j] = c1[l];
                                        c1[l]=0;
                                        break;
                                    }
                                }
                                b1 = true;
                            }
                            if (chromosome[parent[i+1]][j] == childChromosome[i+1][k]){
                                for (int l = 0; l < temp2; l++) {
                                    if (c2[l]!=0){
                                        childChromosome[i+1][j] = c2[l];
                                        c2[l]=0;
                                        break;
                                    }
                                }
                                b2 = true;
                            }
                            if (b1&&b2){
                                break;
                            }
                        }
                        if (!b1){
                            childChromosome[i][j]=chromosome[parent[i]][j];
                        }
                        if (!b2){
                            childChromosome[i+1][j]=chromosome[parent[i+1]][j];
                        }
                    }


              /*  int aa = 0;
                for (int i1 : childChromosome[i]) {
                    if (i1==3){
                        aa++;
                    }
                }
                if (aa<2){
                    aa = 0;
                    for (int i1 : childChromosome[i + 1]) {
                        if (i1==3) aa++;
                    }
                }
                      if (aa>=2){
                    System.out.println("交叉点："+temp1+" "+temp2);
                    System.out.println(Arrays.toString(chromosome[parent[i]]));
                    System.out.println(Arrays.toString(chromosome[parent[i+1]]));
                    System.out.println(Arrays.toString(childChromosome[i]));
                    System.out.println(Arrays.toString(childChromosome[i+1]));
                    System.out.println("----------------------");
                    //variation(i);
                }*/
              /*  variation(i);
                variation(i+1);*/
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
        if (Math.random()<variationRate){
            int temp1 = (int)(Math.random()*(gene-1))+1;
            int temp2 = (int)(Math.random()*(gene-1))+1;
            if (temp2 - temp1>0){
                temp2 -= temp1;
            }else if (temp2-temp1<0){
                int t1 = temp1;
                temp1 = temp2;
                temp2 = -temp2+t1;
            }else {
                temp2=1;
            }
            int[] temp3 = new int[temp2];
            System.arraycopy(childChromosome[i],temp1,temp3,0,temp2);
            for (int j = temp2-1; j >=0 ; j--) {
                childChromosome[i][temp1] = temp3[j];
                temp1++;
            }
            /*int j = temp1;
            for (int k = temp1+temp2-1; k >= temp1; k--) {
                childChromosome[i][j]=temp3[k];
            }*/
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
           // System.out.println(Arrays.toString(adaptiveness));
            System.out.println("最优值为：" + optimalValue);
            System.out.println("最优解为：" + Arrays.toString(optimalSolution));
        }
    }
    public void geneticAlg(){
        while (currGeneration <= generationNum){
            initialization();
            rouletteWheelSelect();
            mating();
            update();
            optimal();
        }
    }

    public static void main(String[] args) {
        int n = 4;
        int starCity = 1;
        double[][] map = new double[n+1][n+1];
        //地图数据初始化
        map[1][2] = 30;map[1][3] = 6; map[1][4] = 4;
        map[2][1] = 30;map[2][3] = 5; map[2][4] = 10;
        map[3][1] = 6; map[3][2] = 5; map[3][4] = 20;
        map[4][1] = 4; map[4][2] = 10;map[4][3] = 20;
        for (int i = 0; i < 10; i++) {
            GA01 ga01 = new GA01(n,starCity,map,50,50,0.6,0.01);
            long s = System.currentTimeMillis();
            ga01.geneticAlg();
            long e = System.currentTimeMillis();
            System.out.println("计算时间："+(e-s)+"毫秒");
            System.out.println("---------------------");
        }
    }
}
