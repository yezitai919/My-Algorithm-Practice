package process.controlling.block02;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 操作系统内核
 */
public class OSKernel {
    /**
     * 外存对象
     */
    private ExternalMemory eme = new ExternalMemory();
    /**
     * 内存对象
     */
    private Memory me = new Memory();
    /**
     *  CPU线程池
     */
    private ExecutorService service1 = Executors.newFixedThreadPool(1);
    /**
     * CPU对象
     */
    private CPU cpu = new CPU(this);
    /**
     * 指令
     */
    private int instructions;

    /**
     * 新建原语
     * @param id
     * @param size
     */
    protected void creatPCB(int id,int size){
        int address = me.getStartingAddress();
        PCB pcb = new PCB(id,size,address);
        me.setStartingAddress(address+size+1);
        System.out.print(id+"号进程的PCB创建成功！");
        if (me.getRemainingCapacity()>=size){
            me.getReadyQueue().add(pcb);
            me.setRemainingCapacity(me.getRemainingCapacity()-size);
            System.out.println("PCB已放入外存的新建队列中！");
        }else {
            eme.getNewQueue().add(pcb);
            System.out.println("PCB已放在内存的就绪队列中！");
        }
    }

    /**
     * 撤销原语
     * @param id
     */
    protected void undoPCB(int id){
        if (instructions==4){
            cpu.setUndoId(id);
        }else if (instructions==0){

        }else {

        }
    }

    private void searchPCB(int id,int ins){
        if (!eme.getEndingQueue().isEmpty()){
            for (PCB pcb : eme.getEndingQueue()) {
                if (pcb.getId()==id){
                    System.out.println(id+"号进程已结束！");
                    return;
                }
            }
        }

        if (!eme.getNewQueue().isEmpty()){
            int size = eme.getNewQueue().size();
            for (int i = 0; i < size; i++) {
                PCB pcb =
            }
        }
    }

    /**
     * 阻塞原语
     * @param id
     */
    protected void blockPCB(int id){

    }

    /**
     * 唤醒原语
     * @param id
     */
    protected void wakePCB(int id){
        if (me.getBlockQueue().isEmpty()){
            System.out.println("当前阻塞队列为空，无可唤醒进程！");
        }else {
            PCB pcb = me.getBlockQueue().poll();
            me.getReadyQueue().offer(pcb);
            System.out.println("成功唤醒"+pcb.getId()+"号进程！");
        }
    }




    /**
     * 加载进程
     */
    protected void loadingPCB(){
        if (eme.getNewQueue().isEmpty()) {
            System.out.println("当前外存中的新建队列为空！");
        }else {
            int n = 0;
            while (true){
                if (eme.getNewQueue().isEmpty()){
                    System.out.println("外存中的新建进程共"+n+"个全部加载完毕！");
                    break;
                }
                int size = eme.getNewQueue().peek().getSize();
                if (size <= me.getRemainingCapacity()){
                    me.getReadyQueue().offer(eme.getNewQueue().poll());
                    me.setRemainingCapacity(me.getRemainingCapacity()-size);
                    n++;
                }else {
                    if (n==0){
                        System.out.println("当前内存容量不足，无法再加载新建进程！");
                    }else {
                        System.out.println("当前已加载"+n+"个新建进程，还剩"+eme.getNewQueue().size()+"个因内存不足而无法加载！");
                    }
                    break;
                }
            }
        }
    }

    /**
     * 执行进程
     */
    protected void executingPCB(int ins){
        if (me.getReadyQueue().isEmpty() && eme.getNewQueue().isEmpty()){
            if (!me.getBlockQueue().isEmpty()){
                int n = me.getBlockQueue().size();
                System.out.println("系统已无可执行进程，还有"+n+"个阻塞进程等待唤醒！");
            }else if (!eme.getEndingQueue().isEmpty()){
                int n = eme.getEndingQueue().size();
                System.out.println("系统所有进程(共"+n+"个)都已执行完毕！");
            }else {
                System.out.println("系统尚未创建进程！");
            }
        }else if (cpu.getInstructions()==2){
            System.out.println("当前CPU正在按优先级顺序执行所有进程！");
        }else {
            cpu.setInstructions(ins);
            service1.submit(cpu);
            System.out.println("指令已下达！");
        }
    }

    /**
     * @param kb
     */
    protected void setMemoryCapacity(int kb){
        me.setMemoryCapacity(kb);
    }

    /**
     * @param ms
     */
    protected void setTimeSlizce(int ms){
        cpu.setTimeSlizce(ms);
    }

    public ExternalMemory getEme() {
        return eme;
    }

    public Memory getMe() {
        return me;
    }

    public ExecutorService getService1() {
        return service1;
    }

    public CPU getCpu() {
        return cpu;
    }

    public void setEme(ExternalMemory eme) {
        this.eme = eme;
    }

    public void setMe(Memory me) {
        this.me = me;
    }

    public void setService1(ExecutorService service1) {
        this.service1 = service1;
    }

    public void setCpu(CPU cpu) {
        this.cpu = cpu;
    }
}
