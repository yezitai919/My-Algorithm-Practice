package loading_problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

/**
 * @Author jinjun99
 * @Date Created in 2021/11/24 13:30
 * @Description
 * @Since version-1.0
 */
public class Demo01 {
    /**
     * 集装箱数量
     */
    private static int n = 20;
    /**
     * 第一艘船容量
     */
    private static int c1 = 30;
    /**
     * 第二艘船容量
     */
    private static int c2 = 30;

    /**
     * 各个集装箱重量
     */
    private static int[] w = new int[n];
    /**
     * 总重量
     */
    private static int totalW = 0;

    /**
     * 最优值结点
     */
    private static Node optimalV = new Node();

    public static void main(String[] args) {
        Random random = new Random();
        int num = 0;
        while (num<n){
            int wi = random.nextInt((c1+c2)*2/n)+1;
            if ((totalW+wi+(n-num)) <= (c1+c2)){
                w[num] = wi;
                totalW+=wi;
                num++;
            }
        }
        bfs();
        System.out.println("第一艘船的容量为："+c1+",第二艘船的容量为："+c2);
        System.out.println("各个集装箱重量为：");
        System.out.println(Arrays.toString(w));
        System.out.println("所有集装箱总重量为："+totalW);
        System.out.println("最优装载：");
        System.out.println("第一艘船装入："+optimalV.nodeW);
        ArrayList<Integer> ship1 = new ArrayList<>();
        ArrayList<Integer> ship2 = new ArrayList<>();

        int s2 = totalW-optimalV.nodeW;
        //标记放入1船的集装箱
        int[] wi = new int[n];
        while (optimalV!=null){
            if (optimalV.left==1){
                ship1.add(w[optimalV.level-1]);
                wi[optimalV.level-1]=1;
            }
            optimalV = optimalV.parent;

        }
        System.out.println(ship1);
        System.out.println("第二艘船装入："+s2);
        for (int i = 0; i < n; i++) {
            if (wi[i]==0){
                ship2.add(w[i]);
            }
        }
        System.out.println(ship2);
    }

    private static void bfs(){
        LinkedList01 liveNode = new LinkedList01();
        //活结点队列
        //LinkedList<Node> liveNode = new LinkedList<>();
        liveNode.offer(new Node(null,0,0,0));
        //活结点队列遍历完结束循环
        while (!liveNode.isEmpty()){
            Node cNode = liveNode.poll();
            //父节点载重量
            int currW = cNode.nodeW;
            //子结点层号
            int level = cNode.level+1;
            //左子结点载重量，即当前集装箱选择放入1船
            int leftW = currW+w[level-1];
            //左子结点值不大于1船载重量
            if (leftW<=c1){
                Node leftChild = new Node(cNode,leftW,level,1);
                //记录最优值结点
                if (leftW > optimalV.nodeW){
                    optimalV = leftChild;
                }
                //非叶子结点入队
                if (level<n){
                    liveNode.offer(leftChild);
                }
            }

            //计算右子结点的上界值
            int upper = upperBound(currW,level);
            //如果右子结点上界值大于最优值并且非叶子结点则入队
            if (level<n && upper >optimalV.nodeW){
                liveNode.offer(new Node(cNode,currW,level,0));
            }
        }
    }

    /**
     * @param level
     * @return
     */
    private static int upperBound(int currW, int level){
        int remainderW = 0;
        for (int i = level; i < n; i++) {
            if (remainderW+w[i]<=c1){
                remainderW += w[i];
            }else {
                break;
            }
        }
        return currW+remainderW;
    }
}
