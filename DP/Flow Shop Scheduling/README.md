流水作业调度：n 个作业{1，2，…，n}要在由 2 台机器 M1 和 M2 组成的流水线上完成加工。每个作业加工的顺序都是先在 M1 上加工，然后在 M2 上加工。M1 和M2加工作业 i 所需的时间分别为 ai 和 bi。流水作业调度问题要求确定这 n 个作业的最优加工顺序，使得从第一个作业在机器 M1 上开始加工，到最后一个作业在机器 M2 上加工完成所需的时间最少。
直观上，一个最优调度应使机器 M1 没有空闲时间，且机器 M2 的空闲时间最少。在一般情况下，机器 M2 上会有机器空闲和作业积压 2 种情况。
实例：

![image](https://user-images.githubusercontent.com/55118194/192788150-6feb80eb-6858-4c2b-8758-2f1e13997778.png)

先用Johnson算法求最优调度序列：
(1)令N1 = { i | ai < bi}, N2 = { i | ai >= bi };
(2)将N1中作业依ai的非减序排序；将N2中作业依bi的非增序排序；
(3)N1中作业接N2中作业构成满足Johnson法则的最优调度。
第一部分是ai<bi的先加工，这时M1加工得比M2快， M2不断积压工时，第二部分是ai>=bi的后加工，这时M2加工比M1快，又可以不断消耗M2的积压工时。这样就能让M2的空闲尽可能少。把N1接上N2就能得到最优调度序列。
设一个二维数组jobs存储每个作业的编号和工时，其中：jobs [i][0]=i; jobs [i][1]=ai; jobs [i][2]=bi;

![image](https://user-images.githubusercontent.com/55118194/192788281-6fddc6e3-ec0e-41b3-a851-10f4dd63bd8e.png)

设一个等大的辅助数组jobSort，遍历jobs，当作业i的ai < bi时，即jobs[i][1]< jobs[i][2]，把jobs [i]的数据复制到jobSort数组，再把jobs[i][2]设为无穷，表示作业i已从jobs中取出。

![image](https://user-images.githubusercontent.com/55118194/192788321-17824e64-200e-4ae1-be10-93026b463a3a.png)

对jobs按jobs[i][2]降序，对jobSort按jobSort[i][1]升序

![image](https://user-images.githubusercontent.com/55118194/192788349-45af47a0-f780-47a3-b9c8-42c570679ce6.png)

设变量j=0，遍历jobSort数组，如果jobSort[i][0]≠0，就把jobSort[i]复制到jobs[j]，然后j++,遍历完jobSort时，jobs就是最优调度的作业序列：

![image](https://user-images.githubusercontent.com/55118194/192788396-4dd2eb62-c9a6-49dd-8e43-cad28c594bde.png)

获得最优调度再用动态规划的思想求加工时长，设M1加工总时长为t1，M2加工总时长为t2。遍历jobs，对于作业i，先在M1上加工: t1+=jobs[i][1], 在M1加工完以后如果M2空闲，即t2<=t1，则t2=t1+jobs[i][2]，如果t2>t1，则t2+=jobs[i][2]。


![image](https://user-images.githubusercontent.com/55118194/192788436-2de6d4a8-a601-4e3b-8d67-3db5b08e3d53.png)

