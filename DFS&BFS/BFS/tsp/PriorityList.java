package tsp;

/**
 * @Author jinjun99
 * @Date Created in 2021/11/29 19:27
 * @Description
 * @Since version-1.0
 */
public class PriorityList {

    private static Node[] list = new Node[10000];
    private static int f = 0;
    private static int l = 0;
    private static int size = 0;
    public void offer(Node node){
        list[l] = node;
        l++;
        size++;
        bubbleSort();
    }
    public Node poll(){
        Node n = list[f];
        list[f] = null;
        f++;
        size--;
        return n;
    }
    public Node peek(){
        return list[f];
    }
    public boolean isEmpty(){
        if (size>0) {
            return false;
        }else {
            return true;
        }
    }
    private void bubbleSort(){
        for (int i = f; i < size-1; i++) {
            for (int j = f; j < size-1-i; j++) {
                if (list[j].dist>list[j+1].dist) {
                    Node tempNode = list[j];
                    list[j] = list[j+1];
                    list[j+1] = tempNode;
                }
            }
        }
    }
}
