package minimum.spanning.tree;

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
        for (int i = 0; i < ga01.populationNum; i++) {
            //i和i+1凑一对交换，
            if (i+1 < ga01.populationNum && Math.random() < ga01.crossoverRate){
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
                //System.arraycopy(chromosome[parent[i]],temp1,childChromosome[i+1],temp1,temp2);
                //System.arraycopy(chromosome[parent[i+1]],temp1,childChromosome[i],temp1,temp2);
                for (int j = temp1; j < temp1+temp2; j++) {
                    int a = ga01.chromosome[ga01.parent[i]][j];
                    int b = ga01.chromosome[ga01.parent[i+1]][j];
                    if (a != ga01.vertex && ga01.chromosome[ga01.parent[i+1]][a-1]==j+1){
                        ga01.childChromosome[i+1][j] = ga01.chromosome[ga01.parent[i+1]][j];
                    }else {
                        ga01.childChromosome[i+1][j] = ga01.chromosome[ga01.parent[i]][j];
                    }
                    if (b != ga01.vertex && ga01.chromosome[ga01.parent[i]][b-1]==j+1){
                        ga01.childChromosome[i][j] = ga01.chromosome[ga01.parent[i]][j];
                    }else {
                        ga01.childChromosome[i][j] = ga01.chromosome[ga01.parent[i+1]][j];
                    }
                }
                //复制交叉前段
                if (temp1>0){
                    System.arraycopy(ga01.chromosome[ga01.parent[i]],0,ga01.childChromosome[i],0,temp1);
                    System.arraycopy(ga01.chromosome[ga01.parent[i+1]],0,ga01.childChromosome[i+1],0,temp1);
                }
                if (temp1+temp2<ga01.gene){
                    System.arraycopy(ga01.chromosome[ga01.parent[i]],temp1+temp2,ga01.childChromosome[i],temp1+temp2,ga01.gene-(temp1+temp2));
                    System.arraycopy(ga01.chromosome[ga01.parent[i+1]],temp1+temp2,ga01.childChromosome[i+1],temp1+temp2,ga01.gene-(temp1+temp2));
                }
                boolean b1 = ga01.maxToV[ga01.parent[i]]>=temp1 && ga01.maxToV[ga01.parent[i]]<(temp1+temp2) && (ga01.maxToV[ga01.parent[i+1]]<temp1 || ga01.maxToV[ga01.parent[i+1]]>=(temp1+temp2));
                boolean b2 = (ga01.maxToV[ga01.parent[i]]<temp1 || ga01.maxToV[ga01.parent[i]]>=(temp1+temp2)) && ga01.maxToV[ga01.parent[i+1]]>=temp1 && ga01.maxToV[ga01.parent[i+1]]<(temp1+temp2);
                boolean b3 = ga01.maxToV[ga01.parent[i]]>=temp1 && ga01.maxToV[ga01.parent[i]]<(temp1+temp2) && ga01.maxToV[ga01.parent[i+1]]>=temp1 && ga01.maxToV[ga01.parent[i+1]]<(temp1+temp2);
                if (b1){
                    int a = ga01.childChromosome[i][ga01.maxToV[ga01.parent[i+1]]];
                    if (Math.random()<(double) 1/ga01.vertex && a!=ga01.vertex && ga01.childChromosome[i+1][a-1]!=ga01.maxToV[ga01.parent[i+1]]+1){
                        ga01.childChromosome[i+1][ga01.maxToV[ga01.parent[i+1]]] = ga01.childChromosome[i][ga01.maxToV[ga01.parent[i+1]]];
                        ga01.childMaxToV[i+1]=ga01.maxToV[ga01.parent[i]];
                    }else {
                        ga01.childMaxToV[i+1]=ga01.maxToV[ga01.parent[i+1]];
                    }
                    ga01.childChromosome[i][ga01.maxToV[ga01.parent[i+1]]] = ga01.vertex;
                    ga01.childMaxToV[i]=ga01.maxToV[ga01.parent[i+1]];
                }else if (b2){
                    int a = ga01.childChromosome[i+1][ga01.maxToV[ga01.parent[i]]];
                    if (Math.random()<(double) 1/ga01.vertex && a!=ga01.vertex && ga01.childChromosome[i][a-1]!=ga01.maxToV[ga01.parent[i]]+1){
                        ga01.childChromosome[i][ga01.maxToV[ga01.parent[i]]] = ga01.childChromosome[i+1][ga01.maxToV[ga01.parent[i]]];
                        ga01.childMaxToV[i]=ga01.maxToV[ga01.parent[i+1]];
                    }else {
                        ga01.childMaxToV[i]=ga01.maxToV[ga01.parent[i]];
                    }
                    ga01.childChromosome[i+1][ga01.maxToV[ga01.parent[i]]] = ga01.vertex;
                    ga01.childMaxToV[i+1]=ga01.maxToV[ga01.parent[i]];
                }else if (b3){
                    ga01.childMaxToV[i]=ga01.maxToV[ga01.parent[i+1]];
                    ga01.childMaxToV[i+1]=ga01.maxToV[ga01.parent[i]];
                }else {
                    ga01.childMaxToV[i]=ga01.maxToV[ga01.parent[i]];
                    ga01.childMaxToV[i+1]=ga01.maxToV[ga01.parent[i+1]];
                }
                i++;
            }else {
                ga01.childChromosome[i]=ga01.chromosome[ga01.parent[i]].clone();
                ga01.childMaxToV[i]=ga01.maxToV[ga01.parent[i]];
            }
        }
    }
}
