package process.controlling.block;

/**
 进程阻塞队列：fifo，实现进程调度，处理器从就绪队列选择进程，
 是进程从就绪态转为运行态。
 数据结构：队列，（用链式存储实现）
 只允许在头部删除，在尾部插入
 队列：另一种被限制的线性表
 数据结构-逻辑结构：线性结构
 物理结构：顺序，链式
 队列：使用固定的一端插入元素，固定的一段删除元素
 只允许在表的前端（front)进行删除操作，只允许在表的后端(rear)进行插入操作
 队尾：进行插入操作的一段
 队首：进行删除操作的一端
 空队列：队列中没有任何的数据元素
 */
public class BlockedQueue<T> {
    //节点n内部类，用于表示链式存储队列中的每个节点
    private class Node{
        private T data;//节点的数据
        private Node next;//保存指向下一个节点的引用
        public Node(){}//构造函数，初始化
        public Node(T data,Node next){
            this.data=data;
            this.next=next;
        }
    }
    /*
    就绪队列的属性
     */
    private Node front;//保存就绪队列的头节点，即下一个会被处理机调度的进程，
    private Node rear;//保存就绪队列的伪结点，即就绪队列中新进来的进程都在这里插入
    private int size;//保存目前进程就绪队列中的进程数目
    /*
    就绪队列的构造方法
     */
    //1创建一个空的进程就绪队列，没有任何进程处于就绪状态
    public BlockedQueue(){
        //构造函数，初始化
        front=null;
        rear=null;
        size=0;
    }
    //2创建一个非空的就绪队列，以一个指定的进程来创建进程就绪队列
    public BlockedQueue(T element){
        front=new Node(element,null);
        rear=front;
        size=1;
    }
    //3返回目前进程就绪队列中进程的个数，，处于就绪状态进程的个数
    public int getSize(){
        return size;
    }
    //4返回进程就绪队列中的最后一个进程，即处于尾部的进程的数据
    public T getData(){
        return rear.data;
    }
    //5判断进程就绪队列是否为空
    public boolean isEmpty(){
        return size==0;
    }
    //6清空进程就绪队列
    public void clear(){
        front=null;
        rear=null;
        size=0;
    }
    //7将进程就绪队列中的所有进程的信息打印处理
    public String toString(){
        if(isEmpty())
            return "[]";
        else{
            StringBuilder sb=new StringBuilder("[");
            Node current=front;
            for(int i=0;i<size && current!=null;i++){
                sb.append(current.data.toString()+",");
                current=current.next;
            }
            sb.append("]");
            return  sb.toString();
        }
    }
    //8进程就绪队列从首部移除元素，即处于就绪队列头部的进程由就绪态转为运行态
    public T removeFromFront(){
        if(isEmpty()){
            throw new RuntimeException("就绪队列为空，无法从就绪队列中移除就绪进程，");
        }else{
            Node del=front;
            front=del.next;
            T value=del.data;
            del=null;
            size--;
            return value;
        }
    }
    //9向就绪队列的尾部插入进程，这些进程可能是timeout的进程，也可能是调度来的进程
    public void addAtRear(T element){
        if(front==null){
            front=new Node(element,null);
            rear=front;
        } else{
            Node newNode=new Node(element,null);
            rear.next=newNode;
            rear=newNode;
        }
        size++;
    }
}
