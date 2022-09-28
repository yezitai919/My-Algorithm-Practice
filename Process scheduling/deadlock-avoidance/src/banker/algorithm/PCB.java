package banker.algorithm;

public class PCB implements Cloneable{
    /**
     * 进程名
     */
    private int id;
    /**
     * 进程的状态(简化版)，0是运行中，1是运行结束
     */
    private int status;
    /**
     * 进程需要都资源数
     */
    private int demand;
    /**
     * 进程已有的资源数
     */
    private int Existing;


    public PCB(int id, int demand) {
        this.id = id;
        this.demand = demand;
    }

    public PCB(int id, int demand, int existing) {
        this.id = id;
        this.demand = demand;
        Existing = existing;
    }

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

    public int getStatus() {
        return status;
    }
    public int getDemand() {
        return demand;
    }

    public int getExisting() {
        return Existing;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    public void setDemand(int demand) {
        this.demand = demand;
    }

    public void setExisting(int existing) {
        Existing = existing;
    }



}
