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
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

private static final double CONFIDENCE_95 = 1.96;
private static final double CONFIDENCE_99 = 3.29;
private int trials = 0;
private double[] samples;

public PercolationStats(int n, int trials) 
{
if (n <= 0 || trials <= 0) {
throw new IllegalArgumentException("grid and number of trials can not be empty");
}
this.trials = trials;
this.samples = new double[trials];

for (int i = 0; i < trials; i++) {
samples[i] = getPercolationThreshold(n);
}
}

private double getPercolationThreshold(int n) {

Percolation p = new Percolation(n);
double count = 0;
double totalSites = n * n;
boolean[] randoms = new boolean[n*n];

while (!p.percolates()) {
	int i = StdRandom.uniform(1, n + 1);
    int j = StdRandom.uniform(1, n + 1);
    if (!p.isOpen(i, j)) {
 p.open(i, j);
 count++;

}
}

return  count / totalSites;

}

private double mean=0;
public double mean() // sample mean of percolation threshold
{
	if(mean == 0)
     mean = StdStats.mean(samples);
	return mean;
}
private double stddev=0;
public double stddev() // sample standard deviation of percolation threshold
{
 if(stddev == 0)
	 stddev = StdStats.stddev(samples);
 return stddev;
}

public double confidenceLo() // low endpoint of 95% confidence interval
{
double mean = mean();
double stddev = stddev();

return mean - (CONFIDENCE_95 * stddev / Math.sqrt(trials));

}

public double confidenceHi() // high endpoint of 95% confidence interval
{
double mean = mean();
double stddev = stddev();

return mean + (CONFIDENCE_95 * stddev / Math.sqrt(trials));

	
}

public static void main(String[] args) // test client (described below)
{
PercolationStats ps =
new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
System.out.println("mean                    = " + ps.mean());
System.out.println("stddev                  = " + ps.stddev());
System.out.
println("95% confidence interval = [" + ps.confidenceLo()+", "+ps.confidenceHi()+"]");
}
}
