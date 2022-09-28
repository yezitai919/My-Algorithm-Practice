public class Demo01{
public static void main(String[] args) {
    //点数
    int n = 10;
    //边的权值
    int[][] edge = new int[n+1][n+1];
    edge[1][2] = 3; edge[1][3] = 8; edge[1][4] = 5;
    edge[2][5] = 13; edge[2][6] = 3;
    edge[3][5] = 6; edge[3][7] = 5;
    edge[4][6] = 7; edge[4][7] = 4;
    edge[5][8] = 4; edge[5][9] = 2;
    edge[6][8] = 3; edge[6][9] = 3;
    edge[7][9] = 8;edge[8][10] = 7;edge[9][10] = 9;
    //选一个源点
    int sourcePoint = 1;
    getRoute(edge,sourcePoint,n);
}
private static void getRoute(int[][]edge,int sourcePoint,int n){
    //当前要搜索的点
    int currPoint = sourcePoint;
    //途经点集
    ArrayList<Integer> s = new ArrayList<>();
    //判断某点是否已经确定
    boolean[] bool = new boolean[n+1];
    //记录源点到其他点的距离
    int[] dist = new int[n+1];
    //循环n次每次确定一个点到源点的最近距离
    for (int i = 1; i <= n; i++) {
        //初始化 所有点间距为无限大
        dist[i]=Integer.MAX_VALUE;
    }
    //源点距离为0
    dist[sourcePoint] = 0;
    //源点放入s[]并确定
    bool[sourcePoint]=s.add(currPoint);
    for (int i = 0; i < n; i++) {
        //遍历边数组
        for (int j = 1; j <=n ; j++) {
            //找到当前点cp直接相距点，放入s[]的点除外
            if (edge[currPoint][j]!=0&&!bool[j]) {
                //记录源点经过当前点到i点的距离，因为能到i点的点不止一个，当前点到i点不一定最近，所以先判断大小再赋值
                dist[j]=Math.min(dist[j],dist[currPoint]+edge[currPoint][j]) ;
            }
        }//工具变量
        int a=Integer.MAX_VALUE, b=0;
        //遍历dist数组，找出距端点最近并且不在s集中的点b，则b点到源点最近距离已确定
        for (int j = 1; j <= n; j++) {
            if (dist[j] < a && !bool[j]){
                a=dist[j];b=j; }
        }//把b点放入s集并确定
        bool[b] = s.add(b);
        //更新当前搜索点
        currPoint=b;
    }//打印每个点到源点的最近距离
    System.out.println(Arrays.toString(dist));
}
}
