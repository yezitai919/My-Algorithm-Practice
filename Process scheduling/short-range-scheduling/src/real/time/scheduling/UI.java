package real.time.scheduling;

import java.util.Scanner;

public class UI {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        OSKernel kernel = new OSKernel();

        //这5行是测试代码，跳过交互，快速创建进程并计算结果。
        /*kernel.creatProcess("A",10,20);
        kernel.creatProcess("B",10,50);
        kernel.creatProcess("C",15,70);
        kernel.earliestDeadlineFirst(3);
        kernel.viewScheduling();*/

        while (true){
            System.out.println("输入数字选择以下操作：");
            System.out.println("1.创建进程  2.查看调度顺序  3.结束选择操作");
            int select = sc.nextInt();
            if (select==1){
                System.out.println("请输入进程表示符：(char)");
                String a = sc.next();
                System.out.println("请输入进程执行时间：(单位：0.1秒)");
                int b = sc.nextInt();
                System.out.println("请输入进程周期：");
                int c = sc.nextInt();
                kernel.creatProcess(a,b,c);

            }
            if (select==2){
                System.out.println("请输入周期数：");
                kernel.earliestDeadlineFirst(sc.nextInt());
                kernel.viewScheduling();
            }
            if (select==3){
                break;
            }
        }
    }
}
