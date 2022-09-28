package six.scheduling;

public class CPU implements Runnable{
    /**
     * 处理器状态，0为空闲，1为忙碌
     */
    private int state;
    /**
     * 时间片(单位：0.1秒)
     */
    private int timeSlice1 = 2;
    private int timeSlice2 = 4;
    private int timeSlice3 = 8;
    /**
     * 指令，
     */
    private int instructions;

    private OSKernel kernel;

    public CPU(OSKernel kernel) {
        this.kernel = kernel;
    }

    @Override
    public void run() {

        if (instructions==1){
            kernel.firstComeFirstServed();
        }
        if (instructions==2){
            kernel.timeSliceRotation();
        }
        if (instructions==3){
            kernel.shortestProcessFirst();
        }
        if (instructions==4){
            kernel.staticPriority();
        }
        if (instructions==5){
            kernel.dynamicPriority();
        }
        if (instructions==6){
            kernel.multistageFeedbackQueueRotation();
        }
    }


    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getTimeSlice1() {
        return timeSlice1;
    }

    public void setTimeSlice1(int timeSlice1) {
        this.timeSlice1 = timeSlice1;
    }

    public int getInstructions() {
        return instructions;
    }

    public void setInstructions(int instructions) {
        this.instructions = instructions;
    }

    public int getTimeSlice2() {
        return timeSlice2;
    }

    public void setTimeSlice2(int timeSlice2) {
        this.timeSlice2 = timeSlice2;
    }

    public int getTimeSlice3() {
        return timeSlice3;
    }

    public void setTimeSlice3(int timeSlice3) {
        this.timeSlice3 = timeSlice3;
    }
}
