package process.controlling.block01;

/**
 * 进程控制块
 */
public class PCB01 {
    /**
     * 进程标识符
     */
    private int id;
    /**
     *进程状态(0表示创建态，1表示就绪态，2表示运行态，3表示阻塞态，4表示结束态）
     */
    private int status;
    /**
     * 进程的指令数(平均每条指令执行10毫秒)
     */
    private int instructionsNum;
    /**
     * 进程开始的时间
     */
    private long starPT;
    /**
     * 进程已占用的CPU时间
     */
    private int currPT;

    public PCB01() {
    }

    public int getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    public int getInstructionsNum() {
        return instructionsNum;
    }

    public long getStarPT() {
        return starPT;
    }

    public int getCurrPT() {
        return currPT;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setInstructionsNum(int instructionsNum) {
        this.instructionsNum = instructionsNum;
    }

    public void setStarPT(long starPT) {
        this.starPT = starPT;
    }

    public void setCurrPT(int currPT) {
        this.currPT = currPT;
    }
}
