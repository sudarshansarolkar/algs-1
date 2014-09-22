package week1;

import java.util.BitSet;

public class Percolation {
	BitSet data ;
	int size ;
	WeightedQuickUnionUF top;
	
	/*
	 * By convention, the row and column indices i and j are integers between 1 and N, where (1, 1) is the upper-left site
	 * @throws IndexOutOfBoundsException if any argument to open(), isOpen(), or isFull() is outside its prescribed range. 
	 * @throws IllegalArgumentException if N ≤ 0. 
	 * The constructor should take time proportional to N2; 
	 * all methods should take constant time plus a constant number of calls to 
	 * the union-find methods union(), find(), connected(), and count(). 
	 */
	
    public Percolation(int N)                // create N-by-N grid, with all sites blocked
    {
    	if(N<=0){
    		throw IllegalArgumentException;
    	}
		data = new BitSet(N*N)
		size = N;
		top = new WeightedQuickUnionUF(N*N+2);
		bottom = new WeightedQuickUnionUF(N*N+2);
    }
    
    private int get1dIndex(int i, int j){
    	if((i > N || i <= 0) || (j > N || j <= 0)){
    		throw new IndexOutOfBoundsException();
    	}
    	int bitIndex = i(-1)*size + (j-1);
    	return bitIndex;
    }
    
    private void checkNeighbour(int i, int j,int bitIndex){
    	//left
    	try{
    		if(isOpen(i, j)){
    			int bitIndexTemp = get1dIndex(i, j);
    			top.union(bitIndex, bitIndexTemp);
    		}
    	}finally{
    		
    	}
    }
    
    public void open(int i, int j)           // open site (row i, column j) if it is not already
    {
    	if((i > N || i <= 0) || (j > N || j <= 0)){
    		throw new IndexOutOfBoundsException();
    	}
    	int bitIndex = get1dIndex(i, j)
    	if(i==1){
    		top.union(0, bitIndex);
    	} else if(i==N){
    		bottom.union(N*N+1, bitIndex);
    	}
    	
    	data.set(bitIndex);
    	
    	//left
    	checkNeighbour(i-1, j);
    	
    	//right
    	checkNeighbour(i+1, j);
    	
    	//top
    	checkNeighbour(i, j-1);
    	
    	//down
    	checkNeighbour(i, j+1);
    	
    	
    }
    public boolean isOpen(int i, int j)      // is site (row i, column j) open?
    {
    	return data.get(get1dIndex(i, j))==false;
    }
    public boolean isFull(int i, int j)      // is site (row i, column j) full?
    {
    	return data.get(get1dIndex(i, j))==true;
    }
    public boolean percolates()              // does the system percolate?
    {
    	
    }
    public static void main(String[] args)   // test client, optional
}
