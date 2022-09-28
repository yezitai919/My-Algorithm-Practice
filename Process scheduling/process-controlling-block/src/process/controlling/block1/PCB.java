package process.controlling.block1;

/**
 * 进程控制块
 */
public class PCB {
    /**
     * 进程标识符
     */
    private int id;
    /**
     *进程状态(0表示就绪态，1表示运行态，2表示阻塞态）
     */
    private int status;

    /**
     * 进程剩余指令数
     */
    private int remainingInsNum;

    public PCB(int id, int instructionsNum) {
        this.id = id;
        this.remainingInsNum = instructionsNum;
    }

    /**
     * 剩余指令数自减
     */
    public void reductionIns(){
        this.remainingInsNum--;
    }

    public int getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    public int getRemainingInsNum() {
        return remainingInsNum;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
