import java.util.Arrays;

public class Demo01 {
    public static void main(String[] args) {
        System.out.println("3x3价值矩阵：");
        //3x3价值矩阵 (可手动修改)
        int[][] p1 = {
                {1,2,3},
                {4,5,6},
                {7,8,9}
        };
        //调用方法计算价值链
        countValue(p1);
        System.out.println("  ");



        System.out.println("5x5价值矩阵：");
        //5x5价值矩阵 (可手动修改)
        int[][] p2 = {
                {1,2,3,4,5},
                {6,7,8,9,10},
                {11,12,13,14,15},
                {16,17,18,19,20},
                {21,22,23,24,25}
        };
        //调用方法计算价值链
        countValue(p2);
        System.out.println("  ");


        System.out.println("10x10价值矩阵：");
        //10x10价值矩阵 (可手动修改)
        int[][] p3 = {
                {1,2,3,4,5,6,7,8,9,10},
                {11,12,13,14,15,16,17,18,19,20},
                {21,22,23,24,25,26,27,28,29,30},
                {31,32,33,34,35,36,37,38,39,40},
                {41,42,43,44,45,46,47,48,49,50},
                {51,52,53,54,55,56,57,58,59,60},
                {61,62,63,64,65,66,67,68,69,70},
                {71,72,73,74,75,76,77,78,79,80},
                {81,82,83,84,85,86,87,88,89,90},
                {91,92,93,94,95,96,97,98,99,100}
        };
        //调用方法计算价值链
        countValue(p3);
    }

    static int count = 0;
    private static void countValue(int[][] p){

        int size = p[0].length;
        int kNum = factorial(size-1);
        int[][] valueChain = new int[kNum][size];
        int[] array = new int[size-1];

        for (int k = 0; k < size; k++) {
            for (int[] ints : valueChain) {
                ints[size-1]=k;
            }
            int num = 0;
            for (int i = 0; i < array.length; i++) {
                if (i==k){
                    num++;
                }
                array[i]=i+num;
            }
            fullPermutation(array,0,size-1,valueChain);
            count=0;
            int maxValue = 0;
            int maxId = 0;
            for (int i = 0; i < kNum; i++) {
                int value = 0;
                for (int j = 0; j < size-1; j++) {
                    value+=p[valueChain[i][j]][valueChain[i][j+1]];
                }
                if (value>maxValue){
                    maxValue=value;
                    maxId = i;
                }
            }
            System.out.println("个体"+(k+1)+"的最大共享价值链:");
            for (int i : valueChain[maxId]) {
                System.out.print(i+1+"  ");
            }
            System.out.println(" ");
            System.out.println("最大共享价值："+maxValue);
        }
    }

    /**
     * 求阶乘
     * @param n
     * @return
     */
    private static int factorial(int n){
        if (n<=1){
            return 1;
        }
        return n*factorial(n-1);
    }
    /** 输入一个数组，求该数组所有元素的全排列
     * @param arr 数组
     * @param index 当前要确定的元素的数组下标，从0开始。
     * @param length 数组长度
     * T(n)=O(n!),S(n)=O(n)
     */
    private static void fullPermutation(int[] arr, int index, int length,int[][]valueChain){
    /*如果当前下标到达数组最后一个元素，则打印当前数组，结束本次递归。
    因为第一次调用本方法获得n种排列，第二次调用获得n×(n-1)种，
    最后一次才是获得n!种,所以在最后一次打印才不会重复。*/
        if(index>=length-1){
           System.arraycopy(arr,0,valueChain[count],0,valueChain[count].length-1);
            count++;
        } else{
            for(int i = index;i<length;i++){
                //交换元素位置
                swap(arr,index,i);
                //每次递归下标+1
                fullPermutation(arr,index+1,length,valueChain);
            /*复原为下次循环做准备，每次循环只确定一个元素，
            其他不变才不会出现重复。*/
                swap(arr,index,i);
            }
        }
    }

    /** 数组元素交换方法
     * @param a 数组
     * @param i 交换元素1
     * @param j 交换元素2
     * T(n)=O(1),S(n)=O(1)
     */
    private static void swap(int[] a, int i, int j){
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
