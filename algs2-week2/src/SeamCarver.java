import java.awt.Color;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;



public class SeamCarver {
    private RGB[][] data;

    private int height;
    private int width;
    private boolean isTransposed;
    
    private static Comparator<Frontier> cmp = new Comparator<Frontier>() {
        @Override
        public int compare(Frontier f1, Frontier f2) {
            return Double.compare(f1.cost, f2.cost);
        }
    };
    
    public SeamCarver(Picture picture) // create a seam carver object based on
                                       // the given picture
    {
        height = picture.height();
        width = picture.width();
        data = new RGB[height][width];

        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++) {
                data[i][j] = new RGB();
                data[i][j].R = (byte) picture.get(j, i).getRed();
                data[i][j].G = (byte) picture.get(j, i).getGreen();
                data[i][j].B = (byte) picture.get(j, i).getBlue();
            }
    }

    public Picture picture() // current picture
    {
        if (isTransposed)
            transpose();

        Picture p = new Picture(width, height);

        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++) {
                Color color = new Color(data[i][j].getR(), data[i][j].getG(),
                        data[i][j].getB());
                p.set(j, i, color);
            }
        return p;
    }

    public int width() // width of current picture
    {
        return width;
    }

    public int height() // height of current picture
    {
        return height;
    }

    public double energy(int x, int y) // energy of pixel at column x and row y
    {
        if (x == 0 || x == width - 1 || y == 0 || y == height - 1)
            return 195075;

        // x(x, y), and x(x, y) are the absolute value in differences of red,
        // green,
        // and blue components between pixel d(x+1, y) and pixel d(x-1, y)

        double deltaX = 0;
        double deltaY = 0;
        double temp = 0;
        // System.out.printf("x = %d , y=%d",x,y);
        temp = (data[y][x - 1].getR() - data[y][x + 1].getR());

        // System.out.printf(" d_x \t (%d,%d) %f + ",data[y][x-1].getR() ,
        // data[y][x+1].getR(),(temp*temp));
        deltaX += temp * temp;
        temp = (data[y][x - 1].getG() - data[y][x + 1].getG());
        deltaX += temp * temp;
        // System.out.printf(" d_x \t (%d,%d) %f + ",data[y][x-1].getG() ,
        // data[y][x+1].getG(),(temp*temp));
        temp = (data[y][x - 1].getB() - data[y][x + 1].getB());
        deltaX += temp * temp;
        // System.out.printf(" d_x \t (%d,%d) %f + ",data[y][x-1].getB() ,
        // data[y][x+1].getB(),(temp*temp));

        // System.out.printf("= %f",(delta_x));

        temp = (data[y - 1][x].getR() - data[y + 1][x].getR());
        // System.out.printf(" d_y \t %f + ",(temp*temp));
        deltaY += temp * temp;
        temp = (data[y - 1][x].getG() - data[y + 1][x].getG());
        // System.out.printf(" %f +",(temp*temp));
        deltaY += temp * temp;
        temp = (data[y - 1][x].getB() - data[y + 1][x].getB());
        deltaY += temp * temp;

        // System.out.printf(" %f",(temp*temp));

        // System.out.printf("= %f",(delta_y));

        // System.out.printf(" total = %f\n",(delta_x + delta_y));

        return (deltaX + deltaY);
    }

    public int[] findHorizontalSeam() // sequence of indices for horizontal seam
    {
        if (!isTransposed)
            transpose();

        int[] res = findVerticalSeam();
        transpose();
        return res;
    }
    
    
    @SuppressWarnings("unchecked")
    public int[] findVerticalSeam() // sequence of indices for vertical seam
    {

        PriorityQueue<Frontier> pq = new PriorityQueue<Frontier>();

        double[] cost1 = new double[width];
        double[] cost2 = new double[width];
        //for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++) {
                //cost[i][j] = Integer.MAX_VALUE;
                cost1[j] = Integer.MAX_VALUE;
                cost2[j] = Integer.MAX_VALUE;
            }

        for (int i = 0; i < width; i++) {
            Frontier f = new Frontier();
            f.cost = energy(i, 0);
            //cost[0][i] = f.cost;
            cost1[i] = f.cost;
            f.path.add(i);
            pq.add(f);
        }

        int h = 0;
        while (h < height - 1) {
            for (int j = 0; j < width; j++) {
                cost2[j] = Integer.MAX_VALUE;
            }
            Frontier f = pq.poll();
            int idx = f.path.getLast();

            Frontier f1 = new Frontier();
            f1.cost = f.cost + energy(idx, f.h + 1);
            f1.path = (LinkedList<Integer>) f.path.clone();
            f1.path.add(idx);

            if (f.h + 1 > h) {
                h = f.h + 1;
            }
            f1.h = f.h + 1;
            //if (cost[f1.h][idx] > f1.cost) {
            if (f1.cost < cost2[idx]) {
                //cost[f1.h][idx] = f1.cost;
                cost2[idx] = f1.cost;
                pq.add(f1);
            }

            if (idx > 1) {
                f1 = new Frontier();
                f1.cost = f.cost + energy(idx - 1, f.h + 1);
                f1.path = (LinkedList<Integer>) f.path.clone();
                f1.path.add(idx - 1);
                f1.h = f.h + 1;
                //if (cost[f1.h][idx] > f1.cost) {
                if (f1.cost < cost2[idx-1]) {
                    //cost[f1.h][idx] = f1.cost;
                    cost2[idx-1] = f1.cost;
                    pq.add(f1);
                }

            }

            if (idx < width - 1) {
                f1 = new Frontier();

                f1.cost = f.cost + energy(idx + 1, f.h + 1);
                f1.path = (LinkedList<Integer>) f.path.clone();
                f1.path.add(idx + 1);
                f1.h = f.h + 1;
                //if (cost[f1.h][idx] > f1.cost) {
                if (f1.cost < cost2[idx+1]) {
                    //cost[f1.h][idx] = f1.cost;
                    cost2[idx+1] = f1.cost;
                    pq.add(f1);
                }

            }
            
            double[] t = cost1;
            cost1 = cost2;
            cost2 = t;

        }

        Frontier f = pq.poll();
        while (f.h < height - 1) {
            f = pq.poll();
        }
        int[] res = new int[f.path.size()];

        int index = 0;
        for (int i : f.path) {
            res[index++] = i;
        }

        return res;
    }

    public void removeHorizontalSeam(int[] seam) // remove horizontal seam from
                                                 // current picture
    {
        if (seam == null)
            throw new NullPointerException("Null reference to seam");
        if (seam.length != width)
            throw new IllegalArgumentException(String.format(
                    "seam.length (%d) !=  width (%d)", seam.length, width));

        if (!isTransposed)
            transpose();

        removeVerticalSeam(seam);

        transpose();

    }

    public void removeVerticalSeam(int[] seam) // remove vertical seam from
                                               // current picture
    {
        if (seam == null)
            throw new NullPointerException("Null reference to seam");

        if (seam.length != height)
            throw new IllegalArgumentException("seam.length !=  height");

        for (int i = 0; i < height; i++) {
            RGB[] t = new RGB[width - 1];
            if (seam[i] > 0)
                System.arraycopy(data[i], 0, t, 0, seam[i]);
            if (width - 1 - seam[i] > 0)
                System.arraycopy(data[i], seam[i] + 1, t, seam[i], width - 1
                        - seam[i]);
            data[i] = t;
        }
        width = width - 1;
    }

    private void transpose() {

        RGB[][] dataT = new RGB[width][height];

        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++) {
                dataT[j][i] = data[i][j];
            }
        data = dataT;
        int t = height;
        height = width;
        width = t;
        if (isTransposed) {
            isTransposed = false;
        } else {
            isTransposed = true;
        }
    }

    private class RGB {
        private byte R;
        private byte G;
        private byte B;

        public short getR() {
            return (short) (R & 0xFF);
        }

        public short getG() {
            return (short) (G & 0xFF);
        }

        public short getB() {
            return (short) (B & 0xFF);
        }
    }

    private class Frontier implements Comparable<Frontier> {
        private LinkedList<Integer> path = new LinkedList<Integer>();
        private int h;
        private double cost;
        @Override
        public int compareTo(Frontier o) {
            // TODO Auto-generated method stub
            return Double.compare(cost, o.cost);
        }
    }

}
