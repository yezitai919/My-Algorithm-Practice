package tetris;

/**
 * @Author jinjun99
 * @Date Created in 2021/11/22 16:46
 * @Description
 * @Since version-1.0
 */
public class Block {
    /**
     * 状态，0为普通块，1为特殊块。
     */
    int event = 0;
    /**
     * 列数
     */
    int col = 0;
    /**
     * 颜色
     */
    int[] val;
    /**
     * 特殊块类型
     */
    int type;
    public Block(int[] val){
        this.val = val;
    }
    public Block(int event,int type){
        this.event = event;
        this.type = type;
    }
}
