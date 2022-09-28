package battle_city;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @Author jinjun99
 * @Date Created in 2021/11/28 22:29
 * @Description
 * @Since version-1.0
 */
public class Demo01 {
    private static int n = 8;
    private static int m = 6;
    private static char[][] map = {
            {'E','B','B','B','E','B'},
            {'E','S','B','E','R','E'},
            {'B','E','R','E','B','E'},
            {'S','E','E','E','B','R'},
            {'B','T','B','B','E','B'},
            {'B','E','B','E','Y','E'},
            {'B','B','B','B','S','E'},
            {'B','E','E','B','B','R'}
    };

    /**
     * 起点的坐标
     */
    private static int[] starting = new int[2];
    /**
     * 终点的坐标
     */
    private static int[] ending = new int[2];

    /**
     * 最优值结点
     */
    private static Node optimalV = new Node();

    /**
     * 方向
     */
    private static int[] dirX = {-1,0,1,0};
    private static int[] dirY = {0,1,0,-1};

    /**
     *
     */
    private static void bfs(){
        /*先把最少回合设置成最大*/
        optimalV.turns = Integer.MAX_VALUE;
        /*头结点*/
        Node top = new Node();
        top.position = Arrays.copyOf(starting,2);
        top.mark = copyArr(map);
        PriorityQueue<Node> liveNode = new PriorityQueue<>(Comparator.comparingInt(node -> node.priority));
        liveNode.offer(top);
        while (!liveNode.isEmpty()){
            Node parent = liveNode.poll();
            /*往四个方向移动的子节点*/
            for (int i = 0; i < 4; i++) {
                int a = parent.position[0] + dirX[i];
                int b = parent.position[1] + dirY[i];
                boolean bl1 = a>=0&&b>=0&&a<n&&b<m;
                boolean bl2 = parent.mark[a][b] == 'E'||parent.mark[a][b] == 'T';
                /*如果往i方向移动一步为空或者终点就选择移动*/
                if (bl1&&bl2) {
                    int[] p1 = {a,b};
                    int t1 = parent.turns +1;
                    char[][] mark1 = copyArr(parent.mark);
                    int pri1 = getPriority(p1,ending,t1);
                    Node child1 = new Node(parent,i,p1,t1,mark1,pri1);
                    if (mark1[a][b]=='T'){
                        optimalV=child1;
                        return;
                    }else {
                        child1.mark[a][b]='Y';
                        liveNode.offer(child1);
                    }
                }
                //如果往i方向是可击毁的墙就原地发射炮弹
                if (bl1&&parent.mark[a][b] == 'B'){
                    int dir = i+4;
                    int[] p2 = Arrays.copyOf(parent.position,2);
                    int t2 = parent.turns +1;
                    char[][]mark2 = copyArr(parent.mark);
                    mark2[a][b] = 'E';
                    int pri2 = getPriority(p2,ending,t2);
                    Node child2 = new Node(parent,dir,p2,t2,mark2,pri2);
                    liveNode.offer(child2);

                }
            }
        }

    }
    private static int getPriority(int[] p,int[] end,int t){
        //两队距离加回合数，给射击墙的动作加权
        return Math.abs(p[0]-end[0])+Math.abs(p[1]-end[1])+t;
    }

    private static char[][] copyArr(char[][] arr){
        int a = arr.length;
        int b = arr[0].length;
        char[][] temp = new char[a][b];
        for (int i = 0; i < a; i++) {
            for (int j = 0; j < b; j++) {
                temp[i][j] = arr[i][j];
            }
        }
        return temp;
    }
    private static void getStartAndEnd(){
        int b = 0;
        for (int i = 0; i < map.length; i++) {
            if (b==2){
                return;
            }
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j]=='Y'){
                    starting[0] = i;
                    starting[1] = j;
                    b++;
                }
                if (map[i][j]=='T'){
                    ending[0] = i;
                    ending[1] = j;
                    b++;
                }
            }
        }
    }
    public static void main(String[] args) {
        getStartAndEnd();
        bfs();
        System.out.println("最少回合数："+optimalV.turns);
        int si = optimalV.turns+1;
        for (int i = 0; i < si; i++) {
            System.out.println("决策："+optimalV.dir);
            System.out.println("状态："+Arrays.toString(optimalV.position));
            optimalV=optimalV.parent;
        }

    }
}
