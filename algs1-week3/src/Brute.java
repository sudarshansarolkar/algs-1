import java.util.Arrays;

public class Brute {
    private static final String SEP = " -> ";

    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        for (int i = 0; i < N; i++) {

            for (int j = i + 1; j < N; j++) {
                if (i == j)
                    continue;
                for (int k = j + 1; k < N; k++) {
                    if (j == k)
                        continue;
                    for (int l = k + 1; l < N; l++) {
                        if (l == k)
                            continue;

                        Point[] arrP = new Point[4];
                        arrP[0] = points[i];
                        arrP[1] = points[j];
                        arrP[2] = points[k];
                        arrP[3] = points[l];
                        // arrP.sort(points[i].SLOPE_ORDER);
                        // Arrays.sort(arrP,arrP[0].SLOPE_ORDER);
                        // if(isPrintable(arrP.get(0), arrP.get(1), arrP.get(2),
                        // arrP.get(3))){
                        double spq = arrP[0].slopeTo(arrP[1]);
                        if (spq == arrP[0].slopeTo(arrP[2])
                                && spq == arrP[0].slopeTo(arrP[3])) {
                            Arrays.sort(arrP);
                            if (isPrintable(arrP[0], arrP[1], arrP[2], arrP[3])) {
                                printPath(arrP[0], arrP[1], arrP[2], arrP[3]);
                            }
                        }
                        // }
                    }
                }
            }
        }

    }

    private static void printPath(Point p1, Point p2, Point p3, Point p4) {
        p1.draw();
        p1.drawTo(p2);
        p2.draw();
        p2.drawTo(p3);
        p3.draw();
        p3.drawTo(p4);
        p4.draw();
        System.out.println(p1.toString() + SEP + p2.toString() + SEP
                + p3.toString() + SEP + p4.toString());

    }

    private static boolean isPrintable(Point p1, Point p2, Point p3, Point p4) {

        return (p1.compareTo(p2) <= 0 && p2.compareTo(p3) <= 0 && p3
                .compareTo(p4) <= 0);

    }
}
