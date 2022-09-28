public class Demo01{
/**流水线作业调度-动态规划
 * @param jobs jobs[0],jobs[1],jobs[3]分别表示作业编号，M1工时，M2工时
 * T(mn),S(n)=O(mn)
 */
private static void flowSort(int[][] jobs){
    int m = jobs.length;
    int n = jobs[0].length;
    //设一等大的辅助数组
    int[][] jobSort = new int[m][n];
    /*遍历jobs，当作业i的ai < bi时，把jobs [i]的数据复制到jobSort数组，
    再把jobs [i][2]设为无穷，表示作业i已从jobs中取出。*/
    for (int i = 0; i < m; i++) {
        if (jobs[i][2]>jobs[i][1]){
            jobSort[i]=Arrays.copyOf(jobs[i],n);
            jobs[i][2]=Integer.MAX_VALUE;
        }
    }
    //对jobs按jobs [2]降序，对jobSort按jobSort [1]升序
    Arrays.sort(jobs, (o1, o2) -> o2[2]-o1[2]);
    Arrays.sort(jobSort, (o1, o2) -> o1[1]-o2[1]);
    /*设变量k=0，遍历jobSort数组，如果jobSort [i][0]≠0，就把jobSort[i]
    复制到jobs[k]，然后k++,遍历完jobSort时，jobs就是最优调度的作业序列*/
    int k =0;
    for (int i = 0; i < m; i++) {
        if (jobSort[i][1]!=0){
            jobs[k]=Arrays.copyOf(jobSort[i],n);
            k++;
        }
    }
    System.out.println("最优调度：");
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            if (j == m-1) {
                System.out.println(jobs[j][i]);
            } else {
                System.out.print(jobs[j][i]+" ");
            }
        }
    }
    /*设M1加工总时长为t1，M2加工总时长为t2。遍历jobs，对于作业i，
    先在M1上加工: t1+= jobs [i][1], 在M1加工完以后如果M2空闲，
    即t2<=t1，则t2=t1+ jobs [i][2]，如果t2>t1，则t2+= jobs [i][2]。*/
    int t1=0,t2=0;
    for (int[] job : jobs) {
        t1 += job[1];
        if (t2 <= t1) {
            t2 = t1+job[2];
        } else {
            t2 += job[2];
        }
    }
    System.out.println("最优调度下M2的完成时间为："+t2);
}
}
