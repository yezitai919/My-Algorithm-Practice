package process.controlling.block02;

import java.util.LinkedList;

public class Memory {
    /**
     * 内存容量(单位：kb)
     */
    private int memoryCapacity = 128;
    /**
     * 内存剩余容量(单位：kb)
     */
    private int remainingCapacity = 128;
    /**
     * 内存剩余容量的起始地址
     */
    private int startingAddress = 1000;
    /**
     * 内存中的就绪队列
     */
    private LinkedList<PCB> readyQueue = new LinkedList<>();
    /**
     * 内存中的阻塞队列
     */
    private LinkedList<PCB> blockQueue = new LinkedList<>();

    public int getMemoryCapacity() {
        return memoryCapacity;
    }

    public int getRemainingCapacity() {
        return remainingCapacity;
    }

    public int getStartingAddress() {
        return startingAddress;
    }

    public LinkedList<PCB> getReadyQueue() {
        return readyQueue;
    }

    public LinkedList<PCB> getBlockQueue() {
        return blockQueue;
    }

    public void setMemoryCapacity(int memoryCapacity) {
        this.memoryCapacity = memoryCapacity;
    }

    public void setRemainingCapacity(int remainingCapacity) {
        this.remainingCapacity = remainingCapacity;
    }

    public void setReadyQueue(LinkedList<PCB> readyQueue) {
        this.readyQueue = readyQueue;
    }

    public void setBlockQueue(LinkedList<PCB> blockQueue) {
        this.blockQueue = blockQueue;
    }

    public void setStartingAddress(int startingAddress) {
        this.startingAddress = startingAddress;
    }
}
