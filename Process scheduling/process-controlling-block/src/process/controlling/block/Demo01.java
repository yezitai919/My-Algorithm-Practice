package process.controlling.block;

import java.util.Scanner;

/**
 * 实验测试类
 */
public class Demo01 {
    //定义系统参数
    public static final int pronum=3;//系统中进程总数,,进程总数不固定，任意，就绪队列阻塞队列用链表实现，依次无所谓
    public static final int time=50;//时间片大小
    //进程创建函数
    public boolean create(){
        return true;
    }
    //进程调度函数
    public boolean scheduling(){
        return true;
    }
    //主进程，系统初始化
    public static void main(String[] args){
        //创建进程就绪对列
        ReadyQueue<PCB> rq=new ReadyQueue<PCB>();
        //创建进程阻塞队列
        BlockedQueue<PCB> bq=new BlockedQueue<PCB>();
        /*
        执行流程
        1录入进程，为进程保存pcb信息
         */
        Scanner sc=new Scanner(System.in);
        System.out.println("进程标识信息:请输入进程编号(进程标识符)，最多可以创建10个进程");
        for(int i=0;i<pronum;i++){
            PCB p=new PCB();
            System.out.println("请输入数字零进入系统:");
            int x=sc.nextInt();
            System.out.println("请按照相应提示输入进程现场信息:");
            System.out.println("请输入进程标识符id的信息");
            int id=sc.nextInt();
            p.setId(id);
            //进程状态(1表示运行态，2表示就绪态，3表示阻塞态）
            int status=2;
            p.setStatus(status);
            System.out.println("请输入通用寄存器ax的内容");
            int ax=sc.nextInt();
            p.setAx(ax);
            System.out.println("请输入通用寄存器bx的内容");
            int bx=sc.nextInt();
            p.setBx(bx);
            System.out.println("请输入通用寄存器cx的内容");
            int cx=sc.nextInt();
            p.setCx(cx);
            System.out.println("请输入通用寄存器dx的内容");
            int dx=sc.nextInt();
            p.setDx(dx);
            System.out.println("请输入程序计数器pc的内容");
            int pc=sc.nextInt();
            p.setPc(pc);
            System.out.println("请输入程序状态字psw的内容");
            int psw=sc.nextInt();
            p.setPsw(psw);
            System.out.println("请输入下一个pcb的位置信息");
            int next=sc.nextInt();
            p.setNext(next);
            System.out.println("请输入进程执行所需的总时间");
            int execute_time=sc.nextInt();
            p.setExecute_time(execute_time);
            // System.out.println(p);
            System.out.println("已经成功为进程建立了进程控制块");
            System.out.println(p.toString());
            System.out.println("该进程已进入就绪队列");
            rq.addAtRear(p);
            System.out.println("查看就绪队列中现有进程"+rq.toString());
        }
        System.out.println("给进程分配cpu");
        PCB pz=rq.removeFromFront();
        pz.setArrival_time(pz.getArrival_time()+time);
        pz.setStatus(3);
        bq.addAtRear(pz);
        while(!rq.isEmpty()){
            PCB p0=rq.removeFromFront();
            p0.setArrival_time(p0.getArrival_time()+time);
            p0.setStatus(1);
            if(p0.getExecute_time()>=p0.getArrival_time()){
                p0.setStatus(2);
                rq.addAtRear(p0);
            }
            System.out.println("查看就绪队列中现有进程"+rq.toString());
        }
        System.out.println("就绪队列中进程执行完毕");
        System.out.println("查看阻塞队列中现有进程"+bq.toString());
        System.out.println("将进程从阻塞队列中移除，转入相应的就绪队列中");
        PCB p0=bq.removeFromFront();
        System.out.println("查看阻塞队列中现有进程"+bq.toString());
        p0.setStatus(2);
        rq.addAtRear(p0);
        System.out.println("查看就绪队列中现有进程"+rq.toString());
        while(!rq.isEmpty()){
            PCB px=rq.removeFromFront();
            px.setArrival_time(px.getArrival_time()+time);
            px.setStatus(1);
            if(px.getExecute_time()>=px.getArrival_time()){
                px.setStatus(2);
                rq.addAtRear(px);
            }
            System.out.println("查看就绪队列中现有进程"+rq.toString());
        }
        System.out.println("查看就绪队列中现有进程"+rq.toString());
        System.out.println("所有进程执行完毕");
    }
}
