package travelling.salesman.problem;

import java.util.Arrays;

/**染色体交叉
 * @author 矜君
 * @date 2020/9/29 10:48.
 */
public class GeneticOperatorCrossover01 {
    /**父母染色体交叉或复制繁衍子代
     * @param ga01 资源对象
     */
    public static void crossover(GeneticAlgResources01 ga01){
        //遍历种群个体
        for (int i = 0; i < ga01.populationNum; i++) {
            //如果当前生成的随机数小于交叉率，则i和i+1号染色体凑一对交叉，生成两个子代，父母淘汰，子代留下
            if (i+1<ga01.populationNum && Math.random()<ga01.crossoverRate){
                //双点交叉
                int temp1 = (int)(Math.random()*(ga01.gene-1))+1;
                int temp2 = (int)(Math.random()*(ga01.gene-1))+1;
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
                System.arraycopy(ga01.chromosome[ga01.parent[i]],temp1,ga01.childChromosome[i+1],temp1,temp2);
                System.arraycopy(ga01.chromosome[ga01.parent[i+1]],temp1,ga01.childChromosome[i],temp1,temp2);


                int[] c1 = Arrays.copyOfRange(ga01.childChromosome[i+1],temp1,temp1+temp2);
                int[] c2 = Arrays.copyOfRange(ga01.childChromosome[i],temp1,temp1+temp2);

                for (int j = 0; j < temp2; j++) {
                    for (int k = 0; k < temp2; k++) {
                        if (c1[j]==c2[k]){
                            c1[j]=0;
                            c2[k]=0;
                        }
                    }
                }

                for (int j = 0; j < ga01.gene; j++) {
                    if (j>=temp1&&j<temp1+temp2){
                        continue;
                    }
                    boolean b1 = false;
                    boolean b2 = false;
                    for (int k = temp1; k < temp1+temp2; k++) {
                        if (ga01.chromosome[ga01.parent[i]][j] == ga01.childChromosome[i][k]){
                            for (int l = 0; l < temp2; l++) {
                                if (c1[l]!=0){
                                    ga01.childChromosome[i][j] = c1[l];
                                    c1[l]=0;
                                    break;
                                }
                            }
                            b1 = true;
                        }
                        if (ga01.chromosome[ga01.parent[i+1]][j] == ga01.childChromosome[i+1][k]){
                            for (int l = 0; l < temp2; l++) {
                                if (c2[l]!=0){
                                    ga01.childChromosome[i+1][j] = c2[l];
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
                        ga01.childChromosome[i][j] = ga01.chromosome[ga01.parent[i]][j];
                    }
                    if (!b2){
                        ga01.childChromosome[i+1][j] = ga01.chromosome[ga01.parent[i+1]][j];
                    }
                }
                i++;
            }else {
                ga01.childChromosome[i]=ga01.chromosome[ga01.parent[i]].clone();
            }
        }
    }
}
