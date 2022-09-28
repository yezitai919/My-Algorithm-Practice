public class Demo01{
public static void main(String[] args) {
    int n = 9;
    int m = 4;
    //各机器工作时间
    int[] machiningT = new int[m];
    //各作业所需的处理时间
    Integer[] processingT = {2,14,4,16,6,5,3,17,12};
    //作业降序
    Arrays.sort(processingT, Collections.reverseOrder());
    for (int i = 0; i < n; i++) {
        //安排当前作业
        machiningT[0]+=processingT[i];
        //机器升序
        Arrays.sort(machiningT);
    }
    //打印活最多的机器的完成时间
    System.out.println(machiningT[0]);
}
}
