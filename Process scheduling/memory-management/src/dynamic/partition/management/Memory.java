package dynamic.partition.management;

import java.util.ArrayList;
import java.util.LinkedList;

public class Memory {
    /**
     * 内存大小
     */
    private int capacity;
    /**
     * 可用分区表
     */
    private ArrayList<Partition> availablePartition = new ArrayList<>();
    /**
     * 内存请求表
     */
    private ArrayList<Request> request = new ArrayList<>();
    /**
     * 内存分配表
     */
    private ArrayList<Partition> memoryAllocation = new ArrayList<>();


    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public ArrayList<Partition> getAvailablePartition() {
        return availablePartition;
    }

    public void setAvailablePartition(ArrayList<Partition> availablePartition) {
        this.availablePartition = availablePartition;
    }

    public ArrayList<Partition> getMemoryAllocation() {
        return memoryAllocation;
    }

    public void setMemoryAllocation(ArrayList<Partition> memoryAllocation) {
        this.memoryAllocation = memoryAllocation;
    }

    public ArrayList<Request> getRequest() {
        return request;
    }

    public void setRequest(ArrayList<Request> request) {
        this.request = request;
    }
}
