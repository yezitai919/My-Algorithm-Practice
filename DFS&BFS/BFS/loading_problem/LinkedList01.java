package loading_problem;

/**
 * @Author jinjun99
 * @Date Created in 2021/11/29 19:13
 * @Description
 * @Since version-1.0
 */
public class LinkedList01 {
    private static Node[] list = new Node[100000000];
    private static int f = 0;
    private static int l = 0;
    private static int size = 0;
    public void offer(Node node){
        list[l] = node;
        l++;
        size++;
    }
    public Node poll(){
        Node n = list[f];
        list[f] = null;
        f++;
        size--;
        return n;
    }
    public boolean isEmpty(){
        if (size>0) {
            return false;
        }else {
            return true;
        }
    }

    public LinkedList01() {
    }
}
