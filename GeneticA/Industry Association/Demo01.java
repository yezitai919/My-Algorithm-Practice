
package trade.association;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

/**
 * @author 矜君
 * @date 2020/9/6 21:51.
 */
public class Demo01 {
    public static void main(String[] args) throws IOException {
        //企业个数
        int n;
        //企业的信息共享成本(如果加入平台)
        double[] c;
        //收益矩阵
        double[][] profit;
        //企业加入平台的最低收益率门槛值
        double r = 0.1;
        //补贴金额的总预算值(补贴上限值)
        double yY = 1000;
        //要求的业内企业加入平台的最低比率
        double tT = 0.6;
        //一个大数
        double mM = 9999;
        /*Scanner sc = new Scanner(System.in);
        System.out.println("请输入计算参数的文件路径名：");
        String fileName = sc.nextLine();*/

        FileReader fileReader = new FileReader("C:\\Users\\矜君\\Desktop\\新建文件夹 (2)\\data_300.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line =bufferedReader.readLine();
        n=(int) getDoubleValue(line);
        c = new double[n];
        profit = new double[n][n];
        int a = 0;
        while (line!=null){
            line = bufferedReader.readLine();
            if (a<n){
                c[a]=getDoubleValue(line);
                a++;
            }else if (a<2*n){
                profit[a-n]= Arrays.copyOf(getDoubleArrays(line,n),n);
                a++;
            }
        }

        bufferedReader.close();
        fileReader.close();

        Date date1 = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("HH-mm-ss");
        String str1 = formatter.format(date1);
    /*    System.out.println("请输入存放计算结果的文件夹路径名：");
        String str2 = sc.nextLine();
        sc.close();
        str2=str2+"\\计算结果-"+str1+".txt";*/

        /*String str2 = "C:\\Users\\矜君\\Desktop\\新建文件夹 (2)\\计算结果-"+str1+".txt";
        File file1 = new File(str2);
        FileWriter fw1 = new FileWriter(file1);
        BufferedWriter bw1 = new BufferedWriter(fw1);
        bw1.write("------------------------------------------------------------------------------------------------------------\t\n");
*/
        //记录开始计算的时间
        long s1 = System.currentTimeMillis();


        //调用求最大化协会收益的函数
        maxRevenue(n,c,profit,r,yY,tT,mM);


        //记录结束计算的时间
        long e1 = System.currentTimeMillis();
        System.out.println("计算时间："+(e1-s1)+" 微秒");
      /*  bw1.write("  计算时间："+(e1-s1)+"微秒\t\n");
        bw1.write("------------------------------------------------------------------------------------------------------------\t\n");


        //记录开始计算的时间
        long s2 = System.currentTimeMillis();

        //调用求最大化参与企业数的函数
       // maxParticipation(n,c,profit,r,yY,tT,mM,bw1);


        //记录结束计算的时间
        long e2 = System.currentTimeMillis();
        bw1.write("  计算时间："+(e2-s2)+"微秒\t\n");
        bw1.write("------------------------------------------------------------------------------------------------------------\t\n");
        bw1.flush();
        bw1.close();
        System.out.println("计算结果的路径名："+str2);*/
    }
    public static void maxRevenue(int n,double[]c,double[][] profit,double r,double yY,double tT,double mM/*,BufferedWriter bw1*/) throws IOException {


        //遗传算法:
        // 基因数=n
        // 初代种群数量
        int populationNum = 50;
        // 初代种群的染色体的基因序列
        int[][] chromosome = new int[populationNum][n];
        // 初代种群适应度
        double[][] adaptiveness = new double[populationNum][2];
        // 种群适应度总和
        double adaptivenessSum = 0;
        //总的演化代数
        int generationNum = 100;
        //当前演化代数
        int currGeneration = 0;
        // 生存率
        double[] survivalRate = new double[populationNum];
        // 生存者编号(有重复，足够优秀的个体可能被选中
        // 多次，这意味着它可以有多个孩子)
        int[] parent = new int[populationNum];
        // 交叉概率
        double matingRate = 0.6;
        //变异概率
        double variationRate = 0.01;
        //优秀比例
        double excellenceRate = 0.2;
        //精英数量
        int eliteNum = (int)(populationNum *excellenceRate);
        // 初代种群的染色体的基因序列
        int[][] childChromosome = new int[populationNum][n];
        // 最优染色体的适应度
        int optimalNo = 0;

        //协会总补贴
        double[] subsidy = new double[populationNum];
        //加入企业数量
        int[] join = new int[populationNum];

        //连续变量，表示企业i的净收益。
        double[][] q = new double[populationNum][n];
        //非负连续变量，表示对企业i的收费金额。
        double[][] z = new double[populationNum][n];
        //非负连续变量，表示对企业i的补贴金额。
        double[][] y = new double[populationNum][n];

        // 生成初始种群
        for (int i = 0; i < populationNum; i++) {
            for (int j = 0; j < n; j++) {
                chromosome[i][j]=Math.random()>0.5?1:0;
            }
        }
        //计算初代适应性
        for (int i = 0; i < populationNum; i++) {
            adaptiveness[i][0]=i;
            //adaptiveness[i][1]=0;
            for (int j = 0; j < n; j++) {
                if (chromosome[i][j]==1){
                    double sum = 0;
                    double threshold1;
                    for (int k = 0; k < n; k++) {
                        sum+=chromosome[i][k]*profit[j][k];
                    }
                    q[i][j]=sum-c[j];
                    threshold1 = q[i][j]-r*c[j];

                    if (threshold1>0){
                        z[i][j]=threshold1/(1+r);
                        if (z[i][j]<=mM){
                            q[i][j]-=z[i][j];
                            adaptiveness[i][1]+=threshold1/(1+r);
                        }//else{报错}
                    }else if (-threshold1<=mM){
                        y[i][j]=-threshold1;
                        subsidy[i]+=y[i][j];
                        q[i][j]+=y[i][j];
                    }
                    join[i]++;
                }
            }

          /*  if ((double)join[i]/n>=tT){
                System.out.println(Arrays.toString(chromosome[i]));
                System.out.println(join[i]);
                System.out.println((double)join[i]/n);
                System.out.println("收费："+adaptiveness[i][1]);
                System.out.println("补贴："+subsidy[i]);
            }*/
            if (subsidy[i]<=yY && (double)join[i]/n>=tT){
                adaptiveness[i][1]-=subsidy[i];
                //System.out.println("收益1："+adaptiveness[i][1]);
            }else {
                /*if ((double)join[i]/n>=tT){
                    System.out.println("收益2："+adaptiveness[i][1]);
                }*/

                adaptiveness[i][1]=-mM;
                subsidy[i]=0;
            }
            adaptivenessSum+=adaptiveness[i][1];

        }
        Arrays.sort(adaptiveness,((o1, o2) -> (int) (o2[1]-o1[1])));


 /*       System.out.println("最优解：");
        System.out.println(Arrays.toString(chromosome[(int) adaptiveness[0][0]]));
        System.out.println(join[(int) adaptiveness[0][0]]);
        System.out.println((double)join[(int) adaptiveness[0][0]]/n);
        System.out.println("收费："+(adaptiveness[0][1]+subsidy[(int) adaptiveness[0][0]]));
        System.out.println("补贴："+subsidy[(int) adaptiveness[0][0]]);
        System.out.println("收益："+adaptiveness[0][1]);*/

        while (currGeneration<generationNum){
            //轮盘赌选择
            for (int i = 0; i < populationNum; i++) {
                double temp= adaptiveness[i][1]/adaptivenessSum;
                survivalRate[i]=i==0?temp:temp+survivalRate[i-1];
            }
            for (int i = 0; i < populationNum; i++) {
                double temp = Math.random();
                for (int j = 0; j < populationNum; j++) {
                    if (temp<survivalRate[j]){
                        parent[i]=j;
                        break;
                    }
                }
            }
            //生存者交换或复制染色体产生子代。
            //精英染色体直接复制

            for (int i = 0; i < eliteNum; i++) {
                childChromosome[i]=chromosome[(int) adaptiveness[i][0]].clone();
            }
            //选出的染色体交叉产生下一代
            for (int i = eliteNum; i < populationNum; i+=2) {
                if (i+1<populationNum){
                    if (Math.random()<matingRate){
                        int temp = (int)(Math.random()* n);
                        System.arraycopy(chromosome[parent[i]],temp+1,childChromosome[i],temp+1, n -temp-1);
                        System.arraycopy(chromosome[parent[i+1]],0,childChromosome[i],0,temp);
                        System.arraycopy(chromosome[parent[i]],0,childChromosome[i+1],0,temp);
                        System.arraycopy(chromosome[parent[i+1]],temp+1,childChromosome[i+1],temp+1, n -temp-1);
                    }else {
                        childChromosome[i]=chromosome[parent[i]].clone();
                        childChromosome[i+1]=chromosome[parent[i+1]].clone();
                    }
                }else {
                    childChromosome[i]=chromosome[parent[i]].clone();
                }
            }
            //基因突变
            for (int i = 0; i < populationNum; i++) {
                for (int j = 0; j < n; j++) {
                    if (Math.random()<variationRate){
                        if (childChromosome[i][j]==0){
                            childChromosome[i][j]=1;
                        }else {
                            childChromosome[i][j]=0;
                        }
                    }
                }
            }
            //更新下一代染色体
            for (int i = 0; i < populationNum; i++) {
                chromosome[i]=childChromosome[i].clone();
            }
            //更新下一代染色体的适应性
            for (int i = 0; i < populationNum; i++) {
                subsidy[i]=0;
                join[i]=0;
                adaptiveness[i][0]=i;
                adaptiveness[i][1]=0;
                adaptivenessSum=0;
                q[i]=new double[n];
                z[i]=new double[n];
                y[i]=new double[n];
                for (int j = 0; j < n; j++) {
                    if (chromosome[i][j]==1){
                        double sum = 0;
                        double threshold1;
                        for (int k = 0; k < n; k++) {
                            sum+=chromosome[i][k]*profit[j][k];
                        }
                        q[i][j]=sum-c[j];
                        threshold1 = q[i][j]-r*c[j];

                        if (threshold1>0){
                            z[i][j]=threshold1/(1+r);
                            if (z[i][j]<=mM){
                                q[i][j]-=z[i][j];
                                adaptiveness[i][1]+=threshold1/(1+r);
                            }//else{报错}
                        }else if (-threshold1<=mM){
                            y[i][j]=-threshold1;
                            subsidy[i]+=y[i][j];
                            q[i][j]+=y[i][j];
                        }
                        join[i]++;
                    }
                }

          /*  if ((double)join[i]/n>=tT){
                System.out.println(Arrays.toString(chromosome[i]));
                System.out.println(join[i]);
                System.out.println((double)join[i]/n);
                System.out.println("收费："+adaptiveness[i][1]);
                System.out.println("补贴："+subsidy[i]);
            }*/
                if (subsidy[i]<=yY && (double)join[i]/n>=tT){
                    adaptiveness[i][1]-=subsidy[i];
                    //System.out.println("收益1："+adaptiveness[i][1]);
                }else {
                /*if ((double)join[i]/n>=tT){
                    System.out.println("收益2："+adaptiveness[i][1]);
                }*/
                    adaptiveness[i][1]=-mM;
                    subsidy[i]=0;
                }
                adaptivenessSum+=adaptiveness[i][1];
            }
            Arrays.sort(adaptiveness,((o1, o2) -> (int)o2[1]-(int)o1[1]));
            currGeneration++;
        }
        //更新最优值和最优解
        if (currGeneration==generationNum){
            for (int i = 0; i < populationNum; i++) {
                int temp = (int) (Math.random() * populationNum);
                if (adaptiveness[temp][1]==adaptiveness[temp+1][1]&&
                        adaptiveness[temp][1]==adaptiveness[temp+2][1]&&
                        adaptiveness[temp][1]==adaptiveness[temp+3][1]){
                    optimalNo=temp;
                    break;
                }
            }
        }
        System.out.print("适应度：[");
        for (int i = 0; i < populationNum; i++) {
            System.out.print(adaptiveness[i][1]+",");
        }
        System.out.println("]");
       // writingDocuments(bw1,n,chromosome[optimalNo],y[optimalNo],z[optimalNo],q[optimalNo],c,r,adaptiveness[optimalNo][1],subsidy[optimalNo],join[optimalNo]);
    }


    public static void maxParticipation(int n,double[]c,double[][] profit,double r,double yY,double tT,double mM,BufferedWriter bw1) throws IOException {
        //加入企业数量
        int join = n;
        //0-1变量，表示企业i是否愿意加入平台，1：是，0：否。
        int[] x = new int[n];
        //0-1变量，表示对企业i是否收费，1：是(收费)，0：否(补贴)，-1：该企业已退出。
        int[] b = new int[n];
        //非负连续变量，表示对企业i的补贴金额。
        double[] y = new double[n];
        //非负连续变量，表示对企业i的收费金额。
        double[] z = new double[n];
        //连续变量，表示企业i的净收益。
        double[] q = new double[n];
        /*企业收益-企业收益门槛值，大于0时可以按比例收费，小于0时就是要补贴金额*/
        double[] threshold = new double[n];
        /*优先级，就是协会对i企业的补贴划算程度，其值=补贴金额-企业i对
        其他企业贡献的收益总额。值越大优先级越高。*/
        double[] priority = new double[n];
        //协会总收益
        double associationProfit = 0;
        //协会总补贴
        double subsidy = 0;
        //先假设所有人都加入
        for (int i = 0; i < n; i++) {
            x[i] = 1;
            b[i] = 1;
        }

        bw1.write("  求入会企业数最大化：\t\n");
        while (true){
            //遍历收益矩阵，计算和更新q[i],threshold[i],z[i],b[i],priority[i].
            for (int i = 0; i < n; i++) {
                //计算q[i]的值
                double pi = 0;
                for (int j = 0; j < n; j++) {
                    pi+=x[j]*profit[i][j];
                }
                q[i]=pi-c[i];
                //计算threshold[i]的值
                threshold[i]=q[i]-r*c[i];
                //如果不需要补贴
                if (threshold[i] >= 0 ){
                    //不用补贴，划算程度设为最大。
                    priority[i] = mM;
                    //计算收费金额
                    z[i]=threshold[i]/(1+r);
                    if (z[i]<=mM){
                        //更新数据
                        q[i]-=z[i];
                        threshold[i]-=r*z[i];
                        associationProfit+=z[i];
                    }
                    //如果本来要补贴的变成不用补贴，更改标记。
                    if (b[i]==0){
                        b[i]=1;
                    }//如果需要补贴，先检查是否已退出
                }else if (x[i]==1){
                    //补贴标记
                    b[i]=0;
                    z[i]=b[i];
                    //计算企业i的贡献总额
                    int contribution = 0;
                    for (int j = 0; j < n; j++) {
                        contribution+=profit[j][i];
                    }//计算补贴企业i的划算程度
                    priority[i]=contribution+threshold[i];
                }
            }

            //寻找优先级最低的企业编号
            //工具变量，temp2记录补贴最不划算的企业编号，temp3是划算程度，辅助比较大小。
            int temp2 = 0;
            double temp3 = mM;
            for (int i = 0; i < n; i++) {
                //遍历threshold找出需要补贴的企业中最不划算的企业编号
                if (b[i]==0 && x[i]==1){
                    if (priority[i]<temp3){
                        temp2=i;
                        temp3=priority[i];
                    }
                    //记录补贴额
                    subsidy-=threshold[i];
                }
            }

            //如果企业补贴额不超上限
            if (subsidy<=yY){
                //更新协会收益
                associationProfit-=subsidy;
                break;
                /*如果补贴额超了，就让补贴最不划算的一个企业退出
                只有加入企业数大于最低比率，才能让企业退出*/
            }else if ((double)join/n>tT){
                join--;
                x[temp2]=0;
            }else {
                reportErrors(bw1,tT,join,subsidy,yY);
                return;
            }
            //退出一个企业后，协会收费要重新计算，收益归零。
            associationProfit=0;
            subsidy=0;
        }
        //最后再更新y[i]
        for (int i = 0; i < n; i++) {
            if (b[i]==0 && -threshold[i]<=mM && x[i]==1){
                y[i]=-threshold[i];
                q[i]+=y[i];
            }
        }
        writingDocuments(bw1,n,x,y,z,q,c,r,associationProfit,subsidy,join);
    }
    private static String interval(double num){
        int n = 16;
        StringBuilder str = new StringBuilder();
        while (Math.abs(num)/10>=1 && n>1){
            num/=10;
            n-=2;
        }
        if (num<0){
            n--;
        }
        for (int i = 0; i < n; i++) {
            str.append(" ");
        }
        return str.toString();
    }
    private static double getDoubleValue(String str) {
        double d = 0;
        if (str != null && str.length() != 0) {
            StringBuilder b = new StringBuilder();
            char[] chars = str.toCharArray();
            for (char c : chars) {
                if (c >= '0' && c <= '9') {
                    b.append(c);
                } else if (c=='.') {
                    if (b.indexOf(".") != -1 && b.length()!=0) {
                        break;
                    } else {
                        b.append(c);
                    }
                }else if (c=='-'){
                    if (b.indexOf("-") != -1 && b.length()==0) {
                        break;
                    } else if (b.length()==0){
                        b.append(c);
                    }
                } else {
                    if (b.length() != 0 ) {
                        break;
                    }
                }
            }
            d = Double.parseDouble("".equals(b.toString())?"0.00":b.toString());
        }
        return d;
    }
    private static double[] getDoubleArrays(String str,int n) {
        double[] d = new double[n];
        if (str != null && str.length() != 0) {
            int temp1 = 0;
            int temp2 = 0;
            StringBuilder b = new StringBuilder();
            char[] chars = str.toCharArray();
            for (char c : chars) {
                if (c >= '0' && c <= '9') {
                    b.append(c);
                    temp2=0;
                } else if (c == '.') {
                    if (b.indexOf(".") != -1 && b.length() != 0 && temp2==0) {
                        temp2=1;
                    } else if (b.length()!=0){
                        b.append(c);

                    }
                } else if (c == '-') {
                    if (b.indexOf("-") != -1 && b.length() != 0 && temp2==0) {
                        temp2=1;
                    } else if (b.length()==0){
                        b.append(c);
                    }

                }else if (b.length() != 0 && temp2==0 ) {
                    temp2=1;
                }
                if (temp2 == 1 && temp1 < n && !"".equals(b.toString())){
                    d[temp1] = Double.parseDouble(b.toString());
                    temp1++;
                    temp2=0;
                    b=new StringBuilder();

                }
            }
            if (!"".equals(b.toString())){
                d[temp1] = Double.parseDouble(b.toString());
            }
        }
        return d;
    }
    private static void reportErrors(BufferedWriter bw1,double tT,int join,double subsidy,double yY) throws IOException {
        bw1.write("  输入数据错误：要求的业内企业加入平台的最低比率为" + tT +
                "时，补贴总额超预算。\t\n");
        bw1.write("  加入企业数为" + join + "，补贴总额为" + subsidy + "，预算总额为" + yY + "。\t\n");
        bw1.write("  请修改要求业内企业加入平台的最低比率或收益矩阵。\t\n");
    }
    private static void writingDocuments(BufferedWriter bw1,int n,int[]x,double[]y,double[]z,double[]q,double[]c,double r,
                                         double associationProfit,double subsidy,int join) throws IOException {
        bw1.write("                  i                 x                   y              " +
                "      z                    q                   c                     r\t\n");
        for (int i = 0; i < n; i++) {
            bw1.write(interval(i+1));
            bw1.write(String.format("%d", i+1));
            bw1.write(interval(x[i]));
            bw1.write(String.format("%d", x[i]));
            bw1.write(interval(y[i]));
            bw1.write(String.format("%.1f", y[i]));
            bw1.write(interval(z[i]));
            bw1.write(String.format("%.1f", z[i]));
            bw1.write(interval(q[i]));
            bw1.write(String.format("%.1f", q[i]));
            bw1.write(interval(c[i]));
            bw1.write(String.format("%.1f", c[i]));
            bw1.write(interval(r));
            bw1.write(String.format("%.1f", r)+"\t\n");
        }
        bw1.write("  行业内的企业个数："+n+"\t\n");
        bw1.write("  加入协会的企业数："+join+"\t\n");
        bw1.write("  协会总收费金额："+(associationProfit+subsidy)+"\t\n");
        bw1.write("  协会总补贴金额："+subsidy+"\t\n");
        bw1.write("  协会净收益金额："+associationProfit+"\t\n");
        bw1.flush();
    }

}


