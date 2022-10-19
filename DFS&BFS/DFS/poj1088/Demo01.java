package poj1088;

import java.util.Scanner;

/**
 * @Author jinjun99
 * @Date Created in 2022/9/30 10:28
 * @Description
 * @Since version-1.0
 */
public class Demo01 {
    static int n = 0;
    static int n1 = 0;
    static int max = 0; // 存放当前这跳路的最大值
    static int arr[][]; // 滑雪数组
    static int ji[][]; // 标记数组
    static int zou1[] = { 1, -1, 0, 0 }; // 移动
    static int zou2[] = { 0, 0, 1, -1 };
    static int max2 = 0; // 输出结果 全局路的最大指

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Scanner sc = new Scanner(System.in);

        while (sc.hasNext()) {
            max2 = 0;
            n = sc.nextInt(); // 数组的长和宽
            n1 = sc.nextInt();
            arr = new int[n][n1];
            ji = new int[n][n1];
            for (int i = 0; i < arr.length; i++) { // 初始化
                for (int k = 0; k < arr[i].length; k++) {
                    arr[i][k] = sc.nextInt();
                    ji[i][k] = -1;
                }
            }
            for (int i = 0; i < arr.length; i++) {
                for (int k = 0; k < arr[i].length; k++) {
                    max = 0;
                    sou(i, k, 1); //找出i k 这个点的最大距离 赋值给 max

                    ji[i][k] = max; //将 max 记录在标记数组中

                    if (max > max2) { //筛选最大的标记数组
                        max2 = max;
                    }

                    max = 0;
                }

            }
            System.out.println(max2);
        }

    }

    static void sou(int i, int j, int sum) { // 通过这个方法每回合 可以找出 i j坐标的开始滑雪的最大值

        if (sum >= max) {
            max = sum;
        }
        for (int q = 0; q < 4; q++) {
            int x1 = i + zou1[q]; //移动
            int y1 = j + zou2[q];
            if ((x1 >= 0 && x1 < n) && (y1 >= 0 && y1 < n1) && (arr[i][j] > arr[x1][y1])) {
                if (ji[x1][y1] == -1) {//如果这个位置没有走过 那就接着搜
                    sou(x1, y1, sum + 1);
                } else { // 如果这个地方走过了 那就直接获取 这个位置的最大距离与之前走过的和
                    int aq = sum;
                    sum = sum + ji[x1][y1];
                    if (sum >= max) {
                        max = sum;
                    }
                    sum = aq; //之前的操作改变了sum 所以这个步骤是为了返还 sum数值

                }
            }
        }
    }
}
