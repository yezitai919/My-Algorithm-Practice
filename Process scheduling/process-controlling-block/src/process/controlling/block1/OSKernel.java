package process.controlling.block1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 系统内核
 */
public class OSKernel {

    /**
     * 内存对象
     */
    private Memory mem = new Memory();

    /**
     * CPU对象
     */
    private CPU cpu = new CPU(mem);

    /**
     *  CPU线程池
     */
    private ExecutorService service = Executors.newFixedThreadPool(1);

    /**
     * 新建原语
     * @param id
     * @param instructionsNum
     */
    protected void creatPCB(int id,int instructionsNum){
        mem.getReadyQueue().offer(new PCB(id,instructionsNum));
        System.out.print(id+"号进程的PCB创建成功！");
    }

    /**
     * 撤销原语或阻塞原语
     */
    protected void undoAndBlock(int cpuIns){
        if (cpu.getInstructions()==0){
            System.out.println("当前CPU空闲！");
        }else {
            int i = cpu.getPrimitive();
            if (i!=0){
                System.out.println("系统正在执行"+(i+2)+"号指令！");
            }else {
                cpu.setPrimitive(cpuIns);
            }
        }
    }
    /**
     * 唤醒原语
     * @param id
     */
    protected void wakePCB(int id){
        if (mem.getBlockQueue().isEmpty()){
            System.out.println("当前无阻塞进程！");
        }else {
            int a = -1;
            for (int i = 0; i < mem.getBlockQueue().size(); i++) {
                if (mem.getBlockQueue().get(i).getId()==id){
                    a=i;
                    break;
                }
            }
            if (a<0){
                System.out.println(id+"号进程未阻塞或不存在！");
            }else {
                PCB pcb = mem.getBlockQueue().remove(a);
                pcb.setStatus(0);
                mem.getReadyQueue().offer(pcb);
                System.out.println(id+"号进程唤醒成功！");
            }
        }
    }

    /**
     * 处理器调度
     */
    protected void processorScheduling(int cpuIns){
        int i = cpu.getInstructions();
        if (i!=0){
            System.out.println("系统正在执行"+(i+4)+"号指令");
        }else if (mem.getReadyQueue().isEmpty()){
            int size = mem.getBlockQueue().size();
            if (size!=0){
                System.out.println("内存中无就绪进程，还剩"+size+"个阻塞进程！");
            }else {
                System.out.println("内存中无进程！");
            }
        }else {
            cpu.setInstructions(cpuIns);
            service.submit(cpu);
        }

    }

    /**
     * 执行一个进程
     */
    protected void executingAProcess(){
        PCB processingPCB = mem.getReadyQueue().poll();
        assert processingPCB != null;
        int id = processingPCB.getId();
        processingPCB.setStatus(1);
        while (processingPCB.getRemainingInsNum()!=0){
            //如果当前进程运行中被撤销原语撤销就结束方法，当前PCB直接被删除。
            if (cpu.getPrimitive()==1){
                System.out.println("当前正在执行的"+id+"号进程已撤销！");
                //指令归零
                cpu.setPrimitive(0);
                //如果是执行所有进程就等执行完再归零
                if (cpu.getInstructions() ==1){
                    cpu.setInstructions(0);
                }
                return;
            }
            //如果当前进程运行中被阻塞原语阻塞就将其放入阻塞队列再结束方法。
            if (cpu.getPrimitive()==2){
                processingPCB.setStatus(2);
                mem.getBlockQueue().offer(processingPCB);
                System.out.println("当前正在执行的"+id+"号进程已阻塞！");
                cpu.setPrimitive(0);
                if (cpu.getInstructions() ==1){
                    cpu.setInstructions(0);
                }
                return;
            }
            //cup执行一条指令,用时10ms，为了简化，执行条指令的过程不可再分割。
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            processingPCB.reductionIns();
        }
        //程序正常执行完成
        //如果是执行所有进程就不必打印每一个进程完成的信息了，不然会影响选择操作
        if (cpu.getInstructions() ==1){
            System.out.println(id+"号进程已完成！");
            cpu.setInstructions(0);
        }

    }

    /**
     * 执行所有进程
     */
    protected void executingAllProcess(){
        while (!mem.getReadyQueue().isEmpty()){
            executingAProcess();
        }
        int size = mem.getBlockQueue().size();
        if (size!=0){
            System.out.println("所有就绪进程执行完毕，还剩"+size+"个阻塞进程！");
        }else {
            System.out.println("所有进程执行完毕！");
        }
        cpu.setInstructions(0);
    }

    /**
     * 查看PCB
     */
    protected void viewPCB(){
        int i = cpu.getInstructions();
        if (i!=0){
            System.out.println("系统正在执行"+(i+4)+"号指令");
        }else {
            if (mem.getReadyQueue().isEmpty()){
                System.out.println("就绪队列为空！");
            }else {
                System.out.print("就绪队列：[  ");
                for (PCB pcb : mem.getReadyQueue()) {
                    System.out.print("(标识符："+pcb.getId()+"，状态："+pcb.getStatus()+"，剩余指令："+pcb.getRemainingInsNum()+")  ");
                }
                System.out.println("]");
            }
            if (mem.getBlockQueue().isEmpty()){
                System.out.println("阻塞队列为空！");
            }else {
                System.out.print("阻塞队列：[  ");
                for (PCB pcb : mem.getReadyQueue()) {
                    System.out.print("(标识符："+pcb.getId()+"，状态："+pcb.getStatus()+"，剩余指令："+pcb.getRemainingInsNum()+")  ");
                }
                System.out.println("]");
            }
        }
    }
}
