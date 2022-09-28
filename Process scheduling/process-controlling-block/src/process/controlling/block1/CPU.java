package process.controlling.block1;

/**
 * 处理器
 */
public class CPU implements Runnable{
    /**
     * cpu指令，1是执行一个进程，2是执行所有进程，
     */
    private int instructions;

    /**
     * 系统原语指令，1是撤销一个进程，2是阻塞一个进程
     */
    private int primitive;

    /**
     * 内存对象
     */
    private final Memory mem;

    /**
     * 内核对象
     */
    private OSKernel kernel = new OSKernel();

    public CPU(Memory mem) {
        this.mem = mem;
    }

    /**
     * cpu类就是接受命令干活，不需要多余的东西
     */
    @Override
    public void run() {
        if (instructions ==1){
            kernel.executingAProcess();
        }
        if (instructions ==2){
            kernel.executingAllProcess();
        }
    }

    public int getInstructions() {
        return instructions;
    }

    public int getPrimitive() {
        return primitive;
    }

    public void setInstructions(int instructions) {
        this.instructions = instructions;
    }

    public void setPrimitive(int primitive) {
        this.primitive = primitive;
    }
}
