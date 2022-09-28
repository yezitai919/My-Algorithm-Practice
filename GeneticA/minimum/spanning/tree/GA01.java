package minimum.spanning.tree;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author 矜君
 * @date 2020/9/26 12:23.
 */
public class GA01 {
    /**
     *顶点数
     */
    private int vertex;
    /**
     *边集
     */
    private double[][] edge;

    public GA01(int vertex, double[][] edge, int populationNum, int generationNum, double matingRate, double variationRate) {
        this.vertex = vertex;
        this.edge = edge;
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
    private int optimalValue = Integer.MAX_VALUE;
    /**
     * 最优染色体的基因序列
     */
    private int[] optimalSolution;

    /**
     * 记录每个染色体中最大点所连的另一点
     */
    private int[] maxToV;
    /**
     * 记录子代每个染色体中最大点所连的另一点
     */
    private int[] childMaxToV;

    /**
     * 生成初始种群
     */
    private void initialization() {
        if (currGeneration == 0) {
            gene = vertex-1;
            chromosome = new int[populationNum][gene];
            adaptiveness = new double[populationNum];
            survivalRate = new double[populationNum];
            parent = new int[populationNum];
            maxToV = new int[populationNum];
            childMaxToV = new int[populationNum];
            childChromosome = new int[populationNum][gene];
            optimalSolution = new int[gene];

            for (int i = 0; i < populationNum; i++) {
                ArrayList<Integer> a = new ArrayList<>();
                ArrayList<Integer> b = new ArrayList<>();
                for (int j = 1; j < vertex; j++) {
                    b.add(j);
                }
                while (true){
                    int k = (int)(Math.random()*(vertex-1)+1);
                    if (edge[vertex][k] != 0){
                        chromosome[i][k-1] = vertex;
                        maxToV[i] = k-1;
                        a.add(k);
                        b.remove(Integer.valueOf(k));
                        break;
                    }
                }
                int eNum = 0;
                while (eNum<gene-1){
                    for (Integer i1 : a) {
                        boolean bk = false;
                        for (Integer i2 : b) {
                            double r = Math.random();
                            if (Math.random() < r && edge[i1][i2] != 0) {
                                /*int aa = chromosome[i][j];
                                if (aa!=vertex&&chromosome[i][aa-1]==j+1){*/
                                if (chromosome[i][i2-1] == 0 && chromosome[i][i1-1]!= i2) {
                                    chromosome[i][i2-1] = i1;
                                    a.add(i2);
                                    b.remove(i2);
                                    eNum++;
                                    bk = true;
                                    break;
                                }
                            }
                        }
                        if (bk) {
                            break;
                        }
                    }
                }
            }
           /* for (int i = 0; i < populationNum; i++) {
                for (int j = 0; j < gene; j++) {
                    int a = chromosome[i][j];
                    if (a!=vertex && chromosome[i][a-1]==j+1){
                        chromosome[i][j] =  chromosome[i+1][j];
                    }
                }
            }*/
            countAdaptiveness();
            //测试
            /*rouletteWheelSelect();
            mating();*/
        }
    }

    /**
     * 计算适应度
     */
    private void countAdaptiveness(){
      /*  System.out.print("种群：");
        for (int[] ints : chromosome) {
            System.out.print(Arrays.toString(ints)+" ");
        }
        System.out.println(" ");*/
        //初始化
        adaptivenessSum=0;
        //记录当前种群中最优适应度个体
        int bestAdaptiveness = Integer.MAX_VALUE;
        int bestAdaptivenessNo = 0;

        for (int i = 0; i < populationNum; i++) {
            int dist = 0;
          //  boolean bk = false;

            for (int j = 0; j < gene; j++) {
            /*    int aa = chromosome[i][j];
                if (aa!=vertex&&chromosome[i][aa-1]==j+1){
                    *//*x++;
                    System.out.println("重复染色体："+currGeneration+"代"+i+"号");
                    System.out.println(Arrays.toString(chromosome[i]));*//*
                    adaptiveness[i]=0;
                    bk = true;
                    break;
                }*/
                dist+=edge[j+1][chromosome[i][j]];
            }

            if (dist<bestAdaptiveness){
                bestAdaptiveness = dist;
                bestAdaptivenessNo = i;
            }
           // if (!bk){
                adaptiveness[i] = 1.0/(double)dist;
                adaptivenessSum += adaptiveness[i];
           // }

        }
        //如果当前种群中适应度最高的个体比最优值个体好好，就更新
        if (bestAdaptiveness < optimalValue){
            optimalValue = bestAdaptiveness;
            optimalSolution = chromosome[bestAdaptivenessNo].clone();
        }

     /*   for (int i = 0; i < populationNum; i++) {
            System.out.println(Arrays.toString(chromosome[i])+"适应度："+adaptiveness[i]);
        }*/
  /*      System.out.print("适应力：");
        System.out.println(Arrays.toString(adaptiveness));*/
    }


    /**
     * 轮盘赌选择
     */
    private void rouletteWheelSelect(){
        //计算适应度区间
        for (int i = 0; i < populationNum; i++) {
            double temp= adaptiveness[i]/adaptivenessSum;
            survivalRate[i]=i==0?temp:temp+survivalRate[i-1];
        }
       /* System.out.print("生存率：");
        System.out.println(Arrays.toString(survivalRate));
        System.out.print("指针：[");*/

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
    /*System.out.println("]");*/


/*测试
        System.out.println("种群：");
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

    }

    /**
     * 生存者交换或复制染色体产生子代。
     */
    private void mating(){
        for (int i = 0; i < populationNum; i++) {
            //i和i+1凑一对交换，
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
                //System.arraycopy(chromosome[parent[i]],temp1,childChromosome[i+1],temp1,temp2);
                //System.arraycopy(chromosome[parent[i+1]],temp1,childChromosome[i],temp1,temp2);
                for (int j = temp1; j < temp1+temp2; j++) {
                    int a = chromosome[parent[i]][j];
                    int b = chromosome[parent[i+1]][j];
                    if (a!=vertex && chromosome[parent[i+1]][a-1]==j+1){
                        childChromosome[i+1][j] = chromosome[parent[i+1]][j];
                    }else {
                        childChromosome[i+1][j] = chromosome[parent[i]][j];
                    }
                    if (b!=vertex && chromosome[parent[i]][b-1]==j+1){
                        childChromosome[i][j] = chromosome[parent[i]][j];
                    }else {
                        childChromosome[i][j] = chromosome[parent[i+1]][j];
                    }
                }
                //复制交叉前段
                if (temp1>0){
                    System.arraycopy(chromosome[parent[i]],0,childChromosome[i],0,temp1);
                    System.arraycopy(chromosome[parent[i+1]],0,childChromosome[i+1],0,temp1);
                }
                if (temp1+temp2<gene){
                    System.arraycopy(chromosome[parent[i]],temp1+temp2,childChromosome[i],temp1+temp2,gene-(temp1+temp2));
                    System.arraycopy(chromosome[parent[i+1]],temp1+temp2,childChromosome[i+1],temp1+temp2,gene-(temp1+temp2));
                }
                boolean b1 = maxToV[parent[i]]>=temp1 && maxToV[parent[i]]<(temp1+temp2) && (maxToV[parent[i+1]]<temp1 || maxToV[parent[i+1]]>=(temp1+temp2));
                boolean b2 = (maxToV[parent[i]]<temp1 || maxToV[parent[i]]>=(temp1+temp2)) && maxToV[parent[i+1]]>=temp1 && maxToV[parent[i+1]]<(temp1+temp2);
                boolean b3 = maxToV[parent[i]]>=temp1 && maxToV[parent[i]]<(temp1+temp2) && maxToV[parent[i+1]]>=temp1 && maxToV[parent[i+1]]<(temp1+temp2);
                if (b1){
                    int a = childChromosome[i][maxToV[parent[i+1]]];
                    if (Math.random()<(double) 1/vertex && a!=vertex && childChromosome[i+1][a-1]!=maxToV[parent[i+1]]+1){
                        childChromosome[i+1][maxToV[parent[i+1]]] = childChromosome[i][maxToV[parent[i+1]]];
                        childMaxToV[i+1]=maxToV[parent[i]];
                    }else {
                        childMaxToV[i+1]=maxToV[parent[i+1]];
                    }
                    childChromosome[i][maxToV[parent[i+1]]] = vertex;
                    childMaxToV[i]=maxToV[parent[i+1]];
                }else if (b2){
                    int a = childChromosome[i+1][maxToV[parent[i]]];
                    if (Math.random()<(double) 1/vertex && a!=vertex && childChromosome[i][a-1]!=maxToV[parent[i]]+1){
                        childChromosome[i][maxToV[parent[i]]] = childChromosome[i+1][maxToV[parent[i]]];
                        childMaxToV[i]=maxToV[parent[i+1]];
                    }else {
                        childMaxToV[i]=maxToV[parent[i]];
                    }
                    childChromosome[i+1][maxToV[parent[i]]] = vertex;
                    childMaxToV[i+1]=maxToV[parent[i]];
                }else if (b3){
                    childMaxToV[i]=maxToV[parent[i+1]];
                    childMaxToV[i+1]=maxToV[parent[i]];
                }else {
                    childMaxToV[i]=maxToV[parent[i]];
                    childMaxToV[i+1]=maxToV[parent[i+1]];
                }
                boolean print = false;
                for (int j = 0; j < gene; j++) {
                    int a = childChromosome[i][j];
                    int b = childChromosome[i+1][j];
                    if (a!=vertex&&childChromosome[i][a-1]==j+1){
                        print =true;
                    }else if (b!=vertex&&childChromosome[i+1][b-1]==j+1){
                        print = true;
                    }
                }

                if (print){
                    System.out.println("交叉点："+temp1+" "+temp2+"  最大点坐标："+maxToV[parent[i]]+" "+maxToV[parent[i+1]]);
                    System.out.println(Arrays.toString(chromosome[parent[i]]));
                    System.out.println(Arrays.toString(chromosome[parent[i+1]]));
                    if (b1||b2){
                        System.out.println(">>>>>>>>>>>>>>>>>>>>>>");
                    }
                    System.out.println(Arrays.toString(childChromosome[i]));
                    System.out.println(Arrays.toString(childChromosome[i+1]));
                    System.out.println("----------------------");
                    variation(i);
                }
                variation(i);
                i++;
            }else {
                childChromosome[i]=chromosome[parent[i]].clone();
                childMaxToV[i]=maxToV[parent[i]];
            }
        }

    }

    /**
     * 基因变异
     */
    private void variation(int i){
        for (int j = 0; j < gene; j++) {
            if (Math.random()<variationRate && childChromosome[i][j] != vertex){
                int temp;
                do {
                    temp = (int) (Math.random()*populationNum);
                } while (temp == i);
                int k = chromosome[temp][j];
                if (k!=vertex && childChromosome[i][k-1] != j+1){
                    childChromosome[i][j] = k;
                }
                if (k==vertex){
                    childChromosome[i][j] = k;
                }
            }
            if (Math.random()<variationRate && childChromosome[i+1][j] != vertex){
                int temp;
                do {
                    temp = (int) (Math.random()*populationNum);
                } while (temp == i+1);
                int k = chromosome[temp][j];
                if (k!=vertex && childChromosome[i+1][k-1] != j+1){
                    childChromosome[i+1][j] = k;
                }
                if (k==vertex){
                    childChromosome[i+1][j] = k;
                }
            }
        }
     /*   System.out.println("变异后：");
        System.out.println(Arrays.toString(childChromosome[i]));
        System.out.println(Arrays.toString(childChromosome[i+1]));*/

    }
    private void update(){
        for (int i = 0; i < populationNum; i++) {
            chromosome[i]=childChromosome[i].clone();
        }
        maxToV = childMaxToV.clone();
        countAdaptiveness();
        currGeneration++;
    }
    private void optimal(){
        if (currGeneration>generationNum) {
           /* System.out.println("适应度：");
            for (double v : adaptiveness) {
                System.out.print((1.0/v)+" ");
            }
            System.out.println(" ");*/
            System.out.println("最优值为：" + optimalValue);
            System.out.println("最优解为：" + Arrays.toString(optimalSolution));
           // System.out.println("有重复边的个体数："+x);
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
        //点数
        int vertex = 6;
        //无向图G的顶点集合v
        //int[] vertex = new int[n+1];
        //无向图G的边集合E
        double[][] edge = new double[vertex+1][vertex+1];
        //最小生成树MST的边集合，点集不用变。
        //int[][] mstEege = new int[n+1][n+1];
        //inputGraph(vertex,edge,mstEege);//输入图数据
        edge[1][2]=10; edge[2][1]=10; edge[1][3]=21; edge[3][1]=21;
        edge[1][5]=8;  edge[5][1]=8;  edge[2][3]=18; edge[3][2]=18;
        edge[2][4]=5;  edge[4][2]=5;  edge[2][6]=6;  edge[6][2]=6;
        edge[3][5]=25; edge[5][3]=25; edge[3][6]=19; edge[6][3]=19;
        edge[4][6]=7;  edge[6][4]=7;  edge[5][6]=33; edge[6][5]=33;

        for (int i = 0; i < 10; i++) {
            GA01 ga01 = new GA01(vertex,edge,50,50,0.5,0.01);
            long s = System.currentTimeMillis();
            ga01.geneticAlg();
            long e = System.currentTimeMillis();
            System.out.println("计算时间："+(e-s)+"毫秒");
            System.out.println("--------------------------");
        }

    }

}
