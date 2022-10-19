package poj1010;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @Author jinjun99
 * @Date Created in 2022/10/4 18:11
 * @Description
 * @Since version-1.0
 */
public class Main {
    /**
     * 邮票数
     */
    static int m;
    /**
     * 客户数
     */
    static int n;
    /**
     * 邮票面值
     */
    static int[] stamps;
    /**
     * 客户需求
     */
    static int[] needs;

    /**
     * 标记选过的邮票
     */
    static int[] check;
    /**
     * 当前客户的当前组合
     */
    static int[] currCombination;
    /**
     * 当前客户的最佳组合
     */
    static int[] bestCombination;
    /**
     * 当前客户的当前组合的邮票下标
     */
    static int[] cCombIndex;
    /**
     * 当前客户的最佳组合的邮票下标
     */
    static int[] bCombIndex;
    /**
     * 当前累计面值
     */
    static int faceValue;
    /**
     * 当前组合邮票种类数
     */
    static int currTypes;
    /**
     * 最优组合的邮票种类数
     */
    static int maxTypes;
    /**
     * 当前组合的最高面值
     */
    static int currMaxValue;
    /**
     * 最优解中的最高面值
     */
    static int maxValue;
    /**
     * 最优解中的邮票张数
     */
    static int minStaNum;

    /**
     * 是否持平
     */
    static int tie;

    static int len;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String str = sc.nextLine();
            len = str.length();
            m = 0;
            stamps = new int[len/2 + 5];
            int num = 0;
            for (int i = 0; i < len; i++) {
                char a  = str.charAt(i);
                if (a==' '){
                    m++;
                    num = 0;
                    continue;
                }
                if (!(num==0&&a-'0'==0)){
                    if (num==0){
                        num = a-'0';
                    }else {
                        num = num*10+(a-'0');
                    }
                    stamps[m] = num;
                }
            }
            str = sc.nextLine();
            len = str.length();
            n = 0;
            num = 0;
            needs = new int[len/2  + 5];
            for (int i = 0; i < len; i++) {
                char a  = str.charAt(i);
                if (a==' '){
                    n++;
                    num = 0;
                    continue;
                }
                if (!(num==0&&a-'0'==0)){
                    if (num==0){
                        num = a-'0';
                    }else {
                        num = num*10+(a-'0');
                    }
                    needs[n] = num;
                }
            }
//            System.out.println("m="+m+"  n="+n);
            bubbleSort(stamps, m);

//            System.out.println(Arrays.toString(stamps));
//            System.out.println(Arrays.toString(needs));
            for (int i = 0; i < n; i++) {
                initData();
                dfs(0, i);
                printAns(i);
            }

        }
    }

    private static void initData() {
        check = new int[m + 5];
        currCombination = new int[4];
        bestCombination = new int[4];
        cCombIndex = new int[4];
        bCombIndex = new int[4];
        faceValue = 0;
        currTypes = 0;
        maxTypes = 0;
        currMaxValue = 0;
        maxValue = 0;
        minStaNum = 0;
        tie = 0;
    }

    private static void printAns(int cusIndex) {
        if (maxTypes == 0) {
            System.out.println(needs[cusIndex] + " ---- none");
        } else {
            System.out.print(needs[cusIndex] + " (" + maxTypes + "): ");
            if (tie == 1){
                System.out.println("tie");
            }else {
                for (int i = 0; i < minStaNum; i++) {
                    if (i == minStaNum - 1) {
                        System.out.println(bestCombination[i]);
                    } else {
                        System.out.print(bestCombination[i] + " ");
                    }
                }
            }
        }

    }

    /**
     * @param staNum   当前组合选定的邮票数
     * @param cusIndex 客户下标
     */
    private static void dfs(int staNum, int cusIndex) {

        if (staNum == 4) {
            return;
        }
        /*遍历所有邮票*/
        for (int i = 0; i < m; i++) {
            /*如果当前邮票加入不超额*/
            if (faceValue + stamps[i] <= needs[cusIndex]) {
                /*当前邮票放入临时组合*/
                currCombination[staNum] = stamps[i];
                /*记录当前邮票下标,为了和初始的0区分+1*/
                cCombIndex[staNum] = i + 1;

                /*累计面值*/
                faceValue += stamps[i];
    /*            if (currCombination[1]==1&&currCombination[3]==3){
                    System.out.println(Arrays.toString(currCombination)+"faceV="+faceValue);
                }*/
                /*回溯工具变量*/
                int a = 0;
                int b = 0;
                /*记录单张邮票的最大面值*/
                if (stamps[i] > currMaxValue) {
                    a = currMaxValue;
                    currMaxValue = stamps[i];
                }
                /*如果当前邮票没选过，种类+1*/
                if (check[i] == 0) {
                    check[i] = 1;
                    currTypes++;
                    b = 1;
                }
                /*当前选中邮票数*/
                int currStaNum = staNum + 1;

                /*当前累计面值=客户需求面值，根据条件更新当前最优解*/
                if (faceValue == needs[cusIndex]) {
                   /* System.out.println(Arrays.toString(currCombination));
                    System.out.println("currTypes="+currTypes+"  currStaNum="+currStaNum+"  currMaxValue"+currMaxValue);*/
                     /*最佳组合定义为不同邮票类型的最大数量。如果是平局，
                     那么总邮票数最少的组合是最好的。如果仍然相同，则具有
                     最高面值邮票的组合为最佳。如果还是平局，请打印 “tie”。*/
                    /*还未找到一个最优解*/
                    boolean bl1 = maxTypes == 0;
                    /*当前解的邮票种类更多*/
                    boolean bl2 = currTypes > maxTypes;
                    /*当前解和最优解的邮票种类一样多*/
                    boolean bl3 = currTypes == maxTypes;
                    boolean bl4 = currStaNum < minStaNum;
                    /*当前解和最优解的邮票数量一样多*/
                    boolean bl5 = currStaNum == minStaNum;
                    boolean bl6 = currMaxValue > maxValue;
                    /*当前解和最优解的最高单张邮票面值一样大*/
                    boolean bl7 = currMaxValue == maxValue;
                    if (bl1||bl2) {
                        recordStamps(currStaNum);

                    } else if (bl3 && bl4) {
                        recordStamps(currStaNum);
                    } else if (bl3 && bl5 && bl6) {
                        recordStamps(currStaNum);
                    } else if (bl3 && bl5 && bl7&&notRepeat()) {
                      tie = 1;
                    }
                } else {
                    /*4层深度不用剪枝，如果要剪枝，可以把邮票按面值从大到小排序，
                    然后 “当前邮票面值”×“剩余邮票数”<"剩余需求面值" 就可以剪枝*/
                    dfs(currStaNum, cusIndex);
                }
                /*回溯*/
                currCombination[staNum] = 0;
                cCombIndex[staNum] = 0;
                faceValue -= stamps[i];
                currMaxValue = a;
                if (b == 1) {
                    check[i] = 0;
                    currTypes--;
                }
            }
        }
    }

    /**
     * @return 判断两个组合是否重复
     */
    private static boolean notRepeat() {
        int repeat = 0;
        int[] temp = new int[4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (cCombIndex[i] == bCombIndex[j]&&temp[j]==0) {
                    temp[j]=1;
                    repeat++;
                    break;
                }
            }
        }
        return repeat != 4;
    }

    /**
     * 记录邮票组合
     * @param currStaNum
     */
    private static void recordStamps(int currStaNum) {
        bestCombination = new int[4];
        bCombIndex = new int[4];
        for (int i = 0; i < currStaNum; i++) {
            bestCombination[i] = currCombination[i];
            bCombIndex[i] = cCombIndex[i];
        }
        maxTypes = currTypes;
        minStaNum = currStaNum;
        maxValue = currMaxValue;
        tie = 0;
    }


    private static void bubbleSort(int[] arr, int len) {

        for (int i = 1; i < len; i++) {
            for (int j = 0; j < len - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }
}
/*
1 2 3 0
7 4 0
1 1 0
6 2 3 0


7 (3): 1 1 2 3
4 (2): 1 3
6 ---- none
2 (2): 1 1
3 (2): tie

解题要点 1: 确定核心算法.
本题最优解的判定过程相当复杂, 无法得出局部最优解, 因此不能采用 DP. 而由于本题的解具有相当有限的深度 (至多为 4), 因此可考虑采用 DFS.

解题要点 2: 剪枝.
当已经分配了 4 张邮票时, 可以剪枝.
进一步地, 若事先已对邮票库存进行了依面值从大到小的排序, 则以下剪枝规则成立:
设已递归至某种面值 value 的邮票, 之前已选取的邮票的总面值为 tmp, 已选取的邮票的张数为 dealt, 用户需求的总面值为 req, 则当下式为 true 时, 可以剪枝:
value×(4−dealt)<req−tmp
*/