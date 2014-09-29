public class PercolationStats {
    private int iterations;
    private int size;
    private double[] arrRes;

    /*
     * The constructor should throw a java.lang.IllegalArgumentException if
     * either N ≤ 0 or T ≤ 0.
     */
    public PercolationStats(int N, int T) // perform T independent computational
                                          // experiments on an N-by-N grid
    {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }

        iterations = T;
        size = N * N;

        arrRes = new double[T];

        for (int i = 0; i < iterations; i++) {

            Percolation p = new Percolation(N);

            while (!p.percolates()) {
                int rand = StdRandom.uniform(size);
                p.open(rand / N + 1, (rand % N) + 1);
            }

            int count = 0;
            for (int j = 0; j < size; j++) {
                if (p.isOpen(j / N + 1, (j % N) + 1)) {
                    count++;
                }
            }

            arrRes[i] = ((double) count / size);

        }

    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(arrRes);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(arrRes);
    }

    // returns lower bound of the 95% confidence interval
    public double confidenceLo() {
        return (mean() - ((1.96 * stddev()) / Math.sqrt(iterations)));
    }

    // returns upper bound of the 95% confidence interval
    public double confidenceHi() {
        return (mean() + ((1.96 * stddev()) / Math.sqrt(iterations)));
    }

    /*
     * mean = 0.5929934999999997 stddev = 0.00876990421552567 95% confidence
     * interval = 0.5912745987737567, 0.5947124012262428
     */
    public static void main(String[] args) // test client, described below
    {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        PercolationStats pstat = new PercolationStats(N, T);
        System.out.println(String.format("mean                    = %f",
                pstat.mean()));
        System.out.println(String.format("stddev                  = %f",
                pstat.stddev()));
        System.out.println(String.format("95%% confidence interval = %f, %f",
                pstat.confidenceLo(), pstat.confidenceHi()));

    }
}
