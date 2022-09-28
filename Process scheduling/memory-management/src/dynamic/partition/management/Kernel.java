package dynamic.partition.management;

import java.util.ArrayList;

public class Kernel {
    /**
     * 内存对象
     */
    private Memory me = new Memory();

    /**
     * 可用分区id，每次使用后自增
     */
    private int parId = 1;
    /**
     * 已使用分区id，每次使用后自增
     */
    private int usedId = 1;

    /**
     * 初始化内存
     * @param size 内存大小
     */
    public void initialization(int size){
        me.setCapacity(size);
        //第一个分区就是内存所以可用空间，这里忽略了系统占用空间
        me.getAvailablePartition().add(new Partition(parId++,size,1));
    }

    /**
     * 分配一个请求
     * @param req 请求对象
     * @param ins 分配法指令：1是最早，2是最优，3是最坏
     */
    public void firstOrBestOrWorstFit(Request req, int ins){
        //获取可以分区队列的引用，方便后面使用这个队列
        ArrayList<Partition> abp = me.getAvailablePartition();


        if (abp.isEmpty()){
            System.out.println("当前内存已分配完！");
            return;
        }


        if (ins==1){
            abp.sort((o1, o2) -> o1.getStartingAddress() - o2.getStartingAddress());
        }else if (ins==2){
            abp.sort((o1, o2) -> o1.getSize() - o2.getSize());
        }else if (ins==3){
            abp.sort((o1, o2) -> o2.getSize() - o1.getSize());
            if (abp.get(0).getSize()<req.getPcbSize()){
                me.getRequest().add(req);
                System.out.println("内存资源不足，请求分配失败！");
                return;
            }
        }

        //记录是否分配成功
        boolean b = false;

        //遍历可用分区队列
        for (int i = 0; i < abp.size(); i++) {
            if (abp.get(i).getSize()>=req.getPcbSize()){
                //如果找到一个容量>=请求容量的可用分区，就给给请求分配内存，分配方法就是创建一个和请求容量一样大的分区对象放入已用分区队列中
                me.getMemoryAllocation().add(new Partition(usedId++,
                        req.getPcbSize(),abp.get(i).getStartingAddress()));
                b = true;
                System.out.println("请求分配成功!");
                //如果可用分区刚好分配完就从可用分区队列删除此对象，否则修改容量和开始地址
                if (abp.get(i).getSize()==req.getPcbSize()){
                    abp.remove(i);
                }else {
                    abp.get(i).reduceSize(req.getPcbSize());
                    abp.get(i).addStartingAddress(req.getPcbSize());
                }
                break;
            }
        }

        //分配失败则放入请求表，分配成功还要查询一下请求表是否有这个请求，有就删除
        if (!b){
            me.getRequest().add(req);
            System.out.println("内存资源不足，请求分配失败！");
        }else if (!me.getRequest().isEmpty()){
            for (int i = 0; i < me.getRequest().size(); i++) {
                if (me.getRequest().get(i).equals(req)){
                    me.getRequest().remove(i);
                    break;
                }
            }
        }
    }

    /**
     * 撤回请求
     * @param id
     */
    public void recycling(int id){
        ArrayList<Partition> mac = me.getMemoryAllocation();
        ArrayList<Partition> abp = me.getAvailablePartition();
        if (mac.isEmpty()){
            System.out.println("当前无内存分配！");
            return;
        }
        for (int i = 0; i < mac.size(); i++) {
            if (mac.get(i).getId()==id){
                mac.get(i).setPcbId(0);
                mac.get(i).setId(parId++);
                abp.add(mac.remove(i));
                System.out.println("退回请求成功！");
                //合并分区
                //先按地址升序
                abp.sort((o1, o2) -> o1.getStartingAddress() - o2.getStartingAddress());
                for (int j = 0; j < abp.size(); j++) {
                    if (abp.get(j).getId()==parId){
                        boolean b1 = abp.get(j-1).getStartingAddress()+abp.get(j-1).
                                getSize()==abp.get(j).getStartingAddress();
                        boolean b2 = j<abp.size()-1&&abp.get(j).getStartingAddress()+
                                abp.get(j).getSize()==abp.get(j+1).getStartingAddress();
                        if (b1&&b2){
                            abp.get(j-1).addSize(abp.get(j).getSize()+abp.get(j+1).getSize());
                            abp.remove(j);
                            abp.remove(j+1);
                            break;
                        }else if (b1){
                            abp.get(j-1).addSize(abp.get(j).getSize());
                            abp.remove(j);
                            break;
                        }else if (b2){
                            abp.get(j).addSize(abp.get(j+1).getSize());
                            abp.remove(j+1);
                            break;
                        }
                    }
                }
                break;
            }
            if (i==mac.size()-1){
                System.out.println("未找到该请求！");
            }
        }
    }

    public void viewMemoryAllocation(){
        me.getMemoryAllocation().sort((o1, o2) -> o1.getStartingAddress() - o2.getStartingAddress());
        for (Partition par : me.getMemoryAllocation()) {
            System.out.println("分区号："+par.getId()+"  分区大小："+par.getSize()+
                    "  分区开始地址："+par.getStartingAddress()+"  占用的进程号："+par.getPcbId());
        }
    }

}
