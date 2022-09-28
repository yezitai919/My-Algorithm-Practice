public class Demo01{
public static void main(String[] args) {
    //点数
    int n = 6;
    //无向图G的顶点集合v
    int[] vertex = new int[n+1];
    //无向图G的边集合E
    int[][] edge = new int[n+1][n+1];
    //最小生成树MST的边集合，点集不用变。
    int[][] mstEege = new int[n+1][n+1];
    inputGraph(vertex,edge,mstEege);//输入图数据
    kruskal(edge,mstEege);//计算最小生成树的边
    int weightOfMST = 0;//最小生成树的总权重
    System.out.println("最小生成树为：");
    for (int i = 1; i <= n; i++) {
        ArrayList<Integer> arr = new ArrayList<>();
        for (int j = 1; j <= n; j++) {
            if (mstEege[i][j]!=Integer.MAX_VALUE && i<j){
                weightOfMST +=mstEege[i][j];
                arr.add(j);
            }
        }
        if (arr.size()!=0) {
            System.out.println(i+"———"+arr);
        }
    }
    System.out.println("最小生成树的总权重为："+ weightOfMST);
}
private static void inputGraph(int[] vertex,int[][] edge,int[][] mstEege){
    for (int i = 0; i < 7; i++) {
        vertex[i] = i;//输入G图点数据
        for (int j = 0; j < 7; j++) {
            edge[i][j] = Integer.MAX_VALUE;//G图的边初始化
            mstEege[i][j] = Integer.MAX_VALUE;//MST的边初始化
        }
    }
    //输入边数据
    edge[1][2]=10; edge[2][1]=10; edge[1][3]=21; edge[3][1]=21;
    edge[1][5]=8;  edge[5][1]=8;  edge[2][3]=18; edge[3][2]=18;
    edge[2][4]=5;  edge[4][2]=5;  edge[2][6]=6;  edge[6][2]=6;
    edge[3][5]=25; edge[5][3]=25; edge[3][6]=19; edge[6][3]=19;
    edge[4][6]=7;  edge[6][4]=7;  edge[5][6]=33; edge[6][5]=33;
}
private static void prim (int n,int[] vertex,int[][] edge,int[][] mstEege){//加点法
    int[] node = new int[n+1];//临时点集
    boolean bool [] = new boolean[n+1];//判断是结点是否确定
    node[1] = vertex[5];//在vertex中随便选个点放入node[1]作为第一个点
    bool[5] = true;
    for (int i = 2; i <= n; i++) {
        /*最小生成树剩下5个节点未确定，每次循环确定一个点，依次放入node[i]，所以i要从2开始*/
        int e = Integer.MAX_VALUE;//记录当前最小的边的值
        int a = 0;//记录node中与当前最小边相连的点
        int b = 0;//记录与当前最小边相连的另一点，要求在vertex中且不在node中。
        for (int j = 1; j <= n; j++) {//遍历vertex中的所有点
            if (node[j]!=0) {//找到node里的点node[j]
                //再遍历vertex中的所有点找到所有与node[j]直接相连的点
                for (int k = 1; k <= n; k++) {
                    //找到最小边edge[node[j]][k]以及距离node[j]最近的点k
                    if (edge[node[j]][k]<e && !bool[k]){
                        e = edge[node[j]][k];//记录当前最小边以及对应点node[j]和K
                        a = node[j];
                        b = k;
                    }
                }
            }
        }
        node[i] = b;//把距node中某点最近的点放入node
        bool[b] = true;
        mstEege[a][b] = e;//记录MST的一条边
        mstEege[b][a] = e;
    }
}
private static void kruskal(int n,int[][] edge,int[][] mstEege){//加边法
            /*初始时每个点作为根节点分别构成一个子树，
             subtree[i]表示第i号结点所在的子树*/
    int[] subtree = new int[n+1];
    boolean bool [][] = new boolean[n+1][n+1];//判断边是否确定
    for (int i = 0; i <= n; i++) {
        subtree[i] = i;//输入每个子树根节点
    } //最小生成树剩下n-1条边未确定,每次循环确定一条放入tree
    for (int i = 0; i < n-1; i++) {
        int e = Integer.MAX_VALUE;//记录当前最小的边的权重
        int a = 0;//记录当前最小边相连的两个点
        int b = 0;
        for (int j = 1; j <= n; j++) {
            for (int k = 1; k <= n; k++) {
                //找到最小边edge[j][k]，要求该边连接的两个点j和k不在同一子树上
                if (edge[j][k]<e && !bool[j][k] &&subtree[j]!=subtree[k]){
                    e = edge[j][k];//记录当前最小边以及对应的点j和k
                    a = j;
                    b = k;
                }
            }
        }
        for (int j = 1; j <= n; j++) {
            if (subtree[j] == b) {
                subtree[j] = subtree[a];//合并子树
            }
        }
        bool[a][b] = true;//确定一条边
        bool[b][a] = true;
        mstEege[a][b] = e;//记录MST的一条边
        mstEege[b][a] = e;
    }
}
}
