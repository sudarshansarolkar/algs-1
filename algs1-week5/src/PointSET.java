import java.util.TreeSet;

public class PointSET {
    private TreeSet<Point2D> rect = new TreeSet<Point2D>();

    public PointSET() // construct an empty set of points
    {

    }

    public boolean isEmpty() // is the set empty?
    {

        return (rect.size() == 0);
    }

    public int size() // number of points in the set
    {

        return rect.size();
    }

    public void insert(Point2D p) // add the point to the set (if it is not
                                  // already in the set)
    {
        if (!rect.contains(p))
            rect.add(p);
    }

    public boolean contains(Point2D p) // does the set contain point p?
    {

        return rect.contains(p);
    }

    public void draw() // draw all points to standard draw
    {
        for (Point2D p : rect)
            p.draw();
    }

    public Iterable<Point2D> range(RectHV rect) // all points that are inside
                                                // the rectangle
    {
        Queue<Point2D> q = new Queue<Point2D>();
        for (Point2D p : this.rect) {
            if (p.x() <= rect.xmax() && p.x() >= rect.xmin()
                    && p.y() <= rect.ymax() && p.y() >= rect.ymin()) {
                q.enqueue(p);
            }
        }

        return q;
    }

    public Point2D nearest(Point2D p) // a nearest neighbor in the set to point
                                      // p; null if the set is empty
    {
        double disMin = Double.POSITIVE_INFINITY;
        Point2D res = null;
        for (Point2D pp : this.rect) {
            double dis = pp.distanceSquaredTo(p);
            if (dis < disMin) {
                res = pp;
                disMin = dis;
            }
        }
        return res;
    }

    public static void main(String[] args) // unit testing of the methods
                                           // (optional)
    {

    }
}
