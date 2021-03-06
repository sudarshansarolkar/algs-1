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

        return get(p, true) != null;
    }

    public void draw() // draw all points to standard draw
    {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius();
        StdDraw.line(0, 1, 0, 0);
        StdDraw.line(0, 0, 1, 0);
        StdDraw.line(1, 0, 1, 1);
        StdDraw.line(0, 1, 1, 1);
        draw(root, root, true);
        // StdDraw.line(x0, y0, x1, y1);
    }

    private void draw(Node n, Node parent, boolean flag) // draw all points to
                                                         // standard draw
    {
        if (n == null)
            return;
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        n.p.draw();
        StdDraw.setPenRadius();
        if (flag) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(n.p.x(), n.rect.ymin(), n.p.x(), n.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            // System.out.println("Draw rec"+n.rect.toString());
            StdDraw.line(n.rect.xmin(), n.p.y(), n.rect.xmax(), n.p.y());
        }

        draw(n.lb, n, !flag);
        draw(n.rt, n, !flag);

        // StdDraw.line(x0, y0, x1, y1);
    }

    public Iterable<Point2D> range(RectHV rect) // all points that are inside
                                                // the rectangle
    {
        Queue<Point2D> q = new Queue<Point2D>();
        range(root, rect, q);
        return q;
    }

    private void range(Node n, RectHV rect, Queue<Point2D> q) {
        if (n == null || !n.rect.intersects(rect))
            return;

        if (rect.contains(n.p)) {
            q.enqueue(n.p);
        }

        range(n.lb, rect, q);
        range(n.rt, rect, q);

    }

    public Point2D nearest(Point2D p) // a nearest neighbor in the set to point
                                      // p; null if the set is empty
    {
        NNode res = new NNode();
        if (root != null) {
            res.p = root.p;
            res.dis = res.p.distanceSquaredTo(p);
        }
        nearest(root, p, res);
        return res.p;
    }

    private void nearest(Node n, Point2D p, NNode res) {
        if (n == null)
            return;

        double dis2 = n.rect.distanceSquaredTo(p);

        if (dis2 > res.dis)
            return;
        
        double dis3 =  n.p.distanceSquaredTo(p);
        if (dis3 < res.dis) {
            res.p = n.p;
            res.dis = dis3;
        }
        
        
        // choose the subtree that is on the same side of the splitting line as 
        // the query point as the first subtree to explore the closest point found 
        // while exploring the first subtree 
        // may enable pruning of the second subtree. 
        
        boolean exportLeft = false;
        boolean exportRight = false;
        if (n.lb != null && n.lb.rect.contains(p)) {
            nearest(n.lb, p, res);
            exportLeft = true;
        }
        if (n.rt != null && n.rt.rect.contains(p)) {
            nearest(n.rt, p, res);
            exportRight = true;
        }

        if (!exportLeft) {
            nearest(n.lb, p, res);
        }

        if (!exportRight) {
            nearest(n.rt, p, res);
        }

    }

    /*private boolean expore(Node n, Point2D p, Point2D[] res) {
        if (res[0] == null)
            return true;
        if (n.rect.contains(p))
            return true;
        double dis2 = n.rect.distanceSquaredTo(p);
        double dis = res[0].distanceSquaredTo(p);
        // find x distance
        // perpendicular distance to rectangle
        if (dis2 < dis)
            return true;

        return false;
    }*/
    private static class NNode{
        Point2D p;
        double dis;
    }
    private static class Node {
        private Point2D p; // the point
        private RectHV rect; // the axis-aligned rectangle corresponding to this
                             // node
        private Node lb; // the left/bottom subtree
        private Node rt; // the right/top subtree

        public Node() {
            // rect = new RectHV(0, 0, 1, 1);
        }
    }

    private Point2D get(Point2D p, boolean flag) {
        return get(root, p, flag);
    }

    private Point2D get(Node x, Point2D p, boolean flag) { // Return value
                                                           // associated with
                                                           // key
        // in the subtree rooted at x;
        // return null if key not present
        // in subtree rooted at x.
        if (x == null)
            return null;
        boolean cmp = false;
        if (flag)
            cmp = compareDouble(p.x(), x.p.x());
        else
            cmp = compareDouble(p.y(), x.p.y());
        if (cmp)
            return get(x.lb, p, !flag);
        else {
            if (x.p.equals(p))
                return p;
            return get(x.rt, p, !flag);
        }

    }

    private void put(Point2D p, boolean flag) { // Search for key. Update value
                                                // if
                                                // found; grow table if new.
        // root =
        put(root, p, flag);
    }

    private Node put(Node x, Point2D p, boolean flag) {
        if (x == null) {
            Node n = new Node();
            n.p = p;
            n.rect = new RectHV(0, 0, 1, 1);
            size++;
            if (root == null)
                root = n;
            return n;
        }

        if (x.p.equals(p)) {
            // System.out.println("duplicate point rejected"+p.toString());
            return x;
        }
        boolean cmp = false;
        if (flag)
            cmp = compareDouble(p.x(), x.p.x());
        else
            cmp = compareDouble(p.y(), x.p.y());

        if (cmp) {

            // x.lb = put(x.lb, p, !flag);
            if (x.lb == null) {
                // System.out.println("Adding left node to"+
                // x.p.toString()+"with rect"+x.rect.toString());
                Node n = new Node();
                n.p = p;
                size++;
                x.lb = n;
                if (flag)
                    x.lb.rect = new RectHV(x.rect.xmin(), x.rect.ymin(),
                            x.p.x(), x.rect.ymax());
                else
                    x.lb.rect = new RectHV(x.rect.xmin(), x.rect.ymin(),
                            x.rect.xmax(), x.p.y());
                return n;
            } else {
                put(x.lb, p, !flag);
            }
        } else {
            if (x.rt == null) {
                // System.out.println("Adding right node to"+x.p.toString()+
                // "with rect"+x.rect.toString());
                Node n = new Node();
                n.p = p;
                size++;
                x.rt = n;
                if (flag)
                    x.rt.rect = new RectHV(x.p.x(), x.rect.ymin(),
                            x.rect.xmax(), x.rect.ymax());
                else
                    x.rt.rect = new RectHV(x.rect.xmin(), x.p.y(),
                            x.rect.xmax(), x.rect.ymax());

                return n;
            } else {
                if (x.rt.p.equals(p)) {
                    // System.out.println("duplicate point rejected"+p.toString());
                    return x.rt;
                }
                put(x.rt, p, !flag);
            }
            // else return x.rt;
        }
        // x.N = size(x.lb) + size(x.rt) + 1;
        return x;
    }

    private boolean compareDouble(double d1, double d2) {
        return d1 < d2;
    }

    public static void main(String[] args) // unit testing of the methods
                                           // (optional)
    {
        String filename = args[0];
        In in = new In(filename);

        StdDraw.show(0);

        // initialize the two data structures with point from standard input
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);

        }

        kdtree.draw();
        StdDraw.show(50);
    }
}
