package puyopuyo;

/**
 * @Author jinjun99
 * @Date Created in 2021/11/21 11:18
 * @Description
 * @Since version-1.0
 */
public class Block {

    /**
     * 状态
     */
    int event = 0;
    /**
     * 方向
     */
    int dir = 0;

    /**
     * 列号
     */
    int col = 0;
    /**
     * 颜色
     */
    int[] val;

    public Block(int[] val) {
        this.val = val;
    }
}
