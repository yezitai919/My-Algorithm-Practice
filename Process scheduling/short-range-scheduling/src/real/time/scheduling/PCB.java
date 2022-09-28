package real.time.scheduling;

public class PCB implements Cloneable{
    /**
     * 进程名
     */
    private String id;

    /**
     * 优先级
     */
    private int priority;

    /**
     * 处理时间
     */
    private int processingTime;
    /**
     * 剩余处理时间
     */
    private int remainingProcessingT;

    /**
     * 到达时间
     */
    private int arrivalTime;
    /**
     * 开始时限
     */
    private int startingTimeLimit;
    /**
     * 结束时限
     */
    private int completionTimeLimit;

    /**
     * 周期
     */
    private int cycle;

    /**
     * 周期数
     */
    private int cycleNum;


    public PCB(String id, int processingTime, int cycle) {
        this.id = id;
        this.remainingProcessingT = processingTime;
        this.processingTime = processingTime;
        this.cycle = cycle;
        upData();
    }

    protected void upData(){
        this.arrivalTime = this.cycle*this.cycleNum;
        this.cycleNum++;
        this.completionTimeLimit = this.cycle*this.cycleNum;
        this.remainingProcessingT = this.processingTime;
        this.startingTimeLimit = this.completionTimeLimit-this.remainingProcessingT;
        this.priority = this.completionTimeLimit;
    }
    public static double add(double a,double b){
        return a+b;
    }

    @Override
    public Object clone(){
        PCB pcb = null;
        try {
            pcb = (PCB) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return pcb;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getRemainingProcessingT() {
        return remainingProcessingT;
    }

    public void setRemainingProcessingT(int remainingProcessingT) {
        this.remainingProcessingT = remainingProcessingT;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getStartingTimeLimit() {
        return startingTimeLimit;
    }

    public void setStartingTimeLimit(int startingTimeLimit) {
        this.startingTimeLimit = startingTimeLimit;
    }

    public int getCompletionTimeLimit() {
        return completionTimeLimit;
    }

    public void setCompletionTimeLimit(int completionTimeLimit) {
        this.completionTimeLimit = completionTimeLimit;
    }

    public int getCycle() {
        return cycle;
    }

    public void setCycle(int cycle) {
        this.cycle = cycle;
    }

    public int getCycleNum() {
        return cycleNum;
    }

    public void setCycleNum(int cycleNum) {
        this.cycleNum = cycleNum;
    }
}
