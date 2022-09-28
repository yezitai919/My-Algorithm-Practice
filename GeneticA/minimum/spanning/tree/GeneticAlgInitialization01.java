package minimum.spanning.tree;

import java.util.ArrayList;

/**种群初始化
 * @author 矜君
 * @date 2020/9/29 21:18.
 */
public class GeneticAlgInitialization01 {
    /** 初始化资源类数据，生成初代种群。
     * @param ga01 资源类对象
     */
    public static void initialization(GeneticAlgResources01 ga01){
        ga01.gene = ga01.vertex-1;
        ga01.chromosome = new int[ga01.populationNum][ga01.gene];
        ga01.adaptiveness = new double[ga01.populationNum];
        //
        ga01.parent = new int[ga01.populationNum];
        ga01.maxToV = new int[ga01.populationNum];
        ga01.childMaxToV = new int[ga01.populationNum];
        ga01.childChromosome = new int[ga01.populationNum][ga01.gene];
        ga01.optimalSolution = new int[ga01.gene];

        for (int i = 0; i < ga01.populationNum; i++) {
            ArrayList<Integer> a = new ArrayList<>();
            ArrayList<Integer> b = new ArrayList<>();
            for (int j = 1; j < ga01.vertex; j++) {
                b.add(j);
            }
            while (true){
                int k = (int)(Math.random()*(ga01.vertex-1)+1);
                if (ga01.edge[ga01.vertex][k] != 0){
                    ga01.chromosome[i][k-1] = ga01.vertex;
                    ga01.maxToV[i] = k-1;
                    a.add(k);
                    b.remove(Integer.valueOf(k));
                    break;
                }
            }
            int eNum = 0;
            while (eNum<ga01.gene-1){
                for (Integer i1 : a) {
                    boolean bk = false;
                    for (Integer i2 : b) {
                        double r = Math.random();
                        if (Math.random() < r && ga01.edge[i1][i2] != 0) {
                                /*int aa = chromosome[i][j];
                                if (aa!=vertex&&chromosome[i][aa-1]==j+1){*/
                            if (ga01.chromosome[i][i2-1] == 0 && ga01.chromosome[i][i1-1]!= i2) {
                                ga01.chromosome[i][i2-1] = i1;
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
    }
}
