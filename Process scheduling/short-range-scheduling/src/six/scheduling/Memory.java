package six.scheduling;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class Memory {
    /**
     * 就绪队列1
     */
    private LinkedList<PCB> ready1 = new LinkedList<>();
    /**
     * 就绪队列2
     */
    private LinkedList<PCB> ready2 = new LinkedList<>();
    /**
     * 就绪队列3
     */
    private PriorityQueue<PCB> ready3 = new PriorityQueue<>(Comparator.comparingInt(PCB::getServiceTime));
    /**
     * 就绪队列4
     */
    private PriorityQueue<PCB> ready4 = new PriorityQueue<>(Comparator.comparingInt(PCB::getPriority));
    /**
     * 就绪队列5
     */
    private PriorityQueue<PCB> ready5 = new PriorityQueue<>(Comparator.comparingInt(PCB::getPriority));
    /**
     * 反馈队列1 (就绪队列6)
     */
    private LinkedList<PCB> feedback1 = new LinkedList<>();
    /**
     * 反馈队列2 (就绪队列7)
     */
    private LinkedList<PCB> feedback2 = new LinkedList<>();
    /**
     * 反馈队列3 (就绪队列8)
     */
    private LinkedList<PCB> feedback3 = new LinkedList<>();
    /**
     * 结束队列1
     */
    private LinkedList<PCB> ending1 = new LinkedList<>();
    /**
     * 结束队列2
     */
    private LinkedList<PCB> ending2 = new LinkedList<>();
    /**
     * 结束队列3
     */
    private LinkedList<PCB> ending3 = new LinkedList<>();
    /**
     * 结束队列4
     */
    private LinkedList<PCB> ending4 = new LinkedList<>();
    /**
     * 结束队列5
     */
    private LinkedList<PCB> ending5 = new LinkedList<>();
    /**
     * 结束队列6
     */
    private LinkedList<PCB> ending6 = new LinkedList<>();

    public LinkedList<PCB> getReady1() {
        return ready1;
    }

    public LinkedList<PCB> getEnding1() {
        return ending1;
    }

    public PriorityQueue<PCB> getReady3() {
        return ready3;
    }

    public void setReady3(PriorityQueue<PCB> ready3) {
        this.ready3 = ready3;
    }

    public PriorityQueue<PCB> getReady4() {
        return ready4;
    }

    public void setReady4(PriorityQueue<PCB> ready4) {
        this.ready4 = ready4;
    }

    public LinkedList<PCB> getReady2() {
        return ready2;
    }

    public void setReady2(LinkedList<PCB> ready2) {
        this.ready2 = ready2;
    }

    public LinkedList<PCB> getEnding2() {
        return ending2;
    }

    public void setEnding2(LinkedList<PCB> ending2) {
        this.ending2 = ending2;
    }

    public LinkedList<PCB> getEnding3() {
        return ending3;
    }

    public void setEnding3(LinkedList<PCB> ending3) {
        this.ending3 = ending3;
    }

    public LinkedList<PCB> getEnding4() {
        return ending4;
    }

    public void setEnding4(LinkedList<PCB> ending4) {
        this.ending4 = ending4;
    }

    public LinkedList<PCB> getFeedback1() {
        return feedback1;
    }

    public void setFeedback1(LinkedList<PCB> feedback1) {
        this.feedback1 = feedback1;
    }

    public LinkedList<PCB> getFeedback2() {
        return feedback2;
    }

    public void setFeedback2(LinkedList<PCB> feedback2) {
        this.feedback2 = feedback2;
    }

    public LinkedList<PCB> getFeedback3() {
        return feedback3;
    }

    public void setFeedback3(LinkedList<PCB> feedback3) {
        this.feedback3 = feedback3;
    }

    public LinkedList<PCB> getEnding6() {
        return ending6;
    }

    public void setEnding6(LinkedList<PCB> ending6) {
        this.ending6 = ending6;
    }

    public PriorityQueue<PCB> getReady5() {
        return ready5;
    }

    public void setReady5(PriorityQueue<PCB> ready5) {
        this.ready5 = ready5;
    }

    public LinkedList<PCB> getEnding5() {
        return ending5;
    }

    public void setEnding5(LinkedList<PCB> ending5) {
        this.ending5 = ending5;
    }
}
