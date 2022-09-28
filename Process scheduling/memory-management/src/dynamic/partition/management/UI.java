package dynamic.partition.management;

import java.util.Scanner;

public class UI {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Kernel kernel = new Kernel();

       /*这几行是测试代码，跳过交互，快速创建进程并计算结果。
       kernel.initialization(128);
        kernel.firstOrBestOrWorstFit(new Request(1,48),1);
        kernel.firstOrBestOrWorstFit(new Request(2,64),1);
        kernel.viewMemoryAllocation();
        System.out.println(" ");
        kernel.recycling(1);
        kernel.firstOrBestOrWorstFit(new Request(3,12),1);
        kernel.firstOrBestOrWorstFit(new Request(4,24),1);
        kernel.viewMemoryAllocation();*/


        while (true) {
            System.out.println("输入数字选择以下操作：");
            System.out.println("1.初始化内存  2.提出内存请求并分配内存  3.收回内存请求  4.输出内存分配表  5.结束选择操作");
            int select = sc.nextInt();
            if (select == 1) {
                System.out.println("请输入内存大小：(kb)");
                kernel.initialization(sc.nextInt());
                System.out.println("初始化成功！");
            }
            if (select == 2) {
                System.out.println("请输入进程名：");
                int id = sc.nextInt();
                System.out.println("请输入进程大小");
                int size = sc.nextInt();
                System.out.println("请输入分配方式：1.最先适应法  2.最优适应法  3.最坏适应法");
                int ins = sc.nextInt();
                kernel.firstOrBestOrWorstFit(new Request(id,size),ins);
            }
            if (select == 3) {
                System.out.println("请输入进程名：");
                int id = sc.nextInt();
                kernel.recycling(id);
            }
            if (select == 4){
                kernel.viewMemoryAllocation();
            }
            if (select == 5){
                break;
            }
        }
    }
}