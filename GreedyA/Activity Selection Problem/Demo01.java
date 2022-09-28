public class Demo01{
public static void main(String[] args) {
    int[][] acticity = {{1,1,4},{2,3,5},{3,0,6},{4,5,7},{5,3,8},
     {6,5,9},{7,6,10},{8,8,11},{9,8,12},{10,2,13},{11,12,14}};
    activityArrangement(acticity);
}
/** 活动安排-贪心算法
 * @param activity 活动数据 [i][0]是编号，[i][1]是开始时间，[i][0]是结束时间
 * T(n)=O(n),S(n)=O(n)
 */
private static void activityArrangement(int[][] activity){
    //把所有活动按结束时间f从早到晚排序
    Arrays.sort(activity, Comparator.comparingInt(o -> o[2]));
    //记录上一活动结束时间
    int endT = 0;
    //按顺序记录安排好的活动编号
    ArrayList<Integer> scheduleList = new ArrayList<>();
    for (int[] ac : activity) {
        //如果当前活动开始时间不早于上一个活动结束时间，就加入安排列表
        if (ac[1] >= endT) {
            scheduleList.add(ac[0]);
            //更新结束时间
            endT = ac[2];
        }
    }
    System.out.println("最优值："+scheduleList.size());
    System.out.println("最优解："+scheduleList);
}
}
