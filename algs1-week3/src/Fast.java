import java.util.ArrayList;
import java.util.Arrays;

public class Fast {

    private static final String SEP = " -> ";

    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        
        Point[] points = new Point[N];
        Point[] pointsR = new Point[N];
        
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
            pointsR[i] = new Point(x, y);
        }

        for (int i = 0; i < N; i++) {

            Arrays.sort(points);
            Arrays.sort(points, pointsR[i].SLOPE_ORDER);
            ArrayList<Point> arr = new ArrayList<Point>();
            double prev = 0;
            boolean isFirst = true;
            arr.add(pointsR[i]);
            for (int j = 0; j < N; j++) {
                if (pointsR[i].compareTo(points[j]) == 0) {
                    continue;
                }
                double tprev = pointsR[i].slopeTo(points[j]);
                if (!isFirst && tprev != prev) {

                    if (arr.size() > 3) {
                        Point[] arrP = arr.toArray(new Point[0]);
                        // Arrays.sort(arrP);
                        if (isPrintable(arrP)) {
                            printPath(arrP);
                        }
                    }
                    prev = tprev;

                    arr = new ArrayList<Point>();
                    arr.add(pointsR[i]);
                    arr.add(points[j]);
                    continue;
                }
                isFirst = false;
                arr.add(points[j]);
                prev = tprev;

            }
            if (arr.size() > 3) {
                Point[] arrP = arr.toArray(new Point[0]);
                // System.out.println("nott"+separator+arr.size());
                // Arrays.sort(arrP);
                if (isPrintable(arrP)) {
                    printPath(arrP);
                }
            }
        }
    }

    private static void printPath(Point[] arr) {

        int count = 0;
        Point prev = null;
        for (Point p : arr) {
            System.out.print(p.toString());
            p.draw();
            if (prev != null) {
                prev.drawTo(p);
            }
            prev = p;
            count++;
            if (count < arr.length)
                System.out.print(SEP);

        }
        System.out.println();

    }

    private static boolean isPrintable(Point[] arr) {
        Point prev = null;
        for (Point p : arr) {
            if (prev != null && prev.compareTo(p) >= 0) {
                return false;
            }
            prev = p;
        }
        return true;
    }

}
