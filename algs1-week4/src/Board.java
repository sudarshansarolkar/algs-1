public class Board {
    private int[][] blocks;

    private int res = -1;
    private boolean goal = false;
    private boolean ischecked = false;
    private Board twin;

    public Board(int[][] b) // construct a board from an N-by-N array of blocks
    {
        blocks = b.clone();
    }

    // (where blocks[i][j] = block in row i, column j)
    public int dimension() // board dimension N
    {
        return blocks.length;
    }

    public int hamming() // number of blocks out of place
    {
        int dis = 0;
        int N = blocks.length;
        for (int i = 0; i < N; i++) {

            for (int j = 0; j < N; j++) {
                if (blocks[i][j] == 0)
                    continue;
                if (blocks[i][j] != ((i * N) + j + 1)) {
                    dis++;
                }
            }
        }

        return dis;
    }

    public int manhattan() // sum of Manhattan distances between blocks and goal
    {
        if (res != -1) {
            return res;
        }
        int dis = 0;
        int N = blocks.length;
        for (int i = 0; i < N; i++) {

            for (int j = 0; j < N; j++) {
                if (blocks[i][j] == 0)
                    continue;
                int x = blocks[i][j] / N;
                int y = blocks[i][j] % N - 1;
                if (blocks[i][j] % N == 0) {
                    y = N - 1;
                    x = x - 1;
                }

                if (blocks[i][j] / N == N) {
                    x = N - 1;
                }

                // System.out.println("blocks[i][j]"+blocks[i][j]+" x="+x+" y="+y);
                dis += Math.abs(x - i) + Math.abs(y - j);

            }
        }
        res = dis;
        return dis;
    }

    public boolean isGoal() // is this board the goal board?
    {
        if (ischecked) {
            return goal;
        }
        ischecked = true;
        int N = blocks.length;
        for (int i = 0; i < N; i++) {

            for (int j = 0; j < N; j++) {

                if (blocks[i][j] == 0) {
                    goal = ((i == (N - 1)) && (j == (N - 1)));
                    return goal;
                }

                if (blocks[i][j] != ((i * N) + j + 1)) {
                    goal = false;
                    return false;
                }
            }
        }
        goal = true;
        return true;
    }

    private int[][] getClone() {
        int N = blocks.length;
        int[][] b = new int[N][N];

        for (int i = 0; i < N; i++) {

            for (int j = 0; j < N; j++) {

                b[i][j] = blocks[i][j];
            }

        }

        return b;
    }

    public Board twin() // a board that is obtained by exchanging two adjacent
                        // blocks in the same row
    {
        if (twin != null) {
            return twin;
        }
        int N = blocks.length;

        /*
         * int[][] b = new int[N][N];
         * 
         * for (int i = 0; i < N; i++) {
         * 
         * for (int j = 0; j < N; j++) {
         * 
         * b[i][j] = blocks[i][j]; } }
         */

        int[][] b = getClone();

        int rand1 = StdRandom.uniform(N - 1);
        int rand2 = StdRandom.uniform(N - 1);

        int tmp = b[rand1][rand2];
        if (N > 2) {
            if (tmp != 0 && b[rand1][rand2 + 1] != 0) {
                b[rand1][rand2] = b[rand1][rand2 + 1];
                b[rand1][rand2 + 1] = tmp;
            } else {
                if (rand1 == N - 1) {
                    rand1 = N - 2;
                } else {
                    rand1++;
                }
                tmp = b[rand1][rand2];
                b[rand1][rand2] = b[rand1][rand2 + 1];
                b[rand1][rand2 + 1] = tmp;
            }
        } else {
            if (b[0][0] != 0 && b[0][1] != 0) {
                tmp = b[0][0];
                b[0][0] = b[0][1];
                b[0][1] = tmp;
            } else {
                tmp = b[1][0];
                b[1][0] = b[1][1];
                b[1][1] = tmp;
            }
        }
        
        twin = new Board(b);
        return twin;
    }

    public boolean equals(Object y) // does this board equal y?
    {
        if (y == null) {
            return false;
        }
        if (!(y instanceof Board)) {
            return false;
        }

        Board by = (Board) y;

        int[][] b = by.blocks;
        int N = blocks.length;

        if (N != b.length) {
            return false;
        }

        for (int i = 0; i < N; i++) {

            for (int j = 0; j < N; j++) {

                if (b[i][j] != blocks[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public Iterable<Board> neighbors() // all neighboring boards
    {
        Queue<Board> n = new Queue<Board>();

        int i = 0;
        int j = 0;
        int N = blocks.length;
        for (int ii = 0; ii < N; ii++) {

            for (int jj = 0; jj < N; jj++) {

                if (blocks[ii][jj] == 0) {
                    i = ii;
                    j = jj;
                    break;
                }
            }

            if (blocks[i][j] == 0) {
                break;
            }
        }

        if (i > 0) {
            int[][] b = getClone();

            int tmp = b[i - 1][j];
            b[i - 1][j] = 0;
            b[i][j] = tmp;
            n.enqueue(new Board(b));
        }

        if (i < N - 1) {
            int[][] b = getClone();

            int tmp = b[i + 1][j];
            b[i + 1][j] = 0;
            b[i][j] = tmp;
            n.enqueue(new Board(b));
        }

        if (j > 0) {
            int[][] b = getClone();

            int tmp = b[i][j - 1];
            b[i][j - 1] = 0;
            b[i][j] = tmp;
            n.enqueue(new Board(b));
        }

        if (j < N - 1) {
            int[][] b = getClone();

            int tmp = b[i][j + 1];
            b[i][j + 1] = 0;
            b[i][j] = tmp;
            n.enqueue(new Board(b));
        }

        return n;
    }

    public String toString() // string representation of this board (in the
                             // output format specified below)
    {
        int N = blocks.length;
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) // unit tests (not graded)
    {
        int[][] b = new int[3][3];

        b[0][0] = 8;
        b[0][1] = 1;
        b[0][2] = 3;
        b[1][0] = 4;
        b[1][1] = 0;
        b[1][2] = 2;
        b[2][0] = 7;
        b[2][1] = 6;
        b[2][2] = 5;

        Board bb = new Board(b);

        // System.out.println(bb.manhattan());

        b = new int[2][2];

        b[0][0] = 1;
        b[0][1] = 0;
        b[1][0] = 2;
        b[1][1] = 3;

        bb = new Board(b);

        // System.out.println(bb.manhattan());

        b[0][0] = 1;
        b[0][1] = 2;
        b[1][0] = 3;
        b[1][1] = 0;

        bb = new Board(b);

        // System.out.println(bb.manhattan());

        b = new int[3][3];

        b[0][0] = 1;
        b[0][1] = 2;
        b[0][2] = 3;
        b[1][0] = 4;
        b[1][1] = 5;
        b[1][2] = 6;
        b[2][0] = 7;
        b[2][1] = 8;
        b[2][2] = 0;

        bb = new Board(b);

        // System.out.println(bb.isGoal());

        b[0][0] = 0;
        b[0][1] = 1;
        b[0][2] = 3;
        b[1][0] = 4;
        b[1][1] = 2;
        b[1][2] = 5;
        b[2][0] = 7;
        b[2][1] = 8;
        b[2][2] = 6;
        bb = new Board(b);

        for (Board b1 : bb.neighbors()) {
            System.out.println(b1.toString());
        }

    }
}