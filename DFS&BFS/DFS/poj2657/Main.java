package poj2657;

import java.util.Scanner;

/**
 * @Author jinjun99
 * @Date Created in 2022/9/27 16:32
 * @Description
 * @Since version-1.0
 */
public class Main {
    /**
     * 圆环格数
     */
    static int n;
    /**
     * 目标编号
     */
    static int z;
    /**
     * 阻碍标记数组
     */
    static boolean[] m;

    /**
     * 找到目标
     */
    static boolean succeed;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()){
            String str = sc.nextLine();
            String[] s = str.split(" ");
/*            if (s[0]==" "){
                break;
            }*/
            n = Integer.parseInt(s[0]);
            z = Integer.parseInt(s[1]);
            m = new boolean[n];
            int t = Integer.parseInt(s[2]);
            if (t!=0){
                String str2 = sc.nextLine();
                String[] s2 = str2.split(" ");
                /*标记阻碍格*/
                for (int i = 0; i < t; i++) {
                    m[Integer.parseInt(s2[i])-1]=true;
                }
            }
            succeed = false;
            for (int i = 1; i < n; i++) {
                dfs(0,i);
                if (succeed){
                    System.out.println(i);
                    break;
                }
            }

        }
    }

    /**
     * @param index 当前下标
     * @param k 当前子树的k
     */
    private static void dfs(int index, int k) {
        /*找到目标*/
        if (index==z-1){
            succeed = true;
            return;
        }
        /*遇到阻碍块或者重复块(说明会一直重复跳不到k)，直接结束当前分支*/
        if (m[index]){
            return;
        }
        /*标记当前块*/
        m[index]=true;
        /*跳跃下一步，如果出界就求余循环*/
        if (index+k<n){
            dfs(index+k,k);
        }else {
            dfs((index+k)%(n-1),k);
        }
        /*回溯*/
        m[index]=false;
    }
}
/*这题一时没看出来和dfs有啥关系，感觉像是循环就能解决的事，后来发现它是章鱼一样的树，
第一层有n个分支，从第二层开始每个子树都只有一个分支，并且是从左到右一个分支走到低再回溯走第二个。
循环确实效率更高，但是数据量不大时用dfs可以更方便传递数据和回溯数据。这题加深了我对dfs的理解。
9 7 2
2 3
13 9 2
7 8
*/