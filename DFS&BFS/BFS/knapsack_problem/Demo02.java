package knapsack_problem;

import java.util.*;

/**
 * @Author jinjun99
 * @Date Created in 2021/11/25 15:11
 * @Description
 * @Since version-1.0
 */
public class Demo02 {
    /**
     * 物品数量
     */
    private static int n = 10;

    /**
     * 背包容量
     */
    private static int c = 50;
    /**
     * 各物品重量
     */
    private static int[] w = {1, 5, 3, 10, 11, 12, 10, 11, 14, 15};
    /**
     * 各物品价值
     */
    private static int[] v = {14, 35, 13, 36, 35, 31, 24, 25, 31, 33};

    private static Node optimalV = new Node();

    public static void main(String[] args) {
        Random r = new Random();
        for (int i = 0; i < n; i++) {
            w[i] = r.nextInt(3*c/n)+1;
            v[i] = r.nextInt(c)+1;
        }
        sortByValue();
        bfs();
        System.out.println("物品数量："+n+" 背包容量："+c);
        System.out.println("各个物品重量："+Arrays.toString(w));
        System.out.println("各个物品价值："+Arrays.toString(v));
        int w1 = optimalV.nodeState1;
        int v1 = optimalV.nodeState2;
        ArrayList<Integer> w2 = new ArrayList<>();
        ArrayList<Integer> v2 = new ArrayList<>();
       // System.out.println(optimalV.level);
        while (optimalV!=null){
            if (optimalV.decision==1){
                w2.add(w[optimalV.level-1]);
                v2.add(v[optimalV.level-1]);
            }
            optimalV = optimalV.parent;
        }
        System.out.println("装入背包的物品重量："+w1);
        System.out.println(w2);
        System.out.println("装入背包的物品价值："+v1);
        System.out.println(v2);
    }



    private static void bfs(){
        //活结点队列，根据优先级排列
       // LinkedList<Node> liveNode = new LinkedList<>();
        PriorityQueue<Node> liveNode = new PriorityQueue<>((node1, node2) -> node2.priority - node1.priority);
        //第一个结点入队
        liveNode.offer(new Node());
        while (!liveNode.isEmpty()){
            //
            Node cNode = liveNode.poll();
            //当前重量
            int cW = cNode.nodeState1;
            //当前价值
            int cV = cNode.nodeState2;
            //子结点层数
            int level = cNode.level+1;
            int leftW = cW+w[level-1];
            int leftV = cV+v[level-1];
            int priority = upperBound(leftW,leftV,level);
            if (leftW <= c){
                //左子结点
                Node leftChild = new Node(cNode,leftW,leftV,level,1,priority);
                if (leftV>optimalV.nodeState2){
                    System.out.println("> "+leftV);
                    optimalV = leftChild;
                }else {
                    System.out.println("< "+leftV+" "+optimalV.nodeState2+" 层 "+level);
                }
                if (level<n){
                    liveNode.offer(leftChild);
                }
            }
            int priority2 = upperBound(cW,cV,level);
            if (level<n && priority2>optimalV.nodeState2){
                liveNode.offer(new Node(cNode,cW,cV,level,0,priority2));
            }
        }
    }


    private static int upperBound(int cw,int cv,int cl){
        int maxW = cw;
        int maxV = cv;
        for (int i = cl; i < n; i++) {
            if (maxW+w[i]<c){
                maxW+=w[i];
                maxV+=v[i];
            }else {
                break;
            }
        }
        return maxV;
    }

    /**
     * 按单位重量价值排序
     */
    private static void sortByValue(){
        //计算单位重量价值
        double [] temp = new double[n];
        for (int i = 0; i < n; i++) {
            temp[i] = (double) v[i]/w[i];
        }
        //排序
        double [] temp2 = Arrays.copyOf(temp,n);
        Arrays.sort(temp2);
        //判断是否复制过
        int[] temp3 = new int[n];
        //排序后的w
        int[] temp4 = new int[n];
        //排序后的v
        int[] temp5 = new int[n];
        int l = n-1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (temp2[i]==temp[j]&&temp3[j]==0){
                    temp4[l] = w[j];
                    temp5[l] = v[j];
                    temp3[j]=1;
                    l--;
                }
            }
        }

        w = Arrays.copyOf(temp4,n);
        v = Arrays.copyOf(temp5,n);
    }

}
