package slave_master;

import java.util.LinkedList;
import java.util.Stack;

/**
 * @Author jinjun99
 * @Date Created in 2021/11/26 19:34
 * @Description
 * @Since version-1.0
 */
public class Demo01 {
    /**
     * 野人或传教士人数
     */
    private static int n = 10;

    /**
     * 船载客数
     */
    private static int m = 4;

    private static Node optimalV = new Node(null,null,null,Integer.MAX_VALUE);
    private static void bfs(){
        LinkedList<Node> liveNode = new LinkedList<>();
        liveNode.offer(new Node(n));
        while (!liveNode.isEmpty()){
            Node parent = liveNode.poll();
            int[] state = parent.state;
            /*因为追求最少航次，所以每次尽量满载*/
            //本次全部运野人
            //船上野人数
            int c1 = Math.min(state[0], m);
            //
            if (c1>0&&state[1]+c1<=state[3]||state[1]==0&&state[3]==0){
                int[] d1 = {c1,0};
                int[] s1 = {state[0]-c1,state[1]+c1,state[2],state[3]};
                Node n1 = new Node(parent,d1,s1,parent.level+1);
                if (s1[0]==0&&s1[2]==0){
                    if (n1.level< optimalV.level){
                        optimalV = n1;
                    }
                }else {
                    liveNode.offer(n1);
                }

            }
            //本次全部运传教士
            //船上教士数
            int c2 = Math.min(state[2], m);
            //
            if (c2>0&&state[2]-c2>=state[0]||state[2]-c2==0&&c2>0){
                int[] d2 = {0,c2};
                int[] s2 = {state[0],state[1],state[2]-c2,state[3]+c2};
                Node n2 = new Node(parent,d2,s2,parent.level+1);
                if (s2[0]==0&&s2[2]==0){
                    if (n2.level< optimalV.level){
                        optimalV = n2;
                    }
                }else {
                    liveNode.offer(n2);
                }
            }

            //其他运载方案，i是传教士人数，j是野人数
            for (int i = 1; i < c2 ; i++) {
                int m1 = Math.min(i,m-i);
                for (int j = 1; j <= m1; j++) {
                    int a1 = state[0]-j;
                    int a2 = state[1]+j;
                    int a3 = state[2]-i;
                    int a4 = state[3]+i;
                    //
                    if (a1<=a3&&a2<=a4){
                        int[] di = {j,i};
                        int[] si = {a1,a2,a3,a4};
                        Node ni = new Node(parent,di,si,parent.level+1);
                        if (si[0]==0&&si[2]==0){
                            if (ni.level< optimalV.level){
                                optimalV = ni;
                            }
                        }else {
                            liveNode.offer(ni);
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        bfs();
       System.out.println("最少航次："+optimalV.level);
        Stack<Node> s = new Stack<>();
        while (optimalV.parent!=null){
            s.push(optimalV);
            optimalV = optimalV.parent;
        }
        int s1 = s.size();
        for (int i = 0; i < s1; i++) {
            Node n1 = s.pop();
            System.out.println("第"+n1.level+"次乘船安排：");
            System.out.println("[野人"+n1.decision[0]+" | 传教士"+n1.decision[1]+"]");
            System.out.println("乘船后左岸: [野人"+n1.state[0]+" | 传教士"+n1.state[2]+"] 右岸: [野人"+n1.state[1]+" | 传教士"+n1.state[3]+"]");
        }
    }
}
