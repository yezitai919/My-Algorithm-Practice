package poj3126;


import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;


/**
 * @Author jinjun99
 * @Date Created in 2022/10/2 17:01
 * @Description
 * @Since version-1.0
 */
public class Main2 {

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int a = sc.nextInt();
            while (a-- > 0) {
                int tt = sc.nextInt();
                int yy = sc.nextInt();
                boolean x[] = new boolean[10005];
                int y[] = new int[10005];
                es(x, y);

                Queue<sb> queue = new LinkedList<sb>();
                int f[] = new int[4];

                f[0] = tt / 1000;
                f[1] = (tt / 100) % 10;
                f[2] = (tt % 100) / 10;
                f[3] = tt % 10;

                sb xx = new sb(f, 0, tt, "");
                y[tt] = 0;
                queue.offer(xx);
                boolean hh = false;

                while (!queue.isEmpty()) {
                    sb nodeSb = queue.poll();
                    int g[] = nodeSb.x;
                    int gg = nodeSb.step;
                    StringBuilder ok = new StringBuilder();
                    for (int i = 0; i < g.length; i++) {
                        ok.append(g[i]);
                    }
                    int ssb = Integer.valueOf(ok.toString());
                    if (ssb == yy) {
                        hh = true;
                        System.out.println(gg);
                        //	System.out.println(nodeSb.f);
                        ;
                        break;
                    }
                    int nb[] = new int[4];
                    for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < nb.length; j++) {
                            nb[j] = g[j];
                        }
                        for (int j = 0; j < 10; j++) {
                            nb[i] = j;

                            StringBuilder nnb = new StringBuilder();
                            for (int k = 0; k < nb.length; k++) {

                                nnb.append(nb[k]);
                            }
                            int ip = Integer.valueOf(nnb.toString());
                            if (y[ip] == 1 && ip >= 1000) {

                                sb vv = new sb(nb, gg + 1, ip, nodeSb.f);
                                queue.offer(vv);
                                y[ip] = 0;
                            }
                        }
                    }

                }
                if (hh == false) {
                    System.out.println("impossible");
                }

            }

        }
    }

    public static void es(boolean x[], int y[]) {
        int xx = 0;
        for (int i = 2; i < x.length; i++) {

            if (x[i] == false) {
                y[i] = 1;

            }
            for (int j = 1; j < y.length && (i * j < y.length); j++) {
                x[j * i] = true;
            }
        }

    }

}

class sb {
    int step;
    int x[] = new int[4];
    int ip;
    String f = "";

    public sb(int x[], int step, int ip, String sb) {
        for (int i = 0; i < x.length; i++) {
            this.x[i] = x[i];
        }
        this.step = step;
        this.f = sb + " " + ip;

    }

}


