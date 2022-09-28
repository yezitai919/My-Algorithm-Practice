package banker.algorithm;

import java.util.Scanner;

/**
 * 资源分配算法
 */
public class OSInterface {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        State state = new State();
        OSKernel algo = new OSKernel();
        while (true){
            System.out.println("请输入数字选择以下操作：");
            System.out.println("1.设置资源总数  2.新建进程  3.提出资源请求  4.资源分配情况显示  5.结束选择操作");
            int select = sc.nextInt();
            if (select==1){
                System.out.println("请输入资源总数(重复设置时只能增大，减小会导致系统资源重置。)");
                int a = sc.nextInt();
                int b = state.getResource();
                if (a<b){
                    state = new State(a);
                    System.out.println("设置成功！ 系统资源分配状态已重置！");
                }else {
                    state.setResource(a);
                    state.setAvailable(a);
                    System.out.println("设置成功！");
                }

            }else if (select==2){
                System.out.println("请输入进程名(整数)：");
                int a = sc.nextInt();
                System.out.println("请输入进程需要的资源数(整数)：");
                int b = sc.nextInt();
                state.getAllocation().add(new PCB(a,b));
            }else if (select==3){
                System.out.println("请输入进程名(整数)：");
                int a = sc.nextInt();
                System.out.println("请输入请求的资源数(整数)：");
                int b = sc.nextInt();
                algo.resourceAllocation(new int[]{a,b},state);
            }else if (select==4){
                System.out.println("系统拥有资源数："+state.getResource()+"  系统剩余资源数："+state.getAvailable());
                for (PCB pcb : state.getAllocation()) {
                    System.out.print("进程名："+pcb.getId()+"  进程状态："+pcb.getStatus()+
                            "  已占有资源数："+pcb.getExisting()+"  还需申请资源数：");
                    if (pcb.getStatus()==0){
                        System.out.println((pcb.getDemand()-pcb.getExisting()));
                    }else {
                        System.out.println(0);
                    }
                }
            }else if (select==5){
                System.out.println("系统已结束！");
                break;
            }/*else {  测试用 快速输入课本的一组数据
                state = new State(10);
                state.setAvailable(2);
                state.getAllocation().add(new PCB(1,8,4));
                state.getAllocation().add(new PCB(2,4,2));
                state.getAllocation().add(new PCB(3,9,2));
                System.out.println("设置成功！");
            }*/
        }
    }

}