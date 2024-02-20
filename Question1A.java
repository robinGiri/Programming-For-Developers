public class Question1A {
    
    public static int minCostDecorating(int[][] costs) {
        int n = costs.length; // Number of venues
        if (n == 0) return 0;
        int k = costs[0].length; // Number of themes
        
        // dp[i][j] represents the minimum cost to decorate up to the i-th venue with theme j
        int[][] dp = new int[n][k];
        
        // Initialize the first venue's costs
        for (int j = 0; j < k; j++) {
            dp[0][j] = costs[0][j];
        }
        
        // Fill dp table
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < k; j++) {
                int minCost = Integer.MAX_VALUE;
                // Find the minimum cost of decorating the previous venue with a different theme
                for (int l = 0; l < k; l++) {
                    if (l != j) {
                        minCost = Math.min(minCost, dp[i-1][l]);
                    }
                }
                dp[i][j] = costs[i][j] + minCost;
            }
        }
        
        // The minimum cost will be the minimum in the last row of dp table
        int minTotalCost = Integer.MAX_VALUE;
        for (int j = 0; j < k; j++) {
            minTotalCost = Math.min(minTotalCost, dp[n-1][j]);
        }
        
        return minTotalCost;
    }
    
    public static void main(String[] args) {
        int[][] costs = {{1, 3, 2}, {4, 6, 8}, {3, 1, 5}};
        System.out.println("Minimum cost to decorate all venues: " + minCostDecorating(costs));
    }
}
