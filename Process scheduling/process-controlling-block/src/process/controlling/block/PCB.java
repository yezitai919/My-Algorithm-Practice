package process.controlling.block;

/**
 * pcb进程控制块类
 */
public class PCB {
    private int id;//进程标识符
    private int status;//进程状态(1表示运行态，2表示就绪态，3表示阻塞态）
    //保护进程现场信息
    private int ax,bx,cx,dx;//通用寄存器ax，bx，cx，dx的内容
    private int pc;//程序计数器内容
    private int psw;//程序状态字内容
    private int next;//下一个pcb的位置
    private int execute_time;//进程执行总共需要用的时间
    private int arrival_time;//进程目前已经占用处理器的时间

    public int getExecute_time() {
        return execute_time;
    }

    public void setExecute_time(int execute_time) {
        this.execute_time = execute_time;
    }

    public int getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(int arrival_time) {
        this.arrival_time = arrival_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getAx() {
        return ax;
    }

    public void setAx(int ax) {
        this.ax = ax;
    }

    public int getBx() {
        return bx;
    }

    public void setBx(int bx) {
        this.bx = bx;
    }

    public int getCx() {
        return cx;
    }

    public void setCx(int cx) {
        this.cx = cx;
    }

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getPc() {
        return pc;
    }

    public void setPc(int pc) {
        this.pc = pc;
    }

    public int getPsw() {
        return psw;
    }

    public void setPsw(int psw) {
        this.psw = psw;
    }

    public int getNext() {
        return next;
    }

    public void setNext(int next) {
        this.next = next;
    }
    public String toString(){
        return "进程信息"+"id:"+id+"  status:"+status+"  ax:"+ax+"  bx:"+bx+"  cx:"+cx+"  dx:"+dx+"  pc:"+pc+"  psw:"+psw+"  next:"+next+"  execute_time:"+execute_time+"  arrival_time:"+arrival_time;
    }
}
