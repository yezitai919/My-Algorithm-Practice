package dynamic.partition.management;

public class Request {
    /**
     * 进程号
     */
    private int pcbId;
    /**
     * 进程请求内存大小
     */
    private int pcbSize;

    public Request(int pcbId, int pcbSize) {
        this.pcbId = pcbId;
        this.pcbSize = pcbSize;
    }

    public int getPcbId() {
        return pcbId;
    }

    public void setPcbId(int pcbId) {
        this.pcbId = pcbId;
    }

    public int getPcbSize() {
        return pcbSize;
    }

    public void setPcbSize(int pcbSize) {
        this.pcbSize = pcbSize;
    }
}
