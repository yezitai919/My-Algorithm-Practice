package skiing;

import java.util.Stack;

/**
 * @Author jinjun99
 * @Date Created in 2021/11/19 21:37
 * @Description
 * @Since version-1.0
 */
public class Demo02 {
    private static int r = 5;
    private static int c = 5;
    private static int[][] heightMap = {
            {1 , 2, 3, 4, 5},
            {16,17,18,19, 6},
            {15,24,25,20, 7},
            {14,23,22,21, 8},
            {13,12,11,10, 9}};


    private static Stack<Integer> currPath = new Stack<>();
    private static Stack<Integer> shortestPath = new Stack<>();

    public static void main(String[] args) {

        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                backTrack(i,j);
            }
        }
        System.out.println("最大长度："+shortestPath.size());
        System.out.println("最长路径：");
        System.out.println(shortestPath);

    }

    private static void backTrack(int i,int j){
        currPath.push(heightMap[i][j]);

        int b=1;
        if (i<r-1&&heightMap[i+1][j]<heightMap[i][j]){
            backTrack(i+1,j);
            b=0;
        }

        if (i>0&&heightMap[i-1][j]<heightMap[i][j]){
            backTrack(i-1,j);
            b=0;
        }
        if (j<c-1&&heightMap[i][j+1]<heightMap[i][j]){
            backTrack(i,j+1);
            b=0;
        }
        if (j>0&&heightMap[i][j-1]<heightMap[i][j]){
            backTrack(i,j-1);
            b=0;
        }

        if (b==1&&currPath.size()>shortestPath.size()){
            shortestPath = (Stack<Integer>) currPath.clone();
        }
        currPath.pop();
    }
}
/*
最大长度：25
最长路径：
[25, 24, 23, 22, 21, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1]
*/