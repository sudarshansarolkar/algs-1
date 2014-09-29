import java.util.BitSet;

public class Percolation {
    private BitSet data;
    private int size;
    private WeightedQuickUnionUF cc;

    private WeightedQuickUnionUF ccb;

    /*
     * By convention, the row and column indices i and j are integers between 1
     * and N, where (1, 1) is the upper-left site
     * 
     * @throws IndexOutOfBoundsException if any argument to open(), isOpen(), or
     * isFull() is outside its prescribed range.
     * 
     * @throws IllegalArgumentException if N ≤ 0. The constructor should take
     * time proportional to N2; all methods should take constant time plus a
     * constant number of calls to the union-find methods union(), find(),
     * connected(), and count().
     */

    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        data = new BitSet(N * N);
        size = N;
        cc = new WeightedQuickUnionUF(N * N + 2);
        ccb = new WeightedQuickUnionUF(N * N + 1);
    }

    private int get1dIndex(int i, int j) {
        if ((i > size || i <= 0) || (j > size || j <= 0)) {
            throw new IndexOutOfBoundsException(String.format("i=%d, j=%d", i,
                    j));
        }
        int bitIndex = (i - 1) * size + (j - 1);
        return bitIndex;
    }

    private void checkNeighbour(int i, int j, int bitIndex) {
        // left
        int bitIndexTemp = 0;
        try {
            if (isOpen(i, j)) {
                bitIndexTemp = get1dIndex(i, j);
                cc.union(bitIndex, bitIndexTemp);
                ccb.union(bitIndex, bitIndexTemp);

            }
        } catch (IndexOutOfBoundsException e) {
            // ignore
        }
    }

    // open site (row i, column j) if it is not already
    public void open(int i, int j) {
        if ((i > size || i <= 0) || (j > size || j <= 0)) {
            throw new IndexOutOfBoundsException(String.format("i=%d, j=%d", i,
                    j));
        }

        if (isOpen(i, j)) {
            return;
        }

        int bitIndex = get1dIndex(i, j);
        if (i == 1) {
            cc.union(size * size, bitIndex);
            ccb.union(size * size, bitIndex);
        }

        if (i == size) {
            cc.union(size * size + 1, bitIndex);
        }

        data.set(bitIndex);

        // left
        checkNeighbour(i - 1, j, bitIndex);

        // right
        checkNeighbour(i + 1, j, bitIndex);

        // top
        checkNeighbour(i, j - 1, bitIndex);

        // down
        checkNeighbour(i, j + 1, bitIndex);

    }

    public boolean isOpen(int i, int j) // is site (row i, column j) open?
    {
        return data.get(get1dIndex(i, j));
    }

    public boolean isFull(int i, int j) // is site (row i, column j) full?
    {
        return cc.connected(size * size, get1dIndex(i, j))
                && ccb.connected(size * size, get1dIndex(i, j));
    }

    public boolean percolates() // does the system percolate?
    {
        return cc.connected(size * size, size * size + 1);
    }

    // test client, optional
    public static void main(String[] args) {

    }
}
