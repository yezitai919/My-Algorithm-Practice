package file.man;
import java.util.*;

public class FileSystem {
    //剩余磁盘大小(MB)
    private static int diskSize = 1024;
    //位示图
    private static int[][] bitMap = new int[32][32];
    //当前目录级数
    private static int dir = 1;
    //当前1级目录名
    private static String directoryName1 = "";
    //当前2级目录名
    private static String directoryName2 = "";
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

        if (directoryName1.equals("")){
            System.out.println("请先设置当前文件目录");
            return;
        }
        System.out.println("请输入文件名");
        String name = sc.nextLine();
        System.out.println("请输入文件大小");
        int size = sc.nextInt();
        int b = countBlockNum(size);
        if (b<0){
            System.out.println("磁盘剩余容量不足");
            return;
        }
        rootDirectory.get(directoryName1).getDirectory().get(directoryName2).getDirectory().put(name,new FCB(name,size,new Date(),b));
        diskSize -= size;
        System.out.println("创建文件成功！");
    }


    /**
     * 计算盘块号
     * @param size
     * @return
     */
    private static int countBlockNum(int size){
        if (size>diskSize){
            return -1;
        }
        int s = -1;
        int e = 0;
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 32; j++) {
                if (bitMap[i][j]==0){
                    if (s==-1){
                        s = 32*i+j;
                    }
                    e++;
                    bitMap[i][j]=1;
                    if (e==size){
                        return s;
                    }
                }else {
                    if (s!=-1){
                        deleteBlockNum(s,size);
                    }
                    s = -1;
                    e = 0;
                }
            }
        }
        return -1;
    }

    /**
     * 删除盘块号
     * @param b
     * @param size
     */
    private static void deleteBlockNum(int b,int size){
        int j1 = b%32;
        int i1 = (b-j1)/32;
        int length = 0;
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 32; j++) {
                if (i==i1&&j==j1){
                    bitMap[i][j]=0;
                    length++;
                }
                if (length==size){
                    return;
                }
                if (length!=0){
                    bitMap[i][j]=0;
                    length++;
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


        FCB fcb = rootDirectory.get(directoryName1).getDirectory().get(directoryName2).getDirectory().get(name);
        if (fcb==null){
            System.out.println("该文件不存在");
        }else {
            System.out.println("文件名："+fcb.getName());
            System.out.println("文件大小："+fcb.getSize());
            System.out.println("创建时间："+fcb.getCreationTime());
            System.out.println("物理块号："+fcb.getBlockNumber());
        }
    }

    private static void traverseDirectory(){

    }

    /**
     * 删除文件
     */
    private static void deleteFile(){
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入文件名");
        String name = sc.nextLine();
        FCB fcb = rootDirectory.get(directoryName1).getDirectory().get(directoryName2).getDirectory().remove(name);
        if (fcb==null){
            System.out.println("该文件不存在");
        }else {
            diskSize+=fcb.getSize();
            System.out.println("删除成功！");
        }
    }

    /**
     * 显示文件列表
     */
    private static void showFileList(){
        Scanner sc = new Scanner(System.in);
        System.out.println("输入数字选择以下操作：");
        System.out.println("1.查看一级文件目录  2.查看二级文件目录");
        int se = sc.nextInt();
        if (se==1){
            System.out.println("一级文件列表：");
            Map fileList = rootDirectory.get(directoryName1).getDirectory();
            Iterator iter = fileList.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                Object key = entry.getKey();
                System.out.println(key);
            }
        }else {
            System.out.println("二级文件列表：");
            Map fileList = rootDirectory.get(directoryName1).getDirectory().get(directoryName2).getDirectory();
            Iterator iter = fileList.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                Object key = entry.getKey();
                System.out.println(key);
            }
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
        System.out.println("输入数字选择以下操作：");
        System.out.println("1.创建一级文件目录  2.创建二级文件目录");
        int se = sc.nextInt();
        if (se==2&&directoryName1.equals("")){
            System.out.println("请先设置一级目录");
        }
        String name;
        while (true){
            System.out.println("请输入目录名称");
            name = sc.nextLine();
            if (rootDirectory.get(name)!=null){
                System.out.println("目录重名，请重新输入！");
            }else {
                break;
            }
        }

        if (se==1){
            rootDirectory.put(name,new FileDirectory(name));
        }else {
            rootDirectory.get(directoryName1).getDirectory().put(name,new SecFileDirectory(name));
        }
        System.out.println("创建成功！");
    }

    /**
     * 设置当前目录
     */
    private static void setCurrDirectory(){
        Scanner sc = new Scanner(System.in);
        System.out.println("输入数字选择以下操作：");
        System.out.println("1.设置一级文件目录  2.设置二级文件目录");
        int se = sc.nextInt();
        if (se==2&&directoryName1.equals("")){
            System.out.println("请先设置一级目录");
        }
        System.out.println("请输入目录名称");
        String name = sc.nextLine();
        if (se==1){
            directoryName1 = name;
        }else {
            directoryName2 = name;
        }
        System.out.println("设置目录成功！");
    }
}
