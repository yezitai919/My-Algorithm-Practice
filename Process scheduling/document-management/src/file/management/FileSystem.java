package file.management;

import java.util.*;

public class FileSystem {
    //剩余磁盘大小(MB)
    private static int diskSize = 1024;
    //位示图
    private static int[][] bitMap = new int[32][32];
    //当前文件目录名
    private static String directoryName = "";
    //根目录(存储用户新建的文件目录)
    private static HashMap<String,FileDirectory> rootDirectory = new HashMap<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("输入数字选择以下操作：");
            System.out.println("1.文件操作  2.文件系统操作  3.目录操作");
            int select = sc.nextInt();
            if (select == 1) {
                System.out.println("输入数字选择以下操作：");
                System.out.println("1.新建文件  2.查看文件属性  3.删除文件");
                int select1 = sc.nextInt();
                if (select1 == 1) {
                    createFile();
                }
                if (select1 == 2) {
                    viewFileProperties();
                }
                if (select1 == 3) {
                    deleteFile();
                }
            }
            if (select == 2) {
                System.out.println("输入数字选择以下操作：");
                System.out.println("1.显示文件列表  2.显示磁盘空间剩余大小  3.输出位示图");
                int select2 = sc.nextInt();
                if (select2 == 1) {
                    showFileList();
                }
                if (select2 == 2) {
                    System.out.println("磁盘剩余容量为："+diskSize);
                }
                if (select2 == 3) {
                    positionDiagram();
                }
            }
            if (select == 3) {
                System.out.println("输入数字选择以下操作：");
                System.out.println("1.创建目录  2.设置当前目录");
                int select3 = sc.nextInt();
                if (select3 == 1) {
                    createDirectory();
                }
                if (select3 == 2) {
                    setCurrDirectory();
                }
            }
        }

    }

    /**
     * 创建文件
     */
    private static void createFile(){
        Scanner sc = new Scanner(System.in);
        if (rootDirectory.isEmpty()){
            System.out.println("请先创建一个文件目录");
            return;
        }

        if (directoryName.equals("")){
            System.out.println("请先设置当前文件目录");
            return;
        }
        System.out.println("请输入文件名");
        String name = sc.nextLine();
        System.out.println("请输入文件大小");
        int size = sc.nextInt();
        ArrayList<int[]> b = countBlockNum(size);
        if (b.isEmpty()){
            System.out.println("磁盘剩余容量不足");
            return;
        }
        rootDirectory.get(directoryName).getDirectory().put(name,new FCB(name,size,new Date(),b));
        diskSize -= size;
        System.out.println("创建文件成功！");
    }


    /**
     * 计算盘块号
     * @param size
     * @return
     */
    private static ArrayList<int[]> countBlockNum(int size){
        ArrayList<int[]> block = new ArrayList<>();
        if (size>diskSize){
            return new ArrayList<int[]>();
        }
        int s = -1;
        int e = 0;
        int l = 0;
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 32; j++) {
                if (bitMap[i][j]==0){
                    if (s==-1){
                        s = 32*i+j;
                    }
                    e++;
                    l++;
                    bitMap[i][j]=1;
                    if (l==size){
                        block.add(new int[]{s,e});
                        return block;
                    }
                }else {
                    if (s!=-1){
                        block.add(new int[]{s,e});
                    }
                    s = -1;
                    e = 0;
                }
            }
        }
        return block;
    }

    /**
     * 删除盘块号
     */
    private static void deleteBlockNum(ArrayList<int[]> b){
        for (int[] ints : b) {
            int j1 = ints[0]%32;
            int i1 = (ints[0]-j1)/32;
            int l = 0;
            for (int i = i1; i < 32; i++) {
                boolean bool = false;
                for (int j = j1; j < 32; j++) {
                    bitMap[i][j]=0;
                    l++;
                    if (l==ints[1]){
                        bool = true;
                        break;
                    }
                }
                if (bool){
                    break;
                }
            }
        }
    }


    /**
     * 查看文件属性
     */
    private static void viewFileProperties(){
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入文件名");
        String name = sc.nextLine();
        FCB fcb = rootDirectory.get(directoryName).getDirectory().get(name);
        if (fcb==null){
            System.out.println("该文件不存在");
        }else {
            System.out.println("文件名："+fcb.getName());
            System.out.println("文件大小："+fcb.getSize());
            System.out.println("创建时间："+fcb.getCreationTime());
            for (int[] ints : fcb.getBlockNumber()) {
                System.out.print("物理块号："+ints[0]+" "+"连续长度："+ints[1]);
                System.out.println(" ");
            }
            System.out.println(" ");

        }
    }

    /**
     * 删除文件
     */
    private static void deleteFile(){
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入文件名");
        String name = sc.nextLine();
        FCB fcb = rootDirectory.get(directoryName).getDirectory().remove(name);
        if (fcb==null){
            System.out.println("该文件不存在");
        }else {
            diskSize+=fcb.getSize();
            deleteBlockNum(fcb.getBlockNumber());
            System.out.println("删除成功！");
        }
    }

    /**
     * 显示文件列表
     */
    private static void showFileList(){
        System.out.println("文件列表：");
        Map fileList = rootDirectory.get(directoryName).getDirectory();
        Iterator iter = fileList.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            System.out.println(key);
        }
    }

    /**
     * 输出位示图
     */
    private static void positionDiagram(){
        System.out.println("位示图如下：");
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 32; j++) {
                if (j==31){
                    System.out.println(bitMap[i][j]);
                }else {
                    System.out.print(bitMap[i][j]+" ");
                }
            }
        }
    }

    /**
     * 创建目录
     */
    private static void createDirectory(){
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入目录名称");
        String name = sc.nextLine();
        rootDirectory.put(name,new FileDirectory(name));
        System.out.println("创建成功！");
    }

    /**
     * 设置当前目录
     */
    private static void setCurrDirectory(){
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入目录名称");
        String name = sc.nextLine();
        directoryName = name;
        System.out.println("设置目录成功！");
    }
}
