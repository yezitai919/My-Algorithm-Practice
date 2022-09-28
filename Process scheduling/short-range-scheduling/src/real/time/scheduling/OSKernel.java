package real.time.scheduling;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class OSKernel {

    private final PriorityQueue<PCB> ready = new PriorityQueue<>(Comparator.comparingInt(PCB::getPriority));

    private final PriorityQueue<PCB> block = new PriorityQueue<>(Comparator.comparingInt(PCB::getArrivalTime));
    private final LinkedList<PCB> scheduling = new LinkedList<>();

    protected void creatProcess(String id,int pT,int cycle){
        PCB pcb = new  PCB(id,pT,cycle);
        ready.offer(pcb);
    }

    protected void earliestDeadlineFirst(int n){

        int currTime = 0;
        int arriTime = 0;

        while (!ready.isEmpty()||!block.isEmpty()){
            if (ready.isEmpty()&&arriTime>currTime){
                scheduling.offer(new PCB("空",(arriTime-currTime),0));
                currTime=arriTime;
            }
            if (currTime==arriTime){
                while (block.peek()!=null&&block.peek().getArrivalTime()==arriTime){
                    ready.offer(block.poll());
                }
                if (block.peek()!=null){
                    arriTime=block.peek().getArrivalTime();
                }else {
                    arriTime = ready.peek().getCompletionTimeLimit();
                }
            }
            PCB pcb = ready.poll();
            assert pcb != null;
            if (pcb.getRemainingProcessingT()+currTime<=arriTime){
                scheduling.offer((PCB)pcb.clone());
                currTime += pcb.getRemainingProcessingT();
                if (pcb.getCycleNum()!=n){
                    pcb.upData();
                    block.offer(pcb);
                    assert block.peek() != null;
                    arriTime = block.peek().getArrivalTime();
                }
            }else if (pcb.getRemainingProcessingT()+currTime>arriTime){
                PCB pcb1 = (PCB) pcb.clone();
                pcb1.setRemainingProcessingT(arriTime-currTime);
                scheduling.offer(pcb1);
                pcb.setRemainingProcessingT(pcb.getRemainingProcessingT()-pcb1.getRemainingProcessingT());
                currTime = arriTime;
                ready.offer(pcb);
            }
        }
    }

    protected void viewScheduling(){
        System.out.println("条型图：");
        System.out.print("|");
        for (PCB pcb : scheduling) {
            if (pcb.getId().equals("空")){
                System.out.print(pcb.getId()+" "+pcb.getRemainingProcessingT()+" | ");
            }else {
                System.out.print(pcb.getId()+pcb.getCycleNum()+" "+pcb.getRemainingProcessingT()+" | ");
            }
        }
        System.out.println(" ");
        System.out.println("表格：");
        System.out.println("  进程名  开始时间  执行时间  完成时间");
        int currTime = 0;
        for (PCB pcb : scheduling) {
            if (pcb.getId().equals("空")){
                System.out.println("   空闲    "+currTime+"       "+pcb.getRemainingProcessingT()+
                        "       "+(currTime+pcb.getRemainingProcessingT()));
            }else {
                System.out.println("   "+pcb.getId()+pcb.getCycleNum()+"    "+currTime+
                        "       "+pcb.getRemainingProcessingT()+"       "+(currTime+pcb.getRemainingProcessingT()));
            }
            currTime+=pcb.getRemainingProcessingT();
        }
    }
}
