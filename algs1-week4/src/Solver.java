import java.util.Comparator;

public class Solver {

    private class Node {
        private Board b;
        private Node prev;
        private int size;

        Node(Board bb, Node n) {
            b = bb;
            prev = n;
            if (n != null) {
                size = n.size + 1;
            }
        }
    }

    private MinPQ<Node> pq1;
    private MinPQ<Node> pq2;

    public Solver(Board initial) // find a solution to the initial board (using
                                 // the A* algorithm)
    {
        Board twin = initial.twin();

        // System.out.println(initial.toString());
        // System.out.println(twin.toString());

        Comparator<Node> comp = new Comparator<Node>() {

            @Override
            public int compare(Node o1, Node o2) {
                int p1 = o1.size + o1.b.manhattan();
                int p2 = o2.size + o2.b.manhattan();

                if (p1 > p2) {
                    return 1;
                } else if (p1 < p2) {
                    return -1;
                } else {
                    return 0;
                }
            }
        };

        pq1 = new MinPQ<Node>(comp);
        pq2 = new MinPQ<Node>(comp);

        Node q1 = new Node(initial, null);

        Node q2 = new Node(twin, null);

        pq1.insert(q1);
        pq2.insert(q2);

        Board t1 = pq1.min().b;
        Board t2 = pq2.min().b;

        while (!(t1.isGoal() || t2.isGoal())) {

            Node q = pq1.delMin();

            for (Board b : q.b.neighbors()) {
                if (q.prev == null || !q.prev.b.equals(b)) {
                    // System.out.println("pq1 add\n"+q.size);
                    pq1.insert(new Node(b, q));

                }
            }

            q = pq2.delMin();

            for (Board b : q.b.neighbors()) {
                if (q.prev == null || !q.prev.b.equals(b)) {
                    // System.out.println("pq2 add\n"+b.toString());
                    pq2.insert(new Node(b, q));

                }
            }

            t1 = pq1.min().b;
            t2 = pq2.min().b;
        }

    }

    public boolean isSolvable() // is the initial board solvable?
    {
        return (pq1.min().b.isGoal());
    }

    public int moves() // min number of moves to solve initial board; -1 if
                       // unsolvable
    {
        if (!isSolvable()) {
            return -1;
        }
        return pq1.min().size;
    }

    public Iterable<Board> solution() // sequence of boards in a shortest
                                      // solution; null if unsolvable
    {
        if (!isSolvable()) {
            return null;
        }

        Node n = pq1.min();
        Stack<Board> s = new Stack<Board>();

        while (n.prev != null) {
            s.push(n.b);
            n = n.prev;
        }
        s.push(n.b);
        return s;
    }

    // public static void main(String[] args) // solve a slider puzzle (given
    // below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
