package knapsack_problem;

import java.util.*;

/**
 * @Author jinjun99
 * @Date Created in 2021/11/25 23:04
 * @Description
 * @Since version-1.0
 */
public class Demo01 {
    public static void main(String[] args) {
        //物品数量
        int n = 10;
        //背包容量
        int c = 50;
        //各个物品重量和价值。忽略下标0元素，goods[i][0]表示物品i的重量，goods[i][1]表示物品i的价值
        int[][] goods = new int[n+1][2];
        //所有物品的总价值
        int tV = 0;
        Random r = new Random();
        //随机赋值
        for (int i = 1; i <= n; i++) {
            goods[i][0] = r.nextInt(20)+1;
            goods[i][1] = r.nextInt(50)+1;
            tV += goods[i][1];
        }
        System.out.println("背包容量为：" + c);
        System.out.println("所有物品的重量和价值为：" );
        for (int i = 1; i <= n; i++) {
            if (i%5 == 0){
                System.out.println(Arrays.toString(goods[i]));
            }else {
                System.out.print(Arrays.toString(goods[i])+" ");
            }
        }
        long s = System.nanoTime();
        Node optimalN = branchAndBound(n,c,goods,tV);
        long e = System.nanoTime();
        //(最优值)
        int optimalV = optimalN.nodeV;
        //(最优解)
        ArrayList<Integer> optimalS = new ArrayList<>();
        for (int i = n; i > 0; i--) {
            if (optimalN.layer == i) {
                if (optimalN.isLeft) {
                    optimalS.add(i);
                }
                optimalN = optimalN.parent;
            }
        }
        Collections.sort(optimalS);
        System.out.println("放入背包的物品价值为："+optimalV);
        System.out.println("放入背包的物品编号为：" +optimalS);
        System.out.println("算法用时"+(e-s)+"纳秒");
    }
    public static class Node{
        //父母结点
        Node parent;
        //当前结点的重量
        int nodeW;
        //当前结点的价值
        int nodeV;
        //当前结点的层数,对应物品编号
        int layer;
        //当前结点的优先级，(上界函数值)
        int priority;
        //是否是左儿子结点，(记录决策)
        boolean isLeft;

        public Node(Node parent, int nodeW, int nodeV, int layer, int priority, boolean isLeft) {
            this.parent = parent;
            this.nodeW = nodeW;
            this.nodeV = nodeV;
            this.layer = layer;
            this.priority = priority;
            this.isLeft = isLeft;
        }
    }

    private static Node branchAndBound (int n, int c, int[][] goods,int tV) {
        //活结点队列
        PriorityQueue<Node> activeNode = new PriorityQueue<>((node1, node2) -> node2.priority - node1.priority);
        //根节点入队
        activeNode.offer(new Node(null,0,0,0,tV,false));
        //每次循环搜索完一个结点的所有子结点。
        while (true){
            //取出队头活结点
            Node cNode = activeNode.poll();
            assert cNode != null;
            //获取当前活结点的重量和价值
            int cW = cNode.nodeW;
            int cV = cNode.nodeV;
            //子结点层数=父结点层数+1
            int cLayer = cNode.layer + 1;
            //左子节点的重量和价值
            int leftW = cW + goods[cLayer][0];
            int leftV = cV + goods[cLayer][1];
            //剩余物品总价值
            int rV = cNode.priority-leftV;
            //如果当前物品cLayer能装入背包就装，创建一个新的左子节点
            if (leftW <= c){
                Node leftN = new Node(cNode,leftW,leftV,cLayer,leftV+rV,true);
                if (cLayer < n) {
                    //非叶子节点入队
                    activeNode.offer(leftN);
                    //右子节点的状态等于父节点，优先级小于父节点，所以不必参与最优值判断
                    activeNode.offer(new Node(cNode,cW,cV,cLayer,cV+rV,false));
                }else {//如果是叶子节点则对比当前活节点队列的最大优先级
                    assert activeNode.peek() != null;
                    if (leftV>=activeNode.peek().priority){
                        //返回最优值节点
                        return leftN;
                    }
                }
            }
        }
    }
}
