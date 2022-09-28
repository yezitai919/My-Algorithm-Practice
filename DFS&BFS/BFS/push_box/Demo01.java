package push_box;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @Author jinjun99
 * @Date Created in 2021/11/28 19:16
 * @Description
 * @Since version-1.0
 */
public class Demo01 {
    private static int n = 6;
    private static int m = 6;
    private static char[][] maze = {
            {'.','.','.','#','@','.'},
            {'@','.','.','*','.','.'},
            {'#','*','#','#','.','.'},
            {'.','.','#','#','*','#'},
            {'.','.','X','.','.','.'},
            {'.','@','#','.','.','.'}};

    private static Node optimalV = new Node();
    private static int[] dirX = {0,1,0,-1};
    private static int[] dirY = {-1,0,1,0};

    private static void bfs(int[][]position){
        Node top = new Node();
        top.position=copyArr(position);
        PriorityQueue<Node> liveNode = new PriorityQueue<>(Comparator.comparingInt(node -> node.priority));
        liveNode.offer(top);
        while (!liveNode.isEmpty()){
            Node parent = liveNode.poll();
            //四个方向搜索
            for (int i = 0; i < 4; i++) {
                int[][] cP = copyArr(parent.position);
                int a = cP[0][0]+dirX[i];
                int b = cP[0][1]+dirY[i];
                boolean bl1 = a>=0&&b>=0&&a<n&&b<m;
                int c = a+dirX[i];
                int d = b+dirY[i];
                boolean bl2 = c>=0&&d>=0&&c<n&&d<m;
                boolean bl3 = true;
                //先遍历'*'的位置
                for (int j = 1; j < 4; j++) {
                    //如果该方向上往前一步是'*'
                    if (bl1&&cP[j][0]==a&&cP[j][1]==b){
                        //则跳过'.'的情况
                        bl3 = false;
                        //如果往前一步不是'#'(即是'.'或'@')
                        if (bl2&&maze[c][d]!='#'){
                            //人和箱子往前一步
                            cP[0][0] = a;
                            cP[0][1] = b;
                            cP[j][0] = c;
                            cP[j][1] = d;
                            //子结点
                            int di = parent.dist+1;
                            int in = parent.intoNum;
                            if (maze[c][d]=='@'){
                                in++;
                            }
                            int pri = getPriority(di,in);
                            Node child = new Node(parent,i,cP,di,in,pri);
                            if (in==3){
                                optimalV=child;
                                return;
                            }
                            liveNode.offer(child);
                            break;
                        }
                    }
                }
                if (bl1&&bl3){
                    cP[0][0] = a;
                    cP[0][1] = b;
                    int di = parent.dist+1;
                    int in = parent.intoNum;
                    int pri = getPriority(di,in);
                    liveNode.offer(new Node(parent,i,cP,di,in,pri));
                }
            }
        }



    }
    private static int getPriority(int dist,int intoNum){
        if (intoNum==0){
            return dist;
        }else {
            return dist /intoNum;
        }

    }
    private static int[][] copyArr(int[][] arr){
        int n = arr.length;
        int m = arr[0].length;
        int[][] a1 = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                a1[i][j]=arr[i][j];
            }
        }
        return a1;
    }

    private static int[][] hide(int num){
        int[][] temp = new int[num+1][2];
        int c = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (maze[i][j]=='*'){
                    temp[c][0]=i;
                    temp[c][1]=j;
                    maze[i][j]='.';
                    c++;
                }
                if (maze[i][j]=='X'){
                    temp[0][0]=i;
                    temp[0][1]=j;
                    maze[i][j]='.';
                }
            }
        }
        return temp;
    }
    private static void recover(int[][] p){
        maze[p[0][0]][p[0][1]] = 'X';
        maze[p[1][0]][p[1][1]] = '*';
        maze[p[2][0]][p[2][1]] = '*';
        maze[p[3][0]][p[3][1]] = '*';
    }
    public static void main(String[] args) {
        int[][] starPosition = hide(3);

        bfs(starPosition);

        recover(starPosition);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print(maze[i][j]+" ");
                if (j==m-1){
                    System.out.println();
                }
            }
        }
        System.out.println("最短距离："+optimalV.dist);

    }
}
