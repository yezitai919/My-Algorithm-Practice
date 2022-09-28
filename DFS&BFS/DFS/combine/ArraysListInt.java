package combine;

/**
 * @Author jinjun99
 * @Date Created in 2021/12/3 15:12
 * @Description 自己写的int型ArraysList
 * @Since version-1.0
 */
public class ArraysListInt {
    private static int[][] arr = new int[1000][1000];
    private static int index = 0;
    public void add(int[] a , int n){
        arr[index][0] = n;
        for (int i = 0; i < n; i++) {
            arr[index][i+1] = a[i];
        }
        index++;
    }
    public int[] get(int i){
        int n = arr[i][0]+1;
        int[] t = new int[n];
        for (int j = 0; j < n; j++) {
            t[j] = arr[i][j];
        }
        return t;
    }
    public int size(){
        return index;
    }
    public int[] remove(int i){
        int n = arr[i][0]+1;
        int[] t = new int[n];
        for (int j = 0; j < n; j++) {
            t[j] = arr[i][j];
            arr[i][j] = 0;
        }
        return t;
    }
}
