package genetic_alg02;

/**
 * @Author jinjun99
 * @Date Created in 2022/3/20 14:58
 * @Description
 * @Since version-1.0
 */
public class OperatorCrossover02 {
    public static void crossover(GenAlgResources02 gAr01){
        for (int i = 0; i < gAr01.populationNum; i++) {
            if (i+2<gAr01.populationNum && Math.random()<gAr01.crossoverRate) {
                //双点交叉
                int t1 = (int) (Math.random() * (gAr01.gene - 3)) + 1;
                int t2 = (int) (Math.random() * (gAr01.gene - 3)) + 2;
                /*保证t2>t1*/
                if (t2 < t1) {
                    int t3 = t1;
                    t1 = t2;
                    t2 = t3;
                }
                /*染色体复制*/
                for (int j = 0; j < gAr01.gene; j++) {
                    /*两个交叉点之间交叉复制*/
                    if (j >= t1 && j <= t2&&gAr01.chromosome[gAr01.parent[i]][j]!=0&&gAr01.chromosome[gAr01.parent[i+1]][j]!=0) {
                        gAr01.childChromosome[i][j] = gAr01.chromosome[gAr01.parent[i+1]][j];
                        gAr01.childChromosome[i+1][j] = gAr01.chromosome[gAr01.parent[i]][j];
                    } else {
                        gAr01.childChromosome[i][j] = gAr01.chromosome[gAr01.parent[i]][j];
                        gAr01.childChromosome[i+1][j] = gAr01.chromosome[gAr01.parent[i+1]][j];
                    }
                }
                /*冲突互换*/
                int count = 0;
                while (true){
                    for (int j = 0; j < gAr01.gene; j++) {
                        if (j>=t1&&j<=t2&&gAr01.childChromosome[i][j]!=0&&gAr01.childChromosome[i+1][j]!=0){
                            for (int k = 0; k < gAr01.gene; k++) {
                                if (k<t1||k>t2||gAr01.childChromosome[i][k]==0||gAr01.childChromosome[i+1][k]==0){
                                    if (gAr01.childChromosome[i][k]!=0&&gAr01.childChromosome[i][k]==gAr01.childChromosome[i][j]){
                                        gAr01.childChromosome[i][k]=gAr01.childChromosome[i+1][j];
                                        count++;
                                    }
                                    if (gAr01.childChromosome[i+1][k]!=0&&gAr01.childChromosome[i+1][k]==gAr01.childChromosome[i+1][j]){
                                        gAr01.childChromosome[i+1][k]=gAr01.childChromosome[i][j];
                                        count++;
                                    }
                                }
                            }
                        }
                    }
                    if (count==0){
                        break;
                    }else {
                        count=0;
                    }
                }
                i++;
            }else {
                gAr01.childChromosome[i]=gAr01.chromosome[gAr01.parent[i]].clone();
            }
        }
    }
}
