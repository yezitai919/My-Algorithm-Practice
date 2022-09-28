package process.controlling.block01;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * 单处理器测试
 */
public class SingleProcessor01 {
    /**
     * 处理器状态，0为空闲，1为负载
     */
    private static int processorStatus = 0;
    /**
     * 正在处理的进程
     */
    private static PCB01 processingPCB;
    /**
     * 就绪队列
     */
    private static Queue<PCB01> readyQueue = new LinkedList<>();
    /**
     * 阻塞队列
     */
    private static Queue<PCB01> blockedQueue = new LinkedList<>();

    /**
     * 扫描仪对象
     */
    private static Scanner sc=new Scanner(System.in);
    public static void main(String[] args) {
        while (true){
            System.out.println("输入数字选择以下操作：");
            System.out.println("1.创建一个进程，2.开始执行一个进程，3.撤销一个进程，4.阻塞一个进程，5.唤醒一个进程，6.结束");
            int select = sc.nextInt();
            if (select==1){
                createProcess(sc);
            }else if (select==2){
                processingProcess();
            }else if (select==3){
                undoProcess();
            }else if (select==4){
                blockingProcess();
            }else if (select==5){
                wakeProcess(sc);
            }else {
                break;
            }
        }
    }

    /**
     * 创建一个进程
     * @param sc 扫描仪对象
     */
    private static void createProcess(Scanner sc){
        PCB01 pcb = new PCB01();
        System.out.println("请输入进程标识符");
        pcb.setId(sc.nextInt());
        System.out.println("请输入进程需要的cpu时间(单位：0.1秒)");
        pcb.setInstructionsNum(sc.nextInt());
        System.out.println(pcb.getId()+"号进程创建成功！");
        readyQueue.offer(pcb);
        System.out.println(pcb.getId()+"号进程已加入就绪队列！");
    }

    /**
     * 开始执行一个进程
     */
    private static void processingProcess(){
        if (readyQueue.isEmpty()){
            System.out.println("就绪队列为空！");
        }else {
            if (processorStatus == 1){
                long s = processingPCB.getStarPT();
                long c = System.currentTimeMillis();
                int t = processingPCB.getInstructionsNum();
                int ct = processingPCB.getCurrPT();
                if ((int)((c-s)/100)<(t-ct)){
                    System.out.println("CPU正在执行"+processingPCB.getId()+"号进程，无法执行新进程！");
                }else {
                    processorStatus = 0;
                    processingPCB = null;
                }
            }

            if (processorStatus == 0 ){
                processorStatus = 1;
                processingPCB = readyQueue.poll();
                assert processingPCB != null;
                processingPCB.setStatus(1);
                System.out.println("开始执行"+processingPCB.getId()+"号进程！");
                processingPCB.setStarPT(System.currentTimeMillis());
            }
        }
    }

    /**
     * 撤销一个进程
     */
    private static void undoProcess(){
        if (processorStatus==0){
            System.out.println("当前CPU空闲！");
        }else {
            long s = processingPCB.getStarPT();
            long c = System.currentTimeMillis();
            int t = processingPCB.getInstructionsNum();
            int ct = processingPCB.getCurrPT();
            processorStatus=0;
            if ((int)((c-s)/100)<(t-ct)){
                System.out.println("正在执行的"+ processingPCB.getId()+"号进程已撤销！");
            }else {
                System.out.println("当前CPU空闲！");
            }
            processingPCB = null;
        }
    }

    /**
     * 阻塞一个进程
     */
    private static void blockingProcess(){
        if (processorStatus == 1){
            long s = processingPCB.getStarPT();
            long c = System.currentTimeMillis();
            int t = processingPCB.getInstructionsNum();
            int ct = processingPCB.getCurrPT();
            int cPT = (int)((c-s)/100);
            if (cPT<(t-ct)){
                processingPCB.setStatus(2);
                processingPCB.setCurrPT(ct+cPT);
                blockedQueue.offer(processingPCB);
            }else {
                processorStatus = 0;
            }
            processingPCB = null;
        }
        if (processorStatus==0){
            System.out.println("当前CPU空闲！");
        }
    }

    /**
     * 唤醒一个进程
     * @param sc 扫描仪对象
     */
    private static void wakeProcess(Scanner sc){
        if (blockedQueue.isEmpty()){
            System.out.println("阻塞队列为空！");
        }else {
            System.out.println("请输入要唤醒的进程的标识符");
            int id = sc.nextInt();
            PCB01 pcb01 = new PCB01();
            for (PCB01 pcb02 : blockedQueue) {
                if (pcb02.getId()==id){
                    pcb01=pcb02;
                    break;
                }
            }
            readyQueue.offer(pcb01);
            Iterator it=blockedQueue.iterator();
            while(it.hasNext()){
                if(pcb01.equals(it.next())){
                    it.remove();
                }
            }
        }
    }
}
