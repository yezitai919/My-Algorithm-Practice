package process.controlling.block1;

import java.util.Scanner;

/**
 * 系统界面
 */
public class OSInterface {

    /**
     * 操作系统内核对象
     */
    private OSKernel kernel;

    /**
     * 扫描仪对象
     */
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        OSKernel kernel = new OSKernel();
        while (true){
            System.out.println("输入数字选择以下操作：");
            System.out.println("1.创建一个进程  2.撤销一个进程  3.阻塞一个进程  4.唤醒一个进程");
            System.out.println("5.执行一个进程  6.执行所有进程  7.查看所有PCB  8.结束所有选择");
            int select = sc.nextInt();
            if (select==1){
                System.out.println("请输入进程标识符");
                int id = sc.nextInt();
                System.out.println("请输入进程的指令数(平均一条指令执行10毫秒)");
                int insNum = sc.nextInt();
                kernel.creatPCB(id,insNum);
            }

            if (select==2){
                kernel.undoAndBlock(1);
            }

            if (select==3){
                kernel.undoAndBlock(2);
            }

            if (select==4){
                System.out.println("请输入进程标识符");
                int id = sc.nextInt();
                kernel.wakePCB(id);
            }

            if (select==5){
                kernel.processorScheduling(1);
            }

            if (select==6){
                kernel.processorScheduling(2);
            }

            if (select==7){
                kernel.viewPCB();
            }

            if (select==8){
                break;
            }
        }
    }
}
