public class Demo01{
/**在正整数升序数组a中寻找两个和为x的元素并打印结果
 * @param a
 * @param x
 * T(n)=O(n),S(n)=O(1)
 */
private static void sum(int[]a,int x){
    int min = 0;
    int max = a.length - 1;
    while(min < max) {
        int s = a[min] + a[max];
        //如果和大于x说明当前最大数加上arr[min]之后的小数都比x大
        if (s > x) {
            max--;
            //如果和小于x说明当前最小数加上arr[max]之前的数都比x小
        } else if (s < x) {
            min++;
        } else if (s == x){
            System.out.println("["+a[min]+","+a[max]+"]");
            break;
        }
    }
    if (min == max) {
        System.out.println("数组中没有两个元素和等于x");
    }
}
/*复杂度分析：长度为n的数组每个元素只扫描了一次，最多循环n次，所以T(n)=O(n)，方法只声明了两个变量，S(n)=O(1)*/
}
