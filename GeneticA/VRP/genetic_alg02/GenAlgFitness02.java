package genetic_alg02;

/**
 * @Author jinjun99
 * @Date Created in 2022/3/20 11:24
 * @Description
 * @Since version-1.0
 */
public class GenAlgFitness02 {
    /**
     * @param gAr01
     */
    public static void calculateFitness(GenAlgResources02 gAr01){
        //适应度总和是直接用+=计算的，所以要先初始化。
        gAr01.fitnessSum=0;
        //零时变量，记录当前种群的最优适应度及其编号
        double bestFitness = Double.MAX_VALUE;
        int bestFitnessNo = 0;

        for (int i = 0; i < gAr01.populationNum; i++) {
            /*当前染色体安排路径的总路程*/
            double distance = 0;
            /*当前子路径累计装载量*/
            int cLoad = 0;
            /*当前子路径累计路程*/
            double cDist = 0;
            /*当前染色体安排路径的总超时数*/
            double timeOut = 0;
            /*两点坐标*/
            double x1 = gAr01.coordinate[0][0];
            double y1 = gAr01.coordinate[0][1];
            double x2 = gAr01.coordinate[gAr01.chromosome[i][1]][0];
            double y2 = gAr01.coordinate[gAr01.chromosome[i][1]][1];
            for (int j = 1; j < gAr01.gene; j++) {

                int jNum = gAr01.chromosome[i][j];
                double dist1 = Math.sqrt(Math.abs(Math.pow(x1-x2,2)+Math.pow(y1-y2,2)));
                /*回到仓库归零*/
                if (jNum==0){
                    cLoad=0;
                    cDist=0;
                }else {
                    /*累加装载量*/
                    cLoad+=gAr01.needs[jNum];
                    /*累加子路径路程*/
                    cDist+=dist1;
                    /*累计用时*/
                    double cTime =cDist/gAr01.speed;
                    /*累计超时*/
                    if (cTime<gAr01.timeLimit[jNum-1][0]){
                        timeOut+=(gAr01.timeLimit[jNum-1][0]-cTime);
                    }
                    if (cTime>gAr01.timeLimit[jNum-1][1]){
                        timeOut+=(cTime-gAr01.timeLimit[jNum-1][1]);
                    }
                }

                /*如果该路程累计的客户需求超过货车载量，说明该路程不可行，
                该染色体适应度变为无穷大直接淘汰*/
                if (cLoad>gAr01.loading){
                    distance=Double.MAX_VALUE;
                    break;
                }
                /*累加总路程*/
                distance+=dist1;


                /*更新两点坐标*/
                if (j+1< gAr01.gene){
                    x1=x2;
                    y1=y2;
                    x2=gAr01.coordinate[gAr01.chromosome[i][j+1]][0];
                    y2=gAr01.coordinate[gAr01.chromosome[i][j+1]][1];
                }
            }

            if (distance<Double.MAX_VALUE){
                /*当前染色体的路程总花费*/
                double currCost = distance*gAr01.drivingCost+timeOut*gAr01.punish;

                if (currCost<bestFitness){
                    bestFitness = currCost;
                    bestFitnessNo = i;
                }
                gAr01.fitness[i]=(1.0/currCost);
                gAr01.fitnessSum+=gAr01.fitness[i];
            }
        }
        //如果当前种群的最优适应度大于当前之前每一代种群的最优适应度就更新当前的最优值和最优解
        if (bestFitness<gAr01.optimalValue){
            gAr01.optimalValue=bestFitness;
            gAr01.optimalSolution = gAr01.chromosome[bestFitnessNo].clone();
        }
    }
}
