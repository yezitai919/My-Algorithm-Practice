package tank;

import java.util.*;

/**
 * @Author jinjun99
 * @Date Created in 2021/11/27 11:41
 * @Description
 * @Since version-1.0
 */
public class Demo01 {
    private static int n = 6;
    private static int m = 6;

    /**
     * 地图
     */
    private static int[][] map = new int[n][m];

    /**
     * 方向
     */
    private static int[] dirX = {0,0,1,1,1,0,-1,-1,-1};
    private static int[] dirY = {0,1,1,0,-1,-1,-1,0,1};
    private static int[] outset = new int[4];
    private static int[] end = new int[4];
    
     
    private static Node optimalV = new Node();
    
    private static void bfs(){
        optimalV.level = Integer.MAX_VALUE;
        PriorityQueue<Node> liveNode = new PriorityQueue<>(Comparator.comparingInt(node -> node.priority));
        //LinkedList<Node> liveNode = new LinkedList<>();
        Node top = new Node();
        top.state = Arrays.copyOf(outset,4);
        top.mark = new int[n][m];
        top.mark[top.state[0]][top.state[1]] = 1;
        top.mark[top.state[2]][top.state[3]] = 1;
        liveNode.offer(top);
       while (!liveNode.isEmpty()){
            Node parent = liveNode.poll();
            int[] state = Arrays.copyOf(parent.state,4);
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    int[] childDecision = {i,j};
                    int[] childState = {state[0]+dirX[i],state[1]+dirY[i],
                            state[2]+dirX[j],state[3]+dirY[j]};
                    int b = check(childDecision,childState,parent.mark);
                    if (b!=0){
                        int[][] mark = copyArr(parent.mark);
                        mark[childState[0]][childState[1]]=1;
                        mark[childState[2]][childState[3]]=1;
                        int priority = priority(childState);
                        Node child = new Node(parent,childDecision,childState, parent.level+1,mark,priority);
                        if (b==1&&child.level<optimalV.level){
                            optimalV = child;
                                return;
                        }
                        if (b==2){
                            liveNode.offer(child);
                        }
                    }
                }
            }
        }

    }
    private static int[][] copyArr(int[][] mark){
        int[][] temp = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                temp[i][j] = mark[i][j];
            }
        }
        return temp;
    }

    private static int check(int[] d,int[] s,int[][] mark){
        //不越界
        boolean b1 = s[0]>=0&&s[1]>=0&&s[2]>=0&&s[3]>=0 && s[0]<n&&s[1]<m&&s[2]<n&&s[3]<m;
        if (b1){
            //到达终点
            boolean b2 = s[0]==end[0]&&s[1]==end[1]&&s[2]==end[2]&&s[3]==end[3];
            if (b2){
                return 1;
            }
            //不相邻
            boolean b3 = Math.abs(s[0]-s[2])>1||Math.abs(s[1]-s[3])>1;
            //不重复
            boolean bl1 = mark[s[0]][s[1]]==0;
            boolean bl2 = mark[s[2]][s[3]]==0;
            boolean bl3 = d[0]==0;
            boolean bl4 = d[1]==0;
            boolean b4 = bl1 && bl2 && !bl3 && !bl4 || bl3 && bl2 || bl4 && bl1;

            if (b3&&b4){
                return 2;
            }
        }

        return 0;
    }
    private static int priority(int[] state){
        int dist = Math.abs(state[0]-end[0])+Math.abs(state[1]-end[1])+Math.abs(state[2]-end[2])+Math.abs(state[3]-end[3]);
        return dist;
    }
    public static void main(String[] args) {
        outset = new int[] {0,0,0,m-1};
        end = new int[] {n-1,0,n-2,m-1};
        bfs();
        System.out.println("最少要"+optimalV.level+"秒俩坦克到达目的地");

    }

    
}
