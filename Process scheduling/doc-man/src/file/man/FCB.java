package file.man;
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
    private int blockNumber;

    public FCB(String name, int size, Date creationTime, int physicalAddress) {
        this.name = name;
        this.size = size;
        this.creationTime = creationTime;
        this.blockNumber = physicalAddress;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public int getBlockNumber() {
        return blockNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public void setBlockNumber(int blockNumber) {
        this.blockNumber = blockNumber;
    }
}
