/******************************************************************************
 *  Name:    Chandrashekhar Muradnar
 *  NetID:   shekhar249
 *  Precept: P01
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 * 
 *  Description:  Model an n-by-n percolation system using the union-find
 *                data structure.
 ******************************************************************************/
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public final class Percolation {
	private boolean[] site;
	private int n;
	private WeightedQuickUnionUF wq;
	private int numberOfOpenSites = 0;
	private boolean[] siteStatusTop;
	private boolean[] siteStatusBottom;
	private boolean isPercolate = false;

	public Percolation(int n) // create n-by-n grid, with all sites blocked
	{
		if (n <= 0) {
			throw new IllegalArgumentException("grid size cannot be zero or less");
		}
		wq = new WeightedQuickUnionUF(n * n);
		this.n = n;
		site = new boolean[(n * n)];
		siteStatusTop = new boolean[n * n];
		siteStatusBottom = new boolean[n * n];
		// blocked|open|connectedToTop|connectedToBottom
		// 8,4,2,1
	}

	private int getIndexForRowColumn(int r, int c) {
		return (r - 1) * n + (c - 1);
	}

	private void validateRowCol(int row, int col) {
		if (row <= 0 || col <= 0 || row > n || col > n) {
			throw new IllegalArgumentException("invaid site for open");
		}
	}

	private void bitSetConnectedToTop(int p) {
		siteStatusTop[p] = true;
	}

	private boolean bitGetConnectedToTop(int p) {
		return siteStatusTop[p];
	}

	private void bitSetConnectedToBottom(int p) {
		siteStatusBottom[p] = true;
	}

	private boolean bitGetConnectedToBottom(int p) {
		return siteStatusBottom[p];
	}

	public void open(int row, int col) {
		validateRowCol(row, col);

		int p = getIndexForRowColumn(row, col);
		if (!site[p]) {
			site[p] = true;
			numberOfOpenSites++;
		} else {
			return;
		}

		

		boolean tleft = false, tright = false, ttop = false, tbottom = false;
		boolean bleft = false, bright = false, btop = false, bbottom = false;
		if (col > 1) {
			int q = getIndexForRowColumn(row, col - 1);
			if (site[q]) {
				tleft = bitGetConnectedToTop(wq.find(q));
				bleft = bitGetConnectedToBottom(wq.find(q));
				wq.union(p, q);
			}
		}
		if (col < n) {
			int q = getIndexForRowColumn(row, col + 1);
			if (site[q]) {
				tright = bitGetConnectedToTop(wq.find(q));
				bright = bitGetConnectedToBottom(wq.find(q));
				wq.union(p, q);
			}
		}
		if (row > 1) {
			int q = getIndexForRowColumn(row - 1, col);
			if (site[q]) {
				ttop = bitGetConnectedToTop(wq.find(q));
				btop = bitGetConnectedToBottom(wq.find(q));
				wq.union(p, q);				
			}
		}
		if (row < n) {
			int q = getIndexForRowColumn(row + 1, col);
			if (site[q]) {
				tbottom = bitGetConnectedToTop(wq.find(q));
				bbottom = bitGetConnectedToBottom(wq.find(q));
				wq.union(p, q);
			}
		}
		boolean connectsToTop=false;
		boolean connectsToBottom=false;
		if (ttop || tbottom || tleft || tright) {
			bitSetConnectedToTop(wq.find(p));
			connectsToTop=true;
		}
		if (btop || bbottom || bleft || bright) {
			bitSetConnectedToBottom(wq.find(p));
			connectsToBottom=true;
		}
        if(connectsToTop && connectsToBottom)
        	isPercolate=true;
		
		if (row == 1) {
			bitSetConnectedToTop(wq.find(p));	
			if (bitGetConnectedToBottom(wq.find(p))) {
				isPercolate = true;
			}
		}
		if (row == n) {
			bitSetConnectedToBottom(wq.find(p));	
			if (bitGetConnectedToTop(wq.find(p))) {
				isPercolate = true;
			}
		}			

	}

	public boolean isOpen(int row, int col) // is site (row, col) open?
	{
		validateRowCol(row, col);
		return site[getIndexForRowColumn(row, col)];
	}

	public boolean isFull(int row, int col) // is site (row, col) full?
	{
		validateRowCol(row, col);

		// return wqf.connected(getIndexForRowColumn(row, col),n * n); //temp comment
		if (isOpen(row, col)) {
			return bitGetConnectedToTop(wq.find(getIndexForRowColumn(row, col)));
		} else {
			return false;
		}
	}

	public int numberOfOpenSites() // number of open sites
	{
		return numberOfOpenSites;
	}

	public boolean percolates() // does the system percolate?
	{
		return isPercolate;
	}

	public static void main(String[] args) {
		Percolation p = new Percolation(3);
		p.open(1, 3);
		p.open(2, 3);
		p.open(3, 1);
		p.open(3, 3);
		System.out.println("Does row 3,col 1 full site ? " + p.isFull(3, 1));
		System.out.println("System Perculates : " + p.percolates());
	}
}
