package friends;

/**
 * @Author jinjun99
 * @Date Created in 2021/11/20 11:12
 * @Description 通过社交图找到一个人的所有朋友，朋友的朋友也是朋友。
 * @Since version-1.0
 */
public class Demo01 {
    private static String[] name = {"lxh","zzx","lrz","cy"};
    private static int[][] friendship = {
        {0,1,1,1},
        {1,0,1,0},
        {1,1,0,0},
        {1,0,0,0}};
    private static int n = 4;
    private static int[][] sign = new int[n][n];
    private static int[] signFri = new int[n];
    private static int sum = 0;
    private static void dfs(int p){
        for (int i = 0; i < n; i++) {
            if (signFri[i]==0&&friendship[p][i]==1){
                signFri[i] = 1;
                sum+=1;
                dfs(i);
            }
        }
    }
    public static void main(String[] args) {
        signFri[0]=1;
         dfs(0);
        System.out.println(sum);
    }

}
