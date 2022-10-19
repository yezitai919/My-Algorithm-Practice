package poj1011;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @Author jinjun99
 * @Date Created in 2022/10/5 21:13
 * @Description
 * @Since version-1.0
 */
public class Main3 {


        static boolean[] used;
        static int num;//分割后木棍的数量
        static int[] s;
        static int sum;
        static int max;
        static int parts;
/*
9
5 2 1 5 2 1 5 2 1
4
1 2 3 4
0
*/
        //"木棒"表示合成后的stick，"木棍"表示未合成的stick
        public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);
            while((num = sc.nextInt()) != 0){
                used = new boolean[num];
                sum = 0;
                s = new int[num];
                for(int i = 0; i<num; i++){
                    s[i] = sc.nextInt();
                    sum += s[i];
                }

                Arrays.sort(s);//对木棍进行排序(从小到大)
                max = s[num - 1];

                //木棒的长度一定大于等于最长的木棍长度，所以从最长的木棍开始
                for(; max<= sum; max++){
                    //木棒的长度一定能被总长度整除
                    if(sum%max == 0){
                        parts = sum/max;//木棒的数目
                        if(search(0, num - 1, 0)){
                            System.out.println(max);
                            break;
                        }
                    }
                }
            }
            sc.close();
        }

        /*
         * 搜索能拼成一个木棒的木棍
         * @param res:当前这根木棒已有的长度
         * @param next：下一个搜索的木棍的下标
         * @param cpl：已经拼成的木棒数量
         */

        public static boolean search(int res, int next, int cpl){
            //当res=max时，本次合成成功。
            if(res == max){
                cpl++;
                res = 0;
                next = num - 1;
                //当一次合成成功后，next可能指在数组的任意位置，此时应该将其置回，继续从当前最大的开始合成
            }

            //cpl = parts,当前所有木棒合成完毕
            if(cpl == parts){
                return true;
            }

            //没有成功，继续合成
            while(next >= 0){
                //如果当前木棍没有被用过
                if(!used[next]){
                    //木棒的当前长度+当前搜索的木棍长度 没有超过max，则可以放入
                    if(res + s[next] <= max){
                        used[next] = true;
                        //继续搜索成功
                        if(search(res + s[next], next - 1, cpl)){
                            return true;
                        }

                        //搜索不成功
                        used[next] = false;

                        //若本次搜索失败时，res=0，说明当前最长木棍无法合成木棒，则肯定会搜索失败
                        if(res == 0){
                            break;
                        }
                        //可以合成当前的，但是剩下的无法合成
                        if(res + s[next] == max){
                            break;
                        }
                    }

                    //如果某一个木棍匹配失败，则在当前条件下，与其相同长度的木棍都不用在匹配了
                    int i = next - 1;
                    while(i >=0 && s[i] == s[next]){
                        i--;
                    }
                    next = i;

                    //计算剩余木棍的总长度
                    int l_s = 0;
                    while(i >= 0){
                        if(!used[i]){
                            l_s += s[i];
                        }
                        i--;
                    }
                    //如果剩余木棍的总长度都小于max-res，则拼凑一定失败
                    if(l_s < max - res){
                        break;
                    }
                    continue;
                }
                next--;//此处用于控制当if(used[next] == false)不满足时，指向下一个木棍
            }
            return false;
        }

}
