package burning_trees;

/**
 * @Author jinjun99
 * @Date Created in 2021/12/7 11:05
 * @Description
 * @Since version-1.0
 */
public class PointArrays {
    int[][] arr = new int[100][2];
    int index = 0;
    int size = 0;
    public void add(int[] pos){
        arr[index][0] = pos[0];
        arr[index][1] = pos[1];
        index++;
        size++;
    }
    public int[] get(int i){
        return arr[i];
    }

}
