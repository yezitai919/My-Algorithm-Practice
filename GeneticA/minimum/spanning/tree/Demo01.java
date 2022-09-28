package minimum.spanning.tree;

import java.util.Arrays;

/**
 * @author 矜君
 * @date 2020/9/28 16:32.
 */
public class Demo01 {
    public static void main(String[] args) {
        int[] a = {1,2,3,4,5,6};
        int[] b = {3,4,2,6,1,5};
        int[] c = new int[6];
        int[] d = new int[6];
        int temp1 = (int)(Math.random()*(6-1))+1;
        int temp2 = (int)(Math.random()*(6-1))+1;
        if (temp2 - temp1>0){
            temp2 -= temp1;
        }else if (temp2-temp1<0){
            int t1 = temp1;
            temp1 = temp2;
            temp2 = -temp2+t1;
        }else {
            temp2=1;
        }
        System.arraycopy(a,temp1,d,temp1,temp2);
        System.arraycopy(b,temp1,c,temp1,temp2);

        if (temp1!=0){
            System.arraycopy(a,0,c,0,temp1);
            System.arraycopy(b,0,d,0,temp1);

        }
        if (temp1+temp2<6){
            System.arraycopy(a,temp1+temp2,c,temp1+temp2,6-(temp1+temp2));
            System.arraycopy(b,temp1+temp2,d,temp1+temp2,6-(temp1+temp2));
        }
        boolean b1 = 5>=temp1 && 5<(temp1+temp2) && (3<temp1 || 3>=(temp1+temp2));
        boolean b2 = (5<temp1 || 5>=(temp1+temp2)) && 3>=temp1 && 3<(temp1+temp2);
        if (b1){
            d[3] = c[3];
            c[3] = 6;
        }else if (b2){
            c[5] = d[5];
            d[5] = 6;
        }
        System.out.println(temp1+" "+temp2);
        System.out.println(Arrays.toString(a));
        System.out.println(Arrays.toString(b));
        if (b1||b2){
            System.out.println("-------------------");
        }
        System.out.println(Arrays.toString(c));
        System.out.println(Arrays.toString(d));
    }
}
