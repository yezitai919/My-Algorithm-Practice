package six.scheduling;

import java.util.Scanner;

public class OSInterface {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        OSKernel kernel = new OSKernel();
        while (true){
            System.out.println("输入数字选择以下操作：");
            System.out.println("1.创建进程  2.处理器调度  3.查看进程运行结果  4.结束选择操作");
            int select = sc.nextInt();
            if (select==1){
                System.out.println("请输入进程表示符：");
                int a = sc.nextInt();
                System.out.println("请输入进程优先级：");
                int b = sc.nextInt();
                System.out.println("请输入进程需要的服务时间：(单位：0.1秒)");
                int c = sc.nextInt();
                kernel.creatProcess(a,b,c);
            }
            if (select==2){
                System.out.println("请选择调度方式：");
                System.out.println("1.先来先服务  2.时间片轮换  3.短进程优先  4.静态优先级  5.动态优先级  6.多级反馈队列");
                int ins =sc.nextInt();
                kernel.processorScheduling(ins);
            }
            if (select==3){
                kernel.viewPCBInformation();
            }
            if (select==4){
                break;
            }
        }
    }
}
