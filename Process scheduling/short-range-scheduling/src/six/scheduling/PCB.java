package six.scheduling;

public class PCB implements Cloneable{
    /**
     * 进程名
     */
    private int id;

    /**
     * 优先级
     */
    private int priority;
    /**
     * 开始等待的时刻
     */
    private int startWaitMoment;
    /**
     * 到达时间
     */
    private int arrivalTime;

    /**
     * 服务时间
     */
    private int serviceTime;

    /**
     * 剩余服务时间
     */
    private int remainingSerT;

    /**
     * 开始时间
     */
    private int startTime;

    /**
     * 完成时间
     */
    private int completionTime;

    /**
     * 周转时间
     */
    private int turnaroundTime;
    /**
     * 带权周转时间
     */
    private double normalizedTurnaroundT;

    public PCB(int id, int priority, int serviceTime) {
        this.id = id;
        this.priority = priority;
        this.serviceTime = serviceTime;
        this.remainingSerT = serviceTime;
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

    public int getId() {
        return id;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getCompletionTime() {
        return completionTime;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public double getNormalizedTurnaroundT() {
        return normalizedTurnaroundT;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public void setCompletionTime(int completionTime) {
        this.completionTime = completionTime;
    }
    public void countTurnaroundTime(){
        this.turnaroundTime = this.completionTime-this.arrivalTime;
    }
    public void countNormalizedTurnaroundT(){
        this.normalizedTurnaroundT = (double)this.turnaroundTime/this.serviceTime;
    }
    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

    public void setNormalizedTurnaroundT(double normalizedTurnaroundT) {
        this.normalizedTurnaroundT = normalizedTurnaroundT;
    }

    public int getRemainingSerT() {
        return remainingSerT;
    }

    /**
     * 重写方法：原值减去输入的参数
     * @param remainingSerT 要减去的值
     */
    public void setRemainingSerT(int remainingSerT) {
        this.remainingSerT -= remainingSerT;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getStartWaitMoment() {
        return startWaitMoment;
    }

    public void setStartWaitMoment(int startWaitMoment) {
        this.startWaitMoment = startWaitMoment;
    }
}
