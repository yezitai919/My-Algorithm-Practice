package six.scheduling;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OSKernel {
    /**
     * 内存对象
     */
    private Memory mem = new Memory();

    /**
     * 处理器对象
     */
    private CPU cpu = new CPU(this);
    /**
     * CPU线程池
     */
    private ExecutorService service = Executors.newFixedThreadPool(1);

    /**
     *  创建原语
     * @param id
     * @param priority
     * @param serviceT
     */
    protected void creatProcess(int id,int priority,int serviceT){
        mem.getReady1().offer(new PCB(id,priority,serviceT));
        mem.getReady2().offer(new PCB(id,priority,serviceT));
        mem.getReady3().offer(new PCB(id,priority,serviceT));
        mem.getReady4().offer(new PCB(id,priority,serviceT));
        mem.getReady5().offer(new PCB(id,priority,serviceT));
        mem.getFeedback1().offer(new PCB(id,priority,serviceT));
        System.out.println("进程创建成功！");
    }

    /**
     * 处理器调度
     */
    protected void processorScheduling(int ins){
        if (cpu.getState()==1){
            System.out.println("处理器正在执行进程！");
        }else {
            boolean a = mem.getReady1().isEmpty()&&mem.getReady2().isEmpty()&&
                    mem.getReady3().isEmpty()&&mem.getReady4().isEmpty()&&
                    mem.getReady5().isEmpty()&&mem.getFeedback1().isEmpty();
            boolean b = ins==1&&mem.getReady1().isEmpty()||ins==2&&mem.getReady2().isEmpty()||
                    ins==3&&mem.getReady3().isEmpty()||ins==4&&mem.getReady4().isEmpty()||
                    ins==5&&mem.getReady5().isEmpty()||ins==6&&mem.getFeedback1().isEmpty();
            if (a){
                System.out.println("内存中无就绪进程!");
            }else if (b){
                System.out.println("该调度已执行过!");
            }else {
                cpu.setInstructions(ins);
                service.submit(cpu);
            }
        }
    }

    /**
     * 先来先服务调度
     */
    protected void firstComeFirstServed(){
        //修改状态防止其他调度命令重叠
        cpu.setState(1);
        int currTime = 0;
        int arrivalTime = 0;
        while (!mem.getReady1().isEmpty()){
            PCB pcb = mem.getReady1().poll();
            assert pcb != null;
            pcb.setStartTime(currTime);
            pcb.setArrivalTime(arrivalTime);
            arrivalTime++;

            int serviceT = pcb.getServiceTime();
            try {
                Thread.sleep(serviceT*100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            currTime+=serviceT;
            pcb.setCompletionTime(currTime);
            pcb.countTurnaroundTime();
            pcb.countNormalizedTurnaroundT();

            mem.getEnding1().offer(pcb);
        }
        cpu.setState(0);
        System.out.println("先来先服务调度执行完毕！");
    }

    /**
     * 时间片轮换调度
     */
    protected void timeSliceRotation(){
        cpu.setState(1);
        int currTime = 0;
        int arrivalTime = 1;
        int leaderId = 0;
        while (!mem.getReady2().isEmpty()){
            PCB pcb = mem.getReady2().poll();
            assert pcb != null;

            if (leaderId==0){
                leaderId = pcb.getId();
            }else if (pcb.getId()!=leaderId && pcb.getArrivalTime()==0){
                pcb.setArrivalTime(arrivalTime);
                pcb.setStartTime(currTime);
                arrivalTime++;
            }
            int remainingSerT = pcb.getRemainingSerT();

            if (remainingSerT<=cpu.getTimeSlice1()){
                try {
                    Thread.sleep(remainingSerT*100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                currTime+=remainingSerT;

                pcb.setCompletionTime(currTime);
                pcb.countTurnaroundTime();
                pcb.countNormalizedTurnaroundT();
                mem.getEnding2().offer(pcb);
            }else {
                try {
                    Thread.sleep(cpu.getTimeSlice1()*100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                currTime+=cpu.getTimeSlice1();

                pcb.setRemainingSerT(cpu.getTimeSlice1());

                mem.getReady2().offer(pcb);
            }
        }
        cpu.setState(0);
        System.out.println("时间片轮换调度执行完毕！");
    }

    /**
     * 最短进程优先调度
     */
    protected void shortestProcessFirst(){
        //修改状态防止其他调度命令重叠
        cpu.setState(1);
        int currTime = 0;
        int arrivalTime = 0;
        while (!mem.getReady3().isEmpty()){
            PCB pcb = mem.getReady3().poll();
            assert pcb != null;
            pcb.setStartTime(currTime);
            pcb.setArrivalTime(arrivalTime);
            arrivalTime++;

            int serviceT = pcb.getServiceTime();
            try {
                Thread.sleep(serviceT*100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            currTime+=serviceT;
            pcb.setCompletionTime(currTime);
            pcb.countTurnaroundTime();
            pcb.countNormalizedTurnaroundT();
            mem.getEnding3().offer(pcb);
        }
        cpu.setState(0);
        System.out.println("所有进程执行完毕！");
    }
    protected void staticPriority(){
        //修改状态防止其他调度命令重叠
        cpu.setState(1);
        int currTime = 0;
        int arrivalTime = 0;
        while (!mem.getReady4().isEmpty()){
            PCB pcb = mem.getReady4().poll();
            assert pcb != null;
            pcb.setStartTime(currTime);
            pcb.setArrivalTime(arrivalTime);
            arrivalTime++;

            int serviceT = pcb.getServiceTime();
            try {
                Thread.sleep(serviceT*100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            currTime+=serviceT;
            pcb.setCompletionTime(currTime);
            pcb.countTurnaroundTime();
            pcb.countNormalizedTurnaroundT();
            mem.getEnding4().offer(pcb);
        }
        cpu.setState(0);
        System.out.println("所有进程执行完毕！");
    }
    protected void dynamicPriority(){
        //修改状态防止其他调度命令重叠
        cpu.setState(1);
        int currTime = 0;
        int arrivalTime = 1;
        int leaderId = 0;
        while (!mem.getReady5().isEmpty()){
            PCB pcb = mem.getReady5().poll();
            assert pcb != null;

            if (leaderId==0){
                leaderId = pcb.getId();
            }else if (pcb.getId()!=leaderId && pcb.getArrivalTime()==0){
                pcb.setArrivalTime(arrivalTime);
                pcb.setStartTime(currTime);
                arrivalTime++;
            }

            int remainingSerT = pcb.getRemainingSerT();
            if (remainingSerT<=cpu.getTimeSlice1()){
                try {
                    Thread.sleep(remainingSerT*100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                currTime+=remainingSerT;
                pcb.setCompletionTime(currTime);
                pcb.countTurnaroundTime();
                pcb.countNormalizedTurnaroundT();
                mem.getEnding5().offer(pcb);
            }else {
                try {
                    Thread.sleep(cpu.getTimeSlice1()*100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                currTime+=cpu.getTimeSlice1();

                pcb.setStartWaitMoment(currTime);

                pcb.setRemainingSerT(cpu.getTimeSlice1());
                mem.getReady5().offer(pcb);
            }

            for (PCB pcb1 : mem.getReady5()) {
                int sWm = pcb.getStartWaitMoment();
                if (sWm!=currTime){
                    pcb1.setPriority(pcb1.getRemainingSerT()/((currTime-sWm)+pcb1.getRemainingSerT()));
                }
            }
        }
        cpu.setState(0);
        System.out.println("所有进程执行完毕！");
    }

    /**
     * 多级反馈队列轮换调度
     */
    protected void multistageFeedbackQueueRotation(){
        cpu.setState(1);
        int currTime = 0;
        int arrivalTime = 1;
        int leaderId = 0;
        while (true){
            PCB pcb = null;

            int timeSlice = 0;

            LinkedList<PCB> feedbackNext = null;

            if (!mem.getFeedback1().isEmpty()){
                pcb = mem.getFeedback1().poll();

                timeSlice = cpu.getTimeSlice1();

                feedbackNext = mem.getFeedback2();
            }else if (!mem.getFeedback2().isEmpty()){
                pcb = mem.getFeedback2().poll();
                timeSlice = cpu.getTimeSlice2();
                feedbackNext = mem.getFeedback3();
            }else if (!mem.getFeedback3().isEmpty()){
                pcb = mem.getFeedback3().poll();
                timeSlice = cpu.getTimeSlice3();
                feedbackNext = mem.getFeedback3();
            }else {
                break;
            }

            assert pcb != null;
            if (leaderId==0){
                leaderId = pcb.getId();
            }else if (pcb.getId()!=leaderId && pcb.getArrivalTime()==0){
                pcb.setArrivalTime(arrivalTime);
                pcb.setStartTime(currTime);
                arrivalTime++;
            }


            int remainingSerT = pcb.getRemainingSerT();

            if (remainingSerT<=timeSlice){
                try {
                    Thread.sleep(remainingSerT*100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                currTime+=remainingSerT;
                pcb.setCompletionTime(currTime);
                pcb.countTurnaroundTime();
                pcb.countNormalizedTurnaroundT();
                mem.getEnding6().offer(pcb);
            }else {
                try {
                    Thread.sleep(timeSlice*100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                currTime+=timeSlice;
                pcb.setRemainingSerT(timeSlice);

                feedbackNext.offer(pcb);
            }
        }
        cpu.setState(0);
        System.out.println("多级反馈队列轮换调度执行完毕！");
    }
    /**
     * 查看进程运行完成后的各种状态
     */
    protected void viewPCBInformation(){
        if (cpu.getState()==1){
            System.out.println("请稍后，处理器正在执行"+cpu.getInstructions()+"指令！");
        }else {
            System.out.println("先来先服务:");
            showEndingQueue(mem.getEnding1());
            System.out.println("时间片轮换:");
            showEndingQueue(mem.getEnding2());
            System.out.println("短进程优先:");
            showEndingQueue(mem.getEnding3());
            System.out.println("静态优先级:");
            showEndingQueue(mem.getEnding4());
            System.out.println("动态优先级:");
            showEndingQueue(mem.getEnding5());
            System.out.println("多级反馈队列:");
            showEndingQueue(mem.getEnding6());
        }
    }

    protected void showEndingQueue(LinkedList<PCB> ending){
        int size = ending.size();
        if (size==0){
            System.out.println("该调度暂无结束进程！");
        }else {
            int aveTurTime = 0;
            double aveNorTurTime = 0;
            System.out.println("进程名  优先级  到达时间  服务时间  开始时间  完成时间  周转时间  带权周转时间");
            for (PCB pcb : ending) {
                int a = pcb.getTurnaroundTime();
                double b = pcb.getNormalizedTurnaroundT();
                aveTurTime+=a;
                aveNorTurTime+=b;
                System.out.println("  "+pcb.getId()+"      "+pcb.getPriority()+"      "+pcb.getArrivalTime()+
                        "        "+pcb.getServiceTime()+"        "+pcb.getStartTime()+"       "+
                        pcb.getCompletionTime()+"       "+a+"       "+ String.format("%.2f", b));
            }
            System.out.println("平均周转时间："+(double)(aveTurTime/size)+"平均带权周转时间："+String.format("%.2f", (aveNorTurTime/size)));
        }
    }
}
