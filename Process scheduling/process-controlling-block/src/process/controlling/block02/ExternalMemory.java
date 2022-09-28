package process.controlling.block02;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class ExternalMemory {
    /**
     * 外存中的新建队列
     */
    private LinkedList<PCB> newQueue = new LinkedList<>();
    /**
     * 外存中的结束队列
     */
    private LinkedList<PCB> endingQueue = new LinkedList<>();

    public LinkedList<PCB> getNewQueue() {
        return newQueue;
    }

    public LinkedList<PCB> getEndingQueue() {
        return endingQueue;
    }

    public void setNewQueue(LinkedList<PCB> newQueue) {
        this.newQueue = newQueue;
    }

    public void setEndingQueue(LinkedList<PCB> endingQueue) {
        this.endingQueue = endingQueue;
    }
}
