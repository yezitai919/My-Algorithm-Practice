package process.controlling.block02;

public class PCB {
    /**
     * 进程标识符
     */
    private int id;
    /**
     *进程状态(0表示创建态，1表示就绪态，2表示运行态，3表示阻塞态，4表示结束态）
     */
    private int status;
    /**
     * 进程的大小(单位kb，平均每kb代表一条指令，平均每条指令执行1毫秒)
     */
    private int size;
    /**
     * 进程第一条指令的地址
     */
    private int startingAddress;

    /**
     * 程序计数器，记录下一条指令的地址。(假设第一条指令地址是10000，下一条指令地址就是10001)
     */
    private int programCounter;

    public PCB(int id, int size, int startingAddress) {
        this.id = id;
        this.size = size;
        this.startingAddress = startingAddress;
        this.programCounter = startingAddress;
    }

    public int getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    public int getSize() {
        return size;
    }

    public int getStartingAddress() {
        return startingAddress;
    }

    public int getProgramCounter() {
        return programCounter;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setStartingAddress(int startingAddress) {
        this.startingAddress = startingAddress;
    }

    public void setProgramCounter(int programCounter) {
        this.programCounter = programCounter;
    }
}
