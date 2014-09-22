package week1;

import java.util.BitSet;

public class PercolationStats {
	Percolation p;
	int iterations;
	int size ;
	/*
	 * The constructor should throw a java.lang.IllegalArgumentException if either N ≤ 0 or T ≤ 0. 
	 */
    public PercolationStats(int N, int T)    // perform T independent computational experiments on an N-by-N grid
    {
    	if(N<=0 || T<=0){
    		throw new IllegalArgumentException();
    	}
    	p = new Percolation(N);
    	iterations= T;
    	size= N*N;
    	
    }
    public double mean()                     // sample mean of percolation threshold
    public double stddev()                   // sample standard deviation of percolation threshold
    public double confidenceLo()             // returns lower bound of the 95% confidence interval
    public double confidenceHi()             // returns upper bound of the 95% confidence interval
    /*
     * mean                    = 0.5929934999999997
	 * stddev                  = 0.00876990421552567
	 * 95% confidence interval = 0.5912745987737567, 0.5947124012262428


     */
    public static void main(String[] args)   // test client, described below
    {
    	int N = Integer.parseInt(args[0]);
    	int T = Integer.parseInt(args[1]);
    	
    	PercolationStats pstat= new PercolationStats(N, T);
    	System.out.println(System.out.format("mean                    = %f",pstat.mean()));
    	System.out.println(System.out.format("stddev                  = %f",pstat.stddev()));
    	System.out.println(System.out.format("95% confidence interval = %f, %f",));


    }
}
