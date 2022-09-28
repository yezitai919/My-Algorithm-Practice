import java.text.DecimalFormat;
public class Demo01 {
    public static void main(String[] args) {
        double cityD = 1000;//两城市的距离(km)
        double tankC = 40;//油箱容量(L)
        double cPetrol = 10;//当前油量
        double sPetrol = 7.51;
        double pConsumption = 20;//耗油量(km/L)
        //gasStation[i]表示第i个加油站的油价(￥/L)和距前一个加油站的距离(km)
        double[][] gasStation = {{sPetrol,0},{7.25,156},{7.69,365},{7.22,572},{7.77,702},{7.35,895},{0,cityD}};
        double[] plan = refuelingPlan(tankC,cPetrol,pConsumption,gasStation);
        DecimalFormat df = new DecimalFormat( "0.00 ");//
        double totalCost = 0;//
        System.out.println("最优的加油方案为：");
        for (int i = 0; i < plan.length; i++) {
            if (plan[i] != 0){
                totalCost += plan[i];
                double rmb = plan[i]*gasStation[i][0];
                System.out.println("在第"+i+"个加油站加"+plan[i]+"升汽油，花费"+df.format(rmb)+"元");
            }
        }
        System.out.println("全程总共花费"+df.format(totalCost)+"元油钱。");
    }
    private static double[] refuelingPlan(double tankC,double cPetrol,double pConsumption,double[][] gasStation){
        int cStation = 0;//当前所在的加油站的编号
        int n = gasStation.length;//加油站数量
        double[] plan = new double[n];//记录每个加油站分别加多少油
        double fullTankD = tankC*pConsumption;//满油箱可行驶的距离
        while (cStation < n-1){
            int cheapest = cStation;//记录油箱范围内最便宜的站的编号
            double cheapestD = 0;
            int furthest = 0;//记录油箱范围内最远的加油站编号
            double furthestD = 0;
            for (int i = cStation+1; i < n; i++) {//
                double stationD = gasStation[i][1] - gasStation[cStation][1];//到加油站i的距离
                furthestD = stationD;
                furthest = i;
                if (stationD > fullTankD) break;
                if (gasStation[i][0] <= gasStation[cStation][0] && cheapest == cStation){
                    cheapest = i;
                    cheapestD = stationD;
                }
            }
            double cheapestP = cheapestD/pConsumption;
            if (cheapest == cStation){//如果找不到更便宜的加油站就在当前站加满油
                plan[cStation] = tankC - cPetrol;//当前站点加油量
                cPetrol = tankC - furthestD/pConsumption;//到达更新油量
                cStation = furthest;//更新站
            }else if (cPetrol >= cheapestP) {//如果找到的最便宜的站比当前站就应该在当前站少加油
                //如果当前油量能开到i就直接开过去
                cPetrol -= cheapestP;//开到i更新当前油量
                cStation = cheapest;//更新当前加油站
            } else {//如果不够油就在当前加油站加到刚好能到i的油量
                plan[cStation] = cheapestP - cPetrol;
                cPetrol = 0;//开到i更新当前油量
                cStation = cheapest;//更新当前加油站
            }
        }
        return plan;
    }
}
