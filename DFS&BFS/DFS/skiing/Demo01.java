package skiing;

import java.util.Stack;

/**
 * @Author jinjun99
 * @Date Created in 2021/11/19 18:37
 * @Description
 * @Since version-1.0
 */
public class Demo01 {
    private static int r = 5;
    private static int c = 5;
    private static int currLen = 0;
    private static int maxLen = 0;
    private static int[][] heightMap = {
            {1 , 2, 3, 4, 5},
            {16,17,18,19, 6},
            {15,24,25,20, 7},
            {14,23,22,21, 8},
            {13,12,11,10, 9}};
    private static int[][] count = new int[r][c];
    public static void main(String[] args) {
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                currLen= backTrack(i,j,i,j);
                if (currLen>maxLen){
                    maxLen = currLen;
                }
            }
        }
        System.out.println(count[2][2]);
        System.out.println(maxLen);
    }
    private static int backTrack(int i,int j,int k,int l){
        if (count[k][l]!=0){
            return count[k][l];
        }
        boolean b=true;
        if (i<r-1&&heightMap[i+1][j]<heightMap[i][j]){
            currLen = 1+backTrack(i+1,j,k,l);
            if (count[k][l]<currLen){
                count[k][l]=currLen;
            }
        }

        if (i>0&&heightMap[i-1][j]<heightMap[i][j]){
            currLen = 1+backTrack(i-1,j,k,l);
            if (count[k][l]<currLen){
                count[k][l]=currLen;
            }
        }
        if (j<c-1&&heightMap[i][j+1]<heightMap[i][j]){
            currLen = 1+backTrack(i,j+1,k,l);
            if (count[k][l]<currLen){
                count[k][l]=currLen;
            }
        }
        if (j>0&&heightMap[i][j-1]<heightMap[i][j]){
            currLen = 1+backTrack(i,j-1,k,l);
            if (count[k][l]<currLen){
                count[k][l]=currLen;
            }
        }
        return count[k][l];
    }
}
