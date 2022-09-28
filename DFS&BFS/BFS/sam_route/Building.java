package sam_route;

/**
 * @Author jinjun99
 * @Date Created in 2021/11/28 13:33
 * @Description
 * @Since version-1.0
 */
public class Building {
    int[] position = new int[2];
    int width = 0;
    int length = 0;
    /**
     * 0,1是停车场坐标，3是停车场位于建筑的方向
     */
    int[] park = new int[3];


    public Building(int[] position, int width, int length, int[] park) {
        this.position = position;
        this.width = width;
        this.length = length;
        this.park = park;
    }
}
