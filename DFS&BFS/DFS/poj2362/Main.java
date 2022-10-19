package poj2362;


import java.util.Scanner;

/**
 * @Author jinjun99
 * @Date Created in 2022/10/5 13:22
 * @Description
 * 用到了定序剪枝，遍历正方形的一条边时，是从n根小棒中选几根，前面跳过的后面不会再挑，
 * 将本次选择的小棒编号i加上1传给下一递归，下次递归接着从i+1开始遍历小棒数组。
 * 完成当前边再把下标调回0。
 * @Since version-1.0
 */
public class Main {
    static int side;
    static int n;
    static int[] sticks;
    static int[] check;

    static int succeed;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();

        for (int i = 0; i < num; i++) {
            n = sc.nextInt();
            sticks = new int[n + 5];
            check = new int[n + 5];
            for (int j = 0; j < n; j++) {
                sticks[j] = sc.nextInt();
            }
            int sum = 0;
            int max = 0;
            for (int j = 0; j < n; j++) {
                sum += sticks[j];
                if (sticks[j]>max){
                    max = sticks[j];
                }
            }
            side = sum / 4;
            if (sum % 4 == 0 && side >= max) {
                succeed = 0;
                dfs(0,0, 0,0);
                if (succeed == 1) {
                    System.out.println("yes");
                } else {
                    System.out.println("no");
                }
            } else {
                System.out.println("no");
            }
        }
    }

    /**
     * @param currLen   当前长度
     * @param finishNum 当前完成棍子数
     */
    private static void dfs(int index,int currLen, int finishNum,int layer) {

        if (succeed==0){
            for (int i = index; i < n; i++) {
                if (currLen + sticks[i] < side && check[i] == 0) {
                    currLen += sticks[i];
                    check[i] = 1;

                    dfs(i+1,currLen, finishNum,layer+1);
                    check[i] = 0;
                    currLen -= sticks[i];
                }
                if (currLen + sticks[i] == side && check[i] == 0) {
                    finishNum++;
                    check[i] = 1;
                    if (finishNum == 3) {
                        succeed = 1;
                        return;
                    }
                    dfs(0,0, finishNum,layer+1);
                    finishNum--;
                    check[i] = 0;
                }

            }
        }

    }

}
/*
3
4 1 1 1 1
5 10 20 30 40 50
8 1 7 2 6 4 4 3 5
*/
/*String str = sc.nextLine();
            int len = str.length();
            n = 0;
            int temp = 0;
            sticks = new int[len/2+5];
            check = new int[len/2+5];
            for (int j = 0; j < len; j++) {
                char a = str.charAt(j);
                if (a!=' '){
                    if (temp==0){
                        temp = a-'0';
                        n++;
                    }else {
                        temp = temp*10+(a-'0');
                    }
                    sticks[n-1] = temp;

                }else {
                    temp=0;
                }
            }*/