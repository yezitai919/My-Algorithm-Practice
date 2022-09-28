package tetris;

/**
 * @Author jinjun99
 * @Date Created in 2021/11/22 15:44
 * @Description
 * @Since version-1.0
 */
public class Deom01 {
    private static int weigth = 5;
    private static int height = 10;
    private static int[][] map = new int[weigth][height];

    public static void main(String[] args) {
        int c = 1;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < weigth; j++) {
                map[j][i] = c;
                c++;
                System.out.print(map[j][i]);
                if (j==weigth-1){
                    System.out.println();
                }
            }
        }
        System.out.println(map[1][2]);
        System.out.println();
        for (int i = height-1; i >= 0; i--) {
            for (int j = 0; j < weigth; j++) {
                System.out.print(map[j][i]);
                if (j==weigth-1){
                    System.out.println();
                }
            }
        }
        System.out.println(map[1][2]);
    }


}
