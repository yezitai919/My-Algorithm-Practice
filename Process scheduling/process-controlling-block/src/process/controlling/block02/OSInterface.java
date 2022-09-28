package process.controlling.block02;

import java.util.Scanner;

/**
 * 操作系统界面
 */
public class OSInterface {
    public static void main(String[] args) {
        //操作系统内核对象
        OSKernel oSk = new OSKernel();
        //扫描仪对象
        Scanner sc=new Scanner(System.in);
        //循环显示选择界面
        while (true){
            System.out.println("输入数字选择以下操作：");
            System.out.println("1.创建一个进程  2.撤销一个进程  3.阻塞一个进程  4.唤醒一个进程  5.执行所有进程  6.结束选择操作");

            //接受选择
            int select = sc.nextInt();

            if (select==1){
                System.out.println("请输入进程标识符(整数)");
                int id = sc.nextInt();
                System.out.println("请输入进程的大小(单位kb，平均每kb代表一条指令，平均每条指令执行1毫秒)");
                int size = sc.nextInt();
                oSk.creatPCB(id,size);
            }
            if (select==2){
                System.out.println("请输入要撤销的进程的标识符");
                oSk.undoPCB(sc.nextInt());
            }

            if (select==3){
                System.out.println("请输入要阻塞的进程的标识符");
                oSk.blockPCB(sc.nextInt());
            }

            if (select==4){
                System.out.println("请输入要唤醒的进程的标识符");
                oSk.wakePCB(sc.nextInt());
            }

            if (select==5){
                oSk.executingPCB(1);
            }

            if (select==6){
                break;
            }
        }
    }
}
