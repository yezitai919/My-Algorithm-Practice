package process.controlling.block02;

public class CPU implements Runnable{
    /**
     * 处理器状态，0为空闲，1为负载
     */
    private int status = 0;
    /**
     * 处理器时间片，(单位：ms)
     */
    private int timeSlizce = 50;
    /**
     * 操作系统内核对象(获取系统资源)
     */
    private OSKernel oSk;
    /**
     * 执行指令编号
     */
    private int instructions;

    /**
     * 当前CPU执行的进程
     */
    private PCB currPCB;

    /**
     * 将被阻塞的进程的标识符
     */
    private int blockId;
    /**
     * 将被撤销的进程的标识符
     */
    private int undoId;

    public CPU(OSKernel oSk) {
        this.oSk = oSk;
    }

    @Override
    public void run() {
        if (instructions==1){
            executingOne();
        }
        if (instructions==2){
            executingAll();
        }
    }

    /**
     * 指令1：执行一个进程
     */
    private void executingOne(){
        currPCB = oSk.getMe().getReadyQueue().poll();
        currPCB.setStatus(2);
        long s = System.currentTimeMillis();
        //当前进程剩余的执行时间
        int remainingIns = currPCB.getSize()-(currPCB.getProgramCounter()-currPCB.getStartingAddress());
        //已执行的指令数
        int executedIns = 0;
        while (true){
            int status = currPCB.getStatus();
            if (status==2){
                long c = System.currentTimeMillis();
                if ((c-s)>executedIns+1){
                    executedIns=(int)(c-s);
                    currPCB.setProgramCounter(currPCB.getProgramCounter()+1);
                }
                if (executedIns==remainingIns){
                    currPCB.setStatus(4);
                    oSk.getEme().getEndingQueue().offer(currPCB);
                    currPCB = null;
                    instructions=0;
                    break;
                }
                if (executedIns==timeSlizce){
                    currPCB.setStatus(1);
                    oSk.getMe().getReadyQueue().offer(currPCB);
                    currPCB = null;
                    instructions=0;
                    break;
                }
            }else {
                int size = currPCB.getSize();
                int executed = currPCB.getProgramCounter()-currPCB.getStartingAddress();
                if (status==3){
                    oSk.getMe().getBlockQueue().offer(currPCB);
                    System.out.println("当前正在执行的"+currPCB.getId()+"号进程已阻塞!"+"(完成度："+executed+"/"+size+")");
                }
                if (status==4){
                    oSk.getEme().getEndingQueue().offer(currPCB);
                    System.out.println("当前正在执行的"+currPCB.getId()+"号进程已撤销!"+"(完成度："+executed+"/"+size+")");
                }
                currPCB = null;
                instructions=0;
                break;
            }
        }
    }

    /**
     * 指令2：执行所有进程
     */
    private void executingAll(){

        /*while (true){
            while (!oSk.getMe().getReadyQueue().isEmpty()) {
                PCB pcb = oSk.getMe().getReadyQueue().poll();
                assert pcb != null;
                executingOne(pcb);
            }
            if (!oSk.getEme().getNewQueue().isEmpty()){
                oSk.loadingPCB();
            }else {
                break;
            }
        }*/
    }

    public int getStatus() {
        return status;
    }

    public int getTimeSlizce() {
        return timeSlizce;
    }

    public OSKernel getoSk() {
        return oSk;
    }

    public int getInstructions() {
        return instructions;
    }

    public PCB getCurrPCB() {
        return currPCB;
    }

    public int getBlockId() {
        return blockId;
    }

    public int getUndoId() {
        return undoId;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setTimeSlizce(int timeSlizce) {
        this.timeSlizce = timeSlizce;
    }

    public void setoSk(OSKernel oSk) {
        this.oSk = oSk;
    }

    public void setInstructions(int instructions) {
        this.instructions = instructions;
    }

    public void setCurrPCB(PCB currPCB) {
        this.currPCB = currPCB;
    }

    public void setBlockId(int blockId) {
        this.blockId = blockId;
    }

    public void setUndoId(int undoId) {
        this.undoId = undoId;
    }
}
