package full_permutation;

import java.util.Arrays;

/**
 * @Author jinjun99
 * @Date Created in 2021/11/20 13:21
 * @Description
 * @Since version-1.0
 */
public class Demo01 {
    public static void main(String[] args) {
        int[] arr = {1,2,3};
        fullPermutation(arr,0,arr.length);
    }
    private static void fullPermutation(int[] arr,int index,int length){
        if (index>=length-1){
            System.out.println(Arrays.toString(arr));
        }else {
            for (int i = index; i < length; i++) {
                swap(arr,index,i);
                fullPermutation(arr,index+1,length);
                swap(arr,index,i);
            }
        }
    }
    private static void swap(int[] arr,int i,int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
