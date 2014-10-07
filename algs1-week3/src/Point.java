/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    /**
     * compare points by their y-coordinates, breaking ties by their
     * x-coordinates. Formally, the invoking point (x0, y0) is less than the
     * argument point (x1, y1) if and only if either y0 < y1 or if y0 = y1 and
     * x0 < x1.
     */
    public final Comparator<Point> SLOPE_ORDER = new Comparator<Point>() {

        @Override
        public int compare(Point o1, Point o2) {
            if (slopeTo(o1) > slopeTo(o2)) {
                return 1;
            } else if (slopeTo(o1) < slopeTo(o2)) {
                return -1;
            } else {
                return 0;
            }
        }
    }; // YOUR DEFINITION HERE

    private final int x; // x coordinate
    private final int y; // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * return the slope between the invoking point (x0, y0) and the argument
     * point x1, y1 which is given by the formula (y1-y0)/(x1-x0) Treat the
     * slope of a horizontal line segment as positive zero; treat the slope of a
     * vertical line segment as positive infinity; treat the slope of a
     * degenerate line segment (between a point and itself) as negative
     * infinity.
     * 
     * @param that
     * @return
     */
    // slope between this point and that point
    public double slopeTo(Point that) {
        if (that == null) {
            throw new NullPointerException();
        }
        
        if (this.x == that.x) {
            return Double.POSITIVE_INFINITY;

        }

        if (this.y == that.y) {
            return 0;
        }

        if (this.x == that.x && this.y == that.y) {
            return Double.NEGATIVE_INFINITY;
        }

        return ((double) (that.y - this.y)) / (double) (that.x - this.x);
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        if (this.y > that.y) {
            return 1;
        } else if (that.y > this.y) {
            return -1;
        } else {
            if (this.x > that.x)
                return 1;
            else if (that.x > this.x)
                return -1;
            else {
                return 0;
            }
        }
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }
}
