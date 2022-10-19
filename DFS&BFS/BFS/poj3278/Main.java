package poj3278;

import java.util.Queue;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.Scanner;
/**
 * @Author jinjun99
 * @Date Created in 2022/9/30 21:16
 * @Description 每次移动有3种选择，求最小步数，BFS，搜索完当前步的所有可能性再走下一步。已AC
 * @Since version-1.0
 */
public class Main {

    static class Node{
        /**
         * 位置
         */
        int x;
        /**
         * 时间
         */
        int t;

        public Node(int x, int t) {
            this.x = x;
            this.t = t;
        }

        public Node() {
        }
    }

    static int maxInt = 100005;
    /**
     * 判断是否走过
     */
    static int[] walk;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int k = sc.nextInt();

        walk = new int[maxInt];

//        NodeLinkedList list = new NodeLinkedList();
        Queue<Node> list = new LinkedBlockingQueue<Node>();
        list.offer(new Node(n,0));
        while (!list.isEmpty()){
            Node node = list.poll();
            int x1 = node.x;
            int x2 = x1+1;
            int x3 = x1-1;
            int x4 = x1*2;
            int t1 = node.t+1;

            if (x1==k){
                System.out.println(node.t);
                break;
            }
            if (x2<maxInt&&walk[x2]==0){
                walk[x2]=1;
                list.offer(new Node(x2,t1));
            }
            if (x3>=0&&walk[x3]==0){
                walk[x3]=1;
                list.offer(new Node(x3,t1));
            }
            if (x4<maxInt&&walk[x4]==0){
                walk[x4]=1;
                list.offer(new Node(x4,t1));
            }
        }
    }
}


/*
5 17
5 1700

  long st = System.currentTimeMillis();
 long e = System.currentTimeMillis();
        System.out.println("计算时长："+(e-st)+"ms");
*/

 /* static class NodeLinkedList{
        int capacity =1000;
        Node[] list = new Node[capacity];
        int f = 0;
        int l = 0;
        int size = 0;

        public void offer(Node n){
            if (l==capacity-5){
                expansion();
            }
            list[l] = n;
            l++;
            size++;

        }
        public Node poll(){
            Node n = list[f];
            list[f]=null;
            f++;
            size--;
            return n;
        }
        public boolean isEmpty(){
            return size == 0;
        }

        private void expansion(){
            capacity = capacity*2;
            Node[] list1 = new Node[capacity];
            for (int i = 0; i < size; i++) {
                list1[i] = list[f];
                f++;
            }
            f=0;
            l=size-1;
            list=list1;
        }

        public NodeLinkedList(int capacity) {
            this.capacity = capacity;
        }

        public NodeLinkedList() {
        }
    }*/