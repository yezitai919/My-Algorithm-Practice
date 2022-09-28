package file.management;

import java.util.ArrayList;
import java.util.Date;

public class FCB {
    /**
     * 文件名
     */
    private String name;
    /**
     * 文件大小
     */
    private int size;
    /**
     * 创建时间
     */
    private Date creationTime;
    /**
     * 物理块号
     */
    private ArrayList<int[]> blockNumber = new ArrayList<>();


    public FCB(String name, int size, Date creationTime, ArrayList<int[]> blockNumber) {
        this.name = name;
        this.size = size;
        this.creationTime = creationTime;
        this.blockNumber = blockNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public ArrayList<int[]> getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(ArrayList<int[]> blockNumber) {
        this.blockNumber = blockNumber;
    }
}
