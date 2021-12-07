package burning_trees;

import java.util.Scanner;

/**
 * @Author jinjun99
 * @Date Created in 2021/12/7 10:58
 * @Description
 * @Since version-1.0
 */
public class CutTreesBFS {
    /**
     * 长
     */
    int n;
    /**
     * 宽
     */
    int m;
    int[] fire;
    /**
     * 地图
     */
    int[][] map;
    /**
     * 可燃树坐标
     */
    PointArrays trees;

    /**
     * 要砍几颗树
     */
    int cutNum;

    public CutTreesBFS(int n, int m, int[] fire, int[][] map, PointArrays trees,int cutNum) {
        this.n = n;
        this.m = m;
        this.fire = fire;
        this.map = map;
        this.trees = trees;
        this.cutNum = cutNum;
    }

    /**
     * @return 获取最优砍树方案
     */
    public Node getCuttingScheme(){
        countTree();
        bfs();
        return optimal;
    }

    /**
     * 所有的树
     */
    int treeNum = 0;
    private void countTree(){
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (map[i][j]==1){
                    treeNum++;
                }
            }
        }
    }

    /**
     * 最优值结点
     */
    Node optimal = new Node(null,null,null,null,0,0);

    /**
     *
     */
    private void bfs(){
        /*头结点*/
        Node top = new Node(null,new int[2],map,trees,0,treeNum);
        /*活结点队列*/
        NodeLinkedList liveNode = new NodeLinkedList();
        liveNode.offer(top);
        while (!liveNode.isEmpty()){
            /*父结点出队*/
            Node parent = liveNode.poll();
            /*遍历所有能砍的树*/
            for (int i = 0; i < parent.trees.size; i++) {
                /*要砍的树的坐标*/
                int[] cutTree = parent.trees.get(i);
                /*砍完树后的地图*/
                int[][] childM = arrCopy(parent.map);
                childM[cutTree[0]][cutTree[1]] = 0;
                /*砍完树后剩下的可燃树*/
                BurningTreesDFS  bT = new BurningTreesDFS(n,m,fire,childM);
                PointArrays childBT = bT.getTrees();
                /*累计砍树多少棵*/
                int sum = parent.sum+1;
                /*优先级为剩下的树=总的树-砍的树-烧的树*/
                int pri = treeNum-sum-childBT.size;
                /*子结点*/
                Node child = new Node(parent,cutTree,childM,childBT,sum,pri);
                /*记录剩下最多树的方案*/
                if (pri>optimal.priority){
                    optimal=child;
                }
                /*砍树少于限定则入队*/
                if (sum<cutNum){
                    liveNode.offer(child);
                }
            }
        }
    }
    private int[][] arrCopy(int[][] map){
        int[][] a = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                a[i][j] = map[i][j];
            }
        }
        return a;
    }
}
