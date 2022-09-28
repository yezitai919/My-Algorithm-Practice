package arrays_sort;

import java.util.Arrays;

/**
 * @Author jinjun99
 * @Date Created in 2021/11/29 18:13
 * @Description
 * @Since version-1.0
 */
public class ArrSort {
    public static void main(String[] args) {
        int[] arr1 = {3,2,8,4,6,7,1,5,9,23,25,36,18};
        //bubbleSort(arr1);
        //selectionSort(arr1);
        //insertionSort(arr1);
        int len = arr1.length-1; 
        //quickSort1(arr1, 0, len);
        int[] arr2 = new int[arr1.length];
        mergeSort(arr1, arr2, 0, len);
        System.out.println(Arrays.toString(arr1));
    }

    public static void bubbleSort(int[] arr) {
        int len = arr.length;
        for (int i = 0; i < len-1; i++) {
            for (int j = 0; j < len-1-i; j++) {
                if (arr[j]>arr[j+1]) {
                    int temp = arr[j];
                    arr[j]=arr[j+1];
                    arr[j+1]=temp;
                }
            }
        }
    }
    /*
     *
     */
    public static void selectionSort(int[] arr) {
        int len = arr.length;
        int minIndex = 0;
        int temp = 0;
        for (int i = 0; i < len-1; i++) {
            minIndex = i;
            for (int j = i+1; j < len; j++) {
                if (arr[j]<arr[minIndex]) {
                    minIndex = j;
                }
            }
            temp = arr[i];
            arr[i]=arr[minIndex];
            arr[minIndex] = temp;
        }

    }

    public static void insertionSort(int[] arr) {
        int preIndex = 0;
        int currValue = 0;
        for (int i = 1; i < arr.length; i++) {
            preIndex = i-1;
            currValue = arr[i];
            while (preIndex>=0&&arr[preIndex]>currValue) {
                arr[preIndex+1]=arr[preIndex];
                preIndex--;
            }
            arr[preIndex+1]=currValue;
        }
    }



    public static void quickSort1(int[] arr,int min,int max) {
        if (min<max) {
            int standard = arr[min];
            int i = min;
            int j = max;
            while (i<j) {
                while (i<j&&arr[j]>=standard) {
                    j--;
                }
                arr[i] = arr[j];
                while (i<j&&arr[i]<=standard) {
                    i++;
                }
                arr[j] = arr[i];
            }
            arr[i] = standard;
            quickSort1(arr, min, i-1);
            quickSort1(arr, i+1, max);

        }

    }

    private static void quickSort(int[] arr, int low, int high) {

        if (low < high) {
            // 找寻基准数据的正确索引
            int index = getIndex(arr, low, high);

            // 进行迭代对index之前和之后的数组进行相同的操作使整个数组变成有序
            //quickSort(arr, 0, index - 1); 之前的版本，这种姿势有很大的性能问题，谢谢大家的建议
            quickSort(arr, low, index - 1);
            quickSort(arr, index + 1, high);
        }

    }

    private static int getIndex(int[] arr, int low, int high) {
        // 基准数据
        int tmp = arr[low];
        while (low < high) {
            // 当队尾的元素大于等于基准数据时,向前挪动high指针
            while (low < high && arr[high] >= tmp) {
                high--;
            }
            // 如果队尾元素小于tmp了,需要将其赋值给low
            arr[low] = arr[high];
            // 当队首元素小于等于tmp时,向前挪动low指针
            while (low < high && arr[low] <= tmp) {
                low++;
            }
            // 当队首元素大于tmp时,需要将其赋值给high
            arr[high] = arr[low];

        }
        // 跳出循环时low和high相等,此时的low或high就是tmp的正确索引位置
        // 由原理部分可以很清楚的知道low位置的值并不是tmp,所以需要将tmp赋值给arr[low]
        arr[low] = tmp;
        return low; // 返回tmp的正确位置
    }

    /**归并排序递归算法
     * @param originalArray 原数组
     * @param tempArray 辅助合并的数组，要求输入一个和原数组等长的空数组
     * @param minIndex 当前(子)数组的首下标
     * @param maxIndex 当前(子)数组的尾下标
     * T(n)=O(nlogn),S(n)=O(n)
     */
    private static void mergeSort(int[] originalArray,int[] tempArray, int minIndex ,int maxIndex) {
        //子数组已经最小，结束递归
        if (minIndex>=maxIndex) {
            return;
        }
        //求当前(子)数组的中间下标值
        int midIndex = (minIndex+maxIndex)/2;
        //左半部分递归排序
        mergeSort(originalArray, tempArray, minIndex,midIndex);
        //右半部分递归排序
        mergeSort(originalArray, tempArray, midIndex+1,maxIndex);
        //将左半部分和右半部分合并
        mergeArrays(originalArray,tempArray,minIndex,midIndex,maxIndex);
    }
    /**
     * @param originalArray 原数组
     * @param tempArray 辅助合并的数组
     * @param minIndex 当前(子)数组的首下标
     * @param midIndex 当前(子)数组的中下标
     * @param maxIndex 当前(子)数组的尾下标
     * T(n)=O(n),S(n)=O(1)
     */
    private static void mergeArrays(int[] originalArray, int[] tempArray, int minIndex, int midIndex, int maxIndex) {
        //辅助数组的首下标
        int tempMin = minIndex;
        //左子数组的首下标
        int leftMin = minIndex;
        //右子数组的首下标
        int rightMin = midIndex + 1 ;
        //如果当前子数组只有一个元素则不能合并。
        if (leftMin >= rightMin ) {
            return;
        }
        //循环条件是左或右子数组长度不为0
        while (leftMin != midIndex + 1 || rightMin != maxIndex + 1){
            if (leftMin == midIndex + 1){
                //如果左子数组长度为0，把右子数组顺序放入辅助数组，下标自增表示当前元素已被取出。
                tempArray[tempMin++] = originalArray[rightMin++];
            }else if (rightMin == maxIndex + 1){
                //如果右子数组长度为0，把左子数组顺序放入辅助数组
                tempArray[tempMin++] = originalArray[leftMin++];
            }else if (originalArray[leftMin ] <= originalArray[rightMin ]){
                //如果右子数组首元素>左子数组首元素，把右子数组首元素放入辅助数组
                tempArray[tempMin++] = originalArray[leftMin++];
            }else {
                //否则把左子数组首元素放入辅助数组
                tempArray[tempMin++] = originalArray[rightMin++];
            }
        }
        //当原数组的所有元素都按顺序放入辅助数组后，将辅助数组复制到原数组
        System.arraycopy(tempArray, 0, originalArray, 0, maxIndex + 1);
    }



}
