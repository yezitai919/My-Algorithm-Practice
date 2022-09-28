package banker.algorithm;

import java.util.ArrayList;

public class State implements Cloneable{
    /**
     * 系统中这种资源的总量
     */
    private int resource;

    /**
     * 未分配给进程的每种资源的总量
     */
    private int available;

    /**
     * 进程i对资源的需求量以及当前已分配给进程i的资源的数量
     */
    private ArrayList<PCB> allocation = new ArrayList<>();

    public State(int resource) {
        this.resource = resource;
        this.available = resource;
    }

    public State() {
    }

    public int getResource() {
        return resource;
    }

    public int getAvailable() {
        return available;
    }

    public ArrayList<PCB> getAllocation() {
        return allocation;
    }

    public void setResource(int resource) {
        this.resource = resource;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public void setAllocation(ArrayList<PCB> allocation) {
        this.allocation = allocation;
    }

    @Override
    public Object clone()  {
        State sta = null;
        try {
            sta = (State) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
       /* for (PCB pcb : sta.getAllocation()) {
            pcb = (PCB) pcb.clone();
        }*/
        return sta;
    }
}
