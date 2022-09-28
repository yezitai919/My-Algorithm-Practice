package dynamic.partition.management;

public class Partition {
    /**
     * 分区号
     */
    private int id;
    /**
     * 分区大小
     */
    private int size;
    /**
     * 分区开始地址
     */
    private int startingAddress;

    /**
     * 被占用的进程id，没被占用时为0。
     */
    private int pcbId;

    public Partition(int id, int size, int startingAddress) {
        this.id = id;
        this.size = size;
        this.startingAddress = startingAddress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSize() {
        return size;
    }

    public void reduceSize(int size) {
        this.size -= size;
    }
    public void addSize(int size){
        this.size += size;
    }

    public int getStartingAddress() {
        return startingAddress;
    }

    public void addStartingAddress(int startingAddress) {
        this.startingAddress += startingAddress;
    }

    public int getPcbId() {
        return pcbId;
    }

    public void setPcbId(int pcbId) {
        this.pcbId = pcbId;
    }
}
