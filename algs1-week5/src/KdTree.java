public class KdTree {
    private Node root;
    private int size;
    
    public KdTree() // construct an empty set of points
    {

    }

    public boolean isEmpty() // is the set empty?
    {

        return size == 0;
    }

    public int size() // number of points in the set
    {

        return size;
    }

    public void insert(Point2D p) // add the point to the set (if it is not
                                  // already in the set)
    {
        put(p, true);
    }

    public boolean contains(Point2D p) // does the set contain point p?
    {

        return get(p, true)!=null;
    }

    public void draw() // draw all points to standard draw
    {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01); 
        
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.setPenRadius() ;
           //StdDraw.line(x0, y0, x1, y1);
    }

    public Iterable<Point2D> range(RectHV rect) // all points that are inside
                                                // the rectangle
    {
        return null;
    }

    public Point2D nearest(Point2D p) // a nearest neighbor in the set to point
                                      // p; null if the set is empty
    {
        return null;
    }

    private static class Node {
        private Point2D p; // the point
        private RectHV rect; // the axis-aligned rectangle corresponding to this
                             // node
        private Node lb; // the left/bottom subtree
        private Node rt; // the right/top subtree
    }

    public Point2D get(Point2D p, boolean flag) {
        return get(root, p, flag);
    }

    private Point2D get(Node x, Point2D p, boolean flag) { // Return value associated with key
                                             // in the subtree rooted at x;
                                             // return null if key not present
                                             // in subtree rooted at x.
        if (x == null)
            return null;
        boolean cmp = false;
        if (flag)
            cmp = compareDouble(x.p.x(), p.x());
        else
            cmp = compareDouble(x.p.y(), p.y());
        if (cmp)
            return get(x.lb, p, !flag);
        else{
            if(x.p.equals(p)) return p;
            return get(x.rt, p, !flag);
        }
        
    }

    public void put(Point2D p, boolean flag) { // Search for key. Update value if
                                              // found; grow table if new.
        root = put(root, p, flag);
    }

    private Node put(Node x, Point2D p, boolean flag) {
        if (x == null) {
            Node n = new Node();
            n.p = p;
            size++;
            return n;
        }
        boolean cmp = false;
        if (flag)
            cmp = compareDouble(x.p.x(), p.x());
        else
            cmp = compareDouble(x.p.y(), p.y());
        if (cmp)
            x.lb = put(x.lb, p, !flag);
        else
            x.rt = put(x.rt, p, !flag);
        // x.N = size(x.lb) + size(x.rt) + 1;
        return x;
    }

    private boolean compareDouble(double d1, double d2) {
        if (d1 < d2) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) // unit testing of the methods
                                           // (optional)
    {

    }
}
