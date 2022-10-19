package poj1426;

import java.util.LinkedList;
import java.util.Scanner;

/**
 * @Author jinjun99
 * @Date Created in 2022/10/2 8:56
 * @Description BFS，每一层节点是同位数，要搜索完一层节点再搜下一层。已AC
 * @Since version-1.0
 */
public class Main {
    /**
     * 输入的正整数
     */
    static int num;
    /**
     * 余数的最大个数
     */
    static int maxNum = 210;

    /**
     * 答案
     */
    static int[] answer;
    /**
     * 答案尾下标（答案长度）
     */
    static int ansIndex;
    /**
     * 余数存在过
     */
    static boolean[] exist;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true){
            num = sc.nextInt();
            if (num==0){
                break;
            }

            answer = new int[maxNum];
            exist = new boolean[maxNum];
            ansIndex = 0;
            bfs();

            for (int i = ansIndex-1; i >=0 ; i--) {
                if (i==0){
                    System.out.println(answer[i]);
                }else {
                    System.out.print(answer[i]);
                }
            }
        }
    }

    private static void bfs() {
        Node root = new Node(null,1,1);
        LinkedList<Node> list = new LinkedList<Node>();
        list.offer(root);
        while (!list.isEmpty()){
            Node parNode = list.poll();
            int remainder = parNode.remainder;
            /*下一个余数*/
            int next =0;
            for (int i = 0; i < 2; i++) {
                next = (remainder*(10%num)+i)%num;

                if (!exist[next]){
                    exist[next] = true;
                    Node child = new Node(parNode,next,i);
                    list.offer(child);
                    if (next==0){
                        Node temp = child;
                        while (temp!=null){

                            answer[ansIndex] = temp.type;
                            ansIndex++;
                            temp = temp.parent;

                        }
                        return;
                    }
                }
            }
        }
    }
    static class Node{
        /**
         * 父节点
         */
        Node parent;
        /**
         * 余数
         */
        int remainder;
        /**
         * 节点种类：根节点=-1，左节点=0，右节点=1
         */
        int type;

        public Node(Node parent, int remainder, int type) {
            this.parent = parent;
            this.remainder = remainder;
            this.type = type;
        }
    }
}
/*

2
6
19
0
*/
