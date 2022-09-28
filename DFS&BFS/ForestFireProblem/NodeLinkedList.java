package burning_trees;

/**
 * @Author jinjun99
 * @Date Created in 2021/12/7 12:49
 * @Description
 * @Since version-1.0
 */
public class NodeLinkedList {
    Node[] list = new Node[1000];

    int f = 0;
    int l = 0;
    int size = 0;
    public void offer(Node n){
        list[l] = n;
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

    public boolean isEmpty(){
        if (size==0){
            return true;
        }
        return false;
    }

    /**
     * 根据优先级降序
     */
    private void bubbleSort(){
        for (int i = f; i < size - 1; i++) {
            for (int j = f; j < size - 1 - i; j++) {
                if (list[j].priority<list[j+1].priority){
                    Node temp = list[j];
                    list[j] = list[j+1];
                    list[j+1] = temp;
                }
            }
        }
    }
}
