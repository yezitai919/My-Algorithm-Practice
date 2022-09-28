package banker.algorithm;

import java.util.ArrayList;

public class OSKernel {

    public void resourceAllocation(int[] request,State state){
        //遍历PCB块
        for (PCB pcb : state.getAllocation()) {
            if (request[0]==pcb.getId()){
                if (pcb.getStatus()==1){
                    System.out.println("该进程已结束 ！");
                }else if (request[1]>pcb.getDemand()-pcb.getExisting()){
                    System.out.println("错误：请求资源数大于该进程需要的资源数！");
                }else if (request[1]>state.getAvailable()){
                    System.out.println("异常：请求资源数大于系统剩余的资源数！");
                }else if (request[1]==pcb.getDemand()-pcb.getExisting()){
                    //因为设定瞬间完成
                    pcb.setStatus(1);
                    state.setAvailable(state.getAvailable()+pcb.getExisting());
                    pcb.setExisting(0);
                    System.out.println("请求成功！！！");
                }else {
                    pcb.setExisting(pcb.getExisting()+request[1]);
                    state.setAvailable(state.getAvailable()-request[1]);
                    State state1 = (State) state.clone();
                    state1.setAllocation(new ArrayList<>());
                    for (int i = 0; i < state.getAllocation().size(); i++) {
                        PCB pcb1 = (PCB) state.getAllocation().get(i).clone();
                        state1.getAllocation().add(pcb1);
                    }
                    if (banker(state1)){
                        System.out.println("请求成功！");
                    }else {
                        pcb.setExisting(pcb.getExisting()-request[1]);
                        state.setAvailable(state.getAvailable()+request[1]);
                        System.out.println("请求失败！该请求会导致系统资源状态不安全！");
                    }
                }
                return;
            }
        }
    }
    private boolean banker(State state){

        while (true){
            boolean bk = true;
            int maxDemand = 0;
            int maxDemandNo = 0;
            //找到需求最大的进程
            for (int i = 0; i < state.getAllocation().size(); i++) {
                PCB pcb1 = state.getAllocation().get(i);
                if (pcb1.getStatus()==0){

                    int temp = pcb1.getDemand()-pcb1.getExisting();
                    
                    if (temp<=state.getAvailable() && pcb1.getDemand()>maxDemand){
                        maxDemand = pcb1.getDemand();
                        maxDemandNo = i;
                    }
                    bk = false;
                }
            }
            if (bk){
                return true;
            }
            //如果需求最小的进程能满足就能进行下去
            if (maxDemand!=0){
                PCB pcb = state.getAllocation().get(maxDemandNo);

                int r = pcb.getExisting();
                state.setAvailable(state.getAvailable()+r);

                pcb.setStatus(1);
                pcb.setExisting(0);
                state.getAllocation().set(maxDemandNo,pcb);
            }else {
                return false;
            }
        }
    }
    public OSKernel() {
    }
}
