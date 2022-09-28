package poj3009;

import java.util.Scanner;

/**
 * @Author jinjun99
 * @Date Created in 2022/9/25 17:07
 * @Description
 * @Since version-1.0
 */
public class Main {
    /**
     * 场地宽
     */
    static int w;
    /**
     * 场地高
     */
    static int h;
    /**
     * 游戏场地
     */
    static int[][] gameBoard;
    /**
     * 起点x
     */
    static int sx;
    /**
     * 起点y
     */
    static int sy;
    /**
     * 终点x
     */
    static int gx;
    /**
     * 终点y
     */
    static int gy;
    /**
     * 当前投掷次数
     */
    private static int currThrowsNum;
    /**
     * 最小投掷次数
     */
    private static int minThrowsNum;
    /**
     * 方向数组
     */
    private static int[] dirX = {0,1,0,-1};
    private static int[] dirY = {1,0,-1,0};
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()){
            w = sc.nextInt();
            h = sc.nextInt();
            if (w==0&&h==0){
                break;
            }
            gameBoard = new int[w][h];

            /*读取上面那行的末尾空格*/
            String str = sc.nextLine();
            for (int i = 0; i < h; i++) {
                /*读取下一行数字*/
                str = sc.nextLine();
                /*按空格分割成数组*/
                String[] s = str.split(" ");
                for (int j = 0; j < s.length; j++) {
                    /*字符转整数*/
                    int temp = Integer.parseInt(s[j]);
                    gameBoard[j][i]= temp;
                    /*判断起点位置*/
                    if (temp==2){
                        sx = j;
                        sy = i;
                    }/*判断终点位置*/
                    if (temp==3){
                        gx = j;
                        gy = i;
                    }
                }
            }
            currThrowsNum = 0;
            minThrowsNum = -1;
            dfs(sx,sy);
            System.out.println(minThrowsNum);
        }
    }



    /**
     * @param x 当前位置x坐标
     * @param y 当前位置y坐标
     */
    private static void dfs(int x,int y) {
        /*往四个方向搜索*/
        for (int i = 0; i < 4; i++) {
            /*当前冰壶移动到的位置*/
            int cx = x;
            int cy = y;
            /*移动格数*/
            int cellsNum = 0;
            /*试探移动格数*/
            for (int j = 0; j <= Math.max(w, h); j++) {
                cx += dirX[i];
                cy += dirY[i];
                cellsNum++;
                /*如果当前方向会出界则直接跳出循环换个方向*/
                if(cx<0||cx>=w||cy<0||cy>=h){
                    break;
                }
                /*如果当前方向和阻碍块接触直接跳出循环换个方向*/
                if (gameBoard[cx][cy]==1&&cellsNum==1){
                    break;
                }
                /*如果到达终点，则更新最小移动次数*/
                if (cx==gx&&cy==gy){
                    /*当前投掷次数+1*/
                    currThrowsNum++;
                    if (currThrowsNum<=10&&(minThrowsNum ==-1|| currThrowsNum < minThrowsNum)){
                        minThrowsNum = currThrowsNum;
                    }
                    currThrowsNum--;
                    /*到达终点就没必要再往其他方向走了，当前子树结束了*/
                    break;
                }

                /*如果撞到阻碍块且符合条件*/
                if (gameBoard[cx][cy]==1&&cellsNum>1){
                    /*当前投掷次数+1*/
                    currThrowsNum++;
                    /*投掷次数=10,直接回溯到上一步换个方向*/
                    if (currThrowsNum==10){
                        currThrowsNum--;
                        break;
                    }
                    /*当前阻碍块消失a*/
                    gameBoard[cx][cy] = 0;
                    /*后退一步*/
                    cx -= dirX[i];
                    cy -= dirY[i];
                    dfs(cx,cy);
                    /*回溯; */
                    currThrowsNum--;
                    cx += dirX[i];
                    cy += dirY[i];
                    gameBoard[cx][cy] = 1;
                    /*跳出当前方向*/
                    break;
                }
            }
        }
    }
}

/* 找出错误的关键代码：System.out.println("跳出：currTN:"+
                currThrowsNum+"cell:"+cellsNum+"("+cx+","+cy+")="+
                gameBoard[cx][cy]+"当前方向："+i);*/
/*想着cx和cy在外层循环回溯了，就懒得写了，结果地图回溯时因为坐标未回溯改变了地图*/