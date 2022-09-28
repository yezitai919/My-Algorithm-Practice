package sam_route;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @Author jinjun99
 * @Date Created in 2021/11/28 0:14
 * @Description
 * @Since version-1.0
 */
public class Deom01 {
    private static int n = 15;
    private static int[][] map = new int[n][n];
    /**
     * 建筑数量
     */
    private static int buildingNum = 0;
    private static ArrayList<Building> buildings = new ArrayList();
    /**
     * 添加建筑
     */
    private static int addBuilding(int[] position,int width,int length,int[] p){
        //检查是否越界
        if (position[0]<1||position[1]<1||position[0]+width>n-1||position[1]+length>n-1){
            return 1;
        }
        int conf = 0;
        //检查是否和其他建筑冲突
        if (buildingNum>0){
            for (int i = 0; i < buildingNum; i++) {
                Building bi = buildings.get(i);
                boolean b1 = position[0]+width<bi.position[0]||position[1]+length<bi.position[1];
                boolean b2 = bi.position[0]+bi.width<position[0]||bi.position[1]+bi.length<position[1];
                if (!(b1||b2)){
                   conf = 1;
                }
            }
        }
        if (conf==1){
            return 2;
        }
        //确定停车场位置
        int[] park = new int[3];
        //上下左右四条边
        if (p[0]==0){
            park[0] = position[0]+Math.min(width,p[1])-1;
            park[1] = position[1];
        }
        if (p[0]==1){
            park[0] = position[0]+Math.min(width,p[1])-1;
            park[1] = position[1]+length-1;
        }
        if (p[0]==2){
            park[0] = position[0];
            park[1] = position[1]+Math.min(length,p[1])-1;
        }
        if (p[0]==3){
            park[0] = position[0]+width-1;
            park[1] = position[1]+Math.min(length,p[1])-1;
        }
        park[2] = p[0];
        Building bi = new Building(position,width,length,park);
        buildings.add(bi);
        buildingNum++;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                map[position[0]+i][position[1]+j] = buildingNum;
            }
        }
        map[park[0]][park[1]] = -buildingNum;
        return 0;
    }

    private static int[] dirX = {0,0,-1,1,1,1,-1,-1};
    private static int[] dirY = {-1,1,0,0,-1,1,1,-1};
    private static Node optimalV = new Node();
    private static void getDistance(int starting,int finishing){
        //最优值设为无穷大
        optimalV.dist = Integer.MAX_VALUE;
        int[] topPosition = new int[2];
        topPosition[0] = buildings.get(starting-1).park[0]+dirX[buildings.get(starting-1).park[2]];
        topPosition[1] = buildings.get(starting-1).park[1]+dirY[buildings.get(starting-1).park[2]];
        Node top = new Node();
        top.position = Arrays.copyOf(topPosition,2);
        top.dist=1;
        top.mark = new int[n][n];
        top.mark[topPosition[0]][topPosition[1]] = 1;
        PriorityQueue<Node> liveNode = new PriorityQueue<>(Comparator.comparingInt(node -> node.priority));
        liveNode.offer(top);
        //转换下标，找到当前方向的左边
        int[] left = {3,2,0,1};
        int[] ending = Arrays.copyOfRange(buildings.get(finishing-1).park,0,2);
        int[] reset = {1,0,3,2};
        //BFS
        while (!liveNode.isEmpty()){
            Node parent = liveNode.poll();
            for (int i = 0; i < 4; i++) {
                /*判断该方向是否可行：往前一步是道路0，
                当前方向右边或右上方是建筑物(符合顺时针方向)*/
                int a = parent.position[0]+dirX[i];
                int b = parent.position[1]+dirY[i];
                int c = parent.position[0]+dirX[left[i]];
                int d = parent.position[1]+dirY[left[i]];
                int e = a+dirX[left[i]];
                int f = b+dirY[left[i]];
                boolean b1 = a>=0&&b>=0&&a<n&&b<n&&map[a][b]==0;
                boolean b2 = c>=0&&d>=0&&c<n&&d<n&&map[c][d]!=0;
                boolean b3 = e>=0&&f>=0&&e<n&&f<n&&map[e][f]!=0;
                boolean b4 = reset[i]!=parent.dir;
                /*if (parent.position[0]==0&&parent.position[1]==14&&i==0){
                    System.out.println("b1:"+b1);
                    System.out.println("b2:"+b2);
                    System.out.println("b3:"+b3);
                    System.out.println("b4:"+b4);
                    System.out.println("map["+a+"]["+b+"] = "+map[a][b]);
                    System.out.println("map["+c+"]["+d+"] = "+map[c][d]);
                    System.out.println("map["+e+"]["+f+"] = "+map[e][f]);
                    System.out.println(" ");
                }*/
                if (b1&&(b2||b3)&&b4){
                    int[] cPos = {a,b};
                    int[][] mark = copyArr(parent.mark);
                    mark[a][b] = 1;
                    int priority = Math.abs(a-ending[0])+Math.abs(b-ending[1]);
                    Node child = new Node(parent,i,cPos,parent.dist+1,mark,priority);

                    //System.out.println("map["+a+"]["+b+"] = "+map[a][b]);
                    for (int j = 0; j < 4; j++) {
                        if (a+dirX[j]==ending[0]&&b+dirY[j]==ending[1]){
                            optimalV = child;
                            optimalV.dist+=1;
                            //System.out.println("map["+a+"]["+b+"] = "+map[a][b]);
                            return;
                        }
                    }
                   /* if (a==0&&b==14){
                        System.out.println("map["+a+"]["+b+"] = "+map[a][b]);
                        System.out.println("mark["+a+"]["+b+"] = "+mark[a][b]);
                        System.out.println("mark["+a+"]["+(b-2)+"] = "+mark[a][b-2]);
                        System.out.println(child.priority);
                        System.out.println("ending:["+ending[0]+","+ending[1]+"]");
                        System.out.println(child.dist);
                    }*/
                    liveNode.offer(child);
                }
            }
        }

    }
    private static int[][] copyArr(int[][] mark){
        int[][] temp = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                temp[i][j] = mark[i][j];
            }
        }
        return temp;
    }

    public static void main(String[] args) {

        addBuilding(new int[]{5,6},4,8,new int[]{0,3});
        addBuilding(new int[]{10,1},4,9,new int[]{2,8});
        addBuilding(new int[]{1,1},8,4,new int[]{3,2});
        addBuilding(new int[]{10,11},4,3,new int[]{0,3});
        addBuilding(new int[]{1,10},3,4,new int[]{2,3});
        addBuilding(new int[]{1,6},3,3,new int[]{1,2});

        int s = 4;
        int f = 5;
        getDistance(s,f);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (map[j][i]<0){
                    System.out.print(map[j][i]+" ");
                }else {
                    System.out.print(" "+map[j][i]+" ");
                }
                if (j==n-1){
                    System.out.println();
                }
            }
        }
        System.out.println("从城市"+s+"到城市"+f+"的最短距离为："+optimalV.dist);
        //System.out.println(optimalV.dir);
    }
}
