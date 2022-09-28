package process.controlling.block1;

import java.util.LinkedList;

/**
 * 内存
 */
public class Memory {
    /**
     * 内存中的就绪队列
     */
    private LinkedList<PCB> readyQueue = new LinkedList<>();
    /**
     * 内存中的阻塞队列
     */
    private LinkedList<PCB> blockQueue = new LinkedList<>();

    public LinkedList<PCB> getReadyQueue() {
        return readyQueue;
    }

    public LinkedList<PCB> getBlockQueue() {
        return blockQueue;
    }
}
