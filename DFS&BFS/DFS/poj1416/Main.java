package poj1416;

import java.util.Scanner;
import java.util.Stack;

/**
 * @Author jinjun99
 * @Date Created in 2022/10/6 11:08
 * @Description
 * 这题没剪枝就过了，但是被1011题训练出来的直觉还是让我找到一些剪枝点，
 * 过了再剪枝和超时再剪枝的心态完全不一样，下次超时不要急，慢慢剪总会AC的
 * @Since version-1.0
 */
public class Main {
    static int target;
    static String num;
    static int len;
    static int sum;
    /**
     * 当前最小和
     */
    static int currMinSum;
    static boolean error;
    static boolean rejected;
    static Stack<Integer> cuttingResults;
    static Stack<Integer> currCuttingR;


    /**
     * @param layer     当前层数（第几刀）
     * @param remainNum 剩余的数字
     */
    private static void dfs(int layer, String remainNum) {

        if (layer == 0) {
            /*如果当前数字=目标数，就不用切。（字符串转整数要自己写个方法）*/
            if (Integer.parseInt(remainNum) == target) {
                cuttingResults.push(target);
                sum = target;
                return;
            }
            /*计算最小分割的和*/
            for (int i = 0; i < len; i++) {
                sum += remainNum.charAt(i) - '0';
            }
            /*如果大于目标数，输出error*/
            if (sum > target) {
                error = true;
                return;
            }
            sum = 0;
        }

        boolean off = false;
        int cLen = remainNum.length();
        for (int i = 1; i <= cLen && !off; i++) {
            int cutNum = 0;
            String cutStr = "";
            /*全切给数字*/
            if (i == cLen) {
                cutNum = Integer.parseInt(remainNum);
            } else {
                /*分割数字和字符串*/
                for (int j = 0; j < cLen; j++) {
                    char cj = remainNum.charAt(j);
                    if (j < i) {
                        if (cutNum == 0) {
                            cutNum += cj - '0';
                        } else {
                            cutNum = cutNum * 10 + cj - '0';
                        }
                    } else {
                        cutStr += cj;
                    }
                }
            }

            /*记录当前数字和*/
            currMinSum += cutNum;
            currCuttingR.push(cutNum);
            /*如果字符串剩1位*/
            if (cutStr.length() == 1) {
                int lastNum = cutStr.charAt(0) - '0';
                currMinSum += lastNum;
                currCuttingR.push(lastNum);
            }

            /*如果分割完并且当前数字和大于最优数字和*/
            if ((cutStr.length() == 1 || cutStr.length() == 0) && (sum == 0 || currMinSum >= sum) && currMinSum <= target) {
                if (currMinSum == sum) {
                    rejected = true;
                } else {
                    rejected = false;
                    sum = currMinSum;
                    cuttingResults = (Stack<Integer>) currCuttingR.clone();
                }
            }
            if (cutStr.length() > 1 && currMinSum < target) {
                dfs(layer+1, cutStr);
            }
            /*剪枝，下一循环只会让currMinSum更大*/
            if (currMinSum > target) {
                off=true;
            }
            /*回溯*/
            currMinSum -= cutNum;
            currCuttingR.pop();
            if (cutStr.length() == 1) {
                currMinSum -= cutStr.charAt(0) - '0';
                currCuttingR.pop();
            }

        }
    }

    private static void initData() {

        currMinSum = 0;
        sum = 0;
        error = false;
        rejected = false;
        cuttingResults = new Stack<Integer>();
        currCuttingR = new Stack<Integer>();

    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            target = sc.nextInt();
            int a = sc.nextInt();
            if (target == 0 && a == 0) {
                break;
            }
            num = a + "";
            len = num.length();
            initData();
            dfs(0, num);
            if (error) {
                System.out.println("error");
            } else if (rejected) {
                System.out.println("rejected");
            } else {
                System.out.print(sum + " ");
                int size = cuttingResults.size();
                for (int i = 0; i < size; i++) {
                    if (i == size - 1) {
                        System.out.println(cuttingResults.get(i));
                    } else {
                        System.out.print(cuttingResults.get(i) + " ");
                    }
                }

            }
        }
    }
}
/*
50 12346
376 144139
927438 927438
18 3312
9 3142
25 1299
111 33333
103 862150
6 1104
0 0
*/